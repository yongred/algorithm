/**
Design a basic calculator, or arithmetic expression evaluator.
Given an input String, return an integer.

At first it should only handle addition and subtraction operators, but expect for additional operators to be added.
*/

/*
Qs:
- Is this Infix calc string? Like 3 + 2 - 4? Assume yes
  - Is there space? Assume no
- Empty string? Return 0
- All integers? Yes

Solution:
- Have 2 stacks one for values, one for operators.
- Loop over Str by chars, 
- When encounter 1st operator, add to operatorStack
- When encounter 2nd operator, calculate the result
- Since last char will be a number, need to make sure last operation is performed
  - So "3+2-" 3+2=5 calculated, 

Impl:
- Operator interface, and operation implement it
- OperatorParser to decide which operation object to return
- A stack to store values and operators


FollowUp:
- Ask them to handle multiply, divide, and parentheses, preserving operation order.

Ex: 30 + 40 / 20 - ((50 * 2 - 10) - 90)
30 + 2 - ((50 * 2 - 10) - 90)
32 - ((50 * 2 - 10) - 90)
32 - ((100 - 10) - 90)
32 - (90 - 90)
32 - 0
32

Solution Follow up:
- When encounter operator, 
  - if prev operator (stored in operatorStack) >= precedence than cur operator than calculate previous operation 
  - else add cur operator to operatorStack.
- When encounter '(' always add to operator stack
- When encounter ')' pop the last two operand and last operator to calculate
*/

import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;
import calculatordesign.*;

public class CalculatorDesign {
	private Deque<Integer> operandStack = new ArrayDeque<>();
	private Deque<Character> operatorStack = new ArrayDeque<>();

	public int calculate(String operation) throws Exception {
		if (operation == "" || operation == null) {
			return 0;
		}

		for (int i = 0; i < operation.length(); i++) {
			char curChar = operation.charAt(i);

			if (Character.isDigit(curChar)) {
				int[] numAndIndex = formNumFromDigits(operation, i);
				int num = numAndIndex[0];
				i = numAndIndex[1];
				operandStack.push(num);

			} else if (curChar == '(') {
				operatorStack.push(curChar);

			} else if (curChar == ')') {
				while (operatorStack.peek() != '(') {
					char operatorToken = operatorStack.pop();
					int result = performOperation(operatorToken);
					operandStack.push(result);
				}
				operatorStack.pop();

			} else {
				while (!operatorStack.isEmpty() && higherPrecedence(operatorStack.peek(), curChar)) {
					char operatorToken = operatorStack.pop();
					int result = performOperation(operatorToken);
					operandStack.push(result);
				}
				operatorStack.push(curChar);
			}
		}

		while (!operatorStack.isEmpty()) {
			char operatorToken = operatorStack.pop();
			int result = performOperation(operatorToken);
			operandStack.push(result);
		}

		return operandStack.pop();
	}

	private int[] formNumFromDigits(String operation, int index) {
		int num = operation.charAt(index) - '0';
		while (index+1 < operation.length() && Character.isDigit(operation.charAt(index+1))) {
			index++;
			int digit = operation.charAt(index) - '0';
			num = num * 10 + digit;
		}
		return new int[] {num, index};
	}

	private int performOperation(char operatorToken) throws Exception {
		Operator operator = OperatorParser.parse(operatorToken);
		return operator.operate(operandStack);
	}

	public boolean higherPrecedence(char ope1, char ope2) throws Exception {
	    if (ope1 == '(') {
	      return false;
	    }
	    Operator operator1 = OperatorParser.parse(ope1);
	    Operator operator2 = OperatorParser.parse(ope2);
	    return operator1.getPrecedence() > operator2.getPrecedence();
	 }

	public static void main(String[] args) {
		CalculatorDesign calculator = new CalculatorDesign();
		String operation = "30 + 40 / 20 - ((50 * 2 - 10) - 90)";
		operation = operation.replaceAll("\\s", "");
		try {
			int result = calculator.calculate(operation);
			System.out.println("Result " + result);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}

}