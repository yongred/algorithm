/**
191. Number of 1 Bits
Write a function that takes an unsigned integer and return the number of '1' bits it has (also known as the Hamming weight).

 

Example 1:

Input: 00000000000000000000000000001011
Output: 3
Explanation: The input binary string 00000000000000000000000000001011 has a total of three '1' bits.
Example 2:

Input: 00000000000000000000000010000000
Output: 1
Explanation: The input binary string 00000000000000000000000010000000 has a total of one '1' bit.
Example 3:

Input: 11111111111111111111111111111101
Output: 31
Explanation: The input binary string 11111111111111111111111111111101 has a total of thirty one '1' bits.
 

Note:

Note that in some languages such as Java, there is no unsigned integer type. In this case, the input will be given as signed integer type and should not affect your implementation, as the internal binary representation of the integer is the same whether it is signed or unsigned.
In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 3 above the input represents the signed integer -3.
 

Follow up:

If this function is called many times, how would you optimize it?
*/

/**
Solution: bit shifting. Logical Right Shift, AND the num to see if == 1;
How to Arrive:
* AND num with 1 check ==1, can check is 1s bit is 1.
* Then we Logical Right shift n, to check next bit. (use logical shift so it always push in 0, instead of 1s in -#;) Since Java 2's complement, no unsign representation.
* Stop until n == 0;
* You can also loop from 0->31, check every bits (1 << i & n) != 0;
* 
* Time: O(1), 32 bits in integer.
* Space: O(1);
*/

public class NumberOfOneBits {

	/**
	 * Solution: Same logic.
	 * Time: O(1)
   * Space: O(1)
	 */
	public int hammingWeight(int n) {
    int count = 0;
    for (int i = 0; i < 32; i++) {
      if ((1 << i & n) != 0) {
        count++;
      }
    }
    return count;
  }

  /**
   * Solution: bit shifting. Logical Right Shift, AND the num to see if == 1;
   * Time: O(1), 32 bits in integer.
   * Space: O(1)
   */
  public int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
      if ((n & 1) == 1) {
        count++;
      }
      // use logical shift so it always push in 0, instead of 1s in -#;
      // since java is 2s complement, no unsign number.
      n >>>= 1;
    }
    return count;
  }

}