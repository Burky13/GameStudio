package sk.tsystems.gamestudio.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.service.commentService.CommentService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserController userController;

	@RequestMapping("/comment")
	public String addComment(Comment comment) {
		if (comment.getText() != null && !"anonymous".equals(userController.getLoggedUsername())) {
			comment.setUserName(userController.getLoggedUsername());
			comment.setCommented_on(new Date());
			commentService.addComment(comment);
		}
		return "redirect:/" + comment.getGame();
	}
}
