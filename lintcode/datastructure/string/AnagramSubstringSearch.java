/**
Anagram Substring Search:

(Or Search for all permutations)
3.3
Given a text txt[0..n-1] and a pattern pat[0..m-1], write a function search(char pat[], char txt[]) that prints all occurrences of pat[] and its permutations (or anagrams) in txt[]. You may assume that n > m. 
Expected time complexity is O(n)

Examples:

1) Input:  txt[] = "BACDGABCDA"  pat[] = "ABCD"
   Output:   Found at Index 0
             Found at Index 5
             Found at Index 6
2) Input: txt[] =  "AAABABAA" pat[] = "AABA"
   Output:   Found at Index 0
             Found at Index 1
             Found at Index 4

*/
import java.util.*;
import java.io.*;

public class AnagramSubstringSearch{
	/*
	Solution1: Brute Force; loop through every substring in txt; using stringBuilder to track how many matches in that substring

	Time: O(n * m);  space: O(m);
	*/
	public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<Integer>();
        for(int i=0; i<= s.length() - p.length(); i++){
            StringBuilder sb = new StringBuilder(p);
            for(int j=0; j< p.length(); j++){
                int indexFound = sb.indexOf(s.charAt(i+j) + "");
                if(indexFound != -1){
                    sb.deleteCharAt(indexFound);
                }else{
                    break;
                }
            }
            if(sb.length() == 0){
                ans.add(i);
            }
        }
        
        return ans;
    }

    /*
    Solution2: Using ASCII arr; if all chars in this problem is in ASCII 256 chars. 
    Time: O(n);
	*/
    public static List<Integer> findAnagrams1(String s, String p) {
    	int MAX = 256;
    	int M = p.length();
    	int N = s.length();
    	if(M > N || M == 0 || N == 0){
    		return new ArrayList<Integer>();
    	}

    	int [] pCount = new int[MAX];
    	int [] windowCount = new int[MAX];
    	List<Integer> ans = new ArrayList<Integer>();
    	//initialize first window(substr) to compare; ex: N=10; M=4; then [0-3] is first substr;
    	for(int i=0; i< M; i++){
    		pCount[p.charAt(i)]++;
    		windowCount[s.charAt(i)]++;
    	}

    	for (int i=M; i< N; i++) {
    		if(compare(pCount, windowCount)){
    			ans.add(i-M);
    		}
    		windowCount[s.charAt(i-M)]--;
    		windowCount[s.charAt(i)]++;
    	}
    	//last substring not added yet, ex: N=10; M=4; then i=9; 9-4=5; [6-9] is last substring
    	if(compare(pCount, windowCount)){
    		ans.add(N-M);
    	}

    	return ans;
    }

    public static boolean compare(int[] arr1, int[] arr2){
    	int MAX = 256;
    	for (int i=0; i<MAX; i++) {
    		if(arr1[i] != arr2[i])
    			return false;
    	}
    	return true;
    }

    /**
	Solution3: optimized solution2, without compare 2 arrs each time;
	Count matches for substr in S, if matches == pat.len, added to res;
	-Keep track of how many times each character appears. 
    -decrements whenn found match; ex: pat = AABC; patCounts = A2,B1,C1
    -when all target patCounts becomes 0, match found
    -remove prev Start char, add cur last char.
	Time: O(n);
    */
	public static List<Integer> findAnagrams3(String s, String p) {
       	int MAX = 256; //ASCII 256 chars
    	int M = p.length();
    	int N = s.length();
    	if(M > N || M == 0 || N == 0){
    		return new ArrayList<Integer>();
    	}
        List<Integer> res = new ArrayList<Integer>();
       
        //Keep track of how many times each character appears. 
        //decrements whenn found match; ex: pat = AABC; patCounts = A2,B1,C1
        //when all target patCounts becomes 0, match found
        int[] patCounts = new int[MAX];
        for (Character c : p.toCharArray()){ 
           patCounts[c]++;
        }
       	//start, end index for window(substr) in S(txt); Match is for counting how many chars in substr matches pat.
       	int start=0, end=0, match=0; 
       	//first substr(window) [0 -> patLen-1] check matches;
       	// ex: S=(BDAC)BACCBAABC; A2,B1,C1 ==> A1,B0,C0,D-1
       	for(int i=0; i< M; i++){
       		patCounts[s.charAt(i)]--;
       		// if >= 0, means match one of the chars in pat, < 0 means char not in pat;
       		if(patCounts[s.charAt(i)] >= 0){
       			match++;
       		}
       		end++;
       	}
       	//if matches == pat.len; meaning substr == pat;
       	if(match == M){
       		//add start index for this match substr
       		res.add(start);
       	}
       	//matches by remove prev char, add cur char; 
       	//ex: substr [BDAC]C -> B[DABC]; left B behind, and adjust  patCounts accordingly.
       	while(end < N) {
       		//remove prev start char
       		char prevStartChar = s.charAt(start);
       		//add back to patCounts; since its not in cur substr; 
       		//ex: S= B(DAC)[B]ACCBAABC; A1,B0,C0,D-1 ==> A1,B1,C0,D-1
       		patCounts[prevStartChar]++;
       		//check if removed char is in the pat, (>= 1) means it's in pat
       		//if so 1 less matching char.
       		if(patCounts[prevStartChar] >= 1){
       			match--;
       		}
       		//increment start to first of cur substr.
       		start++;

       		//newly added last char of substr; ex: B(DAB[C]) 'C'
       		char curLastChar = s.charAt(end);
       		//match newly added last char;
       		//ex: S= B(DACB)ACCBAABC; A1,B1,C0,D-1 ==> A1,B0,C0,D-1
       		patCounts[curLastChar]--;
       		//check if char in the pat
       		if(patCounts[curLastChar] >= 0){
       			match++;
       		}
       		//check if matches all for cur substr (start-end)
       		if(match == M){
       			res.add(start);
       		}
       		//iterate to next
       		end++;
       	}

       	return res;
    }

    public static void main(String [] args){
    	String txt = "BDACBACCBAABC";
    	String pat = "AABC";
    	List<Integer> ans = findAnagrams3(txt, pat);
    	Integer [] arr = ans.toArray(new Integer[ans.size()]);
    	System.out.println(Arrays.toString(arr));
    }
}