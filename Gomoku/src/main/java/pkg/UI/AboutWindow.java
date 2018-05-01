package pkg.UI;

import java.util.List;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import pkg.Files.FileFunctions;
import pkg.util.Logging;

/**
 * Creates a window, that outputs the about file's content.
 */
@SuppressWarnings("restriction")
public abstract class AboutWindow 
{
	/**
	 * Label for displaying the about information.
	 */
	private static Label aboutLabel;
	/**
	 * For positioning into the middle the winner.
	 */
	private static HBox hbox;
	/**
	 * For padding.
	 */
	private static VBox vbox;
	/**
	 * The main scene.
	 */
	private static Scene scene;
	/**
	 * The game window.
	 */
	private static Stage window;
	/**
	 * For the about lines.
	 */
	private static String text;
	/**
	 * String list for lines.
	 */
	private static List<String> lines;
	/**
	 * For file operations.
	 */
	private static FileFunctions ff;
	
	/**
	 * Initialises the window, and the layout.
	 * After that, it opens the about window.
	 */
	public static void aboutBox()
	{
		ff= new FileFunctions();
		lines = ff.AboutString();
		StringBuilder sb= new StringBuilder();
		
		for(String line : lines)
		{
			sb.append(line+"\n");
		}
		text= sb.toString();
		
		window = new Stage();
		window.setMinHeight(80);
		window.setMinWidth(70);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("About");
		
		aboutLabel= new Label(text);
		aboutLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: white; ");
		
		hbox = new HBox();
		hbox.getChildren().add(aboutLabel);
		hbox.setAlignment(Pos.CENTER);
		
		vbox = new VBox();
		vbox.getChildren().add(hbox);
		vbox.setStyle("-fx-background-color: black; -fx-padding: 5px;");
		vbox.setAlignment(Pos.CENTER);
		
		scene= new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
		window.setResizable(false);
		
		Logging.getLogger().info("About Window created");
	}
}
