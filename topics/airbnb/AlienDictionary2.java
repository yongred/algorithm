/**
Follow up: print all possible paths

892. Alien Dictionary
There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you. You receive a list of non-empty words from the dictionary, where words are sorted lexicographically by the rules of this new language. Derive the order of letters in this language.

第一轮是给一堆按某种方式排序好的单词，让你猜字母顺序，返回任意方案，解法就是每个字母是图中的点，构建有向图，然后做拓扑排序，bfs或者dfs都行。follow up是给出所有可能的方案，这里就需要dfs来枚举拓扑排序中每次选哪个点，记得回溯的时候恢复之前的选择。

Example
Given the following words in dictionary,

[
  "wrt",
  "wrf",
  "er",
  "ett",
  "rftt"
]
The correct order is: "wertf"

Given the following words in dictionary,

[
  "z",
  "x"
]
The correct order is: "zx".

Notice
You may assume all letters are in lowercase.
You may assume that if a is a prefix of b, then a must appear before b in the given dictionary.
If the order is invalid, return an empty string.
*/

/*
Solution: Backtracking, reset visited, list, and its adj indegrees.
Ex:
"caa", "daa", "bcc", "bee", "baa", "baag", "baaa"

c -> d -> b
  -> e -> a
g -> a

How to Arrive:
* Key: a unrelated node can intersect the other nodes not related at any time. 
* Becomes a permutation of the unrelated nodes problem while preserving the order of directly connected.
* We know that 0 indegrees nodes (No Edge between) are not directly related, so they can order in diff combos.
* Now with those 0 indegrees, as we visited(removed) them parents, we removed the edge of its adj direct children.
* Which means their children are disconnected (parent edge removed). And there for they can order int diff combos.
* And we can do that layer by layer just like BFS.
Algorithm:
* Build a graph with indegrees;
* Backtrack Helper: graph, list of curOrder.
* Loop through each vertices (keys):
	* Find indegree0 nodes, and Not visited:
		* add to visited
		* add to curOrderList
		* remove this node and edges to adjs, by all adj node indegrees-1; (created new indepent graphs)
		* Now we want to traverse all combos in the graph without curChoosedNode.
		* After done with curNode, we want to try another node for curPos, Backtracking.
		* reset curChar from visited, curOrderList, and reset its adj indegrees+1. (recover its paths unvisited);
* When curOrderList/visited == vertices/keys; We find a order to Print out.
* If never find a order, Cycle.

* Time: O(n!); all permutations.
* Space: O(n!); all permutations.
*/

import java.io.*;
import java.util.*;

public class AlienDictionary2 {

	/**
	 * Solution: Backtracking, reset visited, list, and its adj indegrees.
	 * Time: O(n!); all permutations.
	 * Space: O(n!); all permutations.
	 */
	public void findOrders(String[] words) {
		Map<Character, Set<Character>> graph = new HashMap<>();
		buildGraph(graph, words);
		helper(graph, new ArrayList<>());
	}

	int[] indegrees = new int[26];
	HashSet<Character> visited = new HashSet<>();

	public void helper(Map<Character, Set<Character>> graph, ArrayList<Character> list) {
		for (char key : graph.keySet()) {
			if (!visited.contains(key) && indegrees[key - 'a'] == 0) {
				visited.add(key);
				list.add(key);
				for (char adj : graph.get(key)) {
					indegrees[adj - 'a']--;
				}
				helper(graph, list);
				// backtrack, remove curChar from visited, list, and reset its adj indegrees.
				visited.remove(key);
				list.remove(list.size() - 1);
				for (char adj : graph.get(key)) {
					indegrees[adj - 'a']++;
				}
			}
		}

		if (list.size() == graph.size()) {
			list.forEach(curChar -> {
				System.out.print(curChar);
			});
			System.out.println();
		}
	}

	public void buildGraph(Map<Character, Set<Character>> graph, String[] words) {
		// -1 indicates char not in graph.
		Arrays.fill(indegrees, -1);
		for (String word : words) {
			for (char c : word.toCharArray()) {
				graph.put(c, new HashSet<>());
				indegrees[c - 'a'] = 0;
			}
		}
		// connect edges
		for (int i = 0; i < words.length - 1; i++) {
			String w1 = words[i];
			String w2 = words[i + 1];
			int len = Math.min(w1.length(), w2.length());

			for (int j = 0; j < len; j++) {
				// 2 chars
				char larger = w1.charAt(j);
				char smaller = w2.charAt(j);
				if (larger != smaller) {
					// find a order, smaller->larger, dfs larger visit first.
					graph.get(larger).add(smaller);
					indegrees[smaller - 'a']++;
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		AlienDictionary2 obj = new AlienDictionary2();
		String[] words = new String[] {"caa", "daa", "bcc", "bee", "baa", "baag", "baaa"};
		obj.findOrders(words);
	}
}