package pkg;

/**
 * Class for wrapping player informations.
 */
public class Player {

	/**
	 * Name from/to database.
	 */
	private String username;
	/**
	 * X or O for the player. Will be stored in char[][] array.
	 */
	private char xOrO; 

	/**
	 * Plain old X/O.
	 * @return X/O
	 */
	public char getOldXorO() {
		return xOrO;
	}

	/**
	 * Sets the X/O.
	 * @param xOrO X/O
	 */
	public void setxOrO(char xOrO) {
		this.xOrO = xOrO;
	}

	/**
	 * Creates a <code>Player</code> object.
	 */
	public Player() {}

	/**
	 * Get the user name.
	 * @return user name
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the user name.
	 * @param username user name
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Returns the x/o in the new style.
	 * @return X/O
	 */
	public char getNewXorO()
	{
		if(xOrO == 'X')
		{
			return (char)10006;
		}
		return (char)9679;
	}

	@Override
	public boolean equals(Object obj) {
		
		Player pl= (Player) obj;
		return pl.getUsername()==username;
	}
	
}
