/**
Max Non Overlap Interval.
 coding:  新题，反正我没见过。题目是给你一堆24小时内旅店预定的信息，{{"2-4", "100"}, {"4-8", "200"}, {"5-7", "300"}}，要求找出收入最大的non-overlapping的时间段。现场写出来了。
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

	public int findMaxProfit(List<List<String>> input) {
		if (input == null || input.size() == 0) return 0;
		List<Interval> orders = new ArrayList<>();
		for (List<String> list : input) {
			String[] time = list.get(0).split("-");
			int start = Integer.parseInt(time[0]);
			int end = Integer.parseInt(time[1]);
			int profit = Integer.parseInt(list.get(1));
			orders.add(new Interval(start, end, profit));
		}
		Collections.sort(orders, (a, b) -> {
			return a.start - b.start;
		});
		// DP => look back all results
		int[] dp = new int[orders.size()];
		dp[0] = orders.get(0).profit;
		int global = dp[0];

		for (int i = 1; i < orders.size(); i++) {
			int maxPath = orders.get(i).profit;
			// find j->i pathMax.
			for (int j = 0; j < i; j++) {
				if (orders.get(j).end <= orders.get(i).start) { // if no overlap
					// calc prev routes -> curPoint > any other paths.
					maxPath = Math.max(maxPath, dp[j] + orders.get(i).profit);
				}
			}
			dp[i] = maxPath;
			global = Math.max(global, maxPath);
		}
		return global;
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
	}
}