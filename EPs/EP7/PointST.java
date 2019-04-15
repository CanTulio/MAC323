import edu.princeton.cs.algs4.RedBlackBST<Key,Value>;
import edu.princeton.cs.algs4.Point2D;



public class PointST<Value> {

    // construct an empty symbol table of points 
    private RedBlackBST ST;
    public PointST() {
        this.ST = new RedBlackBST<Point2D, Value>();
    }

    // is the symbol table empty? 
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points
    public int size() {
        return this.ST.size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        this.ST.put(p, val);
    }

    // value associated with point p 
    public Value get(Point2D p) {
        this.ST.get(p);
    }

    // does the symbol table contain point p? 
    public boolean contains(Point2D p) {
        this.ST.contains(p);
    }

    // all points in the symbol table 
    public Iterable<Point2D> points() {
        return this.ST.keys();
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        Point2D min = new Point2D(rect.xmin(), rec.ymin());
        Point2D max = new Point2D(rect.xmax(), rec.ymax());
        return this.ST.keys(min, max); // TODO : não tenho ctz se isso funciona
    }

    // a nearest neighbor of point p; null if the symbol table is empty 
    public Point2D nearest(Point2D p) {
        
        if (this.isEmpty())
            return null;

        Iterator allKeys = this.points();
        int dist = 0;
        int currentDistance;
        Point2D candidate;

        for (key : allKeys) {

            currentDistance = key.distanceSquaredTo(p);

            if (currentDistance >= dist) { // TODO : e esse maior igual?
                dist = currentDistance;
                candidate = key;
            }
        }

        return candidate;
    }

    // unit testing (required)
    public static void main(String[] args)

}