/**
Meeting Time

Input is a number of meetings (start_time, end_time)。 Output is a number of time intervals (start_time, end_time), where there is no meetings.

For exmaple, input is [[1, 3], [6, 7]], [[2, 4]], [[2, 3], [9, 12]] ]

output [[4, 6], [7, 9]]

Leetcode 相似问题: Leetcode 759 Employee Free Time
Leetcode 252/253 Meeting Rooms I/II
http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=146537

给一组 meetings(每个 meeting 由 start 和 end 时间组成)。求出在所有输入 meeting 时间段内没有
会议,也就是空闲的时间段。每个 subarray 都已经 sort 好。
举例:
[[1, 3], [6, 7]], [[2, 4]], [[2, 3], [9, 12]]
返回
[[4, 6], [7, 9]]

这题最简单的方法就是把所有区间都拆成两个点,然后排序,然后扫,每次碰到一个点如果是
左端点就把 busy_employees 加 1,否则减 1,等到每次 busy_employees 为 0 时就是一个新的区
间。这样复杂度 O(MlogM),M 是总共区间数。

follow up: 求不少于 k 个员工空闲的时间段(改一下 check count 的条件就可以了)
*/

/**
Solution: Follow up;
Ex: [[1, 3], [6, 7]], [[2, 4]], [[2, 3], [9, 12]]  K=2;
Points: 1s, 2s, 2s, 3e, 3e, 4e, 6s, 7e, 9s, 12e;
	busy:  1, 2, 3, 2, 1, 0, 1, 0, 1, 0
	free:  2, 1, 0, 1, 2, 3, 2, 3, 2, 3
Start 1: (2=2), 2 free can open interval, check next for same startTime, 2s != 1s, open new Start interval [1, ]
Start 2: (1< 2) open interval [1, ] closed with 2 = [1,2]
Start 2: (0< 2) no open interval;
end 3: (1< 2) 1 free, not enough to open new interval.
end 3: (2=2) 2 free, can open, [3, ];
end 4: (3> 2) open exist [3, ] skip to next;
start 6: (2=2) open exist [3, ], 2 free means this time slot still works, skip, [3, ] still open.
end 7: (3> 2) open exist [3, ], still open b/c free slots 3 > k;
start 9: (2=2) open exist [3, ], still open b/c free slots 2 = k;
end 12: (3> 2) open exist [3, ], This is Last endTime Point, close the still open [3, ] with 12; [3,12];
DONE;

Res: [1, 2], [3, 12]

* Time: O(nlgn)
* Space: O(n)

---------

Solution: PriorityQueue;
Ex: [[1, 4], [6, 7]], [[2, 5]], [[2, 3], [9, 12]]

Res: [5,6] [7,9]
pq: [1,4] [2,3] [2,5] [6,7] [9,12];
[1,4] [2,3] 4> 2, no free, 4> 3 [1,4] ends later so we want to know when is he free.
[1,4] [2,5] 4> 2, no free, 5> 4 [2,5] ends later;
[1,5] [6,7] 5< 6 Yes free, add to res;  [6,7] ends later.
[6,7] [9,12] 7< 12 Yes free, 7< 9 add to res;

*/

import java.io.*;
import java.util.*;

public class MeetingTime {

	class Interval {
		int start;
		int end;

		Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	class Point {
		int time;
		boolean isStart;

		Point(int time, boolean isStart) {
			this.time = time;
			this.isStart = isStart;
		}

	}
	
	/**
	 * Solution: Divide interval into points, sort the points. Count start times, cancel out with end times.
	 * Time: O(nLgn);
	 * Space: O(n)
	 */ 
	public List<Interval> findIntervals(List<List<Interval>> employees) {
    List<Interval> res = new ArrayList<>();
    List<Point> points = new ArrayList<>();
    // [ [[1, 3], [6, 7]], [[2, 4]], [[2, 3], [9, 12]] ]
    for (List<Interval> employee : employees) {
      for (Interval inv : employee) {
        points.add(new Point(inv.start, true));
        points.add(new Point(inv.end, false));
      }
    }
    // Sort
    Collections.sort(points, (a, b) -> {
      return (a.time == b.time) ? (a.isStart ? -1 : 1) : a.time - b.time;
    });
    // total employees
    int n = employees.size();
    int frees = n; // free employees at this time.
    int availableStart = -1; // startTime of all free.
    
    for (int i = 0; i < points.size(); i++) {
      Point cur = points.get(i);
      if (cur.isStart) {
        // 1 more employee become busy. Start time.
        // check available, if endTime -> curStart is all free.
        if (availableStart != -1 && frees == n) {
          res.add(new Interval(availableStart, cur.time));
        }
        frees--;
      } else {
        // 1 more employee free. End time.
        // if all free after this employee.
        if (frees + 1 >= n) {
          availableStart = cur.time;
        }
        frees++;
      }
    }
    
    return res;
  }

	/**
	 * Solution: PriorityQueue.
	 * Time: O(nlgn)
	 */
	public List<Interval> findIntervals2(List<List<Interval>> employees) {
		List<Interval> res = new ArrayList<>();
		// ASC startTime heap.
		PriorityQueue<Interval> pq = new PriorityQueue<>((a, b) -> {
			return a.start - b.start;
		});
		// add employee intervals
		for (List<Interval> list : employees) {
			pq.addAll(list);
		}

		Interval prev = pq.poll();
		while (!pq.isEmpty()) {
			// prev endtime < cur startTime, found interval
			if (prev.end < pq.peek().start) {
				res.add(new Interval(prev.end, pq.peek().start));
				// > end becomes next prev.
				prev = pq.poll();
			} else {
				// whoever has the later endtime we need to find out when is he free
				prev = (prev.end > pq.peek().end) ? prev : pq.peek();
				pq.poll();
			}
		}
		// Done;
		return res;
	}

	/**
   * Follow up: Intervals for atleast k employee is free;
   * Solution: free >= k; busy <= n-k;
   */
  public List<Interval> findIntervalsAtleastK(List<List<Interval>> employees,
      int k) {
    List<Interval> res = new ArrayList<>();
    List<Point> points = new ArrayList<>();
    // loop intervals, make them into points.
    for (List<Interval> employee : employees) {
      // get start and end time.
      for (Interval inv : employee) {
        points.add(new Point(inv.start, true));
        points.add(new Point(inv.end, false));
      }
    }
    // Sort the points
    Collections.sort(points, (a, b) -> {
      return (a.time == b.time) ? (a.isStart ? -1 : 1) : a.time - b.time;
    });
    
    int n = employees.size();
    int frees = n;
    int availableStart = -1;
    // [1, 3], [2, 4], [2, 3], [6, 7], [9, 12]
    // 1s, 2s, 2s, 3e, 3e, 4e, 6s, 7e, 9s, 12e;
    for (int i = 0; i < points.size(); i++) {
      Point cur = points.get(i);
      if (cur.isStart) {
        // start, 1 less free.
        // check if k employees is free; 
        // free-1 would close the interval of k frees.
        if (availableStart != -1 && frees == k) {
          res.add(new Interval(availableStart, cur.time));
          availableStart = -1;
        } else if (availableStart == -1 && frees - 1 >= k) {
          // check if same start time on next point, 
          // ex: [[1, 3], [6, 7]], [[2, 4]], [[2, 3], [9, 12]] k=2;
          // 1->2 free=2; but if change [2,3]->[1,3] free=1;
          // So we have to check if next start == curTime. inorder to open
          if (i + 1 < points.size() && points.get(i + 1).isStart &&
             cur.time == points.get(i + 1).time) {
            continue;
          }
          // next is not a startTime == cur.time; Open a availableStart.
          availableStart = cur.time;
        }
        frees--;
      } else {
        // end, free+1
        // check if no availableStart opened, and frees+1 >= k
        if (availableStart == -1 && frees + 1 >= k) {
          availableStart = cur.time;
        } else if (i == points.size() - 1 && availableStart != -1) {
          // if this is last point endtime. And a intervalStart has not yet closed.
          // add last endtime as end of avaliable point
          // ex: [1,2] [3,4] [5,6] k=2 n=3; 1->6 all have atleast 2 free;
          // 1s 2e 3s 4e 5s 6e; free=3-1=2, open [1, ]; not closed until 6;
          res.add(new Interval(availableStart, cur.time));
        }
        frees++;
      }
      
    }
    
    return res;
  }


	public static void main(String[] args) {
		MeetingTime obj = new MeetingTime();
		List<List<MeetingTime.Interval>> employees = new ArrayList<>();
		List<MeetingTime.Interval> emp1 = new ArrayList<>();
		emp1.add(obj.new Interval(1, 3));
		emp1.add(obj.new Interval(6, 7));
		List<MeetingTime.Interval> emp2 = new ArrayList<>();
		emp2.add(obj.new Interval(2, 4));
		List<MeetingTime.Interval> emp3 = new ArrayList<>();
		emp3.add(obj.new Interval(2, 3));
		emp3.add(obj.new Interval(9, 12));
		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);

		List<MeetingTime.Interval> res = obj.findIntervals(employees);
		res.forEach(interval -> {
			System.out.println(interval.start + " " + interval.end);
		});
		System.out.println("PriorityQueue Solution ------");
		res = obj.findIntervals2(employees);
		res.forEach(interval -> {
			System.out.println(interval.start + " " + interval.end);
		});

		int k = 2;
		List<MeetingTime.Interval> res2 = obj.findIntervalsAtleastK(employees, k);
		System.out.println("with k " + k);
		res2.forEach(interval -> {
			System.out.println(interval.start + " " + interval.end);
		});

	}
}