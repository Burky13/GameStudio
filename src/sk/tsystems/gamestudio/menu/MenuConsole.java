package sk.tsystems.gamestudio.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import sk.tsystems.gamestudio.game.GuessANumber.GuessnumberMain;
import sk.tsystems.gamestudio.game.mines.MinesMain;
import sk.tsystems.gamestudio.game.puzzle.PuzzleMain;

public class MenuConsole {
	private static GuessnumberMain guessANumberMain = new GuessnumberMain();
	private static MinesMain minesMain = new MinesMain();
	private static PuzzleMain puzzleMain = new PuzzleMain();
	
	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	private enum Option {
		GUESS_A_NUMBER, PUZZLE, MINES, EXIT
	};
	public static void main(String[] args) {
		run();
	
	}
	public static void run() {
		while (true) {
			switch (showMenu()) {
			case GUESS_A_NUMBER:
				guessANumberMain.guessANumber();
				break;
			case PUZZLE:
				puzzleMain.puzzle();
				break;
			case MINES:
				minesMain.mines();
				break;
			case EXIT:
				return;
			}
		}
	
	}
	
	private static String readLine() {
		// In JDK 6.0 and above Console class can be used
		// return System.console().readLine();

		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}
	
	private static Option showMenu() {
		System.out.println("Menu.");
		for (Option option : Option.values()) {
			System.out.printf("%d. %s%n", option.ordinal() + 1, option);
		}
		System.out.println("-----------------------------------------------");

		int selection = -1;
		do {
			System.out.println("Option: ");
			try {
				selection = Integer.parseInt(readLine());
			} catch (NumberFormatException e) {
				System.err.println("Invalid index, enter only numbers you can see in menu!");
			}
		} while (selection <= 0 || selection > Option.values().length);

		return Option.values()[selection - 1];
	}
}
