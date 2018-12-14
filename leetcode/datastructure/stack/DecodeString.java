/**
394. Decode String
Given an encoded string, return it's decoded string.

The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.

Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].

Examples:

s = "3[a]2[bc]", return "aaabcbc".
s = "3[a2[c]]", return "accaccacc".
s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
*/

/**
Solution: use 2 stacks, 1 for nums, 1 for other chars.
Ex:
3[a2[c]]d
nums: 3-, 2-, 
chars: [-, a-, [-, c-, cc-, accaccacc-, d-;
accaccaccd;

How to arrive:
* Basically when we encounter #[str] we need to take care of it first. And then use it for later formation.
* We will use 2 stacks: Stack<Integer> nums, Stack<String> strs;
* Notice we use String stack instead of Character. Because we need to form string and store in the stack. Ex: 2[s,t,r] = strstr; Save "strstr" in stack.
* Algorithm:
	* Loop through each char:
		* Case digit:
			* Check if next one is also digit. Form continue digits into a num. Store in Nums stack.
		* Case [:
			* add to Strs stack.
		* Case ]:
			* While Strs.peek not "[":
			* Form string inside [s,t,r]; Using a StringBuilder to **Prepend** Strs.pop() chars.
			* Form the target str, and Pop [
			* Prepend because stack order pop is reversed.
			* Get the times num in Nums.pop();
			* for times # of time, append the targetStr to StringBuilder. 
			* Push the res String "strstrstr" to Strs stack.
		* Case other chars:
			* push to Strs stack in String form (not char).
	* Finished Loop. Now all chars or strings in Strs stack.
	* Prepend them 1 by 1 to a StringBuilder res.
	* While Strs not empty:
		* res insert(0, strs.pop);
	* Return res.toString()
* Time: O(n);
* Space: O(n);
*/

class DecodeString {

	/**
	 * Solution: use 2 stacks, 1 for nums, 1 for other chars.
	 * Time: O(n)
	 * Space: O(n)
	 */
  public String decodeString(String s) {
    if (s == null || s.length() == 0) {
      return "";
    }
    Stack<Integer> nums = new Stack<>();
    Stack<String> strs = new Stack<>();
    
    for (int i = 0; i < s.length(); i++) {
      char chr = s.charAt(i);
      // if digit
      if (chr >= '0' && chr <= '9') {
        int val = chr - '0';
        // if next is also digit
        while (i + 1 < s.length() &&
            s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
          val = val * 10 + (s.charAt(i + 1) - '0');
          i++;
        }
        // add the nums
        nums.push(val);
      } else if (chr == '[') {
        // add open [
        strs.push("[");
      } else if (chr == ']') {
        // close ]
        // transform num * [chars]
        StringBuilder sb = new StringBuilder();
        // get string in []
        while (!strs.peek().equals("[")) {
          // prepend to the string.
          sb.insert(0, strs.pop());
        }
        String target = sb.toString();
        // pop [
        strs.pop();
        // multiply the string
        sb.setLength(0);
        int times = nums.pop();
        for (int j = 0; j < times; j++) {
          sb.append(target);
        }
        strs.push(sb.toString());
      } else {
        // chars
        strs.push(Character.toString(chr));
      }
      
    }
    StringBuilder res = new StringBuilder();
    // all strs are in stack, prepend all 
    while (!strs.isEmpty()) {
      res.insert(0, strs.pop());
    }
    return res.toString();
  }
}