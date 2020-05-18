package test;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import backend.game.ConsolePlayer;
import backend.game.GameManager;
import backend.game.Land;
import backend.game.Player;
import backend.game.Start;

class GameManagerTest {

	@Test
	void testDecideTurn() {
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<Land> board = new ArrayList<Land>();
		for (int i=0;i<4; i++)
			players.add(new ConsolePlayer(100000));
		board.add(new Start("start"));
		
		GameManager gm = new GameManager(players, board, null);
	}

}
