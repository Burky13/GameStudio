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
import sk.tsystems.gamestudio.game.mines.core.Clue;
import sk.tsystems.gamestudio.game.mines.core.Field;
import sk.tsystems.gamestudio.game.mines.core.GameState;
import sk.tsystems.gamestudio.game.mines.core.Mine;
import sk.tsystems.gamestudio.game.mines.core.Tile;
import sk.tsystems.gamestudio.service.ScoreService.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesController {

	private enum Difficulty {
		SMALL, MEDIUM, LARGE;
	}

	private Difficulty difficulty = Difficulty.MEDIUM;

	private static final String DB_GAME_NAME = "mines";

	private Field field = new Field(5, 5, 4);

	private boolean marking = false;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private UserController userController;

	@RequestMapping("/minesNew")
	public String startNewGame() {
		field = new Field(5, 5, 5);
		return "redirect:/mines";
	}

	@RequestMapping("/minesChangeMarking")
	public String changeMarking() {
		marking = !marking;
		return "redirect:/mines";
	}

	@RequestMapping("/minesChangeDifficulty")
	public String minesChangeDifficulty() {
		switch (difficulty) {
		case SMALL:
			difficulty = Difficulty.MEDIUM;
			field = new Field(5, 5, 5);
			break;
		case MEDIUM:
			difficulty = Difficulty.LARGE;
			field = new Field(9, 9, 11);
			break;
		case LARGE:
			difficulty = Difficulty.SMALL;
			field = new Field(15, 15, 30);
			break;
		}
		return "redirect:/mines";
	}

	@RequestMapping("/mines")
	public String mines(@RequestParam(name = "row", required = false) Integer r,
			@RequestParam(name = "column", required = false) Integer c, Model model) {

		if (field.getState() == GameState.PLAYING && r != null && c != null) {
			if (marking) {
				field.markTile(r, c);
			} else {
				field.openTile(r, c);
				if (field.getState() == GameState.SOLVED) {
					scoreService.addScore(
							new Score(DB_GAME_NAME, userController.getLoggedUsername(), field.getScore(), new Date()));
				}
			}
		}
		setModel(model);
		return "mines";
	}

	private void setModel(Model model) {
		model.addAttribute("marking", marking);
		model.addAttribute("scores", scoreService.getBestScores(DB_GAME_NAME));
		// model.addAttribute("field",field); //potrebne, len ak nechceme pouzit
		// getHTMLfield() metodu
	}

	public String getGameState() {
		return field.getState().name();
	}

	public String getHTMLField() {
		Formatter sb = new Formatter();
		sb.format("<table class='mines_field'>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			sb.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				sb.format("<td>\n");
				sb.format("<a href='/mines?row=%d&column=%d'>", row, column);
				sb.format("<img src='/images/mines/" + getImageName(tile) + ".png'>\n");
				sb.format("</a>");
			}
		}
		sb.format("</table>\n");
		return sb.toString();
	}

	public String getImageName(Tile tile) {
		switch (tile.getState()) {
		case CLOSED:
			return "closed";
		case MARKED:
			return "marked";
		case OPENED:
			if (tile instanceof Mine)
				return "mine";
			else
				return "open" + ((Clue) tile).getValue();
		}
		throw new IllegalArgumentException("Uns. tile state " + tile.getState());
	}

}
