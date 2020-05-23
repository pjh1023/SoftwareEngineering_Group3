package backend.game.action;

public class Bankrupt implements Action {
	private int who;
	public Bankrupt(int who) {
		this.who = who;
	}
	public int getWho() {
		return who;
	}
}
