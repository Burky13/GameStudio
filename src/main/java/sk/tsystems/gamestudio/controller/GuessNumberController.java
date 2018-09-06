package sk.tsystems.gamestudio.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.GuessANumber.ui.GuessNumberLogic;
import sk.tsystems.gamestudio.service.ScoreService.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GuessNumberController {

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private UserController userController;

	private static final String DB_GAME_NAME = "guess";
	private int score;
	private GuessNumberLogic logic = new GuessNumberLogic(100);
	private String message;
	private int guessRange = 100;
	private String guessRangeMessage;
	

	@RequestMapping("/guessNew100")
	public String startNewGame100() {
		guessRange = 100;
		logic = new GuessNumberLogic(guessRange);
		message = null;
		guessRangeMessage = "Guess from 0 to " + Integer.toString(guessRange) + ".";
		return "redirect:/guess";
	}
	
	@RequestMapping("/guessNew1000")
	public String startNewGame1000() {
		guessRange = 1000;
		logic = new GuessNumberLogic(guessRange);
		message = null;
		guessRangeMessage = "Guess from 0 to " + Integer.toString(guessRange) +  ".";
		return "redirect:/guess";
	}
	
	@RequestMapping("/guessNew10000")
	public String startNewGame10000() {
		guessRange = 10000;
		logic = new GuessNumberLogic(guessRange);
		message = null;
		guessRangeMessage = "Guess from 0 to " + Integer.toString(guessRange) +  ".";
		return "redirect:/guess";
	}

	@RequestMapping("/guess")
	public String guessNumber(@RequestParam(name = "guess", required = false) Integer guess, Model model) {
		message = null;
		guessRangeMessage = "Guess from 0 to " + Integer.toString(guessRange) +  ".";
		if (guess != null) {
			if (guess == logic.getGeneratedNumber()) {
				message = "You hit it, congratulation!";
				scoreService.addScore(new Score(DB_GAME_NAME, userController.getLoggedUsername(), score, new Date()));
			} else if(guess < logic.getGeneratedNumber()){
				message = "Wrong, try higher number!";
				score++;
			}
			else {
				message = "Wrong, try lower number!";
				score++;
			}
		}
		setModel(model);
		return "guess";
	}

	private void setModel(Model model) {
		model.addAttribute("guessMessage", message);
		model.addAttribute("scores", scoreService.getBestScores(DB_GAME_NAME));
		model.addAttribute("guessRangeMessage", guessRangeMessage);
	}


}
