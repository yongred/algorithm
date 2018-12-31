
/**
Solution 1: iterates strs, get their next digits num and compare.
How to Arrive:
* There is only 2 types of chars in the strs, '.' or digit.
* So we just need to iterates to get the digits until v[i] == meets '.';
* We do that to get both v1's and v2's nums. 
* Then compare them.
* If one of them, v1 or v2 is finished all chars, we init vNum to 0; Since v.0.0 == v;
**Algorithm**
* Declare indexes for v1 and v2: i1, i2;
* While i1 < v1l.len OR i2 < v2.len: (we want to compare until both finished)
	* init v1num to 0; (if i1 > v1.len, we use 0 to compare, Since v.0.0 == v);
	* While i1 < v1.len And v1[i1] char is Not '.' (char is digit)
		* We get the digit (v1char - 'a');
		* v1num * 10 + digit (v1num append the digit);
* We Do the same (above while loop) with v2;
* Now we have v1num and v2num, Compare them.
* If v1num > v2num, return 1;
* if v1num < v2num, return -1;
* if they are ==, increment i1 and i2 to compare next num.
* Time: O(n);
* Space: O(1)

---------------

Solution 2: Use Split("\\."); Then Integer.ParseInt(numStr);
How to Arrive:
* Alternatively we can split the v1 and v2 strs to get the nums.
* **Key** Split("\\."), split(".") will match any char, "." as regex. Escape using "\\.";
*  "\." is same as ".", it has a \ before it, so we need to escape that \. "\\.";
*  Then we use the same logic and algorithm to compare v1num and v2num;
*  We get the IntVal of the numStr using Integer.ParseInt(numStr);
*  
* Time: O(n); Slower, consider Splits is also linear time.
* Space: O(n); Extra spaces for array.
*/

class CompareVersionNumbers {

	/**
	 * Solution 1: iterates strs, get their next digits num and compare.
	 * Time: O(n);
	 * Space: O(1)
	 */
  public int compareVersion(String version1, String version2) {
    // loop compare. 2 pointers.
    int i = 0;
    int j = 0;
    while (i < version1.length() || j < version2.length()) {
      // v1 num, If v1 finished/out of bounce, v.0.0 == v;
      int v1num = 0;
      // get the next v1 digit.
      while (i < version1.length() && version1.charAt(i) != '.') {
        int digit = version1.charAt(i) - '0';
        // add/append the digit.
        v1num = v1num * 10 + digit;
        // increment i
        i++;
      }
      
      // v2 digit num. If v2 finished/out of bounce, v.0.0 == v;
      int v2num = 0;
      // get the next v2 digit.
      while (j < version2.length() && version2.charAt(j) != '.') {
        int digit = version2.charAt(j) - '0';
        // add/append the digit.
        v2num = v2num * 10 + digit;
        // increment i
        j++;
      }
      
      // compare 2 nums
      if (v1num > v2num) {
        return 1;
      } else if (v1num < v2num) {
        return -1;
      }
      // i and j is at '.' or end of str, go to next digit.
      i++;
      j++;
    }
    
    return 0;
  }

  /**
   * Solution 2: Use Split("\\."); Then Integer.ParseInt(numStr);
   * Time: O(n); Slower than Solution1, consider Splits is also linear time.
	 * Space: O(n); Extra spaces for array.
   */
  public int compareVersion(String version1, String version2) {
    // split(".") is any char "." as regex. escape using "\\.";
    String[] nums1 = version1.split("\\.");
    String[] nums2 = version2.split("\\.");
    // iterates nums1, nums2.
    int i = 0;
    int j = 0;
    while (i < nums1.length || j < nums2.length) {
      // get curNum of v1.
      int v1num = 0;
      if (i < nums1.length) {
        v1num = Integer.parseInt(nums1[i]);
      }
      // get curNum of v2;
      int v2num = 0;
      if (j < nums2.length) {
        v2num = Integer.parseInt(nums2[j]);
      }
      // compare
      if (v1num > v2num) {
        return 1;
      } else if (v1num < v2num) {
        return -1;
      }
      // 2 nums equal, compare next nums.
      i++;
      j++;
    }
    // all equal
    return 0;
  }
  
}