package we;

import java.util.Scanner;

public class Player {
	int score;

	public Player() {
		score = 0;
	}

	public int[] getInput(Scanner sc, int k) {
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
