import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdOut;

public class WeakLearner {
    private int dp; // dimension predictor
    private int vp; // value predictor
    private int sp; // sign predictor
    private int k; // dimension of input point

    // train the weak learner
    public WeakLearner(int[][] input, double[] weights, int[] labels) {
        // Check for null arguments
        if (input == null || weights == null || labels == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        // Check for incompatible lengths
        if (input.length != weights.length || input.length != labels.length) {
            throw new IllegalArgumentException(
                    "Incompatible lengths of input, weights, or labels");
        }

        // Check for non-negative weights
        for (double weight : weights) {
            if (weight < 0) {
                throw new IllegalArgumentException("Weights must be non-negative");
            }
        }

        // Check for labels being either 0 or 1
        for (int label : labels) {
            if (label != 0 && label != 1) {
                throw new IllegalArgumentException("Labels must be either 0 or 1");
            }
        }

        k = input[0].length;
        double bestWeightedCorrect = -1;
        double weightedSum = 0;
        for (int j = 0; j < weights.length; j++) weightedSum += weights[j];
        Integer[] integer = new Integer[input.length];
        // iterate through input by each dimension of a point
        for (int dim = 0; dim < input[0].length; dim++) {
            for (int i = 0; i < input.length; i++)
                integer[i] = input[i][dim];
            int[] indices = Merge.indexSort(integer);
            double weightedCorrect = 0;

            // Calculate weighted correctness if partition line was on axis
            for (int j = 0; j < input.length; j++) {
                int prediction = 1;
                if (prediction == labels[indices[j]]) {
                    weightedCorrect += weights[indices[j]];
                }
            }
            // update weightedCorrect
            for (int j = 0; j < input.length; j++) {
                int prediction = 0;
                if (prediction == labels[indices[j]])
                    weightedCorrect += weights[indices[j]];
                else if (prediction != labels[indices[j]])
                    weightedCorrect -= weights[indices[j]];
                if (j < input.length - 1 && input[indices[j]][dim] == input[indices[j
                        + 1]][dim])
                    continue;

                if (weightedCorrect > bestWeightedCorrect) {
                    bestWeightedCorrect = weightedCorrect;
                    dp = dim;
                    vp = input[indices[j]][dim];
                    sp = 0;
                }

                // Check if reversing the prediction improves weighted correctness
                if (weightedSum - weightedCorrect > bestWeightedCorrect) {
                    bestWeightedCorrect = weightedSum - weightedCorrect;
                    dp = dim;
                    vp = input[indices[j]][dim];
                    sp = 1;
                }
            }
        }
    }

    // return the prediction of the learner for a new sample
    public int predict(int[] sample) {
        if (sample == null) {
            throw new IllegalArgumentException("sample cannot be null");
        }
        if (sample.length != k) {
            throw new IllegalArgumentException("sample must be compatible");
        }
        int value = sample[dp];
        if ((sp == 0 && value <= vp) || (sp == 1 && value > vp))
            return 0;
        else return 1;
    }

    // return the dimension the learner uses to separate the data
    public int dimensionPredictor() {
        return dp;
    }

    // return the value the learner uses to separate the data
    public int valuePredictor() {
        return vp;
    }

    // return the sign the learner uses to separate the data
    public int signPredictor() {
        return sp;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In datafile = new In(args[0]);

        int n = datafile.readInt();
        int k = datafile.readInt();

        int[][] input = new int[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                input[i][j] = datafile.readInt();
            }
        }

        int[] labels = new int[n];
        for (int i = 0; i < n; i++) {
            labels[i] = datafile.readInt();
        }

        double[] weights = new double[n];
        for (int i = 0; i < n; i++) {
            weights[i] = datafile.readDouble();
        }

        WeakLearner weakLearner = new WeakLearner(input, weights, labels);
        StdOut.printf("vp = %d, dp = %d, sp = %d\n", weakLearner.valuePredictor(),
                      weakLearner.dimensionPredictor(), weakLearner.signPredictor());

        int[] test = { 1, 2 };
        StdOut.println(weakLearner.predict(test));
    }
}


