/**
374. Guess Number Higher or Lower
We are playing the Guess Game. The game is as follows:

I pick a number from 1 to n. You have to guess which number I picked.

Every time you guess wrong, I'll tell you whether the number is higher or lower.

You call a pre-defined API guess(int num) which returns 3 possible results (-1, 1, or 0):

-1 : My number is lower
 1 : My number is higher
 0 : Congrats! You got it!
Example :

Input: n = 10, pick = 6
Output: 6
*/
/**
Solution: regular binary search.
How to arrive:
* We can guess the mid and they tell you target is smaller -1, greater 1, or == 0;
* When guess() == 0; return mid. When guess = -1, go to left. When guess = 1, go to right.
* Time: O(lgN);
* Space: O(1);
*/

/* The guess API is defined in the parent class GuessGame.
   @param num, your guess
   @return -1 if my number is lower, 1 if my number is higher, otherwise return 0
      int guess(int num); */

public class GuessHigherOrLower extends GuessGame {

	/**
	 * Solution: Binary Search Iterative.
	 * Time: O(lgN)
	 * Space: O(1)
	 */
	public int guessNumber(int n) {
    int start = 1;
    int end = n;
    while (start < end) {
      int mid = start + (end - start) / 2;
      if (guess(mid) == 0) {
        return mid;
      } else if (guess(mid) == -1) {
        // mid > target, go left
        end = mid - 1;
      } else {
        // mid < target, guess(mid) = 1;
        start = mid + 1;
      }
    }
    // 1 num left.
    return start;
  }
	
	/**
	 * Solution: Binary Search recursive.
	 * Time: O(lgN)
	 * Space: O(lgN)
	 */
  public int guessNumber(int n) {
    return helper(1, n);
  }
  
  public int helper(int start, int end) {
    int mid = start + (end - start) / 2;
    if (guess(mid) == 0) {
      return mid;
    } else if (guess(mid) == -1) {
      // mid > target, go left
      return helper(start, mid - 1);
    } else {
      // guess = 1; mid < target, go right.
      return helper(mid + 1, end);
    }
  }
}