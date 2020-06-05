package backend.game.economic;

import Frame_Components.GameFrameTypePanel;
import backend.game.BoundedBuffer;

public class LocalPlayer extends Player {
	//private Scanner sc;
	private GameFrameTypePanel panel;
	private BoundedBuffer<String> bufOut;
	
	public LocalPlayer(String name, int balance, GameFrameTypePanel panel, BoundedBuffer<String> bufOut) {
		super(name, balance);
		//sc = new Scanner(System.in);
		this.panel = panel;
		this.bufOut = bufOut;
	}
	
	@Override
	public int rollDice() {
		/*
		System.out.print("Roll Dice: ");
		int first = sc.nextInt();
		int second = sc.nextInt();
		return first*10 + second;
		*/
		panel.getBtnRollDice().setEnabled(true);
		int result = panel.getBuf().pop();
		panel.getBtnRollDice().setEnabled(false);
		bufOut.push("[Game],"+result);
		return result;
		
	}

	@Override
	public boolean wish2trade(int landNum) {
		panel.askBoolean(landNum);
		int choice = panel.getBuf().pop();
		bufOut.push("[Game],"+choice);
		return choice == 1;
	}

	@Override
	public int where2go() {
		panel.enableLand(true);
		int choice = panel.getBuf().pop();
		panel.enableLand(false);
		bufOut.push("[Game],"+choice);
		return choice;
	}

	@Override
	public int drawCard(int range) {
		panel.getBtnDraw().setEnabled(true);
		int choice = panel.getBuf().pop();
		panel.getBtnDraw().setEnabled(false);
		bufOut.push("[Game],"+choice);
		return choice;
	}
}
