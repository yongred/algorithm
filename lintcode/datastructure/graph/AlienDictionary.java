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

public class AlienDictionary {
  /**
   * @param words: a list of words
   * @return: a string which is correct order
   */
  public String alienOrder(String[] words) {
    // graph, char-> charChildren;
    Map<Character, Set<Character>> graph = new HashMap<>();
    int[] parentsCount = new int[26];
    // build the graph for char order.
    buildGraph(words, graph, parentsCount);
    // make graph into string in order.
    String res = topologicalSort(graph, parentsCount);
    return res;
  }
  
  public void buildGraph(String[] words, Map<Character, Set<Character>> graph,
      int[] parentsCount) {
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
            parentsCount[child - 'a']++;
          }
          break;
        }
      }
    }
    
  }
  
  public String topologicalSort(Map<Character, Set<Character>> graph,
      int[] parentsCount) {
    StringBuilder res = new StringBuilder();
    Deque<Character> queue = new ArrayDeque<>();
    // loop all chars in words.
    for (char chr : graph.keySet()) {
      // find the node with no parents
      if (parentsCount[chr - 'a'] == 0) {
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
        parentsCount[child - 'a']--;
        // no more parent.
        if (parentsCount[child - 'a'] == 0) {
          // it is up next in order.
          queue.addLast(child);
        }
      }
    }
    return res.toString();
  }
  
}