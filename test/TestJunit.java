

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class TestJunit {
	Game g = new Game();
    GameBoard gameBoard = new GameBoard();
	@Before
	public void testCreateGame() {
		StringWriter output = new StringWriter();
		String input = "-1\n" + "5\n" // "Invalid number. Enter again."
				+ "6\n" + "6\n" // Enter boardWidth and boardHeight
				+ "1 1\n" // Enter the mine: row col or -1 -1 to
							// exit
				+ "3 1\n" + "-1 -1\n";
		gameBoard.createGameBoard(new Scanner(input), new PrintWriter(output));
		assertTrue("Mine not located properly", gameBoard.isMine(1, 1));// returns error
																// if mine not
																// located on
																// board
		assertTrue("Mine not located properly", gameBoard.isMine(3, 1));
		assertFalse("Mine not located properly", gameBoard.isMine(2, 2));// returns
																	// error if
																	// cell is a
																	// mine
		assertTrue("Invalid input not considered", (output.toString()).contains("invalid input"));
	}

	@Test
	public void testPlayGame1() {
		int row=1,col=1;
		int gameStat = g.playGame(1, row,col,gameBoard);
		assertTrue("Game not over when required", gameStat == 1);// returns
																	// error
																	// if game
																	// not
																	// over

	}

	@Test
	public void testPlayGame2() {
		int row = 1, col= 2 ;
		int gameStat = g.playGame(2, row,col,gameBoard);// placing a flag at row,col		
		assertTrue("Game not functioning properly", gameStat == 0);// returns
																	// error if
																	// game not
																	// functioning
																	// properly
		assertTrue("Flag not placed", gameBoard.isFlag(1, 2));// returns error if flag
														// not
														// placed
		col = 1;
		gameStat = g.playGame(1, row,col,gameBoard);
		assertTrue("Mine not detected properly", gameStat == 1);
	}

	@Test
	public void testSetUncover() {// This test is for checking if the
									// uncoverCells method is uncovering a range
									// of cells properly
		gameBoard.setUncover(4, 4);
		assertTrue("Cells not uncovered properly", gameBoard.isUncovered(0, 2));// returns
																		// error
																		// if
																		// cell
																		// not
																		// uncovered
		assertTrue("Cells not uncovered properly", gameBoard.isUncovered(0, 3));
		assertTrue("Cells not uncovered properly", gameBoard.isUncovered(0, 4));
		assertTrue("Cells not uncovered properly", gameBoard.isUncovered(5, 3));

	}
}
