/**
204. Count Primes
Count the number of prime numbers less than a non-negative number, n.

Example:

Input: 10
Output: 4
Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
*/

/**
Solution 1: brute force, check 1 -> n-1 isPrime (check 1 -> i) repeats;
Ex:
prime: only 1 and n as divisor. 2 divisors total.
1. only even prime is 2; so check **odd** #s only
2. 0 and 1 is not a prime number. only have 1 divisor.
3. check #s from 3 -> sqrt(n); 
Ex: 64^0.5 = 8; 8 * 8 = 64; 
1 * 64, 2 * 32, 4 * 16, 8 * 8, no need to continue, all just reversed opes. Repeats.
continue: 16 * 4, 32 * 2, 64 * 1;

How to Arrive:
* Use the facts above about Prime #s to determine isPrime:
* From 3 -> sqrt(n):
	* Check every Odd num. (i += 2); if n % i = 0;
* Then use isPrime function check every 2 -> n-1 #s; Count them.

* Time: O(n^2)
* Space: O(1)

Solution 2: Sieve of Eratosthenes, Keep a boolean record of notPrime #s, by mark prime multiples to notPrime.
**Sieve of Eratosthenes**:
From 2 -> sqrt(n);
If i is not marked as notPrime, means it is a prime. B/c all nonPrime #s < i are marked.
Ex: i =10; 4 is marked 2 * 2, 6 is marked 2 * 3, 8 is marked 2 * 4, 9 is marked 3 * 3;
start from i * i -> sqrt(n) mark all multiples as not prime.
start from i * i b/c all multiples < i already marked.
Ex: i = 5; 2 * 5 marked when i=2, 3 * 5 marked when i=3, 4 * 5 marked when i=2 also.

How to Arrive:
* Invariant: All nonPrime #s before curPrime is marked.
* The multiples of a num is not a Prime. So we can loop #s and mark their multiples as notPrime.
* Optimize:
**Skip marked nonPrime**:
	* we don't need to check the multiples of the marked #s, b/c all their multiples are already marked when looping thisNum's denominator. 
	Ex: i=9; denominator =3; 18 marked when looping 3. 27, 36 .. 81 all marked when looping 3's multiples.
**Find multiples from 2 -> sqrt(n)**:
	* From example of solution 1, we know After sqrt(n), every pair of denominators are just repeating.
* After all nonPrime from 2->n-1 marked, we just need to count how many primes left.

* Time: O(n Lg(lgn)); We remove all evens from 1st 2's muls. Then all 3's mul, 5's, 7's.. Halfed everytime.
* Space: O(n)
*/

class CountPrimes {

	/**
	 * Solution 2: Sieve of Eratosthenes, Keep a boolean record of notPrime #s, by mark prime multiples to notPrime.
	 * Time: O(n Lg(lgn)); We remove all evens from 1st 2's muls. Then all 3's mul, 5's, 7's.. Halfed everytime.
	 * Space: O(n)
	 */
	public int countPrimes(int n) {
    if (n <= 1) {
      return 0;
    }
    boolean[] notPrimes = new boolean[n];
    // from 2-> sqrt(n-1); turn F all multiples of cur i.
    for (int i = 2; i * i <= n - 1; i++) {
      // If notPrimes[i] is not changed, then it is a prime 
      if (!notPrimes[i]) {
        // start from i * i -> sqrt(n) mark all multiples of curPrime as not prime.
        for (int mul = i * i; mul <= n - 1; mul += i) {
          notPrimes[mul] = true;
        }
      }
    }
    // count how many primes left in Array. From 2->n-1
    int count = 0;
    for (int i = 2; i <= n - 1; i++) {
      if (!notPrimes[i]) {
        count++;
      }
    }
    return count;
  }

	/**
	 * Solution 1: brute force, check 1 -> n-1 isPrime (check 1 -> i) repeats;
	 * Time: O(n^2)
	 * Space: O(1)
	 */
  public int countPrimes(int n) {
    int count = 0;
    // count prime < n; 1 -> n-1;
    for (int i = 1; i < n; i++) {
      if (isPrime(i)) {
        count++;
      }
    }
    return count;
  }
  
  public boolean isPrime(int n) {
    // 1 and 0 not prime;
    if (n <= 1) {
      return false;
    }
    // 2 is the only even prime.
    if (n == 2) {
      return true;
    }
    if (n % 2 == 0) {
      return false;
    }
    // start from 3 to sqrt(n); check odd #s n % i = 0;
    // i <= sqrt(n); same as i * i = n;
    for (int i = 3; i * i <= n; i += 2) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }
  
}