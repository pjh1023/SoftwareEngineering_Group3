package Frame_Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginFramePanel extends JPanel implements ActionListener{
	private Font headFont = new Font ("Arial", Font.BOLD, height / 15);
	private Font buttonFont = new Font ("Arial", Font.BOLD, headFont.getSize() / 2);
	
	
	private JLabel headLabel = new JLabel("Handong Marble", JLabel.CENTER);
	private LoginFrameTypePanel loginTypePanel = new LoginFrameTypePanel(); // 실제 유저의 입력칸은 편의를 위해 따로 panel을 만들었습니다.
	private JButton loginButton = new JButton();
	private JButton joinButton = new JButton();
		
	static final int width = Frame.LoginFrame.frameWidth;
	static final int height = Frame.LoginFrame.frameHeight;
	public static final int marginHeight = height / 20;
	
	final int startY = height / 5;
	
	public LoginFramePanel() {this.setThis(null);}
	
	public void setThis(Component prevComp) {	// panel에 대한 setting을 해줍니다. (bound, layout, component 추)
		this.setBounds(0, 0, width, height);
		this.setLayout(null);
		addComponents();
		setComponents();
	}
	
	public void setComponents() {	// label, button 등의 setting은 여기서 이루어집니다.
		headLabel.setFont(headFont);
		headLabel.setForeground(new Color(105, 83, 225));
		headLabel.setSize(headLabel.getPreferredSize().width, headLabel.getPreferredSize().height);
		headLabel.setLocation(width / 2 - headLabel.getPreferredSize().width / 2, startY);
		
		loginTypePanel.setThis(headLabel);
		loginTypePanel.addComponents();
		
		loginButton.setBounds(loginTypePanel.getX(), loginTypePanel.getY() + loginTypePanel.getHeight() + 2 * marginHeight, loginTypePanel.getWidth() / 2, buttonFont.getSize() + marginHeight);
		loginButton.setBorderPainted(true);
		loginButton.addActionListener(this); // action이 행해지는 모든 component는 actionlistener이 있고, 이는 actionPerformed에서 실제 action을 다룹니다.
		loginButton.setText("Login");
		loginButton.setFont(buttonFont);
		
		joinButton.setBounds(loginButton.getX() + loginButton.getWidth(), loginButton.getY(), loginButton.getWidth(), loginButton.getHeight());
		joinButton.setBorderPainted(true);
		joinButton.addActionListener(this);
		joinButton.setText("Join");
		joinButton.setFont(buttonFont);
	}
	
	public void addComponents() {	// 해당 panel에 component들을 추가해줍니다.
		this.add(headLabel);
		this.add(loginTypePanel);
		this.add(loginButton);
		this.add(joinButton);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().toString().contains("text=Login")) { // ActionEvent e를 통해서 행해진 action이 무엇인지 비교하고 해당 action을 수행하도록 합니다.
			// login Success
			Frame.Main.loginFrame.logPanel.setVisible(false);
			Frame.Main.loginFrame.remove(Frame.Main.loginFrame.logPanel);
			Frame.Main.loginFrame.dispose();
			Frame.Main.waitingFrame = new Frame.WaitingFrame(); //대기방으로 넘어가기 
			Frame.Main.waitingFrame.setThis();
//			Frame.Main.loginFrame.add(Frame.Main.loginFrame.mainPanel);
			
			// login False
//			else {
//				DB.getInstance().updateCurrentWrongCount(mainClasses.MainController.mainFrame.logPanel.getLogInfoPanel().idTextF.getText());
//				JOptionPane.showMessageDialog(null, "Wrong ID or PW!", "WARNING", JOptionPane.ERROR_MESSAGE);
//			}
		}
		else if (e.getSource().toString().contains("text=Join")) {
			Frame.Main.signupFrame = new Frame.SignupFrame();
			Frame.Main.signupFrame.setThis();
		}
	}	
}
