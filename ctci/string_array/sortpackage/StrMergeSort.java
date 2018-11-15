/**
String merge sort
*/

package sortpackage;
import java.util.*;

public class StrMergeSort{

	public String strMergeSort(String str, int beg, int end){
		String theStr = str;
		if(beg < end){
			int mid = (beg + end) / 2;
			theStr = strMergeSort(theStr, beg, mid);
			theStr = strMergeSort(theStr, mid+1, end);
			theStr = merge(theStr, beg, mid, end);
		}
		return theStr;
	}

	public String merge(String str, int beg, int mid, int end){
		System.out.println(str );
		char [] strArr = str.toCharArray();
		//needed a sentinal for comparison
		char [] left = new char[mid - beg + 2];
		char [] right = new char[end - mid + 1];
		left[left.length -1] = (char)Integer.MAX_VALUE;
		right[right.length -1] = (char)Integer.MAX_VALUE;
		//slit in middle, ex: cdab to (cb), (ad)
		for(int i= 0; i< left.length -1; i++){
			left[i] = strArr[beg + i];
		}
		for(int j= 0; j< right.length -1; j++){
			right[j] = strArr[mid + j + 1];
		}
		//compare and merge, sentinal is largest value so the smaller arr value will be put in
		int lfInd = 0, rtInd = 0;
		for(int x= beg; x<= end; x++){
			
			if((int)left[lfInd] <= (int)right[rtInd]){
				
				strArr[x] = left[lfInd];
				lfInd++; 
			}else{
				strArr[x] = right[rtInd];
				rtInd++;
			}
		}
		//System.out.println(strArr);
		return new String(strArr);
	}

	public static void main(String [] args){
		StrMergeSort obj = new StrMergeSort();
		String str = "fgdbace";
		str = obj.strMergeSort(str, 0, str.length() -1);
		System.out.println(str);
	}
}