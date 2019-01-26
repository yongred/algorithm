/**
941. Sliding Puzzle
On a 2x3 board, there are 5 tiles represented by the integers 1 through 5, and an empty square represented by 0.

A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.

The state of the board is solved if and only if the board is [[1,2,3],[4,5,0]].

Given a puzzle board, return the least number of moves required so that the state of the board is solved. If it is impossible for the state of the board to be solved, return -1.

Example
Given board = [[1,2,3],[4,0,5]], return 1.

Explanation: 
Swap the 0 and the 5 in one move.
Given board = [[1,2,3],[5,4,0]], return -1.

Explanation: 
No number of moves will make the board solved.
Given board = [[4,1,2],[5,0,3]], return 5.

Explanation: 
5 is the smallest number of moves that solves the board.
An example path:
After move 0: [[4,1,2],[5,0,3]]
After move 1: [[4,1,2],[0,5,3]]
After move 2: [[0,1,2],[4,5,3]]
After move 3: [[1,0,2],[4,5,3]]
After move 4: [[1,2,0],[4,5,3]]
After move 5: [[1,2,3],[4,5,0]]
Given board = [[3,2,4],[1,5,0]], return 14.

Notice
board will be a 2 x 3 array as described above.
board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5].
*/

/**
 Solution: BFS, serialize matrix to String.
How to arrive:
* We can traverse every possible moves from init 0 pos board, then do the same with all generated new boards derive from prev board moved.
* When there is a board with 123,450 we find the target.
* Count the moves as we search.
* 2d Array is hard to compare in java. So we will serialize the boards into String.
* Loop rows and cols and append [row][col] to StringBuilder.
* To reverse, or deserialize (Optional) String into 2d array Use rowIdx = idx / numOfCols; And col = idx % numOfCols.
* Use the above formula: 
```
// ex: idx 5 / 3 = 1 idx of 2nd row.
int row = i / cols;
// ex: idx 5 % 3 = 2 idx of 3rd col.
int col = i % cols;
arrBoard[row][col] = board.charAt(i) - '0';
```
* Algorithm:
 * use serialize method to get the initial board String.
 * declare a target string "123450"
 * if init == target then move is 0; return 0;
 * Declare a direction array: {x, y}, {-1, 0} is move up, {1,0} move down, {0,1} move rigt, {0,-1} move left.
 * Declare a queue for storing boards, for BFS.
 * Add init board string to it.
 * Declare a Set for visited boards so we don't go back to same pos.
 * Add init to visited.
 * declare Size var to save the cur queueSize. We need it to know how many level we have traversed. (each level is 1 move);
 * declare moves var init = 1; keep track of moves.
 * While queue is not empty, BFS.
   * queue poll cur board.
   * size decrement.
   * find out where the zero is on curBoard. Return a pos [x, y];
   * Move the board in availiable directions: test 4 dir.
     * Find new pos [x1,y1] using dir array.
     * make sure newPos is within the grid.
     * find the newBoard String by swap curPos val with newPos val.
       * String board index = row * numOfCols + col;
    * if newBoard = target: return moves;
    * else if newBoard is not visited:
      * queue add newBoard,
      * visited add newBoard
    * if cur Level is finished (size==0), meaning all possible moves from cur 0 pos is checked:
      * increment moves; we make 1 more move.
      * size = curQueueSize; The new 0 pos possible moves.
* if not found, return -1;
* Time: O(n!) 6! 6 grids;
* Space: O(n!) 6! 6 grids;
*/

class SlidingPuzzle {
  
  /**
   * Solution: BFS, serialize board to string.
   * Time: O(n!)
   * Space: O(n!)
   */
  public int slidingPuzzle(int[][] board) {
    int rows = board.length;
    int cols = board[0].length;
    int minSteps = 0;
    String init = serialize(board, rows, cols);
    String target = "123450";
    if (init == target) {
      return 0;
    }
    Set<String> visited = new HashSet<>();
    Deque<String> queue = new ArrayDeque<>();
    queue.addLast(init);
    visited.add(init);
    // BFS
    while (!queue.isEmpty()) {
      // size of cur level.
      int size = queue.size();
      // step+1 at every level.
      minSteps++;
      for (int i = 0; i < size; i++) {
        String curBoard = queue.pollFirst();
        Point zeroPos = findZero(curBoard, rows, cols);
        // check 4 dirs
        for (int d = 0; d < 4; d++) {
          int x = zeroPos.x + dirs[d][0];
          int y = zeroPos.y + dirs[d][1];
          // check boundaries
          if (x >= 0 && x < rows && y >= 0 && y < cols) {
            // generate new board;
            String movedBoard = move(curBoard, cols, zeroPos.x, zeroPos.y, x, y);
            // check if found target
            if (movedBoard.equals(target)) {
              return minSteps;
            }
            // check if visited
            if (!visited.contains(movedBoard)) {
              queue.addLast(movedBoard);
              visited.add(movedBoard);
            }
          }
        }
      }
    }
    // not found.
    return -1;
  }
  
  class Point {
    int x;
    int y;
    
    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
  
  int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
  
  public String move(String board, int cols, int zeroRow, int zeroCol,
      int newRow, int newCol) {
    // swap 0 pos with newPos.
    // Ex: idx4 = r1 * 3 + 1 = 4;
    int zeroIndex = (zeroRow * cols) + zeroCol;
    int newIndex = (newRow * cols) + newCol;
    // swap
    char[] arr = board.toCharArray();
    char temp = arr[zeroIndex];
    arr[zeroIndex] = arr[newIndex];
    arr[newIndex] = temp;
    
    return new String(arr);
  }
  
  public Point findZero(String board, int rows, int cols) {
    Point zeroPoint = null;
    for (int i = 0; i < board.length(); i++) {
      if (board.charAt(i) == '0') {
        int r = i / cols;
        int c = i % cols;
        zeroPoint = new Point(r, c);
        break;
      }
    }
    return zeroPoint;
  }
  
  public String serialize(int[][] board, int rows, int cols) {
    StringBuilder sb = new StringBuilder();
    // add ints row by row;
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        sb.append(board[r][c]);
      }
    }
    return sb.toString();
  }


  public int[][] deserialize(String boardStr, int rows, int cols) {
    int[][] board = new int[rows][cols];
    for (int i = 0; i < boardStr.length(); i++) {
      // 0th row indexs 0->2 / 3 = 0; 1st row indexes 3->5 / 3 = 1;
      int r = i / cols;
      // 3 cols, 0->2; 0 % 3 = 0 (0/3=0, 0-0=0); 1 % 3 = 1 (1/3=0, 1-0=1); 2 % 3 = 2;
      // 3 % 3 = 0; 4 % 3 = 1; 5 % 3 = 2; done.
      int c = i % cols;
      board[r][c] = Integer.valueOf(boardStr.charAt(i) + "");
    }
    return board;
  }
}