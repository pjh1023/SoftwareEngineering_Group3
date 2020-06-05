package backend.game;

public class ChanceCardEvent {
	public enum Direction {
		Self(0), Bank(-1), Everyone(-2), First(-3), Last(-4);
		private int value;
		Direction(int value){
			this.value = value;
		}
		public int getValue() {
			return value;
		}
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
	
	public Direction getFrom() {
		return from;
	}
	public Direction getTo() {
		return to;
	}
	public int getAmount() {
		return amount;
	}
	public String getDiscription() {
		return this.discription;
	}
}
