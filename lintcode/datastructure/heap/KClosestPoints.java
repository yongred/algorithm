/**
K Closest Points:

Given some points and a point origin in two dimensional space, find k points out of the some points which are nearest to origin.
Return these points sorted by distance, if they are same with distance, sorted by x-axis, otherwise sorted by y-axis.

Example
Given points = [[4,6],[4,7],[4,4],[2,5],[1,1]], origin = [0, 0], k = 3
return [[1,1],[2,5],[4,4]]

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
    public static Point[] kClosest(Point[] points, Point origin, int k) {
    	//use priorityQueue to store k points with smallest distance from origin
        PriorityQueue<Point> queue = new PriorityQueue<Point>(k, new Comparator<Point>(){
        	/* 
        	 naturally it will be in Min-heap order, we want max-heap order (Largest # pop first);
        	 1) -# means A>B; 0 means A==B; +# means A<B;
        	  This way when A>B, A will be put on top of the queue;
        	  Negative Will Put A on top of queue to pop next;
        	*/
        	@Override
        	public int compare(Point A, Point B){
        		//compare B and A's distance to origin with eachother
        		int diff = getDist(B, origin) - getDist(A, origin);
        		//sort by x-axis if dists are the same;
        		if(diff == 0){
        			diff = B.x - A.x;
        		}
        		//if dists and x-axis are all same, sort by y-axis;
        		if(diff == 0){
        			diff = B.y - A.y;
        		}

        		return diff;
        	}
        });

        for(int i=0; i< points.length; i++){
        	//add the next point for comparison.
        	queue.add(points[i]);
        	//only k elements of the smallest distances should be in queue
        	if(queue.size() > k)
        		queue.poll();
        }
        //result array of points
        Point [] res = new Point[k];
        //pop answers to res, starting from max -> min, so add to res backward
        while(!queue.isEmpty()){
        	//from k-1 -> 0 index. b/c smallest in front and large on back.
        	res[--k] = queue.poll();
        }

        return res;
    }
    //sqrt((x1-x2)^2 + (y1-y2)^2); can do it without sqrt b/c we only want to compare
    public static int getDist(Point A, Point B){
    	return (A.x - B.x) * (A.x - B.x) + (A.y - B.y) * (A.y - B.y);
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

