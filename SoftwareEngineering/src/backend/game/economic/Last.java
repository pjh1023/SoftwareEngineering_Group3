package backend.game.economic;

import java.util.ArrayList;

public class Last implements Economic {
	private ArrayList<Player> players;
	
	public Last(ArrayList<Player> players) {
		this.players = players;
	}
	
	private Player getLast() {
		int min_balance = 2000000000;
		Player last = null;
		for (Player p : players) {
			if (!p.isBankrupt() && p.getBalance() < min_balance) {
				last = p;
				min_balance = p.getBalance();
			}
		}
		return last;
	}

	@Override
	public boolean pay(Economic to, int amount) {
		return getLast().pay(to, amount);
	}

	@Override
	public int paid(int amount) {
		return getLast().paid(amount);
	}
}
