/**
Bank System

http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=304012

design 一个 bank system, 跟地里之前的面经一样,要实现一个 deposite(id, timestamp, amount),
withdraw(id, timestamp, amount), check(id) --> return int;

另外还要实现一个 balance 的 function,
这个 function 跟地里的面经不太一样,要求在 logn 的时间复杂度内完成;
给的参数是 ID, startTime, endTime, 
但是要注意 startTime 是不包含在内的。比如说给你一个 startTime 0, 这个时间
点下一个时间是 10 的话,你算 balance 的时间段应该是从 10 开始,而不是从 0 开始。

另外如果 startTime 是负数的话,那么 startTime 就从 0 开始算;这题之前在面经上看到过,

看到的做法是用一个 map 来记录 timestamp 与 amount 的之间的对应关系,但是这样有一个问题便是 hashmap
中的元素是无序的,所以如果你直接用 hashmap 的话,你得事先排序,这样时间复杂度就不是
log 级别了。

因为准备的时间有效,当时看面经的时候没有考虑到这点,其实这里应该另外用一个
Map<id, List<timestamp>>来存属于那个用户的时间线,这里 suppose 用户交易的时间是顺序的
(跟面试官确认下),所以这样我们存的 list 就是有序的,就不需要额外的排序了,直接用 binary
search 就好了;

写到后面发现了这个问题,马上改 code,但还是没来得及,没写完。。。哎,还是准备不到位了
*/

import java.io.*;
import java.util.*;
import java.sql.Timestamp;

public class BankSystem {

	// id -> amount;
	Map<Integer, Integer> accounts = new HashMap<>();
	// id -> (time -> amount); the +amount or -amount;
	Map<Integer, Map<Timestamp, Integer>> records = new HashMap<>();
	// ASC order of time of transactions.
	Map<Integer, List<Timestamp>> timeLines = new HashMap<>();
	
	public void deposite(int id, Timestamp timestamp, int amount) {
		// Do I need to check if id account exists? What if id not exist?
		// Assume create/put new id
		// update account balance + amount.
		int newAmount = accounts.getOrDefault(id, 0) + amount;
		accounts.put(id, newAmount);
		// record time deposite
		// if no record yet.
		records.putIfAbsent(id, new HashMap<>());
		records.get(id).put(timestamp, newAmount);
		// add to timestamp List, Assume each deposite is in ASC time order.
		timeLines.putIfAbsent(id, new ArrayList<>());
		timeLines.get(id).add(timestamp);
	}

	public void withdraw(int id, Timestamp timestamp, int amount) {
		// Do I need to check if id account exists? What if id not exist?
		// Assume create/put new id
		// update account balance - amount.
		int newAmount = accounts.getOrDefault(id, 0) - amount;
		// What to do with -# balance? Assume bank allow that.
		accounts.put(id, newAmount);
		// record time withdrawal, -# of amount.
		// if no record yet.
		records.putIfAbsent(id, new HashMap<>());
		records.get(id).put(timestamp, newAmount);
		// add to timestamp List, Assume each deposite is in ASC time order.
		timeLines.putIfAbsent(id, new ArrayList<>());
		timeLines.get(id).add(timestamp);
	}

	public int check(int id) {
		if (!accounts.containsKey(id)) {
			System.out.println("Not Found " + id);
			return -1;
		}
		// return amount left, if 
		return accounts.get(id);
	}

	/**
	 * check balance diff +amount, -amount from exclusive (start, end] inclusive end;
	 * must be O(LogN) time; Binary Search.
	 */
	public int balance(int id, Timestamp startTime, Timestamp endTime) {
		if (!accounts.containsKey(id) || !records.containsKey(id)) {
			System.out.println("Not Found " + id);
			return -1;
		}
		// check outOfRange if startTime is later or = last transaction.
		List<Timestamp> times = timeLines.get(id);
		if (times.get(times.size() - 1).before(startTime) ||
	 			times.get(times.size() - 1).equals(startTime) ||
	 			times.get(0).after(endTime)) {
			System.out.println("out of range");
			return -1;
		}
		// check balance diff +amount, -amount from exclusive (start, end] inclusive end;
		// find startTime index using binarySearch.
		int startIndex = searchStart(times, startTime, 0, times.size() - 1);
		int endIndex = searchEnd(times, endTime, 0, times.size() - 1);
		// get Time
		Timestamp start = times.get(startIndex);
		Timestamp end = times.get(endIndex);
		// get Amount diff
		int startAmount = records.get(id).get(start);
		int endAmount = records.get(id).get(end);
		System.out.println(startIndex);
		System.out.println(startAmount + " " + endAmount);

		return Math.abs(startAmount - endAmount);
	}

	/*
	1,2,3,4,6,7,8; find 5; 
	l=1, r=8: mid=4; 
	l=6, r=8: mid=7;
	l=6, r=6;
	return 6; L
	
	1,2,3,6,7,8; find 10;
	l=1, r=8: mid=3;
	l=6, r=8: mid=7;
	l=8, r=8: L=8 ret L8;

	1,2,3,6,7,9; find 8;
	l=1, r=9; m=3;
	l=6, r=9; m=7;
	l=9, r=9;

	1,2,3,6,7,9; find 0;
	l=1, r=9; m=3;
	l=1, r=2; m=1;
	l=1, r=1; m=1;
	l=1, r=0; ret L1;
	*/
	public int searchStart(List<Timestamp> times, Timestamp target, int start, int end) {
		// check to see if == found.
		while (start <= end) {
			int mid = start + (end - start) / 2;
			Timestamp cur = times.get(mid);
			if (cur.equals(target)) {
				// mid for excluding startTime.
				return mid + 1;
			} else if (cur.before(target)) {
				// cur is before tar, tar is on right.
				// inclusive mid
				start = mid + 1;
			} else {
				// cur after target, tar on left
				end = mid - 1;
			}
		}
		// no == found. no exact time = target, return the next available time.
		// left > target by this point start=mid+1. start is at that point
		return start;
	}

	/**
	1,2,3,6,7,9; find 8;
	l=1, r=9; m=3;
	l=3, r=9; m=6;
	l=6, r=9; m=7;
	l=7, r=9; 2 left, 8< 9 choose smaller. L7

	1,2,3,6,7,9; find 2;
	l=1, r=9; m=3;
	l=1, r=2; 2 left 2=2, ret R2;
	
	1,2,3,6,7,9; find 10;
	l=1, r=9; m=3;
	l=3, r=9; m=6;
	l=6, r=9; m=7;
	l=7, r=9; 2 left, 10 > 9; return R9;

	*/
	public int searchEnd(List<Timestamp> times, Timestamp target, int start, int end) {
		// keep 2 left
		while (start + 1 < end) {
			// mid should
			int mid = start + (end - start) / 2;
			Timestamp cur = times.get(mid);
			if (cur.equals(target)) {
				// e
				return mid;
			} else if (cur.before(target)) {
				// cur is before tar, tar is on right.
				// inclusive mid, b/c we want the leftmost # if no match for endTime.
				start = mid;
			} else {
				// cur after target, tar on left
				end = mid - 1;
			}
		}
		// if right is = tar then right ret, if not we choose the left one as Last time.
		return (times.get(end).equals(target) || times.get(end).before(target)) ? end : start;
	}

	public static void main(String[] args) {
		BankSystem obj = new BankSystem();
		Timestamp[] times = new Timestamp[] {Timestamp.valueOf("2000-01-01 10:00:00.0"), Timestamp.valueOf("2000-01-02 10:00:00.0"), Timestamp.valueOf("2000-01-03 10:00:00.0"), Timestamp.valueOf("2000-01-04 10:00:00.0")};
		obj.deposite(1, times[0], 100);
		obj.withdraw(1, times[1], 50);
		System.out.println("check id 1: " + obj.check(1));
		obj.deposite(1, times[2], 150);
		obj.deposite(1, times[3], 100);
		System.out.println("check id 1: " + obj.check(1));
		System.out.println("check id 1 balance range: " + 
			obj.balance(1, Timestamp.valueOf("2000-01-01 10:00:00.0"), Timestamp.valueOf("2000-01-04 10:00:00.0")));
		System.out.println("check id 1 balance range2: " + 
			obj.balance(1, Timestamp.valueOf("2000-01-02 11:00:00.0"), Timestamp.valueOf("2000-01-03 11:00:00.0")));
		System.out.println("check id 1 balance range3: " + 
			obj.balance(1, Timestamp.valueOf("2000-01-01 9:00:00.0"), Timestamp.valueOf("2000-01-04 11:00:00.0")));

	}
}