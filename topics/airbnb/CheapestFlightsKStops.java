/**
787. Cheapest Flights Within K Stops
There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.

Now given all the cities and flights, together with starting city src and the destination dst, your task is to find the cheapest price from src to dst with up to k stops. If there is no such route, output -1.

Example 1:
Input: 
n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
src = 0, dst = 2, k = 1
Output: 200
Explanation: 
The graph looks like this:


The cheapest price from city 0 to city 2 with at most 1 stop costs 200, as marked red in the picture.
Example 2:
Input: 
n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
src = 0, dst = 2, k = 0
Output: 500
Explanation: 
The graph looks like this:


The cheapest price from city 0 to city 2 with at most 0 stop costs 500, as marked blue in the picture.
Note:

The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1.
The size of flights will be in range [0, n * (n - 1) / 2].
The format of each flight will be (src, dst, price).
The price of each flight will be in the range [1, 10000].
k is in the range of [0, n - 1].
There will not be any duplicated flights or self cycles.
*/

/**
Solution 1: BFS, create graph, use queue.
How to arrive:
* We can easily see it as a BFS problem, but we need to find a way to store costs of each path.
* **Key**: don't need to store all path costs sum. We just need the minCost sofar to this city.
* Like DP, we use array distCosts to store minCosts to each city.
**Algorithm**:
* Construct a graph representation using 3d array. [from][to][isConnected, cost]
* init the Graph with all the edges.
* Declare an array of distCosts for minCost to each city from src.
* Fill distCosts with MAXs, and distCosts[src] startCity with 0;
* Declare a queue for BFS. Store indexes represent cities.
* Declare a var stops for keep track of how many stops is curLoc.
* While queue is not empty, and stops is not > k:
	* loop cur queue elms, 0 -> queue.size-1:
		* curLoc = queue.poll();
		* if curLoc is dst, we skip, No need to check if connect to itself.
		* Loop every city to check if curLoc is connected:
			* if stops == k, (stop limit reached), we cannot use curLoc as a stop. Next city has to be dst.
			* So if stops == k and city != dst: Ex: k=1, stop1 is curLoc, stop2 can only be dst.
				* SKIP this city. Don't update costs.
		 * If graph edge is connect curLoc to city. And curLocMinCost + this Edge Cost < cityMinCost distCosts[city]:
		* if (graph[curLoc][city][0] == 1 && distCosts[city] > distCosts[curLoc] + graph[curLoc][city][1])
			 * queue add city as next loc stop.
			 * update minCost for the city;
	* increment stops.
* Check if no routes exists in k stops distCosts[dst] == Integer.MAX_VALUE:
	* return -1;
* Else return distCosts[dst];
* Time: O(n^k); worst case each node is connected to all other nodes. so n-1 each node until k times.
* Space: O(n^k); same as runtime, using queue. (n * n-1) nodes next level;

-------------------
Solution 2: DP, save prev k/stops minCosts.
			 0    1        2        3        4
k=0[0, MAX, MAX, MAX, MAX], 
k=1[0, 100, MAX, 200, MAX], 
k=2[0, 100, 200, 200, 400], 
k=3[0, 100, 200, 200, 300]

How to arrive:
* Similar to prev solution BFS, we store minCost to city sofar in K stops in dp[k+2][n];
* If we know prev Stops/k minCosts, We just need to compare it with cur stops cost.
Ex: prevStop=0: 1->3 = 300; cur stops=1: 1->2->3 = 200; curStop 200 is less cost.
* So **dynamic programming 0 stop to k stops**; 
* **Calc prev Stops, use to calc next stop minCosts**;
* Base cases: 
	* From start city to start city is 0 costs. So no matter the K (how many stops), it's always 0;
	* Init everything else to MAX, so we can compare later to find minCost.
* Recursive cases:
	* We loop from 1 -> K+1: (instead of 0->k; dp use 1st row to init vals).
		* Set src on this k to 0; (src to src always 0 cost);
		* Loop the flights/edges:
			* get the fromCity, toCity, cost;
			* **Key**: if fromMinCost is not MAX (prevent overflow MAX + Cost; Also if fromCost is Max then we know it's not MinCost):
				* toCityMincost = MIN( another route toCityMincost, OR prev stop/k fromCityMinCost + flightCost);
				Ex: prevStop=0: 1->3 = 300; PrevToCityMinCost.
				cur stops=1: 
				1->2=100 (prevStop fromCity Cost); 2->3 = 100 (FlightCost); 
				100 + 100 = 200; curStop 200; 
				200 < 300;
* Check if K stops route exist:
	* No: return -1;
	* Yes: return dp[K+1][dst]
* Time: O(K * Edges)
* Space: O(k * n);
*/

class CheapestFlightsKStops {

	/**
	 * Solution 2: DP, save prev k/stops minCosts.
	 * Time: O(K * Edges)
	 * Space: O(k * n);
	 */
	public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
    // dp [k+2][n], minCosts with k stops to each city.
    int[][] dp = new int[K + 2][n];
    // fill MAXs init for compare to finding minCost.
    for (int i = 0; i <= K + 1; i++) {
      for (int j = 0; j < n; j++) {
        dp[i][j] = Integer.MAX_VALUE;
      }
    }
    // to start city is 0 cost.
    dp[0][src] = 0;
    // use 1->k+1 instead of 0->k;
    for (int i = 1; i <= K + 1; i++) {
      // src to src is 0 cost
      dp[i][src] = 0;
      // loop through flights, edges.
      for (int[] edge : flights) {
        int from = edge[0];
        int to = edge[1];
        int cost = edge[2];
        // prevent overflow MAX + Cost.
        if (dp[i - 1][from] != Integer.MAX_VALUE) {
          // toCityMincost = min(another route toCityMincost, prev stop/k fromCityMinCost + flightCost)
          dp[i][to] = Math.min(dp[i][to], dp[i - 1][from] + cost);
        }
      }
    }
    // check if found a trip in k stops.
    return (dp[K + 1][dst] == Integer.MAX_VALUE) ? -1 : dp[K + 1][dst];
  }

	/**
	 * Solution 1: BFS, create graph, use queue.
	 * Time: O(n^k); worst case each node is connected to all other nodes. so n-1 each node until k times.
	 * Space: O(n^k); same as runtime, using queue. (n * n-1) nodes next level;
	 */
  public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
    // [from][to][isConnected, cost]
    int[][][] graph = new int[n][n][2];
    // init graph
    for (int[] edge : flights) {
      int from = edge[0];
      int to = edge[1];
      int cost = edge[2];
      // the cost
      graph[from][to][1] = cost;
      // connected.
      graph[from][to][0] = 1;
    }
    // min dist cost for each city
    int[] distCosts = new int[n];
    // Fill it with MAXs for init
    Arrays.fill(distCosts, Integer.MAX_VALUE);
    distCosts[src] = 0; // start city default.
    // queue for indexes
    Deque<Integer> queue = new ArrayDeque<>();
    queue.addLast(src);
    // stops count
    int stops = 0;
    // BFS
    while (!queue.isEmpty() && stops <= K) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        // visit city. cur location.
        int curLoc = queue.pollFirst();
        // if curLoc is dst. No need to check if connect to itself.
        if (curLoc == dst) {
          continue;
        }
        // check where it connect, and has less cost to that city.
        for (int city = 0; city < n; city++) {
          // if stop limit reached, and city is not dst, then skip.
          // cannot use curLoc as a stop if stops=k already.
          if (stops == K && city != dst) {
            continue;
          }
          // check connection, and less cost.
          if (graph[curLoc][city][0] == 1 &&
              distCosts[city] > distCosts[curLoc] + graph[curLoc][city][1]) {
            queue.add(city);
            // update minCost
            distCosts[city] = distCosts[curLoc] + graph[curLoc][city][1];
          }
        }
      }
      stops++;
    }
    
    return (distCosts[dst] == Integer.MAX_VALUE) ? -1 : distCosts[dst];
  }

  /**
   * Solution: Dijkstra shortest Path alg. Using PriorityQueue. Same as BFS;
   * Time: O(n^k); same as BFS, B/c we don't use visited.
   * Space: O(n^k); same as runtime, using PQ. (n * n-1) nodes next level;
   */
  class Point {
    // cur city.
    int city;
    // total cost of the path visits.
    int cost;
    // stops left
    int stops;
    
    Point(int city, int cost, int stops) {
      this.city = city;
      this.cost = cost;
      this.stops = stops;
    }
  }
  
  /**
   * @param n: a integer
   * @param flights: a 2D array
   * @param src: a integer
   * @param dst: a integer
   * @param K: a integer
   * @return: return a integer
   * Solution: Dijkstra shortest Path alg. Using PriorityQueue.
   */
  public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
    // graph, city->(city, price);
    Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
    // edges to map graph
    for (int[] edge : flights) {
      // [0] city1 to [1] city2, [2] cost.
      if (!graph.containsKey(edge[0])) {
        graph.put(edge[0], new HashMap<>());
      }
      graph.get(edge[0]).put(edge[1], edge[2]);
    }
    // Dijkstra shortest Path alg.
    PriorityQueue<Point> pq = new PriorityQueue<>((a, b) -> {
      // sort by cost, ASC
      return a.cost - b.cost;
    });
    // add src as starting city point. 
    // K stops, meaning can have k+1 visits cities including dst.
    pq.add(new Point(src, 0, K + 1));
    while (!pq.isEmpty()) {
      Point curPoint = pq.poll();
      // check is dst.
      if (curPoint.city == dst) {
        return curPoint.cost;
      }
      // check if stops limit reached.
      if (curPoint.stops > 0) {
        // Still stops left; find adj point/cities;
        // some city don't have edge -> to other, so remember to create a empty map.
        Map<Integer, Integer> adjCities = graph.getOrDefault(curPoint.city,
            new HashMap<>());
        for (int toCity : adjCities.keySet()) {
          // add to PQ, so it will be sorted in lowest cost
          int totalCost = adjCities.get(toCity) + curPoint.cost;
          // 1 less stop available.
          pq.add(new Point(toCity, totalCost, curPoint.stops - 1));
        }
      }
    }
    // cannot reach.
    return -1;
  }
  
}