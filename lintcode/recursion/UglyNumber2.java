/**
Ugly number is a number that only have factors 2, 3 and 5.

Design an algorithm to find the nth ugly number. The first 10 ugly numbers are 1, 2, 3, 4, 5, 6, 8, 9, 10, 12...
*/

import java.util.*;
import java.io.*;

public class UglyNumber2{

	/*
	naive solution: check each numbers till n timers, using Greatest Divisible Power
	*/

	//largest d^p that can be fully divided by n.
  public static int greatestDivisiblePower(int n, int d){
      while(n % d == 0){
      	n = n/d;
      }
      return n;
  }

  //check is ugly number by if the number can be fully divided by GDP of 2,3,5 (in this order), if n becomes 1 at the end.
  public static boolean isUgly(int n){
  	if(n <= 3 && n > 0)
  		return true;

  	n = greatestDivisiblePower(n, 2);
  	n = greatestDivisiblePower(n, 3);
  	n = greatestDivisiblePower(n, 5);

  	return (n == 1);
  }

	public static int nthUglyNumber(int n) {
    // Write your code here
		int count = 1;
		// check 1st (1) to nth ugly number
		int i = 1;
		while(count < n){
			i++;
			if(isUgly(i))
				count++;
			
		}
		return i;
  }
  
  /**
  better solution using dynamic programming:
	b/c ugly #s are only multiples of 2,3,5. we just need to check if the next smallest ugly
	# is a multiple of 2,3,5. (won't have any other factor, since we only use 2,3,5).
	Space: O(n);  ugly[n]
	Time: O(n);  loop 0 - n-1;
  */

  public static int nthUglyNumberDynamic(int n){
  	int [] ugly = new int[n];
  	ugly[0] = 1;
  	int i2,i3,i5;
  	i2 = i3 = i5 = 0;

  	for(int i=1; i< n; i++){
  		ugly[i] = Math.min( Math.min(ugly[i2]*2, ugly[i3]*3), ugly[i5]*5);
  		//not using elseif b/c we want to increment all the same values, ex: 6 for i2 and i3
  		if(ugly[i] == ugly[i2]*2)
  			i2++;
  		if(ugly[i] == ugly[i3]*3)
  			i3++;
  		if(ugly[i] == ugly[i5]*5)
  			i5++;
  	}

  	return ugly[n-1];

  }

	public static void main(String[] args){
		Scanner reader = new Scanner(System.in);
		System.out.println("enter an integer for n.");
		int n = reader.nextInt();
		
		System.out.println("ans1: " + nthUglyNumber(n));
		System.out.println("ans2: " + nthUglyNumberDynamic(n));
		
	}
}