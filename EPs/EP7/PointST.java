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


import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stdout;



public class PointST<Value> {

    // construct an empty symbol table of points 
    private RedBlackBST<Point2D, Value> ST;
    public PointST() {
        this.ST = new RedBlackBST<Point2D, Value>();
    }

    // is the symbol table empty? 
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points
    public int size() {
        return this.ST.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        this.ST.put(p, val);
    }

    // value associated with point p 
    public Value get(Point2D p) {
        return this.ST.get(p);
    }

    // does the symbol table contain point p? 
    public boolean contains(Point2D p) {
        return this.ST.contains(p);
    }

    // all points in the symbol table 
    public Iterable<Point2D> points() {
        return this.ST.keys();
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        Point2D min = new Point2D(rect.xmin(), rect.ymin());
        Point2D max = new Point2D(rect.xmax(), rect.ymax());
        return this.ST.keys(min, max); // TODO : não tenho ctz se isso funciona
    }

    // a nearest neighbor of point p; null if the symbol table is empty 
    public Point2D nearest(Point2D p) {
        
        if (this.isEmpty())
            return null;

        Iterable<Point2D> allKeys = this.points();
        double dist = 0;
        double currentDistance;
        Point2D candidate = p; // TODO : sem essa inicialização, o cara chia.

        for (Point2D key : allKeys) {

            currentDistance = key.distanceSquaredTo(p);

            if (currentDistance >= dist) { // TODO : e esse maior igual?
                dist = currentDistance;
                candidate = key;
            }
        }

        return candidate;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}