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
 * Match entity class.
 */
@Entity
@Table(name = "Matches")
public class Match {

	/**
	 * Match id.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="match_sequence")
	@SequenceGenerator(name="match_sequence", sequenceName="match_sq", allocationSize=1)
	@Column(name = "MATCH_ID")
	private int match_id;
	/**
	 * User user #1"s id.
	 */
	@Column(name = "USER1")
	private int user1;
	/**
	 * User #2's id.
	 */
	@Column(name = "USER2")
	private int user2;
	/**
	 * Name of the file.
	 */
	@Column(name = "FILE_NAME")
	private String filename;
	
	/**
	 * Creates the match object.
	 * 
	 * @param match_id stores the unique identification of the match
	 * @param user1 stores the unique identification of user1
	 * @param user2 stores the unique identification of user2
	 * @param filename stores the name of the file, where the we saved
	 */
	public Match(int match_id, int user1, int user2,  String filename) {
		
		this.match_id = match_id;
		this.user1 = user1;
		this.user2 = user2;
		this.filename = filename;
		Logging.getLogger().info("Match object created with id: {}",match_id);
	}
	
	/**
	 * Creates the match object.
	 */
	public Match(){}
	
	/**
	 * Get the file name.
	 * @return filename name of the file
	 */
	public String getFilename()
	{
		return filename;
	}

	/**
	 * Getter for user id.
	 * @return match_id
	 */
	public int getMatch_id() {
		return match_id;
	}

	/**
	 * Getter for user1's id.
	 * @return user1
	 */
	public int getUser1() {
		return user1;
	}

	/**
	 * Getter for user2's id.
	 * @return user2
	 */
	public int getUser2() {
		return user2;
	}

	/**
	 * Setter for match id.
	 * @param match_id to set
	 */
	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}

	/**
	 * Setter for user1's id.
	 * @param user1 id to set
	 */
	public void setUser1(int user1) {
		this.user1 = user1;
	}

	/**
	 * Setter for user2's id.
	 * @param user2 id to set
	 */
	public void setUser2(int user2) {
		this.user2 = user2;
	}

	/**
	 * Setter for file name.
	 * @param filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
