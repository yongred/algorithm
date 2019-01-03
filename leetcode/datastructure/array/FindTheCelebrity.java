/**
277. Find the Celebrity
Suppose you are at a party with n people (labeled from 0 to n - 1) and among them, there may exist one celebrity. The definition of a celebrity is that all the other n - 1 people know him/her but he/she does not know any of them.

Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed to do is to ask questions like: "Hi, A. Do you know B?" to get information of whether A knows B. You need to find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).

You are given a helper function bool knows(a, b) which tells you whether A knows B. Implement a function int findCelebrity(n), your function should minimize the number of calls to knows.

Example
Given n = 2

2 // next n * (n - 1) lines 
0 knows 1
1 does not know 0
return 1 // 1 is celebrity

Notice
There will be exactly one celebrity if he/she is in the party. Return the celebrity's label if there is a celebrity in the party. If there is no celebrity, return -1.
*/

/**
Solution: Elimates n-1 elms by check if they knows anybody. Then check the remining one candidate, if everybody knows him, and he knows no one.
Ex:
  0: 1,3
  1: 3
  2: 3
  3:
  4: 3
  
  0->1, yes, c=1
  1->2, no, c=1
  1->3, yes, c=3
  3->4, no, c=3
  check if everybody knows c3, and c3 should not know anybody;
	
How to Arrive:
* Looking at the question carefully, we can see 4 cases:
	A -> B, A is F
	A <- B, B is F
	A <-> B, A & B both F
	A - B, don't know each other, A & B both F
* Since there is at most 1 celebrity, means all other nums knows atleast 1 person. And 1 person knows no one.
* So we can eliminates all other n-1 nums simply by checking if num knows anyone.
* Algorithm:
	*  we init celebrity = 0; then loop 1->n-1; if celebrity knows num, make num = celebrity and check if it knows further nums.
	*  If there is a celebrity, pointer will stop at celebrity index, b/c all others know someone.
	* Then after eliminated n-1 candidates, we have 1 left. We have to check if curCandidate is celebrity, since he might knows someone < him, and someone might know him as well. We have not covered all possibilities.
	* So we loop and check if everybody knows celebrity, and celebrity don't know anybody.
* Time: O(n)
* Space: O(1)
*/

/* The knows API is defined in the parent class Relation.
      boolean knows(int a, int b); */

public class FindTheCelebrity {

	/**
	 * Solution: Simplified, elimates n-1 elms by check if they knows anybody.
	 * Time: O(n)
	 * Space: O(1)
	 */
	public int findCelebrity(int n) {
    int celebrity = 0;
    // if elm knows any #, then move to next one.
    // all other elm should know atleast celebrity, so eliminates them.
    for (int i = 1; i < n; i++) {
      // celebrity should not know anybody.
      if (knows(celebrity, i)) {
        celebrity = i;
      }
    }
    // eliminated all others possibilities. Check if the one left is celebrity.
    for (int i = 0; i < n; i++) {
      if (i == celebrity) {
        continue;
      }
      // check everybody knows celebrity, and celebrity don't know anybody.
      if (!knows(i, celebrity) || knows(celebrity, i)) {
        // if somebody not know celebrity, or celebrity knows somebody.
        return -1;
      }
    }
    return celebrity;
  }
  
  /**
   * @param n a party with n people
   * @return the celebrity's label or -1
   * Solution 1: Eliminates n-1 elms by checking elms know eachother. Then leftover 1 candidate.
   * Time: O(n)
   * Space: O(1)
   */
  public int findCelebrity(int n) {
    // one pass 
    int celebrity = 0;
    int index = 1;
    // loop 0 -> n-1;
    while (index < n) {
      int guess = celebrity;
      // both don't know eachother Or both knows eachother.
      if ((knows(celebrity, index) && knows(index, celebrity)) ||
          (!knows(celebrity, index) && !knows(index, celebrity))){
        guess = index + 1;
        // increment index, so it's not same elm as celebrity.
        index++;
      } else if (knows(celebrity, index)) {
        // a -> b; eliminate a.
        guess = index;
      } else if (knows(index, celebrity)) {
        // b -> a; eliminate b
        guess = celebrity;
      }
      celebrity = guess;
      // check next elm with celebrity guessed.
      index++;
    }
    // elms before guessed celebrity may not be compared.
    if (celebrity <= n - 1) {
      for (int i = 0; i < celebrity; i++) {
        if (knows(celebrity, i) || !knows(i, celebrity)) {
          return -1;
        }
      }
    }
    // if celebrity is outOfBounce, then it's not found.
    return (celebrity >= n) ? -1 : celebrity;
  }
  
}