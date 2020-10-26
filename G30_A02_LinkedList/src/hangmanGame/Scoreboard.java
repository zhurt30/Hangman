package hangmanGame;

import linked_data_structures.DoublyLinkedList;

public class Scoreboard extends DoublyLinkedList<Player> {
	
	private int numPlayers;
	
	
	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public Scoreboard() {
		super();
	}
	
	public Player addPlayer(String name) {
		
		Player player = new Player(name);
		boolean exist = false;
		for(int i =0 ; i < this.getLength() ; ++i) {
			if(this.getElementAt(i).getName().equals(name)) {
				player = this.getElementAt(i);
				exist = true;
				break;
			} 	
		}
		if(!exist) {
			this.add(player);
			numPlayers++;
		}	
			
		return player;
	}
	
	public Player getNextPlayer(int index) {
		
		Player player = new Player();
		if(index <numPlayers-1)
				player = this.getElementAt(index);
		return player;
	}
	
	public void gamePlayed(String name, boolean winOrLose) {
		Player player = new Player();
		int gameTimes = player.getNumberGamesPlayed();
		int wonTimes = player.getNumberGamesWon();
		player.setNumberGamesPlayed(gameTimes+1);
		if(winOrLose)
			player.setNumberGamesWon(wonTimes+1);	
	}


}
