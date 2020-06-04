package Frame;

public class Main {
	public static LoginFrame loginFrame = new LoginFrame();
	public static SignupFrame signupFrame;
	public static WaitingFrame waitingFrame = new WaitingFrame();
  
	static Network.ClientNetwork network = new Network.ClientNetwork();
	
	public static void main(String[] args) {
		network.connect("1234");
		loginFrame.setThis();
	}
}
