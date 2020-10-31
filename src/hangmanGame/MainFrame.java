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
import java.awt.Color;

public class MainFrame extends JFrame {

	private JPanel mainPanel;
	private JPanel letterPanel;
	private JPanel newPanel;
	private JPanel keyboardPanel;
	private JPanel dispPanel;
	private JLabel lettersLabel;
	private ArrayList<JButton> keyButtons;
	private HangmanRunner runner;
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
		setBounds(100, 100, 450, 650);
		this.setResizable(false);

		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(175, 238, 238));
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(mainPanel);

		dispPanel = new JPanel();
		dispPanel.setBackground(new Color(255, 255, 255));
		mainPanel.add(dispPanel, BorderLayout.CENTER);

		JLabel label = new JLabel("Hangman", SwingConstants.CENTER);
		label.setForeground(Color.DARK_GRAY);
		label.setFont(new Font("Snap ITC", Font.BOLD, 16));
		mainPanel.add(label, BorderLayout.NORTH);

		makeMenu();

		keyboardPanel();

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

		runner = new HangmanRunner();
		String name = login();

		if (currentGame == null)
			currentGame = runner.playGame(name);

		load(currentGame);

		checkStatus();
	}

	private String login() {
		Scoreboard board = runner.getScoreboard();
		String[] names = new String[board.getNumPlayers()];

		for (int i = 0; i < board.getNumPlayers(); i++) {
			names[i] = board.getNextPlayer(i).getName();
		}

		LoginFrame dialog = new LoginFrame(names);
		dialog.setModal(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		return dialog.getName();
	}

	private void load(Hangman game) {

		display(game.getLettersOfMistake().getLength());

		lettersLabel.setText(game.getDisplayString().toString());

		keyboardPanel.setVisible(true);
		for (JButton button : keyButtons) {
			char c = button.getText().charAt(0);
			boolean guessed = game.getGuessedLetters().contains(c) || game.getLettersOfMistake().contains(c);
			button.setEnabled(!guessed);
		}
		newPanel.setVisible(false);
	}

	private void guess(char letter) {
		int status = currentGame.getStatus();

		if (status == 0) {
			if (currentGame.guessLetter(letter)) {
				lettersLabel.setText(currentGame.getDisplayString().toString());
			} else {
				display(currentGame.getLettersOfMistake().getLength());
			}
		}
		checkStatus();
	}

	private void checkStatus() {
		int status = currentGame.getStatus();
		if (status == 1) {

			dispMessege("Congratulations!", "You won.");
		} else if (status == -1) {

			dispMessege("\nGame Over!", "Loss");

			lettersLabel.setText(currentGame.getWord().toString());
		}

		if (status != 0) {
			keyboardPanel.setVisible(false);
			boolean hasNext = runner.hasNextGame();
			newPanel.setVisible(hasNext);

			if (!hasNext) {
				dispMessege("Congratulations! ALL Done", "Won");
			}
		}
	}

	private void display(int index) {

		String source = String.format("images/hangman%d.png", index);
		clear(dispPanel);
		dispPanel.add(new JLabel(new ImageIcon(source)));
	}

	private void clear(JPanel panel) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}

	private void makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(new Color(0, 255, 255));
		menuBar.setBackground(new Color(240, 255, 255));
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setBackground(new Color(175, 238, 238));
		gameMenu.setForeground(new Color(0, 0, 0));
		JMenuItem newMenuItem = new JMenuItem("New Game");
		newMenuItem.setBackground(new Color(0, 250, 154));
		newMenuItem.setForeground(new Color(47, 79, 79));
		JMenuItem boradMenuItem = new JMenuItem("Scoreboard");
		boradMenuItem.setForeground(new Color(0, 0, 139));
		boradMenuItem.setBackground(new Color(240, 255, 255));
		JMenuItem hintMenuItem = new JMenuItem("Hint");
		hintMenuItem.setBackground(new Color(0, 255, 255));
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setBackground(new Color(240, 255, 255));
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setBackground(new Color(0, 255, 127));

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setBackground(new Color(0, 255, 255));
		JMenuItem helpMenuItem = new JMenuItem("Instructions");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.setBackground(new Color(240, 255, 255));

		setJMenuBar(menuBar);
		menuBar.add(gameMenu);
		gameMenu.add(newMenuItem);
		gameMenu.add(boradMenuItem);
		gameMenu.add(hintMenuItem);
		gameMenu.add(saveMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(exitMenuItem);

		boradMenuItem.addActionListener(l -> {
			ScoreBoardFrame dialog = new ScoreBoardFrame(runner.getScoreboard());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		});

		hintMenuItem.addActionListener(l -> {
			if (currentGame != null) {
				currentGame.hint();
				load(currentGame);
			}
		});

		saveMenuItem.addActionListener(e -> {
			try {
				runner.save();
				dispMessege("Game saved successfully！", "Save Game");
			} catch (IOException ex) {
				ex.printStackTrace();
				showMessage(ex.getMessage(), "Cann't Save Game！", JOptionPane.WARNING_MESSAGE);
			}
		});

		newMenuItem.addActionListener(e -> {
			try {
				if (runner.hasNextGame()) {
					currentGame = runner.nextGame();
					load(currentGame);
				} else {
					dispMessege("No more...", "No new game");
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		exitMenuItem.addActionListener(e -> {
			try {
				runner.save();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.processEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

		});

		menuBar.add(helpMenu);

		helpMenu.add(aboutMenuItem);

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

			dispMessege(panel, "About");
		});
	}

	private void keyboardPanel() {

		letterPanel = new JPanel();
		letterPanel.setBackground(new Color(175, 238, 238));
		letterPanel.setLayout(new BoxLayout(letterPanel, BoxLayout.X_AXIS));
		letterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		lettersLabel = new JLabel();
		lettersLabel.setForeground(new Color(0, 0, 0));
		lettersLabel.setBackground(new Color(175, 238, 238));
		lettersLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
		attributes.put(TextAttribute.TRACKING, 0.3);

		lettersLabel.setFont(new Font(lettersLabel.getFont().getName(), Font.PLAIN, 28));
		lettersLabel.setFont(lettersLabel.getFont().deriveFont(attributes));
		letterPanel.add(lettersLabel);

		keyboardPanel = new JPanel();
		keyboardPanel.setBackground(new Color(175, 238, 238));
		keyboardPanel.setLayout(new GridLayout(5, 6, 0, 0));
		keyboardPanel.setSize(360, 280);

		keyButtons = new ArrayList<>();

		for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
			final char letter = Character.toLowerCase(alphabet);
			JButton keyButton = new JButton(Character.toString(alphabet));
			keyButtons.add(keyButton);
			keyButton.setSize(50, 50);
			keyboardPanel.add(keyButton);
			keyButton.addActionListener(l -> {
				keyButton.setEnabled(false);
				guess(letter);

			});
		}

		newPanel = new JPanel();
		newPanel.setBackground(new Color(175, 238, 238));
		JButton nextButton = new JButton("Next");
		nextButton.setBackground(new Color(127, 255, 212));
		nextButton.addActionListener((l) -> {
			System.out.println("new game");
			load(currentGame = runner.nextGame());
		});
		newPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.setVisible(false);
		newPanel.add(nextButton);

		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(175, 238, 238));
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		southPanel.add(letterPanel);
		southPanel.add(Box.createVerticalStrut(50));
		southPanel.add(keyboardPanel);
		southPanel.add(newPanel);

		mainPanel.add(southPanel, BorderLayout.SOUTH);
	}

	private void dispMessege(Object message, String title) {
		showMessage(message, null, JOptionPane.INFORMATION_MESSAGE);
	}

	private void showMessage(Object message, String title, int messageType) {
		JOptionPane.showMessageDialog(this, message, title, messageType);
	}

	public void close(WindowEvent e) {
		String comfirmButtons[] = { "Yes", "No" };
		int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit game?", "Hangman",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, comfirmButtons, comfirmButtons[1]);
		if (PromptResult == 0) {
			try {
				runner.save();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}

	}

}
