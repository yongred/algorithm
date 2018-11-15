/**
386. Longest Substring with At Most K Distinct Characters
Given a string s, find the length of the longest substring T that contains at most k distinct characters.

Example
For example, Given s = "eceba", k = 3,

T is "eceb" which its length is 4.

Challenge
O(n), n is the size of the string s.
*/
import java.util.*;
import java.io.*;

public class LongestSubstringKDistinct {
    /**
     * @param s: A string
     * @param k: An integer
     * @return: An integer
     * Solution:
     * First I think of brute force, using nested loops. Every time I encounter > k chars, it goes back to next char after start.
	* ex: "ecceba" k =3, goes back to 'c' when encounter at 'a'
	* b/c we already loop through these chars. There must be a way where we can avoid looping it again.
	* I think of using 2 pointers method, where a StartInd and a curInd, curInd - StartInd = len of cur window;
	* With 2 pointers we just need to (update the startInd) when we encounter conflict distinct > k, and move the startInd to where (distinct == k) again.
	* ex: "ecceba" k =3; start=0, distinct =4; when start->3, distinct=3;
	* We can use a hashTable to store the chars appearances.
	 *We can use Ascii array to count the chars appearances for the cur window
	* Key: we just need to know the cur window chars count. So erase all counts before [start->pos] from Ascii.
	* When distinct == k, we arrived at a new Window, start the process again.
	*
	* Time: O(n); Space: O(1)
     */
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        if(s == null || s.length() == 0 || k == 0)
            return 0;
        int longest = 1;
        int chrCnt = 0; //cur substr distinct chars count
        int start = 0; //cur startIndex
        int [] hash = new int[256]; //how many time char appears in cur substr
        
        for(int i=0; i< s.length(); i++)
        {
        	hash[s.charAt(i)]++; //increment cur char appearance.
            if(hash[s.charAt(i)] == 1) //new char
                chrCnt++;

            if(chrCnt > k)
            {
                int len = i-start;
                longest = Math.max(longest, len);
                //increment start to new pos, where chrCnt == k
                while(chrCnt > k)
                {
                    hash[s.charAt(start)]--; //decrement appearance #
                    //0 means 1 less distinct char
                    if(hash[s.charAt(start)] <= 0){
                        chrCnt--;
                    }
                    
                    start++;
                }
            }
            //if no conflict, check the length, update longest
            else
            {
                int len = i+1-start;
                longest = Math.max(longest, len);
            }
        }
        return longest;
    }

    public static void main(String[] args) {
    	String s = "ecebabddac";
    	int k = 3;
    	int ans = lengthOfLongestSubstringKDistinct(s, k);
    	System.out.println(ans);
    }
}