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
class ScrambleString {

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
  public boolean isScramble(String s1, String s2) {
    // base case, 2 str ==; When 1 or 0 char len; 
    if (s1.equals(s2)) {
      return true;
    }
    // count, assume all lower case chars
    int[] count = new int[26];
    // compare if chars count are same; Ex: great != greet;
    int len = s1.length();
    for (int i = 0; i < len; i++) {
      // cancel out s1 chars w/ s2 chars;
      count[s1.charAt(i) - 'a']++;
      count[s2.charAt(i) - 'a']--;
    }
    // check, all count chars should be 0 if s1 and s2 have same chars.
    for (int i = 0; i < 26; i++) {
      if (count[i] != 0) {
        return false;
      }
    } 
    // i is dividing point; 'a|bcd' to 'abc|d';
    for (int i = 1; i < len; i++) {
      // substr compare, s1.prefix = s2.prefix; s1.postfix = s2.postfix;
      if (isScramble(s1.substring(0, i), s2.substring(0, i)) &&
          isScramble(s1.substring(i), s2.substring(i))) {
        return true;
      }
      // then Reversed substr compare, 
      // s1.prefix = s2.postfix; s1.postfix = s2.prefix;
      // len-i to get the same len substr from opposite side.
      if (isScramble(s1.substring(0, i), s2.substring(len - i)) &&
         isScramble(s1.substring(i), s2.substring(0, len - i))) {
        return true;
      }
    }
    
    return false;
  }
}