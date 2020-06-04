package Frame_Components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Network.ClientNetwork.ClientSender;

public class SignupFramePanel extends JPanel implements ActionListener{
	public SignupFrameTypePanel signupTypePanel = new SignupFrameTypePanel();
	public JButton regButton = new JButton();
	public JButton canButton = new JButton();
	public JButton idCheckButton = new JButton();
	
	
	public SignupFramePanel() {this.setThis(null);}

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
		regButton.setBounds(signupTypePanel.getX(), signupTypePanel.getY() + signupTypePanel.getHeight() + Frame.SignupFrame.marginHeight, signupTypePanel.getWidth() / 3, this.getFont().getSize() +  Frame.SignupFrame.marginHeight);
		regButton.addActionListener(this);
		
		canButton.setText("Cancel");
		canButton.setFont(signupTypePanel.font);
		canButton.setBorderPainted(true);
		canButton.setBounds(signupTypePanel.getX()+signupTypePanel.getWidth() / 3, signupTypePanel.getY() + signupTypePanel.getHeight() + Frame.SignupFrame.marginHeight, signupTypePanel.getWidth() / 3, this.getFont().getSize() +  Frame.SignupFrame.marginHeight);
		canButton.addActionListener(this);
		
		idCheckButton.setText("ID check");
		idCheckButton.setFont(signupTypePanel.font);
		idCheckButton.setBorderPainted(true);
		idCheckButton.setSize(this.getPreferredSize().getSize());
		idCheckButton.setBounds(signupTypePanel.getX()+signupTypePanel.getWidth() *2 / 3, signupTypePanel.getY() + signupTypePanel.getHeight() + Frame.SignupFrame.marginHeight, signupTypePanel.getWidth() / 3, this.getFont().getSize() +  Frame.SignupFrame.marginHeight);
		idCheckButton.addActionListener(this);
	}

	public void addComponents() {
		this.add(signupTypePanel);
		this.add(regButton);
		this.add(canButton);
		this.add(idCheckButton);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().toString().contains("text=Register")) {
			ClientSender.sendMsg("[Register],"+Network.ClientNetwork.userID+","+signupTypePanel.idTextF.getText()+","+String.valueOf(signupTypePanel.pwTextF.getPassword())+","+signupTypePanel.nickNameTextF.getText());			
//			JOptionPane.showMessageDialog(null, "Welcome to Handong Marble!", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);

		}
		else if(e.getSource().toString().contains("text=ID check")) {
			ClientSender.sendMsg("[IdCheck],"+Network.ClientNetwork.userID+","+signupTypePanel.idTextF.getText());
		}
		else if(e.getSource().toString().contains("text=Nick check")) {
			ClientSender.sendMsg("[NickCheck],"+Network.ClientNetwork.userID+","+signupTypePanel.nickNameTextF.getText());
		}
	}
}
