/**
10 Wizards

http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=277494

There are 10 wizards, 0-9, you are given a list that each entry is a list of wizards known by wizard. Define
the cost between wizards and wizard as square of different of i and j. To find the min cost between 0
and 9.

wizard[0] list: 1, 4, 5; 
wizard[4] list: 9;
wizard 0 to 9 min distance is (0-4)^2+(4-9)^2=41 (wizard[0]
->wizard[4]->wizard[9])

巫师不同级别,算最小路径. 有向图里面找最短路径
抽象一下,典型的 djstra 问题
BFS 是可以做的,时间复杂度比 Dijkstra 还小
如果保证 wizard 认识关系双向的话,最优路径不会回头,一个简单 dp 就可以了,线性复杂度。
不保证的话,选一种最短路算法实现,顺手就行。 Dijkstra m*log(n).
楼上的 bfs 算法会退化,别的先不说,一个点在队列里出现两次是没有意义的。(补上了还是会
退化。)

http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=278915

想请教下魔法师这道题,我没太明白题目含义:

简化下比如说有五个魔法师,求 0 到 4 认识的最少距离
0 号认识 1号, 2 号
1 号认识 3号
2 号认识 3号, 4 号
3 号认识 4号

那么 0 号到 4 号的三条路径有: 0->1->3->4, 0->2->3->4, 0->2->4
最小权重和的路径是 0->2->3->4 (3-2)^2 + (4-3)^2= 2

对吗? 因为帖子里说:介绍人的收费是两个巫师级别差的平方,初始巫师本来就认识的那些巫师
不需要收费

多问一句,这个是无向图还是有向图?我理解的认识的话,是两个人都互相认识,对吗?谢谢!

我面试时,面试官没有提到“初始巫师本来就认识的那些巫师不需要收费”,所以你的例子要加上
(2-0)^2.但是我觉得这一点对解法没有影响。

我是根据无向来理解。
*/

import java.io.*;
import java.util.*;

public class TenWizards {

	class Point {
		int wizard;
		// min total dist to here.
		int dist;
		// shortest path's parentNode;
		Point parent;

		Point(int wizard, int dist) {
			this.wizard = wizard;
			this.dist = dist;
			// default parent to null; No parent;
			this.parent = null;
		}

		Point(int wizard, int dist, Point parent) {
			this.wizard = wizard;
			this.dist = dist;
			this.parent = parent;
		}
	}

	/**
	 * Solution: Dijkstra shortest Path
	 * Time: O(E lg E);
	 * Space: O(E + V);
	 */
	public List<Integer> getShortestPath(List<List<Integer>> wizards, int src, int dst) {
		// Dijkstra shortest Path
		List<Integer> res = new ArrayList<>();
		Map<Integer, Set<Integer>> graph = new HashMap<>();
		// build graph
		buildGraph(wizards, graph);
		// PriorityQueue for shortest paths.
		PriorityQueue<Point> pq = new PriorityQueue<>((a, b) -> {
			return a.dist - b.dist;
		});
		// init with src loc, 0 dist;
		pq.add(new Point(src, 0));
		Set<Integer> visited = new HashSet<>();
		Point targetPoint = null;

		while (!pq.isEmpty()) {
			Point point = pq.poll();
			visited.add(point.wizard);
			// check if is Dst;
			if (point.wizard == dst) {
				targetPoint = point;
				break;
			}
			// check children
			for (int child : graph.get(point.wizard)) {
				if (!visited.contains(child)) {
					// calc dist from curPoint to Child;
					int totalDist = point.dist + getDist(point.wizard, child);
					Point nextPoint = new Point(child, totalDist, point);
					pq.add(nextPoint);
				}
			}
		}
		// null not found dst.
		if (targetPoint == null) {
			// no path to dst.
			return res;
		}
		// get all nodes in the path of targetPoint;
		while (targetPoint != null) {
			res.add(targetPoint.wizard);
			targetPoint = targetPoint.parent;
		}
		Collections.reverse(res);
		return res;
	}

	public void buildGraph(List<List<Integer>> wizards, Map<Integer, Set<Integer>> graph) {
		for (int i = 0; i < wizards.size(); i++) {
			graph.putIfAbsent(i, new HashSet<>());
			// connect edges.
			for (int child : wizards.get(i)) {
				graph.get(i).add(child);
			}
		}
	}

	public int getDist(int p1, int p2) {
		// dist = (p1 - p2)^2;
		return (p1 - p2) * (p1 - p2);
	}


	public static void main(String[] args) {
		TenWizards obj = new TenWizards();
		List<List<Integer>> wizards = new ArrayList<>();
		int[][] arr = new int[][] {{1, 4}, {2}, {3, 4}, {}, {1}}; 
		int src = 0;
		int dst = 4;
		for (int i = 0; i < arr.length; i++) {
			wizards.add(new ArrayList<>());
			for (int j = 0; j < arr[i].length; j++) {
				wizards.get(i).add(arr[i][j]);
			}
		}

		List<Integer> res = obj.getShortestPath(wizards, src, dst);
		res.forEach(n -> {
			System.out.print(n + ", ");
		});
		System.out.println();
	}
}


