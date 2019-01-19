/**
Display Page (Pagination)

Given an array of CSV strings representing search results, output results sorted by a score initially. A given host may have several listings that show up in these results. Suppose we want to show 12 results per page, but we don’t want the same host to dominate the results.

Write a function that will reorder the list so that a host shows up at most once on a page if possible, but otherwise preserves the ordering. Your program should return the new array and print out the results in blocks representing the pages.

Given an array of csv strings, output results separated by a blank line.

5
13
1,28,310.6,SF
4,5,204.1,SF
20,7,203.2,Oakland
6,8,202.2,SF
6,10,199.1,SF
1,16,190.4,SF
6,29,185.2,SF
7,20,180.1,SF
6,21,162.1,SF
2,18,161.2,SF
2,30,149.1,SF
3,76,146.2,SF
2,14,141.1,San Jose

以上是一个 Sample 输入,和希望的输出,1,28,100.3,Paris 代表 Host ID, List ID, Points, City. 这是
Airbnb 根据用户搜索条件得出的一些 list,然后我们要分页,第一行的 5 代表每一页最多展示 5 个
list,13 应该是代表有 13 个 List.所以我们是要分成 3 页。规则是:每一页最多展示一个 host 的
list,但是如果再没有其他 host 的 list 可以展示了,就按照原有的顺序填补就可(根据 Points,也
就是排名)。应得到的输出:

希望输出:
1,28,310.6,SF
4,5,204.1,SF
20,7,203.2,Oakland
6,8,202.2,SF
7,20,180.1,SF

6,10,199.1,SF
1,16,190.4,SF
2,18,161.2,SF
3,76,146.2,SF
6,29,185.2,SF -- 这时不得不重复了,从原有队列拉出第一个

6,21,162.1,SF
2,30,149.1,SF
2,14,141.1,San Jose

先找不同 host ID 的 post,填到一页里,如果找不到不同 host ID 的 post,就从开头顺序拉取,填满
为止。就这样简单,不要想太复杂!
比较直接的做法可以把这些 List 全部放在一个 LinkedList 里,从头到尾扫 另一个 HashSet 记录每一
页的重复值,重复就下一个,没重复就 Remove from the list. 如果实在找不到不重的,就从头拉一
个即可。

------
1.好多人说看不懂题
做题界面讲的非常详细，还举了几个例子，如果你面经读不懂题的话，做题的时候读也是肯定能读懂的。比如1,2,3,1,1,2,2，每一页要填5个数，第一页不重复的只能填1,2,3，没填满怎么办（怎么办），那就从剩余的数里面依次放进去两个补满，第一页就变成 1,2,3,1,1 就好了。

2. 输入是String[] ，输出是String[]，每一页中间用个空格String隔开。比如{"第一页", " ", "第二页"}

3. Iterator的remove是完全可以用的。之前有个妹子的帖子里说remove()不能用，搞得人心惶惶，我也一直担心不能remove()。后来大概猜想了一下，妹子可能是直接Arrays.asList(input)完就定义iterator了，这种情况下是肯定不能remove的（不知道自己表述清楚没有，也不知道自己猜的对不对 =。=）。Hackerrank编译器本身没问题，不放心的自己先去test里面试试。

一共11个test cases，全都公开可以看见。限时一共小时做完。HR给了7天时间完成OA，但是强调说大多数candidates都是72小时内做完的。之前准备另一家面试，所以96个小时才做，希望没影响。


补充内容 (2017-10-24 02:38):
刚刚接到follow up的电话，先让我讲一下思路，然后问了时间复杂度，答O(numOfPage * n)面试官不满意，面试官说应该是n + n - m + n - 2m...最后是O（n^2）。然后问我当worst case的情况下，如何优化数据结构
*/

/**
pageSize = 5;
inputs: ["1, a..", "2, a..", "3, a..", "2, b..", "1, b..", "4, a..", "2, c.."]
outputs: ["1, a..", "2, a..", "3, a..", "4, a..", "2, b..", " " (pageSplit),
		"1, b..", "2, c.."]
*/

import java.util.*;
import java.io.*;

public class DisplayPage {

	/**
	 * Solution 2: another verision, using only 1 while loop. Less repeating codes.
	 * Time; O(n^2); worst case is only one hosts for all inputs, so we loop through all elms dispites the pagesize.
	 * Space: O(n)
	 */
	public static List<String> displayPages2(List<String> input, int pageSize) {
		// Res list
		List<String> res = new ArrayList<>();
		// remove non-duplicates hosts, need a hashSet
		Set<String> set = new HashSet<>();
		// use iterator to remove from inputList, put removed into res curPage.
		// use " " for end of page.
		int curPageSize = 0;
		Iterator<String> iter = input.iterator();
		// flag for when iterator reached to last elm, but not enough unique elms in page.
		boolean addDups = false;
		// remove elm util inputList is empty.
		while (iter.hasNext()) {
			String str = iter.next();
			String hostId = str.split(",")[0];
			// add str if not contain in hashSet, or if iteration ended but page not filled completely.
			if (!set.contains(hostId) || addDups) {
				// remove from inputList, add elm to res.
				iter.remove();
				res.add(str);
				set.add(hostId);
				curPageSize++;
			}
			// check if a page finished.
			if (curPageSize == pageSize) {
				// add space, if not last elm
				if (!input.isEmpty()) {
					res.add(" ");
				}
				// reset, set, pageSize, and iterator (start from beginning)
				set.clear();
				curPageSize = 0;
				iter = input.iterator();
				// starting new page, no need to addDups yet.
				addDups = false;
			}
			// iter ended, but inputList not empty, means there are only duplicates left.
			// fill curPage with duplicate hosts.
			if (!iter.hasNext()) {
				iter = input.iterator();
				addDups = true;
			}
		}
		return res;
	}

	/**
	 * Solution 1: Using Iterator and hashSet; fill page by page, remove 1 by 1 util inputList is empty
	 * Time; O(n^2); worst case is only one hosts for all inputs, so we loop through all elms dispites the pagesize.
	 * Space: O(n)
	 */
	public static List<String> displayPages(List<String> input, int pageSize) {
		// Res list
		List<String> res = new ArrayList<>();
		// remove non-duplicates hosts, need a hashSet
		Set<String> set = new HashSet<>();
		// use iterator to remove from inputList, put removed into res curPage.
		// use " " for end of page.
		int curPageSize = 0;
		Iterator<String> iter = input.iterator();
		// remove elms, until all inputs added to res.
		while (!input.isEmpty()) {
			// fill cur page
			while (iter.hasNext() && curPageSize < pageSize) {
				String str = iter.next();
				// get the hostId, 1st subStr before ','
				String hostId = str.split(",")[0];
				// check hashSet for hostId, ignore hostId contains in curPage
				if (!set.contains(hostId)) {
					// unique in this page.
					// remove curStr from inputList, add to res curPage.
					iter.remove();
					res.add(str);
					// record hostId.
					set.add(hostId);
					curPageSize++;
				}
			}
			// check if cur page is not completely filled and inputList not empty,
			// means: not enough unique hosts in inputList. Only duplicates hosts left.
			if (curPageSize < pageSize && !input.isEmpty()) {
				// reset the iterator, get the next duplicated hosts in curPage.
				iter = input.iterator();
				while (iter.hasNext() && curPageSize < pageSize) {
					// fill this page with duplicated hosts in order.
					String str = iter.next();
					// get the hostId, 1st subStr before ','
					String hostId = str.split(",")[0];
					// remove curStr from inputList, add to res curPage.
					iter.remove();
					res.add(str);
					curPageSize++;
				}
			}
			// this page complete, and still page left, go next page. Separated by " ";
			if (!input.isEmpty()) {
				res.add(" ");
			}
			// reset pageSize and hashSet for newPage.
			curPageSize = 0;
			set.clear();
			// reset iterator
			iter = input.iterator();
		}

		return res;
	}


	public static void main(String[] args) {
		String[] arr = new String[] {"1,a..", "2,a..", "3,a..", "2,b..", "1,b..", "4,a..", "2,c.."};
		List<String> input = new ArrayList<>(Arrays.asList(arr));
		int pageSize = 5;
		List<String> res = displayPages(input, pageSize);
		System.out.println( Arrays.toString(res.toArray(new String[res.size()])) );

		List<String> input2 = new ArrayList<>(Arrays.asList(arr));
		List<String> res2 = displayPages2(input2, pageSize);
		System.out.println( Arrays.toString(res2.toArray(new String[res2.size()])) );
	}

}