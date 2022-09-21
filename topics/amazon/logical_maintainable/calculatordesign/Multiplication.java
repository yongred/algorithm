
package calculatordesign;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public class Multiplication implements Operator {

	public final int precedence = 2;

	@Override
	public int operate(Deque<Integer> operandStack) throws Exception {
		if (operandStack.size() < 2) {
			throw new Exception("Need 2 operands");
		}
		int secondOperand = operandStack.pop();
		int firstOperand = operandStack.pop();
		return firstOperand * secondOperand;
	}

	@Override
	public int getPrecedence() {
		return precedence;
	}
}