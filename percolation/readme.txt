Programming Assignment 1: Percolation

Answer these questions after you implement your solution.

/* *****************************************************************************
 *  Describe the data structures (i.e., instance variables) you used to
 *  implement the Percolation API.
 **************************************************************************** */

I used a 2d boolean array to keep track of which sites were open, true indicating
the site is open, false indicating it's closed. I used two weighted union find
data structures to keep track of connected sites, and another to keep track of
backwash ie connected sites not connected to the main path of water flow.
Lastly, I used two int variables, one to keep track of number of total open
sites and one to keep track of the size of the grid.

/* *****************************************************************************
 *  Briefly describe the algorithms you used to implement each method in
 *  the Percolation API.
 **************************************************************************** */
open(): Converts site in question to 1d number, then based on location of site
use union to connect sets. I also split up row and col, and first would connect
sites based off row, then col. If site is in the top row, use union and connect site
to virtual top site set. If site is in the last row, use union and connect site
to virtual bottom site set. If row is in between, use union and connect site to
rows above and below said row, making sure to also take note of newly open site
in backwash, connecting the site to backwash sites above and below. Then, based on
col, connect site to sites left and right of site. As long as site isn't in the
leftmost col, connect site to the site to the left if open using union, and as
long as site isn't in the rightmost col, connect site to the site to the right
if open using union. Again, making sure to take into account backwash, and
connecting sites to backwash open sites.
isOpen(): Check if coordinate is a valid coordinate within grid, then access the
corresponding array element within open which will return true or false
depending on whether the site is open or not.
isFull(): Call find() twice on union find data structure representing backwash,
once on site in question and once on virtual top site
to check if they have the same root, indicating they are connected and the site
is connected to the top of the grid, so the site is filled with water.
numberOfOpenSites(): Return isntance variable openSites which corresponds to
number of open sites in the grid. This was calculated by adding one to the
varibale everytime open is called on a closed site.
percolates(): Call find() twice on weighted union data structure representing
connected sites (not backwash), once on virtual top site and virtual bottom
site and check if their roots are the same, indicating they are connected, and
therefore grid percolates.

/* *****************************************************************************
 *  First, implement Percolation using QuickFindUF.
 *  What is the largest value of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?
 *
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
2 0.002
4 0.003
8 0.005
16 0.014
32 0.057
64 0.575
128 8.776
256 133.566

150 16.15
175 29.782
200 50.522
225 80.247
202 52.438
204 54.448
208 59.351

/* *****************************************************************************
 *  Describe the strategy you used for selecting the values of n.
 **************************************************************************** */

I first started out with n = 2, and started doubling n, until I reached an n
that produced a runtime of longer than 60 seconds. Then, I ran the program again,
this time linearly increasing n but starting at the n right before the runtime
reached above 60. I then utilized the t(2N)/t(N) function, to find b, and then
used the a*n^b formuale to find a, and solved the equation
60=4.63982*10^(-8)n^(3.927845472)
to get to my answer of n = 208.


/* *****************************************************************************
 *  Next, implement Percolation using WeightedQuickUnionUF.
 *  What is the largest value of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?
 *
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
2 0.002
4 0.003
8 0.005
16 0.01
32 0.017
64 0.039
128 0.101
256 0.346
512 1.367
1024 6.621

1000 6.15
1200 12.12
1400 22.702
1600 36.594
1800 52.636
2000 71.45
1918 64.982

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

With very large trials or very large ns, the runtime of PercolationStats
gets very large.
Even at just 100 trials, with an n of 1918, it takes one minute to run the program.
If we wanted a more accurate p* by running more trials, this would take very long
to run. This program is therefore best for smaller n and lesser trials.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */




/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
