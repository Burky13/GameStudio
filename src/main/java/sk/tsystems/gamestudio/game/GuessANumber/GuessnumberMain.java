package sk.tsystems.gamestudio.game.GuessANumber;

import sk.tsystems.gamestudio.game.GuessANumber.ui.GuessANumberConsoleUI;

public class GuessnumberMain {

	public void guessANumber() {
		GuessANumberConsoleUI ui = new GuessANumberConsoleUI(100);
		
		ui.play();
	}

}
