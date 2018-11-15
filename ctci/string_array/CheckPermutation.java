/**
1.2 Check Permutation: Given two strings, write a method to decide if one is a permutation of the
other.
Hints: #1, #84, #122, #131
*/

import sortpackage.*;
import java.util.*;

//ask interviewer if it is CASE sensitive, and if White Space is significant
public class CheckPermutation{
	//O(2NlgN + N), O(NlgN)
	public boolean sortIsPermutation(String str1, String str2){
		if(str1.length() != str2.length())
			return false;
		StrMergeSort ms = new StrMergeSort();
		String sortStr1 = ms.strMergeSort(str1, 0, str1.length()-1);
		String sortStr2 = ms.strMergeSort(str2, 0, str2.length()-1);
		return sortStr1.equals(sortStr2);
	}

	public boolean isPermutation(String str1, String str2){
		if(str1.length() != str2.length())
			return false;
		//assume ascii 128
		int [] ascii = new int[128]; //default value is 0
		for(int i =0; i< str1.length(); i++){
			//counting char appearance nums
			int chrNum = (int)str1.charAt(i);
			ascii[chrNum]++;
		}
		for(int j=0; j< str2.length(); j++){
			int chrNum = (int)str2.charAt(j);
			ascii[chrNum]--;
			//if length must be the same, (space counts), same num chars means any diff will create -num, ex: abc, acd, create -d; abc, acc, create -c 
			if(ascii[chrNum] < 0)
				return false;
		}
		//any num other than 0, means not equal char nums
		// for(int num : ascii){
		// 	if(num != 0)
		// 		return false;
		// }
		return true;
	}

	public static void main(String [] args){
		CheckPermutation obj = new CheckPermutation();
		String str1 = "dcba";
		String str2 = "abcd";
		System.out.println(obj.sortIsPermutation(str1, str2));
		int [] arr = new int[22];
		System.out.println(obj.isPermutation(str1, str2));
	}
}