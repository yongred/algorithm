/**
Fizz Buzz:
Given number n. Print number from 1 to n. But:

when number is divided by 3, print "fizz".
when number is divided by 5, print "buzz".
when number is divided by both 3 and 5, print "fizz buzz".

Example
If n = 15, you should return:

[
  "1", "2", "fizz",
  "4", "buzz", "fizz",
  "7", "8", "fizz",
  "buzz", "11", "fizz",
  "13", "14", "fizz buzz"
]
*/

/**
Solution: n % 15 == 0
How I arrive:
* realize that check for both (5 & 3)(15) needs to be before 3 and 5 individually. To be able to print fizzbuzz and not fizz or buzz. 
* Use Else if statements.
* Anything % 15 == 0 can be % 3 and % 5;
* Time: O(n);
* Space: O(1);

Challenge: use only 1 if statement.
Solution: use while loop;
How to arrive:
* Realize we can find the next multiple by counting the appearance of 3 and 5 multiples.
  * Ex: 3 = 1(p3)* 3; 6 = 2(p3) * 3; 5 = 1(p5) * 5, 20 = 4(p5) * 5;  So we can use p3 to represents the cur count of multiple of 3, and p5 as for 5.
  * **Key**: as we find a 3 multiple or 5 mul, we increments that p3 or/and p5. that way p3 * 3 is the next 3 mul. Same as for 5.
  * **Key**: if num is mul for both. Then that means the next Multiple for 3 and 5 are the same; Meaning p3 * 3 == p5 * 5;
    * Ex: 30 = 6 * 5 = 10 * 3;  At this point we have passed (9) 3 multiples looking for 10th, and (5) 5 multiples looking for 6th.
  * *Using a while loop to check these statements and increment the counts accordingly*
* Time: O(n);
* SpaceL O(1);
*/

import java.util.*;
import java.io.*;

public class FizzBuzz {

  public List<String> fizzBuzz(int n) {

    ArrayList<String> results = new ArrayList<String>();
    for (int i = 1; i <= n; i++) {
      //when divisible by 15, it is divisible by 3 and 5
      if (i % 15 == 0) {
        results.add("fizz buzz");
      } else if (i % 5 == 0) {
        results.add("buzz");
      } else if (i % 3 == 0) {
        results.add("fizz");
      } else {
        results.add(String.valueOf(i));
      }
    }
    return results;
  }

  /**
   * @param n: An integer
   * @return: A list of strings.
   * Solution: only 1 if statement allowed.
   */
  public List<String> fizzBuzz2(int n) {
    List<String> res = new ArrayList<>();
    int i = 1;
    // counting 3 & 5 multiples
    int p3 = 1;
    int p5 = 1;
    
    while (i <= n) {
      // normal number output
      while (i < p3 * 3 && i < p5 * 5) {
        // before encounter the next 3 or 5 multiple
        res.add(i + "");
        i++;
      }
      // check multiples of both 3, 5; Ex: 30 = 6*5 = 10*3 ; p3=10, p5=6
      if (i <= n && p3 * 3 == p5 * 5) {
        res.add("fizz buzz");
        // both 5,3 look for next multiple
        p3++;
        p5++;
        i++;
        continue;
      }
      // check 3 multiples
      while (i <= n && i == p3 * 3) {
        res.add("fizz");
        p3++;
        i++;
      }
      // check 5 multiples
      while (i <= n && i == p5 * 5) {
        res.add("buzz");
        p5++;
        i++;
      }
    }
    
    return res;
  }
}