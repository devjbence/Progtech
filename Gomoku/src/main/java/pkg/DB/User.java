package pkg.DB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pkg.util.Logging;

/**
 * For storing user data from database.
 */
@Entity
@Table(name = "Users")
public class User {

	/**
	 * User id.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_sequence")
	@SequenceGenerator(name="user_sequence", sequenceName="user_sq", allocationSize=1)
	@Column(name = "USER_ID")
	private int user_id;
	/**
	 * Name of the user.
	 */
	@Column(name = "NAME")
	private String name;
	/**
	 * Password of the user.
	 */
	@Column(name = "PASSW")
	private String passw;
	
	/**
	 * Creates a user object.
	 * @param user_id stores the unique identification of the user
	 * @param name stores the name of the user
	 * @param passw stores the password of the user
	 */
	public User(int user_id, String name, String passw) {
		this.user_id = user_id;
		this.name = name;
		this.passw = passw;
		Logging.getLogger().info("User object created");
	}
	
	public User(){}

	public int getUser_id() {
		return user_id;
	}

	public String getName() {
		return name;
	}

	public String getPassw() {
		return passw;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}
	
	
}
