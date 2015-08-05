package we;

import java.util.Scanner;

public class Game {
	int grid[][];
	int cell;
	int boardHeight, boardWidth;

	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		Game g = new Game();
		g.playGame(sc);
		sc.close();
	}

	public void displayGame(int score) {
		int i, j;
		System.out.println("Welcome to Minesweeper : To win uncover all cells without mine");
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

	public void createGame(Scanner scn) {
		int i = 0, j = 0;
		boardHeight = 0;
		boardWidth = 0;
		while (boardHeight <= 0 || boardWidth <= 0) {
			System.out.println("Enter the board height");
			boardHeight = scn.nextInt();
			System.out.println("Enter the board width");
			boardWidth = scn.nextInt();
			if (boardHeight <= 0 || boardWidth <= 0) {
				System.out.println("invalid input. Enter again");
			}
		}

		grid = new int[boardHeight][boardWidth];
		while (!(i == -1 && j == -1)) {
			System.out.println("Enter the mine: row col or -1 -1 to exit");
			i = scn.nextInt();
			j = scn.nextInt();
			if (i >= 0 && i < boardHeight && j >= 0 && j < boardWidth) {
				grid[i][j] = -1;
			} else if (i == -1 && j == -1) {
				break;
			} else {
				System.out.println("invalid input");
			}
		}
		for (i = 0; i < boardHeight; i++) {
			for (j = 0; j < boardWidth; j++) {
				if (grid[i][j] != -1) {
					grid[i][j] = -2;
				}
			}

		}

	}

	public void playGame(Scanner sc) {
		boolean flag = true, flag1 = false;
		int i, j, k;
		Player p = new Player();
		cell = 0;
		p.setScore(cell);
		createGame(sc);

		while (flag && !flag1) {
			displayGame(p.getScore());
			System.out.println("Enter 1 to uncover cell or 2 to flag cell");
			k = sc.nextInt();
			if (k != 1 && k != 2) {
				System.out.println("invalid input");
				continue;
			}
			int c[] = p.getInput(sc, k);
			if (c[0] > boardHeight - 1 || c[0] < 0 || c[1] > boardWidth - 1 || c[1] < 0) {
				System.out.println("invalid input");
				continue;
			}

			if (k == 1) {

				if (grid[c[0]][c[1]] == -1) {
					flag = false;
					System.out.println("Mine found: Game lost with score :" + p.getScore());
				} else if (grid[c[0]][c[1]] == -2) {
					uncoverCells(c[0], c[1]);
					p.setScore(cell);
					flag1 = true;
					for (i = 0; i < boardHeight; i++) {
						for (j = 0; j < boardWidth; j++) {
							if (grid[i][j] == -2)
								flag1 = false;
						}
					}
					if (flag1) {
						displayGame(p.getScore());
						System.out.println("All non-mine cells uncovered . Game won with score :" + p.getScore());
					}
				} else if (grid[c[0]][c[1]] == -3) {
					grid[c[0]][c[1]] = -2;
				}
				else{
					System.out.println("This cell has already been uncovered");
				}
			} else {
				if (grid[c[0]][c[1]] == -2 || grid[c[0]][c[1]] == -1)
					grid[c[0]][c[1]] = -3;
				else
					System.out.println(
							"Flag cannot be placed in the cell indicated as already uncovered or flag present");
			}
		}
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
