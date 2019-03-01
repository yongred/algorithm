<?php

/**
41. First Missing Positive
Given an unsorted integer array, find the smallest missing positive integer.

Example 1:

Input: [1,2,0]
Output: 3
Example 2:

Input: [3,4,-1,1]
Output: 2
Example 3:

Input: [7,8,9,11,12]
Output: 1
Note:

Your algorithm should run in O(n) time and uses constant extra space.
*/

/*
Solution: partition, put # to its right place, index+1 place.
Ex1:
[3,4,-1,1]
[1,-1,3,4] index1 != index1+1 (missing), meaning index1+1=2, 2 is missing.
Ex2:
[7,8,2,1] => [1,2,7,8]; index2+1 != 7; 3 is missing.
Ex3:
[4,2,3,1] => [1,2,3,4]; no missing in between, so n(4)+1=5 is missing.

* Like 3 way partition:
* Positive #s <= n, goes to positions of the array. -#s and #s > n go to other places in array.
* From 0 -> n-1 index places, find and place all positive #s <= n, and place them in num-1 index place.
* To do that, we loop from 0-> n-1:
	* while [curNum-1] != curNum; we swap curNum to [curNum-1] place.
	* Use a while loop to swap, b/c if the swapped num is positive and <= n, then we will have to swap it to correct place as well;
* Time: O(n);
* Space: O(1)

*/

class FirstMissingPositive {

    /**
     * @param Integer[] $nums
     * @return Integer
     */
    function firstMissing($nums) 
    {
        if (empty($nums)) {
            return 1;
        }
        $n = sizeof($nums);
        // place 0->n-1 index nums to correct pos.
        for ($i = 0; $i < $n; $i++) {
            while ($nums[$i] > 0 && $nums[$i] <= $n
                   && $nums[$nums[$i] - 1] != $nums[$i]) {
                $this->swap($nums, $i, $nums[$i] - 1);
            }
        }
        
        for ($i = 0; $i < $n; $i++) {
            if ($nums[$i] != $i + 1) {
                return $i + 1;
            }   
        }
        return $n + 1;
    }
    
    function swap(&$nums, $i, $j)
    {
        $temp = $nums[$i];
        $nums[$i] = $nums[$j];
        $nums[$j] = $temp;
    }
}