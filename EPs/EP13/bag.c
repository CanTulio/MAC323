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
 * ADT Bag implementada com lista ligada de itens. 
 *  
 *    https://algs4.cs.princeton.edu/13stacks/
 *    https://www.ime.usp.br/~pf/estruturas-de-dados/aulas/bag.html
 * 
 * Os itens desta implementação são genéricos: "shallow copy" dos itens dados.
 *
 *----------------------------------------------------------------------------
 * Object copying
 * https://en.wikipedia.org/wiki/Object_copying
 * 
 * Understanding Deep and Shallow Copy 
 * https://we-are.bookmyshow.com/understanding-deep-and-shallow-copy-in-javascript-13438bad941c
 *
 * Shallow copy is a bit-wise copy of an object. A new object is created that has an 
 * exact copy of the values in the original object. If any of the fields of the object 
 * are references to other objects, just the reference addresses are copied i.e., 
 * only the memory address is copied.
 
 * A deep copy copies all fields, and makes copies of dynamically allocated memory 
 * pointed to by the fields. A deep copy occurs when an object is copied along with the 
 * objects to which it refers.
 *
 */

/* interface para o uso da funcao deste módulo */
#include "bag.h"  

#include <stdlib.h>  /* free() */
#include <string.h>  /* memcpy() */
#include "util.h"    /* emalloc() */

#undef DEBUG
#ifdef DEBUG
#include <stdio.h>   /* printf(): para debuging */
#endif

/*----------------------------------------------------------*/
/* 
 * Estrutura Básica da Bag
 * 
 * Implementação com listas ligada dos itens.
 */

typedef struct node {
    int item;
    struct node* next;
} *Node;


struct bag {
    int size;
    Node current;
    Node head;
};


/*------------------------------------------------------------*/
/* 
 * Protótipos de funções administrativas: tem modificador 'static'

 
 * 
 */

static Node new_node(int item);

/*-----------------------------------------------------------*/
/*
 *  newBag()
 *
 *  RETORNA (referência/ponteiro para) uma bag vazia.
 * 
 */

Bag newBag() {
    Bag b = emalloc(sizeof(struct bag));
    b->size = 0;
    b->head = NULL;
    b->current = b->head;
    return b;
}

/*-----------------------------------------------------------*/
/*
 *  freeBag(BAG)
 *
 *  RECEBE uma Bag BAG e devolve ao sistema toda a memoria 
 *  utilizada.
 *
 */
void freeBag(Bag bag) {
    
    Node p, aux;

    if (bag->head == NULL)
        return;

    p = bag->head;
    while(p != NULL) {
        aux = p;
        p = p->next;
        aux->next = NULL;
        free(aux);
    }
    free(bag);
}    

/*------------------------------------------------------------*/
/*
 * OPERAÇÕES USUAIS: add(), size(), isEmpty() e itens().
 */

/*-----------------------------------------------------------*/
/*
 *  add(BAG, ITEM, NITEM)
 * 
 *  RECEBE uma bag BAG e um ITEM e insere o ITEM na BAG.
 *  NITEM é o número de bytes de ITEM.
 *
 *  Para criar uma copia/clone de ITEM é usado o seu número de bytes NITEM.
 *
 */
void add(Bag bag, int item) {
    Node new = new_node(item);
    if (bag->head == NULL){ 
        bag->head = new;
    }
    else {
        new->next = bag->head;
        bag->head = new;
    }

    bag->current = bag->head;
    bag->size++;
}    

/*-----------------------------------------------------------*/
/* 
 *  SIZE(BAG)
 *
 *  RECEBE uma bag BAG
 * 
 *  RETORNA o número de itens em BAG.
 */
int
size(Bag bag) {
    return bag->size;
}

/*-----------------------------------------------------------*/
/* 
 *  ISEMPTY(BAG)
 *
 *  RECEBE uma bag BAG.
 * 
 *  RETORNA TRUE se BAG está vazia e FALSE em caso contrário.
 *
 */
Bool isEmpty(Bag bag) {
    return bag->size == 0;
}

/*-----------------------------------------------------------*/
/* 
 *  ITENS(BAG, INIT)
 * 
 *  RECEBE uma bag BAG e um Bool INIT.
 *
 *  Se INIT é TRUE,  ITENS() RETORNA uma cópia/clone do primeiro item na lista de itens na BAG.
 *  Se INIT é FALSE, ITENS() RETORNA uma cópia/clone do item sucessor do último item retornado.
 *  Se BAG está vazia ou não há sucessor do último item retornada, ITENS() RETORNA NULL.
 *
 *  Se entre duas chamadas de ITENS() a BAG é alterada, o comportamento é  indefinido. 
 *  
 */
int* itens(Bag bag, Bool init) {
    
    int* return_val;
    if (isEmpty(bag))
        return NULL;

    if (init == TRUE) {
        bag->current = bag->head;
    }

    if (bag->current == NULL) { // Separei esta condição da condição de ser vazia
    // para que possa resetar o valor de current a cada init (evita que o iterador
    // nunca seja reiniciado)
        return NULL;
    }

    return_val = emalloc(sizeof(int));
    memcpy(return_val, bag->current->item, sizeof(int));

    if(bag->current != NULL) {
        bag->current = bag->current->next;
    }

    return return_val;
}

/*------------------------------------------------------------*/
/* 
 * Implementaçao de funções administrativas
 */

static Node new_node(int item) {
    
    Node new = emalloc(sizeof(struct node));
    new->item = item;
    new->next = NULL;

    return new;
}

