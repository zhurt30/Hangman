package hangmanGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;


	public class LoginFrame extends JDialog {

		private final JPanel contentPanel = new JPanel();
		private String name;

	
		public LoginFrame(String[] names) {
			setBackground(new Color(240, 230, 140));
			setBounds(100, 100, 450, 300);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBackground(new Color(255, 222, 173));
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);

			contentPanel.setLayout(null);
			{
				JLabel lblNewLabel = new JLabel("Your name:");
				lblNewLabel.setBounds(2000, 15, 71, 16);
				contentPanel.add(lblNewLabel);
			}
			JComboBox comboBox = new JComboBox(names);
			comboBox.setBounds(163, 83, 183, 26);
			{
				comboBox.setEditable(true);
				contentPanel.add(comboBox);
			}

			JLabel lblMessage = new JLabel();
			lblMessage.setBounds(173, 121, 258, 16);
			lblMessage.setForeground(Color.red);
			contentPanel.add(lblMessage);
			{
				JLabel lblNewLabel_1 = new JLabel("Name:");
				lblNewLabel_1.setBounds(100, 88, 61, 16);
				contentPanel.add(lblNewLabel_1);
			}
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setBackground(new Color(255, 239, 213));
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton confirmButton = new JButton("OK");
					confirmButton.setActionCommand("OK");
					buttonPane.add(confirmButton);
					getRootPane().setDefaultButton(confirmButton);

					confirmButton.addActionListener(l -> {
						String input = comboBox.getSelectedItem().toString();
						if (input == null || input.length() ==0 ) {
							lblMessage.setText("Please input name !");
						} else {
							name = input;
							dispose();
						}
					});

				}
				{
					JButton cancelButton = new JButton("Cancel");
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
					cancelButton.addActionListener(l -> {

						dispose();
					});
				}
			}
		}

		public String getName() {
			return name;
		}
	}//LoginFrame