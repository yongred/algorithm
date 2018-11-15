/**
4Sum:

Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target?

Find all unique quadruplets in the array which gives the sum of target.

Example
Given array S = {1 0 -1 0 -2 2}, and target = 0. A solution set is:

(-1, 0, 0, 1)
(-2, -1, 1, 2)
(-2, 0, 0, 2)
*/

import java.util.*;
import java.io.*;

public class FourSum {
    /*
     * @param nums: Give an array
     * @param target: An integer
     * @return: Find all unique quadruplets in the array which gives the sum of zero
     Solution:
     -Sort from small to large
     -in 4Sum, check for 3Sum, in 3Sum check for 2Sum, and add to res;
     -ex: [-2,-1,0,0,1,2] target=0; 4Sum(-2, target=0) (0 - -2) => 3Sum(-1, target=2) (2- -1) => 2Sum(target=3) Found(-2,-1,1,2);

     Time: O(n^3); Space: O(1);
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> res = new ArrayList<List<Integer>>();

        if (nums.length < 4)
            return res;

        Arrays.sort(nums); // sort first for easy search
        int max = nums[nums.length - 1]; //last element is the max;
        //check if target is out of range, target not in [min - max];
        if (nums[0] * 4 > target || max * 4 < target)
            return res;

        for (int i = 0; i < nums.length; i++) {
            //check for dulicates; ex: [1,1,1,2,2] tar=5; there will be 2 (1,1,1,2) matches; avoid that;
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            //check if this num is too small to combine into target. ex: max=3; target=20; nums[i]=2; 2+3*3 = 11 < 20;
            if (nums[i] + (3 * max) < target)
                continue;
            //nums[i] is too large for target, since all nums after will be larger than nums[i], no point to check.
            if (4 * nums[i] > target)
                break;
            //if 4nums[i] == target, all 4*nums after is either >= target.
            if (4 * nums[i] == target) {
                //check if the 3 nums after cur i, is == nums[i], if so we found a match. ex: (0,0,1,1,1,1); target=4; index: 2-5;
                if (i + 3 < nums.length && nums[i + 3] == nums[i])
                    res.add(Arrays.asList(nums[i], nums[i], nums[i], nums[i]));
                break;
            }
            //do the same find 3 nums add up to target- curNum;
            threeSum(nums, target - nums[i], i + 1, nums.length - 1, nums[i], res);
        }

        return res;
    }

    /*
     * @param nums: array.
     * @param target: finding 3 nums add up to target
     * @param start & end: start end index;
     * @param firstNum: num in the 4Sum;
     * @param res: res list to add matches;
    */
    public static void threeSum(int[] nums, int target, int start, int end, int firstNum, List<List<Integer>> res) {
        //if only 2 elements left
        if (start + 1 >= end)
            return;

        int max = nums[end];

        //check if target is out of range, target not in [min - max];
        if (nums[start] * 3 > target || max * 3 < target)
            return;

        for (int i = start; i < end; i++) {
            //check for dulicates; ex: [1,1,1,2,2] tar=4; there will be 2 (1,1,2) matches; avoid that;
            if (i > start && nums[i] == nums[i - 1])
                continue;
            //check if this num is too small to combine into target. ex: max=4; target=20; nums[i]=2; 2+4*2 = 10 < 20;
            if (nums[i] + (2 * max) < target)
                continue;
            //nums[i] is too large for target, since all nums after will be larger than nums[i], no point to check.
            if (3 * nums[i] > target)
                break;
            //if 3*nums[i] == target, all 3*nums after is either >= target.
            if (3 * nums[i] == target) {
                //check if the 2 nums after cur i, is == nums[i], if so we found a match. ex: (0,0,1,1,1); target=3; index: 2-4;
                if (i + 2 < nums.length && nums[i + 2] == nums[i])
                    res.add(Arrays.asList(firstNum, nums[i], nums[i], nums[i]));
                break;
            }
            //find 2 nums add up to target- curNum;
            twoSum(nums, target - nums[i], i + 1, end, firstNum, nums[i], res);
        }

    }

    /*
    * @param nums: array.
    * @param target: finding 3 nums add up to target
    * @param start & end: start end index;
    * @param firstNum: num in the 4Sum;
    * @param secNum: num in the 3Sum;
    * @param res: res list to add matches;
    */

    public static void twoSum(int[] nums, int target, int start, int end, int firstNum, int secNum, List<List<Integer>> res) {

        //if only 1 elements left
        if (start >= end)
            return;

        int max = nums[end];
        int i = start, j = end;
        while (i < j) {
            int sum = nums[i] + nums[j];
            if (sum == target) {
                res.add(Arrays.asList(firstNum, secNum, nums[i], nums[j]));

                int prev = nums[i];
                i++;
                //avoid duplicates
                while (i < j && nums[i] == prev) i++;

                prev = nums[j];
                j--;
                //avoid duplicates
                while (i < j && nums[j] == prev) j--;
            } else if (sum < target) {
                i++;
            } else if (sum > target) {
                j--;
            }
        }
    }

    public static void main(String[] args) {
        int [] arr = { -2, -3, -4, -5, -100, 99, 1, 4, 4, 4, 5, 1, 0, -1, 2, 3, 4, 5};
        List<List<Integer>> res = fourSum(arr, 0);

        for (List<Integer> list : res) {
            Integer [] listArr = list.toArray(new Integer[list.size()]);
            System.out.println(Arrays.toString(listArr));
        }
    }

}