/**
362. Sliding Window Maximum
Given an array of n integer with duplicate number, and a moving window(size k), move the window at each iteration from the start of the array, find the maximum number inside the window at each moving.

Example
For array [1, 2, 7, 7, 8], moving window size k = 3. return [7, 7, 8]

At first the window is at the start of the array like this

[|1, 2, 7| ,7, 8] , return the maximum 7;

then the window move one step forward.

[1, |2, 7 ,7|, 8], return the maximum 7;

then the window move one step forward again.

[1, 2, |7, 7, 8|], return the maximum 8;

Challenge
o(n) time and O(k) memory
*/

/**
Solution: use deque(double ended queue), store current window indexes, remove useless indexes.

How to Arrive:
* Using brute force, loop n elms, inner loop k time to find max;
* To do better, we can use a datastructure to store prev window.
*  What information do we need? StartIndex, endIndex, Max curWindow.
*  **Key**: What is useless? prev window nums, nums < curNum
	*  ex: [9,|6,3,5| ...] 9 is prev window, remove. 3 is useless b/c 5 is larger, so 3 won't have any chance to become largest in a window. 6 is the curMax.
	*  So we will need a datastructure that can **remove element from both head and tail**. **Deque** can do that.
  *  Problem: if we remove elements smaller than cur, how do we know if there is still prev window nums in deque?
		*  ex:  [9,|6,3,5| ...]  9 is still in deque, we need to remove it.
		*  We can **store Indexes in deque** instead of vals. That way when a index is out of bounce (<= endInd - k), remove it.
* **Key** b/c we removed useless nums from prev, and <= cur; The 1st head num on deque is always the largest of cur window.
* Time: O(n); Loop through nums once, T(n), The total remove and add operations to deque is no more than 1 time for each num. That is T(2n);
	* So worst case T(2N) == O(n);
* Auxiliary Space: O(k); Only counts the deque size; (k); Not counting res size;
*/

import treepackage.*;
import java.util.*;
import java.io.*;

public class SlidingWindowMax {
  /**
   * @param nums: A list of integers.
   * @param k: An integer
   * @return: The maximum number inside the window at each moving.
   */
  public List<Integer> maxSlidingWindow(int[] nums, int k) {
    List<Integer> res = new ArrayList<>();
    if (nums.length == 0) {
      return res;
    }
    // keep the indexes of current window. Use index b/c we need to know the
    // startIndex. So we can remove next time.
    Deque<Integer> deque = new ArrayDeque<>();
    int index = 0;
    // init first window.
    while (index < k && index < nums.length) {
      // keep the largest on the head. Newest add to the tail 
      while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[index]) {
        deque.removeLast();
      }
      deque.addLast(index);
      index++;
    }
    // add head (largest in first window) to res.
    res.add(nums[deque.peekFirst()]);
    // sec window to last.
    for (int j = k; j < nums.length; j++) {
      // remove indexes in prev window. j is endInd, - k == startInd.
      if (!deque.isEmpty() && deque.peekFirst() <= j - k) {
        deque.removeFirst();
      }
      // any prev smaller than cur num, is useless. we only need largest in 
      // cur window.
      while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[j]) {
        deque.removeLast();
      }
      deque.add(j);
      // add head (largest in window) to res.
      res.add(nums[deque.peekFirst()]);
    }
    
    return res;
  }
}