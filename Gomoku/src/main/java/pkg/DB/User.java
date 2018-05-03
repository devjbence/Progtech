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
	 * Password for the user.
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
	
	/**
	 * Creates the User object.
	 */
	public User(){}

	/**
	 * Getter for user id.
	 * @return {@link user_id}
	 */
	public int getUser_id() {
		return user_id;
	}

	/**
	 * Getter for user name.
	 * @return {@link name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for user password.
	 * @return {@link passw}
	 */
	public String getPassw() {
		return passw;
	}

	/**
	 * Setter for user id.
	 * @param user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	/**
	 * Setter for user name.
	 * @param name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter for user password.
	 * @param passw to set
	 */
	public void setPassw(String passw) {
		this.passw = passw;
	}
	
	
}
