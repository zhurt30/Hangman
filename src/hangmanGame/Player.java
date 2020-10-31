package hangmanGame;

import java.io.Serializable;

public class Player implements Serializable {
	private String name;
	private int numberGamesPlayed;
	private int numberGamesWon;
	
	public Player() {
		
			this(null);
		
	}
	public Player(String name) {
		super();
		setName(name);
		this.numberGamesWon = getNumberGamesWon();
		this.numberGamesPlayed=getNumberGamesPlayed();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name)   {
		
		if(name == null || name.length() ==0)
			throw new IllegalArgumentException("Player name is null.");
		this.name = name;
	}
	public int getNumberGamesPlayed() {
		return numberGamesPlayed;
	}
	public void setNumberGamesPlayed(int numberGamesPlayed) {
		this.numberGamesPlayed = numberGamesPlayed;
	}
	public int getNumberGamesWon() {
		return numberGamesWon;
	}
	public void setNumberGamesWon(int numberGamesWon) {
		this.numberGamesWon = numberGamesWon;
	}
	
	
}
