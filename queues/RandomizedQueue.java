import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Adapted from code written by Robert Sedgewick and Kevin Wayne in 2019:
// https://algs4.cs.princeton.edu/13stacks/ResizingArrayStack.java.html
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size; // size of queue
    private Item[] randomizedQueue; // array holding items of randomized queue

    // construct an empty randomized queue
    public RandomizedQueue() {
        randomizedQueue = (Item[]) new Object[2];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // resizes array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = randomizedQueue[i];
        }
        randomizedQueue = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't add null item");
        if (randomizedQueue.length == size) resize(2 * randomizedQueue.length);
        randomizedQueue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException("Queue is empty");
        int index = StdRandom.uniformInt(size);
        Item item = randomizedQueue[index];
        randomizedQueue[index] = randomizedQueue[size - 1];
        randomizedQueue[size - 1] = null;
        size--;
        if (size > 0 && size == randomizedQueue.length / 4)
            resize(randomizedQueue.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) throw new NoSuchElementException("Queue is empty");
        return randomizedQueue[StdRandom.uniformInt(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i; // current item's array index
        private Item[] copy; // will store shuffled copy of randomized queue

        // copies randomized queue to new array then shuffles new array
        public ArrayIterator() {
            i = 0;
            copy = (Item[]) new Object[size];
            for (int j = 0; j < size; j++) {
                copy[j] = randomizedQueue[j];
            }
            StdRandom.shuffle(copy);
        }

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item ret = copy[i];
            i++;
            return ret;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            test.enqueue(i);
        }
        StdOut.println(test.size());
        Iterator<Integer> iterator = test.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
        StdOut.println(test.dequeue());
        StdOut.println(test.sample());
        StdOut.println(test.isEmpty());
    }

}
