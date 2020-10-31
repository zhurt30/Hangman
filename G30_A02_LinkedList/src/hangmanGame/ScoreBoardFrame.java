package hangmanGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;



import javax.swing.JTable;
import java.awt.Color;

public class ScoreBoardFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;

	public ScoreBoardFrame(Scoreboard board) {
		setBounds(100, 100, 400, 270);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.ORANGE);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			int numPlayers = board.getNumPlayers();
			Object[][] data = new Object[numPlayers][3];

			for (int i = 0; i < numPlayers; i++) {
				Object[] row = data[numPlayers-i-1];

				Player player = board.getNextPlayer(i);
				row[0] = player.getName();
				row[1] = player.getNumberGamesWon();
				row[2] = player.getNumberGamesPlayed();
			}

			String[] columnNames = { "Player", "Won Times", "Play Numbers" };

			table = new JTable(data, columnNames);
			contentPanel.add(new JScrollPane(table));
			pack();
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 200, 0));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton confirmButton = new JButton("Confirm");
				confirmButton.setActionCommand("Confirm");
				buttonPane.add(confirmButton);
				getRootPane().setDefaultButton(confirmButton);
				confirmButton.addActionListener(l -> {
					dispose();
				});
			}
			
		}
	}

}
