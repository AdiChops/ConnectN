package connect_n_game;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;

public class ConnectNGame_Instructions extends JPanel {

	/**
	 * Create the panel.
	 */
	public ConnectNGame_Instructions(String player1Name, String player2Name, int num, int numRows, int numCols) {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder(getBorder(), "Instructions"));
		
		JTextArea txtrTheRules = new JTextArea();
		txtrTheRules.setWrapStyleWord(true);
		txtrTheRules.setBackground(SystemColor.menu);
		txtrTheRules.setLineWrap(true);
		txtrTheRules.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		txtrTheRules.setText("The rules to this game are very simple. There will be two players that will be playing against each other, player 1 (" + player1Name  + ") with yellow checkers and player 2 (" + player2Name + ") with red checkers. The first player to connect "  + num + " checkers on a board of " + numRows +  " rows and " + numCols +" colums wins the game.");
		txtrTheRules.setEditable(false);
		txtrTheRules.setBounds(23, 55, 417, 124);
		GridBagConstraints gbc_txtrTheRules = new GridBagConstraints();
		gbc_txtrTheRules.gridy = 0;
		add(txtrTheRules, gbc_txtrTheRules);

	}
}
