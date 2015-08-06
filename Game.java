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
			k = p.getInputOption(sc);
			
			c = p.getInputCell(sc, k,g.boardHeight,g.boardWidth);
			
			gameEnder = g.playGame(k, c);
			p.setScore(p.getScore()+1);
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

	public void displayGame(int score) {
		int i, j;
		System.out.println("Welcome to Minesweeper : To win uncover all cells without mines.");
		System.out.println("The number in each cell denotes number of mines in surrounding 8 cells");
		System.out.println("\t Game score: " + score);
		for (i = 0; i < boardHeight; i++) {
			for (j = 0; j < boardWidth; j++) {
				if (isUncovered(i,j))
					System.out.print(grid[i][j] + "\t");
				else if (isFlag(i,j))
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
			System.out.println("Enter the board width");
			output.println("Enter the board width");
			boardWidth = scn.nextInt();
			if (boardWidth <= 0 || boardHeight <= 0) {
				output.println("invalid input. Enter again");
				System.out.println("invalid input. Enter again");
			}
		}
		grid = new int[boardHeight][boardWidth];
		for (i = 0; i < boardHeight; i++) {
			for (j = 0; j < boardWidth; j++) {
				grid[i][j] = -2;
			}
		}
		while (!(i == -1 && j == -1)) {
			System.out.println("Enter the mine: row col format(starting from 0) or -1 -1 to exit");
			output.println("Enter the mine: row col or -1 -1 to exit");
			i = scn.nextInt();
			j = scn.nextInt();
			if (i >= 0 && i < boardHeight && j >= 0 && j < boardWidth) {
				grid[i][j] = -1;
			} else if (i == -1 && j == -1) {
				break;
			} else {
				System.out.println("invalid input");
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

	public void uncoverCells(int row, int col) {// To uncover neighboring cells
												// iteratively in the game if
												// the current cell has no mines
												// in the neighboring cells
		
		int count = 0, i;
		for (i = -1; i <= 1; i++) {//checks the upper neighbors of the current cell for mines
			if (row - 1 >= 0 && col + i >= 0 && row - 1 < boardHeight && col + i < boardWidth) {
				if (grid[row - 1][col + i] == -1)
					count = count + 1;
			}
		}
		for (i = -1; i <= 1; i++) {//checks the lower neighbors of the current cell for mines
			if (row + 1 >= 0 && col + i >= 0 && row + 1 < boardHeight && col + i < boardWidth) {
				if (grid[row + 1][col + i] == -1)
					count = count + 1;
			}
		}
		if (row >= 0 && col + 1 >= 0 && row < boardHeight && col + 1 < boardWidth) {//checks right neighbor 
			if (grid[row][col + 1] == -1)
				count = count + 1;
		}
		if (row >= 0 && col - 1 >= 0 && row < boardHeight && col - 1 < boardWidth) {//checks left neighbor
			if (grid[row][col - 1] == -1)
				count = count + 1;
		}
		grid[row][col] = count;
		if (count == 0) {// if no mines in neighboring cells , then neighboring cells are uncovered
			for (i = -1; i <= 1; i++) {//Uncovering upper neighbors
				if (row - 1 >= 0 && col + i >= 0 && row - 1 < boardHeight && col + i < boardWidth
						&& grid[row - 1][col + i] == -2) {
					uncoverCells(row - 1, col + i);
				}

			}
			for (i = -1; i <= 1; i++) {//Uncovering lower neighbors
				if (row + 1 >= 0 && col + i >= 0 && row + 1 < boardHeight && col + i < boardWidth
						&& grid[row + 1][col + i] == -2) {
					uncoverCells(row + 1, col + i);
				}

			}
			if (row >= 0 && col + 1 >= 0 && row < boardHeight && col + 1 < boardWidth && grid[row][col + 1] == -2) {
				uncoverCells(row, col + 1);//uncovers right neighbor
			}
			if (row >= 0 && col - 1 >= 0 && row < boardHeight && col - 1 < boardWidth && grid[row][col - 1] == -2) {
				uncoverCells(row, col - 1);//uncovers left neighbor
			}

		}
	}

}
