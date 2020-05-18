package backend.game;

import java.util.ArrayList;

public class GameManager {
	private enum Phase {
		DECIDE_TURN, ROLL_DICE, MOVE, IMPRISON, RELEASE, TRANSACTION, SELECT_POSITION, WISH2BUY
	}
	
	private ArrayList<Player> players;
	private ArrayList<Integer> arrange;
	private ArrayList<Land> board;
	private ArrayList<Integer> landOwnership;
	private ArrayList<ChanceCardEvent> chanceCardList;
	private int turn;
	private Phase phase;
	
	private int step;
	private int from;
	private int to;
	private int amount;
	private int realEstate;
	
	public GameManager(ArrayList<Player> players, ArrayList<Land> board, ArrayList<ChanceCardEvent> chanceCardList) {
		this.players = players;
		this.arrange = new ArrayList<Integer>();
		for (int i=0; i < players.size();i++) 
			arrange.add(i);
		this.board = board;
		this.landOwnership = new ArrayList<Integer>();
		for (int i=0; i < board.size(); i++)
			landOwnership.add(-1);
		this.chanceCardList = chanceCardList;
		this.phase = Phase.DECIDE_TURN;	
		
		step = -1;
		from = -2;
		to = -2;
		amount= -1;
		realEstate = -1;
		
	}
	
	private void nextTurn() {
		turn++;
		if (turn == players.size())
			turn = 0;
	}
	
	private void insertionSort(ArrayList<Integer> n) {
		for (int i=1; i < n.size(); i++) {
			int standard = n.get(i) / 10 + n.get(i)%10;
			Player player = this.players.get(i);
			int org = arrange.get(i);
			
			int aux = i - 1;
			while (aux >= 0 && standard < n.get(aux)/10 + n.get(aux)%10) {
				n.set(aux+1, n.get(aux));
				players.set(aux+1, players.get(aux));
				arrange.set(aux+1, arrange.get(aux));
				aux--;
			}
			n.set(aux+1, standard);
			players.set(aux+1, player);	
			arrange.set(aux+1, org);
		}
	}
	
	private int decideTurn() {
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
		return first*10 + second;
	}
	
	public Action getAction() {
		ArrayList<Action> queue = new ArrayList<Action>();
		if (phase == Phase.DECIDE_TURN) {
			int n = decideTurn();
			phase = Phase.ROLL_DICE;
			turn = 0;
			return null; // RollDice(n/10, n%10)
		}
		else if(phase == Phase.ROLL_DICE) {
			int first = players.get(turn).rollDice();
			int second = first % 10;
			first = first / 10;
			
			if (players.get(turn).isImprisoned()) {
				if (first == second) 
					phase = Phase.RELEASE;
				else 
					phase = Phase.IMPRISON;	
			}
			else {
				step = first + second;
				phase = Phase.MOVE;
			}
			return null; // RollDice(first, second)
		}
		else if (phase == Phase.IMPRISON) {
			players.get(turn).imprison();
			nextTurn();
			phase = Phase.ROLL_DICE;
			return null; //FailTOBeReleased(); //Imprison();
		}
		else if (phase == Phase.RELEASE) {
			players.get(turn).release();
			queue.add(null); //Release()
			phase = Phase.ROLL_DICE;
			return null; //Release();
		}
		else if(phase == Phase.MOVE) {
			if ((players.get(turn).getPosition() + step) / board.size() !=
					players.get(turn).getPosition()  / board.size()) {
				int remainder = (players.get(turn).getPosition() + step) % board.size();
				players.get(turn).move(step - remainder);
				step = remainder;
				from = -1;
				to = turn;
				amount = 300000;
				phase = Phase.TRANSACTION;
				return null; //Move(step - remainder)
			}
			
			int position = players.get(turn).move(step) % board.size();
			if (board.get(position) instanceof Prison) {
				players.get(turn).imprison();
				nextTurn();
				phase = Phase.IMPRISON;
			}
			else if (board.get(position) instanceof Travel) 
				phase = Phase.SELECT_POSITION;
			else if (board.get(position) instanceof City) {
				City city = (City)board.get(position);
				if ((city.getOwner() == -1 || city.getOwner() == turn)) {
					if (players.get(turn).getBalance() >= city.getPrice()) {
						phase = Phase.WISH2BUY;
						realEstate = position;
					}
					else {
						nextTurn();
						phase = Phase.ROLL_DICE;
					}
				}
				else {
					from = turn;
					to = city.getOwner();
					amount = city.getTollFee();
					phase = Phase.TRANSACTION;
				}
			}
			else if (board.get(position) instanceof ChanceCard) {
				//pass
			}
			step = 0;
			return null; //Move(turn, step)
		}
		else if (phase == Phase.WISH2BUY) {
			boolean decision = players.get(turn).wish2trade(realEstate);
			City city = (City)board.get(realEstate);
			if (decision && city.getOwner() < 0)
				city.ownedBy(turn);
			else if(decision)
				city.upgrade();
			phase = Phase.ROLL_DICE;
			nextTurn();
			return null; //Success to buy;
		}
		else if(phase == Phase.TRANSACTION) {
			if (from >= 0) {
				if (players.get(from).pay(amount)) 
					if (to >= 0)
						players.get(to).paid(amount);
				else {
					//pass
				}
			}
			else
				if (to >= 0)
					players.get(to).paid(amount);
			if (step >= 0) {
				phase = Phase.MOVE;
			}
			else {
				//pass
			}
			return null; //Transaction(from, to, amount)
				
		}
		else if(phase == Phase.SELECT_POSITION) {
			int destination = players.get(turn).where2go();
			step = destination - players.get(turn).getPosition();
			if (step < 0)
				step += board.size();
			phase = Phase.MOVE;
			return null; //Travel();
		}
		else {
			return null; //error();
		}
	}
}
