package hangmanGame;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import linked_data_structures.SinglyLinkedList;

public class Dictionary extends SinglyLinkedList<String> {
	
	
	//private SinglyLinkedList<String> wordList;

	public Dictionary() throws IOException {
		//this.wordList = new SinglyLinkedList<>();
		makeDictionary();
	}
	
	
//	public SinglyLinkedList<String> getWordList() {
//		return wordList;
//	}

//	public void setWordList(SinglyLinkedList<String> wordList) {
//		this.wordList = wordList;
//	}


	public void makeDictionary() throws IOException {
		File fileName = new File("dictionary.txt");
		Scanner sc = new Scanner(fileName);
		while (sc.hasNextLine()) {
			String word = sc.nextLine();
		//	word = word.replaceAll("[^\\w\\s.]+", "");
			System.out.print(word+"/");
				this.add(word);	
		}
	}
	

//	public void removeWord(String word) {
//		if (wordList.getLength() == 0)
//			throw new IllegalArgumentException("no word anymore");
//		for (int i = 0; i < wordList.getLength(); i++) {
//			if (wordList.getElementAt(i).equals(word))
//				wordList.remove(i);
//		}
//	}
//
//	public String getRandomWord() {
//		if (wordList.getLength() == 0)
//			throw new IllegalArgumentException("no word anymore");
//		Random random = new Random();
//		int randomNum = random.nextInt(wordList.getLength());
//		String word = wordList.getElementAt(randomNum);
//		return word;
//	}
	
	public boolean hasNextWord() {
		return this.getLength() > 0;
	}
	
	public String getNextWord() {
		Random random = new Random();
		int randomNum = random.nextInt(this.getLength());
		String word = this.getElementAt(randomNum);
		this.remove(randomNum);
		
		System.out.println("word is:" +word);
		
		
		return word;
	}
	
	public static void main(String[] args) {
		Dictionary dictionary;
		try {
			dictionary = new Dictionary();
			System.out.println();
			System.out.println("--------------------");
			dictionary.getNextWord();
			System.out.println("--------------------");
			
			
		
for(int i =dictionary.getLength()-1 ; i>=0 ; i--) {
	System.out.print(dictionary.getElementAt(i)+"/");
}			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			
		}
		
	}

}
