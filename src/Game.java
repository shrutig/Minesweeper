

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

//grid[][] is the game board in this game
//In this game, -1 in a grid cell indicates presence of mine, -2 indicates cell not uncovered and >=0 means cell uncovered
public class Game {
	

	public static void main(String args[]) {
		int gameEnder = 0;// stores 1 if game ended with loss, 2 if game ended
							// with win and 0 if incomplete
		int choice;
		int c[] = new int[2];
		Scanner sc = new Scanner(System.in);	
		StringWriter output = new StringWriter();
		
		GameBoard gameBoard = new GameBoard();
		Player p = new Player();		
		gameBoard.createGameBoard(sc, new PrintWriter(output));	
		Game g = new Game();
		p.setScore(0);
		
		while (gameEnder == 0) {
			gameBoard.displayGameBoard(p.getScore());
			choice = p.getInputOption(sc);// stores 1 if user wants to uncover cell
										// or 2 if user wants to place a flag
			c = p.getInputCell(sc, choice, gameBoard.boardHeight, gameBoard.boardWidth);
			gameEnder = g.playGame(choice, c[0],c[1],gameBoard);
			p.setScore(p.getScore() + 1);
			if (gameEnder == 1)
				System.out.println("Mine found: Game lost with score :" + p.getScore());
			else if (gameEnder == 2) {
				gameBoard.displayGameBoard(p.getScore());
				System.out.println("All non-mine cells uncovered . Game won with score :" + p.getScore());
			}
		}
		sc.close();
	}

	public int playGame(int choice, int row,int col,GameBoard gameBoard) {
		int gameStatus = 0;
		boolean WinStatus;
		int i, j;
		if (choice == 1) {// to uncover cell
			if (gameBoard.isMine(row, col)) {
				gameStatus = 1;
			} else if (gameBoard.isCovered(row, col)) {
				gameBoard.setUncover(row, col);
				WinStatus = true;
				for (i = 0; i < gameBoard.boardHeight; i++) {
					for (j = 0; j < gameBoard.boardWidth; j++) {
						if (gameBoard.isCovered(row, col) || gameBoard.isFlag(row, col))
							WinStatus = false;
					}
				}
				if (WinStatus) {
					gameStatus = 2;
				}
			} else if (gameBoard.isFlag(row, col)) {
				gameBoard.setCover(row, col);
				gameStatus = 0;
			} else {
				System.out.println("This cell has already been uncovered");
				gameStatus = 0;
			}
		} else {// to flag cell
			if (gameBoard.isCovered(row, col) || gameBoard.isMine(row, col)) {
				gameBoard.setFlag(row, col);
				gameStatus = 0;
			} else {
				System.out.println("Flag cannot be placed in the cell indicated as already uncovered or flag present");
				gameStatus = 0;
			}
		}
		return gameStatus;
	}
}
