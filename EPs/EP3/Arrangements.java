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
    private char[] a; // talvez mover isso para dentro do construtor...
    public Arrangements(String s) {
        a = s.toCharArray()
             .sort();
        this.s = a;
    }

    public iterator<String> Iterator() {
        return new ArrIterrator();
    }

    private class ArrIterrator implements Iterator<String> {
        
        public ArrIterrator() {

        }

        public boolean hasNext() {
            //desperdiça uma iteração para o caso de uma string de 1 caracter
            boolean crescente = false;
            
            for (int i = 0, char ant = this.s[0] ; i < this.s.length && !crescente; i++) {
                if (this.s[i] > ant)
                    crescente = true;
                ant = this[i];
            }

            return crescente;
        }

        public String next() {
            if (!hasNext())
                throw new NoSuchElementException();
            

            
        }

        public void remove() {
            throw new UnsupportedOperationException();
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
