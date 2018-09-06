package sk.tsystems.gamestudio.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String text;
	private Date commented_on;
	private String userName;
	private String game;
	
	@SuppressWarnings("unused")
	private Comment() {
		
	}

	public Comment(String text, Date commented_on, String userName, String game) {
		super();
		this.text = text;
		this.commented_on = commented_on;
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCommented_on() {
		return commented_on;
	}

	public void setCommented_on(Date commented_on) {
		this.commented_on = commented_on;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}
	
}
