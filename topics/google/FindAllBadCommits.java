/**
Find All Bad Commits

you are working on a project and you noticed that there has been a performance decrease between two releases. You have a function:

boolean worseCommit(int commit1, int commit2) 
that runs performance tests and returns true if commit2 is worse than commit1 and false otherwise.

Find all of the bad commits that have decreased the performance between releases. Assume no improvement in performance.

Commit Id: 1, 2, 3, 4, 5, 6, 7, 8, 9

Performance: 10, 10, 10, 8, 8, 8, 5, 5, 5

Output 4, 7
*/

/**
1,  2,  3,  4, 5, 6, 7, 8, 9
10, 10, 10, 8, 8, 8, 5, 5, 5

Res: 4, 7 DONE;
(1->9)
10 > 5: (1->5)p (5->9)
(1->5)
10 > 8: (1->3)p (3->5)p
(1->3)
10 = 10: return;
(3->5)
10 > 8: (3->4)p (4->5)p
(3->4)
10 > 8: 3+1=4, add 4; return;
(4->5)
8 = 8: return;
(5->9)
8 > 5: (5->7)p (7->9)p
(5->7)
8 > 5: (5->6)p (6->7)p
(5->6)
8 = 8: return;
(6->7)
8 > 5: 6+1=7, add 7; return;
(7->9)
5 = 5: return;
*/

import java.io.*;
import java.util.*;

public class FindAllBadCommits {
	/**
	 * Tools
	 */
	public int[] commits;
	boolean worseCommit(int commit1, int commit2) {
		return (commits[commit2] - commits[commit1] < 0) ? true : false; 
	}

	/**
	 * Solution: Binary Search, compare 1st to last commit
	 * Time: O(n) worst case n-1 bad commits, all diffs. O(lgN) best case.
	 * Space: O(lgN)
	 */
	public List<Integer> res = new ArrayList<>();

	public List<Integer> findBadCommits(int[] commitIds) {
		if (commitIds == null || commitIds.length == 0) {
			return res;
		}
		// binary search. compare first to last commit.
		helper(commitIds, 0, commitIds.length - 1);
		return res;
	}

	public void helper(int[] commitIds, int start, int end) {
		// check end worse than start.
		if (!worseCommit(commitIds[start], commitIds[end])) {
			// end is not worse. means ==. the whole commits have no bad commit.
			return;
		}
		// end worse than start. Check bad commits
		// if only 2 commits left, end is the bad commit.
		if (end - 1 == start) {
			res.add(commitIds[end]);
			return;
		}
		// otherwise, divide and conquer
		int mid = start + (end - start) / 2;
		// inclusive mid, b/c mid still could be the bad commit.
		helper(commitIds, start, mid);
		helper(commitIds, mid, end);
	}


	public static void main(String[] args) {
		FindAllBadCommits obj = new FindAllBadCommits();
		obj.commits = new int[] {-1, 10, 10, 8, 8, 5, 5};
		int[] commitIds = new int[] {1,  2,  3,  4, 5, 6};
		List<Integer> res = obj.findBadCommits(commitIds);
		for (int id : res) {
			System.out.println(id);
		}
	}

}