package sk.tsystems.gamestudio.controller;

import java.util.Date;
import java.util.Formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.puzzle.core.Field;
import sk.tsystems.gamestudio.game.puzzle.core.Tile;
import sk.tsystems.gamestudio.service.ScoreService.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PuzzleController {

	private static final String DB_GAME_NAME = "puzzle";

	private Field field = new Field(3, 3);

	private enum Difficulty {
		SMALL, MEDIUM, LARGE;
	}

	private Difficulty difficulty = Difficulty.MEDIUM;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private UserController userController;

	@RequestMapping("/puzzleNew")
	public String startNewGame() {
		field = new Field(3, 3);
		return "redirect:/puzzle";
	}

	@RequestMapping("/puzzle")
	public String puzzle(@RequestParam(name = "value", required = false) Integer val, Model model) {
		if (!field.isSolved() && val != null) {
			field.moveTile(val);
			if (field.isSolved()) {
				scoreService.addScore(
						new Score(DB_GAME_NAME, userController.getLoggedUsername(), field.getScore(), new Date()));
			}
		}

		setModel(model);
		return "puzzle";
	}

	@RequestMapping("/puzzleChangeDifficulty")
	public String puzzleChangeDifficulty() {
		switch (difficulty) {
		case SMALL:
			difficulty = Difficulty.MEDIUM;
			field = new Field(3, 3);
			break;
		case MEDIUM:
			difficulty = Difficulty.LARGE;
			field = new Field(4, 4);
			break;
		case LARGE:
			difficulty = Difficulty.SMALL;
			field = new Field(5, 5);
			break;
		}
		return "redirect:/puzzle";
	}

	public String getGameState() {
		if (field.isSolved()) {
			return "You won!";
		} else
			return "Playing";
	}

	private void setModel(Model model) {
		model.addAttribute("scores", scoreService.getBestScores(DB_GAME_NAME));
		// model.addAttribute("field",field); //potrebne, len ak nechceme pouzit
		// getHTMLfield() metodu
	}

	public String getHTMLField() {
		Formatter sb = new Formatter();
		sb.format("<table class='puzzle_field'>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			sb.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				if (tile != null) {
					int val = tile.getValue();
					sb.format("<td>\n");
					sb.format("<a href='/puzzle?value=%d'>", val);
					sb.format(tile.getValue() + "</a></td>");

				} else {
					sb.format("<td></td>\n");
				}
			}

		}
		sb.format("</table>\n");
		return sb.toString();
	}
}
