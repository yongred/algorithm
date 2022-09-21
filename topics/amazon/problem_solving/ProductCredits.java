/**
 * Let’s say that Amazon surprises customers on a periodic basis where it gives each customer some credit (X amount) that can be used to buy 2 products from a curated list of products.
 * The credit has to be used completely in one transaction and you can only buy 2 products. Come up with a solution to identify which two products would exactly match the credit.
 *
 *
 * Candidate just needs to know what a transaction is and what product is - even knowing what amazon does/interacts is not needed to complete
 * There are multiple ways to solve this, you could do brute force, you could do backpacking (a little dynamic programming), maybe even searching algorithm
 *
 * Follow up
 * FUQ (Q1): Which two products would maximize the credit utilization (does not need to exactly match) - be able to walk through this and write some code as a bar raising SDE II
 * FUQ (Q2): Generalize it for any number to buying n products (rarely there is time to discuss this or reach this point). - be able to at least explain it, if time write some code
 */

/*
This is basically Two Sums:
Qs:
- Input List of Integers + a Integer for credit: Assume yes
- There will be Two Integers Sum = Credit: yes
- Output is the 2 indexes of the List? Yes
  - Multiple ans? No, one is enough
- O(?) time and space requirement? Just get a solution for now.
- Are the List sorted? No
  - If yes, then two pointers method can achieve O(n) time.
- Duplicate numbers? No

Follow up: Closest Two Sum
- Can the sum > target? Assume yes

FUQ (Q2): Generalize it for any number to buying n products (rarely there is time to discuss this or reach this point). - be able to at least explain it, if time write some code
- This is Dynamic Programming problem, see KSum.java
- store possible combination from 0 - target, and use the previous combo to find target solution
 */

package problem_solving;

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class ProductCredits {

    /*
    Solution1: use a hashmap to store the <diffsNeeded, index>  of each elm.
    ex: credit = 10, products = [3, 9, 7, 8, 5]
    diffsMap = [{7,0}, {1,1}, {3, 2}, {3,2}, {5,4}]

    Time: O(n)
    Space: O(n)
     */
    public int[] getTwoProductsAddUpToCredit1(int[] products, int credit) {
        int[] indexesForTwoProducts = new int[2];
        Map<Integer, Integer> diffsIndexMap = new HashMap<>();

        for (int i = 0; i < products.length; i++) {
            int diff = credit - products[i];
            if (diffsIndexMap.containsKey(products[i])) {
                indexesForTwoProducts[0] = diffsIndexMap.get(products[i]);
                indexesForTwoProducts[1] = i;
                break;
            }
            diffsIndexMap.put(diff, i);
        }

        return indexesForTwoProducts;
    }

    /*
    Follow up: Two Sum Closest to Target credit
    Solution: 2 pointers
    Time: O(n Log n)
    Space: O(n)

    ex: credit = 16, products = [1, 10, 9, 2, 8]
    sort: [1, 2, 8, 9, 10]
    start=0, end=4: 1 + 10 = 11 start++
    start=1, end=4: 2 + 10 = 12 start++
    start=2, end=4: 8 + 10 = 18 end--
    start=2, end=3: 8 + 9 = 17 end--
    start=2, end=2: break
     */
    public int[] twoSumClosest(int[] nums, int target) {
        int[] numSorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(numSorted);
        int startIndex = 0;
        int endIndex = numSorted.length - 1;
        int number1 = 0;
        int number2 = 0;
        int minDiff = Integer.MAX_VALUE;

        while(startIndex < endIndex) {
            int sum = numSorted[startIndex] + numSorted[endIndex];
            int diff = Math.abs(sum - target);
            if (diff < minDiff) {
                number1 = numSorted[startIndex];
                number2 = numSorted[endIndex];
                minDiff = diff;
            }

            if (sum < target) {
                startIndex++;
            } else if (sum > target) {
                endIndex--;
            } else {
                break;
            }
        }

        return findIndexesFor(number1, number2, nums);
    }

    private int[] findIndexesFor(int number1, int number2, int[] nums) {
        int[] result = new int[] {-1, -1};
        result[0] = searchForIndexExclude(number1, nums, -1);
        result[1] = searchForIndexExclude(number2, nums, result[0]);

        Arrays.sort(result);
        return result;
    }

    private int searchForIndexExclude(int target, int[] nums, int excludedIndex) {
        for (int i = 0; i < nums.length; i++) {
            if (target == nums[i] && i != excludedIndex) {
                return i;
            }
        }
        return -1;
    }


    public static void main(String [] args) {
        ProductCredits productCredits = new ProductCredits();
        int[] products = new int[] {3, 9, 7, 8, 5};
        int credit = 10;

        int[] resultIndexes = productCredits.getTwoProductsAddUpToCredit1(products, credit);
        System.out.println(Arrays.toString(resultIndexes));

        int[] followUpInput = new int[] {1, 10, 9, 2, 8};
        int credit2 = 16;
        int[] followUpResult = productCredits.twoSumClosest(followUpInput, credit2);
        System.out.println("Follow up " + Arrays.toString(followUpResult));
    }
}
