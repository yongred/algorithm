/**
 * Given a 2D grid having radio towers in some cells, mountains in some and the rest are empty.
 * A radio tower signal can travel in 4 different directions recursively until it hits a mountain in that direction. What all radio towers can hear each other?
 * I think it's asking if towers have a path to all other towers.
 */

/*
Similar as WallsAndGates problem, using BFS

0 = tower
-1 = mountain
INF = empty

Example
Given the 2D grid:

INF  -1  0  INF
INF INF INF  -1
INF  -1 INF  -1
  0  -1 INF  0

return the result: True
Mark 0 (tower) being reached as 1, if no 0 on the graph

  e  -1   1   e
  e   e   e  -1
  e  -1   e  -1
  1  -1   e   1

For this question, if there's INF left, then return false, no INF means every empty able to reach tower

- Assume this question is asking if all empty cells can reach a tower
 */
package problem_solving;

import java.util.ArrayDeque;
import java.util.Deque;

public class RadioTowerSignals {

    class GridPosition {
        public int row;
        public int col;

        public GridPosition(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    int[][] directions = new int[][] {{1,0}, {-1,0}, {0,1}, {0,-1}};

    private static final int MARK_REACHED_TOWER = -2;

    public boolean areAllTowersReachable(int[][] gridMap, int travelDistanceLimit) {
        areAllTowersReached(gridMap);
        if (gridMap.length == 0) {
            return true;
        }
        int height = gridMap.length;
        int width = gridMap[0].length;
        boolean[][] visited = new boolean[width][height];

        outerLoop:
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (gridMap[row][col] == 0) {
                    breathFirstSearch(gridMap, row, col, visited, travelDistanceLimit);
                    break outerLoop;
                }
            }
        }

        return areAllTowersReached(gridMap);
    }

    private void breathFirstSearch(int[][] gridMap, int row, int col,
                                   boolean[][] visited, int travelDistanceLimit) {
        Deque<GridPosition> positionQueue = new ArrayDeque<>();
        int height = gridMap.length;
        int width = gridMap[0].length;
        int distanceTraveled = 0;
        GridPosition towerPosition = new GridPosition(row, col);

        positionQueue.add(towerPosition);
        gridMap[row][col] = MARK_REACHED_TOWER;
        visited[row][col] = true;

        while (!positionQueue.isEmpty()) {
            distanceTraveled++;
            if (distanceTraveled > travelDistanceLimit) {
                break;
            }

            int gridsInThisLayer = positionQueue.size();
            for (int i = 0; i < gridsInThisLayer; i++) {
                GridPosition curPosition = positionQueue.poll();
                for (int dir = 0; dir < 4; dir++) {
                    int movedToRow = curPosition.row + directions[dir][0];
                    int movedToCol = curPosition.col + directions[dir][1];

                    if (movedToRow >= 0 && movedToRow < height &&
                            movedToCol >= 0 && movedToCol < width &&
                            !visited[movedToRow][movedToCol] && gridMap[movedToRow][movedToCol] != -1) {

                        positionQueue.add(new GridPosition(movedToRow, movedToCol));
                        if (gridMap[movedToRow][movedToCol] == 0) {
                            gridMap[movedToRow][movedToCol] = MARK_REACHED_TOWER;
                        } else {
                            gridMap[movedToRow][movedToCol] = distanceTraveled;
                        }
                        visited[movedToRow][movedToCol] = true;
                    }
                }
            }
        }
    }

    private boolean areAllTowersReached(int[][] gridMap) {
        boolean result = true;
        for (int row = 0; row < gridMap.length; row++) {
            for (int col = 0; col < gridMap[0].length; col++) {
                System.out.print(gridMap[row][col] + ", ");
                if (col == gridMap[0].length - 1) {
                    System.out.print("\n");
                }
                if (gridMap[row][col] == 0) {
                    result = false;
                }
            }
        }
        System.out.println();
        return result;
    }

    public static void main(String[] args) {
        int[][] gridMap = new int[][] {
                {Integer.MAX_VALUE, -1, 0, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, -1},
                {Integer.MAX_VALUE, -1, Integer.MAX_VALUE, -1},
                {0, -1, Integer.MAX_VALUE, 0}
        };
        RadioTowerSignals object = new RadioTowerSignals();
        int distanceLimit = 4;
        boolean result = object.areAllTowersReachable(gridMap, distanceLimit);
        System.out.println("Is all towers reachable: " + result);
    }
}
