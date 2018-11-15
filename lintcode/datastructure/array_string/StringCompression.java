/**
String Compression:
Given an array of characters, compress it in-place.

The length after compression must always be smaller than or equal to the original array.

Every element of the array should be a character (not int) of length 1.

After you are done modifying the input array in-place, return the new length of the array.

Input:
["a","a","b","b","c","c","c"]

Output:
Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]

Explanation:
"aa" is replaced by "a2". "bb" is replaced by "b2". "ccc" is replaced by "c3".
*/

import java.util.*;
import java.io.*;

public class StringCompression{
	/*
	Solution: use StringBuilder to check prev char == cur char. keep a count.
	O(n) time, O(n) space for the stringbuilder
	*/
	public static String compress(String str){
		if(str.length() <= 0)
			return "";
		StringBuilder sb = new StringBuilder();
		int count = 1;
		sb.append(str.charAt(0));

		for (int i=1; i< str.length(); i++) {
			//encounter different char, append last char and its count
			if(str.charAt(i) != sb.charAt(sb.length()-1)){
				if(count > 1)
					sb.append(count);
				sb.append(str.charAt(i));
				count = 1; //reset count
			}else{
				count++;
				//if its the last element and it is consecutive
				if(i == str.length()-1)
					sb.append(count);
			}
		}

		return sb.toString();
	}

	public static void main(String [] args){
		System.out.println(compress("abcc"));
	}

}