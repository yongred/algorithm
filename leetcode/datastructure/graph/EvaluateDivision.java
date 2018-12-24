/**
LeetCode 399. Evaluate Division
Problem:

Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.

Example:
Given a / b = 2.0, b / c = 3.0.
queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
return [6.0, 0.5, -1.0, 1.0, -1.0 ].

The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries , where equations.size() == values.size(), and the values are positive. This represents the equations. Return vector<double>.

According to the example above:

equations = [ ["a", "b"], ["b", "c"] ],
values = [2.0, 3.0],
queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is no contradiction.
*/

/**
Solution: Construct a grah with edge path val by the equations. Then DFS queries.
Ex:
a / b = 2.0;  =>  a->(2)b, b->(0.5)a;

How to arrive:
* The relationship of the letters division can be expressed as graph. Like above example.
* Use a Map to construct the graph, String letter node map to Another map of node and edge val;
Ex: map letter -> (letter : val); ex: a-> (b : 2.0); b-> (a : 0.5);
* Construct the graph with edge vals between node directions.
```
// connect letter1 -> (letter2 : val);
connectPair(graph, pair[0], pair[1], values[i]);
// connect letter2 -> (letter1 : 1/val); Ex: a/b=3; so a = (1/3)b;
connectPair(graph, pair[1], pair[0], 1.0 / values[i]);
```
* Now for each query, we DFS of node1 to node2, will multiply the val of directed pointers.
Ex: a->(2)b; b->(3)c;  a to c: 2 * 3 = 6;  
Opposite: c->(0.333..)b -> (0.5)a; 1/3 * 1/
* In DFS:
  * We need visitedSet for traversed edges. Represented in direction edges string
  * Ex: n1 to n2 is "n1:n2"; visited.add("n1:n2"); b/c n1:n2 diff from n2:n1;
  * If n1 or n2 not exist in graph, return -1;
  * if n1 to n1 then return 1.0;
  * Traverse all neighbors of n1 if leads to n2, Get an multiplied ans from neighbor path if found n2;
  * cur edge val times ans from neighbor path.
  * return the multiplied ans.
Time: O(n + q) q is queries.
Space: O(n)*/

class EvaluateDivision {

  /**
  Solution: Construct a grah with edge path val by the equations. Then DFS queries.
  Time: O(n + q) q is queries.
  Space: O(n)
  */
  public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
    // map letter -> (letter : val); ex: a-> (b : 2.0); b-> (a : 0.5);
    Map<String, Map<String, Double>> graph = new HashMap<>();
    // init the graph.
    for (int i = 0; i < equations.length; i++) {
      String[] pair = equations[i];
      // connect letter1 -> (letter2 : val);
      connectPair(graph, pair[0], pair[1], values[i]);
      // connect letter2 -> (letter1 : 1/val); Ex: a/b=3; so a = (1/3)b;
      connectPair(graph, pair[1], pair[0], 1.0 / values[i]);
    }
    // res double array.
    double[] res = new double[queries.length];
    // declare visited set for dfs
    Set<String> visited = new HashSet<>();
    // DFS
    for (int i = 0; i < queries.length; i++) {
      // get the query pair. l1 l2.
      String[] pair = queries[i];
      // dfs calc the multiplications. 
      // Ex: a->b->c; a = 2b; b = 3c; a= 2(3c) = 6c;
      double ans = dfs(graph, visited, pair[0], pair[1]);
      // record the ans.
      res[i] = ans;
      // clear the visited nodes for next pair.
      visited.clear();
    }
    return res;
  }
  
  public void connectPair(Map<String, Map<String, Double>> graph, String letter1,
      String letter2, double val) {
    // check have neighbors or not.
    if (!graph.containsKey(letter1)) {
      graph.put(letter1, new HashMap<>());
    }
    // connect letter1 -> (letters : val);
    graph.get(letter1).put(letter2, val);
  }
  
  public double dfs(Map<String, Map<String, Double>> graph, Set<String> visited,
      String letter1, String letter2) {
    // we need to know if the edge dir is traversed, not the node.
    String dirKey = letter1 + ":" + letter2;
    // cur edge visited. We did not find l2.
    if (visited.contains(dirKey)) {
      return -1.0;
    }
    // check if node is not in graph
    if (!graph.containsKey(letter1) || !graph.containsKey(letter2)) {
      return -1.0;
    }
    // check if node -> itself.
    if (letter1.equals(letter2)) {
      // a/a = 1.0;
      return 1.0;
    }
    // visit
    visited.add(dirKey);
    // neighbors of l1
    Map<String, Double> neighbors = graph.get(letter1);
    // DFS each neighbor to find l2.
    for (String neighbor : neighbors.keySet()) {
      // calc from neighbor->l2;
      double ans = dfs(graph, visited, neighbor, letter2);
      // check if neighbor route connect to l2.
      if (Double.compare(ans, -1.0) != 0) {
        // not -1.0, we have the connection.
        double l1ToNodeVal = neighbors.get(neighbor);
        return l1ToNodeVal * ans;
      }
    }
    return -1.0;
  }
}