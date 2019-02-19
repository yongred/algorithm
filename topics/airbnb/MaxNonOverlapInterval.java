/**
Max Non Overlap Interval. Weighted Job Scheduling

 coding:  新题，反正我没见过。题目是给你一堆24小时内旅店预定的信息，{{"2-4", "100"}, {"4-8", "200"}, {"5-7", "300"}}，要求找出收入最大的non-overlapping的时间段。现场写出来了。

  coding， max profit giving a bunch of schedules. Input是一个类似于time interval的list，你要做的就是找到一个最优规划，可以让输出的list of intervals互相之间不重叠，但每个interval的timespan（t2-t1)的和最大。
*/

/*
Assume in order of startTime.

{{"2-4", "100"}, {"4-8", "200"}, {"5-7", "300"}, {7-9, 100}, {12-20, 200}, {19-24, 300}, {20-23, 200}}
map:
0 ->(0)2 ->(100)4 ->(200)8 ->(0)12 ->(200)20 ->(200)20-23 ->(0)24
													 ->(0)19 ->(300)24
									->(0)5 ->(300)7 ->(100)9 ->(0)12 ->(200)20 ->(200)20-23 ->(0)24
																					 ->(0)19 ->(300)24
*/

import java.io.*;
import java.util.*;

public class MaxNonOverlapInterval {

	class Interval {
		int start;
		int end;
		int profit;
		public Interval(int start, int end, int profit) {
			this.start = start;
			this.end = end;
			this.profit = profit;
		}
	}

	/**
	 * Solution: DP, compare all prev Interval leads to cut point
	 * Time: O(n^2)
	 * Space: O(n)
	 */
	public int findMaxProfit(List<List<String>> input) {
		if (input == null || input.size() == 0) {
		  return 0;
		}
		// order by start time.
		List<Interval> orderedIntervals = new ArrayList<>();
		for (List<String> list : input) {
			String[] time = list.get(0).split("-");
			int start = Integer.parseInt(time[0]);
			int end = Integer.parseInt(time[1]);
			int profit = Integer.parseInt(list.get(1));
			orderedIntervals.add(new Interval(start, end, profit));
		}
		Collections.sort(orderedIntervals, (a, b) -> {
			return a.start - b.start;
		});
		// DP => look back all results
		int[] dp = new int[orderedIntervals.size()];
		dp[0] = orderedIntervals.get(0).profit;
		int global = dp[0];

		for (int i = 1; i < orderedIntervals.size(); i++) {
			int maxPath = orderedIntervals.get(i).profit;
			// find j->i pathMax.
			for (int j = 0; j < i; j++) {
				if (orderedIntervals.get(j).end <= orderedIntervals.get(i).start) { // if no overlap
					// calc prev routes -> curPoint > any other paths.
					maxPath = Math.max(maxPath, dp[j] + orderedIntervals.get(i).profit);
				}
			}
			dp[i] = maxPath;
			global = Math.max(global, maxPath);
		}
		return global;
	}

	/**
	 * Follow up: Print path of all intervals of Max profit
	 */
	public void findMaxPath(List<List<String>> input) {
		if (input == null || input.size() == 0) {
			System.out.println("No interval");
		  return;
		}
		List<Interval> orderedIntervals = new ArrayList<>();
		// add inputs into interval in order
		for (List<String> list : input) {
			String[] time = list.get(0).split("-");
			int start = Integer.parseInt(time[0]);
			int end = Integer.parseInt(time[1]);
			int profit = Integer.parseInt(list.get(1));
			orderedIntervals.add(new Interval(start, end, profit));
		}
		// sort by start time.
		Collections.sort(orderedIntervals, (a, b) -> {
			return a.start - b.start;
		});

		// dp maxPaths to Interval[i];
		List<List<Interval>> maxPaths = new ArrayList<>();
		maxPaths.add(new ArrayList<>());
		// maxPath to first earliest startTime is itself.
		maxPaths.get(0).add(orderedIntervals.get(0));
		// Sum dp to interval[i];
		int[] sum = new int[orderedIntervals.size()];
		sum[0] = orderedIntervals.get(0).profit;

		for (int i = 1; i < orderedIntervals.size(); i++) {
			maxPaths.add(new ArrayList<>());
			// find prev intervals j not overlap i, find a max path to i.
			for (int j = 0; j < i; j++) {
				// check if not overlap.
				if (orderedIntervals.get(j).end <= orderedIntervals.get(i).start) {
					// check curPath-> i is > prevMaxPath; Find the maxPath to i.
					// maxPath[i] = Max(paths[j]) + interval[i].profit.
					if (sum[j] > sum[i]) {
						sum[i] = sum[j];
						maxPaths.set(i, new ArrayList<Interval>(maxPaths.get(j)));
					}
				}
			}
			// add curInterval[i].profit to maxPath to here.
			maxPaths.get(i).add(orderedIntervals.get(i));
			sum[i] += orderedIntervals.get(i).profit;
		}

		// find the maxPath from all paths.
		List<Interval> res = new ArrayList<>();
		int max = 0;
		for (int i = 0; i < maxPaths.size(); i++) {
			if (sum[i] > max) {
				max = sum[i];
				res = maxPaths.get(i);
			}
		}

		// found the maxPath, print it out
		printPath(res, max);
	}

	public void printPath(List<Interval> path, int total) {
		for (Interval inv : path) {
			System.out.print(inv.start + "-" + inv.end + " (" + inv.profit + ") -> ");
		}
		System.out.println("\nTotal profit " + total);
	}

	public static void main(String[] args) {
		MaxNonOverlapInterval sol = new MaxNonOverlapInterval();
		List<List<String>> input = new ArrayList<>();
		String[][] strs = new String[][] {{"2-4", "100"}, {"4-8", "200"}, {"5-7", "300"}, {"7-9", "100"}, {"12-20", "200"}, {"19-24", "300"}, {"20-23", "200"}};
		for (int i = 0; i < strs.length; i++) {
			input.add(new ArrayList<>());
			for (int j = 0; j < strs[i].length; j++) {
				input.get(i).add(strs[i][j]);
			}
		}
		System.out.println(sol.findMaxProfit(input));

		// print path
		sol.findMaxPath(input);
	}
}