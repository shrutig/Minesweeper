package com.tuplejump.inventorymanagement;

import java.util.Scanner;

public class Player {
	int score;

	public Player() {
		score = 0;
	}

	public int getInputOption(Scanner sc) {
		
		int k = 0;
		while (k != 1 && k != 2) {
			System.out.println("Enter 1 to uncover cell or 2 to flag cell");
			k = sc.nextInt();
			if (k != 1 && k != 2) {
				System.out.println("invalid input.Enter again.");
				continue;
			}
		}
		return k;
	}

	public int[] getInputCell(Scanner sc, int k, int boardHeight, int boardWidth) {
		int a[] = {-1,-1};
		if (k == 1)
			System.out.println(
					"Enter cell with e to uncover it or with flag to unflag it : row col format(starting from 0)");
		else if (k == 2)
			System.out.println("Enter cell with e flag it : row col format(starting from 0)");
		while(a[0] >boardHeight - 1 || a[0] < 0 || a[1] > boardWidth - 1 || a[1] < 0){
		a[0] = sc.nextInt();
		a[1] = sc.nextInt();
		if (a[0] >boardHeight - 1 || a[0] < 0 || a[1] > boardWidth - 1 || a[1] < 0) {
			System.out.println("invalid input. Enter again");
			continue;
		}
		}
		return a;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int s) {
		score = s;
	}

}
