/**
278. First Bad Version
You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all the versions after a bad version are also bad.

Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.

You are given an API bool isBadVersion(version) which will return whether version is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.

Example:

Given n = 5, and version = 4 is the first bad version.

call isBadVersion(3) -> false
call isBadVersion(5) -> true
call isBadVersion(4) -> true

Then 4 is the first bad version. 
*/

/**
Solution: Binary Search.
n = 10; 1stBad = 8;
5 = false, ->; 8 = true <-; 6 = false ->; 7 false, loop out.
How to arrive:
* Realize if cur is good version (false) that means all prevs are Good (false).
* If cur is bad version that means all afters are Bad (true);
* So if Good search ->; if Bad search <-;
* Checking the mid val; 
* **Key**: be careful of the overflow.
	* (left + right) / 2 May cause overflow if right = MAX_VALUE;
	* Use l + ((r - l)/2) instead, r-l is distance, /2 half distance, l+ half distance is mid.
	* this reduce the val of calc, l + ((r - l)/2) < right;
* Time: O(lg(n));
* Space: O(1);
*/


public class FirstBadVersion {
	
	public int firstBadVersion(int n) {
    int firstBad = -1;
    int left = 1;
    int right = n;
    while (left <= right) {
      // this will cause overflow if n = MAX_VALUE;
      // int mid = (left + right) / 2;
      // to prevent overflow, l + ((r - l)/2); 
      // r-l is distance, /2 half distance, l+ half distance is mid.
      int mid = left + (right - left) / 2;
      if (isBadVersion(mid)) {
        firstBad = mid;
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    return firstBad;
  }
}