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
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class CircularSuffixArray {

    private class CircularSuffix implements Comparable<CircularSuffix> {
        
        int first;
        String original;

        public CircularSuffix(String original, int first) {
            
            this.first = first;
            this.original = original;
        }

        public int compareTo(CircularSuffix s) {
            
            int currentThis = this.first;
            int currentThat = s.first;
            int i = 0;
            while(this.original.charAt(currentThis) == s.original.charAt(currentThat)
                    && i < s.original.length()) {
                currentThis = (currentThis + 1)%this.original.length();
                currentThat = (currentThat + 1)%s.original.length();
                i++;
            }

            if(this.original.charAt(currentThis) < s.original.charAt(currentThat)){ 
                return -10; // TODO : retorno gambiarrento
            }
            else
                return 10;
        }

        // public CircularSuffix next(CircularSuffix prev) {
        //     int newfirst = (this.first++)%this.original.length;
        //     return new CircularSuffix(this.original, newfirst);
        // }
    }
    int n;
    CircularSuffix[] arr;
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        this.n = s.length();
        this.arr = new CircularSuffix[this.n];
        for(int i = 0 ; i < this.n; i++) {
            arr[i] = new CircularSuffix(s, i);
        }
    }

    // length of s
    public int length(){
        return this.n;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        return this.arr[i].first;
    }

    private void printAllStrings(String s) {
        CircularSuffixArray CSA = new CircularSuffixArray(s);
        for(int j = 0; j < CSA.length(); j++) {
            String printed ="";
            for(int i = 0; i < s.length(); i++) {
                printed += CSA.arr[j].original.charAt((i+CSA.arr[j].first)%s.length());
            }
            StdOut.println(printed);
            printed="";
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray CSA = new CircularSuffixArray(args[0]);
        CSA.printAllStrings(args[0]);
        StdOut.println("");
        int i = Integer.parseInt(args[1]);
        Arrays.sort(CSA.arr);
        StdOut.println(CSA.arr[0].first);
        StdOut.println("A " + args[1] + " sesima permutação ordenada tem indice " + CSA.index(i));
    }

}