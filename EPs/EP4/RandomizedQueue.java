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

// TODO : checar se eu posso usar variaveis que instanciei no this de outra forma
// exemplo : this.n = 0, e posteriormente usar

// TODO : ta dando problema na hora de fazer o deque e realizar o downsize
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;

import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // construct an empty randomized queue
    private Item[] queue;
    private int n;
    private int topo;

    public RandomizedQueue() { // TODO : resolver essa gambiarra
        @SuppressWarnings("unchecked")
        Item[] queue2 = (Item[]) new Object[1];
        queue = queue2;
        n = 0;
        topo = 0;
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (n == queue.length) {
            queue = resize(queue, 2*n);
        }
        queue[n] = item;
        n++;
        topo++;
    }

    // remove and return a random item
    public Item dequeue() {
        // if (n <= queue.length/4) {
        //     queue = resize(queue, n/4);
        // }

        // Random generator = new Random();
        int randomIndex = StdRandom.uniform(n);
        swap(queue, randomIndex, n-1);
        Item randomValue = queue[randomIndex];


        queue[n-1] = null;
        n--;
        topo--;
        return randomValue;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        int randomIndex = StdRandom.uniform(n);
        return queue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void swap(Item[] arr, int index1, int index2) {
        StdOut.println("O tamanho do vetor vale " + arr.length);
        StdOut.println("index1 : " + index1 + " index2 : " + index2);
        Item temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    private Item[] resize(Item[] oldArray, int newSize) {
        int smallerSize;
        int oldSize =  oldArray.length;
        @SuppressWarnings("unchecked")
        Item[] newArray = (Item[]) new Object[newSize];

        if (newSize < oldSize)
            smallerSize = newSize;
        else
            smallerSize = oldSize;

        for (int i = 0; i < smallerSize; i++) {
            newArray[i] = oldArray[i];
        }
        StdOut.println("O resize foi feito e o novo valor vale " + newSize);
        return newArray;
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        int iteratorsRandomIndex;
        Item[] queueCopy;
        public RandomizedQueueIterator() {
            
            @SuppressWarnings("unchecked")
            Item[] queueCopy2 = (Item[]) new Object[n];
            queueCopy = queueCopy2;
            for (int i = 0; i < n; i ++) {
                queueCopy[i] = queue[i];
            }

        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            iteratorsRandomIndex = StdRandom.uniform(n);
            swap(queueCopy, iteratorsRandomIndex, topo);
            Item antigoTopo = queueCopy[topo];
            topo--;
            
            return antigoTopo;
        }

        public boolean hasNext() {
            return topo > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue <Character> randFila = new RandomizedQueue<Character>();
        StdOut.println("O randQueue está vazio? " + randFila.isEmpty());
        randFila.enqueue('A');
        randFila.enqueue('B');
        randFila.enqueue('C');
        randFila.enqueue('D');
        randFila.enqueue('E');
        StdOut.println("Temos " + randFila.size() + " termos");
        StdOut.println("Termo aleatorio : " + randFila.sample());
        while(!randFila.isEmpty()) {
            StdOut.println("Removendo o termo : " + randFila.dequeue());
            StdOut.println("Temos um total de " + randFila.size() + " termos");
        }
    }

}