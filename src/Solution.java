import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int rows = in.nextInt();
        int cols = in.nextInt();
        int rotations = in.nextInt();
        int [][] arr = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                arr[r][c] = in.nextInt();
            }
        }
        //printMatrix(arr);
        int [][] rotatedArr = rotateMatrix(arr, rotations);
        printMatrix(rotatedArr);
    }

    public static int[][] rotateMatrix(int [][] arr, int rotations) {
        if (arr == null) {
            return null;
        }
        int rows = arr.length;
        if (rows == 0) {
            return arr; //rotating size zero array
        }
        int cols = arr[0].length;

        int [][] newArr = new int[rows][cols];
        for (int inc = 0; inc < Math.ceil(Math.min(rows, cols)/2.0); inc++) {
            Position pos = null;
            int startVal = 0;
            while (true) {
                if (pos == null) {
                    pos = new Position(inc, inc);
                    startVal = arr[inc][inc];
                }
                Position nextPos = getNext(inc, rows - inc - 1, inc, cols - inc - 1, pos, rotations);
                Position oneStep = getNext(inc, rows - inc - 1, inc, cols - inc - 1, pos, 1);
                //System.out.println("i:"+inc+" "+nextPos.r +" "+nextPos.c);
                newArr[pos.r][pos.c] = arr[nextPos.r][nextPos.c];
                pos = oneStep;
                if (oneStep.r == inc && oneStep.c == inc) {
                    break;
                }
            }
        }
        return newArr;
    }

    static class Position {
        int r;
        int c;
        public Position(int row, int col) {
            this.r = row;
            this.c = col;
        }
    }
    private static Position getNext(int minRow, int maxRow, int minCol, int maxCol, Position pos, int rot) {
        if (rot == 0) {
            return pos;
        }
        if (pos.r == minRow && pos.c == minCol) {
            Position nextPos = new Position(minRow, minCol+1);
            return getNext(minRow, maxRow, minCol, maxCol, nextPos, rot-1);
        }
        if (pos.r == minRow && pos.c == maxCol) {
            Position nextPos = new Position(minRow+1, maxCol);
            return getNext(minRow, maxRow, minCol, maxCol, nextPos, rot-1);
        }
        if (pos.r == maxRow && pos.c == maxCol) {
            Position nextPos = new Position(maxRow, maxCol-1);
            return getNext(minRow, maxRow, minCol, maxCol, nextPos, rot-1);
        }
        if (pos.r == maxRow && pos.c == minCol) {
            Position nextPos = new Position(maxRow-1, minCol);
            return getNext(minRow, maxRow, minCol, maxCol, nextPos, rot-1);
        }
        //System.out.println(rot+" "+pos.r+" "+pos.c);
        if (pos.c == minCol) {
            int newRot = 0;
            int newRow = pos.r - rot;
            if (minRow > newRow) {
                newRot = rot - (pos.r - minRow);
                newRow = minRow;
            }
            Position nextPos = new Position(newRow, minCol);
            return getNext(minRow, maxRow, minCol, maxCol, nextPos, newRot);
        }
        if (pos.r == minRow) {
            int newRot = 0;
            int newCol = pos.c + rot;
            if (maxCol < newCol) {
                newRot = rot - (maxCol - pos.c);
                newCol = maxCol;
            }
            Position nextPos = new Position(minRow, newCol);
            return getNext(minRow, maxRow, minCol, maxCol, nextPos, newRot);
        }
        if (pos.c == maxCol) {
            int newRot = 0;
            int newRow = pos.r + rot;
            if (maxRow < newRow) {
                newRot = rot - (maxRow - pos.r);
                newRow = maxRow;
            }
            Position nextPos = new Position(newRow, maxCol);
            return getNext(minRow, maxRow, minCol, maxCol, nextPos, newRot);
        }
        if (pos.r == maxRow) {
            int newRot = 0;
            int newCol = pos.c - rot;
            if (minCol > newCol) {
                newRot = rot - (pos.c - minCol);
                newCol = minCol;
            }
            Position nextPos = new Position(maxRow, newCol);
            return getNext(minRow, maxRow, minCol, maxCol, nextPos, newRot);
        }
        return null;
    }

    public static void printMatrix(int [][] arr) {
        if (arr == null) {
            System.out.println("Null array");
        }
        int rows = arr.length;
        int cols = arr[0].length;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(arr[r][c] + " ");
            }
            System.out.println();
        }
    }
}