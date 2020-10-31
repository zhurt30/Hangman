package hangmanGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import linked_data_structures.SinglyLinkedList;

class HangmanTest {
	

	@Test

	void test1() {

		String wString = "Hell2#o   -zrt!!";

		Hangman hangman = new Hangman();
		hangman.setWord(wString);

		assertEquals("Hell2#o   -zrt!!", hangman.getWord(), "Test Case 1: getWord ()");

	}

	@Test

	void test2() {

		String wString = "Hell2#o   -zrt!!";
		Player player = new Player("zrt");
		Hangman hangman = new Hangman(wString, player);
		SinglyLinkedList<Character> word = hangman.getLettersToBeGuessed();
		String w = "";
		for (int i = word.getLength() - 1; i >= 0; i--) {
			w += word.getElementAt(i);
		}
		assertEquals("Hello -zrt", w, "Test Case 2: getLettersToBeGuessed()");

	}

	@Test

	void test3() {

		String wString = "Hell2#o   -zrt!!";
		Player player = new Player("zrt");
		Hangman hangman = new Hangman(wString, player);

		assertTrue(hangman.guessLetter('z'),"Test Case 3: guessLetter()");  
		assertTrue(hangman.guessLetter('h'),"Test Case 3: guessLetter()"); 
		assertFalse(hangman.guessLetter('s'),"Test Case 3: guessLetter()"); 

		SinglyLinkedList<Character> guessed = hangman.getGuessedLetters();
		String guessedletter = "";
		for (int i = guessed.getLength() - 1; i >= 0; i--) {
			guessedletter += guessed.getElementAt(i);
		}

		SinglyLinkedList<Character> wrong = hangman.getLettersOfMistake();
		String wrongLetter = "";
		for (int i = wrong.getLength() - 1; i >= 0; i--) {
			wrongLetter += wrong.getElementAt(i);
		}

		SinglyLinkedList<Character> display = hangman.getDisplayString();
		String dispString = "";
		
		for (int i = 0; i < display.getLength(); i++) {
			dispString += display.getElementAt(i);
		}
		
		assertEquals("zh", guessedletter, "Test Case 3: getGuessedLetters()");
		assertEquals("s", wrongLetter, "Test Case 3: getLettersOfMistake()");
		assertEquals("H _ _ _ _ -z _ _", dispString, "Test Case 3: getDisplayString()");
		assertEquals(1, hangman.getNumberOfMistakes(), "Test Case 3: getNumberOfMistakes()");
		
		char hint = hangman.hint();
		System.out.println(hint);
		System.out.print(wString);
		System.out.print(isContains(hint, wString));
		assertTrue(isContains(hint, wString), "Test Case 3: hint()-hint letter is include to words");
		assertFalse(isContains(hint, guessedletter), "Test Case 3: hint()-hint letter is not include the letters that have been gussed");
		assertFalse(isContains(hint, wrongLetter), "Test Case 3: hint()-hint letter is not include the letters that have been wrong gussed");
	}
	
	public boolean isContains(char c, String str) {
		return(str.indexOf(String.valueOf(c)) !=-1 );
			
		} 
	
	
}
