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
import java.lang.IllegalArgumentException;

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
        this.head = null; 
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
    public void put(Point2D p, Value val) { 

        boolean right = false;
        boolean left = false;

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

        for (count = 1; !left && !right && this.head != null; count++) { 

            if ( isLevelEven(current.level) ) { // Elemento é do tipo vertical
                
                ymin = current.rect.ymin(); // herda xmin e ymax do pai,
                ymax = current.rect.ymax(); // atualiza o resto baseado no pai. 

                if ( p.x() >= current.p.x()) { // filho direito
                    xmin = current.p.x();
                    if (current.right != null)
                        current = current.right;
                    else
                        right = true; // sera inserido a direita do atual
                }
                else {
                    xmax = current.p.x();
                    if (current.left != null)
                        current = current.left;
                    else 
                        left = true;
                }
            }

            else { // horizontal

                xmin = current.rect.xmin();
                xmax = current.rect.xmax();

                if ( p.y() >= current.p.y() ) {  
                    ymin = current.p.y();
                    if (current.right != null)
                        current = current.right;
                    else
                        right = true;
                }
                else {
                    ymax = current.p.y();
                    if (current.left != null)
                        current = current.left;
                    else
                        left = true;
                }
            }

        }
        RectHV nodeRectangle = new RectHV(xmin, ymin, xmax, ymax);
        
        Node newNode = new Node(p, val, nodeRectangle, null, null, count);
        
        if (this.head == null)
            this.head = newNode;
        if (left)
            current.left = newNode;
        if (right)
            current.right = newNode;
    }

    private boolean isLevelEven(int level) {

        return Math.floor(log2(level)) % 2 == 0;
    }

    private double log2(int n) {

        return (Math.log(n) / Math.log(2));
    }

    // value associated with point p 
    public Value get(Point2D p) {
        
        if (p == null)
            throw new IllegalArgumentException();

        Node result = get(p, this.head);
        if(result != null)
            return result.value;
        else
            return null;
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
        if (p == null)
            throw new IllegalArgumentException();
        return get(p) != null;
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

        if (rect == null)
            throw new IllegalArgumentException();


        Stack<Point2D> insideRange = new Stack<Point2D>();
        insideRange = range(rect, this.head);

        return insideRange;
    }

    private Stack<Point2D> range(RectHV rect, Node start) {

        Stack<Node> candidates = new Stack<Node> ();
        Stack<Point2D> insideRange = new Stack<Point2D>();

        Node current = start;
        candidates.push(current);

        while ( current != null ) { 

            if (rect.intersects(current.rect)) {
                candidates.push(current.left); 
                candidates.push(current.right);
            }

            else { 
                if (isLevelEven(current.level)) { // verificacao do y, linha horizontal
                    if (rect.xmin() < current.p.x()) { 
                        candidates.push(current.left);
                    }

                    else {
                        candidates.push(current.right);
                    }
                }

                else { // verificação do x, linha vertical
                    if (rect.ymin() < current.p.y()) {
                        candidates.push(current.left);
                    }

                    else {
                        candidates.push(current.right);
                    }
                }
            }

            if (rect.contains(current.p))
                insideRange.push(current.p);

            while (candidates.size() > 1 && candidates.peek() == null ) 
                candidates.pop();
              
            current = candidates.pop(); // caso so exista um candidato na pilha
            // e seja um nulo, acaba o loop.
            // sempre vai existir pelo menos um null na pilha pois se empilha
            // os filhos de um nó, e haverão nós com filhos nulos
        }

        return insideRange;
    }

    // a nearest neighbor of point p; null if the symbol table is empty 
    public Point2D nearest(Point2D p) {

        if (p == null)
            throw new IllegalArgumentException();

        Point2D nearestPoint = nearest(p, this.head, null);
        return nearestPoint;
        
    }

    private Point2D nearest(Point2D p, Node current, Point2D closer) {
        if (current != null) {

            if (closer == null){
                closer = current.p;
            }

            if (closer.distanceTo(p) >= current.rect.distanceTo(p)){

                if (current.p.distanceTo(p) < closer.distanceTo(p))
                    closer = current.p;

                if (current.left != null && current.left.rect.contains(p)) {
                    closer = nearest(p, current.left , closer);
                    closer = nearest(p, current.right, closer);
                }

                else {
                    closer = nearest(p, current.right, closer);
                    closer = nearest(p, current.left, closer);
                }

            }
        }
        return closer;

    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}

