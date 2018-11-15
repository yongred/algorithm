/**
128. Hash Function
In data structure Hash, hash function is used to convert a string(or any other type) into an integer smaller than hash size and bigger or equal to zero. The objective of designing a hash function is to "hash" the key as unreasonable as possible. A good hash function can avoid collision as less as possible. A widely used hash function algorithm is using a magic number 33, consider any string as a 33 based big integer like follow:

hashcode("abcd") = (ascii(a) * 333 + ascii(b) * 332 + ascii(c) *33 + ascii(d)) % HASH_SIZE 

                              = (97* 333 + 98 * 332 + 99 * 33 +100) % HASH_SIZE

                              = 3595978 % HASH_SIZE

here HASH_SIZE is the capacity of the hash table (you can assume a hash table is like an array with index 0 ~ HASH_SIZE-1).

Given a string as a key and the size of hash table, return the hash value of this key.f



Example
For key="abcd" and size=100, return 78

Clarification
For this problem, you are not necessary to design your own hash algorithm or consider any collision issue, you just need to implement the algorithm as described.
*/

import java.util.*;
import java.io.*;

public class HashFunction {
    /**
     * @param key: A string you should hash
     * @param HASH_SIZE: An integer
     * @return: An integer
     * Solution: use (a + b)% m = (a%m + b%m)%m; (a*b)%m = (a%m * b%m) %m
     * (33^2 *a + 33*b + c) % m = (33*33*a)%m + (33*b)%m + c%m;
     */
    public int hashCode(char[] key, int HASH_SIZE) {
        long ans = 0;
        for (int i=0; i< key.length; i++)
        {
            //33^2a + 33b + c
            int exp = key.length - 1 - i;
            ans += key[i] * modPow(33, exp, HASH_SIZE);
            ans %= HASH_SIZE;
        }
        return (int)ans;
    }
    //T(lgN)
    public long modPow(int base, int e, int mod)
    {
        if(e == 0)
            return 1;
        if(e == 1)
            return base % mod;
        if(e % 2 == 0)
        {
            long temp = modPow(base, e/2, mod); //memorization
            //mod to keep small. if mod is extremely large, ex: 10^9.
            //(a*b)%m = (a%m * b%m) %m
            return ((temp % mod) * (temp % mod)) % mod; 
        }
        else
        {
            return (base % mod) * modPow(base, e - 1, mod) % mod;
        }
    }
}