/**
Rotate String:
Given a string and an offset, rotate string by offset. (rotate from left to right)
offset=0 => "abcdefg"
offset=1 => "gabcdef"
offset=2 => "fgabcde"
offset=3 => "efgabcd"
*/

import java.util.*;
import java.io.*;

public class RotateString{
	
	/*
	If A = 0 then GCD(A,B)=B, since the GCD(0,B)=B, and we can stop.  
	If B = 0 then GCD(A,B)=A, since the GCD(A,0)=A, and we can stop.  
	Write A in quotient remainder form (A = B⋅Q + R)
	Find GCD(B,R) using the Euclidean Algorithm since GCD(A,B) = GCD(B,R)
	ex: A= 9, B= 6;  
		9 = 6 * q + r = 6 * 1 + 3;
		6 = 3 * 1 + 0;  r=0, take b as answer (3);
	*/
	public static int GCD(int a, int b){
		if(b == 0)
			return a;
		return GCD(b, a % b);
	}

	/**
	where number of sets is equal to GCD of n and d and move the elements within sets.
	If GCD is 1 as is for the above example array (n = 7 and d =2), then elements will be moved within one set only, we just start with temp = arr[0] and keep moving arr[I+d] to arr[I] and finally store temp at the right place.

	Let arr[] be {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}
	a)  arr[] after this step --> {[4], 2, 3, [7], 5, 6, [10], 8, 9, [1], 11, 12}

	b)    Then in second set.
	          arr[] after this step --> {4, [5], 3, 7 [8], 6, 10, [11], 9, 1, [2], 12}

	c)    Finally in third set.
	          arr[] after this step --> {4 5 [6] 7 8 [9] 10 11 [12] 1 2 [3]}
   * @param str: an array of char
   * @param offset: an integer
   * @return: nothing
   */
  	public static void rotateString(char[] str, int offset) {
	    int len = str.length;
	    if(len <= 1)
	    	return;
	    offset = (offset >= len) ? offset % len : offset;
	    int sets = GCD(len, offset);
	    int curInd, replaceInd;
	    char temp;


	    //number of positions to rotate
	    for(int i=0; i< sets; i++){
	    	temp = str[i];
	    	curInd = i;
	    	while(true){
	    		//the place that will replace current
	    		replaceInd = curInd - offset;
	    		//ex: len=10 replaceInd= -2 -2+10=8; 0,1,..[8],9
	    		if(replaceInd < 0)
	    			replaceInd += len;
	    		//when back to first place, its being replaced by first loop, val in temp
	    		if(replaceInd == i)
	    			break;
	    		str[curInd] = str[replaceInd];
	    		curInd = replaceInd;
	    	}
	    	//in the place that needs firstInd val, which is store in temp
	    	str[curInd] = temp;
	    }
  	}

  	public static void main(String [] args){
		Scanner reader = new Scanner(System.in);
		
		System.out.println("input a string: ");
		String str = reader.nextLine();
		System.out.println("input a offset: ");
		int offset = reader.nextInt();

		char [] charArr = str.toCharArray();
		rotateString(charArr, offset);
		System.out.println(String.valueOf(charArr));
		
	}

}



