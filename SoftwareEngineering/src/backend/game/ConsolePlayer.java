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
		System.out.print("Roll Dice: ");
		int first = sc.nextInt();
		int second = sc.nextInt();
		return first*10 + second;
	}

	@Override
	boolean wish2trade(int landNum) {
		System.out.print("Wish2trade? (0/1): ");
		int choice = sc.nextInt();
		return choice == 1;

	}

	@Override
	int where2go() {
		System.out.print("Where2go?: ");
		int choice = sc.nextInt();
		return choice;
	}

	@Override
	int drawCard(int range) {
		int choice = sc.nextInt();
		return choice;
	}

}
