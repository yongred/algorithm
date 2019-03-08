<?php

/**
179. Largest Number
Given a list of non negative integers, arrange them such that they form the largest number.

Example 1:

Input: [10,2]
Output: "210"
Example 2:

Input: [3,30,34,5,9]
Output: "9534330"
Note: The result may be very large, so you need to return a string instead of an integer.
*/

/*
Solution: Custom comparator to sort the nums in a:b or b:a combo;
Ex:
[3, 30, 34, 5, 9]
330 > 303; [3,30]
343 > 334; [34, 3, 30]
534 > 345; [5, 34, 3, 30]
95 > 59; [9, 5, 34, 3, 30];

* We just need to find a way to sort the nums in DESC order of larger combo in front;
* Ex: "3:31" > "31:3";
* In Java we can compare strings like this: "123" > "122"; "122" == "122";
* So just create a custom comparator Lambda to sort the Array nums;
* First, we need to convert nums into Strings of nums;
* Then, Sort by comparing "B:A".compareTo("A:B"); DESC;
* Append Sorted numStrs into Res string;
**Tricky Case**: "000" can be form by [0,0,0] input;
* Check if 1st char is 0; if is return "0"; B/c if first/largest == 0, then the following are all 0s;
* Return res;

* Time: O(N Lg N);
* Space: O(n);
*/

class LargestNumber {

    /**
     * @param Integer[] $nums
     * @return String
     * Solution: Custom comparator to sort the nums in a:b or b:a combo;
	 * Time: O(N Lg N);
	 * Space: O(n);
     */
    function largestNumber($nums)
    {
        if (empty($nums)) {
            return '';
        }
        // convert nums to strs
        $numStrs = [];
        foreach ($nums as $num) {
            $numStrs[] = strval($num);
        }
        // sort DESC, compare "A:B" to "B:A";
        usort($numStrs, function ($a, $b) {
            $ab = $a . $b;
            $ba = $b . $a;
            return strcmp($ba, $ab);
        });
        // check 1st numStr == 0;
        if ($numStrs[0] == "0") {
            return "0";
        }
        // .= is faster than implode(array, '');
        $res = '';
        foreach ($numStrs as $str) {
            $res .= $str;
        }
        return $res;
    }
}