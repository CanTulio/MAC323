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

    Descrição de ajuda ou indicação de fonte:



    Se for o caso, descreva a seguir 'bugs' e limitações do seu programa:

****************************************************************/

/*
 * MAC0323 Estruturas de Dados e Algoritmo II
 * 
 * Tabela de simbolos implementada atraves de uma BST rubro-negra
 *
 *     https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
 * 
 * As chaves e valores desta implementaÃ§Ã£o sÃ£o mais ou menos
 * genÃ©ricos
 */

/* interface para o uso da funcao deste mÃ³dulo */

#include "redblackst.h"  

#include <stdlib.h>  /* free() */
#include <string.h>  /* memcpy() */
#include "util.h"    /* emalloc(), ecalloc() */

#undef DEBUG
#ifdef DEBUG
#include <stdio.h>   /* printf(): para debug */
#endif

/*
 * CONSTANTES 
 */
#define RED   TRUE
#define BLACK FALSE 

/*----------------------------------------------------------*/
/* 
 * Estrutura BÃ¡sica da Tabela de SÃ­mbolos: 
 * 
 * implementaÃ§Ã£o com Ã¡rvore rubro-negra
 */
typedef struct node Node;
struct redBlackST {
    
    int size;
    int (*compar)();
    Node* head;
    int keys_count;
};

/*----------------------------------------------------------*/
/* 
 * Estrutura de um nÃ³ da Ã¡rvore
 *
 */
struct node {
    void* key;
    void* value;
    Bool color;
    Node* right;
    Node* left;
    int size; /* quantidade de elementos pendurados naquele node*/
    size_t size_key;
    size_t size_val;
};

/*------------------------------------------------------------*/
/* 
 *  ProtÃ³tipos de funÃ§Ãµes administrativas.
 * 
 *  Entre essa funÃ§Ãµes estÃ£o isRed(), rotateLeft(), rotateRight(),
 *  flipColors(), moveRedLeft(), moveRedRight() e balance().
 * 
 *  NÃ£o deixe de implmentar as funÃ§Ãµes chamadas pela funÃ§Ã£o 
 *  check(): isBST(), isSizeConsistent(), isRankConsistent(),
 *  is23(), isBalanced().
 *
 */
void flipColors(Node* head);
/*---------------------------------------------------------------*/

Bool isRed(Node* x);
/*---------------------------------------------------------------*/

Node* rotateLeft(Node* head);
/*---------------------------------------------------------------*/

Node* rotateRight(Node* head);
/*---------------------------------------------------------------*/

void freeBST(Node* head);
/*---------------------------------------------------------------*/

Node* putKey(RedBlackST st, Node* head, const void* key, size_t sizekey, const void* val, size_t sizeval);
/*---------------------------------------------------------------*/

Node* newNode(const void* key, size_t sizekey, const void* val, size_t sizeval, Bool color, int count);
/*---------------------------------------------------------------*/

int sizeNode(Node* n);
/*---------------------------------------------------------------*/

void delete(RedBlackST st, const void* key);
/*---------------------------------------------------------------*/

Node* deleteKey(RedBlackST st, Node* h, const void* key);
/*---------------------------------------------------------------*/

Node* minNode(Node* h);
/*---------------------------------------------------------------*/

Node* deleteMinNode(Node* h);
/*---------------------------------------------------------------*/

Node* deleteMaxNode(Node* h);

Node* moveRedRight (Node* h);
/*---------------------------------------------------------------*/

Node* moveRedLeft (Node* h);
/*---------------------------------------------------------------*/

Node* balance(Node* h);
/*---------------------------------------------------------------*/

int rankNode(RedBlackST st, Node* h, const void *key);

Node* selectNode(Node* h, int k);

Node* deleteMaxNode(Node* h);

Node* maxNode(Node* h);

int nodeHeight(Node* h);

Bool nodeIsBST(RedBlackST st, Node* h, void* min, void* max);

Bool NodeisSizeConsistent(Node* h);

Bool nodeIs23(RedBlackST st, Node* h);

Bool nodeIsBalanced(Node* h, int black);
/*---------------------------------------------------------------*/
static Bool
isBST(RedBlackST st);

/*---------------------------------------------------------------*/
static Bool
isSizeConsistent(RedBlackST st);

/*---------------------------------------------------------------*/
static Bool
isRankConsistent(RedBlackST st);

/*---------------------------------------------------------------*/
static Bool
is23(RedBlackST st);

/*---------------------------------------------------------------*/
static Bool
isBalanced(RedBlackST st);

/*-----------------------------------------------------------*/
/*
 *  initST(COMPAR)
 *
 *  RECEBE uma funÃ§Ã£o COMPAR() para comparar chaves.
 *  RETORNA (referÃªncia/ponteiro para) uma tabela de sÃ­mbolos vazia.
 *
 *  Ã‰ esperado que COMPAR() tenha o seguinte comportamento:
 *
 *      COMPAR(key1, key2) retorna um inteiro < 0 se key1 <  key2
 *      COMPAR(key1, key2) retorna 0              se key1 == key2
 *      COMPAR(key1, key2) retorna um inteiro > 0 se key1 >  key2
 * 
 *  TODAS OS OPERAÃ‡Ã•ES da ST criada utilizam a COMPAR() para comparar
 *  chaves.
 * 
 */
RedBlackST
initST(int (*compar)(const void *key1, const void *key2))
{
    RedBlackST st;
    st = emalloc(sizeof(struct redBlackST));
    
    st->compar = compar;
    st->size = 0; /* contagem de subtrees*/
    st->head = NULL;
    st->keys_count = 0;
    return st;
}

/*-----------------------------------------------------------*/
/*
 *  freeST(ST)
 *
 *  RECEBE uma RedBlackST  ST e devolve ao sistema toda a memoria 
 *  utilizada por ST.
 *
 */
void freeST(RedBlackST st) {
    /*free(st->compar);*/
    freeBST(st->head);
    free(st);
}


/*------------------------------------------------------------*/
/*
 * OPERAÃ‡Ã•ES USUAIS: put(), get(), contains(), delete(),
 * size() e isEmpty().
 */

/*-----------------------------------------------------------*/
/*
 *  put(ST, KEY, NKEY, VAL, NVAL)
 * 
 *  RECEBE a tabela de sÃ­mbolos ST e um par KEY-VAL e procura a KEY na ST.
 *
 *     - se VAL Ã© NULL, a entrada da chave KEY Ã© removida da ST  
 *  
 *     - se KEY nao e' encontrada: o par KEY-VAL Ã© inserido na ST
 *
 *     - se KEY e' encontra: o valor correspondente Ã© atualizado
 *
 *  NKEY Ã© o nÃºmero de bytes de KEY e NVAL Ã© o nÃºmero de bytes de NVAL.
 *
 *  Para criar uma copia/clone de KEY Ã© usado o seu nÃºmero de bytes NKEY.
 *  Para criar uma copia/clode de VAL Ã© usado o seu nÃºmero de bytes NVAL.
 *
 */

void  
put(RedBlackST st, const void *key, size_t sizeKey, const void *val, size_t sizeVal)
{
    if (key == NULL) {
        delete(st, key);
        return;
    }

    else {
        st->head = putKey(st, st->head, key, sizeKey, val, sizeVal);
    }

    st->head->color = BLACK;
}    
/*-----------------------------------------------------------*/
/*
 *  get(ST, KEY)
 *
 *  RECEBE uma tabela de sÃ­mbolos ST e uma chave KEY.
 *
 *     - se KEY estÃ¡ em ST, RETORNA NULL;
 *
 *     - se KEY nÃ£o estÃ¡ em ST, RETORNA uma cÃ³pia/clone do valor
 *       associado a KEY.
 * 
 */
void *
get(RedBlackST st, const void *key)
{
    int cmp;
    void* valueCpy;
    Node* p = st->head;
    while (p != NULL) {

        cmp = st->compar(key, p->key);

        if (cmp < 0) 
            p = p->left;

        else if (cmp > 0) {
             p = p->right;
        }

        else {
            valueCpy = emalloc(p->size_val);
            memcpy(valueCpy, p->value, p->size_val );
            return valueCpy;
        }

    }
    return NULL;
}


/*-----------------------------------------------------------*/
/* 
 *  CONTAINS(ST, KEY)
 *
 *  RECEBE uma tabela de sÃ­mbolos ST e uma chave KEY.
 * 
 *  RETORNA TRUE se KEY estÃ¡ na ST e FALSE em caso contrÃ¡rio.
 *
 */
Bool
contains(RedBlackST st, const void *key) // check
{
    return (get(st, key) != NULL);
}

/*-----------------------------------------------------------*/
/* 
 *  DELETE(ST, KEY)
 *
 *  RECEBE uma tabela de sÃ­mbolos ST e uma chave KEY.
 * 
 *  Se KEY estÃ¡ em ST, remove a entrada correspondente a KEY.
 *  Se KEY nÃ£o estÃ¡ em ST, faz nada.
 *
 */
void delete(RedBlackST st, const void *key) { // check

    if (!contains(st, key))
        return;
    // if both children of root are black, set root to red
    if (!isRed(st->head->left) && !isRed(st->head->right))
        st->head->color = RED;
    st->head = deleteKey(st, st->head, key);
    st->size--;
    if (!isEmpty(st)) st->head->color = BLACK;
}


/*-----------------------------------------------------------*/
/* 
 *  SIZE(ST)
 *
 *  RECEBE uma tabela de sÃ­mbolos ST.
 * 
 *  RETORNA o nÃºmero de itens (= pares chave-valor) na ST.
 *
 */
int // check
size(RedBlackST st)
{
    if (st->head == NULL)
        return 0;
    return st->size;
}


/*-----------------------------------------------------------*/
/* 
 *  ISEMPTY(ST, KEY)
 *
 *  RECEBE uma tabela de sÃ­mbolos ST.
 * 
 *  RETORNA TRUE se ST estÃ¡ vazia e FALSE em caso contrÃ¡rio.
 *
 */
Bool // check
isEmpty(RedBlackST st)
{
    return st->size == 0;
}

/*------------------------------------------------------------*/
/*
 * OPERAÃ‡Ã•ES PARA TABELAS DE SÃMBOLOS ORDENADAS: 
 * min(), max(), rank(), select(), deleteMin() e deleteMax().
 */

/*-----------------------------------------------------------*/
/*
 *  MIN(ST)
 * 
 *  RECEBE uma tabela de sÃ­mbolos ST e RETORNA uma cÃ³pia/clone
 *  da menor chave na tabela.
 *
 *  Se ST estÃ¡ vazia RETORNA NULL.
 *
 */
void *
min(RedBlackST st) // check
{
    if (isEmpty(st))
        return NULL;
    else {
        void* cpy;
        Node* min = minNode(st->head); // TODO acho que o erro ta aqui, o min não ta retornando uma copia
        cpy = emalloc(min->size_key);
        memcpy(cpy, min->key, min->size_key);
        return cpy;
    }
}


/*-----------------------------------------------------------*/
/*
 *  MAX(ST)
 * 
 *  RECEBE uma tabela de sÃ­mbolos ST e RETORNA uma cÃ³pia/clone
 *  da maior chave na tabela.
 *
 *  Se ST estÃ¡ vazia RETORNA NULL.
 *
 */
void *
max(RedBlackST st) {
    Node* max =  maxNode(st->head);
    void* cpy = emalloc(max->size_key);
    memcpy(cpy, max->key, max->size_key);
    return cpy;
}

Node* maxNode(Node* h) {
    if (h->right == NULL)
        return h;
    return maxNode(h->right);
}


/*-----------------------------------------------------------*/
/*
 *  RANK(ST, KEY)
 * 
 *  RECEBE uma tabela de sÃ­mbolos ST e uma chave KEY.
 *  RETORNA o nÃºmero de chaves em ST menores que KEY.
 *
 *  Se ST estÃ¡ vazia RETORNA NULL.
 *
 */
int
rank(RedBlackST st, const void *key) {
    return rankNode(st, st->head, key);
} 

int rankNode(RedBlackST st, Node* h, const void *key) {
    if (h == NULL) return 0; 
    int cmp = st->compar(key, h->key); 
    if      (cmp < 0) return rankNode(st, h->left, key); 
    else if (cmp > 0) return 1 + sizeNode(h->left) + rankNode(st, h->right, key); 
    else              return sizeNode(h->left); 
}


/*-----------------------------------------------------------*/
/*
 *  SELECT(ST, K)
 * 
 *  RECEBE uma tabela de sÃ­mbolos ST e um inteiro K >= 0.
 *  RETORNA a (K+1)-Ã©sima menor chave da tabela ST.
 *
 *  Se ST nÃ£o tem K+1 elementos RETORNA NULL.
 *
 */
void *
select(RedBlackST st, int k)
{
    if (k+1 > st->size)
        return NULL;
    Node* retKey = selectNode(st->head, k);
    void* cpy = emalloc(retKey->size_key);
    memcpy(cpy, retKey->key, retKey->size_key);
    return cpy;
    
}

Node* selectNode(Node* h, int k) {
    int t = sizeNode(h->left);
    if (t > k) return selectNode(h->left, k);
    else if (t < k) return selectNode(h->right, k-t-1);
    else return h;
}


/*-----------------------------------------------------------*/
/*
 *  deleteMIN(ST)
 * 
 *  RECEBE uma tabela de sÃ­mbolos ST e remove a entrada correspondente
 *  Ã  menor chave.
 *
 *  Se ST estÃ¡ vazia, faz nada.
 *
 */
void
deleteMin(RedBlackST st) { // check
    if (!isRed(st->head->left) && !isRed(st->head->right))
        st->head->color = RED;
    st->head = deleteMinNode(st->head);
    st->size--;
    if (!isEmpty(st)) st->head->color = BLACK;
}


/*-----------------------------------------------------------*/
/*
 *  deleteMAX(ST)
 * 
 *  RECEBE uma tabela de sÃ­mbolos ST e remove a entrada correspondente
 *  Ã  maior chave.
 *
 *  Se ST estÃ¡ vazia, faz nada.
 *
 */
void
deleteMax(RedBlackST st) {
    if (!isRed(st->head->left) && !isRed(st->head->right))
        st->head->color = RED;

    st->head = deleteMaxNode(st->head);
    st->size--;
    if (!isEmpty(st)) st->head->color = BLACK;
}

Node* deleteMaxNode(Node* h) {
    if (isRed(h->left))
            h = rotateRight(h);

    if (h->right == NULL)
        return NULL;

    if (!isRed(h->right) && !isRed(h->right->left))
        h = moveRedRight(h);

    h->right = deleteMaxNode(h->right);

    return balance(h);
}


/*-----------------------------------------------------------*/
/* 
 *  KEYS(ST, INIT)
 * 
 *  RECEBE uma tabela de sÃ­mbolos ST e um Bool INIT.
 *
 *  Se INIT Ã© TRUE, KEYS() RETORNA uma cÃ³pia/clone da menor chave na ST.
 *  Se INIT Ã© FALSE, KEYS() RETORNA a chave sucessora da Ãºltima chave retornada.
 *  Se ST estÃ¡ vazia ou nÃ£o hÃ¡ sucessora da Ãºltima chave retornada, KEYS() retorna NULL.
 *
 *  Se entre duas chamadas de KEYS() a ST Ã© alterada, o comportamento Ã© 
 *  indefinido. 
 *  
 */
void * 
keys(RedBlackST st, Bool init)
{
    void* retVal;
    if (select(st, st->keys_count) == NULL)
        return NULL;
    if (init == TRUE) {
        retVal = min(st);
    }
    else {
        retVal = select(st, st->keys_count);
    }
    st->keys_count++;
    return retVal;
}



/*------------------------------------------------------------*/
/* 
 * FunÃ§Ãµes administrativas
 */

Node* balance(Node* h) { // check
    if (isRed(h->right))
        h = rotateLeft(h);
    if (isRed(h->left) && isRed(h->left->left))
        h = rotateRight(h);
    if (isRed(h->left) && isRed(h->right))
        flipColors(h);

    h->size = sizeNode(h->left) + sizeNode(h->right) + 1;
    return h;
}

Node* moveRedLeft(Node* h) { // check
    flipColors(h);

    if (isRed(h->right->left)) { 
        h->right = rotateRight(h->right);
        h = rotateLeft(h);
        flipColors(h);
    }

    return h;
}

Node* moveRedRight(Node* h) {
    flipColors(h);

    if (isRed(h->left->left)) { 
        h = rotateRight(h);
        flipColors(h);
    }

    return h;
}

Node* deleteMinNode(Node* h) { 
    if (h->left == NULL)
        return NULL;

    if (!isRed(h->left) && !isRed(h->left->left))
        h = moveRedLeft(h);

    h->left = deleteMinNode(h->left);

    return balance(h);
}

Node* minNode(Node* h) {
    if (h->left == NULL) {
        return h;
    }
    else
        return minNode(h->left);
}

Node* deleteKey(RedBlackST st, Node* h, const void* key) {
    
    if (st->compar(key, h->key) < 0)  {
            if (!isRed(h->left) && !isRed(h->left->left)){
                h = moveRedLeft(h);
            }
            h->left = deleteKey(st, h->left, key);
    }
    else {
        if (isRed(h->left)) {
            h = rotateRight(h);
        }

        if (st->compar(key, h->key) == 0 && (h->right == NULL)){ 
            return NULL;
        }
        
        if (!isRed(h->right) && !isRed(h->right->left)){
            h = moveRedRight(h);
        }

        if (st->compar(key, h->key) == 0) {
            Node* x = minNode(h->right);
            free(h->key);
            free(h->value);
            h->key = emalloc(h->size_key);
            h->value = emalloc(h->size_val);
            memcpy(h->key, x->key, h->size_key);
            memcpy(h->value, x->value, h->size_val);
            h->right = deleteMinNode(h->right);
        }
        else {
            h->right = deleteKey(st, h->right, key);
        }
    }
    return balance(h);
}

void flipColors(Node* head) {

    head->color = !head->color;
    head->left->color = !head->left->color;
    head->right->color = !head->right->color;
}

Bool isRed(Node* x) {
    if (x == NULL)
        return FALSE;
    else
        return x->color == RED;
}
void freeBST(Node* head) {
    if (head != NULL) {
        freeBST(head->left);
        freeBST(head->right);
        free(head->key);
        free(head->value);
        free(head);
    }
}

Node* newNode(const void* key, size_t sizekey,const void* val, size_t sizeval, Bool color, int count) {
    Node* temp_node;
    temp_node = emalloc(sizeof(Node));

    temp_node->key = emalloc(sizekey);
    temp_node->value = emalloc(sizeval);
    temp_node->size_key = sizekey;
    temp_node->size_val = sizeval;

    temp_node->left = NULL;
    temp_node->right = NULL;

    memcpy(temp_node->key, key, sizekey);
    memcpy(temp_node->value, val, sizeval);

    temp_node->color = color;
    temp_node->size = count;

    return temp_node; 
}

Node* putKey(RedBlackST st, Node* head, const void* key, size_t sizekey, const void* val, size_t sizeval) {
    int cmp;
    if (head == NULL){
        head = newNode(key, sizekey, val, sizeval, RED, 1);
        st->size++;
        return head;
    }

    cmp = st->compar(key, head->key);
    if (cmp < 0){
        head->left = putKey(st, head->left, key, sizekey, val, sizeval);
    }
    else if (cmp > 0){
        head->right = putKey(st, head->right,key, sizekey, val, sizeval);

    }
    else{
        free(head->value);
        head->value = emalloc(sizeval);
        head->size_val = sizeval;
        memcpy(head->value, val, sizeval);
    }
    if (isRed(head->right) && !isRed(head->left))      
        head = rotateLeft(head);

    if (isRed(head->left)  &&  isRed(head->left->left)) 
        head = rotateRight(head);

    if (isRed(head->left)  &&  isRed(head->right))
        flipColors(head);

    head->size = sizeNode(head->left) + sizeNode(head->right) + 1;
    return head;
    
}

int sizeNode(Node* n) {
    if (n != NULL)
        return n->size;
    else
        return 0;
}

Node* rotateRight(Node* head) {
    
    Node* x = head->left;
    head->left = x->right;
    x->right = head;

    x->color = x->right->color;
    x->right->color = RED;

    x->size = head->size;
    head->size = sizeNode(head->left) + sizeNode(head->right) + 1;

    return x;
}

Node* rotateLeft(Node* h) {
        /* assert (h != NULL) && isRed(h->right);*/
        Node* x = h->right;
        h->right = x->left;
        x->left = h;
        x->color = x->left->color;
        x->left->color = RED;
        x->size = h->size;
        h->size = sizeNode(h->left) + sizeNode(h->right) + 1;
        return x;
}


/***************************************************************************
 *  Utility functions.
 ***************************************************************************/

/*
 * HEIGHT(ST)
 * 
 * RECEBE uma RedBlackST e RETORNA a sua altura. 
 * Uma BST com apenas um nÃ³ tem altura zero.
 * 
 */
int
height(RedBlackST st)
{
    return nodeHeight(st->head);
}

int nodeHeight(Node* h) {
    if (h == NULL) return -1;
    return 1 + MAX(nodeHeight(h->left), nodeHeight(h->right));
}


/***************************************************************************
 *  Check integrity of red-black tree data structure.
 ***************************************************************************/

/*
 * CHECK(ST)
 *
 * RECEBE uma RedBlackST ST e RETORNA TRUE se nÃ£o encontrar algum
 * problema de ordem ou estrutural. Em caso contrÃ¡rio RETORNA 
 * FALSE.
 * 
 */
Bool
check(RedBlackST st)
{
    if (!isBST(st))            ERROR("check(): not in symmetric order");
    if (!isSizeConsistent(st)) ERROR("check(): subtree counts not consistent");
    if (!isRankConsistent(st)) ERROR("check(): ranks not consistent");
    if (!is23(st))             ERROR("check(): not a 2-3 tree");
    if (!isBalanced(st))       ERROR("check(): not balanced");
    return isBST(st) && isSizeConsistent(st) && isRankConsistent(st) && is23(st) && isBalanced(st);
}


/* 
 * ISBST(ST)
 * 
 * RECEBE uma RedBlackST ST.
 * RETORNA TRUE se a Ã¡rvore Ã© uma BST.
 * 
 */
static Bool
isBST(RedBlackST st) { 
    
    return nodeIsBST(st, st->head, NULL, NULL);
}

Bool nodeIsBST(RedBlackST st, Node* h, void* min, void* max) {
    if (h == NULL) return TRUE;
    if (min != NULL && st->compar(h->key, min) <= 0 ) return FALSE;
    if (max != NULL && st->compar(h->key, max) >= 0) return FALSE;
    return nodeIsBST(st, h->left, min, h->key) && nodeIsBST(st, h->right, h->key, max);
}


/* 
 *  ISSIZECONSISTENT(ST) 
 *
 *  RECEBE uma RedBlackST ST e RETORNA TRUE se para cada nÃ³ h
 *  vale que size(h) = 1 + size(h->left) + size(h->right) e 
 *  FALSE em caso contrÃ¡rio.
 */
static Bool
isSizeConsistent(RedBlackST st)
{
    return NodeisSizeConsistent(st->head);
}

Bool NodeisSizeConsistent(Node* h) {
    if (h == NULL) return TRUE;
    if (h->size != sizeNode(h->left) + sizeNode(h->right) + 1) return FALSE;
    return NodeisSizeConsistent(h->left) && NodeisSizeConsistent(h->right);
}


/* 
 *  ISRANKCONSISTENT(ST)
 *
 *  RECEBE uma RedBlackST ST e RETORNA TRUE se seus rank() e
 *  select() sÃ£o consistentes.
 */  
/* check that ranks are consistent */
static Bool
isRankConsistent(RedBlackST st) {
    int i;
    st->keys_count = 0;
    Bool first = TRUE;
    for (i = 0; i < st->size; i++) {
        if (i != rank(st, select(st, i))) return FALSE;
    }
    void* keys_ret = keys(st, first);
    first = FALSE;
    while (keys_ret != NULL) {
        if (st->compar(keys_ret, select(st, rank(st, keys_ret))) != 0) return FALSE;
        keys_ret = keys(st, first);
    }
    st->keys_count = 0;
    return TRUE;
}
/* 
 *  IS23(ST)
 *
 *  RECEBE uma RedBlackST ST e RETORNA FALSE se hÃ¡ algum link RED
 *  para a direta ou se ha dois links para esquerda seguidos RED 
 *  Em caso contrÃ¡rio RETORNA TRUE (= a ST representa uma Ã¡rvore 2-3). 
 */
static Bool
is23(RedBlackST st)
{
    return nodeIs23(st, st->head);
}

Bool nodeIs23(RedBlackST st, Node* h) {
    if (h == NULL) return TRUE;
    if (isRed(h->right)) return FALSE;
    if (h != st->head && isRed(h) && isRed(h->left))
        return FALSE;
    return nodeIs23(st, h->left) && nodeIs23(st, h->right);
}


/* 
 *  ISBALANCED(ST) 
 * 
 *  RECEBE uma RedBlackST ST e RETORNA TRUE se st satisfaz
 *  balanceamento negro perfeiro.
 */ 
static Bool
isBalanced(RedBlackST st)
{
    int black = 0;     // number of black links on path from root to min
    Node* x = st->head;
    while (x != NULL) {
        if (!isRed(x)) black++;
        x = x->left;
    }
    return nodeIsBalanced(st->head, black);
}

Bool nodeIsBalanced(Node* h, int black) {
    if (h == NULL) return black == 0;
    if (!isRed(h)) black--;
    return nodeIsBalanced(h->left, black) && nodeIsBalanced(h->right, black);
}