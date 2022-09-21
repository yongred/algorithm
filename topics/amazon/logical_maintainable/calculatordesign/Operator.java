
package calculatordesign;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public interface Operator {
	public int operate(Deque<Integer> operandStack) throws Exception;
	public int getPrecedence();
}