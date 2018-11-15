/**
5.3 Flip Bit to Win: You have an integer and you can flip exactly one bit from a 13 to a 1. Write code to
find the length of the longest sequence of ls you could create.
EXAMPLE
Input: 1775 (or: 11011101111)
Output: 8
Hints: #159, #226, #314, #352
*/

public class FlipBitToWin{
	public int longest1s(int num){
		//toBinaryString take O(b) time
		String binary = Integer.toBinaryString(num);
		int longest = 0;
		int left =0, right = 0;
		boolean midZero = false;

		for(int i=0; i< binary.length(); i++){
			int mask = 1 << i;
			int value = (num & mask) != 0 ? 1 : 0; 
			if(i >= binary.length() -1){
				if(longest < right + left + 1)
					longest = right + left + 1;
				break;
			}
			if(value == 0){
				if(midZero == false)
					midZero = true;
				else{  //second 0
					if(longest < right + left + 1)
						longest = right + left + 1;
					right = left;
					left = 0;
				}
			}
			else{  // value = 1
				if(midZero)
					left++;
				else
					right++;
			}
		}
		return longest;
	}

	/*
	1101110111
	*/

	public int flipBit(int n){
		if(~n == 0) return Integer.BYTES * 8;

		int front = 0;
		int back = 0;
		int maxLen = 1;
		//n = 1101110111
		while(n != 0){ // n -> ..0000110 -> ..0000010 -> ..->00000
			if((n & 1) ==1){  //1101110111 & 00001 = 00001
				front++;
			} else if((n & 1) == 0){ //0001101110 & 00001 = 00000 
				//if encounter 0, front start new count/new front, the old front is now the back, if next is also 0, set back to 0, b/c 00 no 1 between
				// 10011 & 00010 = 00010; next is 1 or 0
				back = (n & 2) == 0 ? 0 : front;
				front = 0;
			}
			maxLen = Math.max(prevLen + curLen + 1, maxLen);
			n >>>= 1;  //10011 -> 01001
		}
		return maxLen;
	}

	public static void main(String [] args){
		FlipBitToWin obj = new FlipBitToWin();
		int n = 1203;
		System.out.println("num: " + Integer.toBinaryString(n));
		System.out.println(obj.longest1s(n));
		System.out.println(obj.flipBit(n));
	}
}