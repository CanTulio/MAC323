/****************************************************************
    Nome: Caio Túlio de Deus Andrade
    NUSP: 9797232

    Ao preencher esse cabeçalho com o meu nome e o meu número USP,
    declaro que todas as partes originais desse exercício programa (EP)
    foram desenvolvidas e implementadas por mim e que portanto não 
    constituem desonestidade acadêmica ou plágio.
    Declaro também que sou responsável por todas as cópias desse
    programa e que não distribui ou facilitei a sua distribuição.
    Estou ciente que os casos de plágio e desonestidade acadêmica
    serão tratados segundo os critérios divulgados na página da 
    disciplina.
    Entendo que EPs sem assinatura devem receber nota zero e, ainda
    assim, poderão ser punidos por desonestidade acadêmica.

    Abaixo descreva qualquer ajuda que você recebeu para fazer este
    EP.  Inclua qualquer ajuda recebida por pessoas (inclusive
    monitoras e colegas). Com exceção de material de MAC0323, caso
    você tenha utilizado alguma informação, trecho de código,...
    indique esse fato abaixo para que o seu programa não seja
    considerado plágio ou irregular.

    Exemplo:

        A monitora me explicou que eu devia utilizar a função xyz().

        O meu método xyz() foi baseada na descrição encontrada na 
        página https://www.ime.usp.br/~pf/algoritmos/aulas/enumeracao.html.

    Descrição de ajuda ou indicação de fonte:



    Se for o caso, descreva a seguir 'bugs' e limitações do seu programa:

****************************************************************/

/*
 * MAC0323 Algoritmos e Estruturas de Dados II
 * 
 * ADT Topological é uma "representação topológica" de digrafo.
 * Esta implementação usa ADT Digraph do EP13.
 *  
 * Busque inspiração em: 
 *
 *   https://algs4.cs.princeton.edu/42digraph/
 *   https://algs4.cs.princeton.edu/42digraph/DepthFirstOrder.java
 *   https://algs4.cs.princeton.edu/42digraph/Topological.java
 *   https://algs4.cs.princeton.edu/42digraph/DirectedCycle.java
 * 
 * TOPOLOGICAL
 *
 * Topological é uma ¨representação topológica" de um dado digrafo.
 * 
 * As principais operações são: 
 *
 *      - hasCycle(): indica se o digrafo tem um ciclo (DirectedCycle.java)
 *      - isDag(): indica se o digrafo é acyclico (Topological.java)
 *
 *      - pre(): retorna a numeração pré-ordem de um vértice em relação a uma dfs 
 *               (DepthFirstOrder.java)
 *      - pos(): retorna a numareção pós-ordem de um vértice em relação a uma dfs
 *               (DepthFirstOrder.java)
 *      - rank(): retorna a numeração topológica de um vértice (Topological.java)
 * 
 *      - preorder(): itera sobre todos os vértices do digrafo em pré-ordem
 *                    (em relação a uma dfs, DepthFirstOrder.java)
 *      - postorder(): itera sobre todos os vértices do digrafo em pós-ordem
 *                    (em relação a uma dfs, ordenação topologica reversa, 
 *                     DepthFirstOrder.java)
 *      - order(): itera sobre todos os vértices do digrafo em ordem  
 *                 topologica (Topological.java)
 *      - cycle(): itera sobre os vértices de um ciclo (DirectedCycle.java)
 *
 * O construtor e "destrutor" da classe consomem tempo linear..
 *
 * Cada chama das demais operações consome tempo constante.
 *
 * O espaço gasto por esta ADT é proporcional ao número de vértices V do digrafo.
 * 
 * Para documentação adicional, ver 
 * https://algs4.cs.princeton.edu/42digraph, Seção 4.2 de
 * Algorithms, 4th Edition por Robert Sedgewick e Kevin Wayne.
 *
 */

/* interface para o uso da funcao deste módulo */
#include "topological.h"

#include "digraph.h" /* Digraph, vDigraph(), eDigraph(), adj(), ... */
#include "bag.h"     /* add() e itens() */
#include "util.h"    /* emalloc(), ecalloc(), ERRO(), AVISO() */

#include <stdlib.h>  /* free() */

#undef DEBUG
#ifdef DEBUG
#include <stdio.h>   /* printf(): para debugging */
#endif

// TODO : NÃO ARMAZENAR O GRAFO, só fazer um "adj" pra iterar a adjacencia

/*----------------------------------------------------------*/
/* 
 * Estrutura básica de um Topological
 * 
 */

struct stack {
    int top;
    int size;
    int* s;
};

typedef struct stack* Stack;

struct queue {
    int start;
    int end;
    int size;
    int* s;
};

typedef struct queue* Queue;


struct topological  {
    vertex* pre; // pre[v] = v na preordem
    vertex* post; // post[v] = v na posOrdem
    Queue preOrder; // preciso disso? É pra retornar um iteravel da preorder
    Queue postOrder;
    Bool* marked;

    Stack order; // STACK 
    int* rank; 


    vertex* edgeTo;
    Bool* onStack; // onStack[v] = o vertice v ta na pilha?
    Stack cycle; //Pilha
    int onCycle;

    int preCounter;
    int postCounter;
    int orderCounter;
    int cycleCounter;

    int postIteratorCounter;
    int preIteratorCounter;
    int v;

};

struct digraph {
    int v;
    int e; 
    int bagSize;
    int* inDegree;
    Bag* adjacencyList;
};

/*------------------------------------------------------------*/
/* 
 * Protótipos de funções administrativas: tem modificador 'static'
 * 
 */
static Stack newStack(int size);
static void freeStack(Stack s);
static int isempty(Stack s);
static int isfull(Stack s);
static int peek(Stack s);
static int pop(Stack s);
static int push(Stack s, int data);
static void dfsDFO(Topological ts, Digraph G, vertex v);
static void dfsCicle(Topological ts,Digraph G, vertex v);
static Queue createQueue(int size);
static void freeQueue(Queue Q);
static void enqueue(Queue Q, int content);
static int emptyQueue(Queue Q);
static void removeFromQueue (Queue Q);
static int firstInQueue (Queue Q);
static Stack reversePost(Topological ts);
/*-----------------------------------------------------------*/
/*
 *  newTopologica(G)
 *
 *  RECEBE um digrafo G.
 *  RETORNA uma representação topológica de G.
 * 
 */
Topological
newTopological(Digraph G)
{
    Topological myTopological = malloc(sizeof(struct topological));
    /*---------------DEPTH FIRST ORDER ----------------*/
    myTopological->pre = calloc(G->v, sizeof(vertex));
    myTopological->post = calloc(G->v, sizeof(vertex));
    myTopological->preOrder = calloc(G->v, sizeof(struct queue)); // fila
    myTopological->postOrder = calloc(G->v, sizeof(struct queue)); // fila
    myTopological->preOrder = createQueue(G->v);
    myTopological->postOrder = createQueue(G->v);
    myTopological->marked = malloc(G->v*sizeof(int)); // TODO : essa inicialização não parece estar correta
    
    for(int i = 0; i < G->v; i++){
        myTopological->marked[i] = FALSE;
    }
    //----------------------------------------------------
    
    /*-----------------TOPOLOGICAL--------------------------*/

    myTopological->order = calloc(G->v, sizeof(struct stack)); // Stack
    myTopological->order = newStack(G->v);
    // -----------------------------------------------------
   
    /*---------------Directed cycle----------------------*/
    myTopological->edgeTo = calloc(G->v, sizeof(vertex));
    myTopological->onStack = calloc(G->v, sizeof(vertex));
    myTopological->cycle = NULL;
    // myTopological->cycle = newStack(G->v);
    //-----------------------------------------------------

    myTopological->preCounter = 0;
    myTopological->postCounter = 0;
    myTopological->orderCounter = 0;
    myTopological->cycleCounter = 0;
    myTopological->v = G->v;
    // myTopological->G->e = G->e;
    myTopological->onCycle = -1; // TODO : ver como usar essa variável

    myTopological->preIteratorCounter = 0;
    myTopological->postIteratorCounter = 0;

    for(int i = 0; i < G->v; i++) {
        if(!myTopological->marked[i])
            dfsDFO(myTopological, G, i);
    }

    for(int i = 0; i < G->v; i++){
        myTopological->marked[i] = FALSE;
    }

    for(int i = 0; i < G->v; i++) {
        if(!myTopological->marked[i] && myTopological->cycle == NULL)
            dfsCicle(myTopological, G, i);
    }

    if(!hasCycle(myTopological)) {
        myTopological->order = reversePost(myTopological);
        myTopological->rank = calloc(G->v, sizeof(int));
        int i = 0;
        for(int v = 0; v < myTopological->postOrder->size; v++){
            myTopological->rank[myTopological->order->s[v]] = i++;
        }

    }



    return myTopological; // TODO : tenho que chamar a dfs aqui?
}

/*-----------------------------------------------------------*/
/*
 *  freeTopological(TS)
 *
 *  RECEBE uma representação topologica TS.
 *  DEVOLVE ao sistema toda a memória usada por TS.
 *
 */
void  
freeTopological(Topological ts) {
    free(ts->pre);
    free(ts->post);

    free(ts->preOrder->s);
    free(ts->preOrder);
    free(ts->postOrder);
    free(ts->postOrder->s);

    free(ts->edgeTo);
    free(ts->onStack); // TODO : checar se  a alocação e free de bool e'assim mesmo

    free(ts->cycle->s);
    free(ts->cycle);
    free(ts);
}    

/*------------------------------------------------------------*/
/*
 *  OPERAÇÕES: 
 *
 */

/*-----------------------------------------------------------*/
/* 
 *  HASCYCLE(TS)
 *
 *  RECEBE uma representação topológica TS de um digrafo;
 *  RETORNA TRUE seu o digrafo possui um ciclo e FALSE em caso 
 *  contrário.
 *
 */
Bool
hasCycle(Topological ts)
{
    return ts->cycle != NULL;
}

/*-----------------------------------------------------------*/
/* 
 *  ISDAG(TS)
 *
 *  RECEBE um representação topológica TS de um digrafo.
 *  RETORNA TRUE se o digrafo for um DAG e FALSE em caso 
 *  contrário.
 *
 */
Bool
isDag(Topological ts)
{
    return ts->onCycle == -1;
}

/*-----------------------------------------------------------*/
/* 
 *  PRE(TS, V)
 *
 *  RECEBE uma representação topológica TS de um digrafo e um 
 *  vértice V.
 *  RETORNA a numeração pré-ordem de V em TS.
 *
 */
int
pre(Topological ts, vertex v)
{
    return ts->pre[v];
}

/*-----------------------------------------------------------*/
/* 
 *  POST(TS, V)
 *
 *  RECEBE uma representação topológica TS de um digrafo e um 
 *  vértice V.
 *  RETORNA a numeração pós-ordem de V em TS.
 *
 */
int
post(Topological ts, vertex v)
{
    return ts->post[v];
}

/*-----------------------------------------------------------*/
/* 
 *  RANK(TS, V)
 *
 *  RECEBE uma representação topológica TS de um digrafo e um 
 *  vértice V.
 *  RETORNA a posição de V na ordenação topológica em TS;
 *  retorna -1 se o digrafo não for um DAG.
 *
 */
int
rank(Topological ts, vertex v)
{
    if(ts->cycle != NULL)
        return -1;
    return ts->rank[v];
}

/*-----------------------------------------------------------*/
/* 
 *  PREORDER(TS, INIT)
 * 
 *  RECEBE uma representação topológica TS de um digrafo e um 
 *  Bool INIT.
 *
 *  Se INIT é TRUE,  PREORDER() RETORNA o primeiro vértice na ordenação pré-ordem de TS.
 *  Se INIT é FALSE, PREORDER() RETORNA o vértice sucessor do último vértice retornado
 *                   na ordenação pré-ordem de TS; se todos os vértices já foram retornados, 
 *                   a função retorna -1.
 */
vertex
preorder(Topological ts, Bool init) // TODO : init não esta fazendo diferença.
{
    if(init == TRUE)
        ts->preIteratorCounter = 0;
    if(ts->preIteratorCounter == ts->v)
        return -1;

    else
        return ts->preOrder->s[ts->preIteratorCounter++];
}

/*-----------------------------------------------------------*/
/* 
 *  POSTORDER(TS, INIT)
 * 
 *  RECEBE uma representação topológica TS de um digrafo e um 
 *  Bool INIT.
 *
 *  Se INIT é TRUE,  POSTORDER() RETORNA o primeiro vértice na ordenação pós-ordem de TS.
 *  Se INIT é FALSE, POSTORDER() RETORNA o vértice sucessor do último vértice retornado
 *                   na ordenação pós-ordem de TS; se todos os vértices já foram retornados, 
 *                   a função retorna -1.
 */
vertex
postorder(Topological ts, Bool init)
{
    if(init == TRUE)
        ts->postIteratorCounter = 0;
    if(ts->postIteratorCounter == ts->v)
        return -1;
    return ts->postOrder->s[ts->postIteratorCounter++];
}

/*-----------------------------------------------------------*/
/* 
 *  ORDER(TS, INIT)
 * 
 *  RECEBE uma representação topológica TS de um digrafo e um Bool INIT.
 *
 *  Se INIT é TRUE,  ORDER() RETORNA o primeiro vértice na ordenação topológica 
 *                   de TS.
 *  Se INIT é FALSE, ORDER() RETORNA o vértice sucessor do último vértice retornado
 *                   na ordenação topológica de TS; se todos os vértices já foram 
 *                   retornados, a função retorna -1.
 *
 *  Se o digrafo _não_ é um DAG, ORDER() RETORNA -1.
 */
vertex
order(Topological ts, Bool init)
{
    if(init == TRUE)
        ts->orderCounter = 0;
    if (ts->cycle != NULL || ts->orderCounter == ts->v) // todo : AQUI PODE DAR RUIM
        return -1;
    return ts->order->s[ts->orderCounter++];
}

/*-----------------------------------------------------------*/
/* 
 *  CYCLE(TS, INIT)
 * 
 *  RECEBE uma representação topológica TS de um digrafo e um Bool INIT.
 *
 *  Se INIT é TRUE,  CYCLE() RETORNA um vértice em um ciclo do digrafo.
 *  Se INIT é FALSE, CYCLE() RETORNA o vértice  no ciclo que é sucessor do 
 *                   último vértice retornado; se todos os vértices no ciclo já 
 *                   foram retornados, a função retorna -1.
 *
 *  Se o digrafo é um DAG, CYCLE() RETORNA -1.
 *
 */
vertex
cycle(Topological ts, Bool init)
{
    if(init == TRUE)
        ts->cycleCounter = 0;
    if(ts->cycle == NULL || ts->cycleCounter == ts->v)
        return -1;
    else
        return ts->order->s[ts->orderCounter++];
}


/*------------------------------------------------------------*/
/* 
 * Implementaçao de funções administrativas: têm o modificador 
 * static.
 */

static Stack reversePost(Topological ts) {
    Stack reverse = newStack(ts->postOrder->size);
    for(int v = ts->postOrder->size-1; v >= 0; v--){
        push(reverse, ts->postOrder->s[v]);
    }
    return reverse;

}
static void dfsCicle(Topological ts, Digraph G, vertex v) {
    ts->onStack[v] = TRUE;
    ts->marked[v] = TRUE;
    for (int index = adj(G, v, TRUE);
        index != -1;
        index = adj(G, v, FALSE))
        {
            if(ts->cycle != NULL)
                return;

            else if(!ts->marked[index]){
                ts->edgeTo[index] = v;
                dfsCicle(ts, G, index);
            }

            else if(ts->onStack[index]){
                ts->cycle = newStack(ts->v); // TODO : checar esse tamanho
                for(int x = v; x!=index; x = ts->edgeTo[x])
                    push(ts->cycle, x);
                push(ts->cycle, index);
            }
    }

    ts->onStack[v] = FALSE;
    
}


static void dfsDFO(Topological ts, Digraph G, vertex v) {
    ts->marked[v] = TRUE;
    ts->pre[v] = ts->preCounter++;
    enqueue(ts->preOrder, v);
    for (int index = adj(G, v, TRUE);
    index != -1;
    index = adj(G, v, FALSE))
        {
            if(!ts->marked[index]){
                dfsDFO(ts, G, index);
            }
    }

    enqueue(ts->postOrder, v);
    ts->post[v] = ts->postCounter++;
    
}

static Stack newStack(int size) {
    Stack st = malloc(sizeof(struct stack));
    st->s = calloc(size, sizeof(int));
    st->top = -1;
    st->size = size;

    return st;
}

static void freeStack(Stack s) {
    free(s->s);
    free(s);
}



static int isempty(Stack s) {

   if(s->top == -1)
      return 1;
   else
      return 0;
}
   
static int isfull(Stack s) {

   if(s->top == s->size)
      return 1;
   else
      return 0;
}

static int peek(Stack s) {
   return s->s[s->top];
}

static int pop(Stack s) {
   int data;
	
   if(!isempty(s)) {
      data = s->s[s->top];
      s->top--;   
      return data;
   } else {
      printf("Could not retrieve data, Stack is empty.\n");
      return -1;
   }

   return 0;
}

static int push(Stack s, int data) {

   if(!isfull(s)) {
      s->top++;   
      s->s[s->top] = data;
   } else {
      printf("Could not insert data, Stack is full.\n");
      return -1;
   }

   return 0;
}

static Queue createQueue(int size){
    Queue Q = malloc(sizeof(struct queue));
    Q->size = size;
    Q->start = Q -> end = 0;
    Q->s = calloc(size ,sizeof (int) );
    return Q;
}

static void freeQueue(Queue Q){
    free(Q->s);
    free(Q);
}

static void enqueue(Queue Q, int content) {
    Q->s[Q->end]=content;
    Q->end=(Q->end+1)%Q->size;
}

static int emptyQueue(Queue Q) {
    return (Q->start == Q->end);
}

static void removeFromQueue (Queue Q) {
    if (!emptyQueue(Q))
        Q->start=(Q->start+1)%Q->size;
}

static int firstInQueue (Queue Q) {
    return (Q->s[Q->start]);
}