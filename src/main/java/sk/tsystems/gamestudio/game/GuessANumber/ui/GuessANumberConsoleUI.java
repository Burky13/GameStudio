package sk.tsystems.gamestudio.game.GuessANumber.ui;

import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.Game;
import sk.tsystems.gamestudio.service.ScoreService.ScoreService;
import sk.tsystems.gamestudio.service.ScoreService.ScoreServiceJDBC;

public class GuessANumberConsoleUI implements Game {
	private int maxValue;
	private int answear;
	private int guess;
	private int score;
	
	@Autowired
	private ScoreService scoreService;

	public GuessANumberConsoleUI(int maxValue) {
		this.maxValue = maxValue;
	}

	private void generateNumber(int maxValue) {
		Random random = new Random();
		answear = random.nextInt(maxValue);
	}

	private void processInput() {
		do {
			System.out.println("Guest the number from 0 to " + maxValue);

			Scanner scanner = new Scanner(System.in);
			String line = scanner.nextLine().trim().toUpperCase();
			
			if ("X".equals(line))
				System.exit(0);
			
			int guess = Integer.parseInt(line);
			score++;

			if (guess == answear) {
				System.out.println("You hit it! You won!");
				System.out.println("Your score is " + score + " guesses!");
				scoreService.addScore(new Score("GuessANumber", System.getProperty("user.name"), score, new Date()));
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
		for (Score score : scoreService.getBestScores("GuessANumber")) {
			System.out.printf("%3d. %-16s %5d\n", index, score.getUsername(), score.getPoints());
			index++;
		}
		System.out.println("-----------------------------");
	}

	@Override
	public String getGame() {
		return "GuessANumber";
	}

	@Override
	public void play() {
		printScores();
		generateNumber(maxValue);
		processInput();
	}
}
