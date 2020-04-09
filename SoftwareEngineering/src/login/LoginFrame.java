package login;

import java.awt.Toolkit;

import javax.swing.JFrame;
import java.awt.Window.Type;

public class LoginFrame extends JFrame {
	public static int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public static int startX = screenWidth / 3;
	public static int startY = screenHeight / 6;

	public static int frameWidth = screenWidth / 3;
	public static int frameHeight = screenHeight * 3 / 4;
	
	public login.LoginMainPanel logmainPanel = new login.LoginMainPanel();
	public login.LoginEnterPanel logenterPanel = new login.LoginEnterPanel();
	
	
	public void setThis() {
		this.setBounds(startX, startY, frameWidth, frameHeight);
		getContentPane().setLayout(null);
		this.add(logmainPanel);
		this.add(logenterPanel);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
