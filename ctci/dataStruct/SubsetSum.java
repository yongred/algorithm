package dataStruct;

import java.util.Stack;

public class SubsetSum {
	private Stack<Integer> stack = new Stack<Integer>();
	
	public void subsetSum(int[] A, int n, int sum){
		int tempsum = 0;
		
		if(tempsum == sum)
			System.out.println("ans is empty set, 0");
		
		for(int i=0; i< n-1; i++){
			tempsum = A[i];
			stack.push(A[i]);
			if(tempsum == sum){
				print(stack, sum);
				stack.clear();
				continue;
			}
			for(int j=i+1; j<n; j++){
				stack.push(A[j]);
				tempsum+= A[j];
				
				if(tempsum == sum){
					print(stack, sum);
					stack.clear();
				}
				else if (tempsum > sum){//tempsum > sum
					stack.pop();
					tempsum-= A[j-1];
				}
			}
		}
	}
	
	private void print(Stack<Integer> stack, int sum) {
        StringBuilder sb = new StringBuilder();
        sb.append(sum).append(" = ");
        for (Integer i : stack) {
            sb.append(i).append("+");
        }
        System.out.println(sb.deleteCharAt(sb.length() - 1).toString());
    }
	
	public static void main(String [] args){
	    int[] data = { 1, 3, 4, 5, 6, 2, 7, 8, 9, 10, 11, 13, 14, 15 };
	    SubsetSum obj = new SubsetSum();
	    obj.subsetSum(data, data.length, 15);
	}
}
