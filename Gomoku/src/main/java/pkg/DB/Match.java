package pkg.DB;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	 * Date of the match.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "MATCH_DATE")
	private Date match_date;
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
	 * @param match_date stores the date of the match
	 * @param filename stores the name of the file, where the we saved
	 */
	public Match(int match_id, int user1, int user2, Date match_date, String filename) {
		
		this.match_id = match_id;
		this.user1 = user1;
		this.user2 = user2;
		this.match_date = match_date;
		this.filename = filename;
		Logging.getLogger().info("Match object created with id: {}",match_id);
	}
	
	public Match(){}
	
	/**
	 * Get the file name.
	 * @return filename name of the file
	 */
	public String getFilename()
	{
		return filename;
	}

	public int getMatch_id() {
		return match_id;
	}

	public int getUser1() {
		return user1;
	}

	public int getUser2() {
		return user2;
	}

	public Date getMatch_date() {
		return match_date;
	}

	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}

	public void setUser1(int user1) {
		this.user1 = user1;
	}

	public void setUser2(int user2) {
		this.user2 = user2;
	}

	public void setMatch_date(Date match_date) {
		this.match_date = match_date;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
