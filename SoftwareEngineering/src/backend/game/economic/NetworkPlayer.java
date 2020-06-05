package backend.game.economic;

import java.util.Scanner;

import Frame_Components.GameFrameTypePanel;
import backend.game.BoundedBuffer;

public class NetworkPlayer extends Player {
	private BoundedBuffer<Integer> buf;
	
	public NetworkPlayer(String name, int balance, BoundedBuffer<Integer> bf) {
		super(name, balance);
		this.buf = bf;
	}
	
	@Override
	public int rollDice() {
		int result = buf.pop();
		return result;
		
	}

	@Override
	public boolean wish2trade(int landNum) {
		int choice = buf.pop();
		return choice == 1;
	}

	@Override
	public int where2go() {
		int choice = buf.pop();
		return choice;
	}

	@Override
	public int drawCard(int range) {
		int choice = buf.pop();
		return choice;
	}
}
