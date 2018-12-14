/**
385. Mini Parser
Given a nested list of integers represented as a string, implement a parser to deserialize it.

Each element is either an integer, or a list -- whose elements may also be integers or other lists.

Note: You may assume that the string is well-formed:

String is non-empty.
String does not contain white spaces.
String contains only digits 0-9, [, - ,, ].
Example 1:

Given s = "324",

You should return a NestedInteger object which contains a single integer 324.
Example 2:

Given s = "[123,[456,[789]]]",

Return a NestedInteger object containing a nested list with 2 elements:

1. An integer containing value 123.
2. A nested list containing two elements:
    i.  An integer containing value 456.
    ii. A nested list with one element:
         a. An integer containing value 789.
*/

/**
Solution: Use Stack to Store outer NestedInteger List.
  [123,[456,[789]]]
  stack: [123]p, [456]p, 
  cur: [123]p -> [456]p -> [789]p -> [456, [789]]p -> [123, [456, [789]]]
	ANS = [123, [456, [789]]];
  Case digit: form a num add to cur NestedInteger.
  Case [: push cur NestedInteger to stack
  Case ]: pop prev from stack. add cur to prev NestedInteger. Cur = prev.
	
How to Arrive:
* Looking at the question, we need to create a list when encounter [. We add nums to this list, until it meets another [, which will be a inner list to add nums.
* Then, when we encounter ], the cur inner list is finished, and needs to add to the outer list.
* **Key**: We need to store the outer list, so when inner list finish, outer.add(inner); Create a nested list that way.
* A stack is good for this situation, just push outer lists. When pop, the inner level will pop first, which is the parent list we wanted to add cur inner list.
* Algorithm:
* If 1st char is not [, it is only one integer in NestedInteger;
```
return new NestedInteger(Integer.parseInt(s));
```
* Loop through chars.
	* Case digit: **Either positive or Negative '-'**: 0-9 or '-'
		* Since we need to handle both positive and negative, we cannot just * 10 and + digit; (negative is * 10 - digit); Instead we can just use a StringBuilder to append the digits and '-'; 
		* Parse the res str and add to cur NestedInteger list.
		* Notice: you can also check for ',' as end of a num and use l & r 2 pointers to create substring.
	* Case '[':
		* If cur NestedInteger != null: (cur = null means 1st [, no elm or list yet);
			* stack push cur.
		* Create a new inner list. Cur = new NestedInteger;
	* Case ']':
		* **Key**: stack only contains outer list; Not the inner most cur list. Last ']' have no parent, so not popping anything.
		* if stack is not empty: (stack empty means no more parent/outer list); 
			* pop out top outer list.
			* Add cur to outer list.
			* cur = outer; cur inner list is finished, continue with outer list.
* Return Cur;
* Time: O(n);
* Space: O(n);
*/

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *     // Constructor initializes an empty nested list.
 *     public NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     public NestedInteger(int value);
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // Set this NestedInteger to hold a single integer.
 *     public void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     public void add(NestedInteger ni);
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
class MiniParser {

  /*
  [123,[456,[789]]]
  stack: 
  cur: [123, [456, [789]]]
  Case digit: form a num add to cur NestedInteger.
  Case [: push cur NestedInteger to stack
  Case ]: pop prev from stack. add cur to prev NestedInteger. Cur = prev.
  */
  public NestedInteger deserialize(String s) {
    // Stack for strings
    Stack<NestedInteger> stack = new Stack<>();
    // Not a list; only one int, could be -# or +#;
    if (s.charAt(0) != '[') {
      return new NestedInteger(Integer.parseInt(s));
    }
    NestedInteger cur = null;
    // loop s
    for (int i = 0; i < s.length(); i++) {
      // if digit -# or +#
      if ((s.charAt(i) >= '0' && s.charAt(i) <= '9') || s.charAt(i) == '-') {
        // use stringBuilder b/c we need to handle negative append.
        // don't forget param is string not char
        StringBuilder sb = new StringBuilder(s.charAt(i) + "");
        // check next
        while (i + 1 < s.length() && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
          sb.append(s.charAt(i + 1));
          i++;
        }
        int val = Integer.parseInt(sb.toString());
        // add integer to list, in NestedInteger form.
        cur.add(new NestedInteger(val));
      } else if (s.charAt(i) == '[') {
        // open [, a new inner list
        // first [, new first list.
        if (cur != null) {
          stack.push(cur);
        }
        // new list
        cur = new NestedInteger();
      } else if (s.charAt(i) == ']') {
        // close ]
        if (!stack.isEmpty()) {
          NestedInteger outer = stack.pop();
          outer.add(cur);
          // cur inner list is finished, continue with outer
          cur = outer;
        }
      }
    }
    return cur;
  }
}