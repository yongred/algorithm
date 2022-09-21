
package reversepolishnotion;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public class SquareRoot implements Operator {
	
	@Override
	public int operate(Stack<Integer> operandStack) throws Exception {
		if (operandStack.size() < 1) {
			throw new Exception("Need 1 operand");
		}
		int operand = operandStack.pop();
		return (int)Math.sqrt(operand);
	}
}