/**
Hilbert Curve

Given (x, y, iter), in which (x, y) is position at x-axis and y-axis, and iter is how many iterations will be. Output is in iteration iter, how many steps are required to move from (0, 0) to (x, y) in iteration iter.

https://en.wikipedia.org/wiki/Hilbert_curve

每次给你一个(x, y, iter),问你在 iter 这张图中在(x, y)坐标的点是第几个?
解法:从大往小左,逐渐细化。每次先算出当前点在当前 iter 是在第几象限,先加上前面那些跳
过去的象限里的点。然后找到这个点在这个象限的相对坐标新(x,y),但是还不够!对于三四象限
的点,因为方向变了,需要做镜面映射,把(x,y)映射成(y,x) (第三象限) 或 (M-y, M-x) (第四象限),
M 是象限的长宽。

Analyze! 发现每一个 level 的图会是 4 个 level-1 的变化得来的(一样,或者根据对角线对称)。

输入是坐标 x,y,还是第几 iteration,输出是从原点走多少步达到这个点。
结合 code,先把 iteration 1,2,3 画出来,就知道是干什么的了,对了,如果可能,也可以试试
反着来一遍。输入是第几 iteration,steps,返回坐标, 顺着那个递归的思路就可以做的。代码并不难。思路就是每个 generation 是由前一个 generation
组成的, 有的象限会有翻转。每次进去判断在那个象限然后把相应的坐标翻转一下继续递归就行了
*/

/**
Solution: Hilbert Curve pattern, from curGraph = 4 grids of prevGrid;
Ex:
iter1;
y1[2,3]
y0[1,4]
	x0,x1

iter2:
iter1 iter1
[2,3]	[2,3]
[1,4]	[1,4]
iter1 iter1
[4,3]	[2,1]
[1,2] [3,4]

grid4:
[2,1] -> [2,3]
[3,4]    [1,4]
xs:
       (3,3)->(0,0) 4-3-1
     (2,2)->(1,1) 4-2-1
  (1,1)->(2,2) 4-1-1
(0,0)->(3,3) 4-0-1
ys:
	(1,1)->(0,0) 2-1-1
(0,0)->(1,1) 2-0-1

goal: (3,1)->(0,0); (2,0)->(1,1)
(3,1): 2-1-1=0, 4-3-1=0
(2,0): 2-0-1=1, 4-2-1=1

How to Arrive:
* Question wants us to count the grids of Hilbert Curve Path to a point;
* Hilbert Curve Graph Iters: iter1= 2*2; iter2= 4*4; iter3= 8*8 grids;
* As the example above, iter = 4 girds of iter-1;
* 1st Square, left bottom corner. Shape is iter-1 rotated clockwise, 
	* Shape/steps are Mirror of iter-1 rotated clockwise; (x,y)-> (y,x)
	* The number of steps/grids to get to 1st Square = iter-1 # of grids, 
	* so iter-1 (x',y') = steps(y, x, iter-1); x' = y; y' = x;
* 2nd Square, left top corner. Shape is same as iter-1; but y is higher by len (n/2);
	* iter-1 (x',y') = (x, y - n/2); x' = x, y' = y - n/2;
	* The number of steps/grids to get to 2nd Square = 2 * (# iter-1 grids); 
	* So steps = (squareSize + steps(x, y- n/2, iter-1 ));
* 3rd Square, right top corner. Shape is same as iter-1; but x and y ar both higher by len (n/2);
	* iter-1 = (x',y') = (x - n/2, y - n/2); x' = x - n/2, y' = y - n/2;
	* The number of steps/grids to get to 3rd Square = 3 * (# iter-1 grids); 
	* So steps = ((2 * squareSize) + steps(x, y- n/2, iter-1 ));
* 4th Square, x >= len and y < len; right bottom corner; Shape is iter-1 rotated counter-clockwise;
	* Shape/steps are mirror of iter-1 rotated counter-clockwise; 
	* iter-1 (x', y') = (n - x - 1, n/2 - y - 1); y' = n - x - 1; x' = n/2 - y - 1;
	* The number of steps/grids to get to 4th Square = 4 * (# iter-1 grids);
	* So steps = (3 * squareSize) + steps(n - x - 1, n/2 - y - 1, iter-1);

* Time: O(n) # of iters
* Space: O(lgN);
*/

public class HilbertCurve {
	
	/**
	 * Solution: Hilbert Curve pattern, from curGraph = 4 grids of prevGrid;
	 * Time: O(n) # of iters
	 * Space: O(lgN);
	 */
	public static int stepsToPos(int x, int y, int iter) {
		// only 1 grid.
		if (iter == 0) {
			return 1;
		}
		// iter-1 HilbertCurve n * n square's side len; Figure out the 1/4 square that makes up cur iter.
		// iter1 = 2*2; iter2= 4*4; iter3= 8*8; 2^iter = n; 2^iter-1 = prev n;
		int n = 1 << iter;
		int gridLen = n / 2;
		// 1/4 square size of cur iter.
		int gridSize = gridLen * gridLen;
		int res = 0;
		// figure out the 4 grid squares, where the (x,y) lands
		if (x < gridLen && y < gridLen) {
			// 1st Square, left bottom corner. Shape is iter-1 rotated clockwise, 
			// Shape/steps are Mirror of iter-1 rotated clockwise; (x,y)-> (y,x)
			res = stepsToPos(y, x, iter - 1);
		} else if (x < gridLen && y >= gridLen) {
			// 2nd Square, left top corner. Shape is same as iter-1; but y is higher by len (n/2);
			res = gridSize + stepsToPos(x, y - gridLen, iter - 1);
		} else if (x >= gridLen && y >= gridLen) {
			// 3rd Square, right top corner. Shape is same as iter-1; but x & y are higher by len (n/2);
			res = (2 * gridSize) + stepsToPos(x - gridLen, y - gridLen, iter - 1);
		} else {
			// 4th Square, x >= len & y < len; right bottom corner; Shape is iter-1 rotated counter-clockwise;
			// Shape/steps are mirror of iter-1 rotated counter-clockwise; 
			// y' = n - x - 1; x' = gridLen (n/2) - y - 1;
			res = (3 * gridSize) + stepsToPos(gridLen - y - 1, n - x - 1, iter - 1);
		}
		return res;
	}


	public static void main(String[] args) {
		int x = 7;
		int y = 4;
		int iter = 3;
		System.out.println(stepsToPos(x, y, iter));
	}


}