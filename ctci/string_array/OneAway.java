/**
1.5 One Away: There are three types of edits that can be performed on strings: insert a character,
remove a character, or replace a character. Given two strings, write a function to check if they are
one edit (or zero edits) away.
EXAMPLE
pale, ple -> true
pales, pale -> true
pale, bale -> true
pale, bake -> false
Hints: #23, #97, #130
*/
import java.util.*;

public class OneAway{
	//O(n) time, O(1) Space
	public boolean oneEdit(String str1, String str2){
		int len1 = str1.length(), len2 = str2.length();
		if(len1 == len2){
			return sameLenEdit(str1, str2);
		} else if(len1 > len2){
			return diffLenEdit(str1, str2);
		} else{
			return diffLenEdit(str2, str1);
		}
	}//end oneEdit

	public boolean sameLenEdit(String str1, String str2){
		int count=0;
		for(int i = 0; i < str1.length(); i++){
			if(str1.charAt(i) != str2.charAt(i))
				count++;
			if(count > 1) //more than 1 replace
				return false;
		}
		return true;
	}

	public boolean diffLenEdit(String longer, String shorter){
		//check more than 1 insert or remove
		if(longer.length() - shorter.length() > 1)
			return false;
		int count = 0;
		//get the longer string length to loop
		//i for longer, j for shorter, ex: goku, gku, when at ind 1, i++
		for(int i=0, j=0; i< longer.length(); i++, j++){
			if(longer.charAt(i) != shorter.charAt(j)){
				if(longer.charAt(++i) != shorter.charAt(j))
					return false;
					
				count++;
			}
			if(count > 1)
				return false;
		}//end 
		return true;
	}

	public static void main(String [] args){
		OneAway obj = new OneAway();
		System.out.println(obj.oneEdit("theShit", "theShit"));
		System.out.println(obj.oneEdit("theShit","theSshit"));
		System.out.println(obj.oneEdit("theShit", "thehit"));
		System.out.println(obj.oneEdit("theShit", "thehhit"));
		System.out.println(obj.oneEdit("theShit", "theit"));
		System.out.println(obj.oneEdit("theShit", "thehsit"));
		System.out.println(obj.oneEdit("theShit", "theSsshit"));
	}
}