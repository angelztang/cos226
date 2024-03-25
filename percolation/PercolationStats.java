import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // 95% of area under normal curve, defined in standard deviations
    private static final double CI_95_STDDEVS = 1.96;
    // number of trials
    private int trials;
    // array of percolation thresholds
    private double[] percThresh;
    // running time of constructor
    private double time;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Not valid argument");
        this.trials = trials;
        Stopwatch timer = new Stopwatch();
        percThresh = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation1 grid = new Percolation1(n);
            while (!grid.percolates()) {
                grid.open(StdRandom.uniformInt(n), StdRandom.uniformInt(n));
            }
            percThresh[i] = grid.numberOfOpenSites() / Math.pow(n, 2);
        }
        time = timer.elapsedTime();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percThresh);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percThresh);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return this.mean() - CI_95_STDDEVS * this.stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return this.mean() + CI_95_STDDEVS * this.stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(size, trials);
        StdOut.printf("mean()           = %.6f\n", test.mean());
        StdOut.printf("stddev()         = %.6f\n", test.stddev());
        StdOut.printf("confidenceLow()  = %.6f\n", test.confidenceLow());
        StdOut.printf("confidenceHigh() = %.6f\n", test.confidenceHigh());
        StdOut.printf("elapsed time     = " + test.time);
    }

}
