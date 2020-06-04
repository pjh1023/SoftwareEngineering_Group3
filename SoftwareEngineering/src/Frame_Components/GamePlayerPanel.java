package Frame_Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import backend.game.economic.Player;

public class GamePlayerPanel extends JPanel implements ActionListener{

//	static final int Playerwidth = Frame.GameFrame.frameWidth *  / 5;
//	static final int Playerheight = Frame.GameFrame.frameHeight * 2 / 5;
//	
	static final int width = Frame.GameFrame.frameWidth;
	static final int height = Frame.GameFrame.frameHeight;
	
	public ArrayList<PlayerInfo> players;
	
	Border turnBorder = new LineBorder(Color.red);
	
	public GamePlayerPanel(ArrayList<Player> players) {
		this.players = new ArrayList<PlayerInfo>();
		for (int i=0; i<players.size();i++)
			this.players.add(new PlayerInfo(i + 1, players.get(i)));		
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
		for (int i =0 ; i< players.size();i++)
			this.add(players.get(i));		
	}

	private void setComponents() {
		// TODO Auto-generated method stub
		players.get(0).setBounds(0, 0, width/2, 120);
		players.get(1).setBounds(width/2, 0, width/2, 120);
		if (players.size() > 2)
			players.get(2).setBounds(0, 120, width/2, 120);
		if (players.size() > 3)
			players.get(3).setBounds(width/2, 120, width/2, 120);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}