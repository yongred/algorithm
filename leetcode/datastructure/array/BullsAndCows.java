/**
299. Bulls and Cows
*/

/*
Solution: Count the secret digits, check guess digits in countSec; as B. Compare each pos as A. B-A = B;

count the secret str digits. 
Ex: secret = "1123", guess = "0111"
[1]=2, [2]=1, [3]=1;
count the guess str digits.
[0]=1, [1]=3;

loop s.len, compare chars to g;
if s[i] == g[i] A++;
A=1;

loop g.
if sCount[g[i]] > 0; B++; sCount[g[i]]--;
B=2;
B = B-A = 2-1 = 1;

res: 1A1B;

* Time: O(n)
* Space: O(1)

----------

Solution 2: Cancel out, sees secretChar count++, sees guessChar count--;

Ex: 
s[1123] g[0111]
c[]= [-1,-1,1,1,0,0,0,0,0,0]
bulls=1;
cows=1;
1!=0; c[1]==0 c[0]==0; c[1]++; c[0]--;
1==1 bulls++;
2!=1; c[2]==0, c[1]==1 > 0, cows++; c[2]++; c[1]--;
3!=1; c[3]==0, c[1]==0; c[3]++; c[1]--;
Done.


* So when count[curGuess] > 0, means secret have more this char than guess
* Therefore curGuess is a match char to cancel out prev secretChar, cows++;
* and when count[curSecret] < 0, means guess have more this char than secret.
* Therefore curSecret is a match char to cancel out prev guessChar, cows++;

* Time: O(n)
* Space: O(1)
*/

class BullsAndCows {
  
  /**
   * Solution: Count the secret digits, check guess digits in countSec; as B. Compare each pos as A. B-A = B;
   * Time: O(n)
   * Space: O(1)
   */
  public String getHint(String secret, String guess) {
    // their len are ==;
    int len = secret.length();
    int bulls = 0;
    int cows = 0;
    // 0->9; digits count.
    int[] countSec = new int[10];
    for (char cur : secret.toCharArray()) {
      countSec[cur - '0']++;
    }
    // check how many chars are bull. Digit and Pos are ==;
    for (int i = 0; i < len; i++) {
      if (secret.charAt(i) == guess.charAt(i)) {
        bulls++;
      }
    }
    // check cows, how many digit ==, not pos.
    for (int i = 0; i < len; i++) {
      char cur = guess.charAt(i);
      if (countSec[cur - '0'] > 0) {
        countSec[cur - '0']--;
        cows++;
      }
    }
    // exclude the correct pos digits.
    cows -= bulls;
    return bulls + "A" + cows + "B";
  }

  /**
   Solution 2: Cancel out, sees secretChar count++, sees guessChar count--;
   * Time: O(n)
	 * Space: O(1)
   */
  public String getHint(String secret, String guess) {
    int len = secret.length();
    int[] count = new int[10];
    int bulls = 0;
    int cows = 0;
    for (int i = 0; i < len; i++) {
      // cancel out, secretChar++, guessChar--; count should be 0 if they have same chars.
      if (secret.charAt(i) == guess.charAt(i)) {
        bulls++;
      } else {
        // not ==
        if (count[guess.charAt(i) - '0'] > 0) {
          // when count[curGuess] > 0, means secret have more this char than guess.
          // b/c secretChar count++;
          cows++;
        }
        if (count[secret.charAt(i) - '0'] < 0) {
          // when count[curSecret] < 0, means guess have more this char than secret.
          // b/c guessChar count--;
          cows++;
        }
        count[secret.charAt(i) - '0']++;
        count[guess.charAt(i) - '0']--;
      }
    }
    
    return bulls + "A" + cows + "B";
  }
  
  
}