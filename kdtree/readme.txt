Programming Assignment 5: K-d Trees


/* *****************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **************************************************************************** */
In order to implement the 2d-tree data structure, we used a Node data type
that contained a lb (left/bottom subtree) node and
a rt (right/top subtree) node to represent the 2-d tree
structure. We also utilized the following instance variables: Point2D p (the key
of the key-value pair), Value val, RectHV rect (rectangle corresponding to 
this node),
and int size (the amount of nodes in the subtree).


/* *****************************************************************************
 *  Describe your method for range search in a k-d tree.
 **************************************************************************** */
Our method of range search takes in 3 arguments. The arguments include
a node x, rect which represents the rectangle that we are trying to find
the range for, and a stack to store the points
that are within the range of the rectangle. The method recursively traverses
through the k-d tree by starting with node x and checking if the rect contains
the key of the node, if it does it
pushes x.p (point of x) onto the stack. If not the stack is
just returned. The method then goes through x.lb and x.rt (the left and right
children) recursively and continues pushing as long as the rectangle
contains the key of the node. At the very end, the stack is returned.

/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a k-d tree.
 **************************************************************************** */
Our method takes in a node x (node of k-d tree), a Point key (query to try and
find nearest point), and a Point min (stored closest point). If x is null, null
is returned. Otherwise, the method finds the distance between key and the key of
x (x.p). It then compares this with the distance btwn key and min. If
the distance of x.p to key is less than its distance to min, min is
updated to now be x.p.

If the right/left subtree exists and key (the query point) is within the bounds
of either the left subtree rectangle or the right subtree rectangle, then our
method recursively moves through that subtree. If a point is found closer to the
query point key, then min is updated to be that point.

If key is not within the
bounds of the rectangle, our method looks to see if it should continue
exploring the branch or prune it.
If the distance from key to the subtree rectangle is less than key to min,
there is a possibility of a closer point in the subtree. Thus, the method
continues its exploration in that subtree. The distance to the left and right
subtrees' rectangles determines how the method chooses exploring the subtrees, by
choosing the one that is closer to the key (our query point).

Within the recursion,
the lbOutRect node is used to represent a subtree that contains a possible closer
point to key than the current min. If key is not contained within the left
subtree (x.lb) rectangle and the distance from key to the left subtree's
rectangle is less than the distance from key to min, than the left subtree
possibly might have a point that could update the min. Thus, lbOutRect is set to
x.lb to further explore the left subtree. The rtOutRect node operates under the
same way except now for the right subtree. The same steps are used as with
the third node. Our method then compares the distance from both the third and
the rtOutRect node to key with the distance from key to min. The subtree that
contains a possible closer point is explored first.

Finally, the method returns the final min after searching through the k-d tree.


/* *****************************************************************************
 *  How many nearest-neighbor calculations can your PointST implementation
 *  perform per second for input1M.txt (1 million points), where the query
 *  points are random points in the unit square?
 *
 *  Fill in the table below, rounding each value to use one digit after
 *  the decimal point. Use at least 1 second of CPU time. Do not use -Xint.
 *  (Do not count the time to read the points or to build the 2d-tree.)
 *  (See the checklist for information on how to do this)
 *
 *  Repeat the same question but with your KdTreeST implementation.
 *
 **************************************************************************** */


                 # calls to         /   CPU time     =   # calls to nearest()
                 client nearest()       (seconds)        per second
                ------------------------------------------------------
PointST:          100                   4.533              22.06 ~ 22

KdTreeST:         1000000               2.188              457038.39 ~ 457038

Note: more calls per second indicates better performance.

/* *****************************************************************************
 *  Suppose you wanted to add a method numberInRange(RectHV rect) to your
 *  KdTreeST, which should return the number of points that are inside rect
 *  (or on the boundary), i.e. the number of points in the iterable returned by
 *  calling range(rect).
 *
 *  Describe a pruning rule that would make this more efficient than the
 *  range() method. Also, briefly describe how you would implement it.
 *
 *  Hint: consider a range search. What can you do when the query rectangle
 *  completely contains the rectangle corresponding to a node?
 **************************************************************************** */
A pruning rule that would make this method more efficient is when the argument 
rectangle
completely contains the rectangle corresponding to a node, the method
assumes that all points of the node's subtree are contained by the
query rectangle. The method would then count the points in the node and add
that to the total count. Then, the subtree is pruned as it is already
accounted for under this pruning rule. This would then be more efficient
than the range() method. I would implement this by first starting with the
kd-tree's root node. If the rectangle corresponding to the current node is
completely within the query rectangle, increment an int count by the number of
nodes in the subtree of the current node (size of subtree). If it does not
than use the normal algorithim to recurse through subtrees that intersect
with the query rectangle.

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
none


/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */
none



/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on  how helpful the class meeting was and on how much you learned
 * from doing the assignment, and whether you enjoyed doing it.
 **************************************************************************** */
