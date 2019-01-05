/**
613. High Five
There are two properties in the node student id and scores, to ensure that each student will have at least 5 points, find the average of 5 highest scores for each person.

Example
Given results = [[1,91],[1,92],[2,93],[2,99],[2,98],[2,97],[1,60],[1,58],[2,100],[1,61]]

Return 
*/

/**
Solution: Using minHeap. Store 5 scores in minheap for each id. Compare and replace minimum, until only 5 highest scores remain.

How to Arrive:
* Question asked for average of top 5 scores for each person.
* So we can use minheap, and maintain a size of 5.
*  compare and replace score that is higher than minScore in heap. The remaining are highest 5.
Ex: 1,2,3,6,7,4,5
minheap: 1,2,3,6,7;
4 > 1 replace. [2,3,4,6,7]
5 > 2 replace. [3,4,5,6,7] done.
* We need 5 records for each student, so we need a Map<id, records>;
* After getting all top five scores for each id, we calc the average by sum them and divide by 5;
* Time: O(n); Loop each scores T(n), heapify size of 5, lg(5) constant. T(n) * log5; = O(n);
* Space: O(n);

*/

/**
 * Definition for a Record
 * class Record {
 *     public int id, score;
 *     public Record(int id, int score){
 *         this.id = id;
 *         this.score = score;
 *     }
 * }
 */
public class HighFive {

  /**
   * @param results a list of <student_id, score>
   * @return find the average of 5 highest scores for each person
   * Map<Integer, Double> (student_id, average_score)
   * 
   * Solution: Using minHeap. Store 5 scores in minheap for each id. Compare and replace minimum, until only 5 highest scores remain.
   * Time: O(n); Loop each scores T(n), heapify size of 5, lg(5) constant. T(n) * log5; = O(n);
	 * Space: O(n);
   */
  public Map<Integer, Double> highFive(Record[] results) {
    Map<Integer, Double> res = new HashMap<>();
    // Map of MinHeap 5 in size
    Map<Integer, PriorityQueue<Record>> topFives = new HashMap<>();
    for (Record rec : results) {
      // check have heap.
      if (!topFives.containsKey(rec.id)) {
        topFives.put(rec.id, new PriorityQueue<Record>(5, 
            (Record a, Record b) -> {
              return a.score - b.score;
            }
        ));
      }
      // add id and heap scores
      if (topFives.get(rec.id).size() < 5) {
        topFives.get(rec.id).add(rec);
      } else {
        // size = 5. limit, we replace lowest score with score higher.
        // so the remaining 5 is the highest scores.
        Comparator comparator = topFives.get(rec.id).comparator();
        // check if rec score > lowest rec of curID.
        if (comparator.compare(rec, topFives.get(rec.id).peek()) > 0) {
          topFives.get(rec.id).poll();
          topFives.get(rec.id).add(rec);
        }
      }
    }
    // get all min 5s from ids. Calc their average and store in res.
    for (PriorityQueue<Record> minheap : topFives.values()) {
      // get the sum, / 5;
      double sum = 0;
      int id = minheap.peek().id;
      while (!minheap.isEmpty()) {
        Record rec = minheap.poll();
        sum += rec.score;
      }
      double average = sum / 5;
      // save it in res.
      res.put(id, average);
    }
    return res;
  }
    
    
    
}