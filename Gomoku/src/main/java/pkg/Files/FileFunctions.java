package pkg.Files;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import pkg.DB.Match;
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
	private int x,y;
	/**
	 * X or O.
	 */
	private char xOrO;
	
	/**
	 * Creates the {@link FileFunctions} object.
	 * Initialises the name of the {@link about} file.
	 */
	public FileFunctions()
	{
		about="about";
	}

	/**
	 * From the line, it takes out the x,y position, and the X/O character.
	 * @param line  to parse
	 */
	public void parseLine(String line)
	{
		String [] arr = line.split(";");
		x= Integer.parseInt(arr[0]);
		y= Integer.parseInt(arr[1]);
		xOrO= arr[2].charAt(0);	
	}
	
	/**
	 * Creates a file with the given filename from the board then saves it.
	 * 
	 * @param filename for saving
	 * @param board from saving
	 */
	public void saveFile(String filename,char[][]board)
	{
			Path path = FileSystems.getDefault().getPath("matches/", filename);
			Charset charset = Charset.forName("US-ASCII");
			
			try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
				
				for(int x=0;x<board.length;x++)
				{
					for(int y=0;y<board[0].length;y++)
					{
						if(board[x][y]!= '.' )
						{
							String line=x+";"+y+";"+board[x][y]+"\n";
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
	public  List<String> AboutString()
	{
		List<String> lines= new ArrayList<String>();
		
		Path path = FileSystems.getDefault().getPath("other_stuff/", about);
		Charset charset = Charset.forName("US-ASCII");
		
		StringBuilder sb= new StringBuilder();
		
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
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
	 * @param filename the name of the file, that it reads.
	 * @return list of {@link ParsedLine} objects.
	 */
	public List<ParsedLine> loadFile(String filename)
	{
		List<ParsedLine> plines= new ArrayList<ParsedLine>();
		Charset charset = Charset.forName("US-ASCII");
		Path path = FileSystems.getDefault().getPath("matches/", filename);
		
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	parseLine( line);
		        plines.add(new ParsedLine(x,y,xOrO));
		    }
		} catch (IOException x) {
		    Logging.getLogger().error(x.getMessage());
		}
		
		Logging.getLogger().info("Game state lines stored in List<ParsedLine>");
		return plines;
	}

}
