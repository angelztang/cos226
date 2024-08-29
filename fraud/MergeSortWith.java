public class MergeSortWith {
    // Calculate cumulative counts of different values of target
    // At each feature value, calculate counts for each class
    public static int[][] calculateCumulativeCounts(int[] features, int[] labels, int numClasses) {
        int[][] counts = new int[features.length][numClasses * 2];

        // Initialize cumulative counts
        for (int i = 0; i < features.length; i++) {
            if (i == 0) {
                counts[i][labels[i]]++;
            }
            else {
                // Copy counts from the previous row
                System.arraycopy(counts[i - 1], 0, counts[i], 0, numClasses * 2);
                // Increment the count for the current label
                counts[i][numClasses + labels[i]]++;
            }
        }

        return counts;
    }

    public static void main(String[] args) {
        // Example data
        int[] features = { 1, 2, 3, 4, 5 };
        int[] labels = { 0, 1, 0, 0, 1 };
        int numClasses = 2;

        int[][] counts = calculateCumulativeCounts(features, labels, numClasses);

        // Display cumulative counts
        for (int i = 0; i < features.length; i++) {
            for (int j = 0; j < numClasses * 2; j++) {
                System.out.print(counts[i][j] + " ");
            }
            System.out.println();
        }
    }
}
