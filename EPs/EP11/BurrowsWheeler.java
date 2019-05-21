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
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {

        String s = BinaryStdIn.readString();
        int strSize = s.length();

        CircularSuffixArray CSA = new CircularSuffixArray(s);
        Arrays.sort(CSA.arr);

        Queue<Character> lastLetters = new Queue<Character>();
        int j; // TODO : pensar num nome melhor pra essa variável, pelo amor de
        // cristo
        char c;
        boolean found = false;
        for(int i = 0; i < strSize; i++) {

            if(CSA.index(i) == 0) { // TODO : acho que isso so ocorre uma vez
                j = i;
                found = true;
                BinaryStdOut.write(j);
                while(!lastLetters.isEmpty()){
                    c = lastLetters.dequeue();
                    BinaryStdOut.write(c, 8);
                } 
            }
            lastLetters.enqueue(CSA.arr[i].getLastLetter());

            if(found){
                c = lastLetters.dequeue();
                BinaryStdOut.write(c, 8);
            }
        }

        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform(){
        return;
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args){
        BurrowsWheeler BW = new BurrowsWheeler();
        BurrowsWheeler.transform();
    }

}