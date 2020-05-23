package backend.game.action;

public class Move implements Action{
	private int step;
	
	public Move(int step) {
		this.step = step;
	}
	
	public void setStep(int step) {
		this.step = step;
	}
	public int getStep() {
		return step;
	}
}
