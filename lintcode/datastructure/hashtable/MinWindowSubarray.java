
/**
Find smallest subarray that contains all elements in a set.
Set only contains unique integers.
*/

/**
Update for better understanding. For integers Sets of the same problem.
	ex: 
	int [] arr = {3,1,2,5,6,6,1,2,3};
    int [] set = {1,2,6};
    int [] res = subArr(arr, set); [6,1,2];
		
	Min window, using 2 pointers. And hash.
   * How to Arrive:
   * Notice that brute force is n^2. Having nested loop
   * But iteration 1 time is enough. To do that we can keep a record of Set elm appearance.
   * A **HashMap** can be Helpful to count the cur Win target elms.
   * Now think about what we are returning, a subarr (window). 
   * Window: Meaning we need to have the **curStart, curEnd index** of the window.
   * If cur window have all the appreance of target, then we find a new subarr.
   * **Key**: how to know if window has all the elms needed?
		 * Use a counter. Count when elm < to elm # in Set.
		 * Ex: Set {1,2,2,3} cur elm = 2; hash(2) == 1; needed one more. So count++; When hash(2) == 3, meaning over the needs.
	 * When count == Set.len, we find a new subarr.
   * Compare that subarr len to the shortest. If cur is shorter, update.
   * To keep track of the Shortest, we need **minStartInd** of shortest, and **MinLen**, minEndInd (optional) b/c *startInd + minLen - 1 = endInd*.
   * **Key**: how to go to next Window?
		 * Ex: {3,1,2,5,6,6,1,2,3} set = {1,2,6}, 1st win = [1,2,5,6] next = [2,5,6,6,1], next = [6,6,1,2] => [6,1,2]
		 * What changes? The **curStart elm becomes the next last elm**. And next Start is the next Set elm in the window.
		 * **key**: Also if the next Set elm in window > 1; then we move to the next Set elm in window. next = [6,6,1,2] => [6,1,2]; Shorter.
		 * Meaning: *If we find another elm == start elm. We find a new Window.*
		 * Also we *Don't need the Counter after this point. Since we just replacing start to last, only updates when a replacement is found*.
	 * We do the same with next Window. Finding if there is shorter.
	 * **Key**: remember to erase the window elm count from the prev window.
	 * Return: subArr(arr, minStartInd -> minStartInd + MinLen), Java incl: start, excl: end;
	 * Ex: {3,1,2,5,6,6,1,2,3} set = {1,2,6} Ans: 6,1,2  [5->7];
	 *
	 * Time: O(n)
   * Space: O(S) set.len; 
*/

import java.io.*;
import java.util.*;

class MinWindowSubarray {
  
  /**
   * Solution: Min window, using 2 pointers. And hash.
   * How to Arrive:
   * Notice that brute force is n^2. Having nested loop
   * But iteration 1 time is enough. To do that we can keep a record of Set elm appearance.
   * A HashMap can be Helpful to count the cur Win target elms.
   * Now think about what we are returning, a subarr (window). 
   * Window: Meaning we need to have the Start, end index of the window.
   * If cur window have all the appreance of target, then we find a new subarr.
   * Compare that subarr len to the shortest. If cur is shorter, update.
   * To keep track of the Shortest, we need startInd, endInd of shortest, or just MinLen, b/c startInd + minLen = endInd.
   * 
   * Time: O(n)
   * Space: O(S) set.len;
   */
  public static int [] minWin(int [] arr, int [] set) {
    if (arr == null || set == null) {
      return arr;
    }
    int minLen = Integer.MAX_VALUE;
    int startIndex = -1;
    int start = 0;
    int count = 0;
    // count the appears
    HashMap<Integer, Integer> hash = new HashMap<>();
    // create hash count for sets.
    for (int i = 0; i < set.length; i++) {
      hash.put(set[i], 0);
    }
    // find first start elm
    for (int i = 0; i < arr.length; i++) {
      if (hash.containsKey(arr[i])) {
        hash.put(arr[i], hash.get(arr[i]) + 1);
        start = i;
        count++;
        break;
      }
    }
    // j is endInd
    for (int j = start; j < arr.length; j++) {
      if (hash.containsKey(arr[j])) {
      	// set is unique elms, so 0. If not uniq, create another hash to compare
        if (hash.get(arr[j]) == 0) {
          count++;
        }
        // update chr appearance
        hash.put(arr[j], hash.get(arr[j]) + 1);
      }
      // if window have all elms appears as set. Atleast 1 subArr match. 
      // Don't need counter after this point, since we just finding next elm == Start elm.
      // replacing start elm is still the same count.      
      if (count == set.length) {
        // if cur elm == start elm, we find a new window. Slide the window
        if (arr[j] == arr[start]) {
          hash.put(arr[start], hash.get(arr[j]) - 1);
          int newStart = start + 1;
          // finding next Set elm that is NOT more than the needed amount.
          // in this case All Set elms are unique, So check > 1.
          while (!hash.containsKey(arr[newStart]) || hash.get(arr[newStart]) > 1) {
          	// if over needed #, loop over to find the next in Set elm.
          	// This elm no longer in Window, decrease amount. 
            if (hash.containsKey(arr[newStart]) && hash.get(arr[newStart]) > 1) {
              hash.put(arr[newStart], hash.get(arr[newStart]) - 1);
            }
            newStart++;
          }
          
          start = newStart;
        }
        
        // if there is shorter window, unless we find a replacement of start
        // otherwise not changing. since j is only increasing.
        if (minLen > j - start + 1) {
          startIndex = start;
          minLen = j - start + 1;      
        }
      }
    }
    // not found
    if (startIndex == -1) {
      return null;
    }
    return Arrays.copyOfRange(arr, startIndex, startIndex + minLen);
  }
  
  public static void main(String[] args) {
    int [] arr = {3,1,2,5,6,6,1,2,3};
    int [] set = {3,5};
    int [] res = minWin(arr, set);
    System.out.println(Arrays.toString(res));
  }
}
