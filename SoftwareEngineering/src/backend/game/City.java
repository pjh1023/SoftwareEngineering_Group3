package backend.game;

import java.util.ArrayList;

public class City extends Land {
	private int level;
	private int owner;
	private ArrayList<Integer> price;
	private ArrayList<Integer> tollFee;
	
	public City(String name, ArrayList<Integer> price, ArrayList<Integer> tollFee) {
		super(name);
		level = 0;
		owner = -1;
		this.price = price;
		this.tollFee = tollFee;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getOwner() {
		return owner;
	}

	public int getPrice() {
		if (level + 1 == price.size())
			return 0;
		return price.get(owner==-1?0:level + 1);
	}
	
	public int getTollFee() {
		if (owner == -1)
			return 0;
		return tollFee.get(level);
	}
	
	public boolean ownedBy(int owner) {
		this.owner = owner;
		return true;
	}
	
	public boolean upgrade() {
		level++;
		return true;
	}
}
