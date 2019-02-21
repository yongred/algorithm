/**
3. Shutbox game / pair sum
至于Shutbox, 估计可以wiki一下，就是有一个游戏，有0~9块小木板，每一次扔俩骰子，和为sum, 然后可以选择翻一块或者是两块小木板，使其和 = sum。 小木板翻过一次就不能再用了。若当前已经没有可以翻的小木板（比如小木板还剩1，2，3，4，俩骰子和为9）则游戏结束，输了。赢的条件是，所有的小木板都被顺利翻完。

代码有三个小问，第一个是写代码similation 游戏过程，第二个有点记不太清，类似于算出你选择的策略的成功率，最后一个是选出一个比较好的策略使成功率提高。

新题，题目描述起来很复杂，有9个tile，第一个tile上有数字1，第二个tile有数字2，etc.，初始状态都是unflipped，两个筛子，一个玩家。每一轮玩家摇筛子，看两个筛子的和，计做s，之后可以有两个options，第一个option是找s对应的tile，如果是unflipped，可以选择flip，这样这个tile就变成flipped了。第二个option是找两个tile，保证两个筛子的和是s，并且都是unflipped的。题目就是模拟整个游戏，玩家的策略可以是优先做option1，如果option1没法使用（比如两个筛子的和是10，或者对应的tile已经flipped），就选择option2。如果options2也没办法使用，这个时候游戏以输结束。如果在任何时候所有9个tile都被flipped了，这个时候游戏就算赢。模拟100K次游戏，看看这个策略玩家赢的概率有多大。面试官提示说赢的概率大概在10%左右。
*/

/**
1,2,3,4,5,6,7,8,9

dices: 4+3=7; choices: flip 7 or (1,6) (2,5) (3,4)

rand,
pick from availables

pick fewest flip first;
*/

import java.io.*;
import java.util.*;

public class ShutboxGame {

	boolean[] choices;
	int size;
	boolean win = false;
	boolean lose = false;

	public ShutboxGame() {
		choices = new boolean[10];
		size = 9;
		for (int i = 1; i < 10; i++) {
			choices[i] = true;
		}
	}
	
	public int rollDicesSum() {
		Random rand = new Random();
		// 0->5 + 1 = 1->6;
		int dice1 = rand.nextInt(6) + 1;
		int dice2 = rand.nextInt(6) + 1;
		return dice1 + dice2;
	}

	/**
	 choose rand choice: 2.5% win
	 */
	public void playerRand(int sum) {
		List<int[]> res = getChoices(sum);
		// check if no sum res
		if (res.size() == 0) {
			// System.out.println("No Sum = " + sum + " You Lose!");
			lose = true;
			return;
		}
		// pick a rand index form res.
		Random rand = new Random();
		int randInd = rand.nextInt(res.size());
		// System.out.println("rand: " + randInd + " size " + res.size());
		int[] choosed = res.get(randInd);
		// System.out.println("Sum " + sum);
		// System.out.println(Arrays.toString(choosed));
		// flip the choosen
		for (int i = 0; i < choosed.length; i++) {
			choices[choosed[i]] = false;
			size--;
		}
		// check for win
		if (size == 0) {
			// System.out.println("WIN!");
			win = true;
		}
	}

	public List<int[]> getChoices(int sum) {
		// choose random
		List<int[]> res = new ArrayList<>();
		// get num = sum if exist.
		if (sum <= 9 && choices[sum]) {
			res.add(new int[] {sum});
		}
		// get combos = sum 
		// 2 pointers
		int n1 = 1;
		int n2 = 9;
		while (n1 < n2) {
			if (!choices[n1]) {
				n1++;
				continue;
			}
			if (!choices[n2]) {
				n2--;
				continue;
			}
			if (n1 + n2 == sum) {
				res.add(new int[] {n1, n2});
				n1++;
				n2--;
			} else if (n1 + n2 > sum) {
				n2--;
			} else {
				n1++;
			}
		}
		return res;
	}


	/**
	 Pick Fewest spaces: 4.2%
	 Then pick largest pair
	 */
	public void player2(int sum) {
		// if sum itself is a choice
		if (sum <= 9 && choices[sum]) {
			choices[sum] = false;
			size--;
		} else {
			// choose Cover the Fewest Spaces (if sum is 1-9 and not flipped) choose that.
			List<int[]> res = getChoices(sum);
			// check if no sum res
			if (res.size() == 0) {
				// System.out.println("No Sum = " + sum + " You Lose!");
				lose = true;
				return;
			}
			// pick a rand index form res.
			Random rand = new Random();
			int randInd = rand.nextInt(res.size());
			// System.out.println("rand: " + randInd + " size " + res.size());
			int[] choosed = res.get(randInd);
			// System.out.println("Sum " + sum);
			// System.out.println(Arrays.toString(choosed));
			// flip the choosen
			choices[choosed[0]] = false;
    	choices[choosed[1]] = false;
    	size -= 2;
		}
		// check for win
		if (size == 0) {
			// System.out.println("WIN!");
			win = true;
		}
	}


	public static void main(String[] args) {
		int totalWins = 0;
		for (int i = 0; i < 100000; i++) {
			ShutboxGame obj = new ShutboxGame();
			while (!obj.win && !obj.lose) {
				int sum = obj.rollDicesSum();
				obj.player2(sum);
			}
			if (obj.win) {
				totalWins++;
			}
		}
		System.out.println("totalWins " + totalWins);
		double percent = (double) totalWins / 100000.0;
		percent = (double) (Math.round(percent * 1000) / 10.0);
		System.out.println(percent);


	}

}