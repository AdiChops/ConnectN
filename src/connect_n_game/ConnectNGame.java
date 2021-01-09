package connect_n_game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Aaditya Chopra Description: This class provides the logic of the
 *         game.
 *
 */
public class ConnectNGame {
	public static String fileName;
	public static int numRows;
	public static int numCols;
	public int n;
	public char[][] board;
	public static int playerTurn;
	public File gameState;
	public Player player1 = new Player();
	public Player player2 = new Player();
	public Player[] players = new Player[3];

	public ConnectNGame() {
		n = 4;
		numRows = 6;
		numCols = 7;
		playerTurn = 1;
		players[1] = player1;
		players[2] = player2;
		board = new char[numRows][numCols];
		fileName = "currentGame.txt";
		gameState = new File(fileName);
		newGame();

	}

	public void newGame() {
		board = new char[numRows][numCols];
		for (char[] chars : board) Arrays.fill(chars, 'E');
	}

	public void setNumRows(int rows) {
		numRows = rows;
	}

	public void setNumCols(int cols) {
		numCols = cols;
	}

	public void setN(int num) {
		n = num;
	}

	public static void setPlayerTurn(int turn) {
		playerTurn = turn;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public int getN() {
		return n;
	}

	public boolean validRow(int row) {
		return row <= numRows;
	}

	public boolean validCol(int col) {
		return col <= numCols;
	}

	public boolean validateMove(int row, int col) {
		if (row != 1) {
			return board[convertRow(row) + 1][convertCol(col)] != 'E';
		} else
			return true;
	}

	public boolean validateN(int num) {
		return num >= 3 && num <= 8 && (num <= numRows || num <= numCols);
	}

	public boolean validateNumRows(int rows) {
		return rows >= 4 && rows <= 12;
	}

	public boolean validateNumCols(int cols) {
		return cols >= 4 && cols <= 12;
	}

	public boolean checkEmpty(int row, int col) {
		return board[convertRow(row)][convertCol(col)] == 'E';
	}

	public char isGameOver() {
		for (int col = 0; col < board[0].length; col++) {
			if (checkCol(col) != -1)
				return board[checkCol(col)][col];
		}
		if (getN() <= getNumRows() && getN() <= getNumCols()) {
			int row = getNumRows() - getN();
			int col;
			while (row >= 0) {
				col = 0;
				while (col <= (getNumCols() - getN() -1 ) && checkLeftToRightDiagonal(row, col) == 'N')
					col++;
				if (checkLeftToRightDiagonal(row, col) == 'N')
					row--;
				else
					return checkLeftToRightDiagonal(row, col);
			}
			int rToLRow = getNumRows() - getN();
			int rToLCol;
			while (rToLRow >= 0) {
				rToLCol = getN() - 1;
				while (rToLCol < getNumCols() - 1 && checkRightToLeftDiagonal(rToLRow, rToLCol) == 'N')
					rToLCol++;
				if (checkRightToLeftDiagonal(rToLRow, rToLCol) == 'N')
					rToLRow--;
				else
					return checkRightToLeftDiagonal(rToLRow, rToLCol);
			}
		} // if(n < rows && n < cols)

		for (char[] chars : board)
			if (checkRow(chars) != -1)
				return chars[checkRow(chars)];
		for (char[] chars : board) {
			for (char aChar : chars)
				if (aChar == 'E')
					return 'C';// continue game
		}
		return 'T';// tie
	}

	private int checkRow(char[] rowBoard) {
		int count = 0;
		int i = 0;
		while (i < rowBoard.length - 1 && count < n - 1) {
			if (rowBoard[i] != 'E' && rowBoard[i] == rowBoard[i + 1])
				count++;
			else if (rowBoard[i] != 'E' && rowBoard[i] != rowBoard[i + 1])
				count = 0;
			i++;
		}
		count++;
		if (count == n)
			return i;
		else
			return -1;// no win
	}

	private int checkCol(int col) {
		int count = 0;
		int row = 0;
		while (row < board.length - 1 && count < n - 1) {
			if (board[row][col] != 'E' && board[row][col] == board[row + 1][col])
				count++;
			else if (board[row][col] != 'E' && board[row][col] != board[row + 1][col])
				count = 0;
			row++;
		}
		count++;
		if (count == n)
			return row;
		else
			return -1;// no win
	}

	private char checkLeftToRightDiagonal(int r, int c) {
		for (int i = 1; i < getN(); i++)
			if (board[r][c] != board[r + i][c + i] || board[r][c] == 'E')
				return 'N';// no win
		return board[r][c];
	}

	private char checkRightToLeftDiagonal(int r, int c) {
		for (int i = 1; i < getN(); i++)
			if (board[r][c] != board[r + i][c - i] || board[r][c] == 'E')
				return 'N';// no win
		return board[r][c];
	}

	public void makeMove(int row, int col, int turn) {
		board[convertRow(row)][convertCol(col)] = players[turn].getCheckerColor();
	}

	public void makeFrameMove(int row, int col, int turn) {
		board[row][col] = players[turn].getCheckerColor();
		players[turn].setLastMoveRow(row);
		players[turn].setLastMoveCol(col);
	}

	public void undoMove() {
		switchPlayerTurn();
		board[convertRow(players[playerTurn].getLastMoveRow())][convertCol(players[playerTurn].getLastMoveCol())] = 'E';
	}

	public void undoFrameMove() {
		switchPlayerTurn();
		board[players[playerTurn].getLastMoveRow()][players[playerTurn].getLastMoveCol()] = 'E';
	}

	public void saveGame() {
		try {
			FileWriter out = new FileWriter(gameState);
			out.write(getNumRows() + "\n");
			out.write(getNumCols() + "\n");
			out.write(getN() + "\n");
			out.write(player1.getPlayerName() + "\n");
			out.write(player2.getPlayerName() + "\n");
			out.write(playerTurn + "\n");
			for (char[] chars : board) {
				for (int col = 0; col < chars.length - 1; col++)
					out.write(chars[col] + "~");
				out.write(chars[chars.length - 1] + "\n");
			}
			out.close();
		} catch (IOException e) {
			System.err.println("ERROR! " + e.getMessage());
		}
	}

	public void restoreGame() {
		Scanner inFile;
		try {
			inFile = new Scanner(gameState);
			inFile.useDelimiter("~|\r?\n");
			setNumRows(inFile.nextInt());
			setNumCols(inFile.nextInt());
			setN(inFile.nextInt());
			player1.setPlayerName(inFile.next());
			player1.setCheckerColor('Y');
			player2.setPlayerName(inFile.next());
			player2.setCheckerColor('R');
			setPlayerTurn(inFile.nextInt());
			board = new char[getNumRows()][getNumCols()];
			for (int row = 0; row < getNumRows(); row++)
				for (int col = 0; col < getNumCols(); col++)
					board[row][col] = inFile.next().charAt(0);
		} catch (FileNotFoundException e) {
			System.err.println(fileName + " not found.");
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("ERROR! " + e.getMessage());
			System.exit(-2);
		}
	}

	private int convertRow(int row) {
		return board.length - row;
	}

	private int convertCol(int col) {
		return col - 1;
	}

	public static void switchPlayerTurn() {
		if (playerTurn == 1)
			setPlayerTurn(2);
		else
			setPlayerTurn(1);
	}

	public char accessBoard(int r, int c) {
		return board[r][c];
	}
}
