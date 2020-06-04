package backend.game.economic;

import java.util.ArrayList;

public class First implements Economic {
	private ArrayList<Player> players;
	
	public First(ArrayList<Player> players) {
		this.players = players;
	}
	
	private int getFirst() {
		int max_balance = 0;
		int first = -1;
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (!p.isBankrupt() && p.getBalance() > max_balance) {
				first = i;
				max_balance = p.getBalance();
			}
		}
		return first;
	}

	@Override
	public int pay(Economic to, int amount) {
		int first = getFirst();
		return players.get(first).pay(to, amount) << first;
	}

	@Override
	public int paid(int amount) {
		return players.get(getFirst()).paid(amount);
	}
}
