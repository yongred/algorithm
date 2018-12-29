/**
190. Reverse Bits
Reverse bits of a given 32 bits unsigned integer.

Example 1:

Input: 00000010100101000001111010011100
Output: 00111001011110000010100101000000
Explanation: The input binary string 00000010100101000001111010011100 represents the unsigned integer 43261596, so return 964176192 which its binary representation is 00111001011110000010100101000000.

Example 2:

Input: 11111111111111111111111111111101
Output: 10111111111111111111111111111111
Explanation: The input binary string 11111111111111111111111111111101 represents the unsigned integer 4294967293, so return 3221225471 which its binary representation is 10101111110010110010011101101001.
 
Note:

Note that in some languages such as Java, there is no unsigned integer type. In this case, both input and output will be given as signed integer type and should not affect your implementation, as the internal binary representation of the integer is the same whether it is signed or unsigned.
In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 2 above the input represents the signed integer -3 and the output represents the signed integer -1073741825.
 

Follow up:

If this function is called many times, how would you optimize it?

*/

/**
Solution 1: 2 pointers, setBit clearBit if diff.
How to Arrive:
* leftP = 0; rightP = 31; Points to 1st and last bit.
* Get the bits using (1 << pointers & n) == 0;
* Compare them to see diff:
* If Diff:
	* If (leftBit == 1) rightBit ==0:
		* Clear leftBit to 0; Using ~(1 << leftP) & n; mask 110111 & n;
		* Assign rightBit to 1; Using 1 << rightP | n;
	* else
		* Do the Opposite. Clear rightBit to 0, Assign leftBit to 1;
* Time: O(1); 32 bits.
* Space: O(1)

Solution 2: Push rightBits of n to a Int as leftBit like a Stack.
Ex:
011 
res = 0; 
0 + 1 & 1, 1 << 1 = 10; 
10+ 1&1 = 11, 11 << 1 = 110;
110 + 0&1 = 110; Done;

How to Arrive:
* Since we need to reverse bits, We can think of it as Popping from Stack and Pushing it to another.
* We pop/get rightBits 1 by 1 from n, and push/add to ResInt 1 by 1;
* We get the bit using: `int bit = (1 << i & n) != 0 ? 1 : 0`;
* Then we add to res: res += bit;
* Then we Left Shift the res to open up for next bit push (if res is not at last/32nd bit);
* return res at the end;
* 
* Time: O(1)
* Space: O(1)

*/

public class ReverseBits {

	/**
	 * Solution 2: Push rightBits of n to a Int as leftBit like a Stack.
	 * Time: O(1)
	 * Space: O(1)
	 */
	public int reverseBits(int n) {
    // Do it like stack. Get right bits of n, push into Res from right(top) left(bottom)
    int res = 0;
    // need to reverse all 32 bits in n.
    for (int i = 0; i < 32; i++) {
      // get ith right bit from n
      int bit = (1 << i & n) != 0 ? 1 : 0;
      // add/push it to res as left bit like stack.
      res += bit;
      // check if this is last bit. No need to shift open again, no more next bit.
      // i = 0->31; 31 is amount of shift to get to 32nd bit.
      if (i < 31) {
        // shift res << for next bit. 0011 => 011(0) next bit;
        res <<= 1;
      }
    }
    return res;
  }
	
	/**
	 * Soluion 1: 2 pointers, setBit clearBit if diff.
	 * Time: O(1); 32 bits.
	 * Space: O(1)
	 */
  public int reverseBits(int n) {
    // left right 2 pointers
    // shifting pos 0->31 shifts gets 1->32 bits.
    int left = 0;
    int right = 31;
    while (left < right) {
      // get bits
      int bitLeft = (1 << left & n) != 0 ? 1 : 0;
      int bitRight = (1 << right & n) != 0 ? 1 : 0;
      if (bitRight != bitLeft) {
        // swap them.
        if (bitRight == 1) {
          // left is 0;
          // OR/assign 1 to n's left pos.
          n = (1 << left | n);
          // clear right pos to 0. Using AND 0; Mask like 110111
          n = ~(1 << right) & n;
        } else {
          // bit left = 1; right = 0;
          // OR/assign 1 to right pos.
          n = (1 << right | n);
          // clear left pos to 0. Using AND 0; Mask like 110111
          n = ~(1 << left) & n;
        }
      }
      // update i j;
      left++;
      right--;
    }
    return n;
  }
  
}