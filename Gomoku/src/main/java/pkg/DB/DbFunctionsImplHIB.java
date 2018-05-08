package pkg.DB;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pkg.util.Logging;
import pkg.util.Player;

/**
 * Creates a DAO object using Hibarnate.
 */
public class DbFunctionsImplHIB implements IDbFunctions{

	/**
	 * Main session.
	 */
	private Session session;
	/**
	 * For transactions.
	 */
	private Transaction tx;
	/**
	 * Match entity.
	 */
	private Match match;
	
	/**
	 * Creates the {@link DbFunctionsImplHIB} object.
	 */
	public DbFunctionsImplHIB(){}
	
	@Override
	public void closeConnection()
	{
		ConnectorEM.getSessionFactory().close();
	}
	
	@Override
	public void saveMatch(int match_id,int user1,int user2,Date date,String filename)
	{
		try {
			session= ConnectorEM.getSessionFactory().openSession();
			//tx= session.beginTransaction();
			session.getTransaction().begin();

			Match match = new Match();
			
			match.setUser1(user1);
			match.setUser2(user2);
			match.setMatch_date(date);
			match.setFilename(filename);

			session.save(match);
			session.getTransaction().commit();
			
			//tx.commit();
			Logging.getLogger().info("Match saved with the id: {}", match_id);
		} catch (Exception e) {
			Logging.getLogger().error("Matched not saved, error mess: {}",e.getMessage());
		}finally{
			session.close();		
		}
	}
	
	@Override
	public List<Match> loadMatches(int p1_id, int p2_id) {
		
		List<Match> matches = new ArrayList<Match>();
		int [] srt= new int[]{p1_id,p2_id};
		Arrays.sort(srt);
		
		try {
			session= ConnectorEM.getSessionFactory().openSession();
			tx= session.beginTransaction();

			Query query= session.createQuery("select m from Match m where m.user1 = ? and m.user2 = ?");
			query.setParameter(0, p1_id);
			query.setParameter(1, p2_id);
			
			matches= query.list();
			
			tx.commit();
		} catch (Exception e) {
			Logging.getLogger().error(e.getMessage());
		}finally{
			session.close();		
		}
		
		return matches;
	}
	
	@Override
	public int getIdByUsername(String username)
	{
		try {
			session= ConnectorEM.getSessionFactory().openSession();
			tx= session.beginTransaction();

			Query query= session.createQuery("select u from User u where u.name = ?");
			query.setParameter(0, username);
			
			User user=  (User) query.list().stream().findFirst().orElse(null);
			if(user != null)
			{
				return user.getUser_id();
			}
			tx.commit();
		} catch (Exception e) {
			Logging.getLogger().error(e.getMessage());
		}finally{
			session.close();		
		}
		
		Logging.getLogger().warn("No id found by that username ({})",username);
		return 0;
	}
	
	@Override
	public String getUsernameById(int user_id)
	{
		try {
			session= ConnectorEM.getSessionFactory().openSession();
			tx= session.beginTransaction();

			Query query= session.createQuery("select u from User u where u.user_id = ?");
			query.setParameter(0, user_id);
			
			User user=  (User) query.list().stream().findFirst().orElse(null);
			if(user != null)
			{
				return user.getName();
			}
			tx.commit();
		} catch (Exception e) {
			Logging.getLogger().error(e.getMessage());
		}finally{
			session.close();		
		}
		
		Logging.getLogger().warn("No username found by that id ({})",user_id);
		return "";
	}
	
	@Override
	public Player login(String username, String passw,char xOrO)
	{
		try {
			session= ConnectorEM.getSessionFactory().openSession();
			tx= session.beginTransaction();

			Query query= session.createQuery("select u from User u where u.name = ? and u.passw = ?");
			query.setParameter(0, username);
			query.setParameter(1, passw);
			
			User user=  (User) query.list().stream().findFirst().orElse(null);
			if(user != null)
			{
				Player player = new Player();
				player.setUsername(username);
				player.setxOrO(xOrO);
				
				return player;
			}
			tx.commit();
		} catch (Exception e) {
			Logging.getLogger().error(e.getMessage());
		}finally{
			session.close();		
		}
		
		Logging.getLogger().warn("Cannot login ({})",username);
		return null;
	}
	
	@Override
	public Player signUp(String username, String passw,char xOrO)
	{
		//so if it is not in the databse
		if(getIdByUsername(username)==0 && username.length()<20 && passw.length()<20) 
		{
			try {
				session= ConnectorEM.getSessionFactory().openSession();
				session.getTransaction().begin();

				User user = new User();
				user.setName(username);
				user.setPassw(passw);
				
				session.save(user);
				session.getTransaction().commit();
				
				Player player = new Player();
				player.setUsername(username);
				player.setxOrO(xOrO);
				
				return player;
				
			} catch (Exception e) {
				Logging.getLogger().error(e.getMessage());
			}finally{
				session.close();		
			}
		}
		
		Logging.getLogger().warn("User already in database, no need for signing up.");
		return null;
	}

	@Override
	public int getNextMatchId() { //+3 from the last one
		try {
			session= ConnectorEM.getSessionFactory().openSession();
			session.getTransaction().begin();

			Query query= session.createQuery("select m.match_id from Match m order by m.match_id desc");
			int match_id=   (int) query.list().get(0);
			
			if(match_id !=0)
			{
				Logging.getLogger().info("Next match id: {}",match_id);
				return match_id+3;
			}
			
		} catch (Exception e) {
			Logging.getLogger().error(e.getMessage());
		}finally{
			session.close();		
		}
		
		Logging.getLogger().warn("No match id");
		return 10;
	}
	
}
