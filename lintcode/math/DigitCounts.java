import java.util.*;
import java.io.*;

/**
Count the number of k's between 0 and n. k can be 0 - 9.
*/

public class DigitCounts{
	/*
	Solution: ex: 0-99 find 3s; 1*10^1 + 10*10^0 = 20;
		-[0-999] find 3s: 1*10^2 + 10*10^1 + 100 * 10^0 = 300;
		-count 1 digit at a time;
	*/
	public static int countDigit(int k, int n){
		 // write your code here  
		if(k == 0){
			return countZeroK(k, n);
		}

	    int result = 0;  
	    int base = 1;  
	    while (n/base > 0) {  
	        int cur = (n/base)%10;  
	        int low = n - (n/base) * base; 
	        int high = n/(base * 10);  
	        
	        if (cur == k) {  
	            result += high * base + low + 1;  
	        } else if (cur < k) {  
	            result += high * base;  
	        } else {  
	            result += (high + 1) * base;  
	        }  
	        
	        base *= 10;  
	    }  
	    return result;
	}

	public static int countZeroK(int k, int n){
		if(n < 100)
			return (n/10 + 1);

		int result = 0;
		int base = 1;
		while(n/base > 10){
			int cur = (n/base) % 10;
			int high = n/(base*10);
			int len = Integer.toString(high).length();
			if(len > 1){
				result += (high + 1) * base; 
			}else if(len == 1){
				result += (high * base);
			}
			base *= 10; 
		}

		return result;
	}

	/*
	brute force
	*/
	public static int digitCounts(int k, int n) {
	    int count = 0;
	    char kChar = (char)(k + '0');
	    System.out.println("kchar: " + kChar);
	    for (int i = k; i <= n; i++) {
	        char[] iChars = Integer.toString(i).toCharArray();
	        for (char iChar : iChars) {
	            if (kChar == iChar){
	            	System.out.println(i + " " + iChar + " ");
	            	count++;
	            } 
	        }
	    }
	    return count;
    }

	public static void main(String[] args){
		Scanner reader = new Scanner(System.in);
		System.out.println("enter an integer for n.");
		int n = reader.nextInt();
		System.out.println("enter an integer for f.");
		int k = reader.nextInt();
		System.out.println(countDigit(k, n));
	}
}