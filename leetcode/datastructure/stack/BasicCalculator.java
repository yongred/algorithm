/**
224. Basic Calculator
Implement a basic calculator to evaluate a simple expression string.

The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .

Example 1:

Input: "1 + 1"
Output: 2
Example 2:

Input: " 2-1 + 2 "
Output: 3
Example 3:

Input: "(1+(4+5+2)-3)+(6+8)"
Output: 23
Note:
You may assume that the given expression is always valid.
Do not use the eval built-in library function.
*/

/**
Solution: 2 stacks, 1 for values, 1 for opes.
Ex: 
200 + 100 - (300 - 200) + 2;
values: 200p, 100p, (200+100=)300p, 300p, 200p, (300-200)100p, (300-100)200p, 2p, (200+2)202
ope: +p, -p, (p, -p, +p

How to arrive:
* When we meet 2nd operator + or -, it means there is atleast 2 nums and 1 ope.
* So we need to calc prev 2 nums and ope first. And the res is going to be used for cur ope later.
* Stack is LIFO, so we can pop out last 2 nums, and last ope, perform and store res in Values stack. And push new ope in Operations stack.
Ex: 2 + 1 - #; Vals: 2,1, Opes: '+';  2+1 = 3; Vals: 3, Opes: '-';

* Let's walk through algorithm:
* Iterates through the chars in Str.
	* Case 0-9 digit:
		* Use a StringBuilder. Check if next is also a digit, if yes, append that char also.
		* Add the Int val of str to Values Stack.
	* Case ( open parenthese:
		* Push to Opes stack.
	* Case ) close parenthese:
		* Calc all opes inside cur ();
		* while opes.peek != '('
			* Values pop 2 nums.
			* Opes pop top ope.
			* Calc res in order (pop num2, pop num1) num1 ope num2;
			* Values stack push res;
		* Pop out '('
	* Case + or - :
		* perform last ope with 2 nums on top of Values stack.
		* If opes not empty, and prev is not '('
			* Values pop 2 nums.
			* Opes pop top ope.
			* Calc res in order (pop num2, pop num1) num1 ope num2;
			* Values stack push res;
		* Push cur ope.
* Finished all chars traverse. Only last ope left. Since we calc all prev opes when encounter + or -;
	* Perform last ope:
		* Values pop 2 nums.
		* Opes pop top ope.
		* Calc res in order (pop num2, pop num1) num1 ope num2;
		* Values stack push res; 
* Return last elm in Values stack, it is the res;
* Time: O(n);
* Space: O(n);
*/

class BasicCalculator {

	/**
	 * Solution: 2 stacks, 1 for values, 1 for opes.
	 * Time: O(n)
	 * Space: O(n)
	 */
  public int calculate(String s) {
    // 200 + 100 - (300 - 200) + 2;
    // values: 200p, 100p, (200+100=)300p, 300p, 200p, (300-200)100p, (300-100)200p, 2p, (200+2)202
    // ope: +p, -p, (p, -p, +p
    
    // get rid of spaces front and back.
    s = s.trim();
    char[] charArr = s.toCharArray();
    Stack<Integer> values = new Stack<>();
    Stack<Character> opes = new Stack<>();
    
    for (int i = 0; i < charArr.length; i++) {
      // skip spaces
      if (charArr[i] == ' ') {
        continue;
      }
      // if is digit
      if (charArr[i] >= '0' && charArr[i] <= '9') {
        // check if > 1 digits, append cur num digits.
        StringBuilder numStr = new StringBuilder();
        numStr.append(charArr[i]);
        // check if next is also digit
        while (i + 1 < charArr.length && charArr[i + 1] >= '0' && charArr[i + 1] <= '9') {
          numStr.append(charArr[i + 1]);
          i++;
        }
        // convert string to int.
        int val = Integer.parseInt(numStr.toString());
        values.push(val);
      } else if (charArr[i] == '(') {
        // open (
        opes.add(charArr[i]);
      } else if (charArr[i] == ')') {
        // close )
        // perform ( operations ) inside parenthese.
        // order of 2nd and 1st operands.
        while (opes.peek() != '(') {
          int num2 = values.pop();
          int num1 = values.pop();
          char ope = opes.pop();
          int res = operation(num1, num2, ope);
          // push newly calc num for later operations.
          values.push(res);
        }
        // don't forget to pop '('
        opes.pop();
      } else {
        // operators '-' or '+'
        // If prev ope exist AND If prev oper is not '(',
        // then, calc prev operations first. 
        if (!opes.isEmpty() && opes.peek() != '(') {
          int num2 = values.pop();
          int num1 = values.pop();
          char ope = opes.pop();
          int res = operation(num1, num2, ope);
          // push newly calc num for later operations.
          values.push(res);
        }
        // push cur ope
        opes.push(charArr[i]);
      }
    }
    // finished all prev operations, last left.
    if (!opes.isEmpty()) {
      int num2 = values.pop();
      int num1 = values.pop();
      char ope = opes.pop();
      int res = operation(num1, num2, ope);
      // push newly calc num for later operations.
      values.push(res);
    }
    // last val is the total.
    return values.pop();
  }
  
  public int operation(int num1, int num2, char ope) {
    switch (ope) {
      case '+':
        return num1 + num2;
        // break;
      case '-':
        return num1 - num2;
        // break;
      default:
        return 0;
    }
  }
  
}