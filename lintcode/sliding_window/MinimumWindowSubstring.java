/**
32. Minimum Window Substring
Given a string source and a string target, find the minimum window in source which will contain all the characters in target.

Example
For source = "ADOBECODEBANC", target = "ABC", the minimum window is "BANC"

Challenge
Can you do it in time complexity O(n) ?

Clarification
Should the characters in minimum window has the same order in target?

Not necessary.
Notice
If there is no such window in source that covers all characters in target, return the emtpy string "".
If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in source.
The target string may contain duplicate characters, the minimum window should cover all characters including the duplicate characters in target.
*/

/**
Update for better understanding. For integers Sets of the same problem.
    ex: 
    int [] arr = {3,1,2,5,6,6,1,2,3};
    int [] set = {1,2,6};
    int [] res = subArr(arr, set); [6,1,2];
        
    Min window, using 2 pointers. And hash.
   * How to Arrive:
   * Notice that brute force is n^2. Having nested loop
   * But iteration 1 time is enough. To do that we can keep a record of Set elm appearance.
   * A **HashMap** can be Helpful to count the cur Win target elms.
   * Now think about what we are returning, a subarr (window). 
   * Window: Meaning we need to have the **curStart, curEnd index** of the window.
   * If cur window have all the appreance of target, then we find a new subarr.
   * **Key**: how to know if window has all the elms needed?
         * Use a counter. Count when elm < to elm # in Set.
         * Ex: Set {1,2,2,3} cur elm = 2; hash(2) == 1; needed one more. So count++; When hash(2) == 3, meaning over the needs.
     * When count == Set.len, we find a new subarr.
   * Compare that subarr len to the shortest. If cur is shorter, update.
   * To keep track of the Shortest, we need **minStartInd** of shortest, and **MinLen**, minEndInd (optional) b/c *startInd + minLen - 1 = endInd*.
   * **Key**: how to go to next Window?
         * Ex: {3,1,2,5,6,6,1,2,3} set = {1,2,6}, 1st win = [1,2,5,6] next = [2,5,6,6,1], next = [6,6,1,2] => [6,1,2]
         * What changes? The **curStart elm becomes the next last elm**. And next Start is the next Set elm in the window.
         * **key**: Also if the next Set elm in window > 1; then we move to the next Set elm in window. next = [6,6,1,2] => [6,1,2]; Shorter.
         * Meaning: *If we find another elm == start elm. We find a new Window.*
         * Also we *Don't need the Counter after this point. Since we just replacing start to last, only updates when a replacement is found*.
     * We do the same with next Window. Finding if there is shorter.
     * Return: subArr(arr, minStartInd -> minStartInd + MinLen), Java incl: start, excl: end;
     * Ex: {3,1,2,5,6,6,1,2,3} set = {1,2,6} Ans: 6,1,2  [5->7];
     * Time: O(n)
   * Space: O(S) set.len; 
*/

import java.util.*;
import java.io.*;

public class MinimumWindowSubstring {
    /**
     * @param source : A string
     * @param target: A string
     * @return: A string denote the minimum window, return "" if there is no such a string
     * Solution: use ASCII chr hash
     * targetChrs for chr counts in target, use it to check for matching chr occurrences.
     * sourceChrs for chr counts in cur window, compare to targetChrs to find window.
     * Count the matches chr found, if count == target.len, we find a window.
     * after found a window, we try find shorter window, by finding the start chr of cur window.
     * Ex: [.(A)..|B..B.(A)|..] target = ABB
     * update the sourceChrs to cur window chr counts. if not the next time A become start again the we can't accurately compare the chr count.
     * ex: [.(A)..B..B.(A)BB..] A is still 2 > target (A1)B2
     * Think in window by window cases
     * update minLen and startIndex if new shortest window found.
     * Space: O(1); Time: O(N);
     */
    public static String minWindow(String source , String target) {
        int [] targetChrs = new int[256]; //ASCII
        int [] sourceChrs = new int[256]; //cur window chrs
        //fill hashes for target, ex: ABB -> A1B2
        for (int i = 0; i < target.length(); i++) {
            targetChrs[target.charAt(i)]++;
        }
        int minLen = Integer.MAX_VALUE; //shortest window len
        int startIndex = -1; //first ind of window
        int start = 0; //pos of start right now
        int count = 0; //matches chars found

        for (int i = 0; i < source.length(); i++) {
            sourceChrs[source.charAt(i)]++; //fill hashes for source
            //if cur char is a target char, and it does not exceed # needed
            if (targetChrs[source.charAt(i)] > 0 &&
                    sourceChrs[source.charAt(i)] <= targetChrs[source.charAt(i)]) {
                count++; //increment count indicate getting closer to find a window
            }
            //if atleast 1 window is found
            if (count == target.length()) {
                //check for shorter windows, if src[i] == src[start]
                while (targetChrs[source.charAt(start)] == 0 ||
                        sourceChrs[source.charAt(start)] > targetChrs[source.charAt(start)]) {
                    //if cur char is the target char at the beginning of window.
                    if (sourceChrs[source.charAt(start)] > targetChrs[source.charAt(start)]) {
                        //get rid of chars not in cur window.
                        //if not, next same char count will always larger than target char count.
                        //only maintain cur window chars, so we can check whether a new window is find
                        sourceChrs[source.charAt(start)]--;
                    }
                    //move the start to next target char in source,
                    // b/c we find a new window.
                    start++;
                }
                //check & update if new minLen
                int winLen = i - start + 1;
                if (winLen < minLen) {
                    minLen = winLen;
                    startIndex = start;
                }
            }

        }
        if (startIndex == -1)
            return "";
        return source.substring(startIndex, startIndex + minLen);
    }

    /**
     * @param source : A string
     * @param target: A string
     * @return: A string denote the minimum window, return "" if there is no such a string
     */
    public String minWindowBruteForce(String source , String target) {

        int shortest = Integer.MAX_VALUE;
        int startIndex = -1;
        int [] tarHash = new int[256];
        for (char chr : target.toCharArray()) {
            tarHash[chr]++;
        }

        for (int i = 0; i < source.length(); i++) {
            int [] tempHash = Arrays.copyOf(tarHash, tarHash.length);

            for (int j = i; j < source.length(); j++) {
                if (tempHash[source.charAt(j)] > 0) {
                    tempHash[source.charAt(j)]--;
                }
                int chrleft = 0;
                for (int k = 0; k < tempHash.length; k++) {
                    chrleft += tempHash[k];
                }
                if (chrleft == 0) {
                    if (shortest > j - i + 1) {
                        shortest = j - i + 1;
                        startIndex = i;
                    }
                }
            }
        }

        if (startIndex == -1) {
            return "";
        }
        return source.substring(startIndex, startIndex + shortest);
    }

    public static void main(String[] args) {
        String source = "asblasdbladjbadjab";
        String target = "asb";
        String ans = minWindow(source, target);
        System.out.println(ans);
    }
}