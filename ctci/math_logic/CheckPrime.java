/**
	common question 1: check if n is prime number
*/

import java.util.*;
import java.io.*;

public class CheckPrime{

	/*
		loop through all nums and see if divisible
	*/
	public boolean naivePrime(int n){
		if(n < 2)
			return false;
		for(int i=2; i< n; i++){
			if(n % i == 0)
				return false;
		}
		return true;
	}

	/*
		loop throught only the sqrt of n, b/c 
		64 divisible pairs: (1,64) (2, 32) (4, 16) (8, 8) <-> (64,1) (32,2) (16,4)
		after 8 is identical, so only needs to be divide to 8, which is sqrt(64)
	*/
	public boolean betterPrime(int n){
		int sqrt = (int) Math.sqrt(n);
		if(n < 2)
			return false;
		if(n == 2)
			return true;
		for(int i=3; i<= sqrt; i+=2){
			if(n % i == 0)
				return false;
		}

		return true;
	}

	/**
	Divisibility: every positive integer is composed by a product of primes
		ex: 84 = 2^2 * 3^1 * 5^0 * 7^1 * 11^0 * 13^0 * 17^0 * ...
		-all non-prime numbers are divisible by a prime number.

	Generating a List of Primes: The Sieve of Eratosthenes
	from 2 to num; cross out all prime starting from 2,3,5,7.. and 
	their multiples that are < num
	check only odd integers, since even > 2 is not prime
	only check till sqrt, reasons above
	*/
	public boolean isPrimeSieve(int n){
		boolean [] primesTable = generateListPrime(n);
		return primesTable[n];
	}

	public boolean [] generateListPrime(int max){
		boolean[] primesTable = new boolean[max+1]; //0-max
		//set all odds from 2-max to true, include 2
		init(primesTable);
		//start with 3, b/c evens are already crossed out (2s)
		int prime = 3;
		while(prime <= Math.sqrt(max)){
			//cross off multiples of prime, those are not primes
			crossOff(primesTable, prime);

			//loop to next true val, not checked
			prime = getNextprime(primesTable, prime);
		}

		return primesTable;
	}

	public void crossOff(boolean [] table, int prime){
		/* Cross off remaining multiples of prime. We can start with (prime*prime),
		 * because if we have a k * prime, where k < prime, this value would have
		 * already been crossed off in a prior iteration. */
		for(int i = prime * prime; i< table.length; i += prime){
			table[i] = false;
		}
	}

	public int getNextprime(boolean[] table, int prime){
		int next = prime+1;
		//loop over not prime(true,checked) values
		while(next < table.length && !table[next]){
			next++;
		}
		return next;
	}

	//set all odd nums from 2 - max to true
	public void init(boolean [] table){
		if(table.length >= 2)
			table[2] = true;
		for(int i=3; i< table.length; i+=2){
			table[i] = true;
		}
	}

	public static void main(String [] args){
		CheckPrime obj = new CheckPrime();
		Scanner input = new Scanner(System.in);
		System.out.println("input a num to check prime >");
		int n = input.nextInt();
		System.out.println("naivePrime " + obj.naivePrime(n));
		System.out.println("betterPrime " +  obj.betterPrime(n));
		System.out.println("sievePrime " +  obj.isPrimeSieve(n));

	}

}