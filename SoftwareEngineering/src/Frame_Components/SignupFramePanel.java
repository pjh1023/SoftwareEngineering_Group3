package Frame_Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import Frame.SignupFrame;

import Network.ClientNetwork.ClientSender;


public class SignupFramePanel extends JPanel implements ActionListener{
	public SignupFrameTypePanel signupTypePanel = new SignupFrameTypePanel();
	public static JButton regButton = new JButton();
	public JButton canButton = new JButton();
	public JButton idCheckButton = new JButton();
	public JButton nickCheckButton = new JButton();
	public static JLabel notice = new JLabel("");
	public static boolean nCheckResult = false;
	public static boolean iCheckResult = false;
	JFrame sf;
	
	
	private JButton changeBtn = new JButton();
	
	
	public SignupFramePanel(SignupFrame signupFrame) {
		this.sf=signupFrame;
		this.setThis(null);
	}

	public void setThis(Component prevComp) {
		this.setBounds(0, 0, Frame.SignupFrame.frameWidth, Frame.SignupFrame.frameHeight);
		this.setLayout(null);
		this.setComponents();
		this.addComponents();
	}
	

	public void setComponents() {
		signupTypePanel.setThis(null);
		
		regButton.setText("Register");
		regButton.setFont(signupTypePanel.font);
		regButton.setBorderPainted(true);
		regButton.setBounds(signupTypePanel.getX()-10, signupTypePanel.getY() + signupTypePanel.getHeight() + Frame.SignupFrame.marginHeight, signupTypePanel.getWidth() / 4, this.getFont().getSize() +  Frame.SignupFrame.marginHeight);
		regButton.addActionListener(this);
		regButton.setEnabled(false);
		
		canButton.setText("Cancel");
		canButton.setFont(signupTypePanel.font);
		canButton.setBorderPainted(true);
		canButton.setBounds(signupTypePanel.getX()-10+signupTypePanel.getWidth() / 4, signupTypePanel.getY() + signupTypePanel.getHeight() + Frame.SignupFrame.marginHeight, signupTypePanel.getWidth() / 4, this.getFont().getSize() +  Frame.SignupFrame.marginHeight);
		canButton.addActionListener(this);
		
		idCheckButton.setText("ID Check");
		idCheckButton.setFont(signupTypePanel.font);
		idCheckButton.setBorderPainted(true);
		idCheckButton.setSize(this.getPreferredSize().getSize());
		idCheckButton.setBounds(signupTypePanel.getX()-10+signupTypePanel.getWidth() *2 / 4, signupTypePanel.getY() + signupTypePanel.getHeight() + Frame.SignupFrame.marginHeight, signupTypePanel.getWidth() / 4, this.getFont().getSize() +  Frame.SignupFrame.marginHeight);
		idCheckButton.addActionListener(this);
		
		nickCheckButton.setText("Nick Check");
		nickCheckButton.setFont(signupTypePanel.font);
		nickCheckButton.setBorderPainted(true);
		nickCheckButton.setSize(this.getPreferredSize().getSize());
		nickCheckButton.setBounds(signupTypePanel.getX()-10+signupTypePanel.getWidth() *3 / 4, signupTypePanel.getY() + signupTypePanel.getHeight() + Frame.SignupFrame.marginHeight, signupTypePanel.getWidth() *1 / 3, this.getFont().getSize() +  Frame.SignupFrame.marginHeight);
		nickCheckButton.addActionListener(this);
		
		notice.setText("");
		notice.setFont(signupTypePanel.font);
		notice.setBounds(180, 250,200,20);
		
	}

	public void addComponents() {
		this.add(signupTypePanel);
		this.add(regButton);
		this.add(canButton);
		this.add(idCheckButton);
		this.add(nickCheckButton);
		this.add(notice);
		this.add(changeBtn);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().toString().contains("text=Register")) {

			ClientSender.sendMsg("[Register],"+Network.ClientNetwork.userID+","+signupTypePanel.idTextF.getText()+","+String.valueOf(signupTypePanel.pwTextF.getPassword())+","+signupTypePanel.nickNameTextF.getText());			

			JOptionPane.showMessageDialog(null, "Welcome to Handong Marble!", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Welcome!");
			sf.dispose();
			
		}
		if (e.getSource().toString().contains("text=Cancel")) {
			System.out.println("Good Bye");
			sf.dispose();
		}
		if (e.getSource().toString().contains("text=ID Check")) {
			if(signupTypePanel.idTextF.getText().equals(""))
				return;
			ClientSender.sendMsg("[IdCheck],"+Network.ClientNetwork.userID+","+signupTypePanel.idTextF.getText());
			
		}
		if (e.getSource().toString().contains("text=Nick Check")) {
			if(signupTypePanel.nickNameTextF.getText().equals(""))
				return;
			ClientSender.sendMsg("[NickCheck],"+Network.ClientNetwork.userID+","+signupTypePanel.nickNameTextF.getText());
			
		}
		
	}
}
