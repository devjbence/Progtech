package pkg.UI;

import java.awt.Insets;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import pkg.DB.Match;
import pkg.Files.FileFunctions;
import pkg.util.Logging;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * Creates the LoadWindow.
 */
@SuppressWarnings("restriction")
public abstract class LoadWindow 
{

	/**
	 * Layout for the list.
	 */
	private static StackPane sp;
	/**
	 * The main scene.
	 */
	private static Scene scene;
	/**
	 * Submit the chosen file to load. 
	 */
	private static Button submit;
	/**
	 * The main window.
	 */
	private static Stage window;
	/**
	 * File to load.
	 */
	private static String filename;
	/**
	 * Vertical layout for the list box and submit button.
	 */
	private static VBox vbox;
	/**
	 * List of the found matches.
	 */
	private static List<Match> matches;
	
	/**
	 * Creates a window, that shows saved games. After choosing a game, and then clicked on submit, a new fresh board will appear.
	 * @param matches List of matches.
	 * @param gs for the load method.
	 */
	public static void loadBox(List<Match> matches,GameScene gs)
	{
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Choose a file to load..");
		              
		ListView<String> list = new ListView<String>();
		submit = new Button("submit");
		ObservableList<String> items =FXCollections.observableArrayList (
		matches.stream().map(x->x.getFilename()).collect(Collectors.toList()));
		list.setItems(items);
               
        StackPane sp = new StackPane();
        sp.setStyle("-fx-padding:5px;-fx-border-insets:5px;-fx-background-insets:5px;");
        vbox= new VBox();
        
        sp.getChildren().add(list);
        sp.setMaxHeight(150);
        
        vbox.getChildren().addAll(sp,submit);
        window.setScene(new Scene(vbox, 200, 180));
        window.show();

        submit.setOnAction(e->{
        	filename= list.getSelectionModel().getSelectedItem();
        	gs.load(filename);
        	window.close();
        	Logging.getLogger().info("Submit cliked, the choosen one is: {}", filename);
        });
        Logging.getLogger().info("LoadWindow opened");
	}
}
