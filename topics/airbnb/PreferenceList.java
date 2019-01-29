/**
Preference List
Given a list of everyone's preferred city list, output the city list following the order of everyone's preference order.

For example, input is [[3, 5, 7, 9], [2, 3, 8], [5, 8]]. One of possible output is [2, 3, 5, 8, 7, 9].

Leetcode 相似问题: leetcode #269 Alien Dictionary
http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=218938

也是地里出现过的了,每个人都有一个preference的排序,在不违反每个人的 preference的情况下
得到总体的preference的排序
拓扑排序解决你有list of list,这些叫preference list。

例如:
[[3, 5, 7, 9], [2, 3, 8], [5, 8]]
然后你要根据这个输入,输出一个总的preference list。 这这一题应该就是:
[2, 3, 5, 8, 7, 9]

因为这道题可能有多种符合要求的输出,如何 break tie by person 1,也就是说 bfs 的时候每次优先
选择 person 1 list 里面的元素。

*/

/**
Question:
- What to do with Cycle?
Assume, return [] empty;

*/

import java.io.*;
import java.util.*;

public class PreferenceList {

	public List<Integer> getPreference(List<List<Integer>> preferences) {
		// parent Int > children ints
		Map<Integer, Set<Integer>> graph = new HashMap<>();
		// person1 init
		for (int n : preferences.get(0)) {
			person1.add(n);
		}
		buildGraph(preferences, graph);
		bfs(preferences, graph);
		return res;
	}

	List<Integer> res = new ArrayList<>();
	// val, count.
	HashMap<Integer, Integer> indegrees = new HashMap<>();
	HashSet<Integer> person1 = new HashSet<>();

	public void bfs(List<List<Integer>> preferences, Map<Integer, Set<Integer>> graph) {
		// PriorityQueue to keep person1's preference higher when 2 num have == order.
		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
			if (person1.contains(a) ^ person1.contains(b)) {
				return person1.contains(a) ? -1 : 1;
			} else {
				return a - b;
			}
		});
		// add 0 indegrees to pq;
		for (int key : indegrees.keySet()) {
			if (indegrees.get(key) == 0) {
				pq.add(key);
			}
		}
		// BFS
		while (!pq.isEmpty()) {
			int cur = pq.poll();
			res.add(cur);
			// get it's children
			for (int child : graph.get(cur)) {
				// 1 less indegree
				indegrees.put(child, indegrees.get(child) - 1);
				if (indegrees.get(child) == 0) {
					pq.add(child);
				}
			}
		}
		// check for cycle, all indegrees should be finished/0 if no cycle.
		// Or resList size == indegrees.size;
		if (res.size() != indegrees.size()) {
			res = new ArrayList<>();
		}
	}

	public void buildGraph(List<List<Integer>> preferences, Map<Integer, Set<Integer>> graph) {
		// create empty hashsets for graph
		for (List<Integer> list : preferences) {
			for (int n : list) {
				graph.putIfAbsent(n, new HashSet<>());
				// init indegrees to 0 count.
				indegrees.putIfAbsent(n, 0);
			}
		}
		// for parent and children by list order
		for (List<Integer> list : preferences) {
			for (int i = 0; i < list.size() - 1; i++) {
				int n1 = list.get(i);
				int n2 = list.get(i + 1);
				// connect n1 to n2;
				graph.get(n1).add(n2);
				indegrees.put(n2, indegrees.get(n2) + 1);
			}
		}
	}

	public static void main(String[] args) {
		PreferenceList obj = new PreferenceList();
		List<List<Integer>> preferences = new ArrayList<>();
		int[][] twoDArr= new int[][] {{3, 5, 7, 9}, {2, 3, 8}, {5, 8}};
		for (int i = 0; i < twoDArr.length; i++) {
			preferences.add(new ArrayList<>());
			for (int j = 0; j < twoDArr[i].length; j++) {
				preferences.get(i).add(twoDArr[i][j]);
			}
		}
		List<Integer> res = obj.getPreference(preferences);
		res.forEach(n -> {
			System.out.print(n + ", ");
		});
		System.out.println();
	}
}