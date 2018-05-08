package pkg;

import pkg.UI.FXWindow;
import pkg.util.Logging;

/**
 *	The starting point of the game. 
 */
public class Main {

	/**
	 * Starting point of the application.
	 * @param args arguments.
	 */
	public static void main(String[] args) {
		
		Logging.getLogger().info("Game starts..");
		FXWindow.startToShowGame();
	}

}
