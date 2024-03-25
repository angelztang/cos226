import java.util.Arrays;
import java.util.Comparator;

public class Term implements Comparable<Term> {
    private String query; // content of Term
    private long weight; // weight value of Term

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0)
            throw new IllegalArgumentException("Illegal argument");
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term a, Term b) {
            return Long.compare(b.weight, a.weight);
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        return new PrefixOrder(r);
    }

    private static class PrefixOrder implements Comparator<Term> {
        private int prefixOrderR; // length of prefix

        // initializes prefix order to int r
        public PrefixOrder(int r) {
            if (r < 0)
                throw new IllegalArgumentException(
                        "R must be greater or equal to 0");
            prefixOrderR = r;
        }

        public int compare(Term a, Term b) {
            int shortestWord = Math.min(a.query.length(), b.query.length());
            int iLimit = Math.min(shortestWord, prefixOrderR);
            for (int i = 0; i < iLimit; i++) {
                if (a.query.charAt(i) < b.query.charAt(i))
                    return -1;
                else if (a.query.charAt(i) > b.query.charAt(i))
                    return 1;
            }
            if (b.query.length() < prefixOrderR
                    && b.query.length() < a.query.length())
                return 1;
            else if (a.query.length() < prefixOrderR
                    && a.query.length() < b.query.length())
                return -1;
            else
                return 0;
        }
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return weight + "\t" + query;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term[] test = new Term[4];
        test[0] = new Term("The Apartment", 1960);
        test[1] = new Term("The Sound Of Music", 1965);
        test[2] = new Term("North By Northwest", 1959);
        test[3] = new Term(" ", 1955);
        Arrays.sort(test, byReverseWeightOrder());
        for (Term movie : test)
            System.out.println(movie);
        Arrays.sort(test, byPrefixOrder(4));
        for (Term movie : test)
            System.out.println(movie);
        System.out.println(test[0].compareTo(test[1]));
    }

}

