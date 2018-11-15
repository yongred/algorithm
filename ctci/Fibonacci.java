/**
cached fibonacci number
*/

import java.util.*;

/*
not cached: n=5                          
			  f5
		f4		     f3
	  f3  f2       f2  f1
	f2 f1 f1 f0   f1 f0
  f1 f0	

cached: n=5
		  	  [a5]- f5
	   [a4]- (f4)	+	 [a3]
   	[a3]- (f3) + [a2]       
  [a2]- (f2) f1    		 
	  f1 f0	
*/
public class Fibonacci{
	//O(n), each level only calculated once, n levels
	public int cachedFib(int num){
		//store calculated level, prevent recalculation
		int [] cached = new int[num + 1];
		return fib(num, cached);
	}

	public int fib(int num, int [] cached){
		if(num <= 0)
			return 0;
		if(num == 1)
			return 1;
		if(cached[num] == 0) //if not initialized/cached
			cached[num] = fib(num-1, cached) + fib(num-2, cached);

		return cached[num];
	}

	public static void main(String [] args){
		Fibonacci obj = new Fibonacci();
		int n = 10;
		System.out.println(" num " + n + " fib " + obj.cachedFib(n));
	}
}