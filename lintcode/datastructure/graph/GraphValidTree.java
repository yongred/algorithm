/**
178. Graph Valid Tree
Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to check whether these edges make up a valid tree.

Example
Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.

Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.

Notice
You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
*/

public class GraphValidTree {

	/**
	 * Solution: Union Find, with Union By Rank and Collapsing find.
	 * Time: O(n) V+E
	 * Space: O(n)
	 */
	public class UnionFind {
    // parent of nodes
    public int[] parents;
    // number of nodes points to thisNode.
    public int[] sizes;
    
    public UnionFind(int n) {
      parents = new int[n];
      sizes = new int[n];
      // init node's parent and sizes
      for (int node = 0; node < n; node++) {
        // -1 means self is topNode, no parent.
        parents[node] = -1;
        // only contain itself, 1 total node.
        sizes[node] = 1;
      }
    }
    
    /**
     * Compression/Collapsing Find.
     * O(lgN) in worse case; 
     * Combine with Union Rank Find, Amortised O(1) to construct the graph.
     */
    public int findTopNode(int node) {
      // -1 means itself is the TopNode, no more pointer.
      if (parents[node] == -1) {
        return node;
      }
      // find node's top, while make all nodes connect to the topNode.
      // Compression. Ex: p(4) = p(3) = p(2) = p(1) = 1;
      parents[node] = findTopNode(parents[node]);
      return parents[node];
    }
    
    /**
     * Union By Rank.
     * O(1); Combine with Collapsing Find,Amortised O(1) to construct the graph.
     */
    public void union(int node1, int node2) {
      int topNode1 = findTopNode(node1);
      int topNode2 = findTopNode(node2);
      // cycle
      if (topNode1 == topNode2) {
        return;
      }
      // compare size as rank. Higher # of size/rank become the parent.
      if (sizes[topNode2] > sizes[topNode1]) {
        parents[topNode1] = topNode2;
        // all nodes in topNode1 belongs to topNode2;
        sizes[topNode2] += sizes[topNode1];
      } else {
        // topNode1 size >= topNode2 size;
        parents[topNode2] = topNode1;
        // all nodes in topNode2 belongs to topNode1;
        sizes[topNode1] += sizes[topNode2];
      }
    }
  }
  
  /**
   * @param n: An integer
   * @param edges: a list of undirected edges
   * @return: true if it's a valid tree, or false
   */
  public boolean validTree(int n, int[][] edges) {
    // init the graph.
    UnionFind graph = new UnionFind(n);
    // loop edges detect cycle.
    for (int i = 0; i < edges.length; i++) {
      int[] pair = edges[i];
      // Find nodes' topNode.
      int topNode1 = graph.findTopNode(pair[0]);
      int topNode2 = graph.findTopNode(pair[1]);
      // check cycle, tree cannot have cycle. 1 path between 2 nodes.
      if (topNode1 == topNode2) {
        return false;
      }
      // Union connect them.
      graph.union(topNode1, topNode2);
    }
    // graph can be tree if all nodes are connected in one tree.
    // detect if all nodes in one Tree. The topNode should have n size.
    for (int size : graph.sizes) {
      if (size == n) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param n: An integer
   * @param edges: a list of undirected edges
   * @return: true if it's a valid tree, or false
   * Solution 1: Union Find, detect cycle, and is one tree only.
   * Time: O(n + e), V+E; Using compression (collapsing find) to optimize.
   * Space: O(n);
   */
  public boolean validTree(int n, int[][] edges) {
    // construct a Union find graph. Indexes nodes 0-> n-1;
    // node is index, parent is val.
    int[] graph = new int[n];
    // init all values to -1: self is parent (1 total child);
    Arrays.fill(graph, -1);
    // traverse edges to connect sets into graph, and detect cycle
    for (int i = 0; i < edges.length; i++) {
      int[] pair = edges[i];
      // find their top parent node.
      int p0 = pair[0];
      int p1 = pair[1];
      // positive parent node means have parent. find n0's top parent.
      while (graph[p0] >= 0) {
        p0 = graph[p0];
      }
      // find top parent node of n1;
      while (graph[p1] >= 0) {
        p1 = graph[p1];
      }
      // found top node for both. If they same, then it is a cycle.
      if (p0 == p1) {
        return false;
      } else {
        // Not the same parent, union the graph. 
        // fewer nodes graph connect to more nodes graph.
        if (Math.abs(graph[p1]) > Math.abs(graph[p0])) {
          // merge p0 graph to p1;
          graph[p1] += graph[p0];
          graph[p0] = p1;
          // union by rank, also add child node to new topNode.
          // now just Amortised O(1) find, instead of O(n), if this node get call again.
          if (p0 != pair[0]) {
          	graph[pair[0]] = p1;
          }
          if (p1 != pair[1] && p1 != graph[pair[1]]) {
          	graph[pair[1]] = p1;
          }
        } else {
          // p0 nodes >= p1, merge p1 to p0
          graph[p0] += graph[p1];
          graph[p1] = p0;
          // union by rank, also add child node to new topNode.
          // now just Amortised O(lgN) at worse find, instead of O(n), if this node get call again.
          if (p1 != pair[1]) {
	          graph[pair[1]] = p0;
	        }
	        if (p0 != pair[0] && p0 != graph[pair[0]]) {
          	graph[pair[0]] = p0;
          }
        }
      }
    }
    // If there is only one tree left after unions, 
    // then there should be only 1 negative parent, and |negParent| = n;
    for (int node = 0; node < n; node++) {
      // found negative, the top node. And its parent = n;
      if (graph[node] < 0 && Math.abs(graph[node]) == n) {
        return true;
      }
    }
    return false;
  }
  
  
}