package backend.game.economic;

import java.util.ArrayList;

public class Last implements Economic {
	private ArrayList<Player> players;
	
	public Last(ArrayList<Player> players) {
		this.players = players;
	}
	
	private int getLast() {
		int min_balance = 2000000000;
		int last = -1;
		for (int i = 0; i < players.size();i++) {
			Player p = players.get(i);
			if (!p.isBankrupt() && p.getBalance() < min_balance) {
				last = i;
				min_balance = p.getBalance();
			}
		}
		return last;
	}

	@Override
	public int pay(Economic to, int amount) {
		int last = getLast();
		return players.get(last).pay(to, amount) << last ;
	}

	@Override
	public int paid(int amount) {
		return players.get(getLast()).paid(amount);
	}
}
