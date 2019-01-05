/**
33. N-Queens
The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.

Given an integer n, return all distinct solutions to the n-queens puzzle.

Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space respectively.

Example
There exist two distinct solutions to the 4-queens puzzle:

[
  // Solution 1
  [".Q..",
   "...Q",
   "Q...",
   "..Q."
  ],
  // Solution 2
  ["..Q.",
   "Q...",
   "...Q",
   ".Q.."
  ]
]
Challenge
Can you do it without recursion?
*/

/**
Solution: Back tracking
How to Arrive:
* We can do this row by row or col by col. Same thing. B/c it's n*n;
* We can place a Q on cur place, then going to the next row finding Q. See if it leads to a solution. 
* To check whether Q can be placed, You need to check \, |, /, 3 directions. up left diagonal, up right diagonal, vertical top.
	* We can use a 2D array boolean to place and erase placements.
	* Or we can use a 1D array int, store col indexs for each row.
	  * **Key**: for | just check col1 == col2. For \ ex: (1,1) (3,3) 1-1 == 3-3 col1 - row1 == col2 - row2; For / ex: (2,2) (0,4) 2+2 == 0+4; col1 + row1 == col2 + row2;
* When reach to the end of last row, meaning we have a solution. Add the record cols in array to res.
* If cur Q pos not leading to a solution, 1 row have no valid space, then erase cur Q pos and go to next col on row.
* We are find ALL posible solutions, so whether we get a solution from cur Q pos or not we need to test out all pos.
* Time: O(n!);
* Space: O(n^2);

Runtime analysis:
O(n!);
Number of possible arrangements of N Queens on N x N chessboard is N!, given you are skipping row & column, already having a queen placed. 
Ex: when not valid [Q..., ..Q.] row 2 col 1,2 are skipped. Meaning it won't recurse to row 3 and 4 for those 2 col on row 2.
So average and worst case complexity of the solution is O(N!) (since, you are checking all the possible solutions i.e. 𝑁𝑁arrangements).
We are finding all the possible solutions, the best, average and worst case complexity remains O(N!).

The recurrence is T(n) = n*T(n-1) + N*n, which leads to O((n+1)!). 
When one more recursive call is made, at least one more isValid() should return false. So the number of recursive calls decreases by at least 1 each time. 
And for the overhead in isValid(), since row cols has been filled already, there are at most min(row, N-row+1) iterations of 
for (int preRow = row - 1; preRow >= 0; preRow--). 
But the for loop in search() runs a fixed number of N. That's why N*n exists.
*/

import java.util.*;
import java.io.*;

public class NQueens {
  /*
   * @param n: The number of queens
   * @return: All distinct solutions
   */
  public List<List<String>> solveNQueens(int n) {
    List<List<String>> res = new ArrayList<>();
    if (n <= 0) {
      return res;
    }
    boolean [][] board = new boolean[n][n];
    search(res, board, 0);
    
    return res;
  }
  
  /**
   * Search for ALL posible solutions.
   */ 
  public void search(List<List<String>> res, boolean [][] board, int row) {
    if (row >= board.length) {
      // add to res when find a solution.
      List<String> rowlist = drawBoard(board);
      res.add(rowlist);
      return;
    }
    
    for (int col = 0; col < board.length; col++) {
      if (!isValid(board, row, col)) {
        continue;
      }
      // valid space
      board[row][col] = true;
      // search if this valid space leads to a solution.
      search(res, board, row + 1);
      // whether this leads to a solution or not, we need to look for ALL
      // posibilities.
      board[row][col] = false;
    }
  }
  
  public List<String> drawBoard(boolean [][] board) {
    List<String> row = new ArrayList<>();
    for (int r = 0; r < board.length; r++) {
      StringBuilder sb = new StringBuilder();
      for (int c = 0; c < board[r].length; c++) {
        char pos = board[r][c] ? 'Q' : '.';
        sb.append(pos);
      }
      row.add(sb.toString());
    }
    
    return row;
  }
  
  /**
   * check for \, |, /, 3 directions. 
   * T(n) to check. b/c Even at last row, \ & / takes n-1, | takes n-1
   * So 2(n-1); 
   */
  public boolean isValid(boolean [][] board, int row, int col) {
    // check if a Q is directly on top of cur Q
    for (int preRow = row - 1; preRow >= 0; preRow--) {
      if (board[preRow][col]) {
        return false;
      }
    }
    // check top left diagonal Qs.
    for (int preRow = row - 1, preCol = col - 1; preRow >= 0 && preCol >= 0;
        preRow--, preCol--) {
      if (board[preRow][preCol]) {
        return false;
      }
    }
    // check top right diagonal Qs.
    for (int preRow = row - 1, nextCol = col + 1;
        preRow >= 0 && nextCol < board.length; preRow--, nextCol++) {
      if (board[preRow][nextCol]) {
        return false;
      }
    }
    
    return true;
  }


  /*
   * @param n: The number of queens
   * @return: All distinct solutions
   */
  public List<List<String>> solveNQueens2(int n) {
    List<List<String>> res = new ArrayList<>();
    if (n <= 0) {
      return res;
    }
    List<Integer> rowCols = new ArrayList<>();
    search2(res, rowCols, n);
    
    return res;
  }


  
  /**
   * Search for ALL posible solutions.
   * Solution2: using rowCols 1D array.
   */ 
  public void search2(List<List<String>> res, List<Integer> rowCols, int n) {
    if (rowCols.size() >= n) {
      // add to res when find a solution.
      List<String> rowlist = drawBoard2(rowCols);
      res.add(rowlist);
      return;
    }
    
    for (int col = 0; col < n; col++) {
      if (!isValid2(rowCols, col)) {
        continue;
      }
      // valid space
      rowCols.add(col);
      // search if this valid space leads to a solution.
      search2(res, rowCols, n);
      // whether this leads to a solution or not, we need to look for ALL
      // posibilities.
      rowCols.remove(rowCols.size() - 1);
    }
  }
  
  public List<String> drawBoard2(List<Integer> rowCols) {
    List<String> rowlist = new ArrayList<>();
    for (int r = 0; r < rowCols.size(); r++) {
      StringBuilder sb = new StringBuilder();
      for (int c = 0; c < rowCols.size(); c++) {
        char pos = (rowCols.get(r) == c) ? 'Q' : '.';
        sb.append(pos);
      }
      rowlist.add(sb.toString());
    }
    
    return rowlist;
  }
  
  /**
   * check for \, |, /, 3 directions. 
   * T(n) to check. b/c Even at last row, \ & / takes n-1, | takes n-1
   * So 2(n-1); 
   */
  public boolean isValid2(List<Integer> rowCols, int col) {
    int row = rowCols.size();
    for (int preRow = row - 1; preRow >= 0; preRow--) {
      // check if a Q is directly on top of cur Q, col # is same.
      if (rowCols.get(preRow) == col) {
        return false;
      }
      // check top left diagonal Qs. Ex: (1,1) (2,2) 1-1 == 2-2; 
      if (rowCols.get(preRow) - preRow == col - row) {
        return false;
      }
      // check top right diagonal Qs. Ex: (1,3) (2,2) 1+3 == 2+2;
      if (rowCols.get(preRow) + preRow == col + row) {
        return false;
      }
    }
    
    return true;
  }
}