import java.util.*;
import java.io.*;

/**
 * Write an algorithm which computes the number of trailing zeros in n factorial.
 * 
 */

public class TrailingZeros {

  /*
   * trailing 0s only produced when 2*5, so count the pairs of 2 and 5s, 5 is always fewer than 2s,
   * so count 5s. number like 25, 125 have more than one 5s.
   */
  public static long countZeros(long n) {
    long count = 0;
    for (long i = 5; n / i >= 1; i *= 5) {
      count += n / i;
    }
    return count;
  }

  public static void main(String[] args) {
    Scanner reader = new Scanner(System.in);
    System.out.println("enter an integer");
    long n = reader.nextLong();

    System.out.println(countZeros(n));
  }
}
