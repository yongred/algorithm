/**
Least Occured String
Find Least Occured String in List:
ex: ["abc", "ab", "bbc", "abc"] => ["abc", "bbc"]
*/

import java.util.*;
import java.io.*;

public class LeastOccuredString{
	public static String [] leastOccured(String [] strs){
		if(strs.length <= 1)
			return strs;

		ArrayList<String> result = new ArrayList<String>();
		//least occured
		int least = Integer.MAX_VALUE;
		int count = 1;
		//sort array to count consecutives
		Arrays.sort(strs);
		//add first element
		result.add(strs[0]);
		//prev used to compare if it's consecutive
		String prev = strs[0];
		for (int i=1; i< strs.length; i++) {
			//continue when prev == cur; not finish counting yet
			if(prev.equals(strs[i])){
				count++;

			}else{
				//finished counting, so add prev if it occurs <= least;
				if(count < least){
					//update new least; clear old least occured elements
					least = count;
					result.clear();
					//add new least occured element
					result.add(prev);
					
				}else if(count == least){
					result.add(prev);
				}
				//renew count
				count = 1;
				
			}
			prev = strs[i];
		}
		//decide whether to add last element
		if(count < least){
			least = count;
			result.clear();
			result.add(prev);
		}else if(count == least){
			result.add(prev);
		}

		return result.toArray(new String[result.size()]);
	}

	public static void main(String [] args){
		String [] strs = {"abc", "abc", "bbc", "abc", "bbc", "cc"};
		System.out.println(Arrays.toString(strs));
		String [] result = leastOccured(strs);
		System.out.println(Arrays.toString(result));
	}
}