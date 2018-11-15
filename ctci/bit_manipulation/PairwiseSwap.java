/**
5.7 Pairwise Swap: Write a program to swap odd and even bits in an integer with as few instructions as
possible (e.g., bit 0 and bit 1 are swapped, bit 2 and bit 3 are swapped, and so on).
Hints: #145, #248, #328, #355
*/

public class PairwiseSwap{
	//O(1) times. O(1) space; 5 instructions total ( &,&,>>>,<<,| )
	public int pairSwap(int num){
		//to get all evens, num & 101010..; Odds = num & 010101..
		int evens = (num & 0xAAAAAAAA);  //1010,1010 = 0xAA;
		int odds = (num & 0x55555555);  //0101,0101 = 0x55;
		int newOdds = evens >>> 1;  //1010 >>> 1 = 0101;
		int newEvens = odds << 1;  //0101 << 1 = 1010;
		return newOdds | newEvens;
	}

	public static void main(String [] args){
		PairwiseSwap obj = new PairwiseSwap();
		int n = obj.pairSwap(89);

		System.out.println(Integer.toBinaryString(89));
		System.out.println(Integer.toBinaryString(n));
	}
}