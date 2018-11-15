
/**
strStr:
For a given source string and a target string, you should output the first index(from 0) of target string in source string.

If target does not exist in source, just return -1.
*/

import java.io.*;
import java.util.*;

public class StrStr{
	
	/*
	when encounter a missmatch, find if previous substr have the same as current substr. ex: abcabcabe, missmatch in x, then go back to prev a, b/c ab[c] = ab[c] = ab[e]
	*/
	public static int [] kmp_prefix(String pattern){
		int [] prefix = new int [pattern.length()]; //prefix table
		prefix[0] = 0;  //first char is always 0
		int len = 0;  //longest suffix/prefix index so far
		int i = 1;

		while(i < pattern.length()){
			if(pattern.charAt(i) == pattern.charAt(len)){
				len++;
				prefix[i] = len;
				i++;
			}else{ // P[i] != P[len]
				if(len == 0){
					prefix[i] = len;
					i++;
				}else{ //not equal 0
					//len index goes back to the prefix[len-1]
					len = prefix[len-1];
				}
			}
		}

		return prefix;
	}

	/*
     * @param source: source string to be scanned.
     * @param target: target string containing the sequence of characters to match
     * @return: a index to the first occurrence of target in source, or -1  if target is not part of source.
     * 
     */
    public static int strStr(String source, String target) {
    	if(target == null || source == null)
        	return -1;
    	if(target.equals("")){
        	return 0;
        }
        if(target.length() > source.length())
        	return -1;
    	int [] prefix = kmp_prefix(target);
        int src_ind = 0;
        int tar_ind = 0;
        

        //loop through source
        while(src_ind < source.length()){
        	//if match goes to compare next char.
        	if(target.charAt(tar_ind) == source.charAt(src_ind)){
        		src_ind++;
        		tar_ind++;
        	}

        	//if loop over last index in target, meaning we found a match
        	if(tar_ind == target.length()){
        		//return first ind of the substr
        		return src_ind - tar_ind;
        	}
        	//mismatch found
        	else if(target.charAt(tar_ind) != source.charAt(src_ind)){
        		//go back to last same substr, ex: abcabcab[e] check last [ab]
        		if(tar_ind != 0)
        			tar_ind = prefix[tar_ind-1];
        		else
        			src_ind++;
        	}
        }

        return -1;
    }

    public static void main(String [] args){
    	String source = "abbefabcabcabcaebbc";
    	String target = "abcabcae";
    	System.out.println(strStr(source, target));
    }
}