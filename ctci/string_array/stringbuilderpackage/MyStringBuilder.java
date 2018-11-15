/**
My String Builder Implementation
*/
/*
a+= b means
a = new StringBuilder()
    .append(a)
    .append(b)
    .toString();
*/
package stringbuilderpackage;
import java.util.*;

public class MyStringBuilder{
	char [] charArr;
	int sizeNow = 0;

	public MyStringBuilder(){
		charArr = new char[10];
	}
	public MyStringBuilder(int capacity){
		charArr = new char[capacity];
	}

	public MyStringBuilder append(String str){
		if(str == null)
			str = "null";
		int strlen = str.length();
		if(strlen <= 0) //empty str return current,jobs done
			return this;
		int capNeeded = strlen + sizeNow;
		if(capNeeded > charArr.length) //need more capacity
			expandCapacity(capNeeded);
		//now add str char by char to charArr
		for(int i=0; i< strlen; i++){
			charArr[sizeNow++] = str.charAt(i);
		}
		return this;
	}

	public void expandCapacity(int minCap){
		//double capacity
		int newCap = charArr.length * 2;
		if(charArr.length == 0)
			newCap = 1;
		if(newCap < 0){
			//integer overflow
			newCap = Integer.MAX_VALUE; //use largest posible integer
		}else if(newCap < minCap){
			//old str + new str len is bigger than double capacity
			newCap = minCap;
		}
		//copy old elements to new lengthen arr
		charArr = Arrays.copyOf(charArr, newCap);
	}

	public String toString(){
		//return substr of where there is elements 
		return new String(charArr, 0, sizeNow);
	}

	public char[] getCharArr(){
		return charArr;
	}
	public void setCharArr(char [] charArr){
		this.charArr = charArr;
	}
	public int getSize(){
		return sizeNow;
	}
	public void setSize(int sizeNow){
		this.sizeNow = sizeNow;
	}

	public static void main(String [] args){
		MyStringBuilder obj = new MyStringBuilder();
		obj.append("I am Going").append(" to MAKE IT ").append("to One of BIGGEST TECH COMPANY ").append(" WITH IN THIS YEAR!!");
		System.out.println(obj.toString());
	}
}