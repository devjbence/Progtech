package pkg;

import pkg.util.Logging;

/**
 * 
 * This class contains the logic.
 */
public class Logic {
	
	/**
	 * Player #1.
	 */
	private Player p1;
	/**
	 * Player #2.
	 */
	private Player p2;
	/**
	 * Current player.
	 */
	private Player currentPlayer;
	/**
	 * Board informations. Also contains the char[][] array.
	 */
	private Board board;

	/**
	 * Creates the {@code Game} object.
	 * @param p1 the player who has X.
	 * @param p2 the player who O;
	 */
	public Logic(Player p1,Player p2)
	{	
		this.p1=p1;
		this.p2=p2;
		board= new Board();
		currentPlayer=p2;
		Logging.getLogger().info("logic object created");
	}
	
	/**
	 * 	Getting the {@code currentPlayer}.
	 * 	With this, we can alternate who the current player is.
	 * 
	 * @return {@code currentPlayer}
	 */
	public Player getCurrentPlayer()
	{
		if(currentPlayer.equals(p1))
		{
			currentPlayer=p2;
			Logging.getLogger().info("Current player is: {}",p2);
			return p2;
		}
		
		currentPlayer=p1;
		
		Logging.getLogger().info("Current player is: {}",p1);
		return p1;
	}
	
	/**
	 * 	Getting the {@code otherPlayer}.
	 * 	With this, we can alternate who the other player is.
	 * 
	 * @return {@code otherPlayer}
	 */
	public Player getOtherPlayer()
	{
		if(currentPlayer.equals(p1))
		{
			Logging.getLogger().info("Other player is: {}",p2);
			return p2;
		}
		
		Logging.getLogger().info("Other player is: {}",p1);
		return p1;
	}
	
	
	/**
	 * @return a {@code Board} object.
	 */
	public Board getBoard()
	{
		return board;
	}
	
	/**
	 * To check if there is a tie.
	 * Also check if the game was won, because if it was, then we dont check for tie, and return false.
	 * 
	 * @return true if there is a tie.
	 */
	public boolean checkTie()
	{
		boolean won=WhichWin()!=0;
		
		if(!won)
		{
			if(board.FIELDNUM == board.getNumOfTakenFields())
			{
				Logging.getLogger().info("Tie");
				return true;
			}
		}
		
		Logging.getLogger().info("No tie");
		return false;
	}
	
	/**
	 * Determines if player1 or player2 won.
	 * @return 0 if nobody won, 1 if X won, 2 if O did win.
	 */
	public int WhichWin()
	{
		int scoreForO=0, scoreForX=0;
		boolean breakAll=false;
		for(int y=0;y<board.BOARD_SIZE;y++)
		{
			for(int x=0;x<board.BOARD_SIZE;x++)
			{
				for(Direction dir : Direction.values())
				{
					scoreForX=checkInDirection(x,y,dir,'X'); //X
					scoreForO=checkInDirection(x,y,dir,'O'); //O
					if(scoreForX >=5 || scoreForO >=5) {breakAll=true;break;}
				}
				if(breakAll) {break;}
			}
			if(breakAll) {break;}
		}
		
		
		if(scoreForO==5)
		{
			Logging.getLogger().info("Winner is: {}",'O');
			return 2;
		}
		if(scoreForX==5)
		{
			Logging.getLogger().info("Winner is: {}", 'X');
			return 1;
		}
		
		Logging.getLogger().info("No winner");
		return 0; //0
	}
	
	/**
	 * Checks if the given X/O is at the given position.
	 * @param x column number
	 * @param y row number
	 * @param charToFind X or O
	 * @param shouldbreak if true, than we can stop iterating in {@link #checkInDirection(int x, int y, Direction dir, char XorO)}
	 * @return if the X/0 is at the given position, 1, else 0.
	 */
	private int CheckIfCharIsInPlace(int x,int y, char charToFind,boolean shouldbreak) 
	{
		if(board.getBoard()[x][y]==charToFind)
		{
			return 1;
		}
		shouldbreak=true;
		return 0;
	}
	
	/**
	 * Checks if x,y values are in the board.
	 * @param x column value
	 * @param y row value
	 * @return true if both x and y is on the {@code board}
	 */
	private boolean isInBoundaries(int x, int y)
	{
		return (x>=0 && x <board.BOARD_SIZE  && y>=0 && y<board.BOARD_SIZE);
	}

	/**
	 * Gets x,y coordinates and from that position it loops through the {@code board} in the given {@code Direction}.
	 * @param x column number
	 * @param y row number
	 * @param dir The {@code Direction} in which it will search for X or O
	 * @param XorO the character that we search in the given {@code Direction}
	 * @return a number between 0 and 5. If 5 then, we have a winner.
	 */
	private int checkInDirection(int x, int y, Direction dir, char XorO) {
		
		boolean shouldbreak=false;
		int score=0;
		
		for(int i=0;i<5;i++)
		{
			switch(dir)
			{
			case Top: if(isInBoundaries(x,y)) {score+=CheckIfCharIsInPlace(x,y,XorO,shouldbreak);y--;}else {shouldbreak=true;} break;
			
			case TopRight: if(isInBoundaries(x,y)) { score+=CheckIfCharIsInPlace(x,y,XorO,shouldbreak);x++;y--;}
				else {shouldbreak=true;} break;
				
			case Right: if(isInBoundaries(x,y)) { score+=CheckIfCharIsInPlace(x,y,XorO,shouldbreak);x++;}
				else {shouldbreak=true;} break;
				
			case BottomRight: if(isInBoundaries(x,y)) { score+=CheckIfCharIsInPlace(x,y,XorO,shouldbreak);x++;y++;}
				else {shouldbreak=true;} break;
				
			case Bottom: if(isInBoundaries(x,y)) {score+=CheckIfCharIsInPlace(x,y,XorO,shouldbreak); y++;}
				else {shouldbreak=true;} break;
				
			case BottomLeft: if(isInBoundaries(x,y)) { score+=CheckIfCharIsInPlace(x,y,XorO,shouldbreak);x--;y++;}
				else {shouldbreak=true;} break;
				
			case Left: if(isInBoundaries(x,y)) { score+=CheckIfCharIsInPlace(x,y,XorO,shouldbreak);x--; }else {shouldbreak=true;} break;
			
			case TopLeft: if(isInBoundaries(x,y)) { score+=CheckIfCharIsInPlace(x,y,XorO,shouldbreak);x--;y--;}
				else {shouldbreak=true;} break;
			}
			if(shouldbreak)
			{
				break;
			}
		}
		return score;
	}
}
