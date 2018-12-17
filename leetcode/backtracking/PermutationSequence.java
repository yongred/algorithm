
/**
Solution 1:  find Next Permutation k times.
How to Arrive:
* From the 1st permutation, Ex: n = 4; 1234;
* Then, we do k-1 times of find Next Permutation using the the Find inversePoint and reverse right side method, just like NextPermutation Question.
* Basically from right to left find 1st ASC leftNum < rightNum, the InverseIndex.
* Then find smallestNum > IP on right side of InverseIndex. And swap them.
* Then reverse the rightSide.
* Do that k-1 times, from starting 1234, we get the ans.
* Time : O(n * k);  k could be n! in worse case;
* Space: O(n);

--------------------
Solution 2: Find leading digits 1 by 1. Using the fact that n! is the total perms.
Ex: n=4, [1,2,3,4] k=17;
* There are 4 possible leading nums, follow by 3 nums perms; 
* And we know 3 nums perms is total of 3! possibilities = 3 * 2 * 1 = 6; So each leading num have 6 possibilities of perms.
* In following order of perms:
1 + {2,3,4} -> 1-6th perms, 0-5 indexes.
2 + {1,3,4} -> 7-12th perms, 6-11 indexes.
3 + {1,2,4} -> 13-18th perms, 12-17 indexes.
4 + {1,2,3} -> 19-24 perms, 18-23 indexes.
* Our target is k=17, kthIndex=16, So it lands on 3 + {1,2,4}; We get our 1st leading digit 3.
* ANS = 3_ _ _ ;
* Programmatically: KthIdx = k-1 = 17-1=16; 16 / (n-1)! = 16 / (4-1)! = 16/6 = 2;
* 2 is the index of 3. Also 16/6 = 2 means we want the 3rd num of [1,2,3,4]; It suppasses 2 rows of 6 perms.
* Formula: (k-1) / (n-1)!
**Now we do the same with {1,2,4};**
1 + {2,4} -> 1-2 perms, 0-1 indexes;
2 + {1,4} -> 3-4 perms, 2-3 indexes;
4 + {1,2} -> 5-6 perms, 4-5 indexes;
* Updated n=3 (after choosed one leading num);
* kIdx is no longer 16, we eliminated 1 + {2,3,4} and 2 + {1,3,4} total of perms, which is 12. 
* We are finding new kth num in 3+{1,2,4}; 16-12 = 4; indicates idx 4 (5th num) in this seq.
* Updated kIdx = kIdx - (prev digitIndex * (prev n - 1)! ); 
* kIdx = 16 - (2 * (4-1)!) = 16 - 2 * 6 = 4; kIdx = 4;
* Calc cur Digit Index: (kIdx) / (curN - 1)! = 4 / (3-1)! = 4 / 2 = 2; 
* curDigitIndex = 2; Means 4 + {1,2} is our target. 4 is the curDigit.
* ANS = 3,4 _ _ ;
**Now for {1,2}**:
1 + {2} -> 1st seq, 0 index;
2 + {1} -> 2nd seq, 1 index;
* Updated n = 2; 
* Updated kIdx = 4 (kIdx) - (2 (prevDigitIndex) * (3 (prevN) - 1)!) = 4-(2 * (3-1)!) = 0; kIdx = 0;
* Calc cur DigitIndex = 0 / (2-1)! = 0;  1 + {2} have the perm we looking for.
* 1 is our 3rd Digit.
* ANS = 3,4,1_ ;
**Now for {2}**:
2 + {} -> 1st seq, 0 index;
* Updated n = 1;
* Updated kIdx = 0 (kIdx) - (0 (prevDigitIndex) * (2 (prevN) - 1)! ) = 0 - 0 * (2-1)! = 0;
* Cur DigitIndex = 0 / (1-1)! = 0; 2 + {}; 2 is cur (4th) digit;
* ANS = 3,4,1,2; DONE;
* Time: O(n);
* Space: O(n);
*/

class PermutationSequence {

	/**
	 * Solution 2: Find leading digits 1 by 1. Using the fact that n! is the total perms.
	 * Time: O(n)
	 * Space: O(n)
	 */
	public String getPermutation(int n, int k) {
    // form [1..n] list.
    List<Integer> nlist = new ArrayList<>();
    for (int i = 1; i <= n; i++) {
      nlist.add(i);
    }
    // StringBuilder for res
    StringBuilder res = new StringBuilder();
    // k index, 0-> k-1
    int kthIdx = k - 1;
    // nth pos for finding cur digit
    int nthPos = n;
    // form arr of factorials
    int[] facts = new int[n + 1];
    facts[0] = 1; // 0! = 1;
    for (int i = 1; i <= n; i++) {
      // n! = n * (n-1)!;
      facts[i] = facts[i - 1] * i;
    }
    // loop until all pos filled. choose next pos digit.
    while (nthPos >= 1) {
      // cur digitIndex = kIdx / (nthPos - 1)!
      int digitIndex = kthIdx / facts[nthPos - 1];
      res.append(nlist.get(digitIndex) + "");
      // remove cur selected digit
      nlist.remove(digitIndex);
      // update kthIdx; kthIdx - (prev digitIndex * prev (nth - 1)!);
      // (prev digitIndex * prev (nth - 1)!) = eliminated seqs.
      // digitIndex is the seq rows smaller than cur digit row
      // pre (nth - 1)! is the nums of seqs in each row.
      kthIdx = kthIdx - (digitIndex * facts[nthPos - 1]);
      // update nthPos, 1 less leading digit to find. 1234 -> 234 -> 34 -> 4;
      nthPos--;
    }
    return res.toString();
  }

	/**
	 * Solution 1:  find Next Permutation k times.
	 * Time : O(n * k);  k could be n! in worse case;
	 * Space: O(n);
	 */
  public String getPermutation(int n, int k) {
    char[] curPerm = new char[n];
    // form char[] of 1st perm. ASC.
    for (int i = 1; i <= n; i++) {
      curPerm[i - 1] = (char)(i + '0');
    }
    // find next perm until k. We have 1st already, start with 2nd.
    for (int i = 2; i <= k; i++) {
      curPerm = nextPerm(curPerm);
    }
    return new String(curPerm);
  }
  
  public char[] nextPerm(char[] curPerm) {
    // search right to left start with 2nd last.
    int inverseIndex = curPerm.length - 2;
    // search leftNum < rightNum
    while (inverseIndex >= 0 && curPerm[inverseIndex] >= curPerm[inverseIndex + 1]) {
      inverseIndex--;
    }
    // if found a inversePoint
    if (inverseIndex != -1) {
      // find smallest right num > IP
      int minLargerIndex = inverseIndex + 1;
      for (int i = minLargerIndex; i < curPerm.length; i++) {
        if (curPerm[i] > curPerm[inverseIndex] && curPerm[i] <= curPerm[minLargerIndex]) {
          minLargerIndex = i;
        }
      }
      // swap found min larger with ip
      char temp = curPerm[inverseIndex];
      curPerm[inverseIndex] = curPerm[minLargerIndex];
      curPerm[minLargerIndex] = temp;
      // reverse rightside.
      reverse(curPerm, inverseIndex + 1, curPerm.length - 1);
    }
    // if no IP found, return cur, cur is last perm
    // found IP, return next perm.
    return curPerm;
  }
  
  public void reverse(char[] arr, int start, int end) {
    while (start < end) {
      char temp = arr[start];
      arr[start] = arr[end];
      arr[end] = temp;
      start++;
      end--;
    }
  }
}