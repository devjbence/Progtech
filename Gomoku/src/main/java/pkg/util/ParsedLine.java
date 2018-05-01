package pkg.util;

/**
 * Class for containing data, from saved games.
 */
public class ParsedLine {
	
	/**
	 * Position.
	 */
	private int x,y;
	/**
	 * X or O.
	 */
	private char xOrO;
	
	/**
	 * Creates a ParsedLine object.
	 * @param x column number
	 * @param y row number
	 * @param xOrO X/O
	 */
	public ParsedLine(int x, int y, char xOrO) {
		this.x = x;
		this.y = y;
		this.xOrO = xOrO;
	}

	/**
	 * Get x.
	 * @return x (column)
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets y.
	 * @return y (row)
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets X/O.
	 * @return xOrO X/O
	 */
	public char getxOrO() {
		return xOrO;
	}

	/**
	 * Sets x.
	 * @param x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Set y.
	 * @param y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Set X/O.
	 * @param xOrO to set
	 */
	public void setxOrO(char xOrO) {
		this.xOrO = xOrO;
	}
	
	/**
	 * Returns the X or O in a different style.
	 * @param given_xOrO X/O
	 * @return new X/O
	 */
	public static char getNewXorO(char given_xOrO)
	{
		if(given_xOrO == 'X')
		{
			return (char)10006;
		}
		return (char)9679;
	}

}
