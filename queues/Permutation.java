import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int numberOfStrings = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedStrings = new RandomizedQueue<String>();
        double n = 1.0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (randomizedStrings.size() < numberOfStrings) {
                randomizedStrings.enqueue(s);
            }
            else if (StdRandom.uniformDouble() < (numberOfStrings / n)) {
                randomizedStrings.dequeue();
                randomizedStrings.enqueue(s);
            }
            n++;
        }
        while (!randomizedStrings.isEmpty()) {
            StdOut.println(randomizedStrings.dequeue());
        }
    }
}

