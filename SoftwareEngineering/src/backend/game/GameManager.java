package backend.game;

import java.util.ArrayList;

import backend.game.action.Action;
import backend.game.action.Bankrupt;
import backend.game.action.DrawCard;
import backend.game.action.Imprison;
import backend.game.action.Move;
import backend.game.action.Release;
import backend.game.action.Result;
import backend.game.action.RollDice;
import backend.game.action.SelectPosition;
import backend.game.action.Transaction;
import backend.game.action.Wish2Buy;
import backend.game.economic.Bank;
import backend.game.economic.ConsolePlayer;
import backend.game.economic.Economic;
import backend.game.economic.Everyone;
import backend.game.economic.First;
import backend.game.economic.Last;
import backend.game.economic.Player;

public class GameManager {
	private ArrayList<Player> players;
	private ArrayList<Economic> economics;
	private ArrayList<Integer> arrange;
	private ArrayList<Integer> inverseArrange;
	private ArrayList<Land> board;
	private ArrayList<ChanceCardEvent> chanceCardList;
	private int turn;
	private Action next;
	
	private int step;
	
	public GameManager(ArrayList<Player> players, ArrayList<Land> board, ArrayList<ChanceCardEvent> chanceCardList) {
		this.players = players;
		this.economics = new ArrayList<Economic>();
		economics.add(new Bank());
		economics.add(new Everyone(players));
		economics.add(new First(players));
		economics.add(new Last(players));
		this.arrange = new ArrayList<Integer>();
		this.inverseArrange= new ArrayList<Integer>();
		for (int i=0; i < players.size();i++) { 
			arrange.add(i);
			inverseArrange.add(i);
		}
		this.board = board;
		this.chanceCardList = chanceCardList;
		this.next = new DecideTurn();
		
		turn = -1;
		step = -1;
	}
	
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	
	public ArrayList<Land> getBoard(){
		return this.board;
	}
	
	public ArrayList<Integer> getArrange(){
		return this.arrange;
	}
	
	public ArrayList<Integer> getInverseArrange(){
		return this.inverseArrange;
	}
	
	public int getTurn() {
		return this.turn;
	}
	
	private Economic getEconomic(int i) {
		if (i>=0)
			return players.get(i);
		else
			return economics.get(-i - 1);
	}
	
	private void nextTurn() {
		do {
			turn = (turn + 1) % players.size();
		} while(players.get(turn).getBalance() <= 0);
	}
	
	private void insertionSort(ArrayList<Integer> n) {
		for (int i=1; i < n.size(); i++) {
			int standard = n.get(i) / 10 + n.get(i)%10;
			Player player = this.players.get(i);
			int org = arrange.get(i);
			
			int aux = i - 1;
			while (aux >= 0 && standard > n.get(aux)/10 + n.get(aux)%10) {
				n.set(aux+1, n.get(aux));
				players.set(aux+1, players.get(aux));
				arrange.set(aux+1, arrange.get(aux));
				aux--;
			}
			n.set(aux+1, standard);
			players.set(aux+1, player);	
			arrange.set(aux+1, org);
		}
	
		for (int i=0; i<arrange.size();i++) {
			inverseArrange.set(arrange.get(i), i);
		}
	}
	
	private ArrayList<Integer> decideTurn() {
		ArrayList<Integer> n = new ArrayList<Integer>();
		n.add(0);
		int first = 0, second = 0;
		for (int i=1; i< players.size();i++) {
			int v = players.get(i).rollDice();
			n.add(v);
			first += v / 10;
			second += v % 10;
		}		
		first = 6 - first % 6;
		second = 6 - second % 6;
		n.set(0, first*10 + second);
		insertionSort(n);
		return n;
	}
	
	public Action getAction() {
		Action current = next;
		if (current instanceof DecideTurn) {
			DecideTurn dt = (DecideTurn)current;
			dt.setDice(decideTurn());
			next = new RollDice();
			turn = 0;
		}
		else if(current instanceof RollDice) {
			RollDice rd = (RollDice)current;
			int first = players.get(turn).rollDice();
			int second = first % 10;
			first = first / 10;
			rd.setDice(first, second);
			
			if (players.get(turn).isImprisoned()) {
				if (first == second) 
					next = new Release();
				else 
					next = new Imprison(false);
			}
			else {
				step = first + second;
				next = new Move(step);
			}
		}
		else if (current instanceof Imprison) {
			players.get(turn).imprison();
			nextTurn();
			next = new RollDice();
		}
		else if (current instanceof Release) {
			players.get(turn).release();
			next = new RollDice();
		}
		else if(current instanceof Move) {
			Move move = (Move)current;
			if ((players.get(turn).getPosition() + step) / board.size() !=
					players.get(turn).getPosition()  / board.size()) {
				int remainder = (players.get(turn).getPosition() + step) % board.size();
				players.get(turn).move(step - remainder);
				step = remainder;
				move.setStep(step);
				next = new Transaction(-1, turn, ((Start)board.get(0)).getSalary());
			}
			else {
				int position = players.get(turn).move(step) % board.size();
				if (board.get(position) instanceof Prison) {
					System.out.println("Prison");
					players.get(turn).imprison();
					next = new Imprison(true);
				}
				else if (board.get(position) instanceof Travel) {
					next = new SelectPosition();
				}
				else if (board.get(position) instanceof City) {
					City city = (City)board.get(position);
					if ((city.getOwner() == -1 || city.getOwner() == turn)) {
						if (city.getPrice() > 0 && players.get(turn).getBalance() > city.getPrice()) {
							next = new Wish2Buy();
						}
						else {
							nextTurn();
							next = new RollDice();
						}
					}
					else {
						next = new Transaction(turn, city.getOwner(), city.getTollFee());
					}
				}
				else if (board.get(position) instanceof ChanceCard) {
					next = new DrawCard();
				}
				step = 0;
			}
		}
		else if (current instanceof Wish2Buy) {
			Wish2Buy wb = (Wish2Buy)current;
			wb.setDecision(players.get(turn).wish2trade(players.get(turn).getPosition() % board.size()));
			City city = (City)board.get(players.get(turn).getPosition() % board.size());
			if (wb.getDecision()) {
				next = new Transaction(turn, -1, city.getPrice());
				
				if (city.getOwner() < 0)
					city.ownedBy(turn);
				else 
					city.upgrade();
			}
			else {
				next = new RollDice();
				nextTurn();
			}
		}
		else if(current instanceof Transaction) {
			Transaction ts = (Transaction)current;
			Economic from = getEconomic(ts.getFrom());
			Economic to = getEconomic(ts.getTo());
			boolean result = from.pay(to, ts.getAmount());
			if (step > 0) //salary
				next = new Move(step);
			else if(result) {
				nextTurn();
				next = new RollDice();
			}
			else 
				next = new Bankrupt(ts.getFrom());
		}
		else if(current instanceof SelectPosition) {
			int destination = players.get(turn).where2go();
			step = destination - players.get(turn).getPosition();
			if (step < 0)
				step += board.size();
			next = new Move(step);
		}
		else if(current instanceof DrawCard) {
			int selection = players.get(turn).drawCard(chanceCardList.size());
			ChanceCardEvent cce = chanceCardList.get(selection);
			int from, to, amount;
			if (cce.getFrom() == ChanceCardEvent.Direction.Self)
				from = turn;
			else 
				from = cce.getFrom().getValue();
			
			if (cce.getTo() == ChanceCardEvent.Direction.Self)
				to = turn;
			else 
				to = cce.getTo().getValue();
			
			amount = cce.getAmount();
			next = new Transaction(from, to, amount);
		}
		else if(current instanceof Bankrupt) {
			int countAlive = 0;
			for (Player p : players) 
				if (!p.isBankrupt()) countAlive++;
			if (countAlive == 0)
				next = new Result(true);
			else if(countAlive == 1)
				next = new Result(false);
			else {
				nextTurn();
				next = new RollDice();
			}			
		}
		return current;
	}
	
	
	private static void printBoard(int turn, ArrayList<Player> players, ArrayList<Land> board, ArrayList<Integer> arrange, ArrayList<Integer> inverse) {
		if (turn < 0) return;
		for (int i=0; i < board.size(); i++) {
			if (i < players.size())
				System.out.printf("p%d, balance=%6d ", i, players.get(inverse.get(i)).getBalance());
			else
				System.out.printf("                   ");
			if (board.get(i) instanceof City) {
				City c = (City)board.get(i);
				System.out.printf("%s %2d %5d %5d ", c.getName(), c.getOwner()==-1?-1:arrange.get(c.getOwner()), c.getPrice(), c.getTollFee());
			}
			else {
				System.out.printf("%s                ", board.get(i).getName());
			}
			for (int j=0;j<players.size();j++) {
				if (players.get(inverse.get(j)).getPosition()%board.size() == i)
					System.out.printf("p%d ", j);
				else
					System.out.printf("   ");
			}
			System.out.println();
		}
		System.out.printf("turn of %d\n", arrange.get(turn));
	}
	
	public static void test() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (int i=0;i<4;i++)
			players.add(new ConsolePlayer(15000));
		ArrayList<Land> board = new ArrayList<Land>();
		board.add(new Start("start ", 200));
		for (int i=0;i<8;i++) {
			ArrayList<Integer> price = new ArrayList<Integer>();
			ArrayList<Integer> tollFee = new ArrayList<Integer>();
			for (int j=1;j<=4;j++) {
				price.add(j*10000);
				tollFee.add(j*3000);
			}
			board.add(new City("city"+i, price, tollFee));
		}
		board.add(new Prison("Prison"));
		board.add(new Travel("Travel"));
		board.add(new ChanceCard("C-Card"));
		ArrayList<ChanceCardEvent> deck = new ArrayList<ChanceCardEvent>();
		deck.add(new ChanceCardEvent(ChanceCardEvent.Direction.Bank, ChanceCardEvent.Direction.Self, 300, ""));
		deck.add(new ChanceCardEvent(ChanceCardEvent.Direction.Self, ChanceCardEvent.Direction.Last, 300, ""));
		deck.add(new ChanceCardEvent(ChanceCardEvent.Direction.First, ChanceCardEvent.Direction.Self, 300, ""));
		deck.add(new ChanceCardEvent(ChanceCardEvent.Direction.Everyone, ChanceCardEvent.Direction.Self, 200000, ""));
		deck.add(new ChanceCardEvent(ChanceCardEvent.Direction.Self, ChanceCardEvent.Direction.Everyone, 300, ""));
		
		GameManager gm = new GameManager(players, board, deck);
		while(true) {
			printBoard(gm.getTurn(), gm.getPlayers(), gm.getBoard(), gm.getArrange(), gm.getInverseArrange());
			gm.getAction();
		}
	}
}
