package backend.game;

import java.util.List;

public class City extends Land {
	private int level;
	private int owner;
	private List<Integer> price;
	private List<Integer> tollFee;
	
	public City(String name, List<Integer> list, List<Integer> list2) {
		super(name);
		level = 0;
		owner = -1;
		this.price = list;
		this.tollFee = list2;
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
