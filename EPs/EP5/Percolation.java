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

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

import java.lang.IllegalArgumentException;

// TODO : faz sentido eu usar o this para varias coisas? Isso não violaria
// o encapsulamento?

// TODO : entender a dinamica do EP. Eu vou receber uma matriz pronta, é isso?



public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private [][] Boolean sitesOpen;
    private [][] Boolean sitesFull;
    private int topSite;
    private int bottomSite;
    private int openSites;


    public Percolation(int n) {

        if (n <= 0)
            throw new IllegalArgumentException
        
        this.n = n;
        openSites = 0;
        topSite = -1;
        bottomSite = n;
        sitesOpen = new Boolean[n][n];
        sitesFull = new Boolean[n][n];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                sitesOpen[i][j] = false;
                sitesFull = false;
            }
        }

        for (int i = 0; i < n; i++){
            WeightedQuickUnionUF.union(topSite, toIndex(i, 0));
            WeightedQuickUnionUF.union(bottomSite, toIndex(n-1, i));
        }


    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ( row > this.n || row < 0 || col > this.n || col < 0 )
            throw new IllegalArgumentException()
        sitesOpen[row][col] = true;
        // TODO : ao abrir um site, precisa conectar a todos os sites adjacentes que estejam abertos 
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ( row > this.n || row < 0 || col > this.n || col < 0 )
            throw new IllegalArgumentException()
        return sitesOpen[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ( row > this.n || row < 0 || col > this.n || col < 0 )
            throw new IllegalArgumentException()
        return sitesFull[row][col];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return WeightedQuickUnionUF.count(topSite); // TODO : Não deveria relacionar de alguma forma com o bottomSite?
    }

    // does the system percolate?
    public boolean percolates() {
        return WeightedQuickUnionUF.connected(topSite, bottomSite);
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    // Makes the matrix indexes linear
    private int toIndex(int i, int j) {
        int newIndex = this.n*i + j;
        return newIndex;

    }

}