/**
5.6 Conversion: Write a function to determine the number of bits you would need to flip to convert
integer A to integer B.
EXAMPLE
Input:  29 (or: 11101), 15 (or: 01111)
Output:  2

Hints: #336, #369
*/

public class Conversion{
	//O(b) times, O(1) space
	public int bitsToConvert(int num1, int num2){
		//do XOR so the difference bits will res in 1, same bits is 0, like subtraction
		int res = num1 ^ num2; //ex: 10011 ^ 01010 = 11001; 3 bits differ
		int count = 0;
		//T(b)
		while(res != 0){
			if((res & 1) != 0) //1, differ bit
				count++;
			res >>>=1;
		}
		return count;
		/*
		can also do, c & (c-1) will clear the least sign bit, 
		ex: 11010 -1 = 11001; 11010 & 11001 =11000; b/c 100 -1 = 011; 100 & 011 = 0; cleared rightmost 1
		
		for (int c = a A b; c != 0; c = c & (c-1) ) {
			count++;
	 	}
		*/
	}

	public static void main(String [] args){
		Conversion obj = new Conversion();
		int A = 24, B= 18;
		int ans = obj.bitsToConvert(A, B);
		System.out.println(Integer.toBinaryString(A) + " "+ Integer.toBinaryString(B));
		System.out.println(ans);
	}
}