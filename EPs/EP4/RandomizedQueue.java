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
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;

import java.util.Random;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // construct an empty randomized queue
    Item[] queue;
    private int n;
    private int topo;
    public RandomizedQueue() {
        this.queue = (Item[]) new Object[10];
        this.n = 0;
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return n==0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (n < this.queue.length) {
            this.queue[this.n] = item; // devo usar o this?
            n++;
        }
        else {
            resize(this.queue, 2*n);
        }
    }

    // remove and return a random item
    public Item dequeue() {

    }

    // return a random item (but do not remove it)
    public Item sample() {

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

    }

    private Item[] resize(Item[] oldArray) {
        int oldSize =  oldArray.length;
        Item[] newArray = (Item[]) new Object(2*oldSize);
        for (int i = 0; i < oldSize; i++) {
            newArray[i] = oldArray[i];
        }
        return newArray;
    }
    // unit testing (required)
    public static void main(String[] args) {
        
    }

}