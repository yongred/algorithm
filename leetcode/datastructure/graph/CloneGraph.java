/**
133. Clone Graph
Given the head of a graph, return a deep copy (clone) of the graph. Each node in the graph contains a label (int) and a list (List[UndirectedGraphNode]) of its neighbors. There is an edge between the given node and each of the nodes in its neighbors.


OJ's undirected graph serialization (so you can understand error output):
Nodes are labeled uniquely.

We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.
 

As an example, consider the serialized graph {0,1,2#1,2#2,2}.

The graph has a total of three nodes, and therefore contains three parts as separated by #.

First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
Second node is labeled as 1. Connect node 1 to node 2.
Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.
 

Visually, the graph looks like the following:

       1
      / \
     /   \
    0 --- 2
         / \
         \_/
Note: The information about the tree serialization is only meant so that you can understand error output if you get a wrong answer. You don't need to understand the serialization to solve the problem.
*/

/**
Solution 1: BFS, using queue and HashMap to map <origin, clone>; 
How to Arrive:
* To clone a graph, we just need to traverse the graph, so BFS is a choice.
* **Key**: We need the parentCloned node to add children Cloned nodes, while traversing the children.
* We can do that using a Map<origin, clone>;
* **Key**: Also check for visited nodes. If not visited (not cloned new node), clone the node and add to queue for next level traverse.
* Case Not visited/cloned (map not containskey child):
	* Create a cloneChild;
	* curCloneNode.neighbors.add(cloneChild); 
	* Map (originChild, cloneChild);
	* queue.add(child);
* Case Visited (already cloned):
	* Use the map to get cloneParent and cloneChild. Connect them.

* Time: O(V + E); Traversed each node once, not the visited ones. Then traverse each edge 1 time.
* Cloning Vs and Es one time.
* Space: O(V); No edges, it's just all vertices, since they are inter-connected.

--------------------

Solution 2: DFS, using HashMap to map <origin, clone>, Recursion.
How to Arrive:
* We can recursively go down the branch, clone and connect deepest nodes to upper nodes.
* Base case:
	* curNode is null, then return null.
	* curNode visited/cloned, map.containsKey(node):
		* return the clonedNode using map.get(node).
* Recursive Case:
	* If not visited/cloned:
		* Create a clone for curNode.
		* Map curNode with cloneNode.
		* Loop the neighbors of curNode:
			* Recurse call to clone each neighbor's branch 1 by 1.
			* Add/Connect curCloneNode to clonedNeighbor.
	 `for (UndirectedGraphNode neighbor : node.neighbors) {
      // clone the neighbor branch of curNode.
      UndirectedGraphNode cloneNeighbor = helper(neighbor, map);
      // add cloned branch to clonedNode.
      cloneNode.neighbors.add(cloneNeighbor);
    }`
* return curClonedNode.
* Time: O(V + E); Traversed each node once, not the visited ones. Then traverse each edge 1 time. Cloning Vs and Es one time.
* Space: O(V); No edges, it's just all vertices, since they are inter-connected.

*/

/**
 * Definition for undirected graph.
 * class UndirectedGraphNode {
 *     int label;
 *     List<UndirectedGraphNode> neighbors;
 *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
 * };
 */
public class CloneGraph {

	/**
	 * Solution 2: DFS, using HashMap to map <origin, clone>
	 *
	 * Time: O(V + E); Traversed each node once, not the visited ones. Then traverse each edge 1 time.
   * Cloning Vs and Es one time.
   * Space: O(V); No edges, it's just all vertices, since they are inter-connected.
	 */
	public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
    if (node == null) {
      return null;
    }
    // map origin, clone.
    Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
    // DFS
    return helper(node, map);
  }
  
  public UndirectedGraphNode helper(UndirectedGraphNode node,
      Map<UndirectedGraphNode, UndirectedGraphNode> map) {
    if (node == null) {
      return null;
    }
    // visited, cloned
    if (map.containsKey(node)) {
      // return cloned curNode.
      return map.get(node);
    }
    // not visited/cloned, create clone node.
    UndirectedGraphNode cloneNode = new UndirectedGraphNode(node.label);
    // map the origin and clone
    map.put(node, cloneNode);
    // DFS. visit, clone neighbor branches.
    for (UndirectedGraphNode neighbor : node.neighbors) {
      // clone the neighbor branch of curNode.
      UndirectedGraphNode cloneNeighbor = helper(neighbor, map);
      // add cloned branch to clonedNode.
      cloneNode.neighbors.add(cloneNeighbor);
    }
    return cloneNode;
  }
  
  /**
   * Solution 1: BFS, using queue and HashMap to map <origin, clone>; 
   * Time: O(V + E); Traversed each node once, not the visited ones. Then traverse each edge 1 time.
   * Cloning Vs and Es one time.
   * Space: O(V); No edges, it's just all vertices, since they are inter-connected.
   */
  public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
    if (node == null) {
      return null;
    }
    // clone the root.
    UndirectedGraphNode cloneRoot = new UndirectedGraphNode(node.label);
    // queue use for traverse BFS
    Deque<UndirectedGraphNode> queue = new ArrayDeque<UndirectedGraphNode>();
    // We use hashmap to check visited nodes. And mapping origin -> clone.
    Map<UndirectedGraphNode, UndirectedGraphNode> hashmap = new HashMap<>();
    // map nodes together, so we know which clone node to add child.
    hashmap.put(node, cloneRoot);
    queue.addLast(node);
    // BFS
    while (!queue.isEmpty()) {
      // cur layer of nodes
      UndirectedGraphNode curNode = queue.pollFirst();
      // clone node's children.
      for (UndirectedGraphNode child : curNode.neighbors) {
        // check node if visited
        if (!hashmap.containsKey(child)) {
          // not visited, 
          // create new clone
          UndirectedGraphNode cloneChild = new UndirectedGraphNode(child.label);
          // add cloneChild to cloneParent.
          hashmap.get(curNode).neighbors.add(cloneChild);
          // map cloneChild and child together, for identity. 
          // Use to add future grandChildren clones.
          hashmap.put(child, cloneChild);
          // add child to queue for next level traverse.
          queue.addLast(child);
        } else {
          // visited
          // already cloned this node, no need to revisit, so not adding to queue.
          // just need to connect parentClone to childClone (this child's clone)
          UndirectedGraphNode cloneChild = hashmap.get(child);
          hashmap.get(curNode).neighbors.add(cloneChild);
        }
      }
    }
    // all cloned and connected.
    return cloneRoot;
  }
  
}