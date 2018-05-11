package pkg.DB;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.media.jfxmedia.logging.Logger;

import pkg.Files.FileFunctions;
import pkg.util.Logging;
import pkg.util.Player;

public class DbFunctionsImpJSON implements IDbFunctions {

	/**
	 * Gson object for saving/loading users/matches.
	 */
	private Gson gson;

	/**
	 * For saving and loading gson files.
	 */
	private FileFunctions ff;

	/**
	 * Creates the gson object.
	 */
	public DbFunctionsImpJSON() {
		gson = new Gson();
		ff = new FileFunctions();
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveMatch(int match_id, int user1, int user2,  String filename) {
		
		match_id= getNextMatchId();
		
		Match match= new Match();
		
		match.setMatch_id(match_id);
		match.setUser1(user1);
		match.setUser2(user2);
		match.setFilename(filename);
		
		ff.JSONsaveMatch(match);
	}

	@Override
	public List<Match> loadMatches(int p1_id, int p2_id) {
	
		List<Match> matches = ff.JSONloadMatches();
		
		return matches;
	}

	@Override
	public int getNextUserId() {
		
		List<User> users = ff.JSONloadUsers();

		int id = users.stream().map(x->x.getUser_id())
				.sorted( (x,y)->  y.compareTo(x) ).findFirst().orElse(0);
		
		if(id == 0)
		{
			return 3;
		}
		
		return (id + 3);
	}

	@Override
	public int getNextMatchId() {
		
		List<Match> matches = ff.JSONloadMatches();
		
		int id = matches.stream().map(x->x.getMatch_id())
				.sorted( (x,y)->  y.compareTo(x) ).findFirst().orElse(0);
		
		if(id == 0)
		{
			return 3;
		}
		
		return (id + 3);
	}

	@Override
	public int getIdByUsername(String username) {
		
		List<User> users = ff.JSONloadUsers();

		int id = users.stream().filter(x -> x.getName().equals(username))
				.map(x -> x.getUser_id())
				.findFirst().orElse(0);
		
		if(id == 0)
		{
			return 0;
		}
		
		return id;
	}

	@Override
	public String getUsernameById(int user_id) {

		List<User> users = ff.JSONloadUsers();

		String username = users.stream().filter(x -> ((Integer)x.getUser_id()).equals(user_id)  ) 
				.map(x -> x.getName())
				.findFirst().orElse("");
		
		Logging.getLogger().info("Username found: {}",username);
		
		return username;
	}

	@Override
	public Player login(String username, String passw, char xOrO) {

		List<User> users = ff.JSONloadUsers();
		
		int id = users.stream().filter(x -> x.getName().equals(username) && x.getPassw().equals(passw)).map(x -> x.getUser_id())
				.findFirst().orElse(0);
		
		if (id == 0) {
			return null;
		}

		Player player = new Player();
		player.setUsername(username);
		player.setxOrO(xOrO);

		return player;
	}

	@Override
	public Player signUp(String username, String passw, char xOrO) {

		if (getIdByUsername(username) != 0) {
			return null;
		}

		int id = getNextUserId();

		saveUser(id, username, passw);

		Player player = new Player();
		player.setUsername(username);
		player.setxOrO(xOrO);

		return player;
	}

	@Override
	public void saveUser(int user_id, String username, String passw) {

		user_id= getNextUserId();
		
		User user = new User();
		user.setName(username);
		user.setPassw(passw);
		user.setUser_id(user_id);

		ff.JSONsaveUser(user);
	}

	/*
	public static void main(String[] args) {

		DbFunctionsImpJSON dbf = new DbFunctionsImpJSON();
		//dbf.saveUser(10, "a", "a");

		//dbf.login("a", "a", 'O');
		//dbf.saveMatch(122, 12, 21, Date.valueOf(LocalDate.now()), "no.fil2e");
		
		List<User> users=new ArrayList<User>();
		System.out.println(users.size());
		System.out.println("over");
	}*/

}
