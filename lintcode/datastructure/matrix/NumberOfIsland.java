/**
Number of Islands:
Given a boolean 2D matrix, 0 is represented as the sea, 1 is represented as the island. If two 1 is adjacent, we consider them in the same island. We only consider up/down/left/right adjacent.

Find the number of islands.

Example
Given graph:

[
  [1, 1, 0, 0, 0],
  [0, 1, 0, 0, 1],
  [0, 0, 0, 1, 1],
  [0, 0, 0, 0, 0],
  [0, 0, 0, 0, 1]
]
return 3.
*/

import java.util.*;
import java.io.*;

public class NumberOfIsland{
	/*
     * @param grid: a boolean 2D matrix
     * @return: an integer
     * Solution: when ecounter 1, using DFS, turn connected 1s into 0s;
     * Time: O(Rows * Cols);
     */
    public static int numIslands(boolean[][] grid) {
    	int H = grid.length; //height, rows
    	if(H <= 0)
    		return 0;
    	int W = grid[0].length; //weight, cols
    	if(W <= 0)
    		return 0;
    	
        int count = 0;

        for (int i=0; i< H; i++) {
        	for (int j=0; j< W; j++) {
        		//DFS, turn connected 1s into 0s
        		if(grid[i][j]){
        			DFS(grid, i, j);
        			count++;
        		}
        	}
        }

        return count;
    }

    //DFS, turn connected true grids into false.
    public static void DFS(boolean[][] grid, int i, int j){
    	int H = grid.length; //height, rows
    	int W = grid[0].length; //weight, cols
    	// check if indexes is out of bounce, or this grid is false
    	if(i < 0 || i >= H || j < 0 || j >= W || !grid[i][j])
    		return;
    	//visited, turn this grid to false
    	grid[i][j] = false;

    	DFS(grid, i-1, j); //up
    	DFS(grid, i+1, j); //down
    	DFS(grid, i, j-1); //left
    	DFS(grid, i, j+1); //right

    }


    public static void main(String [] args){
    	boolean [][] mat = {
		  {true, true, false, false, false},
		  {false, true, false, false, true},
		  {false, false, false, true, true},
		  {false, false, false, false, false},
		  {false, false, false, false, true}
		};
		

		System.out.println(numIslands(mat));

    }

}


