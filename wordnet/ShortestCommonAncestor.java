import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ShortestCommonAncestor {
    // digraph
    private Digraph dig;
    // shortest dist
    private int shortestDist;
    // shortest common ancestor
    private int sca;


    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        shortestDist = Integer.MAX_VALUE;
        sca = -1;
        if (G == null) throw new IllegalArgumentException("Argument is null");
        DirectedCycle checkCycle = new DirectedCycle(G);
        int root = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) {
                root += 1;
            }
        }
        if (root != 1)
            throw new IllegalArgumentException("Illegal arg");
        if (checkCycle.hasCycle())
            throw new IllegalArgumentException("Digraph is DAG");
        dig = new Digraph(G);

    }

    // rewritten bfs method
    private HashMap<Integer, Integer> bfs(Digraph G, Iterable<Integer> sources) {
        boolean[] marked = new boolean[dig.V()];
        HashMap<Integer, Integer> bfs = new HashMap<Integer, Integer>();
        Queue<Integer> queue = new Queue<Integer>();

        for (int x : sources) {
            queue.enqueue(x);
            marked[x] = true;
            bfs.put(x, 0);
        }
        while (!queue.isEmpty()) {
            int source = queue.dequeue();
            for (int s : G.adj(source)) {
                if (!marked[s]) {
                    queue.enqueue(s);
                    marked[s] = true;
                    bfs.put(s, bfs.get(source) + 1);
                }
            }
        }
        return bfs;
    }


    // calculates shortest dist and shortest common ancestor
    private void calcSDandSCA(HashMap<Integer, Integer> bfsA,
                              HashMap<Integer, Integer> bfsB) {
        shortestDist = Integer.MAX_VALUE;
        sca = -1;

        for (int x : bfsA.keySet()) {
            if (bfsB.containsKey(x) && bfsA.get(x) + bfsB.get(x) < shortestDist) {
                sca = x;
                shortestDist = bfsA.get(x) + bfsB.get(x);
            }
        }
    }

    // checks if arguments are valid, if not throw IllegalArgumentException
    private void checkValidSubset(Iterable<Integer> subsetA,
                                  Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null)
            throw new java.lang.IllegalArgumentException("Illegal arg");
        int sizeA = 0;
        for (Integer x : subsetA) {
            sizeA++;
            if (x == null || x < 0 || x >= dig.V())
                throw new java.lang.IllegalArgumentException("Illegal argument");
        }
        if (sizeA == 0)
            throw new IllegalArgumentException("Illegal arg");
        int sizeB = 0;
        for (Integer x : subsetB) {
            sizeB++;
            if (x == null || x < 0 || x >= dig.V())
                throw new java.lang.IllegalArgumentException("Illegal argument");
        }
        if (sizeB == 0)
            throw new IllegalArgumentException("Illegal arg");
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        if (v < 0 || v >= dig.V() || w < 0 || w >= dig.V()) {
            throw new IllegalArgumentException("Argument is outside of bounds");
        }
        ArrayList<Integer> subsetA = new ArrayList<Integer>();
        subsetA.add(v);
        ArrayList<Integer> subsetB = new ArrayList<Integer>();
        subsetB.add(w);
        HashMap<Integer, Integer> bfsA = bfs(dig, subsetA);
        HashMap<Integer, Integer> bfsB = bfs(dig, subsetB);
        calcSDandSCA(bfsA, bfsB);
        int dist = shortestDist;
        return dist;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        if (v < 0 || v >= dig.V() || w < 0 || w >= dig.V()) {
            throw new IllegalArgumentException("Argument is outside of bounds");
        }
        ArrayList<Integer> subsetA = new ArrayList<Integer>();
        subsetA.add(v);
        ArrayList<Integer> subsetB = new ArrayList<Integer>();
        subsetB.add(w);
        HashMap<Integer, Integer> bfsA = bfs(dig, subsetA);
        HashMap<Integer, Integer> bfsB = bfs(dig, subsetB);
        calcSDandSCA(bfsA, bfsB);
        int ancestor = sca;
        return ancestor;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        checkValidSubset(subsetA, subsetB);
        HashMap<Integer, Integer> pv = bfs(dig, subsetA);
        HashMap<Integer, Integer> pw = bfs(dig, subsetB);
        calcSDandSCA(pv, pw);
        int dist = shortestDist;
        return dist;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        checkValidSubset(subsetA, subsetB);
        HashMap<Integer, Integer> pv = bfs(dig, subsetA);
        HashMap<Integer, Integer> pw = bfs(dig, subsetB);
        calcSDandSCA(pv, pw);
        int ancestor = sca;
        return ancestor;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        Integer[] listA = { 1, 2, 3, 4 };
        Integer[] listB = { 1, 2, 3 };
        Iterable<Integer> testA = Arrays.asList(listA);
        Iterable<Integer> testB = Arrays.asList(listB);
        int lengthSub = sca.ancestorSubset(testA, testB);
        int ancestorSub = sca.lengthSubset(testA, testB);
        StdOut.printf("length = %d, ancestor = %d\n", lengthSub, ancestorSub);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
