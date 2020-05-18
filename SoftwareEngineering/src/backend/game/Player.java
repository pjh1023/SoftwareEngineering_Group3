package backend.game;

public abstract class Player {
	private int balance;
	private int doubleCount;
	private int prisonCount;
	private int position;
	
	abstract int rollDice();
	abstract boolean wish2trade(int landNum);
	abstract int where2go();
	abstract int drawCard(int range);
	
	protected Player(int balance) {
		this.balance = balance;
		this.doubleCount = 0;
		this.prisonCount = 0;
		this.position = 0;
	}
	
	public boolean pay(int amount) {
		if (balance >= amount) {
			balance -= amount;
			return true;
		}
		return false;
	}
	
	public boolean paid(int amount) {
		balance += amount;
		return true;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public int getDoubleCount() {
		return this.doubleCount;
	}
	
	public boolean isImprisoned() {
		return prisonCount != 0;
	}
	
	public void imprison() {
		prisonCount++;
		if (prisonCount == 4)
			prisonCount = 0;
	}
	public void release() {
		prisonCount = 0;
	}
	
	public int getPosition() {
		return position;
	}
	
	public int move(int distance) {
		this.position += distance;
		return position;
	}
}
