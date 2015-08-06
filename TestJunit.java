package we;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

public class TestJunit {
	Game g = new Game();

	@Before
	public void TestCreateGame() {
		StringWriter output = new StringWriter();
		String input = "-1\n" + "5\n" // "Invalid number. Enter again."
				+ "6\n" + "6\n" // Enter boardWidth and boardHeight
				+ "1 1\n" // Enter the mine: row col or -1 -1 to
							// exit
				+ "3 1\n" + "-1 -1\n";
		g.createGame(new Scanner(input), new PrintWriter(output));
		assertTrue("Mine not located properly", g.isMine(1, 1));// returns error
																// if mine not
																// located on
																// board
		assertTrue("Mine not located properly", g.isMine(3, 1));
		assertFalse("Mine not located properly", g.isMine(2, 2));// returns
																	// error if
																	// cell is a
																	// mine
		assertTrue("Invalid input not considered", (output.toString()).contains("invalid input"));
	}

	@Test
	public void TestPlayGame1() {
		int c[] = { 1, 1 };
		int gameStat = g.playGame(1, c);
		assertTrue("Game not over at proper time", gameStat == 1);// returns
																	// error
																	// if game
																	// not
																	// over

	}

	@Test
	public void TestPlayGame2() {
		int c[] = { 1, 2 };
		int gameStat = g.playGame(2, c);
		c[1] = 1;
		assertTrue("Game not functioning properly", gameStat == 0);// returns
																	// error if
																	// game not
																	// functioning
																	// properly
		assertTrue("Flag not placed", g.isFlag(1, 2));// returns error if flag
														// not
														// placed
		gameStat = g.playGame(1, c);
		assertTrue("Flag not placed", gameStat == 1);
	}

	@Test
	public void TestUncoverCells() {// This test is for checking if the
									// uncoverCells method is uncovering a range
									// of cells properly
		g.uncoverCells(4, 4);
		assertTrue("Cells not uncovered properly", g.isUncovered(0, 2));// returns
																		// error
																		// if
																		// cell
																		// not
																		// uncovered
		assertTrue("Cells not uncovered properly", g.isUncovered(0, 3));
		assertTrue("Cells not uncovered properly", g.isUncovered(0, 4));
		assertTrue("Cells not uncovered properly", g.isUncovered(5, 3));

	}
}
