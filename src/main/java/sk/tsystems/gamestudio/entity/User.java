package sk.tsystems.gamestudio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "web_user") //zmenim meno tabulky, aby sa nevolala user, lebo v postres user je klucove slovo
public class User {
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String username;
	private String password;
	@Transient
	private String repeatPassword;
	
	@SuppressWarnings("unused")
	private User() { //prazdny konstruktor umozni vytvorit objekt JPAcku pre usera z databazy (jeden riadok databazy)
	}
	
	public User(String username, String password, String repeatPassword) {
		super();
		this.username = username;
		this.password = password;
		this.repeatPassword = repeatPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	public boolean validatepassword() {
		return password.equals(repeatPassword);
	}
	
}
