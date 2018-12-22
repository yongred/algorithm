/**
310. Minimum Height Trees
For an undirected graph with tree characteristics, we can choose any node as the root. The result graph is then a rooted tree. Among all possible rooted trees, those with minimum height are called minimum height trees (MHTs). Given such a graph, write a function to find all the MHTs and return a list of their root labels.

Format
The graph contains n nodes which are labeled from 0 to n - 1. You will be given the number n and a list of undirected edges (each edge is a pair of labels).

You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.

Example 1 :

Input: n = 4, edges = [[1, 0], [1, 2], [1, 3]]

        0
        |
        1
       / \
      2   3 

Output: [1]
Example 2 :

Input: n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]

     0  1  2
      \ | /
        3
        |
        4
        |
        5 

Output: [3, 4]
Note:

According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.”
The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
*/

/**
Solution: BFS outer layer to inner (find leaves), Create the graph 0->(n-1) nodes.
Ex: n= 7
 1          0
     \      / 
2 -- 4 -- 5
    /      \  
3           6

layer1 leaves: 1,2,3,5,6,0;
layer2 leaves: 4;
leaves < 2;  ANS = [4];

How to arrive:
* Tree should not have cycle, 2 nodes connected by 1 path only.
Ex: 1 -- 2      This is not a tree, 1 to 3, have 2 paths.
    \   /
		  3

* Observations: 
	* If nodes size = odd: only 1 middle node is the shortest.
	* if nodes size = even: 2 middle nodes are the shortest.
	* Leaf nodes are the longest, can be eliminated first.
	* To find leaves, leaf have only 1 neighor.
	* Shortest height nodes always in the inner most layer.
* Solution: From outter most layer, remove the leaves. Until we have the inner most layer. 1 or 2 nodes. These node/nodes are the ans.
* Algorithm:
* Construct a graph using List<Set<Int>>; 
	* List indexes represent 0->n-1 nodes;
	* Set<int> represent the neighbors of index node. Using a Set instead of List, b/c Set support remove(val); list remove(index), list will update index after remove.
	Ex: Set: (3,4,2,6) rm(4) -> (3,2,6); 
	List: [(0)3, (1)4, (2)2, (3)6]; remove(object 4) => [(0)3, (1)2, (2)6];
	* We **just want to get(object), not get(index)**; So HashSet is good for this;
	* Graph: 1 -> (2,3,4);
* Declare and init the leafs:
	* Find all nodes with only 1 neighbor. Add them to leaves.
* BFS from outter most layer to inner.
	* while graphSize > 2:
		* declare list for storing newLeafs.
		* loop the cur leaves:
			* disconnect leaves with their only connection.
		  * If connectedNode after remove leaf, have only 1 neighbor left, then it becomes newLeaf;
		* Update graph size; graphSize - leaves removed;
		* newLeaves becomes leaves;
* The leftover nodes, last 1 or 2 nodes are the ans, with shortest height.
* Time: O(n);
* Space: O(n);
*/

class MinimumHeightTrees {
  
  /**
   * Solution: BFS outer layer to inner (find leaves), Create the graph 0->(n-1) nodes.
   * Time: O(n);
   * Space: O(n);
   */
  public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    // only 1 node, height is 0. No edge.
    if (n <= 1) {
      return Arrays.asList(0);
    }
    // construct a graph int -> list ints
    // 0->n-1 nodes, so index is node, connect to other indexes.
    // using hashset instead of arrayList, b/c Set allows remove by val.
    List<Set<Integer>> graph = new ArrayList<>(n);
    // init graph nodes with empty lists
    for (int i = 0; i < n; i++) {
      graph.add(i, new HashSet<>());
    }
    // init edges in graph
    for (int i = 0; i < edges.length; i++) {
      int[] pair = edges[i];
      // connect the 2 nodes bi-directionally.
      graph.get(pair[0]).add(pair[1]);
      graph.get(pair[1]).add(pair[0]);
    }
    // find the leafs, with 1 neighbor only
    List<Integer> leafs = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      // node with only 1 neighbor
      if (graph.get(i).size() == 1) {
        leafs.add(i);
      }
    }
    // BFS, from outter most layer to inner most layer.
    int curSize = n;
    // remove leafs from graph. Assign new leafs. last 2 or 1 is the ans.
    while (curSize > 2) {
      // new leafs after cur leafs removed.
      List<Integer> newLeafs = new ArrayList<>();
      // disconnect the leafs.
      for (int leaf : leafs) {
        // get the only node connected to leaf
        int connected = graph.get(leaf).iterator().next();
        // disconnect this node with leaf.
        graph.get(connected).remove(leaf);
        // if connected have only 1 neighbor left, it is a leaf now.
        if (graph.get(connected).size() == 1) {
          newLeafs.add(connected);
        }
      }
      // remove the leafs, leftover nodes goes to next traverse.
      curSize -= leafs.size();
      // new leafs update.
      leafs = newLeafs;
    }
    // last 2 or 1 nodes are the ans.
    return leafs;
  }
  
  
}