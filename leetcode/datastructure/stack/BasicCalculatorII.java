/**
227. Basic Calculator II
Implement a basic calculator to evaluate a simple expression string.

The expression string contains only non-negative integers, +, -, *, / operators and empty spaces . The integer division should truncate toward zero.

Example 1:

Input: "3+2*2"
Output: 7
Example 2:

Input: " 3/2 "
Output: 1
Example 3:

Input: " 3+5 / 2 "
Output: 5
Note:

You may assume that the given expression is always valid.
Do not use the eval built-in library function.
*/

/**
Solution 1: using 2 Stacks, 1 for Values, 1 for Opes
Ex:
// 30 + 20 * 4 - 30 / 6 * 2;
// Values: 30p, 20p, 4p, (20 * 4)80p, (30+80)110, 30p, 6p, (30/6)5, 2
// Opes: +p, * p, -, /p, *
// chars traverse end;
// Values: 110p, 5p, 2p, (5 * 2)10p,  (110-10) 100; Ans = 100;
// Opes: -p, * p;

* Let's walk through algorithm:
* Iterates through the chars in Str.
	* Case 0-9 digit:
		* Use a StringBuilder. Check if next is also a digit, if yes, append that char also.
		* Add the Int val of str to Values Stack.
	* Case +, -, * , / :
		* perform last ope with 2 nums on top of Values stack.
		* If opes not empty, and prev ope is has equal or higher precedence:
			* Values pop 2 nums.
			* Opes pop top ope.
			* Calc res in order (pop num2, pop num1) num1 ope num2;
			* Values stack push res;
* All chars in stacks, Any operation < or = last top ope in opes stack are already performed. So we just need to perform all opes from top down.
	* While opes is not empty:
		* Values pop 2 nums.
		* Opes pop top ope.
		* Calc res in order (pop num2, pop num1) num1 ope num2;
		* Values stack push res; 
* Time: O(n);
* Space: O(n);

------------
Solution 2: Store preVal, res sofar, calc * & / and store in preVal.
Ex:
// 30 + 20 * 4 - 30 / 6 * 2
// ope: +0, +30, +20, * 4, -30, /6, * 2
// prev: 0, 30, 20, 20 * 4=80, -30, -30/6= -5, -5 * 2= -10
// res: 0, 0+0=0, 0+30=30, 30+80=110, 110 + -10 = 100; ANS= 100;

* We store preVal to ResSofar. 
* Since + or - is lower precedence, prev ope perform first. So add preVal to Res.
* PreVal becomes curVal; if +20 = 20; if -20 = -20, Negative.
* Res is always 1 + or - ope behind. * or / is appended to preVal.
Ex: 30 + 20 - 10 * 2 / 5 + 1;  
Defaults: res=0, prev=0; ope= +; 
For +30: res = 0+0=0, preVal = 30;  
For +20: res = 0+30=30, preVal = 20;
For -10: res = 30+20(prev) = 50, preVal = -10; 
* Multiply & divide have higher precedence, need to be take cure first, 
* update prev = prev * or /  by cur, so for next + or -, res can add this val.
For * 2: preVal = -10 * 2 = -20;
For / 5: preVal = -20 / 5 = -4;
For +1: res = 50 + -4= 46; preVal = 1;
* Now all chars traversed, last val left.
Last val: 46 + 1 = 47;
Ans = 47;
* Time: O(n);
* Space: O(1);

*/


class BasicCalculatorII {

	/**
	 * Solution 2: store preVal, res sofar, calc * & / and store in preVal.
	 * Time: O(n)
	 * space: O(1)
	 */
	public int calculate(String s) {
    // 30 + 20 * 4 - 30 / 6 * 2
    // ope: +0, +30, +20, * 4, -30, /6, * 2
    // prev: 0, 30, 20, 20 * 4=80, -30, -30/6= -5, -5 * 2= -10
    // res: 0, 0+0=0, 0+30=30, 30+80=110, 110 + -10 = 100; ANS= 100;
    
    // get rid of spaces
    s = s.trim().replaceAll(" +", "");
    // calc res sofar
    int res = 0;
    // prev val to add to res
    int preVal = 0;
    // ope, default +
    char ope = '+';
    
    for (int i = 0; i < s.length(); i++) {
      int curVal = 0;
      // if digit
      if (Character.isDigit(s.charAt(i))) {
        curVal = Character.getNumericValue(s.charAt(i));
        // if next is also digit.
        while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
          int digit = Character.getNumericValue(s.charAt(i + 1));
          // val digits become next tens place
          curVal = curVal * 10 + digit;
          i++;
        }
      }
      // if ope is +
      if (ope == '+') {
        // append preVal to res, since + or - is lower precedence, prev ope perform first.
        res += preVal;
        // preVal becomes curVal
        preVal = curVal;
      } else if (ope == '-') {
        // append preVal to res, since + or - is lower precedence, prev ope perform first.
        res += preVal;
        // preVal becomes curVal but since its -, negative.
        preVal = -curVal;
      } else if (ope == '*') {
        // * or / have higher precedence, need to be take cure first, 
        // update prev = cur * prev, so for next + or -, res can add this val.
        preVal = preVal * curVal;
      } else {
        // ope == '/'
        // * or / have higher precedence, need to be take cure first, 
        // update prev = cur / prev, so for next + or -, res can add this val.
        preVal = preVal / curVal;
      }
      // find next ope
      while (i < s.length() - 1 && Character.isDigit(s.charAt(i))) {
        i++;
      }
      // last char will not be ope.
      if (i < s.length() - 1) {
        ope = s.charAt(i);
      }
    }
    // last ope
    res += preVal;
    return res;
  }
	
	/**
	 * Solution1: using 2 Stacks, 1 for Values, 1 for Opes
	 * Time: O(n)
	 * Space: O(n)
	 */
  public int calculate(String s) {
    // 30 + 20 * 4 - 30 / 6 * 2;
    // Values: 30p, 20p, 4p, (20*4)80p, (30+80)110, 30p, 6p, (30/6)5, 2
    // Opes: +p, *p, -, /p, *
    // chars traverse end;
    // Values: 110p, 5p, 2p, (5*2)10p,  (110-10) 100; Ans = 100;
    // Opes: -p, *p;
    
    // use char arr
    char[] chArr = s.toCharArray();
    // Values stack
    Stack<Integer> values = new Stack<>();
    // Opes stack
    Stack<Character> opes = new Stack<>();
    // iter chars
    for (int i = 0; i < chArr.length; i++) {
      // skip spaces
      if (chArr[i] == ' ') {
        continue;
      }
      // if digits
      if (chArr[i] >= '0' && chArr[i] <= '9') {
        StringBuilder numStr = new StringBuilder();
        numStr.append(chArr[i]);
        // if next is also digit
        while (i + 1 < chArr.length && chArr[i + 1] >= '0' && chArr[i + 1] <= '9') {
          numStr.append(chArr[i + 1]);
          // increment until next is not digit
          i++;
        }
        int val = Integer.parseInt(numStr.toString());
        values.push(val);
      } else {
        // opes * / - +
        // perform all prev opes that has = or > precedences
        while (!opes.isEmpty() && higherPrecedence(opes.peek(), chArr[i])) {
          int num2 = values.pop();
          int num1 = values.pop();
          char ope = opes.pop();
          int val = operation(num1, num2, ope);
          values.push(val);
        }
        // don't forget to push cur ope.
        opes.push(chArr[i]);
      }
    }
    // All chars in stacks, Any operation < last top ope in opes stack are already performed.
    // perform all rest
    while (!opes.isEmpty()) {
      int num2 = values.pop();
      int num1 = values.pop();
      char ope = opes.pop();
      int val = operation(num1, num2, ope);
      values.push(val);
    }
    // last value is result.
    return values.pop();
  }
  
  public boolean higherPrecedence(char ope1, char ope2) {
    if (ope1 == '*' || ope1 == '/') {
      return true;
    } else if ((ope1 == '+' || ope1 == '-') && (ope2 == '+' || ope2 == '-')) {
      return true;
    } else {
      return false;
    }
  }
  
  public int operation(int num1, int num2, char ope) {
    switch (ope) {
      case '-':
        return num1 - num2;
      case '+':
        return num1 + num2;
      case '*':
        return num1 * num2;
      case '/':
        if (num2 == 0) {
          try {
            throw new Exception("divide 0");
          } catch (Exception e) {
            System.out.println(e.toString());
          }
          
        } else {
          return (int) (num1 / num2);
        }
    }
    return 0;
  }
}