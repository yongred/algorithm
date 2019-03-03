<?php
/**
119. Pascal's Triangle II

Given a non-negative index k where k ≤ 33, return the kth index row of the Pascal's triangle.

Note that the row index starts from 0.


In Pascal's triangle, each number is the sum of the two numbers directly above it.

Example:

Input: 3
Output: [1,3,3,1]
Follow up:

Could you optimize your algorithm to use only O(k) extra space?
*/

/**
 Solution 1: DP, Keep a prevRight var to solve override issue, where the next upLeft = prevRight.

Ex: 
1,2,1
first elm = 1;
prevR=1= curLeft; curRight = 2; 1+2=3
prevR=2= curLeft; curRight=1; 2+1=3
last elm = 1;
1,3,3,1
Done.

* Time: O(n^2)
* Space: O(n); only 1 row used.

--------------

Solution 2: reverse loop each row, curRow[j] = [j-1] col to [j];
Ex:
1
1,1
1, (1+1)2, 1
1, (1+2)3, (2+1)3, 1
1, (1+3)4, (3+3)6, (3+1)4, 1

* curRow[j] == prevRow[j] + prevRow[j-1]; 
* Going ascending 0->rowNum is going to replace the prevRow's val; Like solution1;
* However, curRow has 1 more elm than prevRow, which means if we go <- DESC, we can simply do row[j] += row[j-1]; Means: curRow[j] = prevRow[j] + prevRow[j-1];

* Time: O(n^2);
* Space: O(n);
*/

class PascalsTriangleII
{

    /**
     * @param Integer $rowIndex
     * @return Integer[]
     * Solution 2: reverse loop each row, curRow[j] = [j-1] col to [j];
     * Time: O(n^2);
	 * Space: O(n);
     */
    function getRow($rowIndex)
    {
        $res = [];
        if ($rowIndex < 0) {
            return $res;
        }
        $res[] = 1;
        
        for ($i = 1; $i <= $rowIndex; $i++) {
            // DESC, last-1 to index1;
            for ($j = $i - 1; $j >= 1; $j--) {
                // curRow[j] = prevRow[j] + prevRow[j-1];
                $res[$j] = $res[$j] + $res[$j - 1];
            }
            // last elm = 1;
            $res[] = 1;
        }
        
        return $res;
    }
}