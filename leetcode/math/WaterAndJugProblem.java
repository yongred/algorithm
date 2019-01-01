/**
365. Water and Jug Problem

You are given two jugs with capacities x and y litres. There is an infinite amount of water supply available. You need to determine whether it is possible to measure exactly z litres using these two jugs.

If z liters of water is measurable, you must have z liters of water contained within one or both buckets by the end.

Operations allowed:

Fill any of the jugs completely with water.
Empty any of the jugs.
Pour water from one jug into another till the other jug is completely full or the first jug itself is empty.
Example 1: (From the famous "Die Hard" example)

Input: x = 3, y = 5, z = 4
Output: True
Example 2:

Input: x = 2, y = 6, z = 5
Output: False
*/

/**
Solution: Use Bézout's identity; Find if z is a multiple of GCD(x, y);
How to Arrive:
* This is a Math problem; We could come up with DFS, but very time consuming.
* Idea: Bézout's identity
	* d = gcd of a and b; then there is xa + yb = d;
  * And every multiple of d can have xa + yb; xa + yb = (m)d;
* Coner cases:
	* if x + y < z; then we cannot fill x or y with z water. False
	* if z=0 means empty x or y is already size of z; True
* Find GCD: Using Euclidian Algorithm
	* (Larger = smaller * n + r) => (n = r * n2 + r2)
  * larger = n; smaller = r; return s; l = s * n + r; Until r = 0; return smaller;
  * Same as return larger when smaller = 0;
  * Ex: 10, 45; 45 % 10 = 5, 10 % 5 = 0; GCD= 5;
  * x % y until y = 0; is O(lgN);
* Time: O(lg(x+y))
* Space: O(lg(x+y)) for recursive. Iterative O(1)
*/

class WaterAndJugProblem {

	/**
	 * Solution: Use Bézout's identity; Find if z is a multiple of GCD(x, y);
	 * Time: O(lg(x+y))
	 * Space: O(lg(x+y)) for recursive. Iterative O(1)
	 */
  public boolean canMeasureWater(int x, int y, int z) {
    // Math, d = gcd of a and b; then there is xa + yb = d;
    // And every multiple of d can have xa + yb; xa + yb = (m)d;
    // if x + y < z; then we cannot fill x or y with z water.
    if (x + y < z) {
      return false;
    }
    // Zero cases
    if (z == 0) {
      // z=0 means empty x or y is already size of z;
      return true;
    }
    int gcd = GCD(x, y);
    return (z % gcd == 0);
  }
  
  /**
   * Time: O(lg(x+y)) 
   * Space: O(lg(x+y))
   */
  public int GCD(int x, int y) {
    // find GCD of x and y. Using Euclidian Algorithm
    // (Larger = smaller * n + r) => (n = r * n2 + r2)
    // larger = n; smaller = r; Until r = 0; return s; l = s * n + r;
    // Ex: 10, 45; 45 % 10 = 5, 10 % 5 = 0; GCD= 5;
    // check if prev reminder = 0;
    if (y == 0) {
      return x;
    }
    // find reminder.
    int reminder = x % y;
    // recurse call, (x)larger = smaller, (y)smaller = reminder.
    return GCD(y, reminder);
  }

  /**
   * Time: O(lg(x+y)) 
   * Space: O(1)
   */
  public int GCDIter(int x, int y) {
  	// x as larger, y as smaller;
  	while (y != 0) {
  		int reminder = x % y;
  		x = y;
  		y = reminder;
  	}
  	return x;
  }



}