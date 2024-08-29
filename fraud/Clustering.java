import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KruskalMST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Clustering {

    private int clusters; // number of clusters or k
    private int m; // number of points/vertices
    private CC connect; // connected components of cluster graph


    // run the clustering algorithm and create the clusters
    public Clustering(Point2D[] locations, int k) {
        if (locations == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        if (k < 1 || k > locations.length) {
            throw new IllegalArgumentException(
                    "k cannot be less than one or more than m");
        }
        m = locations.length;
        clusters = k;

        // construct initial and fill initial graph
        EdgeWeightedGraph graph = new EdgeWeightedGraph(m);
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                double weighted = locations[i].distanceTo(locations[j]);
                Edge e = new Edge(i, j, weighted);
                graph.addEdge((e));
            }
        }

        // construct mst
        KruskalMST mst = new KruskalMST(graph);
        Edge[] edges = new Edge[m - 1];
        int c = 0;

        for (Edge e : mst.edges()) {
            if (e != null) {
                edges[c++] = e;
            }
        }
        Arrays.sort(edges);
        Edge[] remainingEdges = new Edge[m - k]; // only consider m-k edges
        for (int i = 0; i < (m - k); i++) {
            remainingEdges[i] = edges[i];
        }

        // construct cluster graph
        EdgeWeightedGraph cluster = new EdgeWeightedGraph(m); // m vertices
        for (int i = 0; i < remainingEdges.length; i++) {
            cluster.addEdge(remainingEdges[i]);
        }
        connect = new CC(cluster);
    }

    // return the cluster of the ith point
    public int clusterOf(int i) {
        if (i < 0 || i > m - 1) {
            throw new IllegalArgumentException("int i is out of bounds");
        }
        return connect.id(i);
    }

    // use the clusters to reduce the dimensions of an input
    public int[] reduceDimensions(int[] input) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null");
        }
        if (input.length != m) {
            throw new IllegalArgumentException("input length must match m");
        }
        int[] reduced = new int[clusters];
        for (int i = 0; i < input.length; i++) {
            reduced[clusterOf(i)] += input[i];
        }

        return reduced;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In filename = new In(args[0]);
        int k = Integer.parseInt(args[1]);
        int m = filename.readInt();
        Point2D[] points = new Point2D[m];
        int i = 0;
        while (!filename.isEmpty()) {
            double x = filename.readDouble();
            double y = filename.readDouble();
            Point2D point = new Point2D(x, y);
            points[i] = point;
            i++;
        }

        Clustering object = new Clustering(points, k);

        int[] input = {
                5, 6, 7, 0, 6, 7, 5, 6, 7, 0, 6, 7, 0, 6, 7, 0, 6, 7, 0, 6, 7
        };

        StdOut.print("(");
        for (int j = 0; j < input.length; j++) {
            if (j < input.length - 1) {
                StdOut.print(input[j] + ", ");
            }
            else {
                StdOut.print(input[j]);
            }
        }
        StdOut.println(")");

        StdOut.println(object.clusterOf(0)); // cluster 0
        StdOut.println(object.clusterOf(1)); // cluster 1
        StdOut.println(object.clusterOf(6)); // cluster 2
        StdOut.println(object.clusterOf(11)); // cluster 3
        StdOut.println(object.clusterOf(20)); // cluster 4

        StdOut.print("(");
        int[] reduce = object.reduceDimensions(input);
        for (int j = 0; j < reduce.length; j++) {
            if (j < reduce.length - 1) {
                StdOut.print(reduce[j] + ", ");
            }
            else StdOut.print(reduce[j]);
        }
        StdOut.println(")");
    }
}
