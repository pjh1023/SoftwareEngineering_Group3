package backend.game.economic;

public class Bank implements Economic {

	@Override
	public int pay(Economic to, int amount) {
		to.paid(amount);
		return 0;
	}
	
	@Override
	public int paid(int amount) {
		return amount;
	}	
}
