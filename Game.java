package we;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

//grid[][] is the game board in this game
//In this game, -1 in a grid cell indicates presence of mine, -2 indicates cell not uncovered and >=0 means cell uncovered
public class Game {
	int grid[][];
	int boardHeight, boardWidth;

	public static void main(String args[]) {
		int gameEnder = 0;// stores 1 if game ended with loss, 2 if game ended
							// with win and 0 if incomplete
		int k;
		Scanner sc = new Scanner(System.in);
		Game g = new Game();
		int c[] = new int[2];
		StringWriter output = new StringWriter();
		g.createGame(sc, new PrintWriter(output));
		Player p = new Player();
		p.setScore(0);
		while (gameEnder == 0) {
			g.displayGame(p.getScore());
			k = p.getInputOption(sc);// stores 1 if user wants to uncover cell
										// or 2 if user wants to place a flag
			c = p.getInputCell(sc, k, g.boardHeight, g.boardWidth);
			gameEnder = g.playGame(k, c);
			p.setScore(p.getScore() + 1);
			if (gameEnder == 1)
				System.out.println("Mine found: Game lost with score :" + p.getScore());
			else if (gameEnder == 2) {
				g.displayGame(p.getScore());
				System.out.println("All non-mine cells uncovered . Game won with score :" + p.getScore());
			}
		}
		sc.close();
	}

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

	public void displayGame(int score) {
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

	public void createGame(Scanner scn, PrintWriter output) {
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

	public int playGame(int k, int c[]) {
		int gameStatus = 0;
		boolean WinStatus;
		int i, j;
		if (k == 1) {// to uncover cell
			if (isMine(c[0], c[1])) {
				gameStatus = 1;
			} else if (isCovered(c[0], c[1])) {
				uncoverCells(c[0], c[1]);
				WinStatus = true;
				for (i = 0; i < boardHeight; i++) {
					for (j = 0; j < boardWidth; j++) {
						if (isCovered(c[0], c[1]) || isFlag(c[0], c[1]))
							WinStatus = false;
					}
				}
				if (WinStatus) {
					gameStatus = 2;
				}
			} else if (isFlag(c[0], c[1])) {
				setCover(c[0], c[1]);
				gameStatus = 0;
			} else {
				System.out.println("This cell has already been uncovered");
				gameStatus = 0;
			}
		} else {// to flag cell
			if (isCovered(c[0], c[1]) || isMine(c[0], c[1])) {
				setFlag(c[0], c[1]);
				gameStatus = 0;
			} else {
				System.out.println("Flag cannot be placed in the cell indicated as already uncovered or flag present");
				gameStatus = 0;
			}
		}
		return gameStatus;
	}

	public boolean isValid(int row, int col) {
		if (row >= 0 && col >= 0 && row < boardHeight && col < boardWidth)
			return true;
		else
			return false;
	}

	public void uncoverCells(int row, int col) {// To uncover neighboring cells
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
					uncoverCells(row - 1, col + i);
				}
			}
			for (i = -1; i <= 1; i++) {// Uncovering lower neighbors
				if (isValid(row + 1, col + i) && isCovered(row + 1, col + i)) {
					uncoverCells(row + 1, col + i);
				}
			}
			if (isValid(row, col + 1) && isCovered(row, col + 1)) {
				uncoverCells(row, col + 1);// uncovers right neighbor
			}
			if (isValid(row, col - 1) && isCovered(row, col - 1)) {
				uncoverCells(row, col - 1);// uncovers left neighbor
			}
		}
	}
}
