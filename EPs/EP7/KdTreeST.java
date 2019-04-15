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


import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stdout;

import java.lang.Math;


public class KdTreeST<Value> {

    // construct an empty symbol table of points 
    private class Node {
        
        private Point2D p;      // the point
        private Value value;    // the symbol table maps the point to this value
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree
        private int level;      // stores the level the node is inserted in
    }

    private int n;
    private Node head;

    public KdTreeST() {
        this.n = 0;
        this.head = null; // necessario?
    }

    // is the symbol table empty? 
    public boolean isEmpty() {
        return this.n == 0;
    }

    // number of points
    public int size() {
        return this.n;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) { //TODO : altamente refatorável
    // TODO : que porra de retangulo eu coloco?
    // TODO : fazer deleção no caso de um put(NULL) 

        Node current = this.head;
        int count;
        for (count = 1; current != null; count++) {

            if ( isLevelEven(current.level) ) {
                if ( p.y() >= current.p.y())
                    current = current.right;
                else
                    current = current.left;
                     
            }

            else {
                if ( p.x() >= current.p.x() )
                    current = current.right;
                else
                    current = current.left;
            }

        }

        current = new Node(p, val, ALGUMRETANGULO, null, null, count);
    }

    private boolean isLevelEven(int level) {
        return Math.floor(log2(level)) % 2 == 0;
    }

    private double log2(int n) {
        return (Math.log(n) / Math.log(2));
    }

    // value associated with point p 
    public Value get(Point2D p) {
        
        Node result = get(p, this.head);
        return result.value;
    }

    private Node get(Point2D p, Node current) {

        if (current == null)
            return null;

        if (current.p == p) 
            return current;

        else {
            if (isLevelEven(current)) {
                if (p.y() < current.p.y())
                    return get(p, current.left);
                else
                    return get(p, current.rigth);
            }

            else {
                if (p.x() < current.p.x())
                    return get(p, current.left);
                else
                    return get(p, current.rigth);
            }
        }
    }

    // does the symbol table contain point p? 
    public boolean contains(Point2D p) {
        return this.get(p) != null;
    }

    // all points in the symbol table 
    public Iterable<Point2D> points()

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect)

    // a nearest neighbor of point p; null if the symbol table is empty 
    public Point2D nearest(Point2D p)

    // unit testing (required)
    public static void main(String[] args)

}