/**
Finding Ocean
Given: An array of strings where L indicates land and W indicates water, and a coordinate marking a starting point in the middle of the ocean.

Challenge: Find and mark the ocean in the map by changing appropriate Ws to Os.

An ocean coordinate is defined to be the initial coordinate if a W, and
any coordinate directly adjacent to any other ocean coordinate.

void findOcean(String[] map, int row, int column);
String[] map = new String[]{
 "WWWLLLW",
 "WWLLLWW",
 "WLLLLWW"
};

printMap(map);
STDOUT:
WWWLLLW
WWLLLWW
WLLLLWW

findOcean(map, 0, 1);
printMap(map);

STDOUT:
OOOLLLW
OOLLLWW
OLLLLWW

LeetCode 733. Flood Fill.

*/

/**
Questions:
- What if (r, c) is at a L?
Assume stop function if is at L;

Solution: Straight forward BFS.
111
110
101

pos: [r1,c1]=1;
newColor = 2;

Res:
222
220
201

How to Arrive:
* Corner cases: 
	* Visited Conditions: (== oldColor And != newColor); B/c newColor == oldColor is possible.
	Ex: 
	[1,1,1]
	[0,0,0]
	fill (0,1) newColor=1;
	* So if newColor == oldColor, no need to do anything.

* Time: O(r * c) or O(n)
* Space: O(r * c) or O(n);
*/

import java.io.*;
import java.util.*;

public class FindingOcean {

	class Point {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public void findOcean(String[] map, int row, int col) {
		printMap(map);
		if (map == null || map.length == 0 || map[row].charAt(col) == 'L') {
			printMap(map);
			return;
		}
		int rows = map.length;
		int cols = map[0].length();
		// transform String[] into char[][]
		char[][] mapArr = new char[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				mapArr[i][j] = map[i].charAt(j);
			}
		}
		// Queue for bfs
		Queue<Point> queue = new ArrayDeque<>();
		queue.add(new Point(row, col));
		// dirs
		int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		// BFS
		while (!queue.isEmpty()) {
			Point point = queue.poll();
			// marked as O
			mapArr[point.x][point.y] = 'O';
			// go 4 dirs
			for (int i = 0; i < 4; i++) {
				int newX = point.x + dirs[i][0];
				int newY = point.y + dirs[i][1];
				// check boundaries, and if is Ocean;
				if (newX >= 0 && newX < rows && newY >= 0 && newY < cols &&
						mapArr[newX][newY] == 'W') {
					queue.add(new Point(newX, newY));
				}
			}
		}
		// print new map
		printMap(convert(mapArr, rows, cols));
	}

	public void printMap(String[] map) {
		for (int l = 0; l < map.length; l++) {
			System.out.println(map[l]);
		}
		System.out.println();
	}

	public String[] convert(char[][] mapArr, int rows, int cols) {
		String[] map = new String[rows];
		int r = 0;
		for (char[] rowArr : mapArr) {
			map[r] = new String(rowArr);
			r++;
		}
		return map;
	}


	public static void main(String[] args) {
		FindingOcean obj = new FindingOcean();
		String[] map = new String[] {
			"WWWLLLW",
			"WWLLLWW",
			"WLLLLWW"
		};
		int row = 0;
		int col = 1;
		obj.findOcean(map, row, col);
	}
}