/**
384. Longest Substring Without Repeating Characters
Given a string, find the length of the longest substring without repeating characters.

Example
For example, the longest substring without repeating letters for "abcabcbb" is "abc", which the length is 3.

For "bbbbb" the longest substring is "b", with the length of 1.

Challenge
O(n) time
*/

import java.util.*;
import java.io.*;

public class LongestSubstringNoRepeat {
	/**
     * @param str: a string
     * @return: an integer
     * Solution 1: using HashMap
     * Ex: "abcbbcad" substr = "bcad" len = 4;
	* I think about brute force 2 loops, O(n^2); Every time encounter a repeat, we goes back to char after the conflicted char, ex: abcb, goes back to 'c';
	* Now I think about how not to repeat that part, is to Store them and compare on the way.
	* By having a HashMap to see if there is conflict, from current substr. It will provide the conflicted prev pos.
	* By having a Start index, I just need to calc len = i - start, and update startInd, no need to go back.
	* Update the longest when len > longest.
	* Now we just need to repeat the process until the last pos. Which will be len = i+1-start;
	*
	* Time: O(N); Space: O(N)
     */
    public int lengthOfLongestSubstring(String str) {
        if(str == null || str.length() == 0)
            return 0;
        int longest = 1, start = 0;
        HashMap<Character, Integer> hash = new HashMap<>();
        
        for(int i=0; i<str.length(); i++)
        {
            if(!hash.containsKey(str.charAt(i)))
            {
                hash.put(str.charAt(i), i);
                //if is the last pos
                if(i == str.length()-1)
                {
                    int len = i+1-start; //count cur pos, so +1
                    if(len > longest)
                        longest = len;
                }
            }
            else //hash contains chr
            {
                int prevInd = hash.get(str.charAt(i));
                //update chr pos to cur
                hash.put(str.charAt(i), i);
                //only focus on current substr, [start->pos]
                if(prevInd >= start)
                {
                    int len = i - start; //len from start->pos-1
                    if(len > longest)
                        longest = len;
                    start = prevInd+1;
                }
            }
        }
        return longest;
        
    }

    /**
     * @param str: a string
     * @return: an integer
     * Solution 2: Using Ascii arr, optimize space: O(N) -> O(1)
     * 
     */
    public int lengthOfLongestSubstring(String str) {
        if(str == null || str.length() == 0)
            return 0;
        int longest = 1, start = 0;
        boolean [] visited = new boolean[256];
        
        for(int i=0; i<str.length(); i++)
        {
            //conflict
            if(visited[str.charAt(i)])
            {
                int len = i-start;
                longest = Math.max(longest, len); 
                //iter start to new start pos, prevConflict+1
                for(int k=start; k< i; k++)
                {
                    if(str.charAt(k) == str.charAt(i))
                    {
                        start = k+1;
                        break;
                    }
                    //remove prev chars, for current substr tracking.
                    visited[k] = false;
                }
            }
            else if(i == str.length()-1)
            {
                int len = i+1-start;
                longest = Math.max(longest, len);
            }
            visited[str.charAt(i)] = true; //visited, if appear again, it's repeat
        }
        return longest;
        
    }
}