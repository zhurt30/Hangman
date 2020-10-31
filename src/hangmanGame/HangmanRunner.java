package hangmanGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import linked_data_structures.SinglyLinkedList;



public class HangmanRunner {
	private Scoreboard scoreboard;
	private Player currentPlayer;
	private Hangman currentGame;
	private final Dictionary DEFAULT_DICTIONARY;
	private Dictionary dictionary;

	public HangmanRunner() throws IOException, ClassNotFoundException {
		DEFAULT_DICTIONARY = new Dictionary();
		

		scoreboard = loadScoreboard();

		if (scoreboard == null)
			scoreboard = new Scoreboard();
	}

	

	public Hangman playGame(String playerName) throws FileNotFoundException, ClassNotFoundException, IOException {

		if (currentPlayer == null || !currentPlayer.getName().equalsIgnoreCase(playerName)) {
			currentPlayer = scoreboard.addPlayer(playerName);
			
			
			dictionary = loadDictionary();
		

			currentGame = loadGame();

		}

	
		if (currentGame == null || currentGame.getStatus() != 0) {
			if (dictionary.hasNextWord()) {
				String word = dictionary.getNextWord();
				currentGame = new Hangman(word,currentPlayer);
			}
		}

	
		

		return currentGame;
	}

	public boolean hasNextGame() {
		if (dictionary == null)
			throw new IllegalStateException();

		return dictionary.hasNextWord();
	}
	

	public Hangman nextGame() {
		if (currentGame == null)
			throw new IllegalStateException();

		System.out.println(currentGame.getWord());
		
			String word = dictionary.getNextWord() ;
			currentGame = new Hangman(word,currentPlayer);
		
		return currentGame;
	}

	

	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}

	public void save() throws IOException {
		saveScoreboard();

		saveDictionary();

		saveGame();
	}

	private void saveGame() throws IOException {
		String filename = "games- " + currentPlayer.getName();
		serialize(currentGame, filename);
	}

	private Hangman loadGame() throws FileNotFoundException, ClassNotFoundException, IOException {

		String filename = "games- " + currentPlayer.getName();
		return deserialize(filename);
	}

	private void saveScoreboard() throws IOException {
		String filename = "scoreboard";
		serialize(this.scoreboard, filename);
	}

	private Scoreboard loadScoreboard() throws FileNotFoundException, ClassNotFoundException, IOException {
		String filename = "scoreboard";
		return deserialize(filename);
	}
	
	
	

	private void saveDictionary() throws IOException {
		String filename = "dictionary-" + currentPlayer.getName();
		serialize(this.dictionary, filename);
	}

	private Dictionary loadDictionary() throws FileNotFoundException, ClassNotFoundException, IOException {

		String filename = "dictionary-" + currentPlayer.getName();
		dictionary = deserialize(filename);
		if (dictionary == null)
			serialize(DEFAULT_DICTIONARY, filename);

		return deserialize(filename);
	}



	private static void serialize(Object object, String filename) throws IOException {
		if (object == null)
			return;
		try (FileOutputStream fileOut = new FileOutputStream(filename)) {
			try (ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
				out.writeObject(object);
			}
		}
		System.out.println("Serialized data is saved in " + filename);
	}

	@SuppressWarnings("unchecked")
	private static <T> T deserialize(String filename)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File(filename);
		if (!file.exists())
			return null;

		try (FileInputStream fileIn = new FileInputStream(filename)) {
			try (ObjectInputStream in = new ObjectInputStream(fileIn)) {
				return (T) in.readObject();
			} catch (InvalidClassException ex) {
				file.delete();
				return null;
			}
		}
	}
	
	
}
