package pkg.UI;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import pkg.util.Logging;

/**
 * Creates a window that outputs a given string.
 */
public abstract class FinishWindow 
{
	/**
	 * Label for determining what the state is. It can be win or tie.
	 */
	private static Label stateLabel;
	/**
	 * For horizontal layout.
	 */
	private static HBox hbox;
	/**
	 * For vertical layout.
	 */
	private static VBox vbox;
	/**
	 * The main scene.
	 */
	private static Scene scene;
	/**
	 * The window.
	 */
	private static Stage window;
	
	/**
	 * Creates and shows a window, that displays the {@code text}.
	 * @param text for output
	 */
	@SuppressWarnings("restriction")
	public static void finishBox(String text)
	{
		window = new Stage();
		window.setMinHeight(80);
		window.setMinWidth(70);
		window.initModality(Modality.APPLICATION_MODAL);
		
		stateLabel= new Label(text);
		stateLabel.setStyle("-fx-font-size: 60px; -fx-text-fill: red; ");
		
		hbox = new HBox();
		hbox.getChildren().add(stateLabel);
		hbox.setAlignment(Pos.CENTER);
		
		vbox = new VBox();
		vbox.getChildren().add(hbox);
		vbox.setStyle("-fx-background-color: black;");
		vbox.setAlignment(Pos.CENTER);
		
		scene= new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
		window.setResizable(false);
		
		Logging.getLogger().info("FinishWindow opened with the message: {}", text);
	}
}
