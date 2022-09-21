/**
We can define a calculator notation in which the operators follow the operands, for example where a normal calculator input would be input as (2 + 3) * 5, our calculator can take input like 2 3 + 5 * to avoid the use of parenthesis.

The task is to implement this calculator for plus, minus, multiply and divide operations.
Assume the input will be passed as an array of String => ["16","5","+"].

Reverse Polish notation

https://www.geeksforgeeks.org/evaluate-the-value-of-an-arithmetic-expression-in-reverse-polish-notation-in-java/


Follow up questions as the interviewer for L5 SDE data points:

- Now add square root operations (twist because it tests logical and maintainable because if building assuming it would always be 2, they would have to re-write a lot of code and it only takes 1

- Now build an interface for users to define their own operations 
  - (this is where L5 becomes bar raising, now the concepts are introduced with operators with 1 operand, but what if we take the calc to the next level - users finding their own functions. 
  - How would we write a function that takes in 4 operands instead? We are looking for them to realize they should write a functional that generically handles this RPN format and make it logical/maintainable. 
  - At the end of the exercise, the candidate should have code with a function that is only responsible for RPN (Reverse Polish Notion) processing and an interface and method of definition of any user defined function
*/

/**
Qs:
- Inputs:
  - Null input: return 0.
  - Empty array: return 0
  - number with decimal? Assume no. Use remainder 1/2 = 0 remainder 1
  - Invalid input? Assume no.
  - negative number? Assume yes.
  - Incorrect order like 2 + 3 instead of 2 3 +: throw exception.
- Output:
  - just return the result.

Could be more than 2 operands follow by an operator
Input: ["4", "13", "5", "/", "+"]
Output: 6
Explanation: (4 + (13 / 5)) = 6

Follow up sqrt:
Input: ["4", "13", "5", "/", "+", "9", "sqrt", -]
Explanation: (4 + (13 / 5)) - sqrt(9) = 3

*/

import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;
import reversepolishnotion.*;

public class CalculatorNotation {
	// use stack, ex: 4 13 5 / +
	// Stack: push 4, push 13, push 5, / pop 5 13, 13 / 5 = 2, push 2, + pop 2 4, 4 + 2 = 6, push 6

	private Stack<Integer> operandStack = new Stack<>();

	public int calculate(String[] numbersAndOperators) {
		if (numbersAndOperators == null || numbersAndOperators.length == 0) {
			return 0;
		}

		for (String numOrOperator : numbersAndOperators) {
			if (isNumeric(numOrOperator)) {
				operandStack.push(Integer.parseInt(numOrOperator));
			} else {
				int result = operate(numOrOperator);
				operandStack.push(result);
			}
		}

		return operandStack.pop();
	}

	/*
	 follow up with Operator Interface
	*/
	public int calculateWithInterfaceFollowUp(String[] numbersAndOperators) throws Exception {
		if (numbersAndOperators == null || numbersAndOperators.length == 0) {
			return 0;
		}

		for (String numOrOperator : numbersAndOperators) {
			if (isNumeric(numOrOperator)) {
				operandStack.push(Integer.parseInt(numOrOperator));
			} else {
				Operator operator = OperatorParser.parse(numOrOperator);
				int result = operator.operate(operandStack);
				operandStack.push(result);
			}
		}

		return operandStack.pop();
	}

	/*
	Utils
	*/
	private boolean isNumeric(String numOrOperator) {
		try {
			int number = Integer.parseInt(numOrOperator);
		} catch (NumberFormatException numberFormatException) {
			return false;
		}
		return true;
	}

	private int operate(String operator) {
		int[] operands = determineOperands(operator);

		switch (operator) {
			case "+":
				return operands[0] + operands[1];
			case "-":
				return operands[0] - operands[1];
			case "*":
				return operands[0] * operands[1];
			case "/":
				return operands[0] / operands[1];
			case "sqrt":
				return (int) Math.sqrt(operands[0]);
			default:
				return 0;
		}
	}

	private int[] determineOperands(String operator) {
		if (operator.equals("sqrt")) {
			return new int[] { operandStack.pop() };
		} else {
			int secondNumber = operandStack.pop();
			int firstNumber = operandStack.pop();
			return new int[] { firstNumber, secondNumber };
		}
	}

	public static void main(String[] args) {
		CalculatorNotation calculatorNotation = new CalculatorNotation();
		String[] calculateInput = new String[] {"4", "13", "5", "/", "+"};
		int result = calculatorNotation.calculate(calculateInput);
		System.out.println("Result " + result);

		String[] calculateInput2 = new String[] {"4", "13", "5", "/", "+", "9", "sqrt", "-"};
		int resultSqrt = calculatorNotation.calculate(calculateInput2);
		System.out.println("Result sqrt " + resultSqrt);

		try {
			int resultWithInterface = calculatorNotation.calculateWithInterfaceFollowUp(calculateInput2);
			System.out.println("Result interface follow up " + resultWithInterface);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}