import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(
            Key[] a, Key key, Comparator<Key> comparator) {
        if (a == (null) || key == (null) || comparator == null) {
            throw new IllegalArgumentException("input cannot be null");
        }
        int lo = 0;
        int hi = a.length - 1;
        int current = -1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            int comp = comparator.compare(key, a[mid]);
            if (comp == 0) {
                current = mid;
                hi = mid - 1; // runs BinarySearch again on first half of arr
            }
            else if (comp < 0)
                hi = mid - 1;
            else
                lo = mid + 1;
        }
        return current;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a,
                                        Key key, Comparator<Key> comparator) {
        if (a == (null) || key == (null) || comparator == null) {
            throw new IllegalArgumentException("input cannot be null");
        }
        int lo = 0;
        int hi = a.length - 1;
        int current = -1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            int comp = comparator.compare(key, a[mid]);
            if (comp == 0) {
                current = mid;
                lo = mid + 1; // runs BinarySearch again on last half of arr
            }
            else if (comp < 0)
                hi = mid - 1;
            else
                lo = mid + 1;
        }
        return current;
    }

    // unit testing (required)
    public static void main(String[] args) {
        String[] a = { "A", "A", "C", "G", "G", "T" };
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        int index0 = BinarySearchDeluxe.firstIndexOf(a, "G", comparator);
        StdOut.println(index0);
        int index1 = BinarySearchDeluxe.lastIndexOf(a, "G", comparator);
        StdOut.println(index1);
    }
}
