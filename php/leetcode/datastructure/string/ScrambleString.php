<?php
/**
87. Scramble String

Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.

Below is one possible representation of s1 = "great":

    great
   /    \
  gr    eat
 / \    /  \
g   r  e   at
           / \
          a   t
To scramble the string, we may choose any non-leaf node and swap its two children.

For example, if we choose the node "gr" and swap its two children, it produces a scrambled string "rgeat".

    rgeat
   /    \
  rg    eat
 / \    /  \
r   g  e   at
           / \
          a   t
We say that "rgeat" is a scrambled string of "great".

Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled string "rgtae".

    rgtae
   /    \
  rg    tae
 / \    /  \
r   g  ta  e
       / \
      t   a
We say that "rgtae" is a scrambled string of "great".

Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.

Example 1:

Input: s1 = "great", s2 = "rgeat"
Output: true
Example 2:

Input: s1 = "abcde", s2 = "caebd"
Output: false
*/


/*
Solution: substr compare charsCount, s1.prefix = s2.prefix; s1.postfix = s2.postfix;
Reversed substr compare charsCount, s1.prefix = s2.postfix; s1.postfix = s2.prefix

from root -> 2charNode, any of them can have their children swapped.

If s1 charsCount != s2 charsCount; FALSE;
Ex: great != greet;

Ex1: Compare charCount of s1[0->i] == s2[0->i], s1[i->n] == s2[i->n];
substr compare, s1.prefix = s2.prefix; s1.postfix = s2.postfix;
[g|r]eat -> rg[e|at] -> rgate;
gr|eat charCount[gr] == charCount[rg];  charCount[eat] == charCount[ate];

So 'rgate' is a scramble of 'great'; TRUE

Ex2: Compare charCount of s1[0->i] == s2[i->n], s1[i->n] == s2[0->i];
Reversed substr compare, s1.prefix = s2.postfix; s1.postfix = s2.prefix;
gr|eat -> [ea|t]gr -> teagr;
gr|eat charCount[gr] ('gr'eat) == charCount[gr] (from tea'gr');
charCount[eat] (gr'eat') == charCount[tea] ('tea'gr);

So 'teagr' is a scramble of 'great'; TRUE

Ex3: Only when there is atleast 1char on each side.
Meaning '' & great => great & ''; cannot do empty '' str.
straight
great egtra : g != e; gr != eg; gre != egt; grea != egtr;
reversed
great egtra : g != a; gr != ra; gre != tra; grea != gtra;
'egtra' is Not a scramble of 'great'; FALSE

**Key**: subtr char counts.
**Base case**: When s1 and s2 == 1 char len; just compare; Or when s1 and s2 is '' they are ==; 

* Time: O(2^n)
   
   o(n) = (o(1) + o(n -1)) + (o(2) + o(n -2)) + … + (o(n - 1) + o(1)))

=> o(n) = 2 * (o(1) + … + o(n - 1))  
so o(n - 1) = 2 * (o(1) + … + o(n -2))

=> o(n) - o(n - 1) = 2 * (o(n - 1))  //we minus o(n) and o(n - 1)
=> o(n) = 3 * o(n - 1) = 3^2 * o(n - 2) = ...  
…  
=> o(n) = 3^n that is exponential 

* Space: O(n); until n->1;
*/

class ScrambleString
{

    /**
     * @param String $s1
     * @param String $s2
     * @return Boolean
     /**
   * Solution: substr compare, s1.prefix = s2.prefix; s1.postfix = s2.postfix;
   * Reversed substr compare, s1.prefix = s2.postfix; s1.postfix = s2.prefix;
   * Time: O(2^n)

   o(n) = (o(1) + o(n -1)) + (o(2) + o(n -2)) + … + (o(n - 1) + o(1)))

	=> o(n) = 2 * (o(1) + … + o(n - 1))  
	so o(n - 1) = 2 * (o(1) + … + o(n -2))

	=> o(n) - o(n - 1) = 2 * (o(n - 1))  //we minus o(n) and o(n - 1)
	=> o(n) = 3 * o(n - 1) = 3^2 * o(n - 2) = ...  
	…  
	=> o(n) = 3^n that is exponential 

   * Space: O(n); until n->1;
     */
    function isScramble($s1, $s2)
    {
        if ($s1 == $s2) {
            return true;
        }
        // count letters.
        $count = [];
        $len = strlen($s1);
        for ($i = 0; $i < $len; $i++) {
            // cancel out
            $count[$s1[$i]] = isset($count[$s1[$i]]) ? $count[$s1[$i]] + 1 : 1;
            $count[$s2[$i]] = isset($count[$s2[$i]]) ? $count[$s2[$i]] - 1 : -1;
        }
        // check all matches, all 0s;
        foreach ($count as $key => $match) {
            //print_r($key . ' ' . $match);
            if ($match != 0) {
                return false;
            }
        }
        // divided prefix, postfix
        for ($i = 1; $i < $len; $i++) {
            // prefix -> prefix, postfix -> postfix
            if ($this->isScramble(substr($s1, 0, $i), substr($s2, 0, $i)) &&
               $this->isScramble(substr($s1, $i), substr($s2, $i))) {
                return true;
            }
            // prefix -> postfix
            if ($this->isScramble(substr($s1, 0, $i), substr($s2, -$i)) &&
                $this->isScramble(substr($s1, $i), substr($s2, 0, -$i))) {
                return true;
            }
        }
        
        return false;
    }
}