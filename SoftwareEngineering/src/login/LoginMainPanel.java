package login;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginMainPanel extends JPanel implements interfaces.Setting{
	private Font headFont = new Font ("Arial", Font.BOLD, height / 10);
	private Font buttonFont = new Font ("Arial", Font.BOLD, headFont.getSize() / 2);
	
	
	private JLabel headLabel = new JLabel("Blue Marble", JLabel.CENTER);
	private LoginEnterPanel logEnterPanel = new LoginEnterPanel();
	private JButton loginButton = new JButton();
	private JButton joinButton = new JButton();
	
//	private LogButtonActionListener actListener = new LogButtonActionListener();
	
	public static int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	static final int width = screenWidth/3;
	static final int height = screenHeight/2;
	public static final int marginHeight = height / 20;
	
	final int startY = height / 5;
	
	public LoginMainPanel() {this.setThis(null);}
	
	public void setThis(Component prevComp) {
		this.setBounds(0, 0, width, height);
		this.setLayout(null);
		addComponents();
		setComponents();
	}
	
	public void setComponents() {
		headLabel.setFont(headFont);
		headLabel.setForeground(new Color(75, 75, 255));
		headLabel.setSize(headLabel.getPreferredSize().width, headLabel.getPreferredSize().height);
		headLabel.setLocation(width / 2 - headLabel.getPreferredSize().width / 2, startY);
		
		logEnterPanel.setThis(headLabel);
		logEnterPanel.addComponents();
		
		loginButton.setBounds(logEnterPanel.getX(), logEnterPanel.getY() + logEnterPanel.getHeight() + 2 * marginHeight, logEnterPanel.getWidth() / 2, buttonFont.getSize() + marginHeight);
		loginButton.setBorderPainted(true);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.Main.loginFrame.setVisible(false);
				main.Main.loginFrame.remove(main.Main.loginFrame.logenterPanel);
//				mainClasses.MainController.mainFrame.add(mainClasses.MainController.mainFrame.mainPanel);
			}
			
		});
		loginButton.setText("Login");
		loginButton.setFont(buttonFont);
		
		joinButton.setBounds(loginButton.getX() + loginButton.getWidth(), loginButton.getY(), loginButton.getWidth(), loginButton.getHeight());
		joinButton.setBorderPainted(true);
//		joinButton.addActionListener(actListener);
		joinButton.setText("Join");
		joinButton.setFont(buttonFont);
	}
	
	public void addComponents() {
		this.add(headLabel);
		this.add(logEnterPanel);
		this.add(loginButton);
		this.add(joinButton);
	}
	
//	public LogInfoPanel getLogInfoPanel() {return logInfoPanel;}
}
