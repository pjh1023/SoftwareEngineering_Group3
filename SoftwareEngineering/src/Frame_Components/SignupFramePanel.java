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

public class SignupFramePanel extends JPanel implements ActionListener{
	public SignupFrameTypePanel signupTypePanel = new SignupFrameTypePanel();
	public JButton regButton = new JButton();
	public JButton canButton = new JButton();
	public JButton idCheckButton = new JButton();
	public JButton nickCheckButton = new JButton();
	public JLabel notice = new JLabel("");
	public boolean nCheckResult = false;
	public boolean iCheckResult = false;
	JFrame sf;
	
	//temp check button 입니다
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
		
		idCheckButton.setText("ID check");
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
		notice.setBounds(120, 250,200,20);
		
		
		////state change btn
		changeBtn.setText("State Change");
		changeBtn.setBounds(300, 250, 100, 20);
		changeBtn.addActionListener(this);
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
			JOptionPane.showMessageDialog(null, "Welcome to Handong Marble!", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Welcome!");
			sf.dispose();
		}
		if (e.getSource().toString().contains("text=Cancel")) {
			System.out.println("Good Bye");
			sf.dispose();
		}
		if (e.getSource().toString().contains("text=ID")) {
			if(iCheckResult) {
				System.out.println("ID Complete");
				notice.setText("ID Check Complete");
				notice.setForeground(new Color(0,200,0));
			}
			else {
				System.out.println("ID Already Exists");
				notice.setText("ID Already Exists");
				notice.setForeground(Color.red);
				
			}
			if(iCheckResult&&nCheckResult) {
				notice.setText("Register Enable!");
				notice.setForeground(new Color(0,200,0));
				regButton.setEnabled(true);
			}
		}
		if (e.getSource().toString().contains("text=Nick")) {
			if(nCheckResult) {
				System.out.println("Nick Complete");
				notice.setText("Nick Check Complete");
				notice.setForeground(new Color(0,200,0));
			}
			else {
				System.out.println("Nick Already Exists");
				notice.setText("Nick Already Exists");
				notice.setForeground(Color.red);
			}
			
			if(iCheckResult&&nCheckResult) {
				notice.setText("Register Enable!");
				notice.setForeground(new Color(0,200,0));
				regButton.setEnabled(true);
			}
		}
		if (e.getSource().toString().contains("text=State")) {
			if(!iCheckResult) {
				iCheckResult = true;
				nCheckResult = false;
			}else
			{
				iCheckResult = true;
				nCheckResult = true;
			}// 지금 state change 한번 누르면 아이디 true, 두번째 누르면 닉네임까지 true 로 바꿔줍니다.(임시테스트로직)
			
			
			System.out.println("state changed");
		}
	}
}
