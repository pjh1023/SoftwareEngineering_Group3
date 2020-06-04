package Frame;

import java.awt.Component;
import javax.swing.JFrame;

public class SignupFrame extends JFrame{
	public SignupFrame() {}
	public static final int startX = Frame.LoginFrame.startX + Frame.LoginFrame.frameWidth / 3;
	public static final int startY = Frame.LoginFrame.startY - Frame.LoginFrame.frameHeight / 6;
	public static final int frameWidth = Frame.LoginFrame.frameWidth;
	public static final int frameHeight = Frame.LoginFrame.frameHeight/2;
	
	public static final int marginHeight = frameHeight / 20;
	public static final int marginWidth = frameWidth / 20;
	
	public Frame_Components.SignupFramePanel signupPanel;
	

	public void setThis() {
		getContentPane().setLayout(null);
		this.setLocation(startX, startY);
		this.setSize(frameWidth, frameHeight);
		this.setVisible(true);
		signupPanel = new Frame_Components.SignupFramePanel(this);
		getContentPane().add(signupPanel);
	}
	
}
