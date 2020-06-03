package backend.game.action;

public class Result implements Action {
	private boolean draw;
	public Result(boolean draw) {
		this.draw = draw;
	}
	public boolean isDraw() {
		return this.draw;
	}
}
