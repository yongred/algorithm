
package calculatordesign;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public class OperatorParser {
	
	public static Operator parse(Character operatorToken) throws Exception {
		switch (operatorToken) {
			case '+':
				return new Addition();
			case '-':
				return new Subtraction();
			case '*':
				return new Multiplication();
			case '/':
				return new Division();
			default:
				throw new Exception("No such operator");
		}
	}
}