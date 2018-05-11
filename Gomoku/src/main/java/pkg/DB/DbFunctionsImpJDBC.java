package pkg.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pkg.util.Logging;
import pkg.util.Player;

/**
 * Class for put or retrieve date from the database.
 */
public class DbFunctionsImpJDBC implements IDbFunctions{
	
	/**
	 * Match object.
	 */
	private Match match;
	/**
	 * User object.
	 */
	private User user;
	/**
	 * Player object.
	 */
	private Player player;
	/**
	 * Database connection.
	 */
	private Connection connection;
	/**
	 * Connector object.
	 */
	private Connector co;
	
	/**
	 * Creates the object.
	 * Initialising the {@link Connector} object, and getting the {@code Connection} with it.
	 */
	public DbFunctionsImpJDBC()
	{
		co = new Connector();
		connection=co.getConnection();
	}
	
	/**
	 * Closes the connection.
	 */
	@Override
	public void closeConnection()
	{
		co.closeConnetion();
	}
	
		@Override
		public void saveMatch(int match_id,int user1,int user2,String filename)
		{
			match=new Match(match_id, user1, user2, filename);
		
			try {
				PreparedStatement ps= connection.prepareStatement("INSERT INTO matches values(?,?,?,?)");
				ps.setInt(1, match_id);
				ps.setInt(2, user1);
				ps.setInt(3, user2);
				ps.setString(4, filename);
				
				int n= ps.executeUpdate();
				
				Logging.getLogger().info("Saved match to database.");
				Logging.getLogger().info("Number of rows affected: {}", n);
				
			} catch (SQLException e) {
				Logging.getLogger().error(e.getSQLState());
			}
			
		}
		
		@Override
		public List<Match> loadMatches(int p1_id, int p2_id) {
			
			List<Match> matches = new ArrayList<Match>();
			int [] srt= new int[]{p1_id,p2_id};
			Arrays.sort(srt);
			
			try {
				PreparedStatement st= connection.prepareStatement("SELECT * from matches where user1=? and user2=?");
				st.setInt(1, srt[0]);
				st.setInt(2, srt[1]);
				
				ResultSet rs = st.executeQuery();
				
				while(rs.next())
				{
					matches.add(new Match(
							rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4)
							));
				}
				
				Logging.getLogger().info("Loaded from database");
				
			} catch (SQLException e) {
				Logging.getLogger().error(e.getSQLState());
			}
			
			return matches;
		}
		
		@Override
		public int getNextMatchId()
		{
			
			PreparedStatement ps;
			try {
				ps = connection.prepareStatement("select match_sq.NEXTVAL from dual");
			
				ResultSet rs = ps.executeQuery();
				if(rs.next())
				{
					Logging.getLogger().info("Id found: {}", rs.getInt(1));
					return rs.getInt(1);
				}
			
			} catch (SQLException e) {
				Logging.getLogger().error(e.getSQLState());
			}
			
			Logging.getLogger().warn("No Id found.");
			return 0;
		}
	
		@Override
		public int getIdByUsername(String username)
		{
			PreparedStatement ps;
			try {

				ps = connection.prepareStatement("select user_id from users where name =?");
				ps.setString(1, username);
				
				ResultSet rs = ps.executeQuery();

				if(rs.next())
				{
					Logging.getLogger().info("Id by username is: {}",rs.getInt(1));
					return rs.getInt(1);
				}
			
			} catch (SQLException e) {
				Logging.getLogger().info(e.getSQLState());
			}
			
			Logging.getLogger().warn("No id found by username");
			return 0;
		}
		
		@Override
		public String getUsernameById(int user_id)
		{
			PreparedStatement ps;
			try {

				ps = connection.prepareStatement("select name from users where user_id =?");
				ps.setInt(1, user_id);
				
				ResultSet rs = ps.executeQuery();

				if(rs.next())
				{
					Logging.getLogger().info("Username found by id: {}", rs.getString(1));
					return rs.getString(1);
				}
			
			} catch (SQLException e) {
				Logging.getLogger().info(e.getSQLState());
			}
			
			Logging.getLogger().warn("No username was found by that id");
			return "";
		}
		
		@Override
		public Player login(String username, String passw,char xOrO)
		{
			PreparedStatement ps;
			
			try {
				ps=connection.prepareStatement("Select * from users where name= ? and passw =?");
				ps.setString(1, username);
				ps.setString(2, passw);
				
				ResultSet rs=ps.executeQuery();
				
				if(rs.next())
				{
					//can login
					player = new Player();
					player.setUsername(username);
					player.setxOrO(xOrO);
				}				
				else
				{
					Logging.getLogger().warn("Can't login");
					return null;
				}
				
			} catch (SQLException e) {
				Logging.getLogger().error(e.getSQLState());
			}
			
			Logging.getLogger().info("Player: {} can log in", player.getUsername());
			return player;
		}

		@Override
		public Player signUp(String username, String passw,char xOrO)
		{
			//so if it is not in the databse
			if(getIdByUsername(username)==0 && username.length()<20 && passw.length()<20) 
			{
				PreparedStatement ps;
				
				try {
					ps=connection.prepareStatement("insert into users (user_id,name,passw) values(user_sq.nextval,?,?)");
					
					ps.setString(1, username);
					ps.setString(2, passw);
					ps.executeQuery();
					
					player= new Player();
					player.setUsername(username);
					player.setxOrO(xOrO);
					
					Logging.getLogger().info("Signed up {}", username);
					return player;
					
				} catch (SQLException e) {
					Logging.getLogger().error(e.getSQLState());
				}
			}
			
			Logging.getLogger().warn("User already in database, no need for signing up.");
			return null;
		}

		@Override
		public int getNextUserId() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void saveUser(int user_id, String username, String passw) {
			// TODO Auto-generated method stub
			
		}
		
}











