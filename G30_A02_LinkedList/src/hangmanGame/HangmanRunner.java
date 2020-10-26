package hangmanGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import hangman.HangmanGame;
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

	public Hangman playGame() throws FileNotFoundException, ClassNotFoundException, IOException {
		if (currentPlayer == null)
			throw new NullPointerException("No player.");

		return playGame(currentPlayer.getName());

	}

	public Hangman playGame(String playerName) throws FileNotFoundException, ClassNotFoundException, IOException {

		if (currentPlayer == null || !currentPlayer.getName().equalsIgnoreCase(playerName)) {
			currentPlayer = scoreboard.addPlayer(playerName);

			dictionary = loadDictionary();

			currentGame = loadGame();

		}

		// no previous game or game is over, will to create new game
		if (currentGame == null || currentGame.statusGame() != 0) {
			if (dictionary.hasNextWord()) {
				String word = dictionary.getNextWord();
				currentGame = new Hangman(word);
			}
		}

	
		if (currentGame != null) {

				if (currentGame.statusGame() == 1)
					scoreboard.gamePlayed(currentPlayer.getName(), true);
				else
					scoreboard.gamePlayed(currentPlayer.getName(), false);

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
		
		int index = dictionary.indexOf(currentGame.getWord());
		if (++index < dictionary.getLength()) {
			SinglyLinkedList<Character> word = dictionary.getElementAt(index);
			currentGame = new Hangman(word);
		} else
			currentGame = null;
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
		String filename = "games for " + currentPlayer.getName();
		serialize(currentGame, filename);
	}

	private Hangman loadGame() throws FileNotFoundException, ClassNotFoundException, IOException {

		String filename = "games for " + currentPlayer.getName();
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
		String filename = "dictionary for " + currentPlayer.getName();
		serialize(this.dictionary, filename);
	}

	private Dictionary loadDictionary() throws FileNotFoundException, ClassNotFoundException, IOException {


		String filename = "dictionary for " + currentPlayer.getName();
		dictionary = deserialize(filename);
		if (dictionary == null)
			serialize(DEFAULT_DICTIONARY, filename);

		return deserialize(filename);
	}



	public void serialize(Object obj, String filename) {
		try (FileOutputStream fileOut = new FileOutputStream(filename)) {
			try (ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
				out.writeObject(obj);
				out.close();
				System.out.println("Serialized data is saved in " + filename);
			} catch (IOException i) {
				i.printStackTrace();
			}
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public <T> T deserialize(String filename) throws IOException, FileNotFoundException {
		T temp = null;
		File file = new File(filename);
		if (!file.exists())
			return null;
		try (FileInputStream fileIn = new FileInputStream(filename)) {
			try (ObjectInputStream in = new ObjectInputStream(fileIn)) {
				temp = (T) in.readObject();
				in.close();
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("class not found");
				c.printStackTrace();
			}
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		HangmanRunner hRunner = new HangmanRunner();
		hRunner.playGame("zrt");
		
	}
}
