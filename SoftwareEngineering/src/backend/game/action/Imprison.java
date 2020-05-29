package backend.game.action;

public class Imprison implements Action {
	private boolean firstTime;
	public Imprison(boolean firstTime) {
		this.firstTime = firstTime;
	}
	public boolean isFirstTime() {
		return this.firstTime;
	}
}
