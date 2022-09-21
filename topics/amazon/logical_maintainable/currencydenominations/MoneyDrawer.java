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

package currencydenominations;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public class MoneyDrawer {
	private List<Currency> currencies;
	private int rangeOfExtraChangeAllowed;

	public MoneyDrawer(List<Currency> currencies, int rangeOfExtraChangeAllowed) {
		this.currencies = currencies;
		this.rangeOfExtraChangeAllowed = rangeOfExtraChangeAllowed;
		sortCurrenciesByValueDesc();
	}

	private void sortCurrenciesByValueDesc() {
		Comparator<Currency> sortByValueComparator = (Currency currency1, Currency currency2) -> currency2.value.compareTo(currency1.value);
		Collections.sort(currencies, sortByValueComparator);
	}

	public void printMinChangesForAmount(int moneyAmount) throws Exception {
		validateMoneyInput(moneyAmount);
		Map<Integer, Integer> numCurrencyNeeded = calculateChangesWithNonExact(moneyAmount);
		printCurrencyNumber(numCurrencyNeeded);
	}

	private void validateMoneyInput(int moneyAmount) throws Exception {
		if (moneyAmount < 0) {
			throw new Exception("Money cannot be negative");
		}
		if (calculateTotalMoney() < moneyAmount) {
			throw new Exception("Not enough change for the amount " + moneyAmount);
		}
	}

	private int calculateTotalMoney() {
		return currencies.stream().mapToInt(currency -> (currency.count * currency.value)).sum();
	}

	private Map<Integer, Integer> calculateChangesWithNonExact(int moneyAmount) throws Exception {
		Map<Integer, Integer> numCurrencyNeeded = new HashMap<>();

		int moneyLeftover = addChangesForAmountReturnLeftover(numCurrencyNeeded, moneyAmount);
		addExtraChangeForLeftoverMoney(numCurrencyNeeded, moneyLeftover);

		return numCurrencyNeeded;
	}

	private int addChangesForAmountReturnLeftover(Map<Integer, Integer> numCurrencyNeeded, int moneyAmount) {
		int moneyLeftover = moneyAmount;

		for (int i = 0; i < currencies.size(); i++) {
			Currency currency = currencies.get(i);
			if (currency.value <= moneyLeftover && currency.count > 0) {
				int numCurrencyWithdrawn = moneyLeftover / currency.value;
				if (numCurrencyWithdrawn > currency.count) {
					numCurrencyWithdrawn = currency.count;
				}
				moneyLeftover -= (numCurrencyWithdrawn * currency.value);
				currency.count -= numCurrencyWithdrawn;
				System.out.println("Testing currency.count " + currency.value + " " + currency.count);
				numCurrencyNeeded.put(currency.value, numCurrencyWithdrawn);
			}
		}
		return moneyLeftover;
	}

	private void addExtraChangeForLeftoverMoney(Map<Integer, Integer> numCurrencyNeeded, int moneyLeftover) 
		throws Exception {

		if (moneyLeftover == 0) {
			return;
		}

		for (int i = currencies.size() - 1; i >= 0; i--) {
			Currency currency = currencies.get(i);
			if (currency.count > 0 && 
				currency.value > moneyLeftover && 
				currency.value <= moneyLeftover + rangeOfExtraChangeAllowed) {

				int currencyNumAlreadyCounted = numCurrencyNeeded.getOrDefault(currency.value, 0);
				numCurrencyNeeded.put(currency.value, currencyNumAlreadyCounted + 1);
				System.out.println("Testing leftover count " + currency.value + " " + currency.count);
				return;
			}
		}
		throw new Exception("Don't have exact change for the amount");
	}

	private void printCurrencyNumber(Map<Integer, Integer> numCurrencyNeeded) {
		numCurrencyNeeded.forEach((currency, count) -> System.out.println(currency + " : " + count));
	}

	public static void main(String[] args) {
		List<Currency> currencies = new ArrayList<>(Arrays.asList(
			new Currency[] {
				new Currency(2000, 3), new Currency(1000, 2), new Currency(500, 2), new Currency(100, 8), 
				new Currency(25, 4), new Currency(10, 10), new Currency(5, 0), new Currency(1, 0)
			}
		));
		int rangeOfExtraChangeAllowed = 5;
		MoneyDrawer moneyDrawer  = new MoneyDrawer(currencies, rangeOfExtraChangeAllowed);

		try {
			moneyDrawer.printMinChangesForAmount(6005);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}