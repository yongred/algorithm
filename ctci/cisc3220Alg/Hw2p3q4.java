package cisc3220Alg;

public class Hw2p3q4 {
	public void recursiveSelect(int [] A, int str){
		if(str >= A.length-1)
			return;
		int minInd = str;
		for(int i= str+1; i< A.length; i++){
			if(A[i] < A[minInd])
				minInd = i;
		}
		if(minInd != str){
			int temp = A[str];
			A[str] = A[minInd];
			A[minInd] = temp;
		}
		recursiveSelect(A, str+1);
	}
	public static void main(String [] args){
		int [] myInts = {10,30,80,0,40,70,50,60,20};
		Hw2p3q4 obj = new Hw2p3q4();
		obj.recursiveSelect(myInts, 0);

		for(int i : myInts){
			System.out.print(i + ",");
		}
	}
}
