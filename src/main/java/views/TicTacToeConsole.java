package views;

import java.util.Scanner;
import model.TicTacToeGame;

public class TicTacToeConsole {
	
	public static void main(String args[]) {
		TicTacToeGame game = new TicTacToeGame();
		game.startNewGame();
		
		Scanner scanner = new Scanner(System.in);
		
		while (game.stillRunning()) {
			System.out.print("Enter row and Column: ");
			String input = scanner.nextLine();
			
			String[] rowCol = input.split(" ");
			int row = Integer.parseInt(rowCol[0]);
			int col = Integer.parseInt(rowCol[1]);
			game.humanMove(row, col, false);
			System.out.println(game);
			System.out.println();
		}
		
		if (game.tied()) {
			System.out.println("Tie");
		}
		else if (game.didWin('X')) {
			System.out.println("X wins");
		}
		else if (game.didWin('O')) {
			System.out.println("O wins");
		}
	}
}
