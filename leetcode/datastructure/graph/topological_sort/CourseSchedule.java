/**
207. Course Schedule
There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

Example 1:

Input: 2, [[1,0]] 
Output: true
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0. So it is possible.
Example 2:

Input: 2, [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0, and to take course 0 you should
             also have finished course 1. So it is impossible.
Note:

The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.
*/

/**
Solution 1: Topological sort, DFS, visiting & visited.

How to Arrive:
* This question is basically telling you to detect a cycle in a graph. We can use topological sort.
* construct a graph 0->n-1 nodes, with all the prerequistites (edges); either direction is fine.
* 1 way to traverse the graph is DFS. 
* **Key**: 2 Sets needed for DFS: 
	* visitedSet for node that traversed all branches (pop from the stack);
	* visitingSet for curNode going through DFS path traverse.
* To detect a cycle:
* visitingSet add curNode means going down curNode's neighbors path. DFS.
* if during curNode traversing down the path, if path circles back to curNode: visitingSet.contains(curNode) Then there is a cycle.
* If visitedSet contains curNode, meaning it is traversed branches of curNode, not found any cycle.
* DFS all nodes 0->n-1; 
* If no cycle return true; else false;
* Time: O(n), V+E;
* Space: O(n)

----------------------

Solution 2: Topological sort, BFS, count total classes able to take.
Ex:
c0
    \
c1 -- c4
    \     \
c2 -- c5 -- c6

c0: 0 indegree;  c1: 0 indegree;  c2: 0 indegree; Enqueue them.
c4: 2 ins, -1(c0) = 1 ins, -1(c1) = 0 ins; Enqueue
c5: 2 ins, -1(c1) = 1 ins, -1(c2) = 0 ins; Enqueue
c6: 2 ins; -1(c4) = 1 ins, -1(c5) = 0 ins; Enqueue

Q: c0-, c1-, c2-, c4-, c5-, c6-;
Count = 1(c0) + 1(c1) + 1(c2) + 1(c4) + 1(c5) + 1(c6) = 6
ANS = 6;

How to Arrive:
* Like DFS, we construct the same graph, nodes index 0->n-1;
* For BFS topological sorts, we need a in-degree counts for each node;
	* in-degree: is the pointers pointing to curNode.
	* out-degree: is the curNode's pointers pointing out to neighbors.
* nodes with 0 indegree, means no dependedClasses needed. So we can take those classes 1st. Add them to Queue.
* **Key**: when all node's indegree becomes 0, then we can take all classes. Else we cannot take all classes, there is a cycle;
Ex:
c1 -> c2, c2 -> c3, c3-> c1;
all of them have 1 indegree. Therefore nobody is added to queue to take the class.
* We will **keep a totalClassTaken count**.
* While queue is not empty:
	* poll the course that can be taken
	* totalClassTaken + 1;
	* the dependedClasses (neighbors) have 1 less indegree (depended class);
	* indegree of neighbor/dependedClass - 1;
  * If indegree of dependedClass == 0:
		* we can take this class next, Add to queue;
* if  totalClassTaken == number of courses
	* return true, able to take all classes.
* else return false;
* Time: O(n), V+E
* Space: O(n)
*/

class CourseSchedule {

	/**
	 * Solution 2: Topological sort, BFS, count total classes able to take.
	 * Time: O(n), V+E
	 * Space: O(n)
	 */
	public boolean canFinish(int numCourses, int[][] prerequisites) {
    // construct a graph of Indexes 0-> n-1 as nodes.
    List<Set<Integer>> graph = new ArrayList<>(numCourses);
    // init empty set to add neighbors
    for (int i = 0; i < numCourses; i++) {
      graph.add(i, new HashSet<>());
    }
    // in-degrees;
    int[] indegrees = new int[numCourses];
    // init edges
    for (int i = 0; i < prerequisites.length; i++) {
      int[] pair = prerequisites[i];
      // either connect dir is fine; We do c1->c0;
      graph.get(pair[1]).add(pair[0]);
      // c0 have 1 more indegree, -> pointing to it.
      indegrees[pair[0]]++;
    }
    // BFS found out total classes we can take.
    int takeCount = bfs(graph, indegrees);
    // able to take all classes?
    return (takeCount == numCourses);
  }
  
  public int bfs(List<Set<Integer>> graph, int[] indegrees) {
    // Queue for bfs
    Deque<Integer> queue = new ArrayDeque<>();
    // count total course taken
    int takeCount = 0;
    // zero indegrees add to queue first. As able to take courses.
    for (int node = 0; node < indegrees.length; node++) {
      // no pre-req class need
      if (indegrees[node] == 0) {
        // take this course first
        queue.addLast(node);
      }
    }
    // bfs
    while (!queue.isEmpty()) {
      int course = queue.pollFirst();
      // total taken course + 1;
      takeCount++;
      // indegree of course's neighbor -1;
      Set<Integer> neighbors = graph.get(course);
      for (int neighbor : neighbors) {
        indegrees[neighbor]--;
        // if able to take this class, no more depend class needed.
        if (indegrees[neighbor] == 0) {
          queue.addLast(neighbor);
        }
      }
    }
    // return the total classes able to take.
    return takeCount;
  }

	/**
	 * Solution 1: Topological sort, DFS, visiting & visited.
	 * Time: O(n), V+E;
	 * Space: O(n)
	 */
  public boolean canFinish(int numCourses, int[][] prerequisites) {
    // construct a graph, index nodes -> (other indexes 0 to n-1);
    List<Set<Integer>> graph = new ArrayList<>(numCourses);
    // init graph with empty set to add neighbors
    for (int i = 0; i < numCourses; i++) {
      graph.add(i, new HashSet<>());
    }
    // init graph edges
    for (int i = 0; i < prerequisites.length; i++) {
      int[] pair = prerequisites[i];
      // one dir connect. to take c1, first take c0; 
      // c0->c1 Or c1->c0; Doesn't really matter, we just need to detect cycle.
      graph.get(pair[1]).add(pair[0]);
    }
    // visited nodes have traversed all neighbors, no need to dfs again.
    Set<Integer> visited = new HashSet<>();
    // currently DFS visiting path nodes. Use to detect cycle.
    Set<Integer> visiting = new HashSet<>();
    // DFS each node, to detech cycle.
    for (int course = 0; course < numCourses; course++) {
      // dfs detect cycle
      boolean cycle = dfsCycle(graph, visiting, visited, course);
      if (cycle) {
        return false;
      }
    }
    return true;
  }
  
  public boolean dfsCycle(List<Set<Integer>> graph, Set<Integer> visiting,
      Set<Integer> visited, int node) {
    // detech cycle, visiting path circle back.
    if (visiting.contains(node)) {
      return true;
    }
    // curNode is visited (all neighbor branch traversed), not detect any cycle.
    if (visited.contains(node)) {
      return false;
    }
    // add this node to cur visiting path
    visiting.add(node);
    // traverse deep in this path, neighbors of curNode.
    Set<Integer> neighbors = graph.get(node);
    for (int neighbor : neighbors) {
      boolean cycle = dfsCycle(graph, visiting, visited, neighbor);
      if (cycle) {
        return true;
      }
    }
    // finish visiting this node's paths.
    visiting.remove(node);
    // finished traverse with all curNode neighbor branches.
    visited.add(node);
    // no cycle found. return false
    return false;
  }
}