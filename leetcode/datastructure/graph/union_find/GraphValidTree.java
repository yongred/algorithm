/**
261. Graph Valid Tree
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
  
  
}