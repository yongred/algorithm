/**
756. Pyramid Transition Matrix
We are stacking blocks to form a pyramid. Each block has a color which is a one letter string, like `'Z'`.

For every block of color `C` we place not in the bottom row, we are placing it on top of a left block of color `A` and right block of color `B`. We are allowed to place the block there only if `(A, B, C)` is an allowed triple.

We start with a bottom row of bottom, represented as a single string. We also start with a list of allowed triples allowed. Each allowed triple is represented as a string of length 3.

Return true if we can build the pyramid all the way to the top, otherwise false.

Example 1:
Input: bottom = "XYZ", allowed = ["XYD", "YZE", "DEA", "FFF"]
Output: true
Explanation:
We can stack the pyramid like this:
    A
   / \
  D   E
 / \ / \
X   Y   Z

This works because ('X', 'Y', 'D'), ('Y', 'Z', 'E'), and ('D', 'E', 'A') are allowed triples.
Example 2:
Input: bottom = "XXYX", allowed = ["XXX", "XXY", "XYX", "XYY", "YXZ"]
Output: false
Explanation:
We can't stack the pyramid to the top.
Note that there could be allowed triples (A, B, C) and (A, B, D) with C != D.
Note:
bottom will be a string with length in range [2, 8].
allowed will have length in range [0, 200].
Letters in all strings will be chosen from the set {'A', 'B', 'C', 'D', 'E', 'F', 'G'}.
*/

/**
      A
     / \
    B   C
   / \ / \
  D   E   F
 / \ / \ / \
G   A   B   C

UpperLevel len = LowerLevel len - 1;

Ex: allowed: [GAB, GAC, GAD, ABC, ABD, ABE, BCA, BCF, DEB, EFC, EFG, BCB, BCA];

Solution: Map "left,right" -> tops, use curRow backtracking/dfs nextRow.
How to Arrive:
* The Question gave us the Bottom, We need to build upper levels;
* Each 2 chars substr of Bottom linked to a TopChar, And allowed have diff top choices for 2chars substrs;
* So First: we need to Map "left,right" -> tops; So we can search Bottom's each 2chars substr's tops choices;
* Next: We want to test each choice of tops leads to topMost pyramid Shape where level len=1;
* Sounds like a DFS/backtracking problem; 
* Base Cases:
	* When curRow len = 1; we are on TopMost level; return True;
	* When NextRow is completely formed, nextRow.len = curRow.len -1; (Or when curRow index == curRow.len - 2);
		* Now we can build another level, curRow= nextRow; nextRow = "";
* Recursive Case:
	* Going from leftmost of Bottom to rightmost pair of 2chars substr:
		* Get all the tops choices for bottom's cur 2chars pair:
		* Choose each top for cur position: (nextRow + curTop) to go with the next 2chars Pair's choices;
		* Try every top for cur position, If any returned True; We Found a possible way, return true;
	* If not found any path leads to pyramid, return false;
	
* Time: O(A^B); B= bottom.len; A= allowedSize; All upper options goes down until Blen;
* so worse case Size of Allowed goes down to BLen; Each level is A-1 choices; So each level is A choices * B;
* Space: O(A); allowed size;
* 
*/

class PyramidTransitionMatrix {

	/**
	 * Solution: Map "left,right" -> tops, backtracking/dfs nextRow.
	 * Time: O(A^B); B= bottom.len; A= allowedSize; All upper options goes down until Blen;
	 * so worse case Size of Allowed goes down to BLen; Each level is A-1 choices; So each level is A choices * B;
	 * Space: O(A); allowed size;
	 */
  public boolean pyramidTransition(String bottom, List<String> allowed) {
    // map "left,right" -> tops options Set;
    Map<String, Set<Character>> map = new HashMap<>();
    for (String str : allowed) {
      // get left,right
      String leftRight = str.substring(0, 2);
      map.putIfAbsent(leftRight, new HashSet<>());
      map.get(leftRight).add(str.charAt(2));
    }
    return dfs(bottom, "", 0, map);
  }
  
  public boolean dfs(String row, String nextRow, int index,
      Map<String, Set<Character>> map) {
    // top row.
    if (row.length() == 1) {
      return true;
    }
    // if nextRow is finished, UpperLevel len = LowerLevel len - 1;
    if (nextRow.length() == row.length() - 1) {
      // Finished this row, go up and build next;
      return dfs(nextRow, "", 0, map);
    }
    // search for row substr(idx,idx+1) as key, for their tops.
    Set<Character> tops = map.getOrDefault(row.substring(index, index + 2),
        new HashSet<>());
    // if empty tops means no available top for this bottom pair.
    for (char top : tops) {
    	// substitude with diff tops for nextRow
      if (dfs(row, nextRow + top, index + 1, map)) {
        // found a top leads to pyramid.
        return true;
      }
    }
    // no top leads to pyramid.
    return false;
  }
   
}