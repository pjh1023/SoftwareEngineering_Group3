package Frame_Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class GamePlayerPanel extends JPanel implements ActionListener{

//	static final int Playerwidth = Frame.GameFrame.frameWidth *  / 5;
//	static final int Playerheight = Frame.GameFrame.frameHeight * 2 / 5;
//	
	static final int width = Frame.GameFrame.frameWidth;
	static final int height = Frame.GameFrame.frameHeight;
	
	
//	private JButton player1 = new JButton("Player1");
//	private JButton player2 = new JButton("Player2");
//	private JButton player3 = new JButton("Player3");
//	private JButton player4 = new JButton("Player4");
	
	public PlayerInfo p1 = new PlayerInfo(1);
	public PlayerInfo p2 = new PlayerInfo(2);
	public PlayerInfo p3 = new PlayerInfo(3);
	public PlayerInfo p4 = new PlayerInfo(4);
	
	
	Border turnBorder = new LineBorder(Color.red);
	
	public GamePlayerPanel() {
		this.setThis(null);
		
	}
	
	private void setThis(Component prevComp) {
		// TODO Auto-generated method stub
		this.setBounds(0,0,width,height);
		this.setBackground(new Color(000, 255, 255));
		this.setLayout(null);
		setComponents();
		addComponents();
		//this.setTurn(1);
	}

	private void addComponents() {
		// TODO Auto-generated method stub
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.add(p4);
		
	}

//	public void setTurn(int turn) {
//		if(turn ==1) {
//			p1.setBackground(Color.red);
//			p2.setBackground(Color.white);
//			p3.setBackground(Color.white);
//			p4.setBackground(Color.white);
//		}
//		
//		else if(turn ==2) {
//			p1.setBackground(Color.white);
//			p2.setBackground(Color.blue);
//			p3.setBackground(Color.white);
//			p4.setBackground(Color.white);
//		}
//		
//		else if(turn ==3) {
//			p1.setBackground(Color.white);
//			p2.setBackground(Color.white);
//			p3.setBackground(Color.green);
//			p4.setBackground(Color.white);
//		}
//		
//		else if(turn ==4) {
//			p1.setBackground(Color.white);
//			p2.setBackground(Color.white);
//			p3.setBackground(Color.white);
//			p4.setBackground(Color.yellow);
//		}
//		else {
//				p1.setBackground(Color.white);
//				p2.setBackground(Color.white);
//				p3.setBackground(Color.white);
//				p4.setBackground(Color.white);
//		}
//		System.out.println("turn is "+turn);
//	}
	
	public void buyLand(int turn, String landName) {
		
	}
	
	
	private void setComponents() {
		// TODO Auto-generated method stub
		p1.setBounds(0, 0, width/2, 120);
		p2.setBounds(width/2, 0, width/2, 120);
		p3.setBounds(0, 120, width/2, 120);
		p4.setBounds(width/2, 120, width/2, 120);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}