package pkg.DB;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pkg.util.Logging;
import pkg.util.Player;

/**
 * Contains the DAO functions. Suitable for Hibarnate and also for JDBC api.
 */
public interface IDbFunctions {

	/**
	 * Closes the connection.
	 */
	public void closeConnection();

	/**
	 * Saves a user into database.
	 * 
	 * @param user_id id of the new user
	 * @param username user name for new user
	 * @param passw password for the new user
	 */
	public void saveUser(int user_id, String username, String passw);
	
	/**
	 * Saves the match into the database.
	 * 
	 * @param match_id
	 *            id of the match
	 * @param user1
	 *            id of user1
	 * @param user2
	 *            id of user2
	 * @param filename
	 *            this will be the name of the file, that stores the current
	 *            game state.
	 */
	public void saveMatch(int match_id, int user1, int user2, String filename);

	/**
	 * Check if there is any saved game. If there is/are, then the user can
	 * choose the one which will be loaded into the game.
	 * 
	 * @param p1_id
	 *            player1'id
	 * @param p2_id
	 *            player2'id
	 * @return {@code List<Match>}
	 */
	public List<Match> loadMatches(int p1_id, int p2_id);

	/**
	 * Returns the next id for a user to save.
	 * 
	 * @return user_id
	 */
	public int getNextUserId();

	/**
	 * Returns the next id for saving matches.
	 * 
	 * @return match_id
	 */
	public int getNextMatchId();

	/**
	 * Return the id to a given user name.
	 * 
	 * @param username
	 *            to check in database
	 * @return user_id or 0 if not found
	 */
	public int getIdByUsername(String username);

	/**
	 * Returns the user name by a given id.
	 * 
	 * @param user_id
	 *            for search in database.
	 * @return user name or empty string if not found
	 */
	public String getUsernameById(int user_id);

	/**
	 * If there is a record in the matches table, where the user name and the
	 * password matches, then it returns a configured player object.
	 * 
	 * @param username
	 *            to find in match table
	 * @param passw
	 *            to find in match table
	 * @param xOrO
	 *            the X or O character for the player object's configuration.
	 * @return the configured player object, or a null, if the database does not
	 *         have the record.
	 */
	public Player login(String username, String passw, char xOrO);

	/**
	 * If there is no record in the matches table, where the user name and the
	 * password matches, then put it into the database and return a configured
	 * player object.
	 * 
	 * @param username
	 *            to find in match table or put into it
	 * @param passw
	 *            to find in match table or put into it
	 * @param xOrO
	 *            the X or O character for the player object's configuration.
	 * @return the configured player object, or a null, if the database has the
	 *         record.
	 */
	public Player signUp(String username, String passw, char xOrO);

}
