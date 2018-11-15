package cisc3220Alg;

import java.util.Arrays;

/**
 * Consider the problem of adding two n-bit binary integers, stored in two n-element arrays A and B.
 *  The sum of the two integers should be stored in binary form in an (n + 1)-element array C. 
 * State the problem formally and write pseudocode for adding the two integers.
 * @author YongLiu
 *
 */
public class Hw2p1q4 {
	public int [] binarySum(int[] A, int[] B, int n){
		//C is for answer and carry bits
		int [] C = new int [n+1];
		Arrays.fill(C, 0); //init array C with 0s
		
		for(int i =0; i< n; i++){
			//sum for the two binary ints and carry
			int sum = A[i] + B[i] + C[i];
			C[i] = sum % 2; //current digit
			C[i+1] = sum / 2; //carry bits, to next digit
		}
		return C;
	}
	
	public static void main(String [] args){
		//read from right to left 1101 -> 1011, 1100 -> 0011 in binary
		int [] A = {0,1,1,1}; //1110
		int [] B = {1,0,1,0};
		int [] C = new Hw2p1q4().binarySum(A, B, A.length);
		for(int i=A.length; i>=0; i--){
			System.out.print(C[i]);
		}
	}
}
