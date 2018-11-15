/**
1.1 Is Unique: Implement an algorithm to determine if a string has all unique characters. What if you cannot use additional data structures?
Hints: #44, # 117, # 132
*/

import java.util.*;
//ascii 128 chars, extended ascii 256, unicode 65536 chars per plane
public class IsUnique{

	//if use hashset
	public boolean isUniq(String str){
		HashSet<Character> ht = new HashSet<Character>();
		boolean uniq = false;
		//O(n^2) run time
		for(int i=0; i< str.length(); i++){
			//java hashset.add calls hashmap.put(key, e), hashmap will loop to find if duplicate 
			uniq = ht.add(str.charAt(i));
			if(!uniq)
				return false;
		}
		return true;
	}

	/*assume is ascii 128 chars, or 256 extended chars; O(n) time complexity
	The time complexity for this code is O( n), where n is the length ofthe string. The space complexity is O( 1).
	(You could also argue the time complexity is O( 1), since the for loop will never iterate through more than 128 characters.} 
	If you didn't want to assume the character set is fixed, you could express the complexity as O(c) space and O(min (c, n)) or O( c) time, where c is the size of the character set.
	*/
	public boolean isUniqueChar(String str){
		if(str.length() > 128)
			return false;
		boolean [] ascTable = new boolean[128];
		for(int i=0; i< str.length(); i++){
			int ascNum = (int)str.charAt(i);
			if(ascTable[ascNum] ) //if its true, duplicated
				return false;
			ascTable[ascNum] = true;
		} 
		return true;
	}

	public static void main(String [] args){
		IsUnique obj = new IsUnique();
		System.out.println(obj.isUniqueChar("abcdefghij"));
		System.out.println(obj.isUniq("abcdefghij"));
	}
}