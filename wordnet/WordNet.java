import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class WordNet {
    private HashMap<Integer, String> idSynset; // hashmap of ids to synsets
    private HashMap<String, LinkedList<Integer>> nounId; // hashmap of nouns to ids
    private ShortestCommonAncestor sca; // ancestral path in rooted DAG


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        idSynset = new HashMap<>();
        nounId = new HashMap<>();

        // read in synsets + indices
        In syn = new In(synsets);
        while (syn.hasNextLine()) {
            String[] eachLine = syn.readLine().split(","); // split by comma
            int id = Integer.parseInt(eachLine[0]);
            String s = eachLine[1];
            String[] noun = s.split(" ");
            idSynset.put(id, s);

            // for each synset read in each noun + indicies
            for (String n : noun) {
                if (nounId.containsKey(n)) {
                    LinkedList<Integer> list = nounId.get(n);
                    list.add(id);
                    nounId.put(n, list);
                }
                else {
                    LinkedList<Integer> list = new LinkedList<Integer>();
                    list.add(id);
                    nounId.put(n, list);
                }
            }
        }
        // builds synset and hypernym digraph
        Digraph d = new Digraph(idSynset.size());
        In h = new In(hypernyms);
        while (h.hasNextLine()) {
            String[] line = h.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int hypId = Integer.parseInt(line[i]);
                d.addEdge(id, hypId);
            }
        }
        sca = new ShortestCommonAncestor(d);
    }


    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        return nounId.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("word cannot be null");
        }
        return nounId.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) {
            throw new IllegalArgumentException("nouns cannot be null");
        }
        if (!isNoun(noun1) || !isNoun(noun2)) {
            throw new IllegalArgumentException("nouns must be a WordNet noun");
        }
        // create list of synsets that each noun is in
        List<Integer> synsets1 = nounId.get(noun1);
        List<Integer> synsets2 = nounId.get(noun2);
        int idAncestor = sca.ancestorSubset(synsets1, synsets2);
        return idSynset.get(idAncestor);

    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) {
            throw new IllegalArgumentException("nouns cannot be null");
        }
        if (!isNoun(noun1) || !isNoun(noun2)) {
            throw new IllegalArgumentException("nouns must be a WordNet noun");
        }
        // create list of synsets that each noun is in
        List<Integer> synsets1 = nounId.get(noun1);
        List<Integer> synsets2 = nounId.get(noun2);
        return sca.lengthSubset(synsets1, synsets2);
    }

    // unit testing (required)
    public static void main(String[] args) {
        String synsets = args[0];
        String hypernyms = args[1];
        WordNet test = new WordNet(synsets, hypernyms);
        StdOut.println(test.nouns());
        StdOut.println(test.isNoun("Bali"));
        StdOut.println(test.distance("Bali", "Barbados"));
        StdOut.println(test.sca("Bali", "Barbados"));
    }
}
