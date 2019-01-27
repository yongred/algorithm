/**
Menu Combination Sum

http://www.1point3acres.com/bbs/thread-289032-1-1.html

给你一个菜单,要你输出一个金额所有能点的不同组 合。要求用完所有钱。

Given a menu (list of items prices), find all possible combinations of items that sum a particular value K.
(A variation of the typical 2sum/Nsum questions).
Return the coins combination with the minimum number of coins. Time complexity O(MN), where M is
the target value and N is the number of distinct coins. Space complexity O(M).

不过这里着重强调这里有一个小坑!!! 之前帖子的人没见人提过,是都没掉里面么??? 就是
说 有一个 interface (vector prices, double total) 这里无论你是使用何种algo,都逃脱不了的是累加
之后并比较。
这里因为是 double类型的累加, 所以在 register 处理的时候会因为为了保证对其,
而对二进制进行变化(当然不是每个double都会出现这种情况)。 这也是double/float 类型在操作
的过程中的问题。没办法保证精度(有特殊的方法进行处理,不过这里不需要考虑) 

举个栗子:
double a = 1.5, b = 1.1213; double c = 2.6213; 这个时候 a+b-c 是不等于0的
具体的理解请会议computer arch。 
哎。。。其实当时这里想到了这个原因,不过因为最近收到了一个感觉还不错的offer 
面试的时候就变的懒了 这里就没问。 当然也没想到这个小坑竟然把我挂了。。。 

正确的过程是,面对这种需要考虑精度的问题 在一开始就要问面试官,精度要求多少 然后在最后求解的
时候 给出这样的判断方式 (abs(a+b-c) < 0.0000001) 之类的。 或是 干脆对每个数字都乘以一个
大的数,比如都乘以10000。 不过注意不要溢出。。。
*/

/**
Questions:
- Are prices all unique?
Assume dup prices;

- Are combos all unique? Does order matters: [2.1, 3.2] and [3.2, 2.1];
Assume unique, order don't matter; so just one [2.1, 3.2];

- Are prices all to the tenth decimals like: 2.10, 2.33, 5.31; And is K also to the tenth;
Assume yes;

- Can we choose a Menu price multiple times like: [2,1] tar=4.2; [2.1, 2.1]; 2 times?
Assume No; Only 1 time per price;

- What is the Precision for the Double comparison? 
Ex: a = 1.5, b = 1.1213; a+b= 2.6212999999999997;
Assume tenth decimal 9.99;

- Are there negative #s;
Assume no -#s;

-----
Solution: Convert all prices like 9.99 -> cents Ints val like 999; Then backtracking.
[0.22, 10.78, 5.50, 5.50] tar=11; 
Sort -> [0.22, 5.50, 5.50, 10.78]
cover to cents integers [22, 550, 550, 1078];

*/

import java.util.*;
import java.io.*;

public class MenuCombinationSum {

	static List<List<Double>> res = new ArrayList<>();
	
	public static List<List<Double>> findCombos(double[] prices, double target) {
		if (prices == null || prices.length == 0) {
			return new ArrayList<>();
		}
		// Sort prices
		Arrays.sort(prices);
		// convert target price to cents integer. Use paramthesis, cast precedent < *;
		int centsTarget = (int) (target * 100);
		// cents vals integers array
		int[] cents = new int[prices.length];
		for (int i = 0; i < prices.length; i++) {
			// Use paramthesis, cast precedent < *;
			cents[i] = (int) (prices[i] * 100);
		}
		// backtrack
		helper(prices, cents, centsTarget, new ArrayList<>(), 0);
		return res;
	}

	public static void helper(double[] prices, int[] cents, int centsTarget, List<Double> list, int start) {
		if (centsTarget < 0) {
			return;
		}
		if (centsTarget == 0) {
			res.add(new ArrayList<>(list));
			return;
		}
		// loop
		for (int i = start; i < prices.length; i++) {
			// check duplicates
			if (i > start && cents[i] == cents[i - 1]) {
				continue;
			}
			// choose cur price
			list.add(prices[i]);
			// go to next position. Use cur centsTar - centsVal= rest of centsTar;
			helper(prices, cents, centsTarget - cents[i], list, i + 1);
			list.remove(list.size() - 1);
		}
	}

	public static void main(String[] args) {
		double[] prices = new double[] {0.22, 5.50, 10.78, 5.50, 5.50};
		double target = 11.00;
		List<List<Double>> res = findCombos(prices, target);
		res.forEach(list -> {
			list.forEach(price -> {
				System.out.print(price + ", ");
			});
			System.out.println();
		});
	}
}