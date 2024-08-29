Programming Assignment 7: Fraud Detection

/* *****************************************************************************
 *  Describe how you implemented the Clustering constructor
 **************************************************************************** */


/* *****************************************************************************
 *  Describe how you implemented the WeakLearner constructor
 **************************************************************************** */

Iterate through the input array k dimension times, sorting one time by x coordinate, and
another time by y coordinate. Use the indexSort method to sort the arrays. Iterate
through the input points one time, now by the opposite coordinate, and assume
the partition line is on the x/y-axis, and add up the weight of correct predictions.
Iterate through the input points one more time, this time using each input
point as a partition line, and recalculate weightedCorrected by adding to this
variable if the input point is now labelled correctly with this new partition line,
and subtract if the point is now labelled incorrectly with this line. After
recalculating weightedCorrect, check if this new value of the variable is better
than the current bestWeightedCorrect. If it is, update bestWeightedCorrect, dp, vp,
and sp to the current weightedCorrect value, dimension, coordinate, and sign. Do
this process again for the other dimensions.

/* *****************************************************************************
 *  Consider the large_training.txt and large_test.txt datasets.
 *  Run the boosting algorithm with different values of k and T (iterations),
 *  and calculate the test data set accuracy and plot them below.
 *
 *  (Note: if you implemented the constructor of WeakLearner in O(kn^2) time
 *  you should use the small_training.txt and small_test.txt datasets instead,
 *  otherwise this will take too long)
 **************************************************************************** */

      k          T         test accuracy       time (seconds)
   --------------------------------------------------------------------------



/* *****************************************************************************
 *  Find the values of k and T that maximize the test data set accuracy,
 *  while running under 10 second. Write them down (as well as the accuracy)
 *  and explain:
 *   1. Your strategy to find the optimal k, T.
 *   2. Why a small value of T leads to low test accuracy.
 *   3. Why a k that is too small or too big leads to low test accuracy.
 **************************************************************************** */


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
