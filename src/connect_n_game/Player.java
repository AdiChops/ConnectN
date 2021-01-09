package connect_n_game;

public class Player {

	public String playerName;
	public int lastMoveRow;
	public int lastMoveCol;
	public char checkerColor;

	public Player() {
		playerName = null;
		lastMoveRow = 0;
		lastMoveCol = 0;
		checkerColor = 'E';
	}

	public void setPlayerName(String name) {
		playerName = name;
	}

	public void setLastMoveRow(int row) {
		lastMoveRow = row;
	}

	public void setLastMoveCol(int col) {
		lastMoveCol = col;
	}

	public void setCheckerColor(char color) {
		checkerColor = color;
	}

	public String getPlayerName() {
		return playerName;
	}

	public char getCheckerColor() {
		return checkerColor;
	}

	public int getLastMoveRow() {
		return lastMoveRow;
	}

	public int getLastMoveCol() {
		return lastMoveCol;
	}

}
