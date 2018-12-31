/**
67. Add Binary
Given two binary strings, return their sum (also a binary string).

The input strings are both non-empty and contains only characters 1 or 0.

Example 1:

Input: a = "11", b = "1"
Output: "100"
Example 2:

Input: a = "1010", b = "1011"
Output: "10101"
*/

/**
Solution: Reversely loop a and b, define a carry, calc sum using the digits and carry.
Ex:
1010
11
a: 0, 1, 0, 1
b: 1, 1, nil(0), nil(0)
carry: 0, 0, 1, 0
sum: 1, 0, 1, 1

ANS = 1101;

How to Arrive:
* We want the binary sum str of the binary string.
* We need a Carry var, 2 indexes for a and b, also a StringBuilder for the res.
* While A or B have char/bit left to calc, (i >= 0 || j >= 0):
	* Get the char bits a[i] and b[j], and convert it to int 0 or 1;
	* If i or j is outOfBounce, just use 0 as val, since ex: 11 = 0011;
	`int valA = (i >= 0) ? a.charAt(i) - '0' : 0;
   int valB = (j >= 0) ? b.charAt(j) - '0' : 0;`
	* Then we calc the sum = bitA + bitB + carry;
	* We prepend the curBit val of the sum into StringBuider, curBit = sum % 2;
	* sb.insert(0, sum % 2);
  * Then we find the carry. Either sum / 2; Or sum >= 2: carry = 1, sum< 2: carry= 0;
  * Then decrement the i and j indexes.
* All chars/bits calculated after loop, check last carry
* If carry == 1, sb prepend 1.
* return res as string.
* Time: O(n)
* Space: O(n)

*/

class AddBinary {

	/**
	 * Solution: Reversely loop a and b, define a carry, calc sum using the digits and carry.
	 * Time: O(n)
	 * Space: O(n)
	 */
  public String addBinary(String a, String b) {
    // carry, a bit, b bit.
    StringBuilder sb = new StringBuilder();
    int carry = 0;
    // 2 pointers.
    int i = a.length() - 1;
    int j = b.length() - 1;
    // calc until both a, b done.
    while (i >= 0 || j >= 0) {
      // get bit from a and b, default val is 0;
      int valA = (i >= 0) ? a.charAt(i) - '0' : 0;
      int valB = (j >= 0) ? b.charAt(j) - '0' : 0;
      int sum = valA + valB + carry;
      // get the bit to add to res bit
      int curBit = sum % 2;
      sb.insert(0, curBit);
      // see if sum carry
      carry = (sum >= 2) ? 1 : 0;
      i--;
      j--;
    }
    // all chars/bits calculated, check last carry.
    if (carry == 1) {
      sb.insert(0, 1);
    }
    // return res string.
    return sb.toString();
  }
  
}