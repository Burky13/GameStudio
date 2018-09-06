package sk.tsystems.gamestudio.service.ratingService;

import sk.tsystems.gamestudio.entity.Rating;

public interface RatingService {
	void addOrUpdateRating(Rating rating);
	
	double getRating(String gameName);
}
