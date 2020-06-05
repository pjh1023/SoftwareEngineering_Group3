package Frame;

public class Main {
	public static LoginFrame loginFrame = new LoginFrame();
	public static SignupFrame signupFrame;
	public static WaitingFrame waitingFrame = new WaitingFrame();
  
	static Network.ClientNetwork network = new Network.ClientNetwork();
	
	static String nickname;
	public static void main(String[] args) {

		int player  = (int) (Math.random()*100000000+1);
		nickname = Integer.toString(player);
		
		network.connect(nickname);
//		System.out.println(nickname);
		
		loginFrame.setThis();
		
	}
}
