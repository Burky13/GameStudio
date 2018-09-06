package sk.tsystems.gamestudio.menu;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import sk.tsystems.gamestudio.game.Game;

public class MenuConsole {
//automaticky vlozi vsetky triedy, ktore su zavedene ako Bean a implementuju Game rozhranie 

	@Autowired
	private Game[] games;
	
//	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

//	private enum Option {
//		GUESS_A_NUMBER, PUZZLE, MINES, EXIT
//	};

	public void run() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			try {
				System.out.println("Choose a game:");
				System.out.println("0. Exit gamestudio");
				for (int i = 0; i < games.length; i++) {
					System.out.println((i + 1) +". "+ games[i].getGame());
				}
				
				int input = Integer.parseInt(scanner.nextLine());
				if (input == 0) {
					return; //ukonci cely run(), teda aj while cyklus
				}
				if (input > 0 && input <= games.length) {
					games[input-1].play();
				} else {
					System.out.println("Wrong input, try again.");
				}
			} catch (Exception ex) {
				System.out.println("Error: "+ex.getMessage());
			}
		}
	}

			
//			switch (showMenu()) {
//			case GUESS_A_NUMBER:
//				guessANumberConsoleUI.play();
//				break;
//			case PUZZLE:
//				puzzleConsoleUI.play();
//				break;
//			case MINES:
//				minesConsoleUI.play();
//				break;
//			case EXIT:
//				return;
//			}
//		}
	
//	}
	
//	private String readLine() {
//		// In JDK 6.0 and above Console class can be used
//		// return System.console().readLine();
//
//		try {
//			return input.readLine();
//		} catch (IOException e) {
//			return null;
//		}
//	}
//	
//	private void printMenu() {
//		System.out.println("0. Exit");
//		System.out.println("Menu.");
//		
//		for (int i = 0; i < games.length; i++) {
//			System.out.println((i+1) + ". " + games[i].getGame());
//		}
//		
//	}
	
	
//	private Option showMenu() {
//		System.out.println("Menu.");
//		for (Option option : Option.values()) {
//			System.out.printf("%d. %s%n", option.ordinal() + 1, option);
//		}
//		System.out.println("-----------------------------------------------");
//
//		int selection = -1;
//		do {
//			System.out.println("Option: ");
//			try {
//				selection = Integer.parseInt(readLine());
//			} catch (NumberFormatException e) {
//				System.err.println("Invalid index, enter only numbers you can see in menu!");
//			}
//		} while (selection <= 0 || selection > Option.values().length);
//
//		return Option.values()[selection - 1];
//	}
}
