
/**
- Now build an interface for users to define their own operations 
  - (this is where L5 becomes bar raising, now the concepts are introduced with operators with 1 operand, but what if we take the calc to the next level - users finding their own functions. 
  - How would we write a function that takes in 4 operands instead? We are looking for them to realize they should write a functional that generically handles this RPN format and make it logical/maintainable. 
  - At the end of the exercise, the candidate should have code with a function that is only responsible for RPN (Reverse Polish Notion) processing and an interface and method of definition of any user defined function
*/

package reversepolishnotion;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public interface Operator {
	public int operate(Stack<Integer> operandStack) throws Exception;
}