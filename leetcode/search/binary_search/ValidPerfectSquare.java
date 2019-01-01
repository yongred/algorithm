
/**
Solution: Binary Search, Finding mid * mid = num;
How to Arrive:
* Naive solution is to search from 1 -> x/2; linear search.
* But we can actually eliminate many nums since we are searching in order.
* Which means we can use binary search.
* Boundary is mid * mid ><= num;  Don't use num / mid ><= mid, b/c int / int is approximiated, 5/2=2, 2 * 2 = 4;
* Time: O(lgN);
* Space: O(1);
*/

class ValidPerfectSquare {
	
	/**
	 * Solution: Binary Search, Finding mid * mid = num;
	 * Time: O(lgN);
	 * Space: O(1);
	 */
  public boolean isPerfectSquare(int num) {
    if (num == 1) {
      return true;
    }
    // use long to prevent overflow from mid * mid;
    long left = 1;
    // perfect sqrt(n) <= n/2, if n > 1; normal sqrt if n > 5;
    long right = num / 2;
    while (left <= right) {
      long mid = left + (right - left) / 2;
      // use m * m instead of n/m = m; since int 5/2=2; 2 * 2 = 4;
      if (mid * mid == num) {
        return true;
      } else if (mid * mid < num) {
        // too small, on right
        left = mid + 1;
      } else {
        // mid^2 > num, too big, on left
        right = mid - 1;
      }
    }
    // coverted all elms, l > r;
    return false;
  }
}