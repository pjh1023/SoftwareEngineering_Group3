package backend.game.action;

public class RollDice implements Action {
	private int dice;
	
	public void setDice(int a, int b) {
		this.dice = a*10 + b;
	}
	
	public int getDice() {
		return dice;
	}
}
