package pkg.DB;
import java.sql.*;

import pkg.util.Logging;

/**
 * 
 *	Registers the connection and logs in.
 *	
 */
public class Connector {
	
	/**
	 * The connection for database operations.
	 */
	private Connection connection;
	
	/**
	 * Create the Connector object.
	 * With {@link #setConnection()} it sets the connection.
	 * 
	 */
	public Connector() 
	{
		setConnection();
		Logging.getLogger().info("Database connected...");
	}
	
	/**
	 * Sets the connection by registering it, then by logging in to it.
	 */
	private void setConnection() 
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			Logging.getLogger().error(e.getMessage());
		}
		try {
			connection = 
			DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","benccee","saapkaa");
		} catch (SQLException e) {
			Logging.getLogger().error(e.getSQLState());
		}
	}
	
	/**
	 * Closes the connection.
	 * 
	 */
	public void closeConnetion() 
	{
		Logging.getLogger().info("Connection closed");
		try {
			connection.close();
		} catch (SQLException e) {
			Logging.getLogger().error(e.getSQLState());
		}
	}
	
	/**
	 * 
	 * @return the configured {@link #connection}} object.
	 */
	public Connection getConnection()
	{
		Logging.getLogger().info("Gets connection object.");
		return connection;
	}
	
}
