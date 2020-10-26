package hangmanGame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import java.awt.font.TextAttribute;
import java.io.IOException;

import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel wordPanel;
	private JPanel nextPanel;
	private JPanel keyboardPanel;
	private JPanel picturePanel;
	private JLabel wordLabel;
	private ArrayList<JButton> keyButtons;
	private HangmanRunner lobby;
	private Hangman currentGame;
	private Player currentPlayer;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 640);
		this.setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		picturePanel = new JPanel();
		contentPane.add(picturePanel, BorderLayout.CENTER);

		contentPane.add(new JLabel("Hangman", SwingConstants.CENTER), BorderLayout.NORTH);

		createMenu();

		createKeyboardPanel();

		try {
			initialize();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initialize() throws ClassNotFoundException, IOException {
		lobby = new HangmanRunner();

		if (currentGame == null)
			currentGame = lobby.playGame("zrt");

//		Character c = currentGame.hint();
//		guess(c);

//		render(currentGame);

		checkGameStatus();
	}

	private void render(Hangman game) {
		// set picture
		showPicture(game.getLettersOfMistake().getLength());

		// reset word blanks
		wordLabel.setText(game.getGuessedLetters().toString());

		// reset keyboard
		keyboardPanel.setVisible(true);
		for (JButton button : keyButtons) {
			char c = button.getText().charAt(0);
			boolean guessed = game.getGuessedLetters().contains(c) || game.getLettersOfMistake().contains(c);
			button.setEnabled(!guessed);
		}
		nextPanel.setVisible(false);
	}


	private void guess(char letter) {
		int status = currentGame.statusGame();
		if (status == 0) {
			if (currentGame.guessLetter(letter)) {
				wordLabel.setText(currentGame.getLettersToBeGuessed().toString());
			} else {
				showPicture(currentGame.getLettersOfMistake().getLength());
			}
		}
		checkGameStatus();
	}

	private void checkGameStatus() {
		int status = currentGame.statusGame();
		if (status == 1) {

			showMessage("Congratulations!", "You won.");
		} else if (status == -1) {

			showMessage("\nGame Over!", "Loss");

			wordLabel.setText(currentGame.getWord().toString());
		}

		// Game Over
		if (status != 0) {
			keyboardPanel.setVisible(false);
			boolean hasNext = lobby.hasNextGame();
			nextPanel.setVisible(hasNext);

			if (!hasNext) {
				showMessage("Congratulations! ALL Done", "Won");
			}
		}
	}

	private void showPicture(int index) {

		String source = String.format("images/hangman%d.png", index);
		clearPanel(picturePanel);
		picturePanel.add(new JLabel(new ImageIcon(source)));
	}

	private void clearPanel(JPanel panel) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}

	// alphabet keyboard
	private void createKeyboardPanel() {
		// create Word Panel
		wordPanel = new JPanel();
		wordPanel.setLayout(new BoxLayout(wordPanel, BoxLayout.X_AXIS));
		wordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		wordLabel = new JLabel();
		wordLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
		attributes.put(TextAttribute.TRACKING, 0.3);

		wordLabel.setFont(new Font(wordLabel.getFont().getName(), Font.PLAIN, 28));
		wordLabel.setFont(wordLabel.getFont().deriveFont(attributes));
		wordPanel.add(wordLabel);

		// create Keyboard Panel
		keyboardPanel = new JPanel();
		keyboardPanel.setLayout(new GridLayout(4, 7, 0, 0));
		keyboardPanel.setSize(360, 280);

		keyButtons = new ArrayList<>();

		for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
			final char letter = Character.toLowerCase(alphabet);
			JButton keyButton = new JButton(Character.toString(alphabet));
			keyButtons.add(keyButton);
			keyButton.setSize(50, 50);
			keyboardPanel.add(keyButton);
			if (alphabet == 'U')
				keyboardPanel.add(new JLabel());

			keyButton.addActionListener(l -> {
				keyButton.setEnabled(false);
				guess(letter);
			});
		}

		nextPanel = new JPanel();
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener((l) -> {
			System.out.println("new game");
			render(currentGame = lobby.nextGame());
		});
		nextPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		nextPanel.setVisible(false);
		nextPanel.add(nextButton);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		southPanel.add(wordPanel);
		southPanel.add(Box.createVerticalStrut(50));
		southPanel.add(keyboardPanel);
		southPanel.add(nextPanel);

		contentPane.add(southPanel, BorderLayout.SOUTH);
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		JMenuItem undoMenuItem = new JMenuItem("Undo");
		JMenuItem exitMenuItem = new JMenuItem("Exit");

		JMenu helpMenu = new JMenu("Help");
		JMenuItem helpMenuItem = new JMenuItem("Instructions");
		JMenuItem aboutMenuItem = new JMenuItem("About");

		setJMenuBar(menuBar);
		menuBar.add(gameMenu);
		gameMenu.add(newMenuItem);
		gameMenu.add(saveMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(undoMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(exitMenuItem);

		saveMenuItem.addActionListener(e -> {

		});



		exitMenuItem.addActionListener(e -> {
			this.processEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

		});

		menuBar.add(helpMenu);
		helpMenu.add(helpMenuItem);
		helpMenu.add(aboutMenuItem);
		
		JMenu mnNewMenu = new JMenu("About");
		menuBar.add(mnNewMenu);

		helpMenuItem.addActionListener(e -> {
			JTextArea area = new JTextArea("");
			area.setSize(400, 300);
			area.setWrapStyleWord(true);
			area.setAutoscrolls(true);
			area.setLineWrap(true);
		//	area.setText(
					
		//	showMessage(area, "Help");
		});

		aboutMenuItem.addActionListener(e -> {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(5, 2));
			panel.add(new JLabel("Title:"));
			panel.add(new JLabel("Hangman"));
			panel.add(new JLabel("Author:"));
			panel.add(new JLabel("RuitaoZhu"));
			panel.add(new JLabel("Year:"));
			panel.add(new JLabel("2020"));
			panel.add(new JLabel("School:"));
			panel.add(new JLabel("Heritage College"));

//			showMessage(panel, "About");
		});
	}

	private void showMessage(Object message, String title) {
		showMessage(message, null, JOptionPane.INFORMATION_MESSAGE);
	}

	private void showMessage(Object message, String title, int messageType) {
		JOptionPane.showMessageDialog(this, message, title, messageType);
	}

	

}
