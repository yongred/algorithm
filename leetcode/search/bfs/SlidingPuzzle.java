/**
773. Sliding Puzzle
On a 2x3 board, there are 5 tiles represented by the integers 1 through 5, and an empty square represented by 0.

A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.

The state of the board is solved if and only if the board is [[1,2,3],[4,5,0]].

Given a puzzle board, return the least number of moves required so that the state of the board is solved. If it is impossible for the state of the board to be solved, return -1.

Examples:

Input: board = [[1,2,3],[4,0,5]]
Output: 1
Explanation: Swap the 0 and the 5 in one move.
Input: board = [[1,2,3],[5,4,0]]
Output: -1
Explanation: No number of moves will make the board solved.
Input: board = [[4,1,2],[5,0,3]]
Output: 5
Explanation: 5 is the smallest number of moves that solves the board.
An example path:
After move 0: [[4,1,2],[5,0,3]]
After move 1: [[4,1,2],[0,5,3]]
After move 2: [[0,1,2],[4,5,3]]
After move 3: [[1,0,2],[4,5,3]]
After move 4: [[1,2,0],[4,5,3]]
After move 5: [[1,2,3],[4,5,0]]
Input: board = [[3,2,4],[1,5,0]]
Output: 14
Note:

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
* Time: O(n!);
* Space: O(n!);
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
    // init board string
    String init = serialize(board);
    // final target board string
    String target = "123450";
    if (init.equals(target)) {
      return 0;
    }
    // dir, up down left right
    int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    // queue for boards possibility
    Deque<String> queue = new ArrayDeque<>();
    queue.addLast(init);
    // set for already visited board.
    Set<String> visited = new HashSet<>();
    visited.add(init);
    // use queue size to keep track of moved
    int size = queue.size();
    // keep track of the moves
    int moves = 1;
    // while queue not empty bfs.
    while (!queue.isEmpty()) {
      String curBoard = queue.pollFirst();
      size--;
      // find cur 0 pos
      int[] pos = findZero(curBoard, cols);
      int row = pos[0];
      int col = pos[1];
      // 4 dir moves
      for (int i = 0; i < 4; i++) {
        // moved pos row and col.
        int newRow = row + dir[i][0];
        int newCol = col + dir[i][1];
        if (newRow < rows && newRow >= 0
            && newCol < cols && newCol >= 0) {
          // make the swap.
          String newBoard = swap(curBoard, cols, row, col, newRow, newCol);
          if (newBoard.equals(target)) {
            return moves;
          } else if (!visited.contains(newBoard)) {
            // not visited, we go check it out.
            queue.addLast(newBoard);
            visited.add(newBoard);
          }
        }
      }
      // finished cur possible moves
      if (size == 0) {
        moves++;
        size = queue.size();
      }
    }
    // cannot reach.
    return -1;
  }
  
  public String swap(String board, int cols, int row1, int col1,
      int row2, int col2) {
    StringBuilder sb = new StringBuilder(board);
    // from row col to string index.
    int index1 = row1 * cols + col1;
    int index2 = row2 * cols + col2;
    char char1 = board.charAt(index1);
    char char2 = board.charAt(index2);
    sb.setCharAt(index1, char2);
    sb.setCharAt(index2, char1);
    return sb.toString();
  }
  
  public int[] findZero(String board, int cols) {
    int[] pos = new int[2];
    for (int i = 0; i < board.length(); i++) {
      if (board.charAt(i) == '0') {
        int row = i / cols;
        int col = i % cols;
        pos[0] = row;
        pos[1] = col;
        break;
      }
    }
    return pos;
  }
  
  public String serialize(int[][] board) {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        sb.append(board[row][col] + "");
      }
    }
    return sb.toString();
  }
  
  public int[][] deserialize(String board, int rows, int cols) {
    int[][] arrBoard = new int[rows][cols];
    // form the board
    for (int i = 0; i < board.length(); i++) {
      // ex: idx 5 / 3 = 1 idx of 2nd row.
      int row = i / cols;
      // ex: idx 5 % 3 = 2 idx of 3rd col.
      int col = i % cols;
      arrBoard[row][col] = board.charAt(i) - '0';
    }
    return arrBoard;
  }
}