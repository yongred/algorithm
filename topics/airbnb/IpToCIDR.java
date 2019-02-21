/**
Leetcode 751 IP to CIDR

Given a start IP address ip and a number of ips we need to cover n, return a representation of the range as a list (of smallest possible length) of CIDR blocks.

A CIDR block is a string consisting of an IP, followed by a slash, and then the prefix length. For example: "123.45.67.89/20". That prefix length "20" represents the number of common prefix bits in the specified range.

Example 1:
Input: ip = "255.0.0.7", n = 10
Output: ["255.0.0.7/32","255.0.0.8/29","255.0.0.16/32"]
Explanation:
The initial ip address, when converted to binary, looks like this (spaces added for clarity):
255.0.0.7 -> 11111111 00000000 00000000 00000111
The address "255.0.0.7/32" specifies all addresses with a common prefix of 32 bits to the given address,
ie. just this one address.

The address "255.0.0.8/29" specifies all addresses with a common prefix of 29 bits to the given address:
255.0.0.8 -> 11111111 00000000 00000000 00001000
Addresses with common prefix of 29 bits are:
11111111 00000000 00000000 00001000
11111111 00000000 00000000 00001001
11111111 00000000 00000000 00001010
11111111 00000000 00000000 00001011
11111111 00000000 00000000 00001100
11111111 00000000 00000000 00001101
11111111 00000000 00000000 00001110
11111111 00000000 00000000 00001111

The address "255.0.0.16/32" specifies all addresses with a common prefix of 32 bits to the given address,
ie. just 11111111 00000000 00000000 00010000.

In total, the reswer specifies the range of 10 ips starting with the address 255.0.0.7 .

There were other representations, such as:
["255.0.0.7/32","255.0.0.8/30", "255.0.0.12/30", "255.0.0.16/32"],
but our reswer was the shortest possible.

Also note that a representation beginning with say, "255.0.0.7/30" would be incorrect,
because it includes addresses like 255.0.0.4 = 11111111 00000000 00000000 00000100 
that are outside the specified range.
Note:
ip will be a valid IPv4 address.
Every implied address ip + x (for x < n) will be a valid IPv4 address.
n will be an integer in the range [1, 1000].
*/

/**
Solution: Convert string ip to long num. Find rightmost 1bit. Convert back to StringIp with sameBits;
Ex: [255.0.0.5] n=10;

Res: [255.0.0.5/32, 255.0.0.6/31, 255.0.0.8/30, 255.0.0.12/31, 255.0.0.14/32]

255.0.0.5
1..1.0.0.00000101
firstBit/steps= 1, 1< 10; 2^0 bit, 32 sameBits. 255.0.0.5/32; 10-1=9 steps left;

255.0.0.6
1..1.0.0.00000110
1..1.0.0.00000111
firstBit/steps= 2, 2< 9; 2^1 bit, 32-1=31 sameBits. 255.0.0.6/31; 9-2=7 steps left;

255.0.0.8
1..1.0.0.00001000
1..1.0.0.00001001
1..1.0.0.00001010
1..1.0.0.00001011
firstBit/steps= 8, 8> 7 reduce 8>>1 = 4, 4< 7; 4= 2^2 bits, 32-2=30 sameBits; 255.0.0.8/30; 7-4=3 steps left;

255.0.0.12
1..1.0.0.00001100
1..1.0.0.00001101
firstBit/steps= 4, 4> 3 reduce 4>>1= 2, 2< 3; 2 is 1 bit, 32-1=31 sameBits; 255.0.0.12/31; 3-2=1 step left;

255.0.0.14
1..1.0.0.00001110
firstBit/steps= 2, 2> 1 reduce 2>>1= 1, 1=1; 1 is 0th bit, 32 sameBits; 255.0.0.14/32; 1-1=0 step left; DONE;

How to Arrive:
* CIDR representation: Ip/sameLeftBits; 
* Count the righmost bit1, it has the number of ip transformations with SameLeftBits;
* Ex: 00100 = 4; [0..01(00), 0..01(01), 0..01(10), 0..01(11)]= 4 ips with 30 SameLeftBits;
* So this CIDR have 4 ips, if n=10 (# of ips after inputIp), 10-4= 6 more ips to find in further CIDRs.
* 
* Time: O(n), the range is the number of executions.
* Space: O(n); range number of res at max.
*/

import java.io.*;
import java.util.*;

public class IpToCIDR {

	/**
	 * Solution 2: Convert string ip to long num. Find rightmost 1bit. Convert back to StringIp with sameBits;
	 * Time: O(n), the range is the number of executions.
	 * Space: O(n); range number of res at max.
	 */
	public static List<String> ipToCIDR2(String ip, int range) {  
		List<String> res = new ArrayList<>();  
    long startIp = ipToLong(ip);

    while (range > 0) {  
    	// get the rightmost bit, it is the max number of steps to take for cur CIDR.
    	// Ex: 100 = 4; [100,101,110,111]= 4 steps;
      // ex 01101100: neg= 10010011+1= 10010100; 01101100 & 10010100 = 00000(100);
      long steps = startIp & (-startIp);
      // decrease bits, until the steps <= range.
      // ex: 100= 4; but only 3 steps left, 4/2=2; [00,11]; 3-2=1; 1 more step; 
      // CIDR= 1xx.xx.xx.2/31; instead of 1xx.xx.xx.4/30;   
      while (steps > range) {
      	steps >>= 1; 
      }
      // get the current decimal CIDR string.
      res.add(longToIP(startIp, (int)steps));  
      // moved steps closer to range end;
      startIp += steps;  
      // need less steps, closer to range end;
      range -= steps;  
    }
    
    return res;  
  }

  static String longToIP(long ip, int steps) {  
    int[] res = new int[4];
    // 255 == 11111111; & all 1s to preserve the bytes
    // ex: 255.10.10.18 & 0.0.0.255 = 0.0.0.18;
    res[0] = (int) (ip & 255);
    // right shift 8 bits to get next byte in ip.
    // ex: 255.10.10.18 >> 8 = 255.255.10.10 & 0.0.0.255 = 0.0.0.10;
    ip >>= 8; 
    res[1] = (int) (ip & 255); 
    // ex: 255.255.10.10 >> 8 = 255.255.255.10 & 0.0.0.255 = 0.0.0.10;
    ip >>= 8;
    res[2] = (int) (ip & 255);
    // ex: 255.255.255.10 >> 8 = 255.255.255.255 & 0.0.0.255 = 0.0.0.255;
    ip >>= 8;
    res[3] = (int) ip;
    // ln(steps) = log(steps) / log(2); Find the bits of firstOne: 000100;
    int sameBits = 32 - (int) (Math.log(steps) / Math.log(2));
    // 255 . 10 . 10 . 18;
    return res[3] + "." + res[2] + "." + res[1] + "." + res[0] + "/" + sameBits;
  }

  public static long ipToLong(String ip) {
		// split each 8 bits/ 1byte, 255 0 0 7;
		String[] ipStrs = ip.split("\\.");
		// 32 bits, 4 bytes;
		long[] ips = new long[4];
		for (int i = 0; i < 4; i++) {
			ips[i] = Long.valueOf(ipStrs[i]);
		}
		// concate into 1 num of 32 bits decimal representation.
    // 11111111.00000000.00000000.00000111 = 255.0.0.7
		return (ips[0] << 24) + (ips[1] << 16) + (ips[2] << 8) + ips[3];
	}
	
	/**
	 * Solution 1: 
	 */
	public static List<String> ipToCIDR(String ip, int range) {
		List<String> res = new ArrayList<>();
		long startIp = ipToLong(ip);
    long endIp = startIp + range - 1;

    while (startIp <= endIp) {
      // identify the location of first 1's from lower bit to higher bit of start IP
      // ex 01101100: neg= 10010011+1= 10010100; 01101100 & 10010100 = 00000(100)
      long locOfFirstOne = startIp & (-startIp);
      // 32bits - firstOne's bits, ln(n) = log(n)/log(2);
      int curMask = 32 - (int) (Math.log(locOfFirstOne) / Math.log(2));

      // calculate how many IP addresses between the start and end
      // e.g. between 1.1.1.111 and 1.1.1.120, there are 10 IP address
      // 3 bits to represent 8 IPs, from 1.1.1.112 to 1.1.1.119 (119 - 112 + 1 = 8)
      double currRange = Math.log(endIp - startIp + 1) / Math.log(2);
      int currRangeMask = 32 - (int) Math.floor(currRange);

      // why max?
      // if the currRangeMask is larger than curMask
      // which meres the numbers of IPs from start to end is smaller than mask range
      // so we can't use as many as bits we want to mask the start IP to avoid exceed the end IP
      // Otherwise, if currRangeMask is smaller than curMask, which meres number of IPs is larger than mask range
      // in this case we can use curMask to mask as many as IPs from start we want.
      curMask = Math.max(currRangeMask, curMask);

      // Add to results
      String curIp = longToIP(startIp);
      res.add(curIp + "/" + curMask);
      // We have already included 2^(32 - curMask) numbers of IP into result
      // So the next roundUp start must insert that number
      startIp += Math.pow(2, (32 - curMask));
    }
    return res;
	}

	private static String longToIP(long longIP) {
    StringBuffer sb = new StringBuffer("");
    sb.append(String.valueOf(longIP >>> 24));
    sb.append(".");
    sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
    sb.append(".");
    sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
    sb.append(".");
    sb.append(String.valueOf(longIP & 0x000000FF));

    return sb.toString();
	}


	public static void main(String[] args) {
		String ip = "255.0.0.5";
		int n = 10;
		List<String> res = ipToCIDR(ip, n);
		System.out.println(Arrays.toString(res.toArray(new String[res.size()])));
		List<String> res2= ipToCIDR2(ip, n);
		System.out.println(Arrays.toString(res2.toArray(new String[res2.size()])));
	}
}