/**
393. UTF-8 Validation
A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:

For 1-byte character, the first bit is a 0, followed by its unicode code.
For n-bytes character, the first n-bits are all one's, the n+1 bit is 0, followed by n-1 bytes with most significant 2 bits being 10.
This is how the UTF-8 encoding would work:

   Char. number range  |        UTF-8 octet sequence
      (hexadecimal)    |              (binary)
   --------------------+---------------------------------------------
   0000 0000-0000 007F | 0xxxxxxx
   0000 0080-0000 07FF | 110xxxxx 10xxxxxx
   0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
   0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
Given an array of integers representing the data, return whether it is a valid utf-8 encoding.

Note:
The input is an array of integers. Only the least significant 8 bits of each integer is used to store the data. This means each integer represents only 1 byte of data.

Example 1:

data = [197, 130, 1], which represents the octet sequence: 11000101 10000010 00000001.

Return true.
It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte character.
Example 2:

data = [235, 140, 4], which represented the octet sequence: 11101011 10001100 00000100.

Return false.
The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes character.
The next byte is a continuation byte which starts with 10 and that's correct.
But the second continuation byte does not start with 10, so it is invalid.
*/

/**
Solution 1: use the nums representing 1000.., 1100.., 1110.. etc to find out how many bytes.

How to Arrive:
* To find out how many 1s on the left, we can simpy create a mask that has 1 more 1s on the leading leftside, AND the num to see if it is == to the desired byte.
Ex: 
10,000,000 = 128; 
11,000,000 = 128 + 64 = 192; 
11,100,000 = 192 + 32 = 224
11,110,000 = 224 + 16 = 240
11,111,000 = 240 + 8 = 248;

1 Byte:
10,000,000 = 128; (10.. & 0xx) = 0;
`((data[i] & 128) == 0)`
2 byte char
11,100,000 = 224; (11100.. & 110xx) = 11000000 (192);
`((data[i] & 224) == 192)`
3 byte char
11,110,000 = 240; (111100.. & 1110xx) = 11100.. (224);
... Etc.

* Also, if num > 255, it's false, b/c we looking for Byte 8bits, stated in the question.
* We found the Bytes of the num, now we just need to know if number of 10xxx.. following is correct.
* We should have Bytes - 1 followers 10xxx.. 
* So we loop further to next bytes-1 nums and using this condition to check: `((trailingByte & 192) != 128)`
* 11,000,000 = 192; (1100.. & 10xx..) = 10000000 (128);
* AND the 1100.. mask with num, if it is 10xxx, then it is correct.
* If i + bytes - 1 > datas.length; Then there is not enough 10xxx..
* Time: O(n)
* space: O(1)

------------------------

Solution 2: Count the left leading 1s to determine the Bytes.
How to Arrive:
* Instead of using 4 conditions to check each 1-4 masks/bytes. We can simply count the left leading 1s.
* To count the left 1s, we Right Shift the target bits to 2^0 rightmost pos, Then we AND the res with 1. If the rightmost bit is 1, then it will = to 1, otherwise = 0;
Ex:  shift n >> i times; 10,000,000 >> 7 = 1; 1 & 1 = 1;
		 11,000,000 >> 6 = ..0011; 0011 & 0001 = 1;
* After knowing the Bytes, we check if Bytes > 4, or the counts == 1; Both is false;
* B/c we are looking for 1-4 bytes only, and 1 bytes should have 0 counts of leading 1s. 0xxx = 1 byte.
* Then we do the same as Prev Solution. Check for 10xxx.. using Counts method. 
* Time: O(n)
* Space: O(1)

*/

class Utf8Validation {

	/**
	 * Solution 2: Count the left leading 1s
	 * Time: O(n)
	 * Space: O(1)
	 */
	public boolean validUtf8(int[] data) {
    if (data == null || data.length == 0) {
      return false;
    }
    // loop bytes
    for (int i = 0; i < data.length; i++) {
      // count this num's left 1s.
      // if 0 1s on left leading, then it is 1 byte.
      int bytesCount = countLeftOnes(data[i]);
      // 1 to 4 bytes only. Where 1 byte is 0x..; 10xx false.
      if (bytesCount > 4 || bytesCount == 1) {
        return false;
      }
      // look for bytesCount-1 # of 10xxx after this byte.
      for (int j = 1; j < bytesCount; j++) {
        // check enough nums for follower bytes.
        if (i + j >= data.length) {
          return false;
        }
        // 10xx byte.
        int followingByte = data[i + j];
        if (countLeftOnes(followingByte) != 1) {
          return false;
        }
      }
      // update i
      if (bytesCount > 1) {
        i += (bytesCount - 1);
      }
    }
    return true;
  }
  
  public int countLeftOnes(int num) {
    int count = 0;
    for (int i = 7; i >= 0; i--) {
      // shift n >> i times; 10,000,000 >> 7 = 1; 1 & 1 = 1;
      // 11,000,000 >> 6 = ..0011; 0011 & 0001 = 1;
      if ((num >> i & 1) != 1) {
        // stop at 1st 0 on left.
        return count;
      }
      count++;
    }
    return count;
  }

  /**
   * Solution 1: use leading 1s masks int to check 1 to 4 bytes.
   * Time: O(n)
   * space: O(1)
   */
  public boolean validUtf8(int[] data) {
    if (data == null || data.length == 0) {
      return false;
    }
    // create compare masks.
    // 10,000,000 = 128; 
    // 11,000,000 = 128 + 64 = 192; 
    // 11,100,000 = 192 + 32 = 224
    // 11,110,000 = 224 + 16 = 240
    // 11,111,000 = 240 + 8 = 248;
    // loop data
    for (int i = 0; i < data.length; i++) {
      int bytesCount = 0;
      // 0-255, 8 bits 1 byte. 0-255 can be in a byte.
      if (data[i] > 255) {
        return false;
      } else if ((data[i] & 128) == 0) {
        // checking 1 byte char.
        // 10,000,000 = 128; (10.. & 0xx) = 0;
        bytesCount = 1;
      } else if ((data[i] & 224) == 192) {
        // 2 byte char
        // 11,100,000 = 224; (11100.. & 110xx) = 11000000 (192);
        bytesCount = 2;
      } else if ((data[i] & 240) == 224) {
        // 3 byte char
        // 11,110,000 = 240; (111100.. & 1110xx) = 11100.. (224);
        bytesCount = 3;
      } else if ((data[i] & 248) == 240) {
        // 4 bytes char.
        // 11,111,000 = 248; (11111000 & 11110xx) = 11110000 (240);
        bytesCount = 4;
      } else {
        return false;
      }
      // check for 10xx.. byte ints after leading byte num.
      for (int j = 1; j < bytesCount; j++) {
        // not enough trailingByte nums in data.
        // Ex: [1110xxxx, 10xx..] need 1 more for 3 bytes.
        if (i + j >= data.length) {
          return false;
        }
        int trailingByte = data[i + j];
        // check is 10xx..
        if ((trailingByte & 192) != 128) {
          // 11,000,000 = 192; (1100.. & 10xx..) = 10000000 (128);
          return false;
        }
      }
      // update i; ex: i0 + (b3-1) = 2 (last idx of cur bytes' num);
      i += (bytesCount - 1);
    }
    return true;
  }

}