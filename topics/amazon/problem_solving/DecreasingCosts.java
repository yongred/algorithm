/**
 * Write a function to take a string and decrease all costs in the string by 15%. Example string would be "Mary spent $5.25 on books this week". Currencies can be of the form $5.25, $ 5.25, and USD 5.25.
 *
 * NOTES for interviewer:
 *
 * Examples of using the standard regex library of each language and some common string manipulation functions in each language (split, merge, etc)
 * US currency is universal, it has been a problem with anyone not understanding it, but they can ask follow up questions to understand the nuances around the currency. What may trip people up more than the currency is that different places use decimals, commas, etc. we would expect an SDE II to clarify this, and if not, then it is a follow up question outlined below.
 * Questions usually seen from candidates to clarify assumptions are "what is the size of the price" "can I expect it to me always a number, decimal, two zeros", "where is price located in the string" (interviewer to say anywhere, you have to find it), ' can there be multiple prices? interviewer to say yes"
 */

/*
Input:
- String
- More than one price? Yes
- Not found? return original string
- Number always in money format? Two decimals? Yes, like $5.00, $ 5.00, USD 5.00
Output:
- String with prices decreased by 15%

Ex:
Input
"This person bought an iphone for $500 and a macbook for USD 1400.00 and an airpod for $ 100.00"

Output:
"This person bought an iphone for $425.00 and a macbook for USD 1190.00 and an airpod for $85.00"

Solution: Split strings. Find signs like $, USD
regex: ([$]|USD)\s{0,1}(\d+)([.]\d{2})
translate: `$ or USD` + `1 or 0 space` + `a dot '.' follow by 2 digits`
 */

package problem_solving;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecreasingCosts {

    public String applyDiscountForPricesInString(String stringWithPrices, int discountPercent) {
        String priceRegex = "([$]|USD)\\s{0,1}(\\d+)([.]\\d{2})";
        Pattern pricePattern = Pattern.compile(priceRegex);
        Matcher priceMatcher = pricePattern.matcher(stringWithPrices);

        StringBuilder stringBuilder = new StringBuilder(stringWithPrices);
        while (priceMatcher.find()) {
            int priceNumberStartIndex = priceMatcher.start(2);
            int priceNumberEndIndex = priceMatcher.end(3);
            String priceStr = stringWithPrices.substring(priceNumberStartIndex, priceNumberEndIndex);

            int percentToMultiply = 100 - discountPercent;
            String percentToMultiplyStr = "0." + percentToMultiply;
            BigDecimal newPrice = new BigDecimal(priceStr).multiply(new BigDecimal(percentToMultiplyStr))
                    .setScale(2, RoundingMode.HALF_UP);

            stringBuilder.replace(priceNumberStartIndex, priceNumberEndIndex, newPrice.toPlainString());
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        DecreasingCosts object = new DecreasingCosts();
        String input = "An iphone $500.00, a macbook USD 1400.00, and an airpod for $ 100.00";
        String result = object.applyDiscountForPricesInString(input, 25);
        System.out.println(result);
    }
}
