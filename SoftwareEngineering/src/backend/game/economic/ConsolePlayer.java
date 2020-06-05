package backend.game.economic;

import java.util.Scanner;

public class ConsolePlayer extends Player {
	private Scanner sc;
	
	public ConsolePlayer(String name, int balance) {
		super(name, balance);
		sc = new Scanner(System.in);
	}
	
	@Override
	public int rollDice() {
		System.out.print("Roll Dice: ");
		int first = sc.nextInt();
		int second = sc.nextInt();
		return first*10 + second;
	}

	@Override
	public boolean wish2trade(int landNum) {
		System.out.print("Wish2trade? (0/1): ");
		int choice = sc.nextInt();
		return choice == 1;

	}

	@Override
	public int where2go() {
		System.out.print("Where2go?: ");
		int choice = sc.nextInt();
		return choice;
	}

	@Override
	public int drawCard(int range) {
		System.out.printf("Select card in range(%d):", range);
		int choice = sc.nextInt();
		return choice;
	}

}
