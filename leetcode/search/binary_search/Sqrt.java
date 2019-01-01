/**
69. Sqrt(x)
Implement int sqrt(int x).

Compute and return the square root of x, where x is guaranteed to be a non-negative integer.

Since the return type is an integer, the decimal digits are truncated and only the integer part of the result is returned.

Example 1:

Input: 4
Output: 2
Example 2:

Input: 8
Output: 2
Explanation: The square root of 8 is 2.82842..., and since 
             the decimal part is truncated, 2 is returned.
*/

/**
Solution: Binary Search, use x/mid ><= mid as boundary;
Ex:
change the problem to search 1 -> x; where Max(tar)^2 <= x;
8
l: 1, 3
r: 8, 3, 2
mid = 1+(8-1)/2= 4; 1+(3-1)/2= 2; 3+(3-3)/2= 3;
4 > 8/4 on left; r= 4-1=3
2 < 8/2 on right; l= 2+1=3
3 > 8/3 on left; r= 3-1=2;
l3 > r2; Done;  RightIndex 2 is ANS;

How to Arrive:
* This problem can be translated into search from 1 -> x; where Max(tar)^2 <= x;
* We look for tar * tar = x; if n * n < x, that means tar is on right. n * n > x, tar is on left.
* So this problem can be solve by Binary Search.
* The Boundary is mid ><= (x / mid); use  x/mid instead of mid * mid == x, prevent overflow.
* 
* Time: O(lgN)
* Space: O(1)
*/
class Sqrt {
  
  /**
   * Solution: Binary Search, use x/mid ><= mid as boundary;
   * Time: O(lgN)
   * Space: O(1)
   */
  public int mySqrt(int x) {
    if (x == 0) {
      return 0;
    }
    // binary search.
    int left = 1;
    int right = x;
    
    while (left <= right) {
      // mid = left last pos
      int mid = left + (right - left) / 2;
      // determine mid * mid ><= x;
      if (mid == (x / mid)) {
        return mid;
      } else if (mid < (x / mid)) {
        // too small, target on right side.
        left = mid + 1;
      } else {
        // mid > x/mid, too big, target on left.
        right = mid - 1;
      }
    }
    // right+1 = left, at l>r; no more candidates. 
    // Ans is the rightIndex, because we are finding largest^2 <= x; 
    // rightIndex is the smaller one if no ==;
    return right;
  }
  
}