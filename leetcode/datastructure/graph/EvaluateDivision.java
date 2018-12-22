

class EvaluateDivision {

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