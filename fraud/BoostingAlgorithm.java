import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class BoostingAlgorithm {
    private double[] weights; // weight of each point array
    private int[][] input; // points
    private int[] labels; // 0 or 1 label of each point
    private LinkedList<WeakLearner> weakLearners; // list of weakLearners
    private Clustering clusters; // clustering
    private int m; // number of points

    // create the clusters and initialize your data structures
    public BoostingAlgorithm(int[][] input, int[] labels, Point2D[] locations, int k) {
        if (input == null || labels == null || locations == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        if (input.length != labels.length || input[0].length != locations.length) {
            throw new IllegalArgumentException("array lengths must be compatible");
        }
        if (k < 1 || k > locations.length) {
            throw new IllegalArgumentException(
                    "k cannot be less than one or greater than m");
        }
        for (int i = 0; i < labels.length; i++) {
            if (labels[i] != 0 && labels[i] != 1) {
                throw new IllegalArgumentException(
                        "label value must be either 0 or 1");
            }
        }

        m = input[0].length;
        clusters = new Clustering(locations, k);
        weakLearners = new LinkedList<WeakLearner>();
        weights = new double[labels.length];
        this.input = new int[input.length][k];
        this.labels = new int[labels.length];
        for (int i = 0; i < input.length; i++) {
            int[] reduced = clusters.reduceDimensions(input[i]);
            for (int j = 0; j < k; j++) {
                this.input[i][j] = reduced[j];
            }
            this.labels[i] = labels[i];
        }
        // default weights
        for (int i = 0; i < weights.length; i++)
            weights[i] = 1 / (double) weights.length;
    }

    // return the current weight of the ith point
    public double weightOf(int i) {
        return weights[i];
    }

    // apply one step of the boosting algorithm
    public void iterate() {
        double weightSum = 0;
        WeakLearner weakLearner = new WeakLearner(input, weights, labels);
        weakLearners.add(weakLearner);

        // weight mistakes 2x
        for (int j = 0; j < input.length; j++) {
            if (weakLearner.predict(input[j]) != labels[j]) {
                weights[j] *= 2;
            }
            weightSum += weights[j];
        }

        // renormalize weights
        for (int x = 0; x < weights.length; x++) weights[x] = weights[x] / weightSum;
    }

    // return the prediction of the learner for a new sample
    public int predict(int[] sample) {
        if (sample == null) {
            throw new IllegalArgumentException("sample cannot be null");
        }
        if (sample.length != m) {
            throw new IllegalArgumentException("sample length must be compatible");
        }
        int count0 = 0, count1 = 0;
        int[] reducedInputs = clusters.reduceDimensions(sample);
        for (int i = 0; i < weakLearners.size(); i++) {
            if (weakLearners.get(i).predict(reducedInputs) == 0) count0++;
            else count1++;
        }
        if (count0 >= count1) return 0;
        else return 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // read in the terms from a file
        DataSet training = new DataSet(args[0]);
        DataSet testing = new DataSet(args[1]);
        int k = Integer.parseInt(args[2]);
        int t = Integer.parseInt(args[3]);

        int[][] trainingInput = training.getInput();
        int[][] testingInput = testing.getInput();
        int[] trainingLabels = training.getLabels();
        int[] testingLabels = testing.getLabels();
        Point2D[] trainingLocations = training.getLocations();

        // train the model
        BoostingAlgorithm model = new BoostingAlgorithm(trainingInput, trainingLabels,
                                                        trainingLocations, k);
        for (int i = 0; i < t; i++)
            model.iterate();

        // calculate the training data set accuracy
        double trainingAccuracy = 0;
        for (int i = 0; i < training.getN(); i++)
            if (model.predict(trainingInput[i]) == trainingLabels[i])
                trainingAccuracy += 1;
        trainingAccuracy /= training.getN();

        // calculate the test data set accuracy
        double testAccuracy = 0;
        for (int i = 0; i < testing.getN(); i++)
            if (model.predict(testingInput[i]) == testingLabels[i])
                testAccuracy += 1;
        testAccuracy /= testing.getN();

        StdOut.println("Training accuracy of model: " + trainingAccuracy);
        StdOut.println("Test accuracy of model: " + testAccuracy);

        StdOut.println("weight of 2nd point: " + model.weightOf(2));
    }
}
