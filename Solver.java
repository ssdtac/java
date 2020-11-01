import javax.swing.*;

public class Solver {

    static final int ROWS = 9;
    static final int COLUMNS = 9;

    public static void main(String[] args) {

        int[][] sudokuGrid = { // 0 means the square is empty
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 3, 0, 8, 5},
                {0, 0, 1, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 5, 0, 7, 0, 0, 0},
                {0, 0, 4, 0, 0, 0, 1, 0, 0},
                {0, 9, 0, 0, 0, 0, 0, 0, 0},
                {5, 0, 0, 0, 0, 0, 0, 7, 3},
                {0, 0, 2, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 9}
        };



        System.out.println("Initial Sudoku is: ");
        printSudoku(sudokuGrid);

        if (sudokuSolver(sudokuGrid)) {
            System.out.println("Solved Sudoku is:");
            printSudoku(sudokuGrid);
        } else System.out.println("There is no solution.");
    }

    static void printSudoku(int[][] initial) {

        System.out.println("-----------------------");

        for (int row = 0; row < 9; row++) {

            for (int column = 0; column < 9; column++) {
                System.out.print((initial[row][column] == 0) ? "_ " : initial[row][column] + " ");
                System.out.print(((column + 1) % 3 == 0) ? "| " : ""); // prints vertical lines to distinguish boxes visually
            }

            if ((row + 1) % 3 == 0)
                System.out.print("\n-----------------------"); // prints horizontal lines to distinguish boxes visually
            System.out.println();
        }
    }

    static boolean sudokuSolver(int[][] sudoku) {
        for (int row = 0; row < Solver.ROWS; row++) {
            for (int column = 0; column < Solver.COLUMNS; column++) { //search empty cell
                if (sudoku[row][column] == 0) {
                    for (int check = 1; check <= 9; check++) { // check each number
                        if (isValid(sudoku, row, column, check)){ // if it is valid, set it
                            sudoku[row][column] = check;

                            if (sudokuSolver(sudoku)){ // restart by calling itself, which checks the next blank space, if it works, it will get back here and keep checking spaces until it doesn't work or it's done
                                return true;
                            } else sudoku[row][column] = 0; // if it doesn't work, the original number (set in line 56) is not a valid solution, so reset it to zero and try again (it will go to the next number in its own for loop 1-9)
                        }
                    }
                    return false; //if it reaches this point it has gone through 1-9 for a square and determined that no number 1-9 works, so it isn't a valid sudoku
                }
            }
        }
        return true; //when it reaches this point it has solved the sudoku, return true which means it is solved
    }
    
    static boolean isValid(int[][] board, int row, int column, int check){
        return (   rowCheck(board, row, check)
                && columnCheck(board, column, check)
                && sectionCheck(board, row, column,check));
    }

    static boolean rowCheck(int[][] board, int row, int check){

        int[] numbs = new int[9];
        System.arraycopy(board[row], 0, numbs, 0, 9);
        for (int i=0; i<9; i++){
            if(check == numbs[i]) return false;
        }
        return true;
    }

    static boolean columnCheck(int[][] board, int column, int check){
        int[] numbs = new int[9];
        for (int row = 0; row<9; row++) { numbs[row] = board[row][column]; }
        for (int i=0; i<9; i++) { if(check == numbs[i]) return false; }
        return true;
    }

    static boolean sectionCheck(int[][] board, int row, int column, int check){
        int[] numbs = new int[9];
        int x = 0;
        int y = 0;
        int k = 0;
        if(row<6 && row>2) x = 3;
        if(row<9 && row>5) x = 6;
        if(column<6 && column>2) y = 3;
        if(column<9 && column>5) y = 6;

        for(int i = x; i < x+3; i++){
            for (int j = y; j < y+3; j++){
                numbs[k] = board[i][j];
                k++;
            }
        }

        for (int i=0; i<9; i++){
            if (check == numbs[i]) return false;
        }
        return true;
    }

}
