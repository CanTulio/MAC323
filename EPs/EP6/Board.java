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

    Descrição de ajuda ou indicação de fonte:



    Se for o caso, descreva a seguir 'bugs' e limitações do seu programa:

****************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack<Item>;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    private int[][] board;
    private int n;
    private int hamming;
    private int manhattan;
    private boolean solved;
    public Board(int[][] tiles) {
        this.hamming = 0;
        this.n = tiles[0].length;
        this.solved = true;

        //recebe uma permutação do teu board
        for(int i = 0; i < this.n; i++)
            for(int j = 0; j < this.n; i++) {
                this.board[i][j] = tiles[i][j];
                if( tileAt(i,j) != linearIndexOf(i,j) + 1  ) // hemming
                    this.hamming++;
                this.manhattan += Math.abs(linearIndexOf(i,j) + 1 - tileAt(i,j));
            }

    }
                                           
    // string representation of this board
    public String toString() { // esse aqui eu acho que vai sair zoado haha
        String output = "";
        output = Integer.toString(this.n) + "\n";
        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.n; j++)
                output += Integer.toString(this.board[i][j]) + " ";
            output += "\n";
        }

        return output;
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        return this.board[row][col];
    }

    // board size n
    public int size() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        return this.hemming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return this.manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        isEqual = true;

        for(int i = 0; i < this.n && isEqual; i++) {
            for(int j = 0; j < this.n && isEqual; j++) {
                if( y[i][j] != this.board[i][j])
                    isEqual = false;
            }
        }

        return equals;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> s = new Stack<Board>();
        // tenho que empilhar todos os movimentos possiveis com o 0. No maximo 
        // vão ser quatro movimentos
    }

    // is this board solvable?
    public boolean isSolvable() {
        if (this.n%2 == 0) { // é par
            
        }
    }


    private int linearIndexOf(int i, int j) {

        return this.n*i + j;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}