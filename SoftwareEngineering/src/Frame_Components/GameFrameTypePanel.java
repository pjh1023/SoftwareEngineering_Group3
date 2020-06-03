package Frame_Components;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import gui.Board;
import gui.Dice;


public class GameFrameTypePanel extends JPanel {
	
	//private JLabel gameTitle = new JLabel("HGU MARBLE");
	//private JLabel gameMap = new JLabel("Game Map");
	
	static final int width = Frame.GameFrame.frameWidth;
	static final int height = Frame.GameFrame.frameHeight;
	
	
	//private GameMapPanel gameMapPanel = new GameMapPanel();
	private Board board = new Board(0,0,width *2/3, height *2/3); //game board
	//private gui.MonopolyMain gameMapPanel = new gui.MonopolyMain();
	private GamePlayerPanel gamePlayerPanel = new GamePlayerPanel(); //player information section
	//private GameChatPanel gameChatPanel = new GameChatPanel();
	//private GameInfoPanel gameInfoPanel = new GameInfoPanel();
	private Player player1 = new Player(1, Color.RED);  //create 4 players_player arrangements in Player class
	private Player player2 = new Player(2, Color.BLUE);
	private Player player3 = new Player(3, Color.GREEN);
	private Player player4 = new Player(4, Color.YELLOW);
	

	//private JPanel contentIncluder;
	static JTextArea infoConsole;
	JPanel playerAssetsPanel;
	CardLayout c1 = new CardLayout();
	ArrayList<Player> players = new ArrayList<Player>(); //player arrayList here
	static int turnCounter = 0;
	JButton btnNextTurn;
	JButton btnRollDice;
	JButton btnPayRent;
	JButton btnBuy;
	JButton btnUpgrade;
	JTextArea panelPlayer1TextArea;
	JTextArea panelPlayer2TextArea;
	JTextArea panelPlayer3TextArea;
	JTextArea panelPlayer4TextArea;
	//Board gameBoard;
	//Player player1;
	//Player player2;
	Boolean doubleDiceForPlayer1 = false; //if you have double dice performance, need this variables
	Boolean doubleDiceForPlayer2 = false;
	Boolean doubleDiceForPlayer3 = false;
	Boolean doubleDiceForPlayer4 = false;
	static int nowPlaying = 0; //which player is playing now? 0(index)-> player 1 
	
	//player1 = new Player(1, Color.RED);

	///Buy Frame
	BorderLayout br = new BorderLayout();
	
	class buyFrame extends JDialog{
		JLabel buyText = new JLabel("R U Buy?");
		JButton yesB = new JButton("Yes");
		JButton noB = new JButton("No");
		JPanel temp = new JPanel();
		
		public buyFrame() {
			//super(frame);
			setLayout(br);
			add(br.SOUTH, temp);
			temp.add(yesB);
			yesB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Buy Yes");
					setVisible(false);
				}
			});
			temp.add(noB);
			
			noB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Buy no");
					setVisible(false);
				}
			});
			setSize(300,200);
			setLocation(500,300);
			add(br.NORTH, buyText);
		}
	}
	
	class upgradeFrame extends JDialog{
		JLabel upgradeText = new JLabel("R U Upgrade?");
		JButton yesB = new JButton("Yes");
		JButton noB = new JButton("No");
		JPanel temp = new JPanel();
		
		public upgradeFrame() {
			//super(frame);
			setLayout(br);
			add(br.SOUTH, temp);
			temp.add(yesB);
			yesB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Upgrade Yes");
					setVisible(false);
				}	
			});
			temp.add(noB);
			
			noB.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Upgrade no");
					setVisible(false);
				}
			});
			setSize(300,200);
			setLocation(500,300);
			add(br.NORTH, upgradeText);
		}
	}
	
	buyFrame bf;
	upgradeFrame uf;
		
	
	public void addComponents() {
		
		players.add(player1);
		this.add(player1, new Integer(1));
		
		players.add(player2);
		this.add(player2, new Integer(1));
		
		players.add(player3);
		this.add(player3, new Integer(1));
		
		players.add(player4);
		this.add(player4, new Integer(1));
		
		JPanel rightPanel = new JPanel(); //for buttons and turn explanation gray panel
		rightPanel.setBackground(Color.LIGHT_GRAY);
		rightPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		rightPanel.setBounds(613, 0, 1440*2/3 - 612, 612);
		this.add(rightPanel);
		rightPanel.setLayout(null);

		//Buy Action!!! 
		btnBuy = new JButton("Buy");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				bf = new buyFrame();
				bf.setVisible(true);
				
				Player currentPlayer = players.get(nowPlaying); //which player's turn?
				
				infoConsole.setText("You bought "+board.getAllSquares().get(currentPlayer.getCurrentSquareNumber()).getName()); //set Text for buying info
				currentPlayer.buyTitleDeed(currentPlayer.getCurrentSquareNumber()); //actual buying game logic (in Player class)
				int withdrawAmount = board.getAllSquares().get(currentPlayer.getCurrentSquareNumber()).getPrice(); //money taken from player's bank game logic
				currentPlayer.withdrawFromWallet(withdrawAmount);//actual withdrawal action 
				btnBuy.setEnabled(false); 
				updatePanelPlayer1TextArea(); //show information update 
				updatePanelPlayer2TextArea();
				updatePanelPlayer3TextArea();
				updatePanelPlayer4TextArea();
				
				board.getAllSquares().get(currentPlayer.getCurrentSquareNumber()).setBoxColor(currentPlayer.getPlayerNumber());;
			}
		});
		//button setting
		btnBuy.setBounds(40, 478, 117, 29);
		rightPanel.add(btnBuy);
		btnBuy.setEnabled(false);
		
		btnUpgrade = new JButton("Upgrade");
		btnUpgrade.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				uf = new upgradeFrame();
				uf.setVisible(true);
			}
		});
		btnUpgrade.setBounds(40, 270, 117, 29);
		rightPanel.add(btnUpgrade);
		btnUpgrade.setEnabled(true);
		
		
		//paying rent Action!!
		btnPayRent = new JButton("Pay Rent");
		btnPayRent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Player currentPlayer = players.get(nowPlaying); //which player's turn?
				Player ownerOfTheSquare; //whose property?
				if((Player.ledger.get(currentPlayer.getCurrentSquareNumber()))==1) { //getting whose property game logic(compare square location and player information (in Player class)
					ownerOfTheSquare = players.get(0);
				}else if((Player.ledger.get(currentPlayer.getCurrentSquareNumber()))==2) {
					ownerOfTheSquare = players.get(1);
				}else if((Player.ledger.get(currentPlayer.getCurrentSquareNumber()))==3) {
					ownerOfTheSquare = players.get(2);
				}else {
					ownerOfTheSquare = players.get(3);
				}
				
				infoConsole.setText("You paid to the player "+ownerOfTheSquare.getPlayerNumber()); //show updation action info

				int withdrawAmount = board.getAllSquares().get(currentPlayer.getCurrentSquareNumber()).getRentPrice(); //payment amount
				System.out.println(withdrawAmount);
				currentPlayer.withdrawFromWallet(withdrawAmount); //withdrawal from player's bank whose paying
				ownerOfTheSquare.depositToWallet(withdrawAmount); //deposit to the owner 
				
				//button setting
				btnNextTurn.setEnabled(true);
				btnPayRent.setEnabled(false);
				//update info at player text Area
				updatePanelPlayer1TextArea();
				updatePanelPlayer2TextArea();
				updatePanelPlayer3TextArea();
				updatePanelPlayer4TextArea();
				//turnCounter++;
				//gameBoard.getAllSquares().get(player1.getCurrentSquareNumber()).setRentPaid(true);
				
			}

		});
		
		//button setting
		btnPayRent.setBounds(170, 478, 117, 29);
		rightPanel.add(btnPayRent);
		btnPayRent.setEnabled(false);

		//DICE!!
		Dice dice1 = new Dice(244, 406, 40, 40);
		this.add(dice1, new Integer(1));

		Dice dice2 = new Dice(333, 406, 40, 40);
		this.add(dice2, new Integer(1));

		//Roll dice Action!!
		btnRollDice = new JButton("Roll Dice");
		btnRollDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(nowPlaying == 0) { //repeated for each players
					// player1's turn
					int dice1OldValue = dice1.getFaceValue(); //get dice number from Dice class
					int dice2OldValue = dice2.getFaceValue();
					dice1.rollDice(); //for dice icon UI
					dice2.rollDice();
					int dicesTotal = dice1.getFaceValue() + dice2.getFaceValue(); //for movement
					if(dice1.getFaceValue() == dice2.getFaceValue()) { //double dice performance if needed(roll once more)
						doubleDiceForPlayer1 = true;
					} else {
						doubleDiceForPlayer1 = false;
					}
					player1.move(dicesTotal); //movement action in Player class
					if(Player.ledger.containsKey(player1.getCurrentSquareNumber()) // if bought by someone
							&& Player.ledger.get(player1.getCurrentSquareNumber()) != player1.getPlayerNumber() // not by itself
							) {
						btnBuy.setEnabled(false);
						btnRollDice.setEnabled(false);
						btnNextTurn.setEnabled(false);
						btnPayRent.setEnabled(true);
					} 
					if (Player.ledger.containsKey(player1.getCurrentSquareNumber()) // if bought by someone 
							&& Player.ledger.get(player1.getCurrentSquareNumber()) == player1.getPlayerNumber()) { // and by itself
						btnBuy.setEnabled(false);
						btnPayRent.setEnabled(false);
						btnNextTurn.setEnabled(true);
					}
					if(board.getUnbuyableSquares().contains(board.getAllSquares().get(player1.getCurrentSquareNumber()))) { //for unbuyablesuares(ex. jail)
						btnBuy.setEnabled(false);
						btnNextTurn.setEnabled(true);
					} else if (!Player.ledger.containsKey(player1.getCurrentSquareNumber())) { // if not bought by someone
						btnBuy.setEnabled(true);
						btnNextTurn.setEnabled(true);
						btnPayRent.setEnabled(false);
					} 
					
	
				} else if(nowPlaying == 1){//repeated for player 2
					// player2's turn
					int dice1OldValue = dice1.getFaceValue();
					int dice2OldValue = dice2.getFaceValue();
					dice1.rollDice();
					dice2.rollDice();
					int dicesTotal = dice1.getFaceValue() + dice2.getFaceValue();
					if(dice1.getFaceValue() == dice2.getFaceValue()) {
						doubleDiceForPlayer2 = true;
					} else {
						doubleDiceForPlayer2 = false;
					}
					player2.move(dicesTotal);
					if(Player.ledger.containsKey(player2.getCurrentSquareNumber()) // if bought by someone
							&& Player.ledger.get(player2.getCurrentSquareNumber()) != player2.getPlayerNumber() // not by itself
							) {
						btnBuy.setEnabled(false);
						btnRollDice.setEnabled(false);
						btnNextTurn.setEnabled(false);
						btnPayRent.setEnabled(true);
					}
					if(Player.ledger.containsKey(player2.getCurrentSquareNumber()) // if bought by someone 
							&& Player.ledger.get(player2.getCurrentSquareNumber()) == player2.getPlayerNumber()) { // and by itself
						btnBuy.setEnabled(false);
						btnPayRent.setEnabled(false);

					}
					if(board.getUnbuyableSquares().contains(board.getAllSquares().get(player2.getCurrentSquareNumber()))) {
						btnBuy.setEnabled(false);
						btnNextTurn.setEnabled(true);
					} else if (!Player.ledger.containsKey(player2.getCurrentSquareNumber())) { // if not bought by someone
						btnBuy.setEnabled(true);
						btnNextTurn.setEnabled(true);
						btnPayRent.setEnabled(false);
					}

				} else if(nowPlaying == 2){
					// player3's turn
					int dice1OldValue = dice1.getFaceValue();
					int dice2OldValue = dice2.getFaceValue();
					dice1.rollDice();
					dice2.rollDice();
					int dicesTotal = dice1.getFaceValue() + dice2.getFaceValue();
					if(dice1.getFaceValue() == dice2.getFaceValue()) {
						doubleDiceForPlayer3 = true;
					} else {
						doubleDiceForPlayer3 = false;
					}
					player3.move(dicesTotal);
					if(Player.ledger.containsKey(player3.getCurrentSquareNumber()) // if bought by someone
							&& Player.ledger.get(player3.getCurrentSquareNumber()) != player3.getPlayerNumber() // not by itself
							) {
						btnBuy.setEnabled(false);
						btnRollDice.setEnabled(false);
						btnNextTurn.setEnabled(false);
						btnPayRent.setEnabled(true);
					}
					if(Player.ledger.containsKey(player3.getCurrentSquareNumber()) // if bought by someone 
							&& Player.ledger.get(player3.getCurrentSquareNumber()) == player3.getPlayerNumber()) { // and by itself
						btnBuy.setEnabled(false);
						btnPayRent.setEnabled(false);

					}
					if(board.getUnbuyableSquares().contains(board.getAllSquares().get(player3.getCurrentSquareNumber()))) {
						btnBuy.setEnabled(false);
						btnNextTurn.setEnabled(true);
					} else if (!Player.ledger.containsKey(player3.getCurrentSquareNumber())) { // if not bought by someone
						btnBuy.setEnabled(true);
						btnNextTurn.setEnabled(true);
						btnPayRent.setEnabled(false);
					}
				} else if(nowPlaying == 3){
					// player4's turn
					int dice1OldValue = dice1.getFaceValue();
					int dice2OldValue = dice2.getFaceValue();
					dice1.rollDice();
					dice2.rollDice();
					int dicesTotal = dice1.getFaceValue() + dice2.getFaceValue();
					if(dice1.getFaceValue() == dice2.getFaceValue()) {
						doubleDiceForPlayer4 = true;
					} else {
						doubleDiceForPlayer4 = false;
					}
					player4.move(dicesTotal);
					if(Player.ledger.containsKey(player4.getCurrentSquareNumber()) // if bought by someone
							&& Player.ledger.get(player4.getCurrentSquareNumber()) != player4.getPlayerNumber() // not by itself
							) {
						btnBuy.setEnabled(false);
						btnRollDice.setEnabled(false);
						btnNextTurn.setEnabled(false);
						btnPayRent.setEnabled(true);
					}
					if(Player.ledger.containsKey(player4.getCurrentSquareNumber()) // if bought by someone 
							&& Player.ledger.get(player4.getCurrentSquareNumber()) == player4.getPlayerNumber()) { // and by itself
						btnBuy.setEnabled(false);
						btnPayRent.setEnabled(false);

					}
					if(board.getUnbuyableSquares().contains(board.getAllSquares().get(player4.getCurrentSquareNumber()))) {
						btnBuy.setEnabled(false);
						btnNextTurn.setEnabled(true);
					} else if (!Player.ledger.containsKey(player4.getCurrentSquareNumber())) { // if not bought by someone
						btnBuy.setEnabled(true);
						btnNextTurn.setEnabled(true);
						btnPayRent.setEnabled(false);
					}
				}

				btnRollDice.setEnabled(false);
				
				
				if(doubleDiceForPlayer1 || doubleDiceForPlayer2||doubleDiceForPlayer3||doubleDiceForPlayer4) {//if double dice, the same player roll dice (for printing info, not actual game logic)
					infoConsole.setText("Click Next Turn to allow player "+ (nowPlaying+1) +" to Roll Dice!");
				} else {
					infoConsole.setText("Click Next Turn to allow player "+ ((nowPlaying+1)%4+1) +" to Roll Dice!"); //else next player roll dice (for printing info)
				} 
				
				
				updatePanelPlayer1TextArea();
				updatePanelPlayer2TextArea();
				updatePanelPlayer3TextArea();
				updatePanelPlayer4TextArea();


			}
		});		
		//button setting 
		btnRollDice.setBounds(40, 413, 246, 53);
		rightPanel.add(btnRollDice);

		//next turn Action!!!
		btnNextTurn = new JButton("Next Turn");
		btnNextTurn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnRollDice.setEnabled(true);
				btnBuy.setEnabled(false);
				btnPayRent.setEnabled(false);
				btnNextTurn.setEnabled(false);
				
				//if double dice, the same player, 
				if(nowPlaying == 0 && doubleDiceForPlayer1) {
					nowPlaying = 0;
					doubleDiceForPlayer1 = false;
				} else if(nowPlaying == 1 && doubleDiceForPlayer2) {
					nowPlaying = 1;
					doubleDiceForPlayer2 = false;
				} else if(nowPlaying == 2 && doubleDiceForPlayer3) {
					nowPlaying = 2;
					doubleDiceForPlayer3 = false;
				} else if(nowPlaying == 3 && doubleDiceForPlayer4) {
					nowPlaying = 3;
					doubleDiceForPlayer4 = false;
				} else if(!doubleDiceForPlayer1 && !doubleDiceForPlayer2 && !doubleDiceForPlayer3&& !doubleDiceForPlayer4) { //if not, the next player's turn
					nowPlaying = (nowPlaying + 1) % 4;
				}
				
				
				c1.show(playerAssetsPanel, ""+(nowPlaying+1)); // maps 0 to 1 and 1 to 2
				updatePanelPlayer1TextArea();
				updatePanelPlayer2TextArea();
				updatePanelPlayer3TextArea();
				updatePanelPlayer4TextArea();
				infoConsole.setText("It's now player "+(nowPlaying+1)+"'s turn!");
			}

		

		});
		
		//button setting
		btnNextTurn.setBounds(40, 519, 246, 53);
		rightPanel.add(btnNextTurn);
		btnNextTurn.setEnabled(false);

		//panel for infoConsol which shows game direction
		JPanel test = new JPanel();
		test.setBounds(40, 312, 246, 68);
		rightPanel.add(test);
		test.setLayout(null);

		playerAssetsPanel = new JPanel();
		playerAssetsPanel.setBounds(40, 28, 246, 189);
		rightPanel.add(playerAssetsPanel);
		playerAssetsPanel.setLayout(c1);

		JPanel panelPlayer1 = new JPanel();
		panelPlayer1.setBackground(Color.RED);
		playerAssetsPanel.add(panelPlayer1, "1");
		panelPlayer1.setLayout(null);

		JLabel panelPlayer1Title = new JLabel("Player 1 All Wealth");
		panelPlayer1Title.setForeground(Color.WHITE);
		panelPlayer1Title.setHorizontalAlignment(SwingConstants.CENTER);
		panelPlayer1Title.setBounds(0, 6, 240, 16);
		panelPlayer1.add(panelPlayer1Title);

		panelPlayer1TextArea = new JTextArea();
		panelPlayer1TextArea.setBounds(10, 34, 230, 149);
		panelPlayer1.add(panelPlayer1TextArea);

		JPanel panelPlayer2 = new JPanel();
		panelPlayer2.setBackground(Color.BLUE);
		playerAssetsPanel.add(panelPlayer2, "2");
		panelPlayer2.setLayout(null);
		c1.show(playerAssetsPanel, ""+nowPlaying);

		JLabel panelPlayer2Title = new JLabel("Player 2 All Wealth");
		panelPlayer2Title.setForeground(Color.WHITE);
		panelPlayer2Title.setHorizontalAlignment(SwingConstants.CENTER);
		panelPlayer2Title.setBounds(0, 6, 240, 16);
		panelPlayer2.add(panelPlayer2Title);

		panelPlayer2TextArea = new JTextArea();
		panelPlayer2TextArea.setBounds(10, 34, 230, 149);
		panelPlayer2.add(panelPlayer2TextArea);
		
		JPanel panelPlayer3 = new JPanel();
		panelPlayer3.setBackground(Color.GREEN);
		playerAssetsPanel.add(panelPlayer3, "3");
		panelPlayer3.setLayout(null);

		JLabel panelPlayer3Title = new JLabel("Player 3 All Wealth");
		panelPlayer3Title.setForeground(Color.WHITE);
		panelPlayer3Title.setHorizontalAlignment(SwingConstants.CENTER);
		panelPlayer3Title.setBounds(0, 6, 240, 16);
		panelPlayer3.add(panelPlayer3Title);

		panelPlayer3TextArea = new JTextArea();
		panelPlayer3TextArea.setBounds(10, 34, 230, 149);
		panelPlayer3.add(panelPlayer3TextArea);
		
		JPanel panelPlayer4 = new JPanel();
		panelPlayer4.setBackground(Color.YELLOW);
		playerAssetsPanel.add(panelPlayer4, "4");
		panelPlayer4.setLayout(null);

		JLabel panelPlayer4Title = new JLabel("Player 4 All Wealth");
		panelPlayer4Title.setForeground(Color.WHITE);
		panelPlayer4Title.setHorizontalAlignment(SwingConstants.CENTER);
		panelPlayer4Title.setBounds(0, 6, 240, 16);
		panelPlayer4.add(panelPlayer4Title);

		panelPlayer4TextArea = new JTextArea();
		panelPlayer4TextArea.setBounds(10, 34, 230, 149);
		panelPlayer4.add(panelPlayer4TextArea);
		
		updatePanelPlayer1TextArea();
		updatePanelPlayer2TextArea();
		updatePanelPlayer3TextArea();
		updatePanelPlayer4TextArea();

		infoConsole = new JTextArea();
		infoConsole.setColumns(20);
		infoConsole.setRows(5);
		infoConsole.setBounds(6, 6, 234, 56);
		test.add(infoConsole);
		infoConsole.setLineWrap(true);
		infoConsole.setText("PLayer 1 starts the game by clicking Roll Dice!");
		
		//this.add(gameMapPanel);
		this.add(board);
		//this.add(gameInfoPanel);
		this.add(gamePlayerPanel);
		//this.add(gameChatPanel);
		//this.add(gameMapPanel);
		
	}

	public void setComponents() {
		//gameMapPanel.setBounds(0, 0, 612, 612);
		board.setBounds(0,0,612,612);
		//gameInfoPanel.setBounds(width *2/3, 0, width *1/3,height *2/3);
		gamePlayerPanel.setBounds(0, 613, width , height-612);
		//gameChatPanel.setBounds(width *2/3, height *2/3, width *1/3,height *1/3);
	}
	
	public void setThis() {
		this.setLayout(null);
		this.setBounds(0,0,width,height);
		this.addComponents();
		this.setComponents();
	}

	public void updatePanelPlayer4TextArea() {
		// TODO Auto-generated method stub
		String result = "";
		String lands = "";
		result += "Current Balance: "+player4.getWallet()+"\n";
		
		result += "Title Deeds: \n";
		for(int i = 0; i < player4.getTitleDeeds().size(); i++) {
			lands += board.getAllSquares().get(player4.getTitleDeeds().get(i)).getName()+",";
			result += " - "+board.getAllSquares().get(player4.getTitleDeeds().get(i)).getName()+"\n";
		}
		panelPlayer4TextArea.setText(result);

		if(lands.endsWith(",")) {
			lands = lands.substring(0, lands.length()-1);			
		}
		gamePlayerPanel.p4.setPlayerInfo(player4.getWallet(), lands);
	}
	
	public void updatePanelPlayer3TextArea() {
		// TODO Auto-generated method stub
		String result = "";
		String lands = "";
		result += "Current Balance: "+player3.getWallet()+"\n";
		
		result += "Title Deeds: \n";
		for(int i = 0; i < player3.getTitleDeeds().size(); i++) {
			lands += board.getAllSquares().get(player3.getTitleDeeds().get(i)).getName()+",";
			result += " - "+board.getAllSquares().get(player3.getTitleDeeds().get(i)).getName()+"\n";
		}
		
		panelPlayer3TextArea.setText(result);
		
		if(lands.endsWith(",")) {
			lands = lands.substring(0, lands.length()-1);			
		}
		gamePlayerPanel.p3.setPlayerInfo(player3.getWallet(), lands);
	}
	
	public void updatePanelPlayer2TextArea() {
		// TODO Auto-generated method stub
		String result = "";
		String lands = "";
		result += "Current Balance: "+player2.getWallet()+"\n";
		
		result += "Title Deeds: \n";
		for(int i = 0; i < player2.getTitleDeeds().size(); i++) {
			lands += board.getAllSquares().get(player2.getTitleDeeds().get(i)).getName()+",";
			result += " - "+board.getAllSquares().get(player2.getTitleDeeds().get(i)).getName()+"\n";
		}
		
		panelPlayer2TextArea.setText(result);
		if(lands.endsWith(",")) {
			lands = lands.substring(0, lands.length()-1);			
		}
		gamePlayerPanel.p2.setPlayerInfo(player2.getWallet(), lands);
	}

	public void updatePanelPlayer1TextArea() {
		// TODO Auto-generated method stub
		String result = "";
		String lands = "";
		result += "Current Balance: "+player1.getWallet()+"\n";
		
		result += "Title Deeds: \n";
		for(int i = 0; i < player1.getTitleDeeds().size(); i++) {
			lands += board.getAllSquares().get(player1.getTitleDeeds().get(i)).getName()+",";
			result += " - "+board.getAllSquares().get(player1.getTitleDeeds().get(i)).getName()+"\n";
		}
		
		
		panelPlayer1TextArea.setText(result);
		if(lands.endsWith(",")) {
			lands = lands.substring(0, lands.length()-1);			
		}
		gamePlayerPanel.p1.setPlayerInfo(player1.getWallet(), lands);	
	}
	
	public static void errorBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.ERROR_MESSAGE);
	}


	
}
