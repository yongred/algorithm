/**
Rotate Image:
You are given an n x n 2D matrix representing an image.
Rotate the image by 90 degrees (clockwise).

	1,1,1,2
	4     2
	4     2
	4,3,3,3     

Example
Given a matrix

[
    [1,2],
    [3,4]
]
rotate it by 90 degrees (clockwise), return

[
    [3,1],
    [4,2]
]
*/

import java.util.*;
import java.io.*;

public class RotateImage{
	/*
	* Solution: 
	* -loop layer by layer; and rotate the 4 sides of that layer;
	* O(n^2) time, T(n/4) * T(n-2i); O(1) space
	*/
	public static void rotate(int [][] mat){
		int N = mat.length; //width x height
		//# of layers == N/2
		for (int layer = 0; layer < N/2; layer++) {
			//starting index of current layer; ex: 2nd layer start with 1
			int first = layer; 
			//last index of current layer; decrease by 1 each layer down;
			int last = N - 1 - layer; 
			//loop elements of each line in current layer; last index is start of another line
			for (int i= first; i< last; i++) {
				//step taken, to calculates decrement index or reverse i (last-step) 
				int step = i - first; 

				int tmp = mat[first][i]; //top -> temp
				mat[first][i] = mat[last-step][first]; //left->top
				mat[last-step][first] = mat[last][last-step]; //bottom->left
				mat[last][last-step] = mat[i][last]; //right->bottom
				mat[i][last] = tmp; //top->right

			}
		}

	}

	public static void printMatrix(int [][] matrix){
		int n = matrix.length;
		for(int y=0; y < n; y++){
			for(int x= 0; x< n; x++){
				System.out.print(matrix[y][x]+ " ");
			}
			System.out.println();
		}
	}

	public static void main(String [] args){
		
		int [][] matrix = {{1,1,1,1,1,2},
						   {4,5,5,5,6,2},
						   {4,8,9,10,6,2},
						   {4,8,12,11,6,2},
						   {4,8,7,7,7,2},
						   {4,3,3,3,3,3}};
		printMatrix(matrix);
		System.out.println();
		rotate(matrix);
		printMatrix(matrix);

	}
}