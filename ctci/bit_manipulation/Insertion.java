/**
5.1 Insertion: You are given two 32-bit numbers, N and M, and two bit positions, i and j. 
Write a method to insert M into N such that M starts at bit j and ends at bit i. You can assume that the bits j through i have enough space to fit all of M. That is, if
M = 10011, you can assume that there are at least 5 bits between j and i. You would not, for
example, have j = 3 and i = 2, because M could not fully fit between bit 3 and bit 2.
EXAMPLE
Input: N = 10,000,000,000 M= 10011, i= 2, j= 6
Output: N = 10,001,001,100
Hints: # 137, #169, #215
*/

public class Insertion{
	//T(i + j), O(n) time; O(1) space
	public int insert(int N, int M, int i, int j){
		int res = N; //
		//from i to j
		for(int Nind = i, Mind=0; Nind <= j; Nind++, Mind++){
			//get value bit from m first
			int mask = 1 << Mind; //1000
			boolean not0 = (M & mask) != 0;
			int value = not0 ? 1 : 0;
			//clear the bit-i for N
			int n_mask = ~(1 << Nind); //11101111
			int clearI = (res & n_mask); //use multiply to clear
			res = clearI | (value << Nind); //shift value to that bit, and add it to 0
		}

		return res;
	}

	//O(1) time; O(1) space
	public int insert2(int N, int M, int i, int j){
		//create a mask like: 11110000011 to clear middle bits,
		//create left: 11110000000 + right : 011
		int allOnes = ~0; // 111111111111111
		int left = allOnes << (j+1); //include j, 11110000000
		//System.out.println(Integer.toBinaryString(left));
		int right = (1 << i) -1; //100 -1 = 011;
		int mask = left | right; // add them, 11110000011
		//System.out.println(Integer.toBinaryString(mask));
		int clearedN = N & mask; // multiply to clear middle
		//System.out.println(Integer.toBinaryString(clearedN));
		int shiftedM = M << i; //10011 << 2 = 1001100, shift to i to do addition
		return clearedN | shiftedM; //10,000,000,000 + 1,001,100 = 10,001,001,100
	}

	public static void main(String [] args){
		Insertion obj = new Insertion();
		int n = 1789; 
		int m = 19; // 10011
		int i = 2;
		int j = 6;
		int res = obj.insert(n,m,i,j);
		int res2 = obj.insert2(n,m,i,j);
		System.out.println(" N " +Integer.toBinaryString(n));
		System.out.println(" M " +Integer.toBinaryString(m));
		System.out.println(" insert " +Integer.toBinaryString(res));
		System.out.println("insert2 " + Integer.toBinaryString(res2));
	}
}






