/**
8.2 Robot in a Grid: Imagine a robot sitting on the upper left corner of grid with r rows and c columns.
The robot can only move in two directions, right and down, but certain cells are "off limits" such that
the robot cannot step on them. Design an algorithm to find a path for the robot from the top left to
the bottom right.
Hints: #331, #360, #388
*/
import java.util.*;

public class RobotGrid{


	public class Grid{
		public int r;
		public int c;
		public Grid(int r, int c){
			this.r = r;
			this.c = c;
		}
	}

	/*
	brute force: 
		-to get the path of (r,c), you can get the path of (r-1,c) or (r,c-1);
		test if left or top path can reach target.
		worst case: check each blockage twice
		Time: O(2^r+c); Space: O(r+c);
	*/

	public ArrayList<Grid> findPath(int [][] maze, int r, int c){
		ArrayList<Grid> grids = new ArrayList<Grid>(); 
		
		if(!findPath(maze, r-1, c-1, grids))
			return null;
		return grids;
	}

	public boolean findPath(int [][] maze, int r, int c, ArrayList<Grid> grids){
		System.out.println(r + ", " + c);
		if(r < 0 || c < 0 || maze[r][c] == Integer.MIN_VALUE){
			return false;
		}
		Grid curGrid = new Grid(r,c);
		if(r==0 && c==0){
			grids.add(curGrid);
			System.out.println("find");
			return true;
		}

		if(findPath(maze, r, c-1, grids) || findPath(maze, r-1, c, grids)){
			grids.add(curGrid);
			return true;
		}

		return false;
		
	}

	/*
	optimized solution: 
		-repeated grids: ex: [5,5] -> [4,5], [5,4]; [4,5] -> [3,5], [4,4]; [5,4] -> [4,4], [5,3];
		[4,4] is repeated.
	 	cache the checked failed grids;
	*/


	public ArrayList<Grid> findPath2(int [][] maze, int r, int c){
		ArrayList<Grid> grids = new ArrayList<Grid>(); 
		boolean [][] failedGrids = new boolean[r][c]; 
		if(!findPath2(maze, r-1, c-1, grids, failedGrids))
			return null;
		return grids;
	}

	public boolean findPath2(int [][] maze, int r, int c, ArrayList<Grid> grids, boolean [][] failedGrids){
		System.out.println("sec: " + r + ", " + c);
		if(r < 0 || c < 0 || maze[r][c] == Integer.MIN_VALUE){
			if(r>=0 && c>=0)
				failedGrids[r][c] = true;
			return false;
		}
		Grid curGrid = new Grid(r,c);

		if(r==0 && c==0){
			grids.add(curGrid);
			System.out.println("find");
			return true;
		}
		System.out.println(failedGrids[r][c]);

		if(failedGrids[r][c]){
			System.out.println("contains");
			return false;
		}

		if(findPath2(maze, r, c-1, grids, failedGrids) || findPath2(maze, r-1, c, grids, failedGrids)){
			grids.add(curGrid);
			return true;
		}

		failedGrids[r][c] = true;
		return false;
		
	}

	public int [][] generateMaze(int row, int col){
		int [][] maze = new int[row][col];
		maze[row-1][col-3] = Integer.MIN_VALUE;
		maze[row-4][col-1] = Integer.MIN_VALUE;
		maze[row-4][col-2] = Integer.MIN_VALUE;
		maze[row-3][col-4] = Integer.MIN_VALUE;
		maze[row-2][col-5] = Integer.MIN_VALUE;
		return maze;
	}

	public static void main(String [] args){
		RobotGrid obj = new RobotGrid();
		int r = 5;
		int c = 6;
		int [][] maze = obj.generateMaze(r, c);
		ArrayList<Grid> path = obj.findPath(maze, r, c);
		ArrayList<Grid> path2 = obj.findPath2(maze, r, c);

		for(Grid grid : path){
			System.out.println(grid.r + ", " + grid.c);
		}
		for(Grid grid : path2){
			System.out.println("sec: " + grid.r + ", " + grid.c);
		}

		HashSet<Grid> hs = new HashSet<Grid>();
		Grid d = obj.new Grid(7,8);
		hs.add(d);
		System.out.println(hs.contains(obj.new Grid(7,8)));
	}



}