

/*package whatever //do not write package name here */

import java.util.*;
import java.lang.*;
import java.io.*;

class PairSum {

	/**
	 * Solution: use prefixSum store in hash.
	 * Time: O(n + m) 
	 * Space: O(n)
	 */
  public static void findPairs(int[] arr1, int[] arr2,
      int n, int m, int target) {
    Arrays.sort(arr1);
    StringBuilder sb = new StringBuilder();
    boolean found = false;
    Set<Integer> set = new HashSet<>();
    // prefixSum
    for (int i = 0; i < m; i++) {
        set.add(target - arr2[i]);
    }
    // find arr2 == any prefixSum
    for (int i = 0; i < n; i++) {
        if (set.contains(arr1[i])) {
            int n2 = target - arr1[i];
            if (!found) {
                sb.append(arr1[i] + " " + n2);
                found = true;
            } else {
                sb.append(", " + arr1[i] + " " + n2);
            }
            
        }
    }
    if (!found) {
        System.out.println("-1");
    } else {
        System.out.println(sb);
    }
  }
    
	public static void main (String[] args) {
		Scanner scan = new Scanner(System.in);
		int testcases = scan.nextInt();
		while (testcases > 0) {
	    int n1 = scan.nextInt();
	    int n2 = scan.nextInt();
	    int tar = scan.nextInt();
	    int[] arr1 = new int[n1];
	    int[] arr2 = new int[n2];
	    for (int i = 0; i < n1; i++) {
	        arr1[i] = scan.nextInt();
	    }
	    for (int i = 0; i < n2; i++) {
	        arr2[i] = scan.nextInt();
	    }
	    findPairs(arr1, arr2, n1, n2, tar);
	    testcases--;
		}
	}

}