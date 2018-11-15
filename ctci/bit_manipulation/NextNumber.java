/**
5.4 Next Number: Given a positive integer, print the next smallest and the next largest number that
have the same number of 1 bits in their binary representation.
Hints: #147, # 175, #242, #312, #339, #358, #375, #390

wording is unclear: Next largest: smallest > num, w/ same 1s.
	Next smallest: means largest < num, same 1s
	ex: if n = 4; nextLargest: 5, nextSmallest: 3
*/

public class NextNumber{
	//O(b) times, O(1) space
	public void nextLarger(int num){
	
		int tmp = num;
		int fst0aft1s = -1, right1sCount = 0; //for smallest > n
		int i=0;
		int prev = -1; // prev digit
		//T(b)
		while(tmp != 0){
			if((tmp & 1) == 0){  //first/cur digit is 0
				if(prev == 1 && fst0aft1s == -1){ //if last digit is 1, and fst0aft1s not assigned yet
					fst0aft1s = i;  //i is fst0aft1s
				}
				prev = 0;
			}
			else if((tmp & 1) == 1){ //cur digit is 1
				
				if(fst0aft1s == -1){ //count right 1s before fst0aft1
					right1sCount++;
				}
				prev = 1;
			}
			i++;
			tmp >>= 1; //
		}

		printNextLarge(num, fst0aft1s, right1sCount);

	}


	public void printNextLarge(int num, int fst0aft1s, int onesCount){
		if(fst0aft1s == -1){
			//no 0s after 1
			System.out.println("printNextLarge: " + Integer.toBinaryString(num));
			return;
		}
		//System.out.println(fst0aft1s);
		int i = fst0aft1s;

		int all1s = ~0;
		int mask = all1s << i;  //111..i000
		int left = (num & mask) | (1 << i);  // 1101010000 + i000 = 110101i000, clear rights and flip bit-i 0 to 1
		int right = (1 << onesCount-1) - 1;  // 0000i000 - 1 = 00000111
		int nextLarge = left | right; // combine
		System.out.println("printNextLarge: " + Integer.toBinaryString(nextLarge));
	}

	public void nextSmaller(int num){
		int tmp = num;
		int fst1aft0s = -1, right1s = 0;
		int prev = -1;
		int i = 0;
		while(tmp != 0){
			if((tmp & 1) == 1){
				if(prev == 0 && fst1aft0s == -1){
					fst1aft0s = i;
				}
				if(fst1aft0s == -1){
					right1s++;
				}
				prev = 1;
			}
			else if((tmp & 1) == 0){
				prev = 0;
			}
			i++;
			tmp >>=1;

		}

		printNextSmall(num, fst1aft0s, right1s);

	}

	public void printNextSmall(int num, int fst1aft0s, int right1s){
		if(fst1aft0s == -1){
			//no 0s after 1
			System.out.println("printNextSmall: " + Integer.toBinaryString(num));
			return;
		}
		int i = fst1aft0s;
		int all1s = ~0;
		int mask = all1s << (i+1); //include i, 111..(i+1)0000
		int left = num & mask; // clear right part include i
		mask = (1 << (right1s+1)) -1; // i0000 - 1 = 01111, one more 1 for fliped bit
		int right = mask << (i - (right1s+1)); //rest digits before i is 0, 11010..i1111000
		int smaller = left | right;
		System.out.println("smaller: " +Integer.toBinaryString(smaller));
	}

	public static void main(String [] args){
		NextNumber obj = new NextNumber();
		int n = 55751;
		System.out.println(Integer.toBinaryString(n));
		obj.nextLarger(n);
		obj.nextSmaller(n);
	}
}