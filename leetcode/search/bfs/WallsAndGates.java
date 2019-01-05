/**
286. Walls and Gates
You are given a m x n 2D grid initialized with these three possible values.

-1 - A wall or an obstacle.
0 - A gate.
INF - Infinity means an empty room. We use the value 2^31 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
Fill each empty room with the distance to its nearest gate. If it is impossible to reach a ROOM, that room should remain filled with INF.

Example
Given the 2D grid:

INF  -1  0  INF
INF INF INF  -1
INF  -1 INF  -1
  0  -1 INF INF
return the result:

  3  -1   0   1
  2   2   1  -1
  1  -1   2  -1
  0  -1   3   4
*/

/**
Solution: BFS from 0s, update distance for INFs.
How to Arrive:
* Question ask us to find minDist 0 from INF, and updates INF to dist.
* So we could go from 0 to each INF and update the dist to INF.
* We can do BFS from 0, goes 4 dirs up down left right.
* increment the dist in every level, queueSize. Check if curDist is < minDist on INF grid.
* Time: O((n * m)^2);
* Space: O(n * m);
*/


public class WallsAndGates {

  /**
   * @param rooms: m x n 2D grid
   * @return: nothing
   * Solution: BFS from 0s, update distance for INFs.
   * Time: O((n * m)^2);
	 * Space: O(n * m);
   */
  public void wallsAndGates(int[][] rooms) {
    if (rooms.length == 0) {
      return;
    }
    int rows = rooms.length;
    int cols = rooms[0].length;
    // start from gate 0s, update INF grid if fewer steps.
    // from each 0, bfs.
    // visited grid.
    boolean[][] visited = new boolean[rows][cols];
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < cols; y++) {
        // find gates, and traverse through empties.
        if (rooms[x][y] == 0) {
          bfs(rooms, x, y, visited);
        }
      }
    }
  }
  
  // dirs
  int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
  
  class Point {
    int x;
    int y;
    
    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
  
  public void clear(boolean[][] arr) {
    for (boolean[] row : arr) {
      Arrays.fill(row, false);
    }
  }
  
  public void bfs(int[][] rooms, int x, int y, boolean[][] visited) {
    // clear visited from new traversal.
    clear(visited);
    int rows = rooms.length;
    int cols = rooms[0].length;
    // queue
    Deque<Point> queue = new ArrayDeque<>();
    queue.addLast(new Point(x, y));
    visited[x][y] = true;
    // dist count.
    int dist = 0;
    // level traverse
    while (!queue.isEmpty()) {
      dist++;
      int levelSize = queue.size();
      for (int i = 0; i < levelSize; i++) {
        Point point = queue.pollFirst();
        // 4 dirs
        for (int d = 0; d < 4; d++) {
          int newX = point.x + dirs[d][0];
          int newY = point.y + dirs[d][1];
          // check boundaries. And visited.
          if (newX >= 0 && newX < rows && newY >= 0 && newY < cols
              && !visited[newX][newY]) {
            // check is wall or gate or empty.
            if (rooms[newX][newY] != -1 && rooms[newX][newY] != 0) {
              // empty. we update minDist, and traverse it.
              queue.addLast(new Point(newX, newY));
              // check min dists, and update.
              rooms[newX][newY] = Math.min(rooms[newX][newY], dist);
            }
            // want to visited every grid not just empty.
            visited[newX][newY] = true;
          } 
        }
      }
    }
  }
    
    
    
}