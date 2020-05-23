package backend.game.action;

public class Wish2Buy implements Action {
	private boolean decision;
	
	public void setDecision(boolean decision) {
		this.decision = decision;
	}
	public boolean getDecision() {
		return decision;
	}
}
