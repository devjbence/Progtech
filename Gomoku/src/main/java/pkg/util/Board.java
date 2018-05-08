package pkg.util;
import pkg.util.*;

/**
 * The main purpose of the class, is to have a board, so that we can test the logic, and for JavaFX window for showing the game.
 */
public class Board {
	
	/**
	 * Size of the board.
	 */
	public final int BOARD_SIZE=13;
	/**
	 * Board array.
	 */
	private char [][]board;
	/**
	 * Number of fields which were picked.
	 */
	private int numOfTakenFields;
	/**
	 *  Board size times board size.
	 */
	public final int FIELDNUM=BOARD_SIZE*BOARD_SIZE;
	
	/**
	 * Creates the {@code Board} object and initialise {@code board} which stores the board.
	 * The {@code board} has fields that are either
	 * <ul>
	 * <li><b>.</b>empty</li>
	 * <li><b>X</b></li>
	 * <li><b>O</b></li>
	 * </ul>
	 */
	public Board()
	{
		board= new char[BOARD_SIZE][BOARD_SIZE];
		initBoard();
		Logging.getLogger().info("Board object created");

	}
	
	/**
	 * Determines that the given x,y position on the board is free or not.
 	 * @param x column number
	 * @param y row number
	 * @return true if {@code board[y][x]} is 0.
	 */
	private boolean isPlaceFree(int x, int y)
	{
		return board[x][y]=='.';
	}
	
	/**
	 * Puts the given character to the given x,y position (if possible).
	 * 
	 * @param x column number
	 * @param y row number
	 * @param character possible values are X/0 
	 * @return true if the place is free
	 */
	public boolean put(int x,int y, char character)
	{
		if(isPlaceFree(x,y))
		{
			board[x][y]=character;	
			numOfTakenFields++;
		}
		else {
			//The place is taken, choose something else.
			return false;
		}
		return true;
	}
	/**
	 * @return the board
	 */
	public char[][] getBoard()
	{
		return board;
	}
	
	/**
	 * Initialises the board.
	 */
	public void initBoard()
	{
		for(int i=0;i<BOARD_SIZE;i++)
		{
			for(int j=0;j<BOARD_SIZE;j++)
			{
				board[i][j]='.';
			}
		}
		numOfTakenFields=0;
		Logging.getLogger().info("board init called");
	}

	/**
	 * Returns the number of taken fields.
	 * @return numOfTakenFields number of taken fields
	 */
	public int getNumOfTakenFields() {
		return numOfTakenFields;
	}

	/**
	 * Set the number of taken fields.
	 * @param numOfTakenFields number of taken fields
	 */
	public void setNumOfTakenFields(int numOfTakenFields) {
		this.numOfTakenFields = numOfTakenFields;
	}
	
	
}
