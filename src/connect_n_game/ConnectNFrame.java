package connect_n_game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
/*
 * NOTE: To play the game, you must launch ConnectNGame_GameSelector. 
 * * 
 * 
 * */

public class ConnectNFrame extends JFrame implements ActionListener, SwingConstants {

	public static ConnectNGame frameGame;
	private JPanel contentPane;
	static JMenuBar menuBar = new JMenuBar();
	JMenu menuUndo = new JMenu("Undo");
	JMenu menuGame = new JMenu("Game");
	JMenuItem itemNewGame = new JMenuItem("New Game");
	JMenuItem itemRestoreGame = new JMenuItem("Restore Game");
	JMenuItem itemSaveGame = new JMenuItem("Save Game");
	JMenuItem itemExitGame = new JMenuItem("Exit Game");
	JMenu menuHelp = new JMenu("Help");
	JMenuItem itemInstructions = new JMenuItem("Instructions");
	JMenuItem itemAbout = new JMenuItem("About");
	static JLabel lblTurn;
	static JPanel pnlBtn = new JPanel();
	private ConnectNFrame newGame;
	private static JButton checkers[][];
	private final JMenuItem mntmUndoLastMove = new JMenuItem("Undo Last Move");
	private Color colors[] = { null, Color.YELLOW, Color.RED };
	private int playAgain;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ConnectNFrame() {
		playAgain = 1;
		frameGame = new ConnectNGame();
		lblTurn = new JLabel("It is " + frameGame.players[ConnectNGame.playerTurn].getPlayerName() + "'s turn");
		setTitle("Heritage Connect-N");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setJMenuBar(menuBar);

		menuBar.add(menuUndo);

		menuUndo.add(mntmUndoLastMove);
		menuGame.setHorizontalAlignment(SwingConstants.LEFT);

		menuBar.add(menuGame);

		menuGame.add(itemNewGame);

		menuGame.add(itemRestoreGame);

		menuGame.add(itemSaveGame);

		menuGame.add(itemExitGame);

		menuBar.add(menuHelp);

		menuHelp.add(itemInstructions);
		itemSaveGame.addActionListener(this);
		itemAbout.addActionListener(this);
		itemInstructions.addActionListener(this);
		itemRestoreGame.addActionListener(this);
		mntmUndoLastMove.addActionListener(this);
		itemExitGame.addActionListener(this);
		itemNewGame.addActionListener(this);

		menuHelp.add(itemAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		lblTurn.setHorizontalAlignment(SwingConstants.CENTER);
		lblTurn.setVerticalAlignment(SwingConstants.TOP);

		lblTurn.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		contentPane.add(lblTurn, BorderLayout.NORTH);
		contentPane.add(pnlBtn, BorderLayout.CENTER);
	}

	public void initializeButtons() {
		pnlBtn.setLayout(new GridLayout(frameGame.getNumRows(), frameGame.getNumCols()));
		checkers = new JButton[frameGame.getNumRows()][frameGame.getNumCols()];
		for (int row = 0; row < frameGame.getNumRows(); row++) {
			for (int col = 0; col < frameGame.getNumCols(); col++) {
				checkers[row][col] = new JButton();
				checkers[row][col].setFont(new Font("board", Font.BOLD, 24));
				pnlBtn.add(checkers[row][col]);
				checkers[row][col].addActionListener(this);
			}
		}
		disableBtns();
	}// initializeBoard()

	public void restoreBoard() {
		dispose();
		frameGame.restoreGame();
		pnlBtn.removeAll();
		lblTurn.setText("");
		initializeButtons();
		lblTurn.setText("It is " + frameGame.players[ConnectNGame.playerTurn].getPlayerName() + "'s turn");
		for (int row = 0; row < frameGame.getNumRows(); row++) {
			for (int col = 0; col < frameGame.getNumCols(); col++) {
				if (frameGame.accessBoard(row, col) == 'R') {
					checkers[row][col].setEnabled(false);
					checkers[row][col].setBackground(Color.RED);
				} else if (frameGame.accessBoard(row, col) == 'Y') {
					checkers[row][col].setEnabled(false);
					checkers[row][col].setBackground(Color.YELLOW);
				}
			}
		}
		disableBtns();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, ConnectNFrame.frameGame.getNumCols() * 80, ConnectNFrame.frameGame.getNumRows() * 80);
		setVisible(true);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	int undoCount = 1;

	public void actionPerformed(ActionEvent e) {
		if (frameGame.isGameOver() == 'C' && frameGame.isGameOver() != 'T') {
			for (int row = 0; row < frameGame.getNumRows(); row++) {
				for (int col = 0; col < frameGame.getNumCols(); col++) {
					if (e.getSource() == checkers[row][col]) {
						checkers[row][col].setBackground(colors[ConnectNGame.playerTurn]);
						frameGame.makeFrameMove(row, col, ConnectNGame.playerTurn);
						ConnectNGame.switchPlayerTurn();
						lblTurn.setText(
								"It is " + frameGame.players[ConnectNGame.playerTurn].getPlayerName() + "'s turn");
						checkers[row][col].setEnabled(false);
						undoCount = 0;
						disableBtns();
						if (frameGame.isGameOver() != 'C' && frameGame.isGameOver() != 'T') {
							if (frameGame.isGameOver() == 'Y')
								playAgain = JOptionPane.showConfirmDialog(this,
										"Congratulations " + frameGame.player1.getPlayerName()
												+ ", you won! Want to play another game?",
										"Congratulations", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
							else
								playAgain = JOptionPane.showConfirmDialog(this,
										"Congratulations " + frameGame.player2.getPlayerName()
												+ ", you won! Want to play another game?",
										"Congratulations", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
							if (playAgain == JOptionPane.YES_OPTION) {
								removeOld();
							} else {
								JOptionPane.showMessageDialog(this, "Thank you for choosing Heritage Connect-N!",
										"Thank You", JOptionPane.INFORMATION_MESSAGE);
								System.exit(-1);
							}
						}
					} // if(getSource())
				} // for(col)
			} // for(row)

			if (e.getSource() == mntmUndoLastMove) {
				if (undoCount == 0) {
					frameGame.undoFrameMove();
					JOptionPane.showMessageDialog(this, frameGame.players[ConnectNGame.playerTurn].getPlayerName()
							+ ", your last move has been undone", "Undo Move", JOptionPane.INFORMATION_MESSAGE);
					undoCount++;
					lblTurn.setText("It is " + frameGame.players[ConnectNGame.playerTurn].getPlayerName() + "'s turn");
					checkers[frameGame.players[ConnectNGame.playerTurn]
							.getLastMoveRow()][frameGame.players[ConnectNGame.playerTurn].getLastMoveCol()]
									.setBackground(null);
					checkers[frameGame.players[ConnectNGame.playerTurn]
							.getLastMoveRow()][frameGame.players[ConnectNGame.playerTurn].getLastMoveCol()]
									.setEnabled(true);
					disableBtns();
				} else
					JOptionPane.showMessageDialog(this, "You cannot undo on the first move or twice in a row.",
							"Invalid Undo", JOptionPane.ERROR_MESSAGE);
			}
			if (e.getSource() == itemExitGame) {
				int saveGame = JOptionPane.showConfirmDialog(this,
						"Would you like to save the game to " + ConnectNGame.fileName + " before exiting?",
						"Save Game?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (saveGame == JOptionPane.YES_OPTION) {
					frameGame.saveGame();
					JOptionPane.showMessageDialog(this,
							"Game has been saved to " + ConnectNGame.fileName
									+ ". Thank you for choosing Heritage Connect-N!",
							"Game Saved, Thank You", JOptionPane.INFORMATION_MESSAGE);
					System.exit(-1);
				} else {
					JOptionPane.showMessageDialog(this,
							"Game was not saved. Thank you for choosing Heritage Connect-N!", "Thank You",
							JOptionPane.INFORMATION_MESSAGE);
					System.exit(-1);
				}
			}
		} // if(C)
		if (frameGame.isGameOver() == 'T') {
			playAgain = JOptionPane.showConfirmDialog(this, "It's a tie! Nobody won! Want to play another game?", "Tie",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (playAgain == JOptionPane.YES_OPTION) {
				removeOld();
			} else {
				JOptionPane.showMessageDialog(this, "Thank you for choosing Heritage Connect-N!", "Thank You",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(-1);
			}
		} // if (tie)

		if (e.getSource() == itemAbout)
			JOptionPane.showMessageDialog(this, new ConnectNGame_AboutPanel(), "About Game", JOptionPane.PLAIN_MESSAGE);

		else if (e.getSource() == itemInstructions)
			JOptionPane.showMessageDialog(this,
					new ConnectNGame_Instructions(frameGame.player1.getPlayerName(), frameGame.player2.getPlayerName(),
							frameGame.getN(), frameGame.getNumRows(), frameGame.getNumCols()),
					"Instructions", JOptionPane.PLAIN_MESSAGE);
		else if (e.getSource() == itemRestoreGame) {
			int restore = JOptionPane.showConfirmDialog(this, "Are you sure you want to leave this game?",
					"Are You Sure?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (restore == JOptionPane.YES_OPTION)
				restoreBoard();
		} else if (e.getSource() == itemNewGame) {
			int newOne = JOptionPane.showConfirmDialog(this, "Are you sure you want to leave this game?",
					"Are You Sure?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (newOne == JOptionPane.YES_OPTION)
				removeOld();
		} else if (e.getSource() == itemSaveGame) {
			frameGame.saveGame();
			JOptionPane.showMessageDialog(this, "The game has been saved to " + ConnectNGame.fileName, "Game Saved",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}// actionPerformed

	private void disableBtns() {
		for (int row = 0; row < frameGame.getNumRows() - 1; row++) {
			for (int col = 0; col < frameGame.getNumCols(); col++) {
				if ((checkers[row][col].getBackground() != Color.YELLOW
						&& checkers[row][col].getBackground() != Color.RED
						&& checkers[row + 1][col].getBackground() != Color.YELLOW
						&& checkers[row + 1][col].getBackground() != Color.RED)
						|| checkers[row][col].getBackground() == Color.YELLOW
						|| checkers[row][col].getBackground() == Color.RED)
					checkers[row][col].setEnabled(false);
				else
					checkers[row][col].setEnabled(true);
			}
		}
	}

	private void removeOld() {
		lblTurn.setText("");
		menuBar.removeAll();
		pnlBtn.removeAll();
		setVisible(false);
		dispose();
		new ConnectNGame_GameSelector().setVisible(true);
	}
}
