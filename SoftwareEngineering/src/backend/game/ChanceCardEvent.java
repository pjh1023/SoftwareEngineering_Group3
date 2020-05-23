package backend.game;

public class ChanceCardEvent {
	public enum Direction {
		Self, Bank, Everyone, First, Last;
	}
	
	private Direction from;
	private Direction to;
	private int amount;
	private String discription;
	
	public ChanceCardEvent(Direction from, Direction to, int amount, String discription) {
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.discription = discription;
	}
}
