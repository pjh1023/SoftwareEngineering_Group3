package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import backend.game.economic.ConsolePlayer;
import backend.game.economic.Player;

public class PlayerTest {

	@Test
	public void paidTest() {
		Player p = new ConsolePlayer("hello", 300);
		
		p.paid(400);
		assertEquals(p.getBalance(), 700);
		
		p.paid(2000);
		assertEquals(p.getBalance(), 2700);
		
		p.paid(-300);
		assertEquals(p.getBalance(), 2400);
	}
	
	@Test
	public void payTest() {
		Player p1 = new ConsolePlayer("hi", 2000);
		Player p2 = new ConsolePlayer("hello", 100);
		
		p1.pay(p2, 400);
		assertEquals(p1.getBalance(), 1600);
		assertEquals(p2.getBalance(), 500);
		
		p2.pay(p1, 600);
		assertEquals(p1.getBalance(), 2200);
		assertTrue(p2.isBankrupt());
	}

	@Test
	public void moveTest() {
		Player p = new ConsolePlayer("Hunnam", 200);
		p.move(5213231);
		
		assertEquals(p.getPosition(), 5213231);
		
		p.move(24);
		assertEquals(p.getPosition(), 5213255);
	}
	
	@Test
	public void imprisonTest() {
		Player p1 = new ConsolePlayer("Hunnam", 400);
		Player p2 = new ConsolePlayer("Lee Chanhyeok", 500);
		
		assertTrue(!p1.isImprisoned());
		
		p1.imprison();
		assertTrue(p1.isImprisoned());
		
		p2.imprison();
		p2.release();
		assertTrue(!p2.isImprisoned());
		
		p1.imprison();
		p1.imprison();
		p1.imprison();
		assertTrue(!p1.isImprisoned());
	}
}
