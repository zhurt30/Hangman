package hangmanGame;

import java.util.Random;

import java.util.StringTokenizer;

import linked_data_structures.SinglyLinkedList;

public class Hangman implements java.io.Serializable {

	private String word;
	// private StateOfGame state;
	private SinglyLinkedList<Character> lettersToBeGuessed;
	private SinglyLinkedList<Character> lettersOfMistake;
	private SinglyLinkedList<Character> guessedLetters;
	// private transient List<StateChangeListener> listeners;
	// private int numOfGuessed;
	private int numberOfMistakes;

	public Hangman(String word) {
		this.guessedLetters = new SinglyLinkedList<>();
		this.lettersOfMistake = new SinglyLinkedList<>();
		this.lettersToBeGuessed = new SinglyLinkedList<Character>();
		this.word = word;

	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public SinglyLinkedList<Character> getLettersOfMistake() {
		return lettersOfMistake;
	}

	public SinglyLinkedList<Character> getGuessedLetters() {
		return guessedLetters;
	}

	public SinglyLinkedList<Character> getLettersToBeGuessed() {
		return lettersToBeGuessed;
	}

	public void setLettersToBeGuessed() {
		String word = RemoveSpace(this.getWord());
		;
		if (word.length() != 0) {
			for (int i = 0; i < word.length(); i++) {
				if (Character.isLetter(word.charAt(i)) || word.charAt(i) == '-' || word.charAt(i) == ' ') {

					this.lettersToBeGuessed.add(word.charAt(i));
					System.out.print(word.charAt(i));
				}

			} // for
		} else
			throw new IllegalArgumentException("no word anymore");

	}// setLettersToBeGuessed()

	public boolean guessLetter(char letter) {
		// numOfGuessed ++;
		letter = Character.toLowerCase(letter);
		boolean success = false;
		for (int i = 0; i < lettersToBeGuessed.getLength(); i++)
			if (letter == Character.toLowerCase(lettersToBeGuessed.getElementAt(i).charValue()))
				success = true;

		if (success) {

			this.guessedLetters.add(letter);

//			if (getLettersOfNotGuessed().getLength() == 0) {
//
//				setState(StateOfGame.WIN);
//			}
		} else {

			this.lettersOfMistake.add(letter);
			numberOfMistakes++;

//			if (this.lettersOfMistake.getLength() > 5) {
//				setState(StateOfGame.LOSS);
//			}
		}

		return success;

	}// guessLetter(char)

	private SinglyLinkedList<Character> getRemainingLetters() {
		SinglyLinkedList<Character> remainingLetters = new SinglyLinkedList<Character>();

		for (int i = 0; i < lettersToBeGuessed.getLength(); i++) {

			char letter = lettersToBeGuessed.getElementAt(i);
			char c = Character.toLowerCase(letter);
			if (c != '-' && c != ' ') {
				if (!this.getGuessedLetters().contains(c) && !remainingLetters.contains(c))
					remainingLetters.add(letter);
			}
		}

		return remainingLetters;
	}// getRemainingLetters()

	public char hint() {
		SinglyLinkedList<Character> hintLetters = getRemainingLetters();
		char hint = ' ';
		if (hintLetters.getLength() != 0) {
			Random random = new Random();
			int randomNum = random.nextInt(getRemainingLetters().getLength());

			hint = hintLetters.getElementAt(randomNum);

		}
		System.out.println("\n" + "hint:" + hint);
		return hint;

	}// hint()

	public int statusGame() {
		int status = 0; // pending

		if (getRemainingLetters().getLength() == 0) {
			status = 1; // win
		}

		if (numberOfMistakes > 5) {
			status = -1; // lose
		}
		return status;
	}// statusGame()

	public SinglyLinkedList<Character> getDisplayString() {
		SinglyLinkedList<Character> displayString = new SinglyLinkedList<Character>();
		for (int i = 0; i < lettersToBeGuessed.getLength(); ++i) {
			char letter = lettersToBeGuessed.getElementAt(i);
			char c = Character.toLowerCase(letter);
			if (guessedLetters.contains(c))
				displayString.add(letter);
			else if (Character.isLetter(c)) {
				displayString.add('_');
				displayString.add(' ');
			}

			else
				displayString.add(letter);
		}

		return displayString;

	}// getDisplayString()

	// Remove excess Spaces
	private String RemoveSpace(String str) {

		StringTokenizer pas = new StringTokenizer(str, " ");
		str = "";
		while (pas.hasMoreTokens()) {
			String s = pas.nextToken();
			str = str + s + " ";
		}
		return str.trim();
	}// delSpace(String)

	public static void main(String[] args) {

		String wString = "ni  //????;{:{{>}|   Hao!@##$%%^&*(()_++~!1123-mang;3454365467'''55";
		// String wString = "ahh";
		Hangman hangman = new Hangman(wString);

		System.out.println(hangman.getWord());

		hangman.setLettersToBeGuessed();

		hangman.guessLetter('n');
		for (int i = 0; i < hangman.getGuessedLetters().getLength(); i++) {

			System.out.print("#" + hangman.getGuessedLetters().getElementAt(i));
		}

		for (int i = 0; i < hangman.getRemainingLetters().getLength(); i++) {

			System.out.print("@" + hangman.getRemainingLetters().getElementAt(i));
		}
		hangman.hint();

		hangman.getDisplayString();
		for (int i = 0; i < hangman.getDisplayString().getLength(); ++i) {

			System.out.print(hangman.getDisplayString().getElementAt(i));
		}
		
		hangman.guessLetter('z');
		for (int i = 0; i < hangman.getGuessedLetters().getLength(); i++) {

			System.out.print("#" + hangman.getGuessedLetters().getElementAt(i));
		}

		for (int i = 0; i < hangman.getRemainingLetters().getLength(); i++) {

			System.out.print("@" + hangman.getRemainingLetters().getElementAt(i));
		}
		hangman.hint();

		hangman.getDisplayString();
		for (int i = 0; i < hangman.getDisplayString().getLength(); ++i) {

			System.out.print(hangman.getDisplayString().getElementAt(i));
		}
		
		for (int i = 0; i < hangman.getLettersOfMistake().getLength(); ++i) {

			System.out.print("!"+hangman.getLettersOfMistake().getElementAt(i));
		}
		
		System.out.print("\nwrong ="+hangman.numberOfMistakes);
		
		

	}// main()

}// class Hangman
