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

        Método perm2 obtido de : 
        https://www.ime.usp.br/~coelho/mac0323-2019/eps/ep02/Permutations.java

    Descrição de ajuda ou indicação de fonte:



    Se for o caso, descreva a seguir 'bugs' e limitações do seu programa:

****************************************************************/

/******************************************************************************
 *  Compilation:  javac-algs4 STPerms.java
 *  Execution:    java STPerms n s t opcao
 *  
 *  Enumera todas as (s,t)-permutações das n primeiras letras do alfabeto.
 *  As permutações devem ser exibidas em ordem lexicográfica.
 *  Sobre o papel da opcao, leia o enunciado do EP.
 *
 *  % java STPerms 4 2 2 0
 *  badc
 *  bdac
 *  cadb
 *  cdab
 *  % java STPerms 4 2 2 1
 *  4
 *  % java STPerms 4 2 2 2
 *  badc
 *  bdac
 *  cadb
 *  cdab
 *  4
 *   
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class STPerms {
    
    // print n! permutation of the characters of the string s (in order)
    public  static void perm1(String s) { perm1("", s); }
    private static void perm1(String prefix, String s) {
        int n = s.length();
        if (n == 0) StdOut.println(prefix);
        else {
            for (int i = 0; i < n; i++)
               perm1(prefix + s.charAt(i), s.substring(0, i) + s.substring(i+1, n));
        }

    }

    // print n! permutation of the elements of array a (not in order)
    public static void perm2(String s) {
        int n = s.length();
        char[] a = new char[n];
        for (int i = 0; i < n; i++)
            a[i] = s.charAt(i);
        perm2(a, n);
    }

    private static void perm2(char[] a, int n) {
        if (n == 1) {
            StdOut.println(new String(a));
            return;
        }
        for (int i = 0; i < n; i++) {
            swap(a, i, n-1);
            perm2(a, n-1);
            swap(a, i, n-1);
        }
    }  

    // swap the characters at indices i and j
    private static void swap(char[] a, int i, int j) {
        char c = a[i];
        a[i] = a[j];
        a[j] = c;
    }
    

    private static  void initArray(int n, char[] alphabet) {
        for (int i = 0; i < n; i++) {
            alphabet[i] = (char)(97+i);
        }
    }

    private static void printArray(int n, char[] alphabet) {
        for(char letter : alphabet) {
            StdOut.print(letter);
        }
        StdOut.print('\n');
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int s = Integer.parseInt(args[1]);
        int t = Integer.parseInt(args[2]);
        int opt = Integer.parseInt(args[3]);

        // char teste = (char)(n+97);
        char alphabet[] = new char[n];
        initArray(n, alphabet);
        // printArray(n, alphabet);
        String alph = new String(alphabet);
        perm1(alph);
        // StdOut.println(teste);
        Stopwatch timer = new Stopwatch();

        StdOut.println("Elapsed time = " + timer.elapsedTime());
    }

}
