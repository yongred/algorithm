/**
1.3 URLify: Write a method to replace all spaces in a string with '%20: You may assume that the string
has sufficient space at the end to hold the additional characters, and that you are given the "true"
length of the string. (Note: If implementing in Java, please use a character array so that you can
perform this operation in place.)
EXAMPLE
Input: "Mr John Smith     ", 13
Output: "Mr%20John%20Smith"
Hints: #53, #118
*/

import java.util.*;

public class URLify{
	//T(n+n), O(n), space O(n)
	public String urlify(char [] arr, int size){
		if(arr.length <= 0)
			return "";
		//size needed for %20 expand
		int newSize = size;
		//T(n)
		for(int i=0; i< size; i++){
			//expand by 2 each time find space, ' ' is 1, '%20' is 3
			if(arr[i] == ' ')
				newSize += 2;
		}
		//O(n) Space complexity
		char [] newArr = new char [newSize];
		int ind = 0; //index for new Arr
		//T(n)
		for(int i=0; i< size; i++){
			if(arr[i] != ' '){ //assign when not space
				newArr[ind] = arr[i];
			}else{  //when space
				newArr[ind] = '%';
				newArr[++ind] = '2';
				newArr[++ind] = '0';
			}
			ind++;
		}
		return new String(newArr); 
	}

	//using backward approach, without allocating new space, Space O(1)
	public void replaceSpace(char [] arr, int size){
		int newSize = size;

		for(int i=0; i< size; i++){
			//expand by 2 each time find space, ' ' is 1, '%20' is 3
			if(arr[i] == ' ')
				newSize += 2;
		}
		int ind = newSize -1;
		//assign backward, extra space allow us to assign to the same arr without replacing the original chars when looping
		for(int i = size-1; i >= 0; i--){
			if(arr[i] != ' '){
				arr[ind] = arr[i];
			}else{ //is space, assign backward
				arr[ind] = '0';
				arr[--ind] = '2';
				arr[--ind] = '%';
			}
			ind--;
		}

	}

	public static void main(String [] args){
		URLify obj = new URLify();
		String str = "abc def ggggg lalalal               ";
		int size = 21;
		//System.out.println( obj.urlify(str.toCharArray(), size) );
		char [] arr = str.toCharArray();
		obj.urlify(arr, size);
		System.out.println(new String(arr));
	}
}