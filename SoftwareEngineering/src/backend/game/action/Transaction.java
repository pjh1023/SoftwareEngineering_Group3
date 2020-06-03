package backend.game.action;

public class Transaction implements Action {
	private int from, to;
	private int amount;
	
	public Transaction(int from, int to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}
	
	public void setDirection(int from, int to) {
		this.from = from;
		this.to = to;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getFrom() {
		return from;
	}
	public int getTo() {
		return to;
	}
	public int getAmount() {
		return amount;
	}
}
