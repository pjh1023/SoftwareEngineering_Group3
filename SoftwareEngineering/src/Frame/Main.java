package Frame;

import backend.game.GameManager;

public class Main {
	public static LoginFrame loginFrame = new LoginFrame();
	public static SignupFrame signupFrame;
	public static WaitingFrame waitingFrame = new WaitingFrame();

	public static GameFrame gameFrame = new GameFrame();
	
	
	public static void main(String[] args) {
		//loginFrame.setThis();
		GameManager.test();
	}
}
