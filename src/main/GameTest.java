package main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class GameTest {
	Game game;

	public GameTest() {
		game = new Game(new ArrayList<Player>(), 0, 0, 0, new OnStatusChange() {

			@Override
			public void sendMessage(String message, ArrayList<Player> players) {
				// TODO Auto-generated method stub

			}

			@Override
			public void closeConnection(ArrayList<Player> players) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Test
	public void test1() {
		String guess = "hülle";
		assertEquals("huelle", game.changeChars(guess));
	}
	
	@Test
	public void test2() {
		String guess = "hölle";
		assertEquals("hoelle", game.changeChars(guess));
	}
	
	@Test
	public void test3() {
		String guess = "häh";
		assertEquals("haeh", game.changeChars(guess));
	}
	
	@Test
	public void test4() {
		String guess = "haß";
		assertEquals("hass", game.changeChars(guess));
	}
	
	@Test
	public void test5() {
		String guess = "ümmm";
		assertEquals("uemmm", game.changeChars(guess));
	}
	
	@Test
	public void test6() {
		String guess = "mü";
		assertEquals("mue", game.changeChars(guess));
	}
}
