
/**
Solution: BFS with visited array to track.
How to Arrive:
* Go from (0,0) to find 9 on the grid. Just return if reachable boolean.
* Just a standard BFS.
* Time: O(n * m); Only search 1 path in n * m grid. Traverse once.
* Space: O(n * m);
*/

public class CanReachTheEndpoint {
  
  /**
   * @param map: the map
   * @return: can you reach the endpoint
   * Solution: BFS with visited array to track.
   * Time: O(n * m); Only search 1 path in n * m grid. Traverse once.
	 * Space: O(n * m);
   */
  public boolean reachEndpoint(int[][] map) {
    if (map.length == 0) {
      return false;
    }
    // BFS for 9.
    // from (0,0) pos, start BFS.
    boolean reached = bfs(map, 0, 0);
    return reached;
  }
  
  // up down left right.
  int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
  
  class Point {
    int x;
    int y;
    
    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
  
  public boolean bfs(int[][] map, int x, int y) {
    int rows = map.length;
    int cols = map[0].length;
    // visited array
    boolean[][] visited = new boolean[rows][cols];
    // queue
    Deque<Point> queue = new ArrayDeque<>();
    queue.addLast(new Point(x, y));
    visited[x][y] = true;
    // dist, question not asked, but if asked.
    int dist = 0;
    // level traverse
    while (!queue.isEmpty()) {
      dist++;
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        Point point = queue.pollFirst();
        // go 4 dirs
        for (int d = 0; d < 4; d++) {
          int newX = point.x + dirs[d][0];
          int newY = point.y + dirs[d][1];
          // check boundary and visted.
          if (newX >= 0 && newX < rows && newY >= 0 && newY < cols
              && !visited[newX][newY]) {
            // check is space 1.
            if (map[newX][newY] == 1) {
              // space we can traverse.
              queue.addLast(new Point(newX, newY));
            } else if (map[newX][newY] == 9) {
              // found target, we can stop.
              return true;
            }
            visited[newX][newY] = true;
            // if 0 block, do nothing.
          }
        }
      }
    }
    // not found 9.
    return false;
  }
  
  
  
  
}