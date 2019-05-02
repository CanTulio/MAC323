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
    
    char x; /* TODO : pra que esse x?*/
    int size;
    int (*compar)();
    Node* head;
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

Node* rotateLeft(Node* head);
Node* rotateRigth(Node* head);
void freeBST(Node* head);
Node* putKey(RedBlackST st, Node* head, const void* key, size_t sizekey, const void* val, size_t sizeval);
Node* newNode(const void* key, size_t sizekey, const void* val, size_t sizeval, Bool color, int count);
int sizeNode(Node* n);

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
    st = emalloc(sizeof(RedBlackST));
    
    st->compar = compar;
    st->size = 0; /* contagem de subtrees*/
    st->head = NULL;
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
    free(st->head);
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
        // printf("A chave é nula!\n");
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
    int count = 0;
    void* valueCpy;
    Node* p = st->head;
    while (p != NULL) {

        // printf("Antes do st->compar \n")
        /*printf("Valor de p vale %d\n", p->value);*/
        printf("key, p-key : %s\n", (char*)key);
        cmp = st->compar(key, p->key);

        // printf("Depois do st->compar \n");

        if (cmp < 0) 
            p = p->left;

        else if (cmp > 0) {
            printf("p->right : %p\n", p->right);
             p = p->right;
        }

        else {
            valueCpy = emalloc(p->size_val);
            memcpy(valueCpy, p->value, p->size_val );
            return valueCpy;
        }
        count++;

        printf("count vale %d\n", count);
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
contains(RedBlackST st, const void *key)
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
void
delete(RedBlackST st, const void *key)
{
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
int
size(RedBlackST st)
{
    if (st->head == NULL)
        return 0;
    return sizeNode(st->head);
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
Bool
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
min(RedBlackST st)
{
    return NULL;
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
max(RedBlackST st)
{
    return NULL;
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
rank(RedBlackST st, const void *key)
{
    return 0;
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
    return NULL;
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
deleteMin(RedBlackST st)
{
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
deleteMax(RedBlackST st)
{
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
    return NULL;
}



/*------------------------------------------------------------*/
/* 
 * FunÃ§Ãµes administrativas
 */

void freeBST(Node* head) {
    if (head != NULL) {
        freeBST(head->left);
        freeBST(head->right);
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

    return temp_node; /* Verificar se isso da merda, proque não to retornando 
    copia, so referencia*/
}

Node* putKey(RedBlackST st, Node* head, const void* key, size_t sizekey, const void* val, size_t sizeval) {
    
    int cmp;
    if (head == NULL){
        printf("Criação de um novo nó de conteudo %s\n", key);
        head = newNode(key, sizekey, val, sizeval, RED, 1); /* TODO : o erro ta aqui */
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
    /* printf("Retornando a chave %s", head->key);*/
    return head;
    
}

int sizeNode(Node* n) {
    return n->size;
}

Node* rotateRigth(Node* head) {
    
    Node* x;
    
    x =  head->left;
    head->left = x->right;
    x->right = head;

    x->color = x->right->color;
    x->right->color = RED;

    x->size = head->size;
    head->size = sizeNode(head->left) + sizeNode(head->right) + 1;

    return x;
}

Node* rotateLeft(Node* h) {
        /* assert (h != null) && isRed(h.right);*/
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
        return 0;
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
isBST(RedBlackST st)
{
    return FALSE;
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
    return FALSE;
}


/* 
 *  ISRANKCONSISTENT(ST)
 *
 *  RECEBE uma RedBlackST ST e RETORNA TRUE se seus rank() e
 *  select() sÃ£o consistentes.
 */  
/* check that ranks are consistent */
static Bool
isRankConsistent(RedBlackST st)
{
    return FALSE;
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
    return FALSE;
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
    return FALSE;
}
