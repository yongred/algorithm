/**
479. Second Max of Array
Find the second max number in a given array.

Example
Given [1, 3, 2, 4], return 3.

Given [1, 2], return 1.

Notice
You can assume the array contains at least two numbers.
*/

/**
Solution 1: use idea of selection sort, stop iteration after 2.
* find the largest store in nums[0],
* find the sec largest store in nums[1].
* Time: T(2n) = O(n);
* Space: O(1)

Solution 2: use 2 variables, Max and secMax.
* when find new Max, secMax = max, max = newMax;
* **Key**: don't forget when you find (max >num > secMax); Update secMax = num;
	* Ex: [5,8,6]; Max = 8; update secMax = 5; in last pos, 6 < 8, but 6 > 5(secMax);
* Time: O(n) = T(n);
* Space: O(1);
*/

import java.util.*;
import java.io.*;

public class SecondMax {
    /**
     * @param nums: An integer array
     * @return: The second max number in the array.
     * Solution 1: use selection sort idea.
     * Time: T(2n) = O(n)
     * Space: O(1)
     */
    public int secondMax(int[] nums) {
      for (int i = 0; i < 2; i++) {
        int maxIndex = i;
        for (int j = i + 1; j < nums.length; j++) {
          if (nums[j] > nums[maxIndex]) {
            maxIndex = j;
          }
        }
        int temp = nums[i];
        nums[i] = nums[maxIndex];
        nums[maxIndex] = temp;
      }
      
      return nums[1];
    }

    /**
     * @param nums: An integer array
     * @return: The second max number in the array.
     * Solution: 2 vars store max and secMax
     * Time: O(n) = T(N);
     * Space: O(1)
     */
    public int secondMax2(int[] nums) {
      int max = Integer.MIN_VALUE;
      int secMax = Integer.MIN_VALUE;
      for (int i = 0; i < nums.length; i++) {
        if (nums[i] > max) {
          secMax = max;
          max = nums[i];
        } else if (nums[i] > secMax) {
          secMax = nums[i];
        }
      }
      return secMax;
    }
}