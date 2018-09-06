package sk.tsystems.gamestudio.service.commentService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.service.ScoreService.ScoreException;

public class CommentServiceJPA implements CommentService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void addComment(Comment comment) {
		try {
			entityManager.persist(comment);
		} catch (Exception e) {
			throw new ScoreException("Cannot save comment", e);
		}		
	}
	
//	@Transactional
//	public void deleteComment(Comment comment) {
//		comment = entityManager.merge(comment);
//		entityManager.remove(comment);
//	}

	@Override
	public List<Comment> getComments(String gameName) {
		try {
			return entityManager
					.createQuery("select c from Comment c where c.game = :gameName order by c.commented_on desc", Comment.class)
					.setParameter("gameName", gameName)
					.setMaxResults(10)
					.getResultList();
		} catch (Exception e) {
			throw new ScoreException("Error getting comment for game " + gameName, e);
		}
	}
}
 