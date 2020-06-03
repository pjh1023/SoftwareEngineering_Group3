package Frame_Components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameFramePanel extends JPanel { //implements ActionListener{
	
	//폰트, 헤드라벨, 버튼들, 사이즈 등 
	//private JButton diceRoll = new JButton("Dice");
	//private JButton Sample = new JButton("WoW");
	
	
	private GameFrameTypePanel gameTypePanel = new GameFrameTypePanel(); //actual board, playerinfo, dice, etc 
	
	//private JLabel headLabel = new JLabel("malmaa", JLabel.CENTER);
	
	
	static final int width = Frame.GameFrame.frameWidth;
	static final int height = Frame.GameFrame.frameHeight;
	
	
	public GameFramePanel() {
		this.setThis(null);
	}
	
	private void setThis(Component prevComp) {
		// TODO Auto-generated method stub
		this.setBounds(0,0,width,height);
		this.setLayout(new BorderLayout());
		setComponents();
		addComponents();
		
	}

	public void setComponents() {
		gameTypePanel.setThis();	
	}
	
	
	public void addComponents() {
		this.add(gameTypePanel);
	}
	
	
	//@Override
	//public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	//}
}
