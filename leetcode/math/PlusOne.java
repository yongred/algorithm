/**
66. Plus One
Given a non-empty array of digits representing a non-negative integer, plus one to the integer.

The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.

You may assume the integer does not contain any leading zero, except the number 0 itself.

Example 1:

Input: [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.
Example 2:

Input: [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.
*/

/**
Solution 1: use a Stack. Add digit reversely. Find carry.
How to Arrive:
* Problems: using an Array, fixed size, what if there is carry in last digit.
	* using an ArrayList, adding the digits have to be in reverse order. So you will have to reverse List at the end.
* A Stack is fitted for this situation. Calc the digits and carry from n-1 to 0 elm; Push them to stack.
* Then pop them into an array, we have the correct order.
* **Key**: Since 1st digit needs to add 1, we can just init Carry = 1; So we can do consistent opes through out the iteration, without treating 1st digit as special case.
	* Also, don't forget after all digits from Input array is calculated, there might still be an carry. 
	* Check if Carry == 1 after the iteration of digits, push to stack if is has an carry.
*
* Time: O(n)
* Space: O(n)

---------------

Solution 2: Use carry/digit 9 as barrier, update digits arr directly.
Ex:
arr: 999
res: 1000

i2 9+1 = 10, carry 1;
i1 9+1 = 10, carry 1;
i0 9+1 = 10, carry 1; 
out
carry=1, res: 1 + 000;

How to Arrive:
* We are only adding 1, so only if there is 9 in the digits, there is carry.
* In other words, if there is not carry we can stop calc.
* So we can edit the digtis Array directly, Only if there is a carry in last digit we need an new int[n+1] array.
* And the only time the last digit will have a carry is case of all 9s, 999 + 1 = 1000;
* In that case we are sure there is just an 1 in index0, it follows by all 0s.
* Which means we don't need to assign all digits array elms to new ResArray.
* We just need to res[0] = 1; That's it.

* Time: O(n)
* Space: O(n) in worse case if there is a carry, if not O(1);
*/

class PlusOne {

	/**
	 * Solution 2: Use carry/digit 9 as barrier, update digits arr directly.
	 * Time: O(n)
	 * Space: O(n) in worse case if there is a carry, if not O(1);
	 */
	public int[] plusOne(int[] digits) {
    // use 9 as an barrier, if > 9 continue adding, if not we stop.
    // if there is carry, 9+1, the digits from left will change
    // if not the digits on left will not change.
    int n = digits.length;
    int carry = 1;
    for (int i = n - 1; i >= 0; i--) {
      int val = digits[i] + carry;
      // get/update the digit
      digits[i] = val % 10;
      carry = val / 10;
      // if there is carry we continue, if not just return current digits.
      if (carry == 0) {
        return digits;
      }
    }
    // not returned means still carry == 1;
    // it means res = 1000...0s, so just assign index0 to 1;
    int[] res = new int[n + 1];
    res[0] = 1;
    return res;
  }

	/**
	 * Solution 1: use a Stack. Add digit reversely. Find carry.
	 * Time: O(n)
	 * Space: O(n)
	 */
  public int[] plusOne(int[] digits) {
    // stack
    Stack<Integer> stack = new Stack<>();
    int carry = 1;
    // loop reversely.
    for (int i = digits.length - 1; i >= 0; i--) {
      int val = digits[i] + carry;
      // get the digit.
      int curDigit = val % 10;
      // for reversed order, 1234 -> 4321
      stack.push(curDigit);
      // find if carry.
      carry = (val >= 10) ? 1 : 0;
    }
    // find if last digit carried.
    if (carry == 1) {
      stack.push(1);
    }
    // all digits in stack. pop and store in array.
    int[] res = new int[stack.size()];
    int index = 0;
    while (!stack.isEmpty()) {
      res[index] = stack.pop();
      index++;
    }
    return res;
  }
  
}