/**
323. Number of Connected Components in an Undirected Graph
Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to find the number of connected components in an undirected graph.

Example 1:

     0          3

     |          |

     1 --- 2    4

Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.

Example 2:

     0           4

     |           |

     1 --- 2 --- 3

Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.

 Note:

You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
*/

/**
Solution: construnct UnionFind, use Union by Rank/size and Collapsing Find.
How to arrive:
* We are basically construct a graph, then find number of graphs not connected.
* We can use Union Find to construct the graph;
	* We keep a graphCount in the UnionFind. Init to n; Each successful union(n1, n2), graphCount--;
	* After each union, 2 sets merged, so less unconnected graph/set.
	* Skip when there is a Cycle topNode1 == topNode2; In the same set, no merge, graphCount same.
	* Use union by rank/size and Collapsing Find to optimize
	* Collapsing Find
	Ex: 4 -> 3 -> 2 -> 1;  find(1);  Going from 4 to 1;  4->1;  3->1;  2->1;
	* Union by Rank, compare size, larger size as parent.
* 
* Time: O(n) V+E;
* Space: O(n)

-----
Solution 2: DFS Or BFS
How to Arrvie:
* Since it is undirected Graph, We can just check all adj neighbors of a node. And record in visitedSet.
* Loop through nodes, if a node is not Visited, then it is not in prev traversed Graph.
* B/c undirected graph means nodes are strongly connected (all nodes in the graph is reachable to eachother).
* So if curNode is not visited then it's in diff graph from prevNodes.
* 
* Time: O(n) V+E;
* Space: O(n)
*/

import java.util.*;
import java.io.*;

public class ConnectedComponentsUndirectedGraph {

	// construct Union Find.
	public class UnionFind {
		// parents
		int[] parents;
		// sizes
		int[] sizes;
		// keep a count of graph. -1 each time union.
		int graphCount;

		public UnionFind(int n) {
			parents = new int[n];
			sizes = new int[n];
			for (int node = 0; node < n; node++) {
				// -1 for itself is topNode, no parent.
				parents[node] = -1;
				sizes[node] = 1;
			}
			graphCount = n;
		}

		public int findTopNode(int node) {
			// Collapsing Find, connect to topNode.
			if (parents[node] == -1) {
				// -1 parent, node is topNode;
				return node;
			}
			// assign all parent of nodes in path to TopNode
			// Compression. Ex: p(4) = p(3) = p(2) = p(1) = 1;
			parents[node] = findTopNode(parents[node]);
			return parents[node];
		}

		// Union by Rank, connect to larger size.
		public void union(int node1, int node2) {
			int topNode1 = findTopNode(node1);
			int topNode2 = findTopNode(node2);
			// check cycle
			if (topNode1 == topNode2) {
				return;
			}
			// compare their size/rank
			if (sizes[topNode2] > sizes[topNode1]) {
				parents[topNode1] = topNode2;
				sizes[topNode2] += sizes[topNode1];
			} else {
				// topNode1 size >= topNode2;
				parents[topNode2] = topNode1;
				sizes[topNode1] += sizes[topNode2];
			}
			// Union 2 sets, 1 less separated graph.
			graphCount--;
		}
	}

	/**
	 * Solution: construnct UnionFind, use Union by Rank/size and Collapsing Find.
	 * Time: O(n) V+E;
	 * Space: O(n)
	 */
	public int countComponents(int n, int[][] edges) {
		UnionFind graph = new UnionFind(n);
		// loop connect edges
		for (int i = 0; i < edges.length; i++) {
			int[] pair = edges[i];
			graph.union(pair[0], pair[1]);
		}
		// return graphCount.
		return graph.graphCount;
	}

	public static void main(String[] args) {
		// get all inputs
		Scanner scanner = new Scanner(System.in);
		int cases = scanner.nextInt();
		int[] resArr = new int[cases];
		for (int c = 0; c < cases; c++) {
			int n = scanner.nextInt();
			int edgeSize = scanner.nextInt();
			scanner.nextLine();  // Consume newline left-over
			int[][] edges = new int[edgeSize][2];
			// get edge inputs
			for (int i = 0; i < edgeSize; i++) {
				String edgeStr = scanner.nextLine();
				String[] pair = edgeStr.split(" ");
				int n1 = Integer.parseInt(pair[0]);
				int n2 = Integer.parseInt(pair[1]);
				edges[i][0] = n1;
				edges[i][1] = n2;
			}

			// find components
			ConnectedComponentsUndirectedGraph obj = new ConnectedComponentsUndirectedGraph();
			int res = obj.countComponents(n, edges);
			resArr[c] = res;
		}
		
		// print all res
		for (int res : resArr) {
			System.out.println(res);
		}
		
	}
}