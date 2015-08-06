package we;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

public class Game {
	int grid[][];
	int cell;
	int boardHeight, boardWidth;

	public static void main(String args[]) {
		int flag = 0;
		int k;
		Scanner sc = new Scanner(System.in);
		Game g = new Game();
		int c[] = new int[2];
		StringWriter output = new StringWriter();
		g.createGame(sc, new PrintWriter(output));
		Player p = new Player();
		g.cell = 0;
		p.setScore(g.cell);
		while (flag == 0) {
			g.displayGame(p.getScore());
			k = p.getInputOption(sc);
			if (k != 1 && k != 2) {
				System.out.println("invalid input");
				continue;
			}
			c = p.getInputCell(sc, k);
			if (c[0] > g.boardHeight - 1 || c[0] < 0 || c[1] > g.boardWidth - 1 || c[1] < 0) {
				System.out.println("invalid input");
				continue;
			}
			flag = g.playGame(k, c);
			p.setScore(g.cell);
			if (flag == 1)
				System.out.println("Mine found: Game lost with score :" + p.getScore());
			else if (flag == 2) {
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

	public boolean isUncovered(int row, int col) {
		if (grid[row][col] >= 0)
			return true;
		else
			return false;
	}

	public void displayGame(int score) {
		int i, j;
		System.out.println("Welcome to Minesweeper : To win uncover all cells without mines.");
		System.out.println("The number in each cell denotes number of mines in surrounding 8 cells");
		System.out.println("\t Game score: " + score);
		for (i = 0; i < boardHeight; i++) {
			for (j = 0; j < boardWidth; j++) {
				if (grid[i][j] >= 0)
					System.out.print(grid[i][j] + "\t");
				else if (grid[i][j] == -3)
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
			 System.out.println("Enter the board height");
			output.println("Enter the board height");
			boardHeight = scn.nextInt();
			if (boardHeight <= 0) {
				// System.out.println("invalid input. Enter again");
				output.println("invalid input. Enter again");
				continue;
			}
			 System.out.println("Enter the board width");
			output.println("Enter the board width");
			boardWidth = scn.nextInt();
			if (boardWidth <= 0) {
				output.println("invalid input. Enter again");
				// System.out.println("invalid input. Enter again");
				continue;
			}
		}
		grid = new int[boardHeight][boardWidth];
		for (i = 0; i < boardHeight; i++) {
			for (j = 0; j < boardWidth; j++) {
				grid[i][j] = -2;
			}
		}
		while (!(i == -1 && j == -1)) {
			 System.out.println("Enter the mine: row col or -1 -1 to exit");
			output.println("Enter the mine: row col or -1 -1 to exit");
			i = scn.nextInt();
			j = scn.nextInt();
			if (i >= 0 && i < boardHeight && j >= 0 && j < boardWidth) {
				grid[i][j] = -1;
			} else if (i == -1 && j == -1) {
				break;
			} else {
				// System.out.println("invalid input");
				output.println("invalid input");
			}
		}

	}

	public int playGame(int k, int c[]) {
		int flag = 0;
		boolean flag1;
		int i, j;
		if (k == 1) {
			if (isMine(c[0], c[1])) {
				flag = 1;
			} else if (grid[c[0]][c[1]] == -2) {
				uncoverCells(c[0], c[1]);
				flag1 = true;
				for (i = 0; i < boardHeight; i++) {
					for (j = 0; j < boardWidth; j++) {
						if (grid[i][j] == -2 || grid[i][j] == -3)
							flag1 = false;
					}
				}
				if (flag1) {
					flag = 2;
				}
			} else if (grid[c[0]][c[1]] == -3) {
				grid[c[0]][c[1]] = -2;
				flag = 0;
			} else {
				System.out.println("This cell has already been uncovered");
				flag = 0;
			}
		} else {
			if (grid[c[0]][c[1]] == -2 || grid[c[0]][c[1]] == -1) {
				grid[c[0]][c[1]] = -3;
				flag = 0;
			} else {
				System.out.println("Flag cannot be placed in the cell indicated as already uncovered or flag present");
				flag = 0;
			}
		}
		return flag;
	}

	public void uncoverCells(int row, int col) {
		cell = cell + 1;
		int count = 0, i;
		for (i = -1; i <= 1; i++) {
			if (row - 1 >= 0 && col + i >= 0 && row - 1 < boardHeight && col + i < boardWidth) {
				if (grid[row - 1][col + i] == -1)
					count = count + 1;
			}
		}
		for (i = -1; i <= 1; i++) {
			if (row + 1 >= 0 && col + i >= 0 && row + 1 < boardHeight && col + i < boardWidth) {
				if (grid[row + 1][col + i] == -1)
					count = count + 1;
			}
		}
		if (row >= 0 && col + 1 >= 0 && row < boardHeight && col + 1 < boardWidth) {
			if (grid[row][col + 1] == -1)
				count = count + 1;
		}
		if (row >= 0 && col - 1 >= 0 && row < boardHeight && col - 1 < boardWidth) {
			if (grid[row][col - 1] == -1)
				count = count + 1;
		}
		grid[row][col] = count;
		if (count == 0) {
			for (i = -1; i <= 1; i++) {
				if (row - 1 >= 0 && col + i >= 0 && row - 1 < boardHeight && col + i < boardWidth
						&& grid[row - 1][col + i] == -2) {
					uncoverCells(row - 1, col + i);
				}

			}
			for (i = -1; i <= 1; i++) {
				if (row + 1 >= 0 && col + i >= 0 && row + 1 < boardHeight && col + i < boardWidth
						&& grid[row + 1][col + i] == -2) {
					uncoverCells(row + 1, col + i);
				}

			}
			if (row >= 0 && col + 1 >= 0 && row < boardHeight && col + 1 < boardWidth && grid[row][col + 1] == -2) {
				uncoverCells(row, col + 1);
			}
			if (row >= 0 && col - 1 >= 0 && row < boardHeight && col - 1 < boardWidth && grid[row][col - 1] == -2) {
				uncoverCells(row, col - 1);
			}

		}
	}

}
