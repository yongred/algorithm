/**
8.1 Triple Step: A child is running up a staircase with n steps and can hop either 1 step, 2 steps, or 3
steps at a time. Implement a method to count how many possible ways the child can run up the
stairs.
Hints: # 152, # 178, #217, #237, #262, #359
*/


public class TripleStep{
	/* 
	solution:
		-The person can reach n’th stair from either (n-1)’th stair or from (n-2)’th stair, or from (n-3)’th stair.
		ex: n=10; 9+1 = 10 steps, 8+2 = 10; 7+3 = 10;  find # of ways to 7,8,9 steps and add them together,
			and you find # of ways to 10 steps.
			10 = 9(3+3+3)+1, or 9(2+2+2+3)+1 ..etc; 10= 8(2+4+2)+1, or 8(3+4+1)+1 ..etc
										10
						9				8				7
					8 7 6		7	6 5 	6 5 4
				...

		-brute force: time: O(3^n);  space: O(n) 
	*/
	public int countWays(int n){
		//base cases
		if(n < 1) return 0;
		if(n == 1) return 1; 
		if(n == 2) return 2; // 1+1, 2
		if(n == 3) return 4; // 1+1+1, 1+2, 2+1, 2
		System.out.println("count " + n);
		return countWays(n-1) + countWays(n-2) + countWays(n-3);
		
	}

	/*
	using dynamic (memorization) programming;  save already calculated values to mem[n]
	 											10
						9						[8]				[7]
					8 [7] [6]		 						
				7
			6

	Time: O(n);  Space: O(n); 
	*/
	public int dynamicWays(int n){
		int [] mem = new int [n+1]; //cached till n
		return dynamicWays(n, mem);
	}

	public int dynamicWays(int n, int [] mem){
		//base cases
		if(n < 1) return 0;
		if(n == 1) return 1; 
		if(n == 2) return 2; // 1+1, 2
		if(n == 3) return 4; // 1+1+1, 1+2, 2+1, 2
		if(mem[n] == 0){  //not memorized yet
			System.out.println("mem " + n);
			mem[n] = dynamicWays(n-1, mem) + dynamicWays(n-2, mem) + dynamicWays(n-3, mem);
		}
		return mem[n];
	}

	/*
	iterative solution:
		-cached val = last 3 added together; so just iterate through and add the last 3 index val
		Time: O(n); Space: O(n)
	*/
	public int iterWays(int n){
		//base cases
		if(n < 1) return 0;
		if(n == 1) return 1; 
		if(n == 2) return 2; // 1+1, 2
		if(n == 3) return 4; // 1+1+1, 1+2, 2+1, 2
		int [] mem = new int[n+1];
		mem[0]=0; mem[1]=1; mem[2]=2; mem[3]=4;  //base values
		for(int i=4; i< n+1; i++){
			mem[i] = mem[i-1] + mem[i-2] + mem[i-3];  //cur val = last 3 added together
		}
		return mem[n];
	}

	/*
	optimize space iterative solution:
		similar to above; but instead using arr, just use integers to store the previous 3 vals

	Time: O(n);  Space: O(1)
	*/

	public int iterOptWays(int n){
		//base cases
		if(n < 1) return 0;
		if(n == 1) return 1; 
		if(n == 2) return 2; // 1+1, 2
		if(n == 3) return 4; // 1+1+1, 1+2, 2+1, 2
		int cur = 0, n1 = 4, n2 = 2, n3 = 1;  //base values
		for(int i=4; i< n+1; i++){
			cur = n1 + n2 + n3;   //cur val = last 3 added together
			//f3 + f4 + f5,  becomes f4 + f5 + f6, iterate 1 position
			n3 = n2;  
			n2 = n1; 
			n1 = cur;
		}
		return cur;
	}

	public static void main(String [] args){
		TripleStep obj = new TripleStep();
		int n = 13;
		System.out.println("countWays " + obj.countWays(n));
		System.out.println("dynamicWays " + obj.dynamicWays(n));
		System.out.println("iterWays " + obj.iterWays(n));
		System.out.println("iterOptWays " + obj.iterOptWays(n));
	}

}