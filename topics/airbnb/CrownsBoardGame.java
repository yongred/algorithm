/**
Crowns Board Game:

给了上面这个图和很长的题干，读好久题，我一开始以为是个OOD。 后来给了input String[] board， 就是实现 calculate_score，score=连续土地的面积 * 连续土地的皇冠数。

bfs，小哥说没问题。最后有个bug没搞出来。

第一题是找总面积，还是最大面积

连续相同类型的土地面积，比如题图里蓝色的水那块的得分是 7(土地面积) * 3(皇冠数) = 21，森林那块是4(土地面积) * 0(皇冠数)  = 0，求的是整个board的得分

------------------

一种游戏叫做Kingdomino，游戏在棋盘上进行（比如5x5）。棋盘上有不同种类的方块，方块上可能有皇冠。例如，一个方块表示为："G2"，那么它表示方块的种类为"G"，上面有两个皇冠。
我们规定，同一种类的方块可连成一片，我们可以称之为一个“区域”。那么这个区域的得分为：同种方块数量 x 区域内的皇冠数。整个游戏的得分为，所有不同种类区域的得分之和。

例如一下棋盘，array of strings：[
  "G0 W1 W1 W0 P2",
  "W0 W0 F0 F0 F0",
  "W0 W1 F0 S2 S1",
  "G0 X0 G1 G0 G0",
  "S0 M2 M0 G1 F0"
];

上面棋盘中：
21分来自，7个W x 3皇冠；
2分来自，1个P x 2皇冠；
6分来自，2个S x 3皇冠；
8分来自，4个G x 2皇冠；
4分来自，2个M x 2皇冠；

总共41分。

问题：创建一个类，我们可以叫它Kingdom，实例化的时候输入棋盘（array of strings），并且实现一个方法，calculateScore()，这个方法计算总分数。

lands: -1 is not a land.
{1,2,2,2,3}
{2,2,4,4,4}
{2,2,4,5,5}
{6,-1,7,7,7}
{8,9,9,7,10}

crowns
{0,1,1,0,2}
{0,0,0,0,0}
{0,1,0,2,1}
{0,0,1,0,0}
{0,2,0,1,0}

0 + 21 + 2 + 0 + 6 + 0 + 8 + 0 + 4 + 0 = 41
*/

import java.io.*;
import java.util.*;

public class CrownsBoardGame {

	class Point {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	/**
	 * Solution 1: BFS
	 * Time: O(n)
	 * Space: O(n)
	 */
	public int getScore(String[] board) {
		// crowns [r][c] = # of crowns;
		String[][] newBoard = convertBoard(board);
		int rows = newBoard.length;
		int cols = newBoard[0].length;
		// BFS
		boolean[][] visited = new boolean[rows][cols];
		int total = 0;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (!visited[r][c]) {
					total += bfs(newBoard, r, c, visited);
				}
			}
		}
		return total;
	}

	int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

	public int bfs(String[][] newBoard, int row, int col, boolean[][] visited) {
		int rows = newBoard.length;
		int cols = newBoard[0].length;

		Deque<Point> queue = new ArrayDeque<>();
		queue.add(new Point(row, col));
		visited[row][col] = true;

		int sizes = 0;
		int crowns = 0;
		while (!queue.isEmpty()) {
			Point point = queue.poll();
			// update sizes and crowns
			String curGrid = newBoard[point.x][point.y];
			char land = curGrid.charAt(0);
			int crown = Integer.parseInt(curGrid.substring(1, curGrid.length()));
			sizes++;
			crowns += crown;
			// 4 dirs
			for (int i = 0; i < 4; i++) {
				int x = point.x + dirs[i][0];
				int y = point.y + dirs[i][1];
				// check boundaries, and if is Ocean;
				if (x >= 0 && x < rows && y >= 0 && y < cols && !visited[x][y]) {
					char toLand = newBoard[x][y].charAt(0);
					if (land == toLand) {
						queue.add(new Point(x, y));
						visited[x][y] = true;
					}
				}
			}
		}
		// calc
		return (sizes * crowns);
	}

	public String[][] convertBoard(String[] board) {
		int rows = board.length;
		int cols = board[0].split(" ").length;
		String[][] res = new String[rows][cols];

		for (int r = 0; r < rows; r++) {
			String[] row = board[r].split(" ");
			for (int c = 0; c < cols; c++) {
				res[r][c] = row[c];
			}
		}
		
		return res;
	}

	public static void main(String[] args) {
		CrownsBoardGame obj = new CrownsBoardGame();
		String[] board = {
			"G0 W1 W1 W0 P2",
		  "W0 W0 F0 F0 F0",
		  "W0 W1 F0 S2 S1",
		  "G0 X0 G1 G0 G0",
		  "S0 M2 M0 G1 F0"
		};

		int res = obj.getScore(board);
		System.out.println(res);
	}
}