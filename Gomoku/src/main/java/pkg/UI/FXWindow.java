package pkg.UI;

import java.util.Random;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Creates the main window.
 */
@SuppressWarnings("restriction")
public class FXWindow extends Application {

	/**
	 * The window.
	 */
	Stage pm;
	/**
	 * The main scene.
	 */
	Scenes scene;
	
	/**
	 * Starts the main Window.
	 */
	public static void startToShowGame() {
		launch();
	}

	/**
	 * Initialising the main window.
	 */
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) throws Exception {

		pm = Scenes.getPrimaryStage();
		scene = new AuthenticationScene(pm);
	}
	

}
