/**
803. Shortest Distance from All Buildings
You want to build a house on an empty land which reaches all buildings in the shortest amount of distance. You can only move up, down, left and right. You are given a 2D grid of values 0, 1 or 2, where:

·Each **0** marks an empty land which you can pass by freely.
·Each **1** marks a building which you cannot pass through.
·Each **2** marks an obstacle which you cannot pass through.
Example
For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2):

1 - 0 - 2 - 0 - 1
|   |   |   |   |
0 - 0 - 0 - 0 - 0
|   |   |   |   |
0 - 0 - 1 - 0 - 0
The point (1,2) is an ideal empty land to build a house, as the total travel distance of 3+3+1=7 is minimal. So return 7.

*/

/**
Solution: BFS, keep a record of distSum from 0s to 1s, and # of reachable buildings from 0s;
How to Arrive:
* There are many steps to this problem, but overall it's just a BFS problem.
* Question wants min sum of dists from a 0 grid to all 1s grid.
* We can do bfs from each 0s grid or 1s grid. (use 1s grid)
* We need an array to store distSum of each 0s grid.
* We also need to know if each 0s grid can reach all buildings 1s grid.
* So we have an array to store the total reachable buildings from 0s grid.
* Then we start from each 1s grid and do BFS:
	* We keep a dist var to record the levelTraverse's curLevel. Use it to calc distSum.
	* We also want to keep a count of how many other buildings can be reach from curBuilding. (if not able to reach all buildings, then there is no place to build a house that can reaches all).
	* When reached to an 0 grid, we update 0 grid's distSum. Also increment its reachables count.
	* When reached to other 1s grid, we update/increment the reachableBuildings from curBuilding.
	* Ignore the 2 (blocked grid).
	* Make sure to keep a Visited array, and clear Visited array for each new BFS from a building.
	* Return boolean if reachableBuildings == allBuildings.
* if not reachable to all buildings just return -1;
* After BFS all buildings, all 0s grid distSum are calculated.
* We check the 0s grid if their reachables == allBuildings.
* Then we compare MinSum to their distSum, find the min.

* Time: O((n * m)^2); Traverse each grid once from each building, worse case many buildings.
* Space: O(n * m);
*/

public class ShortestDistanceFromAllBuildings {
  
  // reachable buildings
  int[][] reaches;
  // distance sum from all buildings.
  int[][] distSum;
  // dirs, up down left right
  int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
  
  /**
   * @param grid: the 2D grid
   * @return: the shortest distance
   * Solution: BFS, keep a record of distSum from 0s to 1s, and # of reachable buildings from 0s;
   * Time: O((n * m)^2); Traverse each grid once from each building, worse case many buildings.
   * Space: O(n * m);
   */
  public int shortestDistance(int[][] grid) {
    if (grid.length == 0) {
      return -1;
    }
    int rows = grid.length;
    int cols = grid[0].length;
    reaches = new int[rows][cols];
    distSum = new int[rows][cols];
    // find out buildings number. needed to determine how many reachable.
    int buildings = 0;
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < cols; y++) {
        if (grid[x][y] == 1) {
          buildings++;
        }
      }
    }
    // bfs from each building, fill dist from all 0s.
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < cols; y++) {
        if (grid[x][y] == 1) {
          // search this building's dists. 
          // Check if curBuilding able to reach other buildings.
          boolean connectedBuildings = bfs(grid, x, y, buildings);
          // not able to reach all other buildings.
          if (!connectedBuildings) {
            return -1;
          }
        }
      }
    }
    // calculated all dists, and found all reachable areas.
    // find the minGrid distSum reachable to all buildings.
    int minSum = Integer.MAX_VALUE;
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < cols; y++) {
        if (reaches[x][y] == buildings) {
          minSum = Math.min(minSum, distSum[x][y]);
        }
      }
    }
    return (minSum == Integer.MAX_VALUE) ? -1 : minSum;
  }
  
  class Point {
    int x;
    int y;
    
    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
  
  public boolean bfs(int[][] grid, int x, int y, int buildings) {
    int rows = grid.length;
    int cols = grid[0].length;
    // cleared visited grid before each bfs.
    boolean[][] visited = new boolean[rows][cols];
    // queue of x,y point.
    Deque<Point> queue = new ArrayDeque<>();
    // put in the building grid.
    queue.addLast(new Point(x, y));
    visited[x][y] = true;
    // search for 0s.
    // count dist. level.
    int dist = 0;
    // if reachable to all buildings from cur building. Count curBuilding as 1st.
    int reachables = 1;
    while (!queue.isEmpty()) {
      // update dist after each level finished.
      dist++;
      // level traverse.
      int levelSize = queue.size();
      for (int i = 0; i < levelSize; i++) {
        Point point = queue.pollFirst();
        // get neighbors. 4 dirs.
        for (int d = 0; d < 4; d++) {
          // reachable grid.
          int newX = point.x + dirs[d][0];
          int newY = point.y + dirs[d][1];
          // check boundary. And if visited.
          if (newX >= 0 && newX < rows && newY >= 0 && newY < cols
              && !visited[newX][newY]) {
            // now check block 2 or building 1 or empty 0.
            if (grid[newX][newY] == 0) {
              // empty grid 0, add to queue to traverse.
              queue.addLast(new Point(newX, newY));
              // calc dist and update reaches.
              // Add cur building's dist to this grid.
              distSum[newX][newY] += dist;
              // can reach to cur building.
              reaches[newX][newY]++;
            } else if (grid[newX][newY] == 1) {
              reachables++;
            }
            visited[newX][newY] = true;
          }
        }
      }
    }
    // return if all buildings reachable from cur building.
    // if not then this graph don't have a position.
    return reachables == buildings;
  }
  
  
  
}