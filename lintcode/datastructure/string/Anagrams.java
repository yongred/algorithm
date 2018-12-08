/**
Anagrams:

Given an array of strings, return all groups of strings that are anagrams.

Example
Given ["lint", "intl", "inlt", "code"], return ["lint", "inlt", "intl"].

Given ["ab", "ba", "cd", "dc", "e"], return ["ab", "ba", "cd", "dc"].

*/
import java.util.*;
import java.io.*;

public class Anagrams{
	/*
     * @param strs: A list of strings
     * @return: A list of strings
     Solution1: Using hashMap to determine whether matches.
     -Hash keys for anagrams like "abc" "bca" can use ASCII arr to generate
     Time: O(n * m); m == avg len of each str in strs; 
     Space: O(n);
     */
    public static List<String> anagrams(String[] strs) {
        List<String> res = new ArrayList<String>();
        HashMap<String, String> hs = new HashMap<String, String>();

        for(int i=0; i< strs.length; i++){
        	String curStr = strs[i];
        	//ASCII 256
        	char [] keyArr = new char[256];
        	for(int j=0; j< curStr.length(); j++){
        		keyArr[curStr.charAt(j)]++;
        	}
        	String key = new String(keyArr);

        	if(hs.containsKey(key)){
        		if(!res.contains(hs.get(key)))
        			res.add(hs.get(key));
        		res.add(curStr);
        	}else{
        		hs.put(key, curStr);
        	}
        }

        return res;
    }

    public static void main(String [] args){
    	String [] strs = {"ray","cod","abe","ned","arc","jar","owl","pop","paw","sky","yup","fed","jul","woo","ado","why","ben","mys","den","dem","fat","you","eon","sui","oct","asp","ago","lea","sow","hus","fee","yup","eve","red","flo","ids","tic","pup","hag","ito","zoo"};

    	List<String> ans = anagrams(strs);
    	String [] arr = ans.toArray(new String[ans.size()]);
    	System.out.println(Arrays.toString(arr));
    }	
}



