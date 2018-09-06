package sk.tsystems.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Rating {

	@Id
	@GeneratedValue
	private int id;
	private String game;
	private String userName;
	private double userRating;
	
	@SuppressWarnings("unused")
	private Rating() {
			
	}
	
	public Rating(int id, String game, String userName, double userRating) {
		super();
		this.id = id;
		this.game = game;
		this.userName = userName;
		this.userRating = userRating;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getUserRating() {
		return userRating;
	}

	public void setUserRating(double userRating) {
		this.userRating = userRating;
	}
}
