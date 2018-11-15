/**
common bit tasks: get, set, clear 

*/

public class BitTasks{

	//right most are bit-0, so 1010 is 3rd bit to 0th bit
	//use 0th bit: mask = 1 << i;
	//right most is bit-1, so 1010 is 4th to 1st
	//use 1st bit: mask = 1 << i-1;

	public int getBit(int num, int i){
		//to get bit-i: first shift 1 to bit-i; 
		//then multiply(AND) num to it to see if its 1 or 0, since anything * 1 = itself
		int mask = 1 << i; //create 1000
		boolean not0 = (num & mask) != 0; //besides bit-i anything is 0, since mask only has bit-i as 1, others are 0s
		return (not0) ? 1 : 0;
	}

	public int setBit(int num, int i){
		//set bit-i to 1: first shift to bit-i, then OR(add) 1 to bit-i, 0 will become 1, 1 + 1 will stays 1
		int mask = 1 << i; //creat 1000
		return num | mask; //add
	}
	public int clearBit(int num, int i){
		//clear bit at i to 0
		//create 001000 then flip to 110111
		int mask = ~(1 << i);  
		//think of it as multiply, i bit is 0, it will always be 0 * by watever. others are 1 so 1 * by anything is itself.s
		return num & mask; 
	}

	public int clearThroughI(int num, int i){
		//ex: 00001000 - 1 = 00000111; anything i and before i is cleared b/c anything * 0 = 0; but anything after i bit is preserved, since anything * 1 is itself
		int mask = (1 << i) -1; //00000111
		//System.out.println(Integer.toBinaryString(mask));
		return num & mask;
	}

	public int clearIThrough0(int num, int i){
		//include i and 0, clear all bits from i - 0; 
		//we need to make mask like: 1111000; right bits to be 0 so it clears those bits, so use -1: 1111 to shift left to create something like 1000
		int mask = (-1 << i+1); //+1 b/c i is included, make bit-i 0; 11111000
		return num & mask;

	}

	public int updateBit(int num, int i, boolean is1){
		//to update bit-i to value; first we clear bit-i to 0; 
		//then we assign value to position i
		//use 1101111 to clear bit-i, then use | to add 10000 (1<< i)
		int value = (is1) ? 1 : 0;
		int mask = ~(1 << i); //make 1101111
		int clearedI = num & mask;  //multiply to clear i
		return clearedI | (value << i); //add clearedI to v0000(v << i) to assign value to pos i
	}

	public static void main(String [] args){
		BitTasks obj = new BitTasks();
		System.out.println(Integer.toBinaryString(127) + " getBit: " + Integer.toBinaryString(obj.getBit(127, 3)) );
		System.out.println(Integer.toBinaryString(16) + " setBit: " + Integer.toBinaryString(obj.setBit(16, 3)) );
		System.out.println( Integer.toBinaryString(127) + " clearBit: " + Integer.toBinaryString(obj.clearBit(127, 3)) );
		System.out.println(Integer.toBinaryString(127) + " clearThroughI: " + Integer.toBinaryString(obj.clearThroughI(127, 3)) );
		System.out.println(Integer.toBinaryString(127) + " clearIThrough0: " + Integer.toBinaryString(obj.clearIThrough0(127, 3)) );
		System.out.println(Integer.toBinaryString(127) + " updateBit: " + Integer.toBinaryString(obj.updateBit(127, 3, false)) );
	}
}