<?php
/**
229. Majority Element II

Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.

Note: The algorithm should run in linear time and in O(1) space.

Example 1:

Input: [3,2,3]
Output: [3]
Example 2:

Input: [1,1,1,3,3,2,2,2]
Output: [1,2]
*/

/*
Solution: Boyer-Moore voting with 2 cands.
Ex: [1,3,1,1,3,2,2,2]
A: 1
B: 1-,3-, 2
cntA: i0 0+1=1, i2 1+1=2, i3 2+1=3, i4 3-1=2, i5 2-1=1
cntB: i1 0+1=1, i4 1+1=2, i4 2-1=1, i5 1-1=0, i6 0+1=1
Done;
check cnt1=3 > 2(8/3); cnt2=3 > 2(8/3);
Ans= [1,2];

* Find number > n/3 means we can have max 2 candidates.
* maintain 2 candidates, A and B; 
* countA+1 when num = A; countB+1 when num=B; (one or the other, A should not = to B);
* When A=0 num becomes new A; same with B; again num can either be A or B, not both.
* When num != A && != B; then num is a possible new candidate, so countA-- & countB--, both decrements;
* After finish getting 2 possible candidates, we have to validate if A and B have count > n/3;
* Loop nums again, count A and count B; 
* Add A or/and B to Res if A or/and B is > n/3;
* Time: O(n)
* Space: O(1)
*/

class MajorityElementII
{

    /**
     * @param Integer[] $nums
     * @return Integer[]
     * Solution: Boyer-Moore voting with 2 cands.
	 * Time: O(n)
	 * Space: O(1)
     */
    function majorityElement($nums)
    {
        if (empty($nums)) {
            return [];
        }
        $candA = $nums[0];
        $candB = $nums[0];
        $countA = 0;
        $countB = 0;
        
        foreach ($nums as $num) {
            if ($num == $candA) {
                $countA++;
                continue;
            }
            
            if ($num == $candB) {
                $countB++;
                continue;
            }
            
            if ($countA == 0) {
                $candA = $num;
                $countA++;
                continue;
            }
            
            if ($countB == 0) {
                $candB = $num;
                $countB++;
                continue;
            }
            // not A and B, new possible cand
            $countA--;
            $countB--;
        }
        
        // check A and B > n/3;
        $countA = 0;
        $countB = 0;
        foreach ($nums as $num) {
            if ($num == $candA) {
                $countA++;
            } else if ($num == $candB) {
                $countB++;
            }
        }
        
        $res = [];
        if ($countA > sizeof($nums) / 3) {
            $res[] = $candA;
        }
        if ($countB > sizeof($nums) / 3) {
            $res[] = $candB;
        }
        
        return $res;
    }
}