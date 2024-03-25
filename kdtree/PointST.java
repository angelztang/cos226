import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

public class PointST<Value> {
    private RedBlackBST<Point2D, Value> points; // table of 2D points

    // construct an empty symbol table of points
    public PointST() {
        points = new RedBlackBST<Point2D, Value>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points
    public int size() {
        return points.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null) {
            throw new IllegalArgumentException("point cannot be null");
        }
        if (val == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        points.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        return points.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point cannot be null");
        }
        return points.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return points.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rectangle cannot be null");
        }
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p : points()) {
            if (rect.contains(p)) {
                queue.enqueue(p);
            }
        }
        return queue;

    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point cannot be null");
        }
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D closest = null;
        for (Point2D pCurrent : points()) {
            double distanceCurrent = p.distanceSquaredTo((pCurrent));

            // update min if current distance is less
            if (distanceCurrent < minDistance) {
                minDistance = distanceCurrent;
                closest = pCurrent;
            }
        }
        return closest;
    }

    // unit testing (required)
    public static void main(String[] args) {
        PointST<Integer> test = new PointST<>();
        StdOut.println("The table is empty = " + test.isEmpty());
        Point2D a = new Point2D(1, 2);
        Point2D b = new Point2D(2, 2);
        Point2D c = new Point2D(3, 3);

        test.put(a, 4);
        test.put(b, 1);
        test.put(c, 2);

        StdOut.println("The table continas point c = " + test.contains(c));
        StdOut.println("size = " + test.size());
        StdOut.println("Value at point a = " + test.get(a));
        StdOut.println(test.points());
        RectHV tester = new RectHV(2.0, 2.0, 4.0, 6.0);
        StdOut.println(test.range(tester));
        StdOut.println(test.nearest(b));

        // timing analysis code

        // String filename = args[0];
        // In in = new In(filename);
        // PointST<Double> table = new PointST<>();
        // while (!in.isEmpty()) {
        //     double x = in.readDouble();
        //     double y = in.readDouble();
        //     Point2D test = new Point2D(x, y);
        //     table.put(test, 1.0);
        // }
        // Stopwatch timer = new Stopwatch();
        // for (int i = 0; i < 1000000; i++) {
        //     double x = StdRandom.uniformDouble(0.0, 1.0);
        //     double y = StdRandom.uniformDouble(0.0, 1.0);
        //     Point2D p = new Point2D(x, y);
        //     table.nearest(p);
        // }
        // StdOut.println("time = " + timer.elapsedTime());
    }

}
