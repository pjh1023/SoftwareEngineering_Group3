package backend.game.economic;

public class Bank implements Economic {

	@Override
	public boolean pay(Economic to, int amount) {
		to.paid(amount);
		return true;
	}
	
	@Override
	public int paid(int amount) {
		return amount;
	}	
}
