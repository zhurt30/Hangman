package hangmanGame;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm.WordListener;

class DictionaryTest {

	@Test
	void test() throws IOException {
		Dictionary dictionary = new Dictionary();

		assertEquals(59, dictionary.getWordList().getLength());
		assertTrue(dictionary.hasNextWord());

		String nextString = dictionary.getNextWord();

		boolean isRemove = true;
		for (int i = 0; i < dictionary.getWordList().getLength(); i++) {
			System.out.println(dictionary.getWordList().getElementAt(i).toString());
			if (dictionary.getWordList().getElementAt(i).toString().equals(nextString))
				isRemove =false;
			
		}
        assertTrue(isRemove, "Test Case: getNextWord() - get the WordListener then remove it to dictionary" );
		assertEquals(58, dictionary.getWordList().getLength());
	}

}
