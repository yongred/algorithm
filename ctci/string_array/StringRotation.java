/**
1.9 String Rotation: Assume you have a method isSubstring which checks if one word is a substring of another. 
Given two strings, s1 and s2, write code to check if s2 is a rotation of s1 using only one call to isSubstring 
(e.g., "waterbottle" is a rotation of "erbottlewat").
Hints: #34, #88, #104
*/
import java.util.*;

public class StringRotation{
	//O(n) time if isSubstring is T(n)
	public boolean isRotate(String s1, String s2){
		if(s1.length() != s2.length() && s1.length() <= 0)
			return false;
		String s1s1 = s1 + s1;
		return isSubstring(s1s1, s2) == -1 ? false : true; 
	}
	/*boyer-moore bad character rule:
	  from right to left iterate to check match
	  -upon mismatch, skip alignments until:
	  1. mismatch becomes a match, or
	  2. P moves past mismatched character
	*/
	public int isSubstring(String text, String pattern){
		int pLen = pattern.length();
		int tLen = text.length();
		int [] right = new int[256]; //ascii
		//T(256), O(1)
		for(int c=0; c<256; c++)
			right[c] = -1;  //1 pos further right when not found
		//T(pLen), O(P)
		for(int j=0; j< pLen; j++)
			right[pattern.charAt(j)] = j;  // ex: abc: a=0, b=1, c=2

		int i = 0; //offset
		//O(n+m) times in worst case, O(n/m) in average case
		while(i <= tLen-pLen){ //ex: p= abc; t= aaabbbabb; 9-3 = 6; so that is aaabbb(abb)-abc
			int skip = 0;
			for(int j= pLen-1; j >= 0; j--){ //right to left scan
				if(pattern.charAt(j) != text.charAt(i+j)){
					//i+j = curInd of text, right[textChar] = char pos in Pattern, j - right[char] = steps from j to p[char]
					skip = Math.max(1, j - right[text.charAt(i+j)]);
					break;
				}

			}
			if(skip == 0) return i; //all chars equal, so skip not assigned, found
			i = i + skip;
		}
		return -1;
	}//end isSubstring

	public static void main(String [] args){
		StringRotation obj = new StringRotation();
		String text = "aaabbbabc";
		String pattern = "abc";
		System.out.println(obj.isSubstring(text, pattern));
		String s1 = "waterbottle";
		String s2 = "erbottlewat";
		System.out.println(obj.isRotate(s1, s2));
	}
}