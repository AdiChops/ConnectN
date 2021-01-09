package connect_n_game;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Color;

/*
 * NOTE: To play the game, this is the class you must launch.
 * */
public class ConnectNGame_GameSelector extends JFrame {

	private boolean validRows;
	private boolean validCols;
	private boolean namesExist;
	private boolean validNum;
	private JPanel contentPane;
	private JTextField fldPlayer1;
	private JTextField fldPlayer2;
	private static JPanel newGameSetupPanel;
	private static JPanel gameSelectorPanel;
	private JComboBox cmbxN;
	private static ConnectNGame_GameSelector frame = new ConnectNGame_GameSelector();
	ConnectNFrame gameFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConnectNGame_GameSelector() {
		setTitle("Heritage Connect-N Setup");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 597, 522);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		validRows = true;
		validCols = true;
		namesExist = true;
		validNum = true;
		newGameSetupPanel = new JPanel();
		newGameSetupPanel.setVisible(false);
		newGameSetupPanel.setBounds(52, 64, 495, 393);
		contentPane.add(newGameSetupPanel);
		newGameSetupPanel.setLayout(null);

		JLabel lblPlayer1 = new JLabel("Player 1 Name: ");
		lblPlayer1.setHorizontalTextPosition(SwingConstants.LEADING);
		lblPlayer1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayer1.setBounds(83, 33, 103, 14);
		newGameSetupPanel.add(lblPlayer1);

		fldPlayer1 = new JTextField();
		fldPlayer1.setColumns(10);
		fldPlayer1.setBounds(196, 30, 143, 20);
		newGameSetupPanel.add(fldPlayer1);

		JLabel lblPlayer2 = new JLabel("Player 2 Name: ");
		lblPlayer2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayer2.setBounds(83, 80, 103, 14);
		newGameSetupPanel.add(lblPlayer2);

		fldPlayer2 = new JTextField();
		fldPlayer2.setColumns(10);
		fldPlayer2.setBounds(196, 77, 143, 20);
		newGameSetupPanel.add(fldPlayer2);

		JLabel lblN = new JLabel("N: ");
		lblN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblN.setBounds(83, 127, 103, 14);
		newGameSetupPanel.add(lblN);

		cmbxN = new JComboBox();
		cmbxN.setModel(new DefaultComboBoxModel(new String[] { "--", "3", "4", "5", "6", "7", "8" }));
		cmbxN.setBounds(196, 124, 48, 20);
		newGameSetupPanel.add(cmbxN);

		JLabel lblRows = new JLabel("Number of Rows: ");
		lblRows.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRows.setBounds(83, 179, 103, 14);
		newGameSetupPanel.add(lblRows);

		JLabel lblCols = new JLabel("Number of Columns: ");
		lblCols.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCols.setBounds(63, 231, 123, 14);
		newGameSetupPanel.add(lblCols);

		JComboBox cmbxRows = new JComboBox();
		cmbxRows.setModel(
				new DefaultComboBoxModel(new String[] { "--", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		cmbxRows.setBounds(196, 176, 48, 20);
		newGameSetupPanel.add(cmbxRows);

		JComboBox cmbxCols = new JComboBox();
		cmbxCols.setModel(
				new DefaultComboBoxModel(new String[] { "--", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		cmbxCols.setBounds(196, 228, 48, 20);
		newGameSetupPanel.add(cmbxCols);

		JButton btnPlay = new JButton("Play Game");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbxRows.getSelectedIndex() != 0) {
					validRows = true;
					if (cmbxCols.getSelectedIndex() != 0) {
						validCols = true;
						if (validateNum(Integer.parseInt(String.valueOf(cmbxRows.getSelectedItem())),
								Integer.parseInt(String.valueOf(cmbxCols.getSelectedItem())))) {
							validNum = true;
							if (!fldPlayer1.getText().isEmpty() && !fldPlayer2.getText().isEmpty()) {
								namesExist = true;
								gameFrame = new ConnectNFrame();
								ConnectNFrame.frameGame
										.setNumCols(Integer.parseInt(String.valueOf(cmbxCols.getSelectedItem())));
								ConnectNFrame.frameGame.setN(Integer.parseInt(String.valueOf(cmbxN.getSelectedItem())));
								ConnectNFrame.frameGame
										.setNumRows(Integer.parseInt(String.valueOf(cmbxRows.getSelectedItem())));
								ConnectNFrame.frameGame.player1.setPlayerName(fldPlayer1.getText());
								ConnectNFrame.frameGame.player2.setPlayerName(fldPlayer2.getText());
								ConnectNFrame.frameGame.player1.setCheckerColor('Y');
								ConnectNFrame.frameGame.player2.setCheckerColor('R');
								ConnectNFrame.lblTurn.setText("It is "
										+ ConnectNFrame.frameGame.players[ConnectNGame.playerTurn].getPlayerName()
										+ "'s turn");
								gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
								gameFrame.setBounds(100, 100, ConnectNFrame.frameGame.getNumCols() * 80,
										ConnectNFrame.frameGame.getNumRows() * 80);
								gameFrame.setVisible(true);
								gameFrame.frameGame.newGame();
								gameFrame.initializeButtons();
								setVisible(false);
							} // if(name)
							else
								namesExist = false;
						} // if(N)
						else
							validNum = false;
					} // if(cols)
					else
						validCols = false;
				} // if(rows)
				else
					validRows = false;
				checkFields();
			}// actionPerformed()
		});
		btnPlay.setBounds(83, 305, 103, 23);
		newGameSetupPanel.add(btnPlay);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fldPlayer1.setText("");
				fldPlayer2.setText("");
				cmbxN.setSelectedIndex(0);
				cmbxRows.setSelectedIndex(0);
				cmbxCols.setSelectedIndex(0);
			}
		});
		btnClear.setBounds(200, 305, 89, 23);
		newGameSetupPanel.add(btnClear);

		gameSelectorPanel = new JPanel();
		gameSelectorPanel.setBounds(31, 102, 480, 299);
		contentPane.add(gameSelectorPanel);
		gameSelectorPanel.setLayout(null);

		JTextArea txtAreaExplain = new JTextArea();
		txtAreaExplain.setWrapStyleWord(true);
		txtAreaExplain.setText(
				"Would you like to start a new game or would you like to restore the game that is currently saved?");
		txtAreaExplain.setLineWrap(true);
		txtAreaExplain.setFont(new Font("Calibri Light", Font.PLAIN, 18));
		txtAreaExplain.setEditable(false);
		txtAreaExplain.setBackground(SystemColor.menu);
		txtAreaExplain.setBounds(44, 52, 437, 148);
		gameSelectorPanel.add(txtAreaExplain);

		JButton btnNew = new JButton("New Game");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameSelectorPanel.setVisible(false);
				newGameSetupPanel.setVisible(true);
			}
		});
		btnNew.setBounds(112, 211, 97, 23);
		gameSelectorPanel.add(btnNew);

		JButton btnRestore = new JButton("Restore Game");
		btnRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameFrame = new ConnectNFrame();
				gameFrame.restoreBoard();
			}
		});
		btnRestore.setBounds(267, 211, 128, 23);
		gameSelectorPanel.add(btnRestore);

		JLabel lblTitle = new JLabel("Welcome to Heritage Connect-N!");
		lblTitle.setForeground(new Color(0, 128, 128));
		lblTitle.setFont(new Font("Kristen ITC", Font.PLAIN, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(31, 11, 516, 42);
		contentPane.add(lblTitle);

	}

	private boolean validateNum(int row, int col) {
		return (cmbxN.getSelectedIndex() != 0 && (Integer.parseInt(String.valueOf(cmbxN.getSelectedItem())) <= row
				|| Integer.parseInt(String.valueOf(cmbxN.getSelectedItem())) <= col));
	}

	public void checkFields() {
		if (!namesExist)
			JOptionPane.showMessageDialog(this, "Please enter the name of both of the players.", "Missing Name(s)",
					JOptionPane.ERROR_MESSAGE);
		if (!validRows)
			JOptionPane.showMessageDialog(this, "Please select the number of rows on the board.",
					"Missing Number of Rows", JOptionPane.ERROR_MESSAGE);
		if (!validCols)
			JOptionPane.showMessageDialog(this, "Please select the number of columns on the board.",
					"Missing Number of Columns", JOptionPane.ERROR_MESSAGE);
		if (!validNum)
			JOptionPane.showMessageDialog(this,
					"Please select a valid value for N that is smaller than or equal to the number of rows and/or columns.",
					"Invalid N", JOptionPane.ERROR_MESSAGE);

	}
}// class
