package backend.game.economic;

import java.util.ArrayList;

public class First implements Economic {
	private ArrayList<Player> players;
	
	public First(ArrayList<Player> players) {
		this.players = players;
	}
	
	private Player getFirst() {
		int max_balance = 0;
		Player first = null;
		for (Player p : players) {
			if (!p.isBankrupt() && p.getBalance() > max_balance) {
				first = p;
				max_balance = p.getBalance();
			}
		}
		return first;
	}

	@Override
	public boolean pay(Economic to, int amount) {
		return getFirst().pay(to, amount);
	}

	@Override
	public int paid(int amount) {
		return getFirst().paid(amount);
	}
}
