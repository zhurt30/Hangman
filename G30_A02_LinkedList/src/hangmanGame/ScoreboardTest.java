package hangmanGame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;


class ScoreboardTest {

	@Test
	void test1() {
		Scoreboard boardScoreboard = new Scoreboard();
		assertEquals(0, boardScoreboard.getNumPlayers());
		
		Player player = boardScoreboard.addPlayer("zrt");
		
		assertEquals("zrt", player.getName(),"Test Case1 : addPlayer() / constructor()");
		assertEquals(0, player.getNumberGamesPlayed());
		assertEquals(0, player.getNumberGamesWon());
		assertEquals(1,  boardScoreboard.getNumPlayers());
		
		Player player2 = boardScoreboard.addPlayer("zhurt");
		assertEquals(2,  boardScoreboard.getNumPlayers(),"Test Case1 : addPlayer() ");
		
		Player player3 = boardScoreboard.addPlayer("zhurt");
		assertEquals(2,  boardScoreboard.getNumPlayers(),"Test Case1 : addPlayer()-same name ");
		
		assertEquals(boardScoreboard.getPlayerList().getElementAt(0).getName(), boardScoreboard.getNextPlayer(0).getName());
		
	}
	
	@Test
	void test2() {
		Scoreboard boardScoreboard = new Scoreboard();
		assertNull(boardScoreboard.getNextPlayer(0),"Test Case2 : getNextPlayer()");
		
		boardScoreboard.addPlayer("bac");
		boardScoreboard.addPlayer("abc");
		boardScoreboard.addPlayer("acb");

		assertEquals("abc", boardScoreboard.getNextPlayer(0).getName(),"Test Case2 : getNextPlayer()-Alphabetically");
		assertEquals("acb", boardScoreboard.getNextPlayer(1).getName(),"Test Case2 : getNextPlayer()-Alphabetically");
		assertEquals("bac", boardScoreboard.getNextPlayer(2).getName(),"Test Case2 : getNextPlayer()-Alphabetically");
		
	}
	
	@Test
	void test3() {
		Scoreboard board = new Scoreboard();
		
		Player player = board.addPlayer("zrt");
		assertEquals("zrt", player.getName(),"Test Case3 : getName()");
		
		board.gamePlayed("zrt", true);
		assertEquals(1, player.getNumberGamesWon(),"Test Case3 : getNumberGamesWon()/gamePlayed()");
		assertEquals(1, player.getNumberGamesPlayed(),"Test Case3 : getNumberGamesPlayed(/gamePlayed())");
		
		board.gamePlayed("zrt", false);
		assertEquals(1, player.getNumberGamesWon(),"Test Case3 : gamePlayed()");
		assertEquals(2, player.getNumberGamesPlayed(),"Test Case3 : gamePlayed()");
		
	}


}
