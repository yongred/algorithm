/**
434. Number of Islands II
Given a n,m which means the row and column of the 2D matrix and an array of pair A( size k). Originally, the 2D matrix is all 0 which means there is only sea in the matrix. The list pair has k operator and each operator has two integer A[i].x, A[i].y means that you can change the grid matrix[A[i].x][A[i].y] from sea to island. Return how many island are there in the matrix after each operator.

Example
Given n = 3, m = 3, array of pair A = [(0,0),(0,1),(2,2),(2,1)].

return [1,1,2,2].

Notice
0 is represented as the sea, 1 is represented as the island. If two 1 is adjacent, we consider them in the same island. We only consider up/down/left/right adjacent.
*/

/**
Solution: Construct UnionFind graph, keep a count of cur islands. Check 4 dirs for union.
How to Arrive:
* To find how many islands there are, we can assign 1 grid as the Owner of the island, all other grids in this island have a ownerPointer pointing to this grid.
* Just like Parent node in UnionFind graph. We keep a Owners array to store each island's owner.
* When new Land (1) created, we check 4 dirs up (-1,0), down (1,0), left (0,-1), right (0,1) for adj lands (1);
* When encounter island in each adj dir grid, we connect the 2 islands together. (Union)
	* We use Collapsing Find to find the Owners. (all grids assign to same owner in this island);
	* We do a Union by Rank/Size. Compare islands' sizes, the larger size becomes the new Owner of connected island.
	* Skip if 2 grids' owner are the same. That means they already on same island.
* The question is asking us to Add the cur number of islands to a resList each time a grid operation is performed.
	* So we need to keep a Count for the islands so far.
	* When there is a new Land filled, we ++islandsCount;
	* Then we check to connect/Union adj islands. Every successful Union we --islandCount; (island of same owner Skip);
	* **Make sure to check if Land is already filled**; there might be duplicated Point opes. Ex: (2,2) (2,2); twice.
		* in that case, we add the same islandCount to resList. Then skip to next Point ope.
* Time: O(n * m + opes);  n * m to construct the graph, + opes to fill the lands and connect the islands.
* Space: O(n * m);
*/

/**
 * Definition for a point.
 * class Point {
 *     int x;
 *     int y;
 *     Point() { x = 0; y = 0; }
 *     Point(int a, int b) { x = a; y = b; }
 * }
 */

public class NumberOfIslandsII {

  /**
   * @param n: An integer
   * @param m: An integer
   * @param operators: an array of point
   * @return: an integer array
   * Solution: construct UnionFind graph, keep a count of cur islands.
   * Time: O(n * m + opes);  n * m to construct the graph, + opes to fill the lands and connect the islands.
   * Space: O(n * m);
   */
  public List<Integer> numIslands2(int n, int m, Point[] operators) {
    List<Integer> res = new ArrayList<>();
    if (operators == null || operators.length == 0) {
      return res;
    }
    // create the graph matrix.
    init(n, m);
    // dir array, up down left right.
    int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    // loop opes. check dirs.
    for (int i = 0; i < operators.length; i++) {
      Point pos = operators[i];
      // check if already is Land, if not turn pos into land, else skip
      if (graph[pos.x][pos.y] == 1) {
        // add the same count.
        res.add(islandsCount);
        continue;
      }
      // turn pos into land
      graph[pos.x][pos.y] = 1;
      // owner of itself.
      owners[pos.x][pos.y] = new Point(pos.x, pos.y);
      // update size of island.
      sizes[pos.x][pos.y] = 1;
      // 1 more island.
      islandsCount++;
      // check 4 dirs for island.
      for (int j = 0; j < 4; j++) {
        int x = pos.x + dir[j][0];
        int y = pos.y + dir[j][1];
        // check boundary.
        if (x >= 0 && x < n && y >= 0 && y < m) {
          // if is adj island.
          if (graph[x][y] == 1) {
            // Union them. 
            union(pos.x, pos.y, x, y);
          }
        }
      }
      // store cur number of islands.
      res.add(islandsCount);
    }
    return res;
  }
  
  // graph matrix, land 1 or sea 0;
  int[][] graph;
  // owners of the island
  Point[][] owners;
  // sizes of each island.
  int[][] sizes;
  // keep a count for current islands.
  int islandsCount = 0;
  // initialize 
  public void init(int rows, int cols) {
    graph = new int[rows][cols];
    owners = new Point[rows][cols];
    sizes = new int[rows][cols];
    // owners init to itself
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        // seas owner -1.
        owners[r][c] = new Point(-1, -1);
      }
    }
  }

  // Find top owner. Collapsing find.
  public Point findOwner(int x, int y) {
    // check seas.
    if (owners[x][y].x == -1 || owners[x][y].y == -1) {
      return owners[x][y];
    }
    // found owner, when itself is the owner. Top level node.
    if (owners[x][y].x == x && owners[x][y].y == y) {
      return owners[x][y];
    }
    // collapsing find
    owners[x][y] = findOwner(owners[x][y].x, owners[x][y].y);
    return owners[x][y];
  }

  // Union by rank
  public void union(int x1, int y1, int x2, int y2) {
    Point owner1 = findOwner(x1, y1);
    Point owner2 = findOwner(x2, y2);
    // check if on same island.
    if (owner1.x == owner2.x && owner1.y == owner2.y) {
      return;
    }
    // compare size, larger size island is the owner.
    if (sizes[owner2.x][owner2.y] > sizes[owner1.x][owner1.y]) {
      // owner1's new owner is owner2
      owners[owner1.x][owner1.y] = new Point(owner2.x, owner2.y);
      // update new size of owner2
      sizes[owner2.x][owner2.y] += sizes[owner1.x][owner1.y];
    } else {
      // owner1 size >= owner2 size.
      owners[owner2.x][owner2.y] = new Point(owner1.x, owner1.y);
      // update size of owner1
      sizes[owner1.x][owner1.y] += sizes[owner2.x][owner2.y];
    }
    // after union. 1 less island.
    islandsCount--;
  }
  
  
}