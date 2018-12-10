/**
849. Basic Calculator III
Implement a basic calculator to evaluate a simple expression string.

The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .

The expression string contains only non-negative integers, +, -, *, / operators , open ( and closing parentheses ) and empty spaces . The integer division should truncate toward zero.

You may assume that the given expression is always valid. All intermediate results will be in the range of [-2147483648, 2147483647]

Example
"1 + 1" = 2
" 6-4 / 2 " = 4
"2*(5+5*2)/3+(6/2+8)" = 21
"(2+6* 3+5- (3*14/7+2)*5)+3"=-12
Notice
Do not use the eval built-in library function.
*/

/**
Solution: 2 stacks, 1 for values, 1 for opes.
Ex: 
200 + 100 - 300 / (200 + 100) * 2;
values: 200p, 100p, (200+100)300, 300p, 200p, 100p, (200+100)300p, (300/300)1, 2
ope: +p, -, /p, (p, +p, * ;
// Finish all chars traverse, add all nums and opes to stacks. Only opes < than top ope * have left.
300 - 1 * 2;
values: 300p, 1p, 2p, (1 * 2)2p, (300-2)298; RES = 298.
opes: -p, * p

How to arrive:
* When we meet 2nd operator +, -, * , /,  it means there is atleast 2 nums and 1 ope.
* If the prev ope have = or > precedence, we need to calc prev 2 nums and ope first. And the res is going to be used for cur ope later.
* So we will need a function to check precedence of * / - +;
* Stack is LIFO, so we can pop out last 2 nums, and last ope, perform and store res in Values stack. And push new ope in Operations stack.
Ex: 2 + 1 * 3; Vals: 2,1p,3p Opes: '+', ' * 'p ; '+' < ' * ' so 1 * 3 = 3, save it  Values: 2, 3; opes: +; 2+3 = 5;

* Let's walk through algorithm:
* Iterates through the chars in Str.
	* Case 0-9 digit:
		* Use a get Int val of char.
		* While next is also digit, append to cur val. (Can use Char - '0');
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
	* Case +, - , * , /:
		* perform last ope with 2 nums on top of Values stack.
		* while opes not empty, and prev is not '(', and prev ope has > or = precedence.
			* Values pop 2 nums.
			* Opes pop top ope.
			* Calc res in order (pop num2, pop num1) num1 ope num2;
			* Values stack push res;
		* Push cur ope.
* All chars in stacks, and all prev > or = precendence opes are calculated. Perform rest ope in top down order of stack.
	* While opes Stack is not empty:
		* Values pop 2 nums.
		* Opes pop top ope.
		* Calc res in order (pop num2, pop num1) num1 ope num2;
		* Values stack push res; 
* Return last elm in Values stack, it is the res;
* Time: O(n);
* Space: O(n);
*/

public class BasicCalculatorIII {

  /**
   * @param s: the expression string
   * @return: the answer
   * Solution1: using 2 Stacks, 1 for Values, 1 for Opes
	 * Time: O(n)
	 * Space: O(n)
   */
  public int calculate(String s) {
    char[] chArr = s.toCharArray();
    // use stacks values and opes
    Stack<Integer> values = new Stack<>();
    Stack<Character> opes = new Stack<>();
    // loop through charArr
    for (int i = 0; i < chArr.length; i++) {
      // skip spaces.
      if (chArr[i] == ' ') {
        continue;
      }
      // if digit, form a Number
      if (chArr[i] >= '0' && chArr[i] <= '9') {
        int val = chArr[i] - '0';
        while (i + 1 < chArr.length && 
            chArr[i + 1] >= '0' && chArr[i + 1] <= '9') {
          int digit = chArr[i + 1] - '0';
          val = val * 10 + digit;
          i++;
        }
        values.push(val);
      } else if (chArr[i] == '(') {
        // if ( add to stack values
        opes.push(chArr[i]);
      } else if (chArr[i] == ')') {
        // if ), while not (,  2nums 1 ope
        while (opes.peek() != '(') {
          int num2 = values.pop();
          int num1 = values.pop();
          char ope = opes.pop();
          int val = operation(num1, num2, ope);
          values.push(val);
        }
        // pop '('
        opes.pop();
      } else {
        // if opes, prev opes not empty and higher or = precendence. do prev opes.
        while (!opes.isEmpty() && higherPrecedence(opes.peek(), chArr[i])) {
          int num2 = values.pop();
          int num1 = values.pop();
          char ope = opes.pop();
          int val = operation(num1, num2, ope);
          values.push(val);
        }
        // push cur ope.
        opes.push(chArr[i]);
      }
    }
    // all chars in stacks, and all prev > or = precendence opes are calculated.
    // calc rest of the opes.
    while (!opes.isEmpty()) {
      int num2 = values.pop();
      int num1 = values.pop();
      char ope = opes.pop();
      int val = operation(num1, num2, ope);
      values.push(val);
    }
    // last value left is total.
    return values.pop();
  }
  
  public boolean higherPrecedence(char ope1, char ope2) {
    if (ope1 == '(') {
      // we don't count () opes
      return false;
    } else if ((ope2 == '*' || ope2 == '/') && (ope1 == '+' || ope1 == '-')) {
      return false;
    } else {
      return true;
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
        return num1 / num2;
    }
    return 0;
  }
  
  
  
  
}