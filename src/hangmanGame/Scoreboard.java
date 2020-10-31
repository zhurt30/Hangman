package hangmanGame;

import java.io.Serializable;

import linked_data_structures.DoublyLinkedList;

public class Scoreboard  implements Serializable {
	
	
	private DoublyLinkedList<Player> playerList;
	
	
	public DoublyLinkedList<Player> getPlayerList() {
		return playerList;
	}

	public int getNumPlayers() {
		return playerList.getLength();
	}


	public Scoreboard() {
		super();
		this.playerList =new DoublyLinkedList<Player>();
	}
	
	public Player addPlayer(String name) {
		
		
		Player player = null;
		
		for(int i =0 ; i < playerList.getLength() ; ++i) {
			if(playerList.getElementAt(i).getName().equals(name)) {
				player = playerList.getElementAt(i);
				break;
			} 	
		}
		if(player==null) {
			 player = new Player(name);
			int index =0;
			for(int i =0 ; i < playerList.getLength() ; ++i) {
				if(name.compareToIgnoreCase(playerList.getElementAt(i).getName())<0)
					index= i;
				} 
			playerList.add(player, index);
			
		}	
			
		return player;
	}
	
	public Player getNextPlayer(int index) {
		
		Player player = null;
		if(index <getNumPlayers())
				player = playerList.getElementAt(index);
		return player;
	}
	
	public void gamePlayed(String name, boolean winOrLose) {
		Player player = addPlayer(name);
		player.setNumberGamesPlayed(player.getNumberGamesPlayed() + 1);
		if (winOrLose)
			player.setNumberGamesWon(player.getNumberGamesWon() + 1);
	}
	



}
