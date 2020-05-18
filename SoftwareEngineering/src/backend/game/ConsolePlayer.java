package backend.game;

import java.util.Scanner;

public class ConsolePlayer extends Player {
	private Scanner sc;
	
	public ConsolePlayer(int balance) {
		super(balance);
		sc = new Scanner(System.in);
	}
	
	@Override
	int rollDice() {
		int first = sc.nextInt();
		int second = sc.nextInt();
		return first*10 + second;
	}

	@Override
	boolean wish2trade(int landNum) {
		int choice = sc.nextInt();
		return choice == 1;

	}

	@Override
	int where2go() {
		int choice = sc.nextInt();
		return choice;
	}

	@Override
	int drawCard(int range) {
		int choice = sc.nextInt();
		return choice;
	}

}
