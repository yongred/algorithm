/**
Crowns Board Game:

给了上面这个图和很长的题干，读好久题，我一开始以为是个OOD。 后来给了input String[] board， 就是实现 calculate_score，score=连续土地的面积 * 连续土地的皇冠数。

bfs，小哥说没问题。最后有个bug没搞出来。

第一题是找总面积，还是最大面积

连续相同类型的土地面积，比如题图里蓝色的水那块的得分是 7(土地面积) * 3(皇冠数) = 21，森林那块是4(土地面积) * 0(皇冠数)  = 0，求的是整个board的得分

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
	public int getScore(String[] board, int[][] crowns) {
		// crowns [r][c] = # of crowns;
		int rows = board.length;
		int cols = board[0].length();
		// BFS
		Queue<Point> queue = new ArrayDeque<>();
		// char, count size;
		int[] sizes = new int[256];
		int[] crownsCount = new int[256];
		queue.add(new Point(0, 0));
		int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		boolean[][] visited = new boolean[rows][cols];
		visited[0][0] = true;

		while (!queue.isEmpty()) {
			Point point = queue.poll();
			char cur = board[point.x].charAt(point.y);
			sizes[cur]++;
			// System.out.println(cur + " " + sizes[cur]);
			// check crowns
			if (crowns[point.x][point.y] > 0) {
				crownsCount[cur] += crowns[point.x][point.y];
			}
			// 4 dirs
			for (int i = 0; i < 4; i++) {
				int x = point.x + dirs[i][0];
				int y = point.y + dirs[i][1];
				// boundaries
				if (x >= 0 && x < rows && y >= 0 && y < cols && !visited[x][y]) {
					queue.add(new Point(x, y));
					// Have to make visited while adding queue, if not other siblings will also Add this grid to queue.
					// Causing Duplicates;
					visited[x][y] = true;
				}
			}
		}
		// check how many size and crowns
		int total = 0;
		for (int i = 0; i < 256; i++) {
			// System.out.println((i + 'a') + " Size " + sizes[i] + " crowns " + crownsCount[i]);
			total += (sizes[i] * crownsCount[i]);
		}
		return total;
	}

	public static void main(String[] args) {
		CrownsBoardGame obj = new CrownsBoardGame();
		String[] board = {
			"ABBBC",
			"BBDDD",
			"BBDEE",
			"FFGGG",
			"HIIGJ"
		};
		int[][] crowns = new int[][] {
			{0,1,1,0,2},
			{0,0,0,0,0},
			{0,1,0,2,1},
			{0,0,1,0,0},
			{0,2,0,1,0}
		};

		int res = obj.getScore(board, crowns);
		System.out.println(res);
	}
}