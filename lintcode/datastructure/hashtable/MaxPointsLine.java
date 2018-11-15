/**
186. Max Points on a Line
Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.

Example
Given 4 points: (1,2), (3,6), (0,0), (1,3).

The maximum number is 3.
*/

import java.util.*;
import java.io.*;

public class MaxPointsLine {

	class Point {
	      int x;
	      int y;
	      Point() { x = 0; y = 0; }
	      Point(int a, int b) { x = a; y = b; }
	  }
    /**
     * @param points: an array of point
     * @return: An integer
     * Solution:
     * Use Slope to determine the points on the same line.
     * Use hash to store the slopes w/ the cur point as starter
     * y=mx+b; we eliminates the calc of (b) by clear the slope hashes after we store the max for cur point starter. So it doesn't carry over.
     * ex: (1,2)(3,6)(0,0)(1,3)(0,1); start(1,2)(3,6)(0,0) m=2 cnt=3; start(1,3)(0,1) m=2 cnt=2;
     * Also, we have to consider duplicates points and vertical points.
     * Duplicates of cur point needs to be added to every slope cnt.
     * Verticals are count as another line, outside of slopes.
     * 
     * Time: O(N^2) 2loops for points. Space: O(N)
     */
    public int maxPoints(Point[] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        
        int max = 0;
        HashMap<Double, Integer> slopes = new HashMap<>();
        for(int i=0; i< points.length; i++)
        {
            int dup = 0; //duplicates as the cur point.
            int vertical = 1; //points on vertial straight line
            for(int j=i+1; j< points.length; j++)
            {
                //check dup and vertical
                if(points[i].x == points[j].x)
                {
                    if(points[i].y == points[j].y)
                    {
                        dup++;
                    }
                    else
                    {
                        vertical++;
                    }
                    continue;
                }
                //calc slope
                double slope = (double)(points[j].y - points[i].y) / 
                        (double)(points[i].x - points[j].x);
                
                if(slopes.containsKey(slope))
                {
                    slopes.put(slope, slopes.get(slope)+1);
                }
                else
                {
                    slopes.put(slope, 2);
                }
            }
            //get max slopes for the cur point
            for(int count : slopes.values())
            {
                max = Math.max(max, count+dup); //add the dup points to compare
            }
            max = Math.max(max, vertical+dup); //compare with veritcal line points
            //slopes for each starting point is independent from other points
            //this prevents parallel slopes, since parallel points never touch.
            slopes.clear();
        }
        return max;
    }
}