package backend.game.economic;

import java.util.ArrayList;

public class Everyone implements Economic {
	private ArrayList<Player> players;
	
	public Everyone(ArrayList<Player> players) {
		this.players = players;
	}

	@Override
	public int pay(Economic to, int amount) {
		int result = 0;
		for (int i = 0; i< players.size(); i++) {
			Player p = players.get(i);
			if (!p.isBankrupt())
				result = (p.pay(to, amount) << i) | result;
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
