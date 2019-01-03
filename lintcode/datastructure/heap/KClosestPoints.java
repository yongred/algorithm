/**
612. K Closest Points
Given some points and an origin point in two-dimensional space, find k points which are nearest to the origin.
Return these points sorted by distance, if they are same in distance, sorted by the x-axis, and if they are same in the x-axis, sorted by y-axis.

Example
Given points = [[4,6],[4,7],[4,4],[2,5],[1,1]], origin = [0, 0], k = 3
return [[1,1],[2,5],[4,4]]

*/

/**
Solution1: Use MaxHeap with k points, Find point < maxElm in heap, replace it. The remining k elms are minDist.
* Question ask to find the k minDist from Origin. Which means we can use a Heap to maintain k size of minDists.
* Easier to use a MaxHeap, so we can just pop off Larger points until k minDist left.
* We can use the distance formula to calc dist of 2 points.
* Distance = sqrt((x1-x2)^2 + (y1-y2)^2);
* Question say to compare Dist, if dists== compare x-axis, if xs== compare y-axis. So we will implement our PriorityQueue with Comparator like this.
* We want a MaxHeap, so DESC order of comparator. 
* Max heap, DESC order, b - a, so a>b -, a<b +, a=b 0;
* We add first k points in maxHeap.
* Then, compare the rest of the points to maxElm in heap, replace if we found shorterDist points. This maintains K minDist elms in heap.
* The rest k elms are minDists.
* Question wants Res in array of k Points from minDist to maxDist, ASC.
* So we just add the maxHeap.poll elms in reverse order.

Time: O(N lg K); lgK is the heapify height of binary tree of K elements.
Space: O(K);
*/

import java.util.*;
import java.io.*;

public class KClosestPoints {

	public static class Point {
    public int x;
    public int y;
    Point() { x = 0; y = 0; }
    Point(int a, int b) { x = a; y = b; }
  }

  /*
   * @param points: a list of points
   * @param origin: a point
   * @param k: An integer
   * @return: the k closest points
   * Solution1: 
     -Use PriorityQueue as Max-Heap to pop off largest distances.
     -Distance = sqrt((x1-x2)^2 + (y1-y2)^2);

     Time: O(N lg K); lgK is the heapify height of binary tree of K elements.
     Space: O(K);
   */
  public Point[] kClosest(Point[] points, Point origin, int k) {
    // Max Heap.
    PriorityQueue<Point> maxHeap = new PriorityQueue<Point>(
        (Point a, Point b) -> {
          // Max heap, DESC order, b - a, so a>b -, a<b +, a=b 0;
          // compare dist from origin.
          if (dist(a, origin) != dist(b, origin)) {
            // > +, < -, = 0;
            return dist(b, origin) - dist(a, origin);
          } else if (a.x != b.x) {
            // origin dists of a, b are ==, compare their x-axis
            return b.x - a.x;
          } else {
            // their distFromOrigin and x-axis are ==, compare y-axis.
            return b.y - a.y;
          }
        }
    );
    // Construct a MaxHeap of k elms. amortised O(k);
    int pi = 0;
    while (maxHeap.size() < k) {
      maxHeap.add(points[pi]);
      pi++;
    }
    // Find point < maxElm in heap, replace it. The remining k elms are minDist. O(n-k)
    for (int i = pi; i < points.length; i++) {
      // use comparator implemented earlier in heap to compare their dists. O(k);
      // DESC compare so, a>b -, a<b +, a=b 0; So p[i] < maxHeap.peek() Should be +.
      if (maxHeap.comparator().compare(points[i], maxHeap.peek()) > 0) {
        maxHeap.poll();
        maxHeap.add(points[i]);
      }
    }
    // return as array sorted by minDist to largestDist.
    // Front of heap is Max, so reverse order.
    Point[] res = new Point[k];
    int index = k - 1;
    while (!maxHeap.isEmpty()) {
      res[index] = maxHeap.poll();
      index--;
    }
    return res;
  }
  
  public int dist(Point a, Point b) {
    // dist of 2 points = sqrt((x1-x2)^2 + (y1-y2)^2), 
    // we don't need to sqrt. just need it to compare.
    return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
  }

  public static void main(String [] args){
  	int k = 3;
  	Point origin = new Point(0,0);
  	Point [] points = {new Point(4,6), new Point(4,7), new Point(4,4), new Point(2,5), new Point(1,1)};
  	Point [] res = kClosest(points, origin, k);

  	for(Point pt : res){
  		System.out.println("[" + pt.x + ", " + pt.y + "]");
  	}
  }

}

