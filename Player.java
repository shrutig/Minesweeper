package we;

import java.util.Scanner;

public class Player {
	int score;

	public Player() {
		score = 0;
	}

	public int getInputOption(Scanner sc) {
		System.out.println("Enter 1 to uncover cell or 2 to flag cell");
		int k = sc.nextInt();
		return k;
	}

	public int[] getInputCell(Scanner sc, int k) {
		int a[] = new int[2];
		if (k == 1)
			System.out.println("Enter cell with e to uncover it or with flag to unflag it : row col");
		else if (k == 2)
			System.out.println("Enter cell with e flag it : row col");
		a[0] = sc.nextInt();
		a[1] = sc.nextInt();
		return a;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int s) {
		score = s;
	}

}
