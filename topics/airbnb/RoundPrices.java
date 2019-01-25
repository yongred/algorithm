/**
Round Prices

Given an array of numbers A = [x1, x2, ..., xn] and T = Round(x1+x2+... +xn). We want to find a way to round each element in A such that after rounding we get a new array B = [y1, y2, ...., yn] such that y1+y2+...+yn = T where yi = Floor(xi) or Ceil(xi), ceiling or floor of xi.

We also want to minimize sum |x_i-y_i|

Round Prices
http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=146539
公司 list 价格分成好几个部分,但是都是整数,如果在美金是整数,到了欧洲的网页显示汇率转
换之后就变成了 floating point,然后要 round 成整数,但是全部加起来 round,和单独 round 再加
起来,结果会不一样
# base price 100 => 131.13 => 131
# cleaning fee 20 => 26.23 => 26
# service fee 10 => 13.54 => 14
# tax 5 => 6.5 => 7
# => 177.4E => 178E
# sum 135$ => 178.93E => 179E

那么问题就来了,给个 input list of floating points, 要求 output list of integers, 满足以下两个
constraint, 就是和跟 Round(x1+x2+... +xn)的结果一样,但是 minimize output 和 input 的绝对值差之和

#Input: A = [x1, x2, ..., xn]
# Sum T = Round(x1+x2+... +xn) ; 178.93E => 179
# Output: B = [y1, y2, ...., yn]
# Constraint #1: y1+y2+...+yn = T
# Constraint #2: minimize sum(abs(diff(xi - yi)))

举例
# A = [1.2, 2.3, 3.4]
# Round(1.2 + 2.3 + 3.4) = 6.9 => 7
# 1 + 2 + 3 => 6; 7-6 = 1;
# 0.2 + 0.3 + 0.4 = 0.9; 

# 1 + 3 + 3 => 7; 7=7 yes
# 0.2 + 0.7 + 0.4 = 1.3; diff = 1.3; 1.2 - 1 =0.2, 3 - 2.3 =0.7, 3.4 - 3= 0.4;

# 1 + 2 + 4 => 7; 7=7;
# 0.2 + 0.3 + 0.6 = 1.1; diff = 1.1; 1.2 - 1 =0.2, 2.3 - 2 =0.3, 4 - 3.4= 0.6;

所以[1,2,4]比[1,3,3]要好

先将所有 floor(x)加起来统计出如果所有都 floor 的话还差多少,按照 ceil 以后需要加的价格排
序,贪心取最小的补齐即可。

做法是这样:比如[1.2, 2.5, 3.6, 4.0]
建个新数列是原来数字的 int, arr = [1, 2, 3, 4]
然后算需要补多少个数字才能到需要的 sum. round(1.2+2.5+3.6+4.0) - (1+2+3+4) = 1, 补 1 个数字
就好

现在把原数组和 arr 的差值排列一下。 差值是[0.2, 0.5, 0.6, 0]. 我们要从差值最大的数补起,我把
index 排列了一下就是[2, 1, 0, 3],按这个顺序补数字就好。

因为我们只需要补一个数字,先补 index = 2 的, 所以最后的就结果是 [1, 2, 4, 4]。

http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=156061


贪心应该就可以解决
先对每个 element 进行 round(向最小的那边进行 round ceil 或者 floor)
然后把值加起来 如果和 T 有个差值 比 T 大或者小 X
如果比 T 大(小同理),我们可以对那些取 ceil 的 element,

按照这个 element 和它 floor 的差值从小到大排个序
从小到大取 X 个 elements 取 floor 就可以得到 T。
先将所有的 x 取 floor, 然后 T - sum(floor(x)) 得到多少个 x 需要 ceil
按照小数部分将数组排序,从大到小 ceil,其他的 floor。 最后就是想要的结果。
如果用 selection sort 的方法 可以达到 O(n)

*/

/**
# A = [1.2, 2.3, 3.4]
# Round(1.2 + 2.3 + 3.4) = 6.9 => 7
# 1 + 2 + 3 => 6; 7-6 = 1;
# 0.2 + 0.3 + 0.4 = 0.9; 

# 1 + 3 + 3 => 7; 7=7 yes
1.2 - 1 =0.2, 3 - 2.3 =0.7, 3.4 - 3= 0.4;
# 0.2 + 0.7 + 0.4 = 1.3;  diff = 1.3; 

# 1 + 2 + 4 => 7
1.2 - 1 =0.2, 2.3 - 2 =0.3, 4 - 3.4= 0.6;
# 0.2 + 0.3 + 0.6 = 1.1;  diff = 1.1; 

1.1 < 1.3; 1+2+4 better than 1+3+3;

Ex 2:
[1.2, 2.5, 3.6, 4.0];
Round(1.2 + 2.5 + 3.6 + 4.0) = R(11.3) = 11;
Find all floors = 1 + 2 + 3 + 4 = 10; Diff = 11-10 = 1;
decimal reminders: [0.2, 0.5, 0.6, 0.0]; We want the smallest diff to make 10->11;
diffs 2- 1.2 =0.8; 3- 2.5= 0.5; 4- 3.6= 0.4; 5(4 becomes 5)- 4.0= 1.0; make ceil(4.0)->5.0 in our case, make 0.0 cost the highest so it is correctly sorted.
costs: [0.8, 0.5, 0.4, 1.0]; (0.4) is least costly; Sort [0.4, 0.5, 0.8, 1.0];
Sort them by cost indexes: [2, 1, 0, 3] [3.6c, 2.5f, 1.2f, 4.0f]; 3.6->4; 10+1=11; 
Res: [3.6, 2.5, 1.2, 4.0] => [4, 2, 1, 4]; DONE;
*/
import java.io.*;
import java.util.*;

public class RoundPrices {
	
	/**
	 * Solution: Find all floors, Find neededDiff = roundSum - floorSum; Sort nums by ceil-num diffs ASC; Fill neededDiffs until 0 with front nums as Ceils, the rest are Floors;
	 * Time: O(nLgn)
	 * Space; O(n)
	 */
	public int[] roundPrices(double[] prices) {
		int n = prices.length;
		int[] res = new int[n];
		NumWithDiff[] numWithDiffs = new NumWithDiff[n];
		// loop prices find: floorSum, priceSum, diff ceil-num
		int floorSum = 0;
		double priceSum = 0.0;
		for (int i = 0; i < n; i++) {
			int floor = (int) Math.floor(prices[i]);
			int ceil = floor + 1; // make ceil(4.0)->5.0 in our case, make 0.0 cost the highest so it is correctly sorted.
			// diff
			double diff = ceil - prices[i];
			numWithDiffs[i] = new NumWithDiff(floor, diff);
			// update sums.
			floorSum += floor;
			priceSum += prices[i];
		}
		// round sum of priceSum
		int roundSum = (int) Math.round(priceSum);
		// floorSum diff to roundSum
		int neededDiff = roundSum - floorSum;
		// sort nums with diffs ASC, 
		Arrays.sort(numWithDiffs, (a, b) -> {
			return Double.compare(a.ceilDiff, b.ceilDiff);
		});
		// while neededDiff > 0, we fill 1 by 1 with nums in ASC diff orders, make those front ones ceil
		// loop each nums with diff
		int index = 0;
		while (neededDiff > 0) {
			int ceil = numWithDiffs[index].floorVal + 1;
			res[index] = ceil;
			neededDiff--;
			index++;
		}
		// filled ceils, now all floors
		for (int i = index; i < n; i++) {
			res[i] = numWithDiffs[i].floorVal;
		}

		return res;
	}

	class NumWithDiff {
		int floorVal;
		// diff from actual num to ceil.
		double ceilDiff;

		NumWithDiff(int floorVal, double ceilDiff) {
			this.floorVal = floorVal;
			this.ceilDiff = ceilDiff;
		}
	}

	public static void main(String[] args) {
		RoundPrices obj = new RoundPrices();
		double[] prices = {1.2, 2.3, 3.4};
		// double[] prices = {1.2, 2.5, 3.6, 4.0};
		int[] res = obj.roundPrices(prices);
		System.out.println(Arrays.toString(res));
	}

}
