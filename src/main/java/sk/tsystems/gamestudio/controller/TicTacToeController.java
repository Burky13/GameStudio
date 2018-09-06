package sk.tsystems.gamestudio.controller;

import java.util.Formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.game.ticTacToe.Field;
import sk.tsystems.gamestudio.game.ticTacToe.GameState;
import sk.tsystems.gamestudio.game.ticTacToe.Player1;
import sk.tsystems.gamestudio.game.ticTacToe.Player2;
import sk.tsystems.gamestudio.game.ticTacToe.Tile;
import sk.tsystems.gamestudio.service.ScoreService.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION) // zabezpeci, aby pri otvoreni inkognito okna nebol prihlaseny ten isty
											// pouzivatel
public class TicTacToeController {

	private Field field = new Field(15, 15);
	
	@Autowired
	private ScoreService scoreService;

	@Autowired
	private UserController userController;
	
	@RequestMapping("/tictactoeNew")
	public String startNewGame() {
		field = new Field(15, 15);
		return "redirect:/tictactoe";
	}

	@RequestMapping("/tictactoe")
	public String ticTacToe(@RequestParam(name = "row", required = false) Integer row,
			@RequestParam(name = "column", required = false) Integer column, Model model) {
		String message = null;
		if (field.getState() == GameState.PLAYING && row != null && column != null) {
			field.markTile(row, column);
			field.checkField(row, column);
			if (field.getState() == GameState.PLAYER1WON) {
				message = "Player 1 won the game!";
			} else if (field.getState() == GameState.PLAYER2WON) {
				message = "Player 2 won the game!";
			} else if(field.getState() == GameState.DRAW){
				message = "It's a draw, nobody won!";
			}
		}
		model.addAttribute("tictactoeMessage", message);
		model.addAttribute("turn", field.isPlayer1Turn());
		return "tictactoe";
	}

	public String getHTMLField() {
		Formatter sb = new Formatter();
		sb.format("<table class='tictactoe_field'>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			sb.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				if (tile instanceof Player1) {
					sb.format("<td>\n");
					sb.format("<a href='/tictactoe?row=%d&column=%d'>", row, column);
					sb.format('X' + "</a></td>");
				} else if (tile instanceof Player2) {
					sb.format("<td>\n");
					sb.format("<a href='/tictactoe?row=%d&column=%d'>", row, column);
					sb.format('O' + "</a></td>");
				} else {
					sb.format("<td>\n");
				sb.format("<a href='/tictactoe?row=%d&column=%d'>", row, column);
				sb.format("-" + "</a></td>");
				}
			}
		}
		sb.format("</table>\n");
		return sb.toString();
	}

	public String getGameState() {
		return field.getState().name();
	}
}
