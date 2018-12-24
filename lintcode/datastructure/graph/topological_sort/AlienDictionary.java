/**
892. Alien Dictionary
There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you. You receive a list of non-empty words from the dictionary, where words are sorted lexicographically by the rules of this new language. Derive the order of letters in this language.

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
There may be multiple valid order of letters, return the smallest in lexicographical order
*/

/**
**Problem**: Not able to sort unrelated chars in lexicographical order. Ex: c > d; e > b;    "cdeb" or "ebcd";
Solution 1: Topological sort, BFS.
Ex:   ["b", "ad", "aa", "c"]
 b
     \
d --> a --> c
order: "bdac";
indegree: b0, d0, a2 - 1(b) - 1(c) = a0, c1 - 1(a) = c0; 
BFS: +b & +d = "bd"; +a = "bda"; +c = "bdac";

How to arrive:
* Understand the question: We are given strs, need to compare the Chars in strs to see the Orders, Like: charA > charD > charB ...;
* So, going through words, compare w1 chars with w2 chars. w1[i] char != w2[i] char, then w1char is larger.
* We construct a graph of Characters using this comparison. For BFS, we want the largest to have 0 in-degree. So D > B, then D->B;
* After construct the Graph, we just do BFS using a Queue. Put in the 0 in-degree nodes (largest). 
* poll curLevel nodes. Then get their neighbors, the neighbors (smaller letters) now have 1 less in-degree.
* If neighbor node in-degree becomes 0, then it has no more dependence. It is the next largest.
* Append the chars level by level.
* If Not all nodes traversed, (nodes that cannot have 0 in-degrees), then there is a cycle. Return "";
* Else return the appended String.
* 
* Time: O(n); V+E, n is number of chars in words.
* Space: O(n); num of chars in words.

----------------------

Solution 2: Topological sort, DFS.
Ex: ["b", "ad", "aa", "c"]
c --> a --> b
               \
                  d
order: "bdac";
visited: b, d, a, c;
DFS: loop: a b c d;
a -> b, b has no child, add to 'b' visited; a -> d, 'd' has no child, add 'd' to visited;  'a' has no more child, add to visited.
'b' is visited return;
'c' -> a, a is visited, c has no more child, add 'c' to visited.
'd' is visited.

How to Arrive:
* Same as Topological BFS, we construct the graph. **But in opposite order**
* Because DFS, the deepest level is traversed first, so we want the largest to be the deepest level.
* Use visiting Set to represent current dfs path from curNode.
* If visitingSet has curNode, meaning path is circle back (cycle) return "";
* Use visited Set to represent already traversed node.
* 
* Time: O(n); V+E, n is number of chars in words.
* Space: O(n); num of chars in words.
*/

public class AlienDictionary {

  /**
   * @param words: a list of words
   * @return: a string which is correct order
   * Solution 2: Topological sort, DFS.
   * Time: O(n); V+E, n is number of chars in words.
   * Space: O(n); num of chars in words.
   */
  public String alienOrder(String[] words) {
    // build a graph of chars in words as node.
    Map<Character, Set<Character>> graph = new HashMap<>();
    buildGraph(graph, words);
    // prepare dfs
    // store cur dfs path node, to detect cycle. Ex: a > b, b > c, c > a;
    Set<Character> visiting = new HashSet<>();
    // store finish all children and self traverse nodes.
    Set<Character> visited = new HashSet<>();
    // StringBuilder for cur path.
    StringBuilder curOrder = new StringBuilder();
    // DFS Traverse all nodes
    for (Character node : graph.keySet()) {
      boolean cycle = dfs(graph, visiting, visited, curOrder, node);
      // cycle detected, not valid order.
      if (cycle) {
        return "";
      }
    }
    return curOrder.toString();
  }
  
  public boolean dfs(Map<Character, Set<Character>> graph, Set<Character> visiting,
      Set<Character> visited, StringBuilder curOrder, char node) {
    // check if cycle, true for has cycle.
    if (visiting.contains(node)) {
      return true;
    }
    // check if visited, curNode finished all branch traverse.
    if (visited.contains(node)) {
      // false not has cycle.
      return false;
    }
    // add node to cur path.
    visiting.add(node);
    // dfs all neighbors
    Set<Character> neighbors = graph.get(node);
    for (char neighbor : neighbors) {
      boolean cycle = dfs(graph, visiting, visited, curOrder, neighbor);
      if (cycle) {
        // cycle dectect.
        return true;
      }
    }
    // no cycle detected, finished traverse node's branches
    visiting.remove(node);
    visited.add(node);
    // add node to curOrder path.
    curOrder.append(node);
    return false;
  }
  
  public void buildGraph(Map<Character, Set<Character>> graph, String[] words) {
    // init hashset for adding node neighbors
    for (String word : words) {
      // every unique char in words arr is a node.
      for (char chr : word.toCharArray()) {
        if (!graph.containsKey(chr)) {
          // empty hashset as neighbor nodes.
          graph.put(chr, new HashSet<>());
        }
      }
    }
    // loop words and compare chars
    for (int i = 0; i < words.length - 1; i++) {
      String word1 = words[i];
      String word2 = words[i + 1];
      // compare chars of 2 words
      int len = (word1.length() < word2.length()) ? word1.length()
          : word2.length();
      for (int j = 0; j < len; j++) {
        char parent = word1.charAt(j);
        char child = word2.charAt(j);
        // if diff chars, declare relationship
        if (parent != child) {
          // child -> parent, dfs deepest level traverse first.
          // in this case largest on the bottom.
          graph.get(child).add(parent);
          // need to break after 1st non-match, 
          // only highest level non-match char are compared.
          break;
        }
      }
    }
  }

  /**
   * @param words: a list of words
   * @return: a string which is correct order
   * Solution 1: Topological sort, BFS.
   * Time: O(n); V+E, n is number of chars in words.
   * Space: O(n); num of chars in words.
   */
  public String alienOrder(String[] words) {
    // graph, char-> charChildren;
    Map<Character, Set<Character>> graph = new HashMap<>();
    int[] indegrees = new int[26];
    // build the graph for char order.
    buildGraph(words, graph, indegrees);
    // make graph into string in order.
    String res = bfs(graph, indegrees);
    return res;
  }
  
  public void buildGraph(String[] words, Map<Character, Set<Character>> graph,
      int[] indegrees) {
    // every char in words have a set
    for (String word : words) {
      for (char chr : word.toCharArray()) {
        if (!graph.containsKey(chr)) {
          graph.put(chr, new HashSet<Character>());
        }
      }
    }
    // loop through words
    for (int i = 0; i + 1 < words.length; i++) {
      // compare cur to next
      String word1 = words[i];
      String word2 = words[i + 1];
      int len = Math.min(word1.length(), word2.length());
      // compare chars in 2 words. Find diff chars, make graph edge.
      for (int j = 0; j < len; j++) {
        char parent = word1.charAt(j);
        char child = word2.charAt(j);
        // diff char found
        if (parent != child) {
          // connect in graph.
          // check if parent has child
          if (!graph.get(parent).contains(child)) {
            // parent connect to child
            graph.get(parent).add(child);
            // count the parents pointing to child
            indegrees[child - 'a']++;
          }
          break;
        }
      }
    }
    
  }
  
  public String bfs(Map<Character, Set<Character>> graph,
      int[] indegrees) {
    StringBuilder res = new StringBuilder();
    Deque<Character> queue = new ArrayDeque<>();
    // loop all chars in words.
    for (char chr : graph.keySet()) {
      // find the node with no parents
      if (indegrees[chr - 'a'] == 0) {
        queue.addLast(chr);
      }
    }
    // bfs
    while (!queue.isEmpty()) {
      char curChar = queue.pollFirst();
      Set<Character> children = graph.get(curChar);
      // curChar in the cur order pos
      res.append(curChar);
      for (char child : children) {
        // one less parent for child
        indegrees[child - 'a']--;
        // no more parent.
        if (indegrees[child - 'a'] == 0) {
          // it is up next in order.
          queue.addLast(child);
        }
      }
    }
    return res.toString();
  }
  
}