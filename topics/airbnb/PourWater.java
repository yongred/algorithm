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
Ex: [2,0,2,1,2,2,3,1] drops=4, loc=6;

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

Print Graph:
* To Print graph, we need to distinguish between Ground and Water.
* To do that we use 2 Arrays, 1 for original heights and 1 for waters dropped.
* When drop water at a pos, we increments waters array's pos.
* We pass in heighers and waters to Print.
* B/c of the function print and println goes from top to bottom, line by line, we will need to print in that order.
* To print Level by Level, we found the MaxHeight of originHeight + water;
* From the maxHeight -> 0 level:
  * Loop for each Col in originHeights and Waters:
    * First check for highest waterLevel placement, if curLevel > originHeight(ground) but <= originHeight + water, then we know level is at water, So print 'W';
    * If curLevel <= originHeight, we know is at Ground level, Print '#';
    * If neither: Then there is no Ground or Water at this position. Then it is " " empty;

------------

Follow up: no walls on 2 sides, Kept waters in graph, w/o walls. 
If no depression lower than location, water will fall out of graph. So cur W will not print.
那把数组两边加个-1，然后流到两边也不增加高度不就OK了吗？会有什么corner case呢？
比如input是[3 2 2 1], 然后我处理一下input变成[-1, 3, 2, 2, 1, -1]
你说的往左流没有出界，往右流出界是在index 2倒水的情况吗？
先向左找连续的非递增的点，发现是2，和倒水点高度一样。再向右找连续的非递增的点，发现是-1，直接返回，不改变-1的高度。
请问这样还有什么corner case没覆盖到吗？

* When no walls on the side, if no higher block or hole in between loc -> left and right, then water will fall out of range.
Ex: [3,2,2,1]; loc=2; <- left has 3 blocked, but no hole; go right -> rightSight have no block after 1, water will fall out of range b/c 2->1->Out; out is -INF, a deepest hole.
* To simulates this condition, we can change the input heights into having 2 -#s on left and right; 
* When a water falls into -#s, we do not drop water on any pos. Otherwise stays the same.
* So we make [3 2 2 1] => [-1, 3, 2, 2, 1, -1];
* Keep a var dropLoc = loc; the final pos for drop Water.
* We go left first, keep a var leftLowest, 
* If found a hole in between like (2,1),2,3; then fill in the hole 2,2,2,3 and return; dropLoc = index;
* if found a Block higher like (3,2,)|2,2,1 but no hole; (dropLoc == loc) we go Right to find a hole;
* If found leftLowest = -1 Or dropLoc = idx0, means water will drop OutOfRange on left; we also go Right to find a hole.
* Now go Right: keep a var rightLowest;
* If right have a hole in between like 2,2,(1,2) then fills it. => 2,2,2,2; dropLoc = index;
* If rightLowest = -1 Or dropLoc = lastIndex, falls out of range, do nothing. return; Like 2,3,3,(2,2)
* If rightSide found a Block then dropLoc no change, = what we found on LeftSide: either -1 or loc;
* Finished checking left and right, if dropLoc != 0 And dropLoc != lastIndex (-1s, OutOfBound), then we dropWater on dropLoc;

*/

import java.io.*;
import java.util.*;

public class PourWater {
  
  /**
   * @param heights: the height of the terrain
   * @param V: the units of water
   * @param K: the index
   * @return: how much water is at each index
   */
  public static void pourWater(int[] heights, int drops, int loc) {
    // dropped locs, # of times.
    int[] waters = new int[heights.length];
    // drop water V times on K loc.
    for (int i = 0; i < drops; i++) {
      drop(heights, waters, loc);
    }
    print(heights, waters);
  }
  
  public static void drop(int[] heights, int[] waters, int loc) {
    // check left for continuous non-increasing, lower.
    int lowest = heights[loc] + waters[loc];
    int dropLoc = loc;
    for (int i = loc; i >= 0; i--) {
      // higher level, lowest non-increasing is curLevel.
      if (heights[i] + waters[i] > lowest) {
        break;
      } else if (heights[i] + waters[i] < lowest) {
        // found lower
        lowest = heights[i] + waters[i];
        dropLoc = i;
      }
    }
    // check if dropLoc changed.
    if (dropLoc != loc) {
      // found dropLoc, drop water.
      waters[dropLoc]++;
      return;
    }
    // not found on left, find right.
    for (int i = loc; i < heights.length; i++) {
      if (heights[i] + waters[i] > lowest) {
        break;
      } else if (heights[i] + waters[i] < lowest) {
        lowest = heights[i] + waters[i];
        dropLoc = i;
      }
    }
    // drop water on dropLoc.
    waters[dropLoc]++;
  }
  
  /**
   * Print from highest level to lowest level; level by level, that is how printLn works.
   */
  public static void print(int[] heights, int[] waters) {
    // print level by level. Find max height
    int maxHeight = 0;
    for (int i = 0; i < heights.length; i++) {
      maxHeight = Math.max(maxHeight, heights[i] + waters[i]);
    }
    // loop level from top to bottom
    for (int l = maxHeight; l >= 0; l--) {
      // check if is water or ground. by compare total height with groundHeight.
      for (int i = 0; i < heights.length; i++) {
        // above ground, below water, print W.
        if (l > heights[i] && l <= heights[i] + waters[i]) {
          System.out.print("W");
        } else if (l <= heights[i]) {
          // on the ground level. print #
          System.out.print("#");
        } else {
          // l > heights[i] + waters[i]; above water level, empty spot.
          // or if heights[i] + waters[i] == -#, lower than ground level (No Walls, out of range), empty.
          System.out.print(" ");
        }
      }
      System.out.println();
    }

    System.out.println();
  }

  /**
   * Follow up: no walls on 2 sides, Kept waters in graph, w/o walls. 
   * If no depression lower than location, water will fall out of graph. So cur W will not print.
    那把数组两边加个-1，然后流到两边也不增加高度不就OK了吗？会有什么corner case呢？
    比如input是[3 2 2 1], 然后我处理一下input变成[-1, 3, 2, 2, 1, -1]
    你说的往左流没有出界，往右流出界是在index 2倒水的情况吗？
    先向左找连续的非递增的点，发现是2，和倒水点高度一样。再向右找连续的非递增的点，发现是-1，直接返回，不改变-1的高度。
    请问这样还有什么corner case没覆盖到吗？
   */
  public static void pourWaterNoWalls(int[] heights, int drops, int loc) {
    // heights w/ -1s on the sides.
    int[] holeHeights = new int[heights.length + 2];
    holeHeights[0] = -1;
    holeHeights[holeHeights.length - 1] = -1;
    // [3 2 2 1] => [-1, 3, 2, 2, 1, -1]
    for (int i = 0; i < heights.length; i++) {
      holeHeights[i + 1] = heights[i];
    }
    // dropped locs, # of times.
    int[] holeWaters = new int[holeHeights.length];
    // drop water V times on K loc.
    for (int i = 0; i < drops; i++) {
      dropNoWalls(holeHeights, holeWaters, loc + 1);
    }
    print(holeHeights, holeWaters);
  }

  public static void dropNoWalls(int[] holeHeights, int[] holeWaters, int loc) {
    // find lowest non-increasing hole's height.
    int leftLowest = holeHeights[loc] + holeWaters[loc];
    int dropLoc = loc;
    // left search
    for (int i = loc - 1; i >= 0; i--) {
      // if a higher level block encountered, no hole.
      if (holeHeights[i] + holeWaters[i] > leftLowest) {
        break;
      } else if (holeHeights[i] + holeWaters[i] < leftLowest) {
        // found a depression.
        leftLowest = holeHeights[i] + holeWaters[i];
        dropLoc = i;
      }
    }
    // check if found a hole within the valley range. 
    // -# or loc=0 means fall out of range. dropLoc = loc means there is a block on left but no hole.
    if (dropLoc > 0 && dropLoc != loc) {
      holeWaters[dropLoc]++;
      return;
    }
    // search right lowest;
    int rightLowest = holeHeights[loc] + holeWaters[loc];
    // right search
    for (int i = loc + 1; i < holeHeights.length; i++) {
      if (holeHeights[i] + holeWaters[i] > rightLowest) {
        break;
      } else if (holeHeights[i] + holeWaters[i] < rightLowest) {
        // found a depression.
        rightLowest = holeHeights[i] + holeWaters[i];
        dropLoc = i;
      }
    }
    // check if water flow out of range, if not drop water on dropLoc.
    if (dropLoc > 0 && dropLoc < holeHeights.length - 1) {
      holeWaters[dropLoc]++;
    }
    
  }

  public static void main(String[] args) {
    int[] heights = new int[] {0,1,1,2,1,2,0};
    int drops = 5;
    int loc = 3;
    pourWater(heights, drops, loc);
    pourWaterNoWalls(heights, drops, loc);
  }
}