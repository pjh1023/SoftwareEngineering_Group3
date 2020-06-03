package backend.game;

import java.util.ArrayList;

import backend.game.action.Action;

public class DecideTurn implements Action {
	private ArrayList<Integer> dice;
	
	public void setDice(ArrayList<Integer> dice) {
		this.dice = dice;
	}
	
	public ArrayList<Integer> getDice() {
		return this.dice;
	}
}
