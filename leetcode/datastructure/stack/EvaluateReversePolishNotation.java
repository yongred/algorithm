/**
150. Evaluate Reverse Polish Notation
Evaluate the value of an arithmetic expression in Reverse Polish Notation.

Valid operators are +, -, *, /. Each operand may be an integer or another expression.

Note:

Division between two integers should truncate toward zero.
The given RPN expression is always valid. That means the expression would always evaluate to a result and there won't be any divide by zero operation.
Example 1:

Input: ["2", "1", "+", "3", "*"]
Output: 9
Explanation: ((2 + 1) * 3) = 9
Example 2:

Input: ["4", "13", "5", "/", "+"]
Output: 6
Explanation: (4 + (13 / 5)) = 6
Example 3:

Input: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
Output: 22
Explanation: 
  ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
= ((10 * (6 / (12 * -11))) + 17) + 5
= ((10 * (6 / -132)) + 17) + 5
= ((10 * 0) + 17) + 5
= (0 + 17) + 5
= 17 + 5
= 22
*/

/**
Solution: use Stack to store #s.
Ex: 7,3,2,+,-; equals 7 - (3+2) = 2;

How to arrive:
* Reverse Polish Nation, opes always after nums, atleast 2 nums before a ope for the calc.
* We store #s in a stack, when encounter a ope, we pop out 2 nums in stack and calc that ope in the correct order.
```
int num2 = nums.pop();
int num1 = nums.pop();
// calc 2 nums on top.
int val = operation(num1, num2, ope);
nums.push(val);
```
* The res calculated store in stack for next ope calc.
* The last val in stack is the total.
* Time : O(n);
* Space: O(n);
*/

class EvaluateReversePolishNotation {

	/**
	 * Solution: use Stack to store #s.
	 * Time: O(n);
	 * Space: O(n)
	 */
  public int evalRPN(String[] tokens) {
    // # stack
    Stack<Integer> nums = new Stack<>();
    // loop tokens
    for (int i = 0; i < tokens.length; i++) {
      String curStr = tokens[i];
      // check is is num (- or + #), store int in stack.
      if ((curStr.charAt(0) >= '0' && curStr.charAt(0) <= '9') 
          || (curStr.length() > 1 && curStr.charAt(1) >= '0' && curStr.charAt(1) <= '9')) {
        int val = Integer.parseInt(curStr);
        nums.push(val);
      } else {
        // opes + - * /
        // if ope, pop 2 num, calc in num1 ope num2 order. store in res.
        int num2 = nums.pop();
        int num1 = nums.pop();
        // calc 2 nums on top.
        int val = operation(num1, num2, curStr);
        nums.push(val);
      }
    }
    // last elm in stack is total.
    return nums.pop();
  }
  
  public int operation(int num1, int num2, String ope) {
    switch (ope) {
      case "+":
        return num1 + num2;
      case "-":
        return num1 - num2;
      case "*":
        return num1 * num2;
      case "/":
        return num1 / num2;
    }
    return 0;
  }
}