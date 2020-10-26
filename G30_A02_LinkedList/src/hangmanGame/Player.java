package hangmanGame;

public class Player implements java.io.Serializable {
	private String name;
	private int numberGamesPlayed;
	private int numberGamesWon;
	
	public Player() {
		super();
	}
	public Player(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
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
