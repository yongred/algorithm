/**
common bit tasks: get, set, clear 

*/

public class BitTasks{

	//right most are bit-0, so 1010 i[3210];
	//use 0th bit: mask = 1 << i;

	public int getBit(int num, int i){
		// to get bit-i: first shift 1 to bit-i; 
		// 1 << i, if i = 3, creates 001000
		// then AND num to it to see if its 1 or 0, Anything & 1 will be itself, 0 & 1 = 0, 1 & 1= 1; 
		// (like multiply by 1), other pos will become 0s, since 0 & any = 0s;
		// Ex: 0101, i=2, 1 << 2 = 0100; (0101 & 0100) = 0100; Not 0, so it's 1; 
		// If (0001 & 0100) = 0000; Is 0, return 0;
		return (num & (1 << i)) != 0 ? 1 : 0;
	}

	// turn on bit. Turn bit to 1;
	public int setBit(int num, int i){
		//set bit-i to 1: first shift to bit-i, then OR 1 to bit-i, (0 | 1 = 1); (1 | 1 = 1); (like addition)
		// Or 1 with any bit, it becomes 1; Other pos stays the same, since 0 | any = itself.
		// Ex: 0001, i=2, 1 << 2 = 0100; (0001 | 0100 = 0101);
		return num | (1 << i);
	}
	public int clearBit(int num, int i){
		//clear bit at i to 0
		// opposite of setBit.
		//create 001000 then flip to 110111
		int mask = ~(1 << i);  
		//think of it as multiply, i bit is 0, it will always be 0 * by watever. others are 1 so 1 * by anything is itself.
		// Ex: 1101 i=2; (1101 & 1011 = 0001);
		return num & mask; 
	}

	public int clearLeftOfI(int num, int i){
		// clear leftside inclusive i pos.
		// 00001000 - 1 = 00000111
		int mask = (1 << i) -1; 
		// create all 0s on leftside till i (inclusive), b/c 0 & any = 0 (clear);
		// Ex: 11101 i=3; mask= 01000-1= 00111; (11101 & 00111 = 00101);
		return num & mask;
	}

	public int clearRightOfI(int num, int i){
		// clear Right side include i pos.
		//we need to make mask like: 1111000; right bits to be 0 so it clears those bits, 
		// so use -1: 111111 to shift left to create something like 111000
		int mask = (-1 << i+1); //+1 b/c i is included, make bit-i 0; 11111000
		// Ex: 101101 i=2; mask= 111111 << (2+1)= 111000; (101101 & 111000 = 101000)
		return num & mask;

	}

	public int updateBit(int num, int i, boolean is1){
		// Update bit-i to value; 
		// first we clear bit-i to 0; Using clearMask 110111;
		// then we OR clearedNum with assign val
		int value = (is1) ? 1 : 0; // the value to assign to i.
		int clearMask = ~(1 << i); // make 1101111 to clear pos i to 0;
		// (num & clearMask) clear i to 0; (val << i) shifts val to i pos. Then OR val to i pos.
		// Ex: 10010 i=3 val=1; clearMask = 10111, (10010 & 10111= 10010); valShift = 01000; (10010 | 01000) = 11010;
		return (num & clearMask) | (value << i);
	}

	public static void main(String [] args){
		BitTasks obj = new BitTasks();
		System.out.println(Integer.toBinaryString(127) + " getBit: " + Integer.toBinaryString(obj.getBit(127, 3)) );
		System.out.println(Integer.toBinaryString(16) + " setBit: " + Integer.toBinaryString(obj.setBit(16, 3)) );
		System.out.println( Integer.toBinaryString(127) + " clearBit: " + Integer.toBinaryString(obj.clearBit(127, 3)) );
		System.out.println(Integer.toBinaryString(127) + " clearLeftOfI: " + Integer.toBinaryString(obj.clearLeftOfI(127, 3)) );
		System.out.println(Integer.toBinaryString(127) + " clearRightOfI: " + Integer.toBinaryString(obj.clearRightOfI(127, 3)) );
		System.out.println(Integer.toBinaryString(127) + " updateBit: " + Integer.toBinaryString(obj.updateBit(127, 3, false)) );
	}
}