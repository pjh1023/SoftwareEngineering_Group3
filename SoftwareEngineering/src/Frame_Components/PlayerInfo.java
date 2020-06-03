package Frame_Components;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;



public class PlayerInfo extends JPanel {

	 int iWidth = 120;
	 int iHeight = 120;
	
	 
	 public String pName = "Player";
	 public String pMoney = "0";
	
	 
	 public JLabel playerName = new JLabel(pName);
	 public JLabel playerMoney = new JLabel(pMoney);
	 public JTextArea lands = new JTextArea();
	 private ImageIcon pImage;
	 public JLabel playerIcon = new JLabel();
	  
	 
	 Border panelBorder = new LineBorder(Color.black);
	 Border turnBorder = new LineBorder(Color.red);
	 
	 public PlayerInfo(int pNum) {
		 this.setThis(pNum);
		 this.setLayout(null);
		
	 }
	 
	 public void setPlayer(int playerNumber) {
		 ImageIcon tImage;
		 
		 if(playerNumber==1) {
			 tImage = new ImageIcon("img/p1.png");
		 }
		 else if(playerNumber==2) {
			tImage = new ImageIcon("img/p2.png");
		
		 }
		 else if(playerNumber==3) {
			 tImage = new ImageIcon("img/p3.png");
		 }
		 else {
			 tImage = new ImageIcon("img/p4.png");
			
		 }
		 //System.out.println(playerNumber + "번 유저 이미지 설정했다");
		 Image tempImage = tImage.getImage();
		 Image dImage = tempImage.getScaledInstance(iWidth, iHeight, Image.SCALE_SMOOTH);
		 this.pImage = new ImageIcon(dImage);
	 }
	 
	public void setPlayerInfo(int Money, String Lands) {
		this.playerMoney.setText("Money : " + Integer.toString(Money));
		this.lands.setText("Lands : " + Lands);
	}
	 
	private void setThis(int pNum) {
		// TODO Auto-generated method stub
		this.setPlayer(pNum);
		this.setComponents();	
		this.setBackground(Color.white);
		
		this.add(playerIcon);
		this.add(playerName);
		this.add(playerMoney);
		this.add(lands);
		
		playerIcon.setBounds(0, 0, 120, 120);
		playerIcon.setBorder(panelBorder);
		playerName.setBounds(130, 0, 200, 30);
		playerMoney.setBounds(130, 30, 200, 40);
		
		lands.setBounds(130, 79, 340, 40);
		//lands.setBackground(Color.LIGHT_GRAY);
//		
	}
	 
	private void setComponents() {
		playerIcon.setIcon(pImage);
		this.setBorder(panelBorder);		 
		 
	}
	 

}