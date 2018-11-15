/**
1.7 Rotate Matrix: Given an image represented by an NxN matrix, 
where each pixel in the image is 4 bytes, 
write a method to rotate the image by 90 degrees. can you do this in place?
Hints: #51, #100
*/
/*
	1,1,1,2
	4     2
	4     2
	4,3,3,3     
*/
public class RotateMatrix{
	//O(n^2) time, T(n/4) * T(n-2i); O(1) space
	public boolean rotate(int [][] matrix){
		//matrix[y][x]
		if(matrix.length != matrix[0].length || matrix.length == 0)
			return false;
		int N = matrix.length;
		// one layer at a time, layer # is half of height or width
		//T(n/2)
		for(int layer = 0; layer < N/2; layer++){
			int first = layer;
			int last = N - 1 - layer;  //ex: 0..5, n=6, 6-1-0 = 5; 1..4, n=6, 6-1-1 =4;
			//T(n-1, n-3, n-5..1): use formula n(n+1)/2 = sigma(0-n)
			//Sigma(i=1 to n/2, n-1-2i) = (n/2)/2 * (n-1-2i+1); = (n^2)/4
			for(int i= first; i< last; i++){  //{[1,2,3],4}
				// 1..4, i=3, 3-1 = 2; 2 steps taken
				int steps = i - first; 
				//tmp = top; y is first row, x = i looping
				int tmp = matrix[first][i]; 
				//top = left; left: y is last to first+1, x is first columns; to move from last to first+1, last-steps
				matrix[first][i] = matrix[last-steps][first];
				//left = bottom; bottom: y is last, x is last to first+1
				matrix[last-steps][first] = matrix[last][last-steps];
				//bottom = right; right: y = i looping, x is last
				matrix[last][last-steps] = matrix[i][last];
				//right = top;
				matrix[i][last] = tmp;
			}
		}
		return true;
	}

	public void printMatrix(int [][] matrix){
		int n = matrix.length;
		for(int y=0; y < n; y++){
			for(int x= 0; x< n; x++){
				System.out.print(matrix[y][x]+ " ");
			}
			System.out.println();
		}
	}

	public static void main(String [] args){
		RotateMatrix obj = new RotateMatrix();
		int [][] matrix = {{1,1,1,1,1,2},
						   {4,5,5,5,6,2},
						   {4,8,9,10,6,2},
						   {4,8,12,11,6,2},
						   {4,8,7,7,7,2},
						   {4,3,3,3,3,3}};
		System.out.println(obj.rotate(matrix));
		obj.printMatrix(matrix);

	}
}