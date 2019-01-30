/**
Skiing Score
滑雪题

输入(String[][] travel, String[][] point)
输出：int score
travel的形式是[["start","3","A],["A","4","B"],["B","5","END1"]]

第一个String为出发点，第二个为从某个点到下一个点的cost，第三个String为结束点，可以想象为滑雪的人从“start"到"A"需要的cost为3.
point的形式为[["A","5"],["B","6"],["END","3"]]
代表到达每一个点的reward
所要求的是从start到end的最大score，注意end点可能有很多个，start是固定的
score的计算方法是reward-cost
比如说从start到A，cost是3，reward是5，那么A的score就是5-3=2，当然只是举个例子，A的score应该是所有到达A的方式里最大的那个。
我的做法是先用Hashmap把cost, reward保存下来，然后再用一个Hashmap存每个点的score，做bfs一直到END，可以找到最大的score。
Follow up 是要求打印从start到end的路径，就是使得score最大的那条路。
做法是用一个parent把每个点的parent记录下来，再从END一路找parent回去，找到start了就是我们要找的路径。

*/

/**
Dilstra Path Alg:
(reward - cost) = score
score order in DESC, prioritize Higher Score in Queue.

Question:
- Will there be cycle? 
Ex: A->B cost=1, reward=5; B->A cost=1, reward=4; Then the score will increase Infinitely.
Assume no Cycle.
*/
import java.io.*;
import java.util.*;

public class SkiingScore {

	class Point {
		String place;
		// total max score for the path.
		int score;
		Point parent;

		Point(String place, int score) {
			this.place = place;
			this.score = score;
			this.parent = null;
		}

		Point(String place, int score, Point parent) {
			this.place = place;
			this.score = score;
			this.parent = parent;
		}
	}
	
	/**
	 * Solution: Dilstra Path Alg:
		(reward - cost) = score
		score order in DESC, prioritize Higher Score in Queue.
	 * Time: O(V + E); O(T + P);
	 * Space: O(V + E);
	 */
	public int getScore(String[][] travel, String[][] points) {
		// build graph place -> (toPlace, cost);
		Map<String, Map<String, Integer>> graph = new HashMap<>();
		buildGraph(graph, travel);
		// place -> rewards
		Map<String, Integer> rewards = new HashMap<>();
		for (String[] placeReward : points) {
			// make reward in integer.
			rewards.put(placeReward[0], Integer.valueOf(placeReward[1]));
		}
		// prioritize by path Score. Always choosing the higher score first.
		Queue<Point> pq = new PriorityQueue<>((a, b) -> {
			// Score DESC.
			return b.score - a.score;
		});
		// Starting point, reward 0;
		pq.add(new Point("start", 0));
		Point path = null;
		// BFS
		while (!pq.isEmpty()) {
			Point point = pq.poll();
			// check if reached end;
			if (point.place.equals("END")) {
				path = point;
				break;
			}
			// adj
			Map<String, Integer> toPlaceCosts = graph.get(point.place);
			for (String toPlace : toPlaceCosts.keySet()) {
				int totalScore = point.score + (rewards.get(toPlace) - toPlaceCosts.get(toPlace));
				// record the path, score and parent point.
				Point newPoint = new Point(toPlace, totalScore, point);
				pq.add(newPoint);
			}
		}
		// Finished traverse all.
		if (path == null) {
			// no path to END;
			return -1;
		}
		// Print the path
		printPath(path, true);
		return path.score;
	}

	public void printPath(Point path, boolean last) {
		if (path.parent == null) {
			System.out.print(path.place + ", ");
			return;
		}
		printPath(path.parent, false);
		System.out.print(path.place + ", ");
		if (last) {
			System.out.println();
		}
	}

	public void buildGraph(Map<String, Map<String, Integer>> graph, String[][] travel) {
		for (String[] edge : travel) {
			graph.putIfAbsent(edge[0], new HashMap<>());
			// make cost in integer
			graph.get(edge[0]).put(edge[2], Integer.valueOf(edge[1]));
		}
	}

	public static void main(String[] args) {
		SkiingScore obj = new SkiingScore();
		String[][] travel = {
			{"start","3","A"},{"A","4","B"},{"B","4","END"},
			{"start","1","C"},{"C","3","A"},{"C","1","END"}
		};
		String[][] points = {
			{"A","5"},{"B","6"},{"C","4"},{"END","3"}
		};

		int res = obj.getScore(travel, points);
		System.out.println(res);
	}
}