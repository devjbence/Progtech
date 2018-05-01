package pkg.UI;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Contains the starting point of the primaryStage.
 */
@SuppressWarnings("restriction")
public abstract class Scenes extends Application{

	/**
	 * The main window.
	 */
	private static Stage primaryStage;
	
	/**
	 * Return the primaryStage object.
	 * @return {@link #primaryStage} window
	 */
	public static Stage getPrimaryStage()
	{
		setPrimaryStage();
		
		return primaryStage;
	}

	/**
	 * Sets the primaryStage.
	 * @param ps window
	 */
	public static void setPrimaryStage(Stage ps)
	{
		primaryStage=ps;
	}
	
	/**
	 * Sets the main window.
	 */
	private static void setPrimaryStage() {
		primaryStage = new Stage();
	}
	
}
