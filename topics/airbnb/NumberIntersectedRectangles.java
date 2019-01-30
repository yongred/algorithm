/**
Number of Intersected Rectangles

You have a plain with lots of rectangles on it, find out how many of them intersect.

本质是 rectangle intersection + union find
Leetcode 相似问题: Leetcode #323 Number of Connected Components in an Undirected Graph
*/

/**
Solution: UnionFind + rectIntersect;
How to Arrive:
* RectIntersect:
Not overlaping
case1:
l1[] l2[]: l1.x2 <= l2.x1

Or case2: 
l2[] l1[]: l1.x1 >= l2.x2

Or case3:
l2[]
l1[] l1.y2 <= l2.y1

Or case4:
l1[]
l2[] l1.y1 >= l2.y2;

* Question wants to know the number of Rectangles intersected with others.
* We can use UnionFind to put intersected Rects into Same Sets;
* All the intersected Rects that are connected have the same TopNode;
* And the TopNode has the Size (# of nodes in this intersected Set).
* After forming the UnionFind graph and union/connect with all intersected nodes, we can loop through all unique parents and find the ones that (size > 1) meaning atleast 2 rects intersected. 
* Add the sizes of the ones that are intersected. We get the totalIntersected rects;

* Time: O(n^2), nested loop to check intersections between all nodes.
* Space: O(n); UnionFind parents, sizes;
*/

import java.util.*;
import java.io.*;

public class NumberIntersectedRectangles {

	class UnionFind {
		int[] parents;
		int[] sizes;

		UnionFind(int n) {
			parents = new int[n];
			sizes = new int[n];

			for (int i = 0; i < n; i++) {
				// init parent as itself.
				parents[i] = i;
				// size = 1, just itself.
				sizes[i] = 1;
			}
		}

		/** 
		 * Collapsing Find, get it's top node.
		 * O(lgN) in worse case; 
     * Combine with Union Rank Find, Amortised O(1) to construct the graph.
     */
		public int findTop(int node) {
			if (parents[node] == node) {
				return node;
			}
			// all path nodes' parent becomes topNode.
			parents[node] = findTop(parents[node]);
			return parents[node];
		}

		/**
     * Union By Rank/Size.
     * O(1); Combine with Collapsing Find,Amortised O(1) to construct the graph.
     */
		public void union(int n1, int n2) {
			// find their tops
			int top1 = findTop(n1);
			int top2 = findTop(n2);
			// find cycle
			if (top1 == top2) {
				return;
			}
			// check their sizes
			if (sizes[top2] > sizes[top1]) {
				// connect top1->top2;
				parents[top1] = top2;
				// upadte size of top2
				sizes[top2] += sizes[top1];
			} else {
				// top1 size >= top2
				parents[top2] = top1;
				sizes[top1] += sizes[top2];
			}
		}
 	}
	
	public int countIntersects(int[][] rectangles) {
		// rect: [x1,y1,x2,y2] x1 left, y1 bottom.
		// rects: 0->n-1; indexes are rects;
		int n = rectangles.length;
		UnionFind graph = new UnionFind(n);
		// connect the graph
		for (int i = 0; i < n; i++) {
			int[] rect1 = rectangles[i];
			// find rect1's intersected rects.
			for (int j = i + 1; j < n; j++) {
				int[] rect2 = rectangles[j];
				if (intersect(rect1, rect2)) {
					System.out.println(i + " intersect " + j);
					// Union those 2
					graph.union(i, j);
				}
			}
		}
		// Go through all unique parents add their sizes together we get the rects intersected.
		int totalRects = 0;
		Set<Integer> uniqueParents = new HashSet<>();
		for (int i = 0; i < n; i++) {
			if (!uniqueParents.contains(graph.parents[i])) {
				// if size of set > 1 means there is intersection. =1 means no intersect, so no need to count.
				if (graph.sizes[i] > 1) {
					totalRects += graph.sizes[i];
				}
				uniqueParents.add(graph.parents[i]);
			}
		}

		return totalRects;
	}

	public boolean intersect(int[] r1, int[] r2) {
		// cases of Not intersect, same corner point not count
		// r1[] r2[]: r1.x2 <= r2.x1; OR
		// r2[] r1[]: r1.x1 >= r2.x2; OR
		// r2[]
		// r1[]: r1.y2 <= r2.y1; OR
		// r1[]
		// r2[]: r1.y1 >= r2.y2;
		return !((r1[2] <= r2[0]) || (r1[0] >= r2[2]) ||
				(r1[3] <= r2[1]) || (r1[1] >= r2[3]));
	}

	public static void main(String[] args) {
		NumberIntersectedRectangles obj = new NumberIntersectedRectangles();
		int[][] rectangles = {
			{0, 0, 10, 10}, {5, 5, 15, 15}, {12, 12, 16, 16},
			{20, 20, 30, 30}, {25, 25, 35, 35},
			{-10, -10, 0, 0},
			{-10, 10, 0, 20}
		};

		int res = obj.countIntersects(rectangles);
		System.out.println(res);
	}
}