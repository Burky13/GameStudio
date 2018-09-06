package sk.tsystems.gamestudio.service.commentService;

import java.util.List;

import sk.tsystems.gamestudio.entity.Comment;

public interface CommentService {

	void addComment(Comment comment);
	
	List<Comment> getComments(String gameName);
}
