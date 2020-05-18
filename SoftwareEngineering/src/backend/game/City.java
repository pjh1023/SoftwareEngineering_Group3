package backend.game;

import java.util.ArrayList;

public class City extends Land {
	private int level;
	private int owner;
	private ArrayList<Integer> price;
	private ArrayList<Integer> tollFee;
	
	public City(String name, ArrayList<Integer> price, ArrayList<Integer> tollFee) {
		super(name);
		level = -1;
		owner = -1;
		this.price = price;
		this.tollFee = tollFee;
	}
	
	public int getOwner() {
		return owner;
	}

	public int getPrice() {
		return price.get(level + 1);
	}
	
	public int getTollFee() {
		return tollFee.get(level);
	}
	
	public boolean ownedBy(int owner) {
		this.owner = owner;
		level++;
		return true;
	}
	
	public boolean upgrade() {
		level++;
		return true;
	}
}
