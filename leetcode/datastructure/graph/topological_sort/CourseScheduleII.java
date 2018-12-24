/**
210. Course Schedule II
There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.

There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.

Example 1:

Input: 2, [[1,0]] 
Output: [0,1]
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished   
             course 0. So the correct course order is [0,1] .
Example 2:

Input: 4, [[1,0],[2,0],[3,1],[3,2]]
Output: [0,1,2,3] or [0,2,1,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both     
             courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0. 
             So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
Note:

The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.

*/

/**
Solution 1: Topological sort, DFS.
How to Arrive:
* Almost the same as CourseSchedule I.
* The main differences:
	* **For dfs, We want the deepest node to be added first**. Therefore we going to make less depended classes as leaves.
	Ex: [0,1] [0,2] 
	c0 needs c1 to be taken first; So graph is c0 -> c1;
	c0 also needs c2 to be taken; so graph is c0->c2;
	c0 --> c1
	     \
			   c2;
	* Another difference is the return; We are still detecting Cycle. If there is Cycle we return [] array, meaning we cannot finish the course.
 * When detecting doing dfs, we add finished/visited nodes to resList; When curNode has no more depended classes to take, we take this class (add to res);
* Time: O(n) V+E
* Space: O(n);

--------------------

Solution 2: Topological sort, BFS
How to arrive:
* Almost the same as CourseSchedule I.
* The main differences:
	* Like solution 1 DFS, the order of how graph connected matters.
	* **For BFS, top levels are traversed first, so we want no/less dependent classes on top to take first**; Opposite to DFS.
		* [0,1] c0 needs c1 to complete, so c1->c0; c1 take first.
  * Another difference is the return result.
		* We want to return the list of classOrders and also checking if there is a cycle.
		* For BFS, we just need to count the totalClasses able to take, *Indegree/dependence becomes 0* or cycles cannot have 0 indegree.
		* BFS function will return a list of classOrder, it's size should == numCourses.
* Time: O(n) V+E
* Space: O(N)
*/

class CourseScheduleII {

	/**
	 * Solution 2: Topological sort, BFS
	 * Time: O(n) V+E
	 * Space: O(N)
	 */
	public int[] findOrder(int numCourses, int[][] prerequisites) {
    // construct the graph.
    List<Set<Integer>> graph = new ArrayList<>();
    // init hashsets as neighbors
    for (int i = 0; i < numCourses; i++) {
      graph.add(i, new HashSet<>());
    }
    // indegrees count
    int[] indegrees = new int[numCourses];
    // init edges, [0,1] as c1->c0; c1 take first then c0;
    for (int i = 0; i < prerequisites.length; i++) {
      int[] pair = prerequisites[i];
      // [0,1] c0 needs c1 to complete, so c1->c0; c1 take first.
      graph.get(pair[1]).add(pair[0]);
      // c0 + 1 indegree/depended class
      indegrees[pair[0]]++;
    }
    // BFS res list add course taken.
    List<Integer> res = new ArrayList<>();
    bfs(graph, indegrees, numCourses, res);
    // if res list size != numCourses, not all class can be taken.
    if (res.size() != numCourses) {
      return new int[0];
    }
    // convert res into array.
    int[] resArray = new int[res.size()];
    for (int i = 0; i < res.size(); i++) {
      resArray[i] = res.get(i);
    }
    return resArray;
  }
  
  public void bfs(List<Set<Integer>> graph, int[] indegrees, int numCourses,
      List<Integer> classOrder) {
    // queue
    Deque<Integer> queue = new ArrayDeque<>();    
    // add 0 dependence classes to queue, take them first.
    for (int node = 0; node < numCourses; node++) {
      if (indegrees[node] == 0) {
        queue.addLast(node);
      }
    }
    // bfs
    while (!queue.isEmpty()) {
      int course = queue.pollFirst();
      // save the class taken order.
      classOrder.add(course);
      // neighbor classes
      Set<Integer> neighbors = graph.get(course);
      // neighbor classes have 1 less indegree/depended class.
      for (int neighbor : neighbors) {
        indegrees[neighbor]--;
        // neighbor class have no more dependence, take this class.
        if (indegrees[neighbor] == 0) {
          queue.addLast(neighbor);
        }
      }
    }
  }

	/**
	 Solution 1: Topological sort, DFS.
	 * Time: O(n) V+E
	 * Space: O(n);
	 */
  public int[] findOrder(int numCourses, int[][] prerequisites) {
    // build the graph.
    List<Set<Integer>> graph = new ArrayList<>(numCourses);
    // init hashsets for depend classes
    for (int i = 0; i < numCourses; i++) {
      graph.add(i, new HashSet<>());
    }
    // init edges
    for (int i = 0; i < prerequisites.length; i++) {
      int[] pair = prerequisites[i];
      // connect c0 -> c1; [0,1] to take class0, need c1 first.
      // for dfs we want to finish all class's depended classes first. Then add curNode.
      graph.get(pair[0]).add(pair[1]);
    }
    // visiting set for cur path nodes
    Set<Integer> visiting = new HashSet<>();
    // visited set for node finished traverse all neighbor branches.
    Set<Integer> visited = new HashSet<>();
    // res list to add nodes for path.
    List<Integer> res = new ArrayList<>();
    // DFS detect cycle, and res add taken classes.
    for (int node = 0; node < numCourses; node++) {
      boolean cycle = dfsCycle(graph, visiting, visited, res, node);
      if (cycle) {
        return new int[0];
      }
    }
    // return the order in array
    int[] arrRes = new int[res.size()];
    for (int i = 0; i < res.size(); i++) {
      arrRes[i] = res.get(i);
    }
    return arrRes;
  }
  
  public boolean dfsCycle(List<Set<Integer>> graph, Set<Integer> visiting,
      Set<Integer> visited, List<Integer> res, int node) {
    // detect cycle, visiting path circles back to curNode.
    if (visiting.contains(node)) {
      return true;
    }
    // visited curNode's branches already. Course took.
    if (visited.contains(node)) {
      return false;
    }
    // visiting path include this node.
    visiting.add(node);
    // traverse all neighbors of node.
    Set<Integer> neighbors = graph.get(node);
    for (int neighbor : neighbors) {
      // visit neighbor dfs
      boolean cycle = dfsCycle(graph, visiting, visited, res, neighbor);
      if (cycle) {
        return cycle;
      }
    }
    // add node as taken class
    res.add(node);
    // remove from visiting path, finished curNode's path.
    visiting.remove(node);
    // finish visit all branches of curNode.
    visited.add(node);
    // no cycle
    return false;
  }
}