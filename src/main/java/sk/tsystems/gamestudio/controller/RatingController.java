package sk.tsystems.gamestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.service.ratingService.RatingService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private UserController userController;
	
	@RequestMapping("/rating")
	public String averageRating(Rating rating) {
		if(rating.getUserRating() != 0.0 && !"anonymous".equals(userController.getLoggedUsername())) {
		rating.setUserName(userController.getLoggedUsername());
		ratingService.addOrUpdateRating(rating);
		return "redirect:/" + rating.getGame();
		}
		return "redirect:/";
	}
}
