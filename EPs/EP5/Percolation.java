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
// o encapsulamento? Eu acho que não por causa da palavra"private", vou conferir

// TODO : entender a dinamica do EP. Eu vou receber uma matriz pronta, é isso?
// TODO : quando tornar um quadrado "Full"?, ou seja, parte do caminho que em 
// tese percola



public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private boolean[][] sitesOpen;
    private int topSite;
    private int bottomSite;
    private int openSites;
    private int n;
    private WeightedQuickUnionUF WQUF;


    public Percolation(int n) {

        if (n <= 0)
            throw new IllegalArgumentException();
        WQUF = new WeightedQuickUnionUF(n*n+2); // permite armazenar o topsite e o bottomsite
        this.n = n;
        openSites = 0;
        topSite = n*n;
        bottomSite = n*n+1;
        sitesOpen = new boolean[n][n];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                sitesOpen[i][j] = false;
            }
        }


    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if( row > this.n || row < 0 || col > this.n || col < 0 )
            throw new IllegalArgumentException();
        if( !isOpen(row, col) ) {
            int linearIndex = toIndex(row, col);
            sitesOpen[row][col] = true;
            if(row == 0 )
                WQUF.union(linearIndex, topSite);
            if(row == n-1)
                WQUF.union(linearIndex, bottomSite);
            connectAround(row, col);
            openSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if( row > this.n || row < 0 || col > this.n || col < 0 )
            throw new IllegalArgumentException();
        return sitesOpen[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if( row > this.n || row < 0 || col > this.n || col < 0 )
            throw new IllegalArgumentException();
        return WQUF.connected( toIndex(row, col), topSite );
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return WQUF.connected(topSite, bottomSite);
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    private void connectAround(int row, int col) {

        int[][] arround = {{row, col-1}, {row+1, col}, {row, col+1}, {row-1, col}};
        for(int i = 0; i < 4; i++) {
            if(arround[i][0] < n && arround[i][0] >= 0 && arround[i][1] < n && arround[i][1] >= 0 && isOpen(arround[i][0], arround[i][1]))
            // Tudo isso pra dizer que eu vou conectar com o vizinho livre adjacente. TODO : refatorar isso.
                WQUF.union( toIndex(row, col), toIndex(arround[i][0], arround[i][1]) );
        }
    }

    // Makes the matrix indexes linear
    private int toIndex(int i, int j) {
        int newIndex = this.n*i + j;
        return newIndex;

    }

}