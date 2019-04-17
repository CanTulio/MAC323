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
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

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

        public Node(Point2D p, Value value, RectHV rect, Node left, Node right, int level) {
            
            this.p = p;
            this.value = value;
            this.rect = rect;
            this.left = left;
            this.right = right;
            this.level = level;
        }
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

        if ( contains(p) ) { // atualiza o valor se o elemento já estiver na ST
            Node content = get(p, this.head);
            content.value = val;
            return;
        }

        Node current = this.head;
        int count;
        Double xmin, ymin, xmax, ymax;

        xmin = Double.NEGATIVE_INFINITY;
        ymin = Double.NEGATIVE_INFINITY;

        xmax = Double.POSITIVE_INFINITY;
        ymax = Double.POSITIVE_INFINITY;

        for (count = 1; current != null; count++) { // posso fazer a verificação
        // do contains aqui.

            if ( isLevelEven(current.level) ) { // vertical
                
                xmin = current.rect.xmin();
                xmax = current.rect.xmax();  

                if ( p.x() >= current.p.x()) { // filho direito
                    ymin = current.p.y();
                    current = current.right;
                }
                else {
                    ymax = current.p.y();
                    current = current.left;
                }
            }

            else { // horizontal

                ymin = current.rect.ymin();
                ymax = current.rect.ymax();

                if ( p.y() >= current.p.y() ) {  // filho direito 
                    xmin = current.p.x();
                    current = current.right;
                }
                else {
                    xmax = current.p.x();
                    current = current.left;
                }
            }

        }
        RectHV nodeRectangle = new RectHV(xmin, ymin, xmax, ymax);
        current = new Node(p, val, nodeRectangle, null, null, count);
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
            if (isLevelEven(current.level)) {
                if ( p.x() < current.p.x() )
                    return get(p, current.left);
                else
                    return get(p, current.right);
            }

            else {
                if ( p.y() < current.p.y() )
                    return get(p, current.left);
                else
                    return get(p, current.right);
            }
        }
    }

    // does the symbol table contain point p? 
    public boolean contains(Point2D p) {
        return this.get(p) != null;
    }

    // all points in the symbol table 
    public Iterable<Point2D> points() {
        
        Queue <Point2D> myPoints = new Queue<Point2D> ();
        Queue <Node> aux = new Queue<Node>();

        Node current = this.head;

        aux.enqueue(current);
        myPoints.enqueue(current.p);

        while( this.head != null && !aux.isEmpty()) {
            
            if (current.left != null) {
                myPoints.enqueue(current.left.p);
                aux.enqueue(current.left);
            }

            if (current.right != null) {
                myPoints.enqueue(current.right.p);
                aux.enqueue(current.right);
            }

            current = aux.dequeue();
        }

        return myPoints;
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {

        Stack<Point2D> insideRange = new Stack<Point2D>();
        insideRange = range(rect, this.head);

        return insideRange;
    }

    private Stack<Point2D> range(RectHV rect, Node start) {

        Stack<Node> candidates = new Stack<Node> ();
        Stack<Point2D> insideRange = new Stack<Point2D>();

        Node current = start;
        candidates.push(current);

        // TODO : detalhe importante : a minha função empilha nós nulos, mas ela
        // prontamente remove os nós nulos a baixo (algoritmo lazy)

        while ( current != null ) { 
           
            if (rect.intersects(current.rect)) {
                candidates.push(current.left); 
                candidates.push(current.right);
            }

            else { // TODO : verificar se é refatoravel unir 1 com 2, algo assim.
                if (isLevelEven(current.level)) { // verificacao do y, linha horizontal
                    if (rect.xmin() < current.p.x()) { //1
                        candidates.push(current.left);
                    }

                    else {
                        candidates.push(current.right);
                    }
                }

                else { // verificação do x, linha vertical
                    if (rect.ymin() < current.p.y()) { // 2
                        candidates.push(current.left);
                    }

                    else {
                        candidates.push(current.right);
                    }
                }
            }

            if (rect.contains(current.p))
                insideRange.push(current.p);

            while (candidates.size() > 1 && candidates.peek() == null ) // esse length serve pra deixar pelo menos um nulo, caso só insira caras nulos. Dai o loop vai acabar. isso é meio gambiarra. TODO : remover isso.
                candidates.pop();
              
            current = candidates.pop();
        }

        return insideRange;
        // if ( rect.contains(start.p) ) {

        // }
        // if ( rect.intersects(start.rect) ) {
        //     range(rect, start.left);
        //     range(rect, start.right);
        // }
    }

    // a nearest neighbor of point p; null if the symbol table is empty 
    public Point2D nearest(Point2D p) {
        return null;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}