package Frame_Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

//import java.awt;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class WaitingFramePanel extends JPanel {
	static final int width = Frame.LoginFrame.frameWidth;
	static final int height = Frame.LoginFrame.frameHeight;
	
	private JLabel headLabel = new JLabel("Handong Marble", JLabel.CENTER);

	private Font headFont = new Font ("Arial", Font.BOLD, height / 15);
	private Font buttonFont = new Font ("Arial", Font.BOLD, headFont.getSize() / 2);
	
	final int startY = height / 5;
	
	public WaitingFramePanel() {this.setThis(null);}
	public void setThis(Component prevComp) {
		this.setBounds(0,0, width, height);
		this.setLayout(null);
		addComponents();
		setComponents();
	}
	
	public void setComponents() {
		headLabel.setFont(headFont);
		headLabel.setForeground(new Color(105, 83, 225));
		headLabel.setSize(headLabel.getPreferredSize().width, headLabel.getPreferredSize().height);
		headLabel.setLocation(width / 2 - headLabel.getPreferredSize().width / 2, startY);
	}
	public void addComponents() {	// 해당 panel에 component들을 추가해줍니다.
		this.add(headLabel);

	}

}
