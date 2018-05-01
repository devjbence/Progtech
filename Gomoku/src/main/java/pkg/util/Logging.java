package pkg.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *	Singleton class for the logger object.
 */
public class Logging {
	
	/**
	 * Logger object by the LoggerFactory.
	 */
	private static Logger logger = LoggerFactory.getLogger(Logging.class);
	
	/**
	 * Static class.
	 */
	private Logging(){}
	
	/**
	 * Returns the logger object.
	 * @return logger
	 */
	public static Logger getLogger()
	{
		return logger;
	}
}
