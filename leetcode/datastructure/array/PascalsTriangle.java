/**
118. Pascal's Triangle

Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.


In Pascal's triangle, each number is the sum of the two numbers directly above it.

Example:

Input: 5
Output:
[
     [1],
    [1,1],
   [1,2,1],
  [1,3,3,1],
 [1,4,6,4,1]
]
*/

/**
Solution: Dynamic Programming, use prevRow's res to get curRow.

* Init firstRow [1]; we know its just 1;
* Loop through each row:
	* Declare new Row list
	* Loop from j=0 -> j=i;  (Each row have same # of elms as row number):
		* Construct by adding prevRow's j-1 and j num;
		* left is prev row's j-1, if j=0, then it is count as 0.
		* right is prev row's j, if j=i, then it is count as 0.
		* Note: we could also just put 1st and last col as 1.
		* curRow's [j] = left + right; from prevRow;
	* Add new row to Res;

* Time: O(n^2);
* Space: O(n^2)
*/

class PascalsTriangle {

	/**
	 * Solution: Dynamic Programming, use prevRow's res to get curRow.
	 * Time: O(n^2);
	 * Space: O(n^2)
	 */
  public List<List<Integer>> generate(int numRows) {
    List<List<Integer>> res = new ArrayList<>();
    if (numRows < 1) {
      return res;
    }
    res.add(Arrays.asList(1));
    
    for (int i = 1; i < numRows; i++) {
      List<Integer> row = new ArrayList<>();
      for (int j = 0; j <= i; j++) {
      	// Note: we could also just put 1st and last col as 1.
        // left is prev row's j-1, if j=0, then it is count as 0.
        int up1 = (j == 0) ? 0 : res.get(i - 1).get(j - 1);
        // right is prev row's j, if j=i, then it is count as 0.
        int up2 = (j == i) ? 0 : res.get(i - 1).get(j);
        row.add(up1 + up2);
      }
      res.add(row);
    }
    return res;
  }
  
  
}