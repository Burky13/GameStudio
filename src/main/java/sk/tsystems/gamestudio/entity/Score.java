package sk.tsystems.gamestudio.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Score implements Serializable {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String game;
	private String username;
	private int points;
	private Date played_on;

	public Score() {
	}

	public Score(String game, String username, int points, Date played_on) {
		this.game=  game;
		this.username = username;
		this.points = points;
		this.played_on = played_on;
	}

	public String getGame() {
		return game;
	}
	
	public void setGame(String game) {
		this.game = game;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Date getPlayedOn() {
		return played_on;
	}

	public void setPlayedOn(Date played_on) {
		this.played_on = played_on;
	}

	@Override
	public String toString() {
		return "Score{" +
        "game='" + game + '\'' +
        ", username='" + username + '\'' +
        ", points=" + points +
        ", playedOn=" + played_on +
        '}';
	}
}
