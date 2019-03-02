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

class PascalsTriangleII {

	/**
	 * Solution 1: DP, use prev row to get curRow. Keep a prevRight var to solve override issue.
	 * Time: O(n^2)
	 * Space: O(n); only 1 row used.
	 */
  public List<Integer> getRow(int rowIndex) {
    List<Integer> res = new ArrayList<>();
    // 1st elm is 1;
    res.add(1);
    
    for (int i = 1; i <= rowIndex; i++) {
      // prev Right needed, b/c it is override by new row's right.
      int prevRight = 1;
      // fill 2nd elm to last-1 elm for cur row.
      for (int j = 1; j < i; j++) {
        // prevRight is the cur left.
        int upLeft = prevRight;
        int upRight = res.get(j);
        res.set(j, upLeft + upRight);
        // update curRight as next's prevRight.
        prevRight = upRight;
      }
      // last elm is 1;
      res.add(1);
    }
    
    return res;
  }


  /**
   * Solution 2: reverse loop each row, curRow[j] = [j-1] col to [j];
   * Time: O(n^2);
	 * Space: O(n);
   */
  public List<Integer> getRow(int rowIndex) {
    List<Integer> res = new ArrayList<>();
    res.add(1);
    // 2nd row to last row;
    for (int i = 1; i <= rowIndex; i++) {
      // DESC, from last-1 elm to 2nd index1 elm;
      for (int j = i - 1; j >= 1; j--) {
        // Means: curRow[j] = prevRow[j] + prevRow[j-1];
        res.set(j, res.get(j - 1) + res.get(j));
      }
      // last elm is 1;
      res.add(1);
    }
    
    return res;
  }

}