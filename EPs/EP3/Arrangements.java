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

        Método Iterator.next() inspirado em :  
         *https://www.nayuki.io/page/next-lexicographical-permutation-algorithm
    Descrição de ajuda ou indicação de fonte:



    Se for o caso, descreva a seguir 'bugs' e limitações do seu programa:

****************************************************************/

// excessões pedidas
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;

// pode ser útil
import java.util.Arrays; // Arrays.sort(), Arrays.copyOf(), ...

import java.util.Iterator; // passo 0 para criarmos um iterador

import edu.princeton.cs.algs4.StdOut;

public class Arrangements implements Iterable<String> {
    // Construtor
    private final char[] first;

    public Arrangements(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        first = a;
    }

    public Iterator<String> iterator() {
        return new ArrIterrator();
    }

    private class ArrIterrator implements Iterator<String> {

        private char[] current;
        private boolean isFirst;

        public ArrIterrator() {
            isFirst = true;
            current = first.clone();
        }

        public String next() {
            if (!hasNext())
                throw new NoSuchElementException();
            if (isFirst) {
                isFirst = false;
                return String.valueOf(current);
            }

            //Encontra a maior sequencia não crescente (sufixo)
            int i,j;
            for (i = current.length-1; i > 0 && current[i-1] >= current[i]; i--);
            // Nesse ponto, i é o elemento mais a esquerda do sufixo
            int pivot = i - 1;
            // e i-1 é o pivô. Precisamos encontrar o elemento mais a direita do
            // sufixo que excede current[pivot]
            for (j = current.length-1; current[j] <= current[pivot]; j--);
            // certamente haverá j tal que current[j] <= current[pivot] porque 
            // o vetor no intervalo [pivot+1, ..., n-1] é maior que pivot
            // devido ao laço anterior.
            swap(current, pivot, j);
            reverse(current, i);
            String nextString  = String.valueOf(current);
            return nextString;
        }


        public boolean hasNext() {
            //desperdiça uma iteração para o caso de uma string de 1 caracter
            boolean crescente = false;
            char ant = current[0];
            for (int i = 0; i < current.length && !crescente; i++) {
                if (current[i] > ant)
                    crescente = true;
                ant = current[i];
            }
            // if (!crescente) {
            //     current = first.clone();
            //     StdOut.println("alooo quebrada");
            // }
            return (crescente || isFirst); // caso seja a primeira permutação, imprima.
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void reverse(char[] v, int leftMost) {
            // reverte o array a partir do elemento mais a esquerda do vetor.
            char temp;

            for (int j = v.length - 1; leftMost < j; j--) {
                temp = v[leftMost];
                v[leftMost] = v[j];
                v[j] = temp;
                leftMost++;
                j--;
            }
        }

        private void swap(char[] v, int i, int j) {
            // Troca v[i] com v[j].
            char temp = v[i];
            v[i] = v[j];
            v[j] = temp;
        }
    }

    // Unit test
    public static void main(String[] args) {
        String s = args[0];
        Arrangements arr = new Arrangements(s);
        
        StdOut.println("Teste 1: imprime no máximo os 10 primeiros arranjos");
        Iterator<String> it = arr.iterator();
        for (int i = 0; it.hasNext() && i < 10; i++) {
            StdOut.println(i + " : " + it.next());
        }
        
        StdOut.println("Teste 2: imprime todos os arranjos");
        int i = 0;
        for (String arranjo: arr) {
            StdOut.println(i + " : " + arranjo);
            i++;
        }
    }
}
