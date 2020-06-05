package Frame_Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import backend.game.economic.Player;

public class PlayerPanel extends JPanel{

	private int playerNumber;
	JLabel lblPlayerNumber;
	static int totalPlayers = 0; // we might need this number later on
	public static HashMap<Integer, Integer> ledger= new HashMap<>();
	private Player player;

	//private int currentSquareNumber = 0; // where player is currently located on (0 - 19). initially zero
	private ArrayList<Integer> titleDeeds = new ArrayList<Integer>(); // squares that the player has
	
	public ArrayList<Integer> getTitleDeeds() {
		return titleDeeds;
	}

	public boolean hasTitleDeed(int squareNumber) {
		return titleDeeds.contains(squareNumber) ? true : false;
	}
	public PlayerPanel(int xCoord, int yCoord, int width, int height) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBounds(xCoord, yCoord, 20, 20);
		this.setLayout(null);
	}

	public PlayerPanel(int playerNumber, Color color, Player player) {
		// TODO Auto-generated constructor stub
		this.playerNumber = playerNumber;
		this.player = player;
		this.setBackground(color);
		lblPlayerNumber = new JLabel(""+playerNumber);
		lblPlayerNumber.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblPlayerNumber.setForeground(Color.WHITE);
		this.add(lblPlayerNumber); 
		if(playerNumber == 1) {
			this.setBounds(30, 33, 20, 28); // need to fix here for adjustable player numbers
		} else if(playerNumber == 2) {
			this.setBounds(60, 33, 20, 28); // need to fix here for adjustable player numbers
		} else if(playerNumber == 3) {
			this.setBounds(30, 63, 20, 28); // need to fix here for adjustable player numbers
		} else if(playerNumber == 4) {
			this.setBounds(60, 63, 20, 28); // need to fix here for adjustable player numbers
		}
		totalPlayers++;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}


	static int[][] xLocations = {
			{31, 131, 231, 331, 431, 531,
			531, 531, 531, 531, 531,
			431, 331, 231, 131, 31,
			31, 31, 31, 31},
			{61, 191, 291, 361, 461, 561,
				561, 561, 561, 561, 561,
				461, 361, 261, 161, 61,
				61, 61, 61, 61},
			{31, 131, 231, 331, 431, 531,
					531, 531, 531, 531, 531,
					431, 331, 231, 131, 31,
					31, 31, 31, 31},
			{61, 191, 291, 361, 461, 561,
						561, 561, 561, 561, 561,
						461, 361, 261, 161, 61,
						61, 61, 61, 61}
			};

	int[][] yLocations = {
			{33, 33, 33, 33, 33, 33,
			133, 233, 333, 433, 533,
			533, 533, 533, 533, 533,
			433, 333, 233, 133},
			{33, 33, 33, 33, 33, 33,
				133, 233, 333, 433, 533,
				533, 533, 533, 533, 533,
				433, 333, 233, 133},
			{63, 63, 63, 63, 63, 63,
					163, 263, 363, 463, 563,
					563, 563, 563, 563, 563,
					463, 363, 263, 163},
			{63, 63, 63, 63, 63, 63,
						163, 263, 363, 463, 563,
						563, 563, 563, 563, 563,
						463, 363, 263, 163}
			};

	public void updatePosition() {
		this.setLocation(xLocations[playerNumber-1][(player.getPosition()+10)%20], yLocations[playerNumber-1][(player.getPosition()+10)%20]);
	}



	// by comparing player's coordinates according to the board, we will get it's
	// current square number
	// currently unused, found a better way
	public int getCurrentSquareNumberByCoordinates() {

		int x = this.getX();
		int y = this.getY();

		if(x < 100) {
			if(y < 100) {
				return 0;
			} else if(y > 100 && y < 200) {
				return 19;
			} else if(y > 200 && y < 300) {
				return 18;
			} else if(y > 300 && y < 400) {
				return 17;
			} else if(y > 400 && y < 500) {
				return 16;
			} else {
				return 15;
			}
		} else if(x > 100 && x < 200) {
			if(y < 100) {
				return 1;
			} else {
				return 14;
			}
		} else if(x > 200 && x < 300) {
			if(y < 100) {
				return 2;
			} else {
				return 13;
			}
		} else if(x > 300 && x < 400) {
			if(y < 100) {
				return 3;
			} else {
				return 12;
			}
		}else if(x > 400 && x < 500) {
			if(y < 100) {
				return 4;
			} else {
				return 11;
			}
		} else {
			if(y < 100) {
				return 5;
			} else if(y > 100 && y < 200) {
				return 6;
			} else if(y > 200 && y < 300) {
				return 7;
			} else if(y > 300 && y < 400) {
				return 8;
			} else if(y > 300 && y < 500) {
				return 9;
			} else {
				return 10;
			}
		}
	}

}
