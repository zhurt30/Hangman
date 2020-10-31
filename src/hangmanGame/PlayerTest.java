package hangmanGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	void test1() {
		Player player = new Player("zrt");
		assertEquals("zrt", player.getName(),"Test Case1 : constructor()/ getName() / setName");
		assertEquals(0, player.getNumberGamesPlayed());
		assertEquals(0, player.getNumberGamesWon());
		
		new Player("zhurt");

		try {
			new Player("");
			fail(" IllegalArgumentException was not thrown.");
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	
	}


	@Test
	void test2() {
		Player player = new Player("zrt");

		player.setNumberGamesPlayed(player.getNumberGamesPlayed() + 1);
		assertEquals(1, player.getNumberGamesPlayed(),"Test Case2 : getNumberGamesPlayed()/setNumberGamesPlayed()");

		player.setNumberGamesWon(player.getNumberGamesWon() + 1);
		assertEquals(1, player.getNumberGamesWon(),"Test Case2 :getNumberGamesWon()/ setNumberGamesWon()");
	}

}
