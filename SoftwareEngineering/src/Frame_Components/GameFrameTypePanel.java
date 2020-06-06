package Frame_Components;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Frame.GameFrame;
import Network.ClientNetwork.ClientSender;
import Network.ClientNetwork;
import Network.User;
import backend.game.BoundedBuffer;
import backend.game.ChanceCard;
import backend.game.ChanceCardEvent;
import backend.game.City;
import backend.game.DecideTurn;
import backend.game.GameManager;
import backend.game.Land;
import backend.game.Prison;
import backend.game.Start;
import backend.game.Travel;
import backend.game.action.Action;
import backend.game.action.Bankrupt;
import backend.game.action.Imprison;
import backend.game.action.Release;
import backend.game.action.Result;
import backend.game.action.RollDice;
import backend.game.action.Transaction;
import backend.game.economic.LocalPlayer;
import backend.game.economic.NetworkPlayer;
import backend.game.economic.Player;
import gui.Board;
import gui.Dice;
import gui.Square;


public class GameFrameTypePanel extends JPanel{
	private ArrayList<User> users;
	
	public GameFrameTypePanel(ArrayList<User> users) {
		super();
		this.users = users;
	}
	
	
	public class RunGM implements Runnable {

		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Action a = gm.getAction();
				for (int i = 0; i < players.size(); i++) {
					gamePlayerPanel.players.get(i).setPlayerInfo();
					pp.get(i).updatePosition();
				}
				
				for (int i = 0; i< board.size(); i++) {
					Land l = board.get(i);
					if (l instanceof City) {
						City c = (City)l;
						if (c.getOwner() >= 0)
							boardP.getAllSquares().get(i).setBoxColor(gm.getOriginN(c.getOwner()));
					}
				}
				
				
				int pNum = gm.getOriginN(gm.getTurn());
				turnColor.setBackground(GameFrame.COLORS[pNum] );
				turnText.setText(gm.getTurnPlayer().getName() + "'s turn");
				if (a instanceof DecideTurn) {
					
				}
				else if (a instanceof RollDice) {
					RollDice rd = (RollDice)a;
					dice1.setDice(rd.getDice()/10);
					dice2.setDice(rd.getDice()%10);
				}
				else if (a instanceof Imprison) {
					Imprison imp = (Imprison)a;
					stateArea.setText(players.get(imp.getTurn()).getName() +
							(imp.isFirstTime()?" has imprisoned":" failed to escape"));
				}
				else if (a instanceof Release) {
					stateArea.setText(gm.getTurnPlayer().getName() + " is released");		
				}
				else if (a instanceof Transaction) {
					Transaction ts = (Transaction)a;
					if (ts.getFrom() >= 0 && ts.getTo() >= 0 ) {
						stateArea.setText(players.get(ts.getFrom()).getName() + " paid " +
					ts.getAmount() + " to " + players.get(ts.getTo()).getName());
					}
				}
				else if (a instanceof Bankrupt) {
					int result = ((Bankrupt)a).getWho();
					stateArea.setText("");
					for (int i = 0; i < 4; i ++) {
						if ((result & (1 << i)) != 0)
							stateArea.append(players.get(i).getName() + " is bankrupt\n");
					}
				}
				else if (a instanceof Result) {
					if (((Result)a).isDraw() || gm.getTurnPlayer() instanceof LocalPlayer) {
						ClientSender.sendMsg("[Win],"+Network.ClientNetwork.userID+","+Network.ClientNetwork.nickname);
					}
					else {
						ClientSender.sendMsg("[Lose],"+Network.ClientNetwork.userID+","+Network.ClientNetwork.nickname);
					}
					stateArea.setText("Game End");
					repaint();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Frame.GameFrame.self.dispose();
					Frame.WaitingFrame.self.setVisible(true);
					Frame.WaitingFrame.readyButton.setVisible(true);
					Frame.WaitingFrame.loadingButton.setVisible(false);
					
					break;
				}
				repaint();			
			}
		}	
	}
	static final int width = Frame.GameFrame.frameWidth;
	static final int height = Frame.GameFrame.frameHeight;
	
	private BoundedBuffer<Integer> buf;
	public BoundedBuffer<Integer> getBuf(){
		return buf;
	}
	
	private Board boardP; //game board
	private GamePlayerPanel gamePlayerPanel; //player information section
	private ArrayList<PlayerPanel> pp;
	
	private Dice dice1, dice2;
	private Random random;
	private JPanel turnColor;
	private JLabel turnText;

	//private JPanel contentIncluder;
	JPanel playerAssetsPanel;
	CardLayout c1 = new CardLayout();
//	ArrayList<PlayerPanel> playersP = new ArrayList<PlayerPanel>(); //player arrayList here

	private JButton btnRollDice;
	private JButton btnDraw;
	
	private JTextArea stateArea;

	///Buy Frame
	BorderLayout br = new BorderLayout();
	
	public void askBoolean(int landNum) {
		new BooleanDialog(landNum).setVisible(true);
	}
	
	public void enableLand(boolean b) {
		for (Square s: boardP.getAllSquares())
			s.setEnabled(b);
	}
	
	class BooleanDialog extends JDialog{
		private JLabel message = new JLabel("R U Upgrade?");
		private JButton yesB = new JButton("Yes");
		private JButton noB = new JButton("No");
		JPanel temp = new JPanel();
		
		public BooleanDialog(int landNum) {
			City city = (City)board.get(landNum);
			if (city.getOwner() < 0) {
				message.setText("Will you buy this building for " + city.getPrice()
				+"\nExpected toll fee is " + city.getExpectedTollFee());
			}
			else {
				message.setText("Will you upgrade this building for " + city.getPrice()
				+"\nExpected toll fee is " + city.getExpectedTollFee());
			}
			
			setLayout(br);
			add(BorderLayout.SOUTH, temp);
			temp.add(yesB);
			yesB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buf.push(1);
					dispose();
				}	
			});
			temp.add(noB);
			
			noB.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					buf.push(0);;
					dispose();
				}
			});
			setSize(300,200);
			setLocation(500,300);
			add(br.NORTH, message);
		}
	}
	
	private ArrayList<Player> players;
	private ArrayList<Land> board;
	private ArrayList<ChanceCardEvent> deck;
	private GameManager gm;
	
	private void init() {
		buf = new BoundedBuffer<Integer>();
		random = new Random();
		
		deck = new ArrayList<ChanceCardEvent>();
		deck.add(new ChanceCardEvent(ChanceCardEvent.Direction.Bank, ChanceCardEvent.Direction.Self, 300, "장학금 선정\n오 예! 장학금300원을 받으세요."));
		deck.add(new ChanceCardEvent(ChanceCardEvent.Direction.Self, ChanceCardEvent.Direction.Last, 800, "아 미안 카드를 안 들고 왔네\n친구 대신 버스 카드를 찍어 주세요."));
		deck.add(new ChanceCardEvent(ChanceCardEvent.Direction.First, ChanceCardEvent.Direction.Self, 300, "삥뜯기\n돈이 가장 많은 친구에게 300원을 삥뜯습니다."));
		deck.add(new ChanceCardEvent(ChanceCardEvent.Direction.Everyone, ChanceCardEvent.Direction.Self, 3000, "한동만나\n친구들의 도움으로 학식을 먹습니다."));
		deck.add(new ChanceCardEvent(ChanceCardEvent.Direction.Self, ChanceCardEvent.Direction.Everyone, 300, "생활관 보증금 기부\n나도 모르는 사이에 300원을 기부하세요."));
		
		players = new ArrayList<Player>();
		players.add(new LocalPlayer(users.get(0).getNickname(), 15000, this, ClientNetwork.inBuf));
		for (int i = 1; i< users.size(); i++)
			players.add(new NetworkPlayer(users.get(i).getNickname(), 15000, ClientNetwork.outBuf));
		
		//players.add(new ConsolePlayer("P1", 15000));
		//players.add(new ConsolePlayer("P1", 15000));
		//players.add(new ConsolePlayer("P3", 15000));
		
		board = new ArrayList<Land>();
		board.add(new Start("start", 800));
		board.add(new City("HDH", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("NTH", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("NMH", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("ANH", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new Prison("천마지"));
		board.add(new City("Bethel", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("Lothem", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("은혜", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("창조", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new ChanceCard("Chance"));
		board.add(new City("비전", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("오석", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("학생회관", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("갈대상자", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new Travel("한동 한 바퀴"));
		board.add(new City("에벤에셀", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("하용조관", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("반기문", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));
		board.add(new City("채플", Arrays.asList(10000, 20000, 30000, 400000), Arrays.asList(100000, 2000, 3000)));

		pp = new ArrayList<PlayerPanel>();
		for (int i=0;i<players.size();i++)
			pp.add(new PlayerPanel(i+1, GameFrame.COLORS[i], players.get(i)));
		
		gm = new GameManager(players, board, deck);
		
		boardP = new Board(0,0,width *2/3, height *2/3, board);
		gamePlayerPanel = new GamePlayerPanel(players);
		
		//enableLand(false);
		for (Square s: boardP.getAllSquares())
			s.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buf.push(((Square)e.getSource()).getNumber());
				}
			});
	}
	
	public JButton getBtnRollDice() {
		return this.btnRollDice;
	}
	
	public JButton getBtnDraw() {
		return this.btnDraw;
	}
	
	public void addComponents() {
		//Roll dice Action!!
		btnRollDice = new JButton("Roll Dice");
		btnRollDice.setEnabled(false);
		init();		
		
		for (int i = 0; i < pp.size(); i++)
		{
			//playersP.add(pp.get(i));
			PlayerPanel pp = this.pp.get(i);
			this.add(pp);
		}
		
		JPanel rightPanel = new JPanel(); //for buttons and turn explanation gray panel
		rightPanel.setBackground(Color.LIGHT_GRAY);
		rightPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		rightPanel.setBounds(613, 0, 1440*2/3 - 612, 612);
		this.add(rightPanel);
		rightPanel.setLayout(null);
		
		btnDraw = new JButton("Draw a Card");
		btnDraw.setBounds(40, 430, 246, 53);
		rightPanel.add(btnDraw);
		btnDraw.setEnabled(false);
		btnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = random.nextInt(deck.size());
				stateArea.setText(deck.get(choice).getDiscription());
				buf.push(choice);
			}
		});	
		
		//DICE!!
		dice1 = new Dice(244, 406, 40, 40);
		this.add(dice1, 1);

		dice2 = new Dice(333, 406, 40, 40);
		this.add(dice2, 1);

		btnRollDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buf.push(random.nextInt(6) * 10 + random.nextInt(6) + 11);

			}
		});	
		
		//button setting 
		btnRollDice.setBounds(40, 500, 246, 53);
		rightPanel.add(btnRollDice);

		playerAssetsPanel = new JPanel();
		playerAssetsPanel.setBounds(40, 28, 246, 189);
		rightPanel.add(playerAssetsPanel);
		playerAssetsPanel.setLayout(c1);

		turnColor = new JPanel();
		turnColor.setBackground(Color.RED);
		playerAssetsPanel.add(turnColor, "1");
		turnColor.setLayout(null);

		turnText = new JLabel("");
		turnText.setForeground(Color.WHITE);
		turnText.setHorizontalAlignment(SwingConstants.CENTER);
		turnText.setBounds(0, 6, 240, 16);
		turnColor.add(turnText);

		stateArea = new JTextArea();
		stateArea.setBounds(10, 34, 230, 149);
		turnColor.add(stateArea);

		c1.show(playerAssetsPanel, "");
		
		for (int i = 0; i < players.size(); i++)
			gamePlayerPanel.players.get(i).setPlayerInfo();
		
		this.add(boardP);
		this.add(gamePlayerPanel);
	}
	
	public void setComponents() {
		boardP.setBounds(0,0,612,612);
		gamePlayerPanel.setBounds(0, 613, width , height-612);
	}
	
	public void setThis() {
		this.setLayout(null);
		this.setBounds(0,0,width,height);
		this.addComponents();
		this.setComponents();
		
		RunGM runner = new RunGM();
		Thread gmt = new Thread(runner, "GMT");
		gmt.start();
	}	
}
