package hangmanGame;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;
import linked_data_structures.SinglyLinkedList;

public class Dictionary  implements Serializable {
	
	
	private SinglyLinkedList<String> wordList;

	public Dictionary() throws IOException {
		this.wordList = new SinglyLinkedList<>();
		makeDictionary();
	}
	
	
	public SinglyLinkedList<String> getWordList() {
		return wordList;
	}

	public void setWordList(SinglyLinkedList<String> wordList) {
		this.wordList = wordList;
	}


	public void makeDictionary() throws IOException {
		File fileName = new File("dictionary.txt");
		Scanner sc = new Scanner(fileName);
		while (sc.hasNextLine()) {
			String word = sc.nextLine();
				wordList.add(word);	
		}
	}
	

	
	public boolean hasNextWord() {
		return wordList.getLength() > 0;
	}
	
	public String getNextWord() {
		Random random = new Random();
		int randomNum = random.nextInt(wordList.getLength());
		String word = wordList.getElementAt(randomNum);
		wordList.remove(randomNum);
		
		System.out.println("word is:" +word);
		
		
		return word;
	}
	
//	public static void main(String[] args) {
//		Dictionary dictionary;
//		try {
//			dictionary = new Dictionary();
//			System.out.println();
//			System.out.println("--------------------");
//			dictionary.getNextWord();
//			System.out.println("--------------------");
//			
//			
//		
//for(int i =dictionary.getLength()-1 ; i>=0 ; i--) {
//	System.out.print(dictionary.getElementAt(i)+"/");
//}			
//			
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			
//		}
//		
//	}

}
