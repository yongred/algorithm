/**
1.6 String Compression: Implement a method to perform basic string compression using the counts of repeated characters. 
For example, the string aabcccccaaa would become a2b1c5a3. 
If the "compressed" string would not become smaller than the original string, your method should return
the original string. You can assume the string has only uppercase and lowercase letters (a - z).
Hints: #92, # 110
*/
import java.util.*;

public class StringCompression{
	//O(n) time, O(n) space for the stringbuilder
	public String compressStr(String str){
		//len < 2: ex: bb -> b2, ab -> a1b1, longer or equal to the len
		if(str.length() <= 2)
			return str;
		//StringBuilder is O(n) speed, str+= char is O(n^2)
		StringBuilder sb = new StringBuilder();  
		int count =1;
		char curChar = str.charAt(0);  //the char counting
		for(int i=1; i< str.length(); i++){
			if(curChar != str.charAt(i) || i == str.length()-1){
				//either a different char or at the end of the str
				//amortized insertion O(1) runtime: n/2, n/4..1
				sb.append(curChar);
				sb.append(count);
				curChar = str.charAt(i);  //char counting changed
				count=1;  //include this i char
			} else{ 
				count++;
			}
		}
		
		return sb.length() >= str.length() ? str : sb.toString();
	}

	/*
	solution count first then compress, 
	This will be more optimal in cases where we don't have a large number of repeating characters. 
	It will avoid us having to create a string that we never use. 
	The downside of this is that it causes a second loop through the characters and also adds nearly duplicated code. 

	One other benefit of this approach is that we can initialize StringBuilder to its necessary capacity up-front. Without this, StringBuilder will (behind the scenes) need to double its capacity every time it hits capacity. The capacity could be double what we ultimately need.
	*/

	//T(n + n), 2n for counting and compressing, O(2n) time, O(n) space
	public String compressStr2(String str){
		int compressedLen = countCompressedLen(str);
		if(compressedLen >= str.length())
			return str;

		StringBuilder sb = new StringBuilder();  
		int count =1;
		char curChar = str.charAt(0);
		for(int i=1; i< str.length(); i++){
			if(curChar != str.charAt(i) || i == str.length()-1){
				//either a different char or at the end of the str
				sb.append(curChar);
				sb.append(count);
				curChar = str.charAt(i);
				count=1;
			} else{
				count++;
			}
		}

		return sb.toString();
	}

	public int countCompressedLen(String str){
		int compressedLen = 0;
		int count = 1;
		char curChar = str.charAt(0);
		for(int i= 1; i < str.length(); i++){
			if(curChar != str.charAt(i) || i == str.length()-1){
				//1 for char, stringValue count.len is ex: 123.len = 3
				int increaseLen = 1 + String.valueOf(count).length();
				//System.out.println("incr: " + increaseLen);
				compressedLen += increaseLen;
				curChar = str.charAt(i);
				count = 1;
			}else{
				count++;
			}
		}
		//System.out.println(compressedLen);
		return compressedLen;
	}

	public static void main(String [] args){
		StringCompression obj = new StringCompression();
		System.out.println(obj.compressStr("aabcccccaaa"));
		System.out.println(obj.compressStr2("aabcccccaaa"));
	}
}