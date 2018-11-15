/**
5.2 Binary to String: Given a real number between 0 and 1 (e.g., 0.72) that is passed in as a double, print
the binary representation. If the number cannot be represented accurately in binary with at most 32
characters, print "ERROR:'
Hints: #143, #167, #173, #269, #297
*/

import java.util.*;

public class BinaryToString{
	/*An algorithm is said to be constant time (also written as O(1) time) if the value of T(n) is bounded by a value that does not depend on the size of the input.
	Despite the name "constant time", the running time does not have to be independent of the problem size, but an upper bound for the running time has to be bounded independently of the problem size.
	*/
	/*O(1); constant time b/c it doesn't deppend on the size of the input. always 1-31; ex: for(i=0; i<200; i++) its constant time.
	and nested loop like: for(i<100){  for(j<200){} } its also constant time; O(1) space
	*/
	public void decimal01ToBinary(double num){
		StringBuilder binary = new StringBuilder();
		binary.append("."); //decimal point
		double tmp = num;
		//T(1-31)
		while(tmp != 0){ //divide 2 till 0, get remindar
			if(binary.length() >= 32){
				System.out.println("ERROR");
				return;
			}
			if(tmp * 2 >= 1){ 
			// 0.75 * 2 = 1.5; res = 0.1???; 0.5 * 2...
				binary.append("1");
				tmp = tmp * 2 -1;
			}else{
			//0.25 * 2 = 0.5; res = 0.0???; 0.5 * 2...
				binary.append("0");
				tmp = tmp * 2;
			}

		}

		System.out.println( binary.toString());
	}

	public static void main(String [] args){
		BinaryToString obj = new BinaryToString();
		obj.decimal01ToBinary(0.703125);
	}
}