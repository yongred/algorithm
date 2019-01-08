/**
1562. Number of restaurants
Give a List of data representing the coordinates[x,y] of each restaurant and the customer is at the origin[0,0]. Find the n restaurants closest to the customer firstly. Then you need to pick n restaurants which appeare firstly in the List and the distance between the restaurant and the customer can't more than the longest distance in the n closest restaurants. Return their coordinates in the original order.

Example
Given : n = 2 , List = [[0,0],[1,1],[2,2]]
Return : [[0,0],[1,1]]
Given : n = 3,List = [[0,1],[1,2],[2,1],[1,0]]
Return :[[0,1],[1,2],[2,1]]
Notice
1.Coordinates in range [-1000,1000]
2.n>0
3.No same coordinates
*/

public class NumberOfRestaurants {
	
  /**
   * @param restaurant: 
   * @param n: 
   * @return: list
   */
  public List<List<Integer>> nearestRestaurant(List<List<Integer>> restaurant, int n) {
    // Base case, important: could have restaurant.size < n;
    if (restaurant == null || restaurant.size() < n || restaurant.size() == 0) {
      return new ArrayList<>();
    }
    // List<Integer> pair: pair.get(0) is x, pair.get(1) is y.
    List<List<Integer>> res = new ArrayList<>();
    int[] dists = new int[restaurant.size()];
    for (int i = 0; i < dists.length; i++) {
      List<Integer> pair = restaurant.get(i);
      dists[i] = dist(pair);
    }
    Arrays.sort(dists);
    // nth dist is the max dist we can have. Add all smaller ones to reslist.
    int limitDist = dists[n - 1];
    for (List<Integer> pair : restaurant) {
      if (dist(pair) <= limitDist) {
        res.add(pair);
      }
    }
    return res;
  }
  
  public int dist(List<Integer> pair) {
    // calc point dist from (0,0); sqrt(x^2 + y^2);
    // List<Integer> pair: pair.get(0) is x, pair.get(1) is y.
    return pair.get(0) * pair.get(0) + pair.get(1) * pair.get(1);
  }
}