import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Outcast {

    private final WordNet wordnet; // store wordnet

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxdistance = 0;
        int index = 0;
        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;
            for (int j = 0; j < nouns.length; j++) {
                distance += wordnet.distance(nouns[i], nouns[j]);
            }
            if (distance > maxdistance) {
                maxdistance = distance;
                index = i;
            }
        }
        return nouns[index];
    }

    // test client (see below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}
