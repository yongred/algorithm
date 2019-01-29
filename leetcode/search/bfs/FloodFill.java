/**
733. Flood Fill
An image is represented by a 2-D array of integers, each integer representing the pixel value of the image (from 0 to 65535).

Given a coordinate (sr, sc) representing the starting pixel (row and column) of the flood fill, and a pixel value newColor, "flood fill" the image.

To perform a "flood fill", consider the starting pixel, plus any pixels connected 4-directionally to the starting pixel of the same color as the starting pixel, plus any pixels connected 4-directionally to those pixels (also with the same color as the starting pixel), and so on. Replace the color of all of the aforementioned pixels with the newColor.

At the end, return the modified image.

Example 1:
Input: 
image = [[1,1,1],[1,1,0],[1,0,1]]
sr = 1, sc = 1, newColor = 2
Output: [[2,2,2],[2,2,0],[2,0,1]]
Explanation: 
From the center of the image (with position (sr, sc) = (1, 1)), all pixels connected 
by a path of the same color as the starting pixel are colored with the new color.
Note the bottom corner is not colored 2, because it is not 4-directionally connected
to the starting pixel.
Note:

The length of image and image[0] will be in the range [1, 50].
The given starting pixel will satisfy 0 <= sr < image.length and 0 <= sc < image[0].length.
The value of each color in image[i][j] and newColor will be an integer in [0, 65535].
*/

/**
111
110
101

pos: [r1,c1]=1;
newColor = 2;

Res:
222
220
201

Solution: Straight forward BFS.
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

class FloodFill {
  
  class Point {
    int x;
    int y;
    
    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
  
  /**
   * Solution: BFS.
   * Time: O(r * c) or O(n)
   * Space: O(r * c) or O(n);
   */
  public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
    if (image == null || image.length == 0 || newColor == image[sr][sc]) {
      return image;
    }
    int rows = image.length;
    int cols = image[0].length;
    // BFS Queue
    Queue<Point> queue = new ArrayDeque<>();
    // 4 dirs
    int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    queue.add(new Point(sr, sc));
    int oldColor = image[sr][sc];
    // bfs
    while (!queue.isEmpty()) {
      Point point = queue.poll();
      // mark color, Same as visited.
      image[point.x][point.y] = newColor;
      // go 4 dirs
      for (int i = 0; i < 4; i++) {
        int newX = point.x + dirs[i][0];
        int newY = point.y + dirs[i][1];
        // check boundary, and not visited (== oldColor && != newColor);
        if (newX >= 0 && newX < rows && newY >= 0 && newY < cols &&
           image[newX][newY] == oldColor) {
          queue.add(new Point(newX, newY));
        }
      }
    }
    return image;
  }
  
  
}