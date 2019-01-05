
/**
Solution: Kruskal/Prim algorithm + UnionFind to connect and detect cycle.
How to Arrive:
* The question is giving us all connections/edges. Asking us to find the Min Cost Edges connect to all cities. MST.
* Basically asking us to construct a MST, then record all MST's edges.
* To use Kruskal/Prim's algorithm, we need to find the Min costs.
	 **Kruskal algorithm**: Always find the next minCost edge on graph, not in MST set.
		* As long as the vertex is not in mstSet yet, we find the minCost edge, and connect the vertex of that edge.
		* Until all vertices connected.
	**Prim's algorithm**: 
		* Find the 1st minCost edge, add their vertices to mstSet.
		* Then find next minCost edge connected to vertices in mstSet. If vertex of edge not in mstSet already. Not cycle.
		* Until all vertices connected (in mstSet).
* Kruskal algorithm is simplier to use with UnionFind.
* We just need to sort the Edges/Connections by cost. That way we know all the next minCost edge just by looping ASC.
* Then to detect if vertex is already in graph/mstSet, just use UnionFind for that. Skip if Edge creates cycle.
* Loop through all connections, connect Vertices using UnionFind, add nonCycle connections to ResList.
* **Key**: assign a key index for each cityStr, so we can use Integers in UnionFind.
* Time: O(E log(E)); sort edges T(e log e) + init map T(e) + UnionFind amortised T(e) collapsing find + unionByRank;
* Space: O(V); map is all vertices, UnionFind is rootes/sizes of all vertices.
*/

/**
 * Definition for a Connection.
 * public class Connection {
 *   public String city1, city2;
 *   public int cost;
 *   public Connection(String city1, String city2, int cost) {
 *       this.city1 = city1;
 *       this.city2 = city2;
 *       this.cost = cost;
 *   }
 * }
 */
public class MinimumSpanningTree {

  /**
   * @param connections given a list of connections include two cities and cost
   * @return a list of connections from results
   * Solution: Kruskal/Prim algorithm + UnionFind to connect and detect cycle.
   * Time: O(E log(E)); sort edges T(e log e) + init map T(e) + UnionFind amortised T(e) collapsing find + unionByRank;
   * Space: O(V); map is all vertices, UnionFind is rootes/sizes of all vertices.
   */
  public List<Connection> lowestCost(List<Connection> connections) {
    List<Connection> res = new ArrayList<>();
    // Sort the edges by cost.
    connections.sort(
      (a, b) -> {
        // compare connection by cost.
        if (a.cost != b.cost) {
          return a.cost - b.cost;
        } else if (!a.city1.equals(b.city1)) {
          // cost ==, compare city1 str. Use string equals to compare.
          return a.city1.compareTo(b.city1);
        } else {
          // cost, city1 ==, compare city2 str.
          return a.city2.compareTo(b.city2);
        }
      }
    );
    // Map cityStr with a index, use in UnionFind.
    // count the vertices;
    int cityIndex = 0;
    Map<String, Integer> cityMap = new HashMap<>();
    // init the map, cities with a index.
    for (int i = 0; i < connections.size(); i++) {
      // check city1 is in map.
      if (!cityMap.containsKey(connections.get(i).city1)) {
        cityMap.put(connections.get(i).city1, cityIndex);
        cityIndex++;
      }
      // check city2 in map.
      if (!cityMap.containsKey(connections.get(i).city2)) {
        cityMap.put(connections.get(i).city2, cityIndex);
        cityIndex++;
      }
    }
    // init UnionFind with n total cities.
    UnionFind uf = new UnionFind(cityIndex);
    // connect the uf set into graph of MST.
    for (int i = 0; i < connections.size(); i++) {
      int city1 = cityMap.get(connections.get(i).city1);
      int city2 = cityMap.get(connections.get(i).city2);
      // detect cycle, if union successful.
      if (uf.union(city1, city2)) {
        // union success, add edge/connection to reslist.
        res.add(connections.get(i));
      }
    }
    // check if # of vertices-1 == res.size, if not graph is disconnected.
    return (cityIndex - 1 == res.size()) ? res : new ArrayList<>();
  }
  
  class UnionFind {
    // root of each node.
    int[] roots;
    // size of the connected graph/tree
    int[] sizes;
    
    UnionFind(int n) {
      // n is number of nodes
      roots = new int[n];
      sizes = new int[n];
      // init nodes' root as itself.
      for (int i = 0; i < n; i++) {
        // init node indexes
        roots[i] = i;
      }
      // init size of each node as 1.
      Arrays.fill(sizes, 1);
    }
    
    // collapsing find.
    public int findRoot(int node) {
      // find node with itself as root.
      if (node == roots[node]) {
        return node;
      }
      // connected to topNode as parent. p1 = p2 = p3 = p4(top);
      roots[node] = findRoot(roots[node]);
      return roots[node];
    }
    
    // union by rank/size. Return if successful union, false if cycle.
    public boolean union(int node1, int node2) {
      int root1 = findRoot(node1);
      int root2 = findRoot(node2);
      // check cycle, don't connect, in same set.
      if (root1 == root2) {
        return false;
      }
      // compare size, union.
      if (sizes[root2] > sizes[root1]) {
        // root2 higher rank, connect root1->root2.
        roots[root1] = root2;
        sizes[root2] += sizes[root1];
      } else {
        // root1 higher rank, connect root2->root1.
        roots[root2] = root1;
        sizes[root1] += sizes[root2];
      }
      return true;
    }
  }
    
    
}