
package reversepolishnotion;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public class Subtraction implements Operator {
	
	@Override
	public int operate(Stack<Integer> operandStack) throws Exception {
		if (operandStack.size() < 2) {
			throw new Exception("Need 2 operands");
		}
		int secondOperand = operandStack.pop();
		int firstOperand = operandStack.pop();
		return firstOperand - secondOperand;
	}
}