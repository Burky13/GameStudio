package sk.tsystems.gamestudio.game.mines.ui;

import java.util.Date;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.Game;
import sk.tsystems.gamestudio.game.mines.core.Clue;
import sk.tsystems.gamestudio.game.mines.core.Field;
import sk.tsystems.gamestudio.game.mines.core.GameState;
import sk.tsystems.gamestudio.game.mines.core.Mine;
import sk.tsystems.gamestudio.game.mines.core.Tile;
import sk.tsystems.gamestudio.service.ScoreService.ScoreService;
import sk.tsystems.gamestudio.service.ScoreService.ScoreServiceJDBC;

public class MinesConsoleUI implements Game {
	private Field field;
	
	@Autowired
	private ScoreService scoreService;
	
	private int score;
	private long time;
	private long initialTime;

	public MinesConsoleUI() {
	}

	public void play() {
		this.field = new Field(9, 9, 1);
		printScores();
		initialTime = System.currentTimeMillis();

		do {
			print();
			processInput();
		} while (field.getState() == GameState.PLAYING);

		print();

		if (field.getState() == GameState.SOLVED) {
			System.out.println("You won! CONGRATULATION!!!");
			time = (System.currentTimeMillis() - initialTime) / 1000;
			score = 500 - (int) time;
			System.out.println("You solved the game in " + (time) + " sec. Score = " + score);
			scoreService.addScore(new Score("Mines", System.getProperty("user.name"), score, new Date()));
		} else if (field.getState() == GameState.FAILED) {
			System.out.println("You found a mine! You lost! Better luck next time, try it again.");
		}
	}

	private void print() {
		printFieldHeader();

		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.print((char) (row + 'A'));
			for (int column = 0; column < field.getColumnCount(); column++) {
				System.out.print(" ");
				printTile(row, column);
			}
			System.out.println();
		}
	}

	private void printFieldHeader() {
		System.out.print(" ");
		for (int column = 0; column < field.getColumnCount(); column++) {
			System.out.print(" ");
			System.out.print(column + 1);
		}
		System.out.println();
	}

	private void printTile(int row, int column) {
		Tile tile = field.getTile(row, column);
		switch (tile.getState()) {
		case OPENED:
			if (tile instanceof Mine)
				System.out.print("X");
			else if (tile instanceof Clue)
				System.out.print(((Clue) tile).getValue());
			break;
		case CLOSED:
			System.out.print("-");
			break;
		case MARKED:
			System.out.print("M");
			break;
		}
	}

	private void processInput() {
		System.out.print("Enter input: ");
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine().toUpperCase().trim();

		if ("X".equals(line))
			System.exit(0);
		if (line.matches("[OM][A-I][1-9]")) {
			int row = line.charAt(1) - 'A';
			int column = line.charAt(2) - '1';
			if (line.charAt(0) == 'O')
				field.openTile(row, column);
			else
				field.markTile(row, column);
		} else
			System.out.println("Invalid input!");
	}

	private void printScores() {
		int index = 1;
		System.out.println("-----------------------------");
		System.out.println("No.  Player             Score");
		System.out.println("-----------------------------");
		for (Score score : scoreService.getBestScores("Mines")) {
			System.out.printf("%3d. %-16s %5d\n", index, score.getUsername(), score.getPoints());
			index++;
		}
		System.out.println("-----------------------------");
	}

	@Override
	public String getGame() {
		return "Mines";
	}
	
}
