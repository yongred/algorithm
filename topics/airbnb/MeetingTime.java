/**
Meeting Time

Input is a number of meetings (start_time, end_time)。 Output is a number of time intervals (start_time, end_time), where there is no meetings.

For exmaple, input is [[1, 3], [6, 7]], [[2, 4]], [[2, 3], [9, 12]] ]

output [[4, 6], [7, 9]]

Leetcode 相似问题: Leetcode 252/253 Meeting Rooms I/II
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
nonBusy: 2, 1, 0, 1, 2, 3, 2, 3, 2, 3
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
*/

import java.io.*;
import java.util.*;

public class MeetingTime {

	class Range {
		int start;
		int end;

		Range(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	class Point implements Comparable<Point> {
		int time;
		boolean isStart;

		Point(int time, boolean isStart) {
			this.time = time;
			this.isStart = isStart;
		}

		@Override
		public int compareTo(Point point) {
			// ASC, -# smaller in front.
			if (this.time != point.time) {
				return this.time - point.time;
			} else {
				// If this is start time, place in front.
				return (this.isStart) ? -1 : 1;
			}
		}
	}
	
	/**
	 * Solution: Divide range into points, sort the points. Count start times, cancel out with end times.
	 * Time: O(nLgn);
	 * Space: O(n)
	 */ 
	public List<Range> findIntervals(List<List<Range>> employees) {
		List<Range> res = new ArrayList<>();
		List<Point> points = new ArrayList<>();
		// loop ranges, make them into points.
		for (List<Range> rangeList : employees) {
			// get start and end time.
			for (Range range : rangeList) {
				points.add(new Point(range.start, true));
				points.add(new Point(range.end, false));
			}
		}
		// Sort the points
		Collections.sort(points);
		// find intervals
		// count how many busy start times, cancel out start w/ end time.
		int busyEmployees = 0;
		// find a avaliable time for new interval.
		int avaliableStart = -1;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).isStart) {
				busyEmployees++;
				// Not first start time, but 0 busy before this. We found a available time interval.
				if (i > 0 && busyEmployees == 1) {
					res.add(new Range(avaliableStart, points.get(i).time));
					avaliableStart = -1;
				}
			} else {
				// end time
				busyEmployees--;
				// Not last end time, no more busy, finished/cancel out all start time.
				if (i < points.size() - 1 && busyEmployees == 0) {
					avaliableStart = points.get(i).time;
				}
			}
		}
		return res;
	}

	/**
	 * Follow up: Intervals for atleast k employee is free;
	 * Solution: free >= k; busy <= n-k;
	 */
	public List<Range> findIntervalsAtleastK(List<List<Range>> employees, int k) {
		List<Range> res = new ArrayList<>();
		List<Point> points = new ArrayList<>();
		// loop ranges, make them into points.
		for (List<Range> rangeList : employees) {
			// get start and end time.
			for (Range range : rangeList) {
				points.add(new Point(range.start, true));
				points.add(new Point(range.end, false));
			}
		}
		// Sort the points
		Collections.sort(points);
		// find intervals
		// count how many busy start times, cancel out start w/ end time.
		int busyEmployees = 0;
		// find a avaliable time for new interval.
		int avaliableStart = -1;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).isStart) {
				busyEmployees++;
				// check if >= k employees is free; meaning busy <= size-k;
				// startInterval is set, means have atleast k free. 
				// And this 1 busyEmployee just start meeting. Free = k-1, end of cur interval
				if (avaliableStart != -1 && busyEmployees == employees.size() - k + 1) {
					res.add(new Range(avaliableStart, points.get(i).time));
					// reset avaliableStart for next interval
					avaliableStart = -1;
				} else if (avaliableStart == -1 && busyEmployees <= employees.size() - k) {
					// check if same start time on next point, meaning another employee taking a same start time meeting.
					if (i + 1 < points.size() && points.get(i + 1).isStart &&
							points.get(i + 1).time == points.get(i).time) {
						continue;
					} else {
						// next is not a same start time. We assign cur point as new intervalStart
						avaliableStart = points.get(i).time;
					}
				}
			} else {
				// end time
				busyEmployees--;
				// check for new available start
				if (avaliableStart == -1 && busyEmployees == employees.size() - k) {
					// no new interval started yet, and this employee is just free from a meeting, free = k;
					avaliableStart = points.get(i).time;
				} else if (i == points.size() - 1 && avaliableStart != -1) {
					// if this is last point endtime. And a intervalStart has not yet closed.
					// add last endtime as avaliable point
					res.add(new Range(avaliableStart, points.get(i).time));
				}
			}
		}
		return res;
	}


	public static void main(String[] args) {
		MeetingTime obj = new MeetingTime();
		List<List<MeetingTime.Range>> employees = new ArrayList<>();
		List<MeetingTime.Range> emp1 = new ArrayList<>();
		emp1.add(obj.new Range(1, 3));
		emp1.add(obj.new Range(6, 7));
		List<MeetingTime.Range> emp2 = new ArrayList<>();
		emp2.add(obj.new Range(2, 4));
		List<MeetingTime.Range> emp3 = new ArrayList<>();
		emp3.add(obj.new Range(2, 3));
		emp3.add(obj.new Range(9, 12));
		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);

		List<MeetingTime.Range> res = obj.findIntervals(employees);
		res.forEach(range -> {
			System.out.println(range.start + " " + range.end);
		});

		int k = 2;
		List<MeetingTime.Range> res2 = obj.findIntervalsAtleastK(employees, k);
		System.out.println("with k " + k);
		res2.forEach(range -> {
			System.out.println(range.start + " " + range.end);
		});

	}
}