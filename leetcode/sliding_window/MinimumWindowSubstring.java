
/**
Solution: Use ASCII chr hash. Sliding window after find a match.
Ex:
"ADOBECODEBANC", t = "ABC"
min = ADOBEC, CODEBA, BANC (ans);
start: 0->5, 5->10, 9->12; 
find first (ADOBEC)ODEBANC
find another 'A' = start 'A' slide over: ADOBE(CODEBA)NC
find another 'C' = start 'C' slide over: ADOBECODE(BANC)

How to Arrive:
* First, to know whether or not we found a substr that contains all tchars, we need to count the chars in the substr and target, same if they ==;
* To do that we can use 2 hashes in ASCII, and a matches counter, (matches == t.len) then contains all chars.
* Now we need to know the minimum len, and the substr of that.
* We need a minLen, a minStartIndex.
* 
* Algorithm:
* We need *minLen* to keep track of min, *startIndex* for shortest substr index.
* need *matches* to count the chars match in cur window.
* need *start* to track cur win start index.
* And 1 hash for winCount (cur window chars count); 1 hash for tCount (target chars count);
* Loop chars in s:
	* Count cur Window chars.
	* **First, We need to find the 1st matchin window**
	* If this char is in target, and curWindow currently has <= amount of curChar as in target:
		* increment matches.
	* if matches == t.len: (means we find match atleast 1 match)
		* **Key**: check if find a newChar == startChar, means we find a new match window.
		* While the startChar is in target, Or startChar count > tCount[startChar]:
			* startChar count > tCount[startChar] means: the newChar == startChar, therefore count increased in window.
			* This also means: we are skipping over any targetChars that is more than needed.
			Ex: [.(A)..B.|B.C.(A)|..] target = ABC; From A..B.B.C -> B.C.A; We skip over A and B.
			* Remove all prev window chars,
			* increment cur win start index.
		* Calc cur winLen, then compare to minLen
		* if winLen < minLen:
			* update minLen, update minStartIndex.
* Finished the loop;
* if Not found any substr matches: (minStartIndex == -1)
	* return "";
* else return substr(minStartIndex, minStartIndex+minLen);
* Time: O(N);
* Space: O(1); 
*/

class MinimumWindowSubstring {

	/**
	Solution: Use ASCII chr hash. Sliding window after find a match.
	* Time: O(N);
	* Space: O(1); 
	*/
  public String minWindow(String s, String t) {
    int minLen = Integer.MAX_VALUE;
    // startIndex for shortest substr ans
    int minStartIndex = -1;
    // count for matches letters.
    int matches = 0;
    // cur start index
    int start = 0;
    // hash for counting letters for curWin
    int[] winCount = new int[256];
    // hash for t letters appearances.
    int[] tCount = new int[256];
    // init
    for (char chr : t.toCharArray()) {
      tCount[chr]++;
    }
    // loop through s
    for (int i = 0; i < s.length(); i++) {
      char chr = s.charAt(i);
      // count chars in cur window
      winCount[chr]++;
      // find and count matches, if chr in t, and win has < # of curChar.
      if (tCount[chr] > 0 && winCount[chr] <= tCount[chr]) {
        matches++;
      }
      // if find at least 1 match, 
      // we can just slide beginning letter, replace w/ new found tchar = startChar.
      if (matches == t.length()) {
        // check if find a new win contain all tchars, startChar == newChar;
        // if is newChar is startChar then winCount[s.charAt(start)] > tCount[s.charAt(start)].
        // skip over Non-tchar, or win[tchar] > tCount[tchar]; 
        while (tCount[s.charAt(start)] == 0 ||
            winCount[s.charAt(start)] > tCount[s.charAt(start)]) {
          // remove prev win chars. For accurate count.
          winCount[s.charAt(start)]--;
          // slide window, remove startChar. move startChar to next tchar in window.
          start++;
        }
        // calc if new Min
        int winLen = i - start + 1;
        if (winLen < minLen) {
          // update new min and startIndex for min substr
          minLen = winLen;
          minStartIndex = start;
        }
      }
    }
    // if not found.
    if (minStartIndex == -1) {
      return "";
    }
    return s.substring(minStartIndex, minStartIndex + minLen);
  }

}