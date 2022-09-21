/**
56. Two Sum
Given an array of integers, find two numbers such that they add up to a specific target number.

The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are zero-based.

Example
numbers=[2, 7, 11, 15], target=9

return [0, 1]

Challenge
Either of the following solutions are acceptable:

O(n) Space, O(nlogn) Time
O(n) Space, O(n) Time
Notice
You may assume that each input would have exactly one solution
*/

public class TwoSum {
    /**
     * @param numbers: An array of Integer
     * @param target: target = numbers[index1] + numbers[index2]
     * @return: [index1, index2] (index1 < index2)
     * Solution: using HashMap to store diffs to find in later #s.
     * ex: [-1,3,7,-4,8] target = 3;
     * Create hash for all diffs needed [4,0,-4,7,-5] as we loop through
     * Check if nums[i] is in diffs. If is, we find our pair.
     * Time: O(N) since hash check is O(1); Space: O(N);
     */
    public int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> diffs = new HashMap<Integer, Integer>();
        int [] ans = new int[2];
        
        for (int i=0; i < numbers.length; i++)
        {
            if (diffs.containsKey(numbers[i]))
            {
                ans[0] = diffs.get(numbers[i]);
                ans[1] = i;
                break;
            }
            else
            {
                diffs.put(target - numbers[i], i);
            }
        }
        return ans;
    }

    /**
     * @param numbers: An array of Integer
     * @param target: target = numbers[index1] + numbers[index2]
     * @return: [index1, index2] (index1 < index2)
     * Solution: using 2 pointers solution.
     * Sort the array first.
     * With 2 indexes approach from opposite sites.
     * check numsSorted[left] + numsSorted[right] is > < = to target.
     *      if sum > target, right--; to decrease sumVal
     *      if sum < target, left++; to increase sumVal
     *      if == then we find the 2 vals
     * now loop through the nums and find 2 vals' index.
     *
     * Space: O(n); Time: T(NLgN) + T(N) = O(NlgN); 
     */
    public int[] twoSum2pointers(int[] numbers, int target) {
        int [] numsSorted = Arrays.copyOf(numbers, numbers.length);
        Arrays.sort(numsSorted);
        int [] ans = new int[2];
        int leftInd = 0, rightInd = numbers.length - 1;
        int val1 = 0, val2 = 0;
        
        while(leftInd < rightInd)
        {
            if(numsSorted[leftInd] + numsSorted[rightInd] < target)
                leftInd++;
            else if(numsSorted[leftInd] + numsSorted[rightInd] > target)
                rightInd--;
            else
            {
                val1 = numsSorted[leftInd];
                val2 = numsSorted[rightInd];
                break;
            }
        }
        //now loop through the nums and find 2 vals' index.
        if(val1 != val2)
        {
            for (int i=0; i< numbers.length; i++)
            {
                if(numbers[i] == val1)
                    ans[0] = i;
                else if(numbers[i] == val2)
                    ans[1] = i;
            }
        }
        //if 2 vals are =, then find 1 first, then another.
        if(val1 == val2)
        {
            int i;
            for(i=0; i< numbers.length; i++)
            {
                if(numbers[i] == val1)
                {
                    ans[0] = i;
                    break;
                }
            }
            for(int j=i+1; j< numbers.length; j++)
            {
                if(numbers[j] == val2)
                {
                    ans[1] = j;
                    break;
                }
            }
        }
        //ind1 must < ind2
        Arrays.sort(ans);
        return ans;
    }
}