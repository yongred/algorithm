/**
755. Pour Water
We are given an elevation map, heights[i] representing the height of the terrain at that index. The width at each index is 1. After V units of water fall at index K, how much water is at each index?

Water first drops at index K and rests on top of the highest terrain or water at that index. Then, it flows according to the following rules:

If the droplet would eventually fall by moving left, then move left.
Otherwise, if the droplet would eventually fall by moving right, then move right.
Otherwise, rise at it's current position.
Here, "eventually fall" means that the droplet will eventually be at a lower level if it moves in that direction. Also, "level" means the height of the terrain plus any water in that column.
We can assume there's infinitely high terrain on the two sides out of bounds of the array. Also, there could not be partial water being spread out evenly on more than 1 grid block - each unit of water has to be in exactly one block.

Example
Example 1:

Given: heights = [2,1,1,2,1,2,2], V = 4, K = 3
Return: [2,2,2,3,2,2,2]
Explanation:
#       #
#       #
##  # ###
#########
 0123456    <- index

The first drop of water lands at index K = 3:

#       #
#   w   #
##  # ###
#########
 0123456    

When moving left or right, the water can only move to the same level or a lower level.
(By level, we mean the total height of the terrain plus any water in that column.)
Since moving left will eventually make it fall, it moves left.
(A droplet "made to fall" means go to a lower height than it was at previously.)

#       #
#       #
## w# ###
#########
 0123456    

Since moving left will not make it fall, it stays in place.  The next droplet falls:

#       #
#   w   #
## w# ###
#########
 0123456  

Since the new droplet moving left will eventually make it fall, it moves left.
Notice that the droplet still preferred to move left,
even though it could move right (and moving right makes it fall quicker.)

#       #
#  w    #
## w# ###
#########
 0123456  

#       #
#       #
##ww# ###
#########
 0123456  

After those steps, the third droplet falls.
Since moving left would not eventually make it fall, it tries to move right.
Since moving right would eventually make it fall, it moves right.

#       #
#   w   #
##ww# ###
#########
 0123456  

#       #
#       #
##ww#w###
#########
 0123456  

Finally, the fourth droplet falls.
Since moving left would not eventually make it fall, it tries to move right.
Since moving right would not eventually make it fall, it stays in place:

#       #
#   w   #
##ww#w###
#########
 0123456  

The final answer is [2,2,2,3,2,2,2]:

    #    
 ####### 
 ####### 
 0123456 
Example 2:

Given: heights = [1,2,3,4], V = 2, K = 2
Return: [2,3,3,4]
Explanation:
The last droplet settles at index 1, since moving further left would not cause it to eventually fall to a lower height.
Example 3:

Given: heights = [3,1,3], V = 5, K = 1
Return: [4,4,4]
Notice
1.heights will have length in [1, 100] and contain integers in [0, 99].
2.V will be in range [0, 2000].
3.K will be in range [0, heights.length - 1].
*/

/**
Solution: Search left for lowest non-increasing level. If not found, search right.
Ex: [2,0,2,1,2,2,3,1] V=4, K=6;

            -
- w - w - -   w
  w -         -
  -
0 1 2 3 4 5 6 7

How to Arrive:
* We are basically finding lowest height in continuous non-increasing subarr of either left [0->k] or right [k->n-1];
* Ex: [0,2,1,2,2(k),2,3] k=4; Left non-increasing: [2,1,2,2(k)] lowest height is 1. Right non-increasing: [2(k),2], lowest is 2(k) itself.
* So search left first, find Lowest Height in continuous non-increasing subarr.
* If Not found on Left, DropIndex is still K. Search right for Lowest Height in continuous non-increasing subarr.
* Increment the arr[dropIndex], meaning drop the water.
* Search (drop) for V times;
* Time: (V * n);
* Space: O(1);
*/
public class PourWater {

  /**
   * @param heights: the height of the terrain
   * @param V: the units of water
   * @param K: the index
   * @return: how much water is at each index
   * Solution: Search left for lowest non-increasing level. If not found, search right.
   * Time: (V * n);
	 * Space: O(1);
   */
  public int[] pourWater(int[] heights, int V, int K) {
    for (int i = V; i > 0; i--) {
      drop(heights, K);
    }
    return heights;
  }
  
  public void drop(int[] heights, int K) {
    int dropIndex = K;
    // search left for a non-increasing lowest pos.
    for (int i = K - 1; i >= 0; i--) {
      // stop at higher left, compare with rightside.
      if (heights[i] > heights[i + 1]) {
        break;
      }
      // find a continuous lower level.
      if (heights[i] < heights[dropIndex]) {
        dropIndex = i;
      }
    }
    // check if left have find a drop point.
    if (dropIndex == K) {
      // search right
      for (int i = K + 1; i < heights.length; i++) {
        // compare with leftside, stop at higher.
        if (heights[i] > heights[i - 1]) {
          break;
        }
        if (heights[i] < heights[dropIndex]) {
          dropIndex = i;
        }
      }
    }
    // increment droped pos.
    heights[dropIndex]++;
  }
  
  
}