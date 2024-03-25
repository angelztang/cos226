Programming Assignment 2: Deques and Randomized Queues


/* *****************************************************************************
 *  Explain briefly how you implemented the randomized queue and deque.
 *  Which data structure did you choose (array, linked list, etc.)
 *  and why?
 **************************************************************************** */
I used a linked list to implement the deque class. I kept track of both the first
and last node of the list, in order to make implementing addFirst, addLast,
removeFirst,
and removeLast easier. I used a linked list because it has no size limitation,
while with an array, if you kept adding elements you would have to also write a
resizing array method which would increase run time substantially.

I used an array to implement the randomized queue, and stored the items in the
array. I used an array because it is easier to access all elements of the array,
since each element has an index, while if I were to use a linked list for
randomized queue, I would have to traverse the whole list if I wanted to access
an element in the middle of the list.

/* *****************************************************************************
 *  How much memory (in bytes) do your data types use to store n items
 *  in the worst case? Use the 64-bit memory cost model from Section
 *  1.4 of the textbook and use tilde notation to simplify your answer.
 *  Briefly justify your answers and show your work.
 *
 *  Do not include the memory for the items themselves (as this
 *  memory is allocated by the client and depends on the item type)
 *  or for any iterators, but do include the memory for the references
 *  to the items (in the underlying array or linked list).
 **************************************************************************** */

Randomized Queue:   ~  ~32n  bytes
public class RandomizedQueue {
    randomizedQueue = (Item[]) new Object[2];    8 bytes x array length
    size = 0;
    }
~8n when array full (array length = n)
~32n when array 1/4 full (array length = 4n)
    Therefore, the worst case is right before resizing down, and the array
    is 1/4 full, so it has an array length of 4n. 4nx8=32n

Deque:              ~  ~48n  bytes

private class Node { 16 bytes (object overhead)
                    + 8 bytes (non static nested class extra overhead)
        private Item item; 8 bytes (reference to Item)
        private Node next; 8 bytes (reference to Node)
        private Node prev; 8 bytes (reference to Node)
    }

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
N/a

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */



/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
