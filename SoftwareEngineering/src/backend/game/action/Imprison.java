package backend.game.action;

public class Imprison implements Action {
	private boolean firstTime;
	private int turn;
	public Imprison(int p, boolean firstTime) {
		this.firstTime = firstTime;
	}
	public boolean isFirstTime() {
		return this.firstTime;
	}
	public int getTurn() {
		return turn;
	}
}
