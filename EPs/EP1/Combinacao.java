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

/******************************************************************************
 *  Compilation:  javac-algs4 Combinacao.java
 *  Execution:    java Combinacao n k [opcao]
 *  
 *  Enumera todas as combinações dos números em {1,2,...,n} k a k.
 *  Se opcao = 0 (defaul), gera e exibe todas as permutações em ordem 
 *  lexicográfica
 *  Se opcao = 1 apenas, __gera todas__ as combinações, mas __não__ as 
 *  exibe; apenas exibe o total de combinações.
 *
 * % java Combinacao 5 3 1
 * 10
 * elapsed time = 0.002
 * % java Combinacao 5 3 
 * 1 2 3 
 * 1 2 4 
 * 1 2 5 
 * 1 3 4 
 * 1 3 5 
 * 1 4 5 
 * 2 3 4 
 * 2 3 5 
 * 2 4 5 
 * 3 4 5 
 * 10
 * elapsed time = 0.002
 * % java Combinacao 100 3 1
 * 161700
 * elapsed time = 0.004
 * % java Combinacao 1000 3 1
 * 166167000
 * elapsed time = 0.726
 *   
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;


public class Combinacao {
    private static int count = 0; // contador de combinações
    private static int opcao = 0; 
        // 1 imprimir apenas o número de combinações (default)
        // 0 imprimir as combinações e o número de combinações

    public static void combinacao(int n, int k) {
        
        if (k < 0 || n < 0) {
            throw new IllegalArgumentException("n e k devem ser não nulos.\n");
        }

        int seq[] = new int[k];
        boolean finished = false;
            // Variavel que determina o fim dos cálculos de combinacaoo(n,k).

        int pivot = k-1;
            // Variável que guarda a casa do vetor seq[] sendo analisada.
        int max = n;
            // Guarda o valor máximo que cada casa do vetor seq[] pode ter. 
        int newPivot;
            // Armazena um valor de pivot para modifica-lo sem perder a 
            // referencia ao valor original.
        int aux;
            // Armazena o valor de seq[pivot] sem perder o valor original 
            // do array.

        if (n == 0 || k == 0) {
            finished = true;
        }

        inicializaVetor(seq, k);

        while (!finished) {

            imprimeCombinacao(seq, k, opcao);   
            if(seq[pivot] < max) {
                seq[pivot]++;
            }

            else {
                while(pivot > 0 && seq[pivot] >= max) {
                    pivot--;
                    max = novoMaximo(n, k, pivot);
                }

                if(seq[0] == max) {
                    finished = true;
                }

                else {
                    aux = seq[pivot];
                    newPivot = pivot;

                    while(newPivot < k) {
                        seq[newPivot] = ++aux;
                        newPivot++;
                    }

                    pivot = k-1;
                }
            }

            max = novoMaximo(n, k, pivot);
            count++;
        }
    }

    private static int novoMaximo(int n, int k, int pivot) {
        // Método que retorna o valor maximo que o item seq[pivot] pode
        // assumir dentro de uma combinação C(n,k).
        return( n-k+(pivot+1) );
    }

    private static void inicializaVetor(int[] seq, int tamanho) {
        // Método que inicializa o vetor seq como uma sequencia [1, tamanho].
        for(int i = 0; i < tamanho; i++) {
            seq[i] = i+1;
        }
    }

    private static void imprimeCombinacao(int[] seq, int tamanho, int opcao) {
        // Método que imprime o número de combinaçãoes de N tomado a K, e, se 
        // opção = 1, imprime todas as combinações possíveis dados n e k.
        if(opcao == 0)
            imprimeVetor(seq, tamanho);
    }

    private static void imprimeVetor(int[] seq, int tamanho) {
        // Método que itera o vetor seq[] e imprime seus itens.
        for(int item : seq) {
            StdOut.print(item + " ");
        }
        StdOut.print('\n');
    }

    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        if (args.length == 3) {
            opcao = Integer.parseInt(args[2]);
        }
        Stopwatch timer = new Stopwatch();
        combinacao(n, k);
        StdOut.println(count);
        StdOut.println("elapsed time = " + timer.elapsedTime());
    }
}
