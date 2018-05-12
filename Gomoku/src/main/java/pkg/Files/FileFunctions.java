package pkg.Files;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import pkg.DB.Match;
import pkg.DB.User;
import pkg.util.Logging;
import pkg.util.ParsedLine;

/**
 * Contains every file related operation.
 */
public class FileFunctions {

	/**
	 * For reading from file.
	 */
	private FileReader in;
	/**
	 * For writing to file.
	 */
	private FileWriter wr;
	/**
	 * Match object.
	 */
	private Match match;
	/**
	 * Parsed information from the file.
	 */
	private ParsedLine pl;

	/**
	 * About info.
	 */
	private String about;
	/**
	 * Position.
	 */
	private int x, y;
	/**
	 * X or O.
	 */
	private char xOrO;

	/**
	 * Creates the {@link FileFunctions} object. Initialises the name of the
	 * {@link about} file.
	 */
	public FileFunctions() {
		about = "about";
	}

	/**
	 * From the line, it takes out the x,y position, and the X/O character.
	 * 
	 * @param line
	 *            to parse
	 */
	public void parseLine(String line) {
		String[] arr = line.split(";");
		x = Integer.parseInt(arr[0]);
		y = Integer.parseInt(arr[1]);
		xOrO = arr[2].charAt(0);
	}

	/**
	 * Creates a file with the given filename from the board then saves it.
	 * 
	 * @param filename
	 *            for saving
	 * @param board
	 *            from saving
	 */
	public void saveFile(String filename, char[][] board) {

		Path path = FileSystems.getDefault().getPath("matches/", filename);

		if (!Files.exists(path)) {
			File file = new File("matches");
			file.mkdir();
		}

		Charset charset = Charset.forName("US-ASCII");

		try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {

			for (int x = 0; x < board.length; x++) {
				for (int y = 0; y < board[0].length; y++) {
					if (board[x][y] != '.') {
						String line = x + ";" + y + ";" + board[x][y] + "\n";
						writer.write(line, 0, line.length());
					}
				}
			}
		} catch (IOException x) {
			Logging.getLogger().error(x.getMessage());
		}
		Logging.getLogger().info("File saved by the name: {}", filename);
	}

	/**
	 * Reads in the about file.
	 * 
	 * @return {@code List<String>} object that contains the about file's lines.
	 */
	public List<String> AboutString() {
		List<String> lines = new ArrayList<String>();

		StringBuilder sb = new StringBuilder();
		
		//try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/"+about)))  ) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException x) {
			Logging.getLogger().error(x.getMessage());
		}

		Logging.getLogger().info("About text stored in List<String>");
		return lines;
	}

	/**
	 * Loads the file, and builds a list of {@link ParsedLine} objects.
	 * 
	 * @param filename
	 *            the name of the file, that it reads.
	 * @return list of {@link ParsedLine} objects.
	 */
	public List<ParsedLine> loadFile(String filename) {
		List<ParsedLine> plines = new ArrayList<ParsedLine>();
		Charset charset = Charset.forName("US-ASCII");
		Path path = FileSystems.getDefault().getPath("matches/", filename);

		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				parseLine(line);
				plines.add(new ParsedLine(x, y, xOrO));
			}
		} catch (IOException x) {
			Logging.getLogger().error(x.getMessage());
		}

		Logging.getLogger().info("Game state lines stored in List<ParsedLine>");
		return plines;
	}

	/**
	 * Loads the users in from the JSON slash Users dot json file.
	 * 
	 * @return {@code List<User>}
	 */
	public List<User> JSONloadUsers() {

		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<User>>() {
		}.getType();

		List<User> users = new ArrayList<User>();

		if (new File("JSON/Users.json").isFile()) {
			try {
				users = gson.fromJson(new FileReader("JSON/Users.json"), listType);
				Logging.getLogger().info("Loaded {} users.", users.size());

			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {

				Logging.getLogger().error("Error on parsing the Users.json file into List<User>. Error message: {}",
						e.getMessage());
			}

		}

		if (users == null) {
			users = new ArrayList<User>();
		}

		return users;
	}

	/**
	 * Loads the matches in from the JSON slash Matches dot json file.
	 * 
	 * @return {@code List<Match>}
	 */
	public List<Match> JSONloadMatches() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		Type listType = new TypeToken<ArrayList<Match>>() {
		}.getType();

		List<Match> matches = new ArrayList<Match>();

		if (new File("JSON/Matches.json").isFile()) {
			try {
				matches = gson.fromJson(new FileReader("JSON/Matches.json"), listType);
				Logging.getLogger().info("Loaded {} matches.", matches.size());

			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {

				Logging.getLogger().error("Error on parsing the Matches.json file into List<Match>. Error message: {}",
						e.getCause());
			}
		}

		if (matches == null) {
			matches = new ArrayList<Match>();
		}

		return matches;
	}

	/**
	 * Saves a given user to the Users dot json file.
	 * 
	 * @param user
	 *            to save.
	 */
	public void JSONsaveUser(User user) {
		Path path = FileSystems.getDefault().getPath("JSON/", "Users.json");

		if (!Files.exists(path)) {
			File file = new File("JSON");
			file.mkdir();
		}

		List<User> users = JSONloadUsers();
		users.add(user);

		users = users.stream().sorted((x, y) -> ((Integer) x.getUser_id()).compareTo(y.getUser_id()))
				.collect(Collectors.toList());

		try (Writer writer = new FileWriter("JSON/Users.json")) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(users, writer);
		} catch (IOException e) {
			Logging.getLogger().error("Erron with saving to Users.json. Error message: {}", e.getMessage());
		}
	}

	/**
	 * Saves a given match to the Users dot json file.
	 * 
	 * @param match
	 *            to save
	 */
	public void JSONsaveMatch(Match match) {
		Path path = FileSystems.getDefault().getPath("JSON/", "Matches.json");

		if (!Files.exists(path)) {
			File file = new File("JSON");
			file.mkdir();
		}

		List<Match> matches = JSONloadMatches();
		matches.add(match);

		matches = matches.stream().sorted((x, y) -> ((Integer) x.getMatch_id()).compareTo(y.getMatch_id()))
				.collect(Collectors.toList());

		try (Writer writer = new FileWriter("JSON/Matches.json")) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(matches, writer);

		} catch (IOException e) {
			Logging.getLogger().error("Erron with saving to Matches.json. Error message: {}", e.getMessage());
		}
	}

}
