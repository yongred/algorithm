/**
1.8 Zero Matrix: Write an algorithm such that if an element in an MxN matrix is 0, its entire row and
column are set to O.
Hints: # 17, #74, #102
*/

public class ZeroMatrix{
	//T(3n*m) = O(n*m) times; O(1) space: use matrix as storage
	public void clearZero(int[][] matrix){
		//matrix[rows][cols]
		int n = matrix[0].length; //cols
		int m = matrix.length; //rows
		boolean firstRow = false;
		boolean firstCol = false;
		//check if first row has zero
		for(int c=0; c< n; c++){
			if(matrix[0][c] == 0){
				firstRow = true;
				break;
			}
		}
		//check if first col has zero
		for(int r=0; r< m; r++){
			if(matrix[r][0] == 0){
				firstCol = true;
				break;
			}
		}
		//use first row and col to store info for which row/col to clear, T(n*m)
		for(int r = 1; r < m; r++){
			for(int c=1; c< n; c++){
				if(matrix[r][c] == 0){
					matrix[0][c] = 0; //first row, c col set to 0
					matrix[r][0] = 0; //first col, r row set to 0
				}
			}
		}
		//check and clear rows, T(m*n) worst case
		for(int r=1; r< m; r++){
			if(matrix[r][0] == 0){  //first col, r rows
				clearRow(matrix, r);
			}
		}
		//check and clear cols, T(n*m) worst case
		for(int c=1; c< n; c++){
			if(matrix[0][c] == 0){  //first row, c cols
				clearCol(matrix, c);
			}
		}
		//clear first row and col last, avoid clearing everything
		if(firstRow){
			clearRow(matrix,0);
		}
		if(firstCol){
			clearCol(matrix,0);
		}
	}//end of clearZero

	public void clearRow(int [][] matrix, int r){
		int n = matrix[0].length; //cols
		for(int c =0; c < n; c++)  //clear whole row
			matrix[r][c] = 0;
	}
	public void clearCol(int[][] matrix, int c){
		int m = matrix.length; //rows
		for(int r =0; r < m; r++)  //clear whole col
			matrix[r][c] = 0;
	}

	public void printMatrix(int [][] matrix){
		int n = matrix[0].length;
		int m = matrix.length;
		for(int r=0; r < m; r++){
			for(int c= 0; c< n; c++){
				System.out.print(matrix[r][c]+ " ");
			}
			System.out.println();
		}
	}

	public static void main(String [] args){
		ZeroMatrix obj = new ZeroMatrix();
		int [][] matrix = {{1,1,1,1,1,2},
						   {4,5,0,5,6,2},
						   {4,8,9,10,6,2},
						   {4,8,12,0,6,0},
						   {4,8,7,7,7,2},
						   {0,3,3,3,3,3},
						   {4,3,3,3,3,3}};
		obj.clearZero(matrix);
		obj.printMatrix(matrix);
	}
}