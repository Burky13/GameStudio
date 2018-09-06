package sk.tsystems.gamestudio.service.ratingService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.service.ScoreService.ScoreException;

public class RatingServiceJPA implements RatingService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void addOrUpdateRating(Rating rating) {
		Rating r = null;
		try {
			r = entityManager
					.createQuery("select r from Rating r where r.game = :gameName and r.userName = :userName", Rating.class)
					.setParameter("gameName", rating.getGame())
					.setParameter("userName", rating.getUserName())
					.getSingleResult();
		} catch (NoResultException e) {
			
		}
		if(r != null) {
			r.setUserRating(rating.getUserRating());			
		}else if(r == null){
			entityManager.persist(rating);
		}
	}

	@Override
	public double getRating(String gameName) {
		try {
			Double value = entityManager
					.createQuery("select avg(userRating) from Rating r where r.game = :gameName", Double.class)
					.setParameter("gameName", gameName)
					.getSingleResult();
			if(value != null) {
				return value;
			}
			else return 0.0;
		} catch (NoResultException e) {
			throw new ScoreException("Cannot get average rating for game " + gameName, e);
		}
	}

}
