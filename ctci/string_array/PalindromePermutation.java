/**
1.4 Palindrome Permutation: Given a string, write a function to check if it is a permutation of
a palindrome. A palindrome is a word or phrase that is the same forwards and backwards. A
permutation is a rearrangement of letters. The palindrome does not need to be limited to just
dictionary words.
EXAMPLE
Input: Tact Coa
Output: True (permutations:"taco cat'; "atco cta'; etc.)
*/

import java.util.*;

public class PalindromePermutation{
	//assume unicode, and only letters counts, ask is it case sensitive
	public boolean isPalinPermutation(String str){
		//palindrome can have no more than 1 odd num of certain char
		//getNumericValue used for unicode char int
		if(str.length() < 1)
			return false;
		if(str.length() == 1)
			return true;
		int odd = 0;
		int [] table = createTable(str);
		for(char chr : str.toCharArray()){
			int ind = getCharInd(chr);
			if(ind != -1 && table[ind] % 2 != 0)
				odd++;
			if(odd > 1)
				return false;
		}
		return true;
	}

	//solution2 reduce time, init table and count odd at the same time
	public boolean isPalinPermutation2(String str){
		if(str.length() < 1)
			return false;
		if(str.length() == 1)
			return true;

		int size = 52; //upper and lower case letters
		int [] table = new int[52];
		int odd =0;
		//ex: abcba, a-a, b-b, c, cancel out the evens, left out odd
		for(char chr : str.toCharArray()){
			int ind = getCharInd(chr);
			if(ind != -1){ //is letter
				table[ind]++;
			
				if(table[ind] % 2 != 0) //odd
					odd++;
				else
					odd--; //cancel out even, left odd
			}
		}
		return odd <= 1;  //no more than 1 odd to be palind permute
	}

	//make indexes a->0, b->1.. z->25, A-> 26 .. Z-> 51
	public int getCharInd(Character chr){
		int a = Character.getNumericValue('a');
		int z = Character.getNumericValue('z');
		int A = Character.getNumericValue('A');
		int Z = Character.getNumericValue('Z');
		int target = Character.getNumericValue(chr);
		if(a <= target && target <= z){
			return target - a;
		}
		else if(A <= target && target <= Z){
			//A is 26, ex B - A = 1, 1+26 = 27
			return target - A + 26;
		}
		return -1; //not letter
	}

	public int [] createTable(String str){
		//52 total inclue upper and lower case
		// int size = (Character.getNumericValue('z') - Character.getNumericValue('a') + 1);
		// size += (Character.getNumericValue('Z') - Character.getNumericValue('A') + 1);
		int size = 52;
		int [] letters = new int [size];
		for(char chr : str.toCharArray()){
			int ind = getCharInd(chr);
			if(ind != -1)
				letters[ind]++;
		}
		return letters;

	}

	public static void main(String [] args){
		PalindromePermutation obj = new PalindromePermutation();
		String str = "@#op@#";
		System.out.println(obj.isPalinPermutation(str));
		System.out.println(obj.isPalinPermutation2(str));
	}

}
