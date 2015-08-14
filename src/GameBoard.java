package we;

import java.io.PrintWriter;

import java.util.Scanner;

//grid[][] is the game board in this game
//In this game, -1 in a grid cell indicates presence of mine, -2 indicates cell not uncovered and >=0 means cell uncovered
public class GameBoard {
	int grid[][];
	int boardHeight, boardWidth;

	public boolean isMine(int row, int col) {
		if (grid[row][col] == -1)
			return true;
		else
			return false;
	}

	public boolean isCovered(int row, int col) {
		if (grid[row][col] == -2)
			return true;
		else
			return false;
	}

	public boolean isUncovered(int row, int col) {
		if (grid[row][col] >= 0)
			return true;
		else
			return false;
	}

	public boolean isFlag(int row, int col) {
		if (grid[row][col] == -3)
			return true;
		else
			return false;
	}

	public void setFlag(int row, int col) {
		grid[row][col] = -3;
	}

	public void setMine(int row, int col) {
		grid[row][col] = -1;
	}

	public void setCover(int row, int col) {
		grid[row][col] = -2;
	}

	public boolean isValid(int row, int col) {
		if (row >= 0 && col >= 0 && row < boardHeight && col < boardWidth)
			return true;
		else
			return false;
	}

	public void setUncover(int row, int col) {// To uncover neighboring cells
		// iteratively in the game if
		// the current cell has no mines
		// in the neighboring cells
		int count = 0, i;
		for (i = -1; i <= 1; i++) {// checks the upper neighbors of the current
			// cell for mines
			if (isValid(row - 1, col + i)) {
				if (isMine(row - 1, col + i))
					count = count + 1;
			}
		}
		for (i = -1; i <= 1; i++) {// checks the lower neighbors of the current
			// cell for mines
			if (isValid(row + 1, col + i)) {
				if (isMine(row + 1, col + i))
					count = count + 1;
			}
		}
		if (isValid(row, col + 1)) {// checks right neighbor
			if (isMine(row, col + 1))
				count = count + 1;
		}
		if (isValid(row, col - 1)) {// checks left neighbor
			if (isMine(row, col - 1))
				count = count + 1;
		}
		grid[row][col] = count;
		if (count == 0) {// if no mines in neighboring cells , then neighboring
			// cells are uncovered
			for (i = -1; i <= 1; i++) {// Uncovering upper neighbors
				if (isValid(row - 1, col + i) && isCovered(row - 1, col + i)) {
					setUncover(row - 1, col + i);
				}
			}
			for (i = -1; i <= 1; i++) {// Uncovering lower neighbors
				if (isValid(row + 1, col + i) && isCovered(row + 1, col + i)) {
					setUncover(row + 1, col + i);
				}
			}
			if (isValid(row, col + 1) && isCovered(row, col + 1)) {
				setUncover(row, col + 1);// uncovers right neighbor
			}
			if (isValid(row, col - 1) && isCovered(row, col - 1)) {
				setUncover(row, col - 1);// uncovers left neighbor
			}
		}
	}

	public void displayGameBoard(int score) {
		int i, j;
		System.out.println("Welcome to Minesweeper : To win uncover all cells without mines.");
		System.out.println("The number in each cell denotes number of mines in surrounding 8 cells");
		System.out.println("\t Game score: " + score);
		for (i = 0; i < boardHeight; i++) {
			for (j = 0; j < boardWidth; j++) {
				if (isUncovered(i, j))
					System.out.print(grid[i][j] + "\t");
				else if (isFlag(i, j))
					System.out.print("flag" + "\t");
				else
					System.out.print("e" + "\t");
			}
			System.out.println("");
		}
	}

	public void createGameBoard(Scanner scn, PrintWriter output) {
		int i = 0, j = 0;
		boardHeight = 0;
		boardWidth = 0;
		while (boardHeight <= 0 || boardWidth <= 0) {
			System.out.println("Enter the board height follwed by board Width in the next line");
			boardHeight = scn.nextInt();
			boardWidth = scn.nextInt();
			if (boardWidth <= 0 || boardHeight <= 0) {
				output.println("invalid input. Enter again");
				System.out.println("invalid input. Enter again");
			}
		}
		grid = new int[boardHeight][boardWidth];
		for (i = 0; i < boardHeight; i++) {
			for (j = 0; j < boardWidth; j++) {
				setCover(i, j);
			}
		}
		while (!(i == -1 && j == -1)) {
			System.out.println("Enter the mine: row col format(starting from 0) or -1 -1 to exit");
			i = scn.nextInt();
			j = scn.nextInt();
			if (isValid(i, j)) {
				setMine(i, j);
			} else if (i == -1 && j == -1) {
				break;
			} else {
				System.out.println("invalid input");
				output.println("invalid input");
			}
		}
	}
}