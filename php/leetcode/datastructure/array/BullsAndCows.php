<?php

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
     * @param String $secret
     * @param String $guess
     * @return String
     */
    function getHint($secret, $guess)
    {
        $len = strlen($secret);
        // hash to count
        $count = array_fill(0, 10, 0);
        $bulls = 0;
        $cows = 0;

        for ($i = 0; $i < $len; $i++) {
            if ($secret[$i] == $guess[$i]) {
                $bulls++;
            } else {
                // check if count secret > guess for curGuessChar.
                if ($count[intval($guess[$i])] > 0) {
                    $cows++;
                }
                // check if count guess > secret for curSecretChar.
                if ($count[intval($secret[$i])] < 0) {
                    $cows++;
                }
                $count[intval($secret[$i])]++;
                $count[intval($guess[$i])]--;
            }
        }

        return $bulls . "A" . $cows . "B";
    }
}