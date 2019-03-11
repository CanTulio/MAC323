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

// Performance : Toda operação do deck deve ter tempo de execução constante
// Memória : O deck com n itens deve ocupar no máximo 48n+192 bytes
// iterador : Toda operação deve rodar em tempo constante


/* *************CHEAT SHEET DE MEMORIA *************************************

int, float : 4 bytes
char : 2 bytes
boolean : 1 byte
Referencia a um objeto : 8 bytes
    Tamanho de um objeto : endereço + variáveis de instancia + padding
    (o java quer fixar a memória de um objeto em múltiplos de 8bytes numa maqui
    na de 64bits)

Uma lista ligada simples ocupa : 
    * 8 bytes para a inner class da lista.
    * 8 bytes para referenciar o header da classe na qual a lista está sendo 
        implementada.
    * 8 bytes para referenciar o objeto de dados (no máximo)
        * se esse objeto tiver só um int, ocupa 4 bytes. Mas dai o objeto ocuparia
        12 bytes e ele arredonda pra 16
    * 8 bytes para referenciar a proxima lista.

    Total lista ligada simples : 40 bytes

* Assim, uma lista ligada dupla ocupa 48 bytes


*/

import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;

import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private class node<Item> {

        private Item item;
        private node<Item> next;
        private node<Item> prev;

        public node(Item content, node<Item> next, node<Item> prev) {
            this.item = content;
            this.next = next;
            this.prev = prev;
        }
    }
    private node<Item> first;
    private node<Item> last;
    private int n;

    // construct an empty deque
    public Deque() {
        n = 0;
        first = last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        node<Item> newContent = new node<Item>(item, first, null);
        first = newContent;
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        node<Item> newContent = new node<Item>(item, null, last);
        last.next = newContent;
        last = newContent; 
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        node<Item> temp = first.next;
        Item content = first.item;
        // first.next = null; talvez seja desnescessário
        first = null;
        n--;
        return content;
    }

    // remove and return the item from the back
    public Item removeLast() { // essa mini operação me obriga a fazer uma lista duplamente ligada grrrr 
        Item content = last.item;
        last.prev.next = null;
        last = last.prev;
        return content;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DoubleListIterator(first);
    }

    private class DoubleListIterator implements Iterator<Item> {
        private node<Item> current;
        public DoubleListIterator(node<Item> start) {
            //construtor do iterador
            current = start;
        }
        public Item next() {
            current = current.next;
            return current.item;
        }

        public boolean hasNext() {
            return !(current.next == null);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
    // unit testing (required)
    public static void main(String[] args) {
        
    }

}