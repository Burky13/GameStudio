package sk.tsystems.gamestudio.game.GuessANumber.ui;

import java.util.Random;

public class GuessNumberLogic {
	private int generatedNumber;
	
	public GuessNumberLogic(int maxValue) {
		generateNumber(maxValue);
	}

	public void generateNumber(int maxValue) {
		Random random = new Random();
		generatedNumber =  random.nextInt(maxValue);
	}

	public int getGeneratedNumber() {
		return generatedNumber;
	}
}
