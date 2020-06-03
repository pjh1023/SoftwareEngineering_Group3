package backend.game.economic;

import java.util.ArrayList;

public class Everyone implements Economic {
	private ArrayList<Player> players;
	
	public Everyone(ArrayList<Player> players) {
		this.players = players;
	}

	@Override
	public boolean pay(Economic to, int amount) {
		boolean result = true;
		for (Player p : players) {
			if (!p.isBankrupt())
				result = p.pay(to, amount) && result;
		}
		return result;
	}

	@Override
	public int paid(int amount) {
		int sum = 0;
		for (Player p : players) {
			if (!p.isBankrupt())
				sum += p.paid(amount);
		}
		return sum;
	}
}
