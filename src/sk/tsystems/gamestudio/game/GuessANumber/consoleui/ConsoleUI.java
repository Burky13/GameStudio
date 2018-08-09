package sk.tsystems.gamestudio.game.GuessANumber.consoleui;

import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.ScoreService;
import sk.tsystems.gamestudio.service.ScoreServiceJDBC;

public class ConsoleUI {
	private int maxValue;
	private int answear;
	private int guess;
	private int score;
	private ScoreService scoreService = new ScoreServiceJDBC();
	
	
	public ConsoleUI(int maxValue) {
		this.maxValue = maxValue;
	}

	public void play() {
		printScores();
		generateNumber(maxValue);
		processInput();
	}

	private void generateNumber(int maxValue) {
		Random random = new Random();
		answear = random.nextInt(maxValue);
	}

	private void processInput() {
		do {
			System.out.println("Guest the number from 0 to " + maxValue);

			Scanner scanner = new Scanner(System.in);
			String line = scanner.nextLine().trim();
			int guess = Integer.parseInt(line);
			score++;

			if (guess == answear) {
				System.out.println("You hit it! You won!");
				System.out.println("Your score is " + score + " guesses!");
				scoreService.addScore(new Score("GuessANumber",System.getProperty("user.name"), score, new Date()));
				System.exit(0);
			} else if (guess < answear)
				System.out.println("Wrong, try higher number!");
			else
				System.out.println("Wrong, try lower number!");
		} while (guess != answear);
	}
	private void printScores() {
		int index = 1;
		System.out.println("-----------------------------");
		System.out.println("No.  Player             Score");
		System.out.println("-----------------------------");
		for(Score score : scoreService.getBestScores("GuessANumber")) {
			System.out.printf("%3d. %-16s %5d\n", index, score.getPlayer(), score.getPoints());
			index++;
		}
		System.out.println("-----------------------------");
	}
	
}
