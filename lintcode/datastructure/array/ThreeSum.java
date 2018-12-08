/**
57. 3Sum
Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Example
For example, given array S = {-1 0 1 2 -1 -4}, A solution set is:

(-1, 0, 1)
(-1, -1, 2)
Notice
Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ≤ b ≤ c)

The solution set must not contain duplicate triplets.
*/

/**
Solution: Sort, and finding 2 first.
Ex: [-1,0,1,2,-1,-4, 2] = (-1,0,1) (-1,-1,2) (-4,2,2)
How to Arrive:
* Thinking the brute force, 3 nested loop, from 0->n; combine with 1->n, with 2->n; O(n^3); All possible 3 subarr.
* How to optimize?
	* finding 2 nums == target would take n^2, nested loop find all possible 2 combos.
	* I know it can be optimized to just O(n), since it just requires 1 check for each elm.
	* **Key**: what if we sort them? Then we can narrow the range of 2 nums. leftmost (smallest) -> rightmost (largest)
		* By having 2 pointers, p1 in smallest, p2 in largest; Now according to smallest + largest ><= target, we can move p1 and p2 accordingly.
		* Ex: target = 1; [-3, 1, 4, 6] -3+6 = 3 > 1; p2--; This takes O(n)
* So, we can have (target - 1st num) pass down to find in twoSum
* We need to look out for **Duplications**, since question asked us for uniq answers.
	* **When cur val == prev val SKIP**, That goes for check in 3sum and 2sum function.
* Time: O(n^2);
* auxiliary space: O(1), not counting res list.
*/

import java.util.*;
import java.io.*;

public class ThreeSum {

  /**
   * @param numbers: Give an array numbers of n integer
   * @return: Find all unique triplets in the array which gives the sum of zero.
   */
  public List<List<Integer>> threeSum(int[] numbers) {
    List<List<Integer>> res = new ArrayList<>();
    if (numbers == null || numbers.length < 3) {
      return res;
    }
    Arrays.sort(numbers);
    int max = numbers[numbers.length - 1];
    
    for (int i = 0; i < numbers.length - 2; i++) {
      // avoid duplicate, same as prev
      if (i > 0 && numbers[i] == numbers[i - 1]) {
        continue;
      }
      // if curVal too small, go to next
      if (numbers[i] + 2 * max < 0) {
        continue;
      }
      // numbers[i] is the cur smallest. if numbers[i]*3 > 0;
      // then nums afterward will be too large too.
      if (numbers[i] * 3 > 0) {
        break;
      }
      twoSum(numbers, 0 - numbers[i], i + 1, res);
    }
    
    return res;
  }
  
  public void twoSum(int[] numbers, int target, int start, 
      List<List<Integer>> res) {
    if (numbers.length - start < 2) {
      return;
    }
    int end = numbers.length - 1;
    int j = end;
    int i = start;
    
    while (i < j) {
      int sum = numbers[i] + numbers[j];
      if (sum == target) {
        List<Integer> list = Arrays.asList(numbers[start - 1], numbers[i],
            numbers[j]);
        res.add(list);
        i++;
        j--;
        // skip over dupl only when find a Solution, 
        // b/c when not Solution it is skipping anyways.
        // don't worry about index out of bounce, since it is after increment
        while (i < j && numbers[i] == numbers[i - 1]) {
          i++;
        }
        while (i < j && numbers[j] == numbers[j + 1]) {
          j--;
        }
      } else if (target > sum) {
        i++;
      } else {
        j--;
      }
    }
  }
}