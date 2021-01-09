
package connect_n_game;

import java.util.Scanner;

public class ConnectNCLI {
	private static int moves;

	public ConnectNCLI() {
		moves = 0;
	}

	private static ConnectNGame interfaceGame = new ConnectNGame();

	private static void displayBoard() {
		for (int row = 0; row < interfaceGame.board.length; row++) {
			for (int col = 0; col < interfaceGame.board[row].length; col++) {
				System.out.print(interfaceGame.board[row][col] + "  ");
			}
			System.out.println();
		}
	}

	private static void promptUser() {
		displayBoard();
		System.out.print(interfaceGame.players[ConnectNGame.playerTurn].getPlayerName()
				+ ", enter square number (row, column) of your move > ");
	}

	private static boolean checkInput(String val) {
		if (!val.contains(","))
			return false;
		String firstNum = val.substring(0, val.indexOf(","));
		String secondNum = val.substring(val.indexOf(",") + 1, val.length());
		for (int i = 0; i < firstNum.length(); i++)
			if (!Character.isDigit(firstNum.charAt(i)))
				return false;
		for (int i = 0; i < secondNum.length(); i++)
			if (!Character.isDigit(secondNum.charAt(i)))
				return false;
		return true;
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to Heritage Connect-N!\n");
		System.out.print("Please enter N to start a new game or R to resume the game stored in currentGame.txt > ");
		char starter = input.next().charAt(0);
		while (starter != 'R' && starter != 'N') {
			System.out.print("ERROR! Please enter a valid key > ");
			starter = input.next().charAt(0);
		}
		if (starter == 'N') {
			System.out.print("Enter the number of rows on the game board > ");
			int rows = input.nextInt();
			while (!interfaceGame.validateNumRows(rows)) {
				System.out.print("Invalid entry, please re enter valid number of rows (between 4 and 12) > ");
				rows = input.nextInt();
			}

			interfaceGame.setNumRows(rows);
			System.out.print("Enter the number of columns on the game board > ");
			int cols = input.nextInt();
			while (!interfaceGame.validateNumCols(cols)) {
				System.out.print("ERROR! Please enter a valid number of columns (between 4 and 12) > ");
				cols = input.nextInt();
			}
			interfaceGame.setNumCols(cols);
			System.out.print("Enter the value for N, the number of checkers in a row for a win > ");
			int num = input.nextInt();
			while (!interfaceGame.validateN(num)) {
				System.out.print(
						"ERROR! Please enter a valid N that is between 3 and 8 and that is smaller than or equal to the number of rows and/or columns on the board > ");
				num = input.nextInt();
			}
			interfaceGame.setN(num);
			interfaceGame.newGame();
			System.out.print("Enter the name of Player 1, yellow > ");
			String name = input.next();
			interfaceGame.player1.setPlayerName(name);
			interfaceGame.player1.setCheckerColor('Y');
			System.out.print("Enter the name of player 2, red > ");
			name = input.next();
			interfaceGame.player2.setPlayerName(name);
			interfaceGame.player2.setCheckerColor('R');
		} // if('N')
		else {
			System.out.println("Restoring game from currentGame.txt.");
			interfaceGame.restoreGame();
		}
		System.out.println("Type Q at any time to exit the game, S to save the game or U to undo your last move.\n");
		while (interfaceGame.isGameOver() == 'C') {
			int row;
			int col;
			do {
				promptUser();
				String value = input.next();
				int numUndo = 0;
				while (value.charAt(0) != 'S' && value.charAt(0) != 'U' && value.charAt(0) != 'Q'
						&& !checkInput(value)) {
					System.out.println("ERROR! Invalid input, please re-enter valid input!");
					promptUser();
					value = input.next();
				}
				while (value.indexOf(',') == -1) {
					while (value.charAt(0) == 'S') {
						System.out.println("The current game state has been saved to currentGame.txt.");
						interfaceGame.saveGame();
						promptUser();
						value = input.next();
					}
					while (value.charAt(0) != 'S' && value.charAt(0) != 'U' && value.charAt(0) != 'Q') {
						System.out.println("ERROR! Invalid input, please re-enter valid input!");
						promptUser();
						value = input.next();
					}
					while (value.charAt(0) == 'U') {
						if (moves == 0)
							System.out.println(
									"ERROR! Invalid input! There is no previous move, there is nothing to undo.");
						else if (numUndo == 0) {
							interfaceGame.undoMove();
							System.out.println(interfaceGame.players[ConnectNGame.playerTurn].getPlayerName()
									+ ", your last move has been undone.");
							numUndo++;
						} else
							System.out.println("ERROR! You are only allowed to use one undo per turn.");
						promptUser();
						value = input.next();
					}
					if (value.charAt(0) == 'Q') {
						System.out.print(
								"Would you like to save the game to currentGame.txt before quitting (Yes or No)? ");
						String answer = input.next();
						if (answer.equalsIgnoreCase("Yes") || answer.charAt(0) == 'Y' || answer.charAt(0) == 'y') {
							System.out.println("Saving the game to currentGame.txt!");
							interfaceGame.saveGame();
						} else if (answer.equalsIgnoreCase("No") || answer.charAt(0) == 'N' || answer.charAt(0) == 'n')
							System.out.println("The game has not been saved.");
						else
							System.out.println("Invalid answer! Game has not been saved!");
						System.out.println("Thank you for playing Heritage Connect-N!");
						System.exit(-1);
					} // if(Q)
				} // while(!=cooridnates)
				while (value.charAt(0) != 'S' && value.charAt(0) != 'U' && value.charAt(0) != 'Q'
						&& !checkInput(value)) {
					System.out.println("ERROR! Invalid input, please re-enter valid input!");
					promptUser();
					value = input.next();
				}
				row = Integer.parseInt(value.substring(0, (value.indexOf(","))));
				col = Integer.parseInt(value.substring(value.indexOf(",") + 1));
				if (!interfaceGame.validRow(row))
					System.out.print("Invalid row number! Please try again!\n");
				else if (!interfaceGame.validCol(col))
					System.out.print("Invalid column number! Please try again!\n");
				else if (!interfaceGame.checkEmpty(row, col))
					System.out.print("Invalid move! That location has a checker! Please try again!\n");
				else if (!interfaceGame.validateMove(row, col))
					System.out.print(
							"Invalid move! You must place the checker on top of another checker! Please try again!\n");
			} while (!interfaceGame.validRow(row) || !interfaceGame.validCol(col)
					|| !interfaceGame.validateMove(row, col) || !interfaceGame.checkEmpty(row, col));// invalid
																										// cases
			interfaceGame.makeMove(row, col, ConnectNGame.playerTurn);
			interfaceGame.players[ConnectNGame.playerTurn].setLastMoveRow(row);
			interfaceGame.players[ConnectNGame.playerTurn].setLastMoveCol(col);
			ConnectNGame.switchPlayerTurn();
			moves++;
		} // while(isGameOver() == 'C')
		displayBoard();
		if (interfaceGame.isGameOver() == 'T')
			System.out.println("The board filled up, no one won! It's a tie!");
		else if (interfaceGame.isGameOver() == 'Y')
			System.out.println("Congratulations " + interfaceGame.player1.getPlayerName() + ", you won!");
		else if (interfaceGame.isGameOver() == 'R')
			System.out.println("Congratulations " + interfaceGame.player2.getPlayerName() + ", you won!");
		else
			System.out.println(interfaceGame.isGameOver());
	}// main()

}
