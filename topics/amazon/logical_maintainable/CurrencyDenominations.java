/**
https://w.amazon.com/bin/view/RecruitingEngine/Pathfinder/InterviewToolkit/Logical/Maintainable/

Given a sum of money compute the minimum number of bills and coins that equal that sum. Assume you only have the following denominations:
Bills: 20, 10, 5, 1,
Coins: .25, .1, .05, .01
For example, given 6.35 the solution would be One 5, One 1, One .25, One .1


Follow up:
- You will now be given a cash drawer that defines how much of each bill and coin you have to make change with, please alter your solution to account for this.
- Edge Cases: Candidate should identify or be hinted towards two edge cases, not having enough money, and not being able to make exact change.
- For exact change edge case, throwing error is not enough, they should be able to return more change than asked (customer obsession) within a certain range (Don't give a 20 if 1 cent is owed, but a nick might be ok)
*/

/**
Come up with Solution:

Qs:
- Edge cases: 
  - Is the number of changes unlimit? Infinite bills and coints? Assume yes for now, later change to limited.
  - Input: 
    - null amount handling. Assume: Throw exception.
    - Negative numbers? Assume no.
    - Would decimal point be only 2 (money amount). Assume Yes
  - Input: Double/Float point is not good for money calculation, Assume use BigDecimal
    - Use String constructor in new BigDecimal("20") don't use double argument constructor.
  - What is the output? Assume: printout strings like "5x 5, 1x 1, 1x .25, 1x .1"
    - Multiple answers if same number of bills? Assume just return one answer is enough
  - Time and space requirement? Assume: since testing that, any solution is good.


Follow up: (See currencydenominations.MoneyDrawer.java for my solution)
- Limited number of changes
- What happen when not enough money? Assume: throw exception
- What happen when no exact change, 
  - ex: 60.50, 3x 20s, 1x 0.25, 2x 0.1, owe 0.05; give more change within the allowed range = 0.05, which is 0.1 in this case.
*/

import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;
import currencydenominations.*;

public class CurrencyDenominations {

	private static final List<BigDecimal> billsAndCoins = Arrays.asList(
			new BigDecimal[] {
				new BigDecimal("20"), new BigDecimal("10"), new BigDecimal("5"), new BigDecimal("1"), 
				new BigDecimal("0.25"), new BigDecimal("0.10"), new BigDecimal("0.05"), new BigDecimal("0.01")
			}
		);

	public void printMinNumberOfChangesForMoney(BigDecimal moneyAmount) throws Exception {
		if (moneyAmount == null) {
			throw new Exception("Money cannot be null");
		}

		for (BigDecimal currency : billsAndCoins) {
			int numCurrencyNeeded = 0;
			if (moneyAmount.compareTo(currency) != -1) {
				numCurrencyNeeded = moneyAmount.divide(currency).intValue();
				moneyAmount = moneyAmount.remainder(currency);
			}
			
			System.out.println(currency + " : " + numCurrencyNeeded);
			System.out.println("Money left " + moneyAmount);
		}
	}

	public static void main(String[] args) {
		CurrencyDenominations currencyDenominations  = new CurrencyDenominations();
		BigDecimal moneyInput = new BigDecimal("18.60");
		try {
			currencyDenominations.printMinNumberOfChangesForMoney(moneyInput);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
