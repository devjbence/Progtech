package pkg.UI;

import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pkg.Board;
import pkg.Logic;
import pkg.Player;
import pkg.DB.IDbFunctions;
import pkg.DB.DbFunctionsImpJDBC;
import pkg.DB.DbFunctionsImplHIB;
import pkg.DB.Match;
import pkg.Files.FileFunctions;
import pkg.util.Logging;
import pkg.util.ParsedLine;

/**
 * Creates the game window.
 */
@SuppressWarnings("restriction")
public class GameScene extends Scenes{
	
	/**
	 * The main window.
	 */
	Stage mainStage;
	/**
	 * The logic of the game.
	 */
	private Logic logic;
	/**
	 * The board object.
	 */
	private Board board;
	/**
	 * Player #1.
	 */
	private Player player;
	/**
	 * Player #2.
	 */
	private Player otherPlayer;
	/**
	 * For database operations.
	 */
	private IDbFunctions dbf;
	/**
	 * For file operations.
	 */
	private FileFunctions ff;
	/**
	 * Layout for the Gomoku board.
	 */
	private GridPane boardGrid ;
	/**
	 * Vertical layout for the labels and the grid.
	 */
	private VBox vbox;
	/**
	 * Horizontal layout for the labels.
	 */
	private HBox hboxTurns;
	/**
	 * File menu.
	 */
	private Menu file ;
	/**
	 * About menu.
	 */
	private Menu about ;
	/**
	 * Next player.
	 */
	private Label plNameText;
	/**
	 * Name of the player.
	 */
	private Label plName;
	/**
	 * Save menu.
	 */
	private MenuItem save ;
	/**
	 * New game.
	 */
	private MenuItem newGame;
	/**
	 * Chose a game to load if there is any.
	 */
	private MenuItem load ;
	/**
	 * About me page.
	 */
	private MenuItem aboutme ;
	/**
	 * Menu bar.
	 */
	private MenuBar menuBar;
	/**
	 * Layout for the menu.
	 */
	private BorderPane MenuLayout;
	/**
	 * Buttons representing the board.
	 */
	private Button[][] fields ;
	/**
	 * If there are stored files for the id's then it gives you the list of them.
	 */
	private List<Match> matches ;
	/**
	 * If the game has ended already.
	 */
	private boolean overOnce;
	
	/**
	 * Creates the game object.
	 * Initialising.
	 * 
	 * @param dbf for database
	 * @param logic for logic
	 */
	public GameScene(IDbFunctions dbf, Logic logic)
	{
		mainStage= Scenes.getPrimaryStage();
		this.logic= logic;
		board = logic.getBoard();
		player= logic.getCurrentPlayer();
		otherPlayer= logic.getOtherPlayer();
		this.dbf= dbf;
		ff= new FileFunctions();
		overOnce=false;
		Logging.getLogger().info("GameScene object created");
	}	
	
	/**
	 * Initialising the layout.
	 */
	private void initLayout()
	{
		boardGrid = new GridPane();
		boardGrid.setPadding(new Insets(20,10,10,10));
		boardGrid.setHgap(0);
		boardGrid.setVgap(0);
		
		vbox = new VBox();
		hboxTurns= new HBox();
		
		file = new Menu("File");
		about = new Menu("About");
		
		plNameText = new Label("Turns: ");
        plNameText.setId("plNameText");
        
		plName= new Label(player.getUsername()+" ( "+ player.getNewXorO()+" )");
		plName.setPadding(new Insets(10,10,0,10));
		plNameText.setPadding(new Insets(10,10,0,10));
		
		newGame= new MenuItem("New Game");
		save = new MenuItem("Save");
		load = new MenuItem("Load");
		aboutme = new MenuItem("About me");
		
		file.getItems().addAll(newGame,save,load);
		about.getItems().addAll(aboutme);

		menuBar =new MenuBar();
        menuBar.getMenus().addAll(file,about);
		
		MenuLayout = new BorderPane();
        MenuLayout.setTop(menuBar);
        
        fields = new Button[13][13];
        
        Logging.getLogger().info("GameScene layout intialised");
	}
	
	/**
	 * Creates the file name, by the following pattern:
	 * matchid_x_y where x less than or equal to y.
	 * After that, it saves it to a file, and saves it to the database.
	 */
	private void save()
	{
     	int id=dbf.getNextMatchId();
    	int user1= dbf.getIdByUsername(player.getUsername());
    	int user2= dbf.getIdByUsername(otherPlayer.getUsername());
    	int[] srt= new int[]{user1,user2};
    	Arrays.sort(srt);
    	
    	Date match_date = new Date(System.currentTimeMillis());
    	String filename=id+"_"+srt[0]+"_"+srt[1];
    	
    	ff.saveFile(filename, board.getBoard());
        dbf.saveMatch(id, srt[0], srt[1], match_date, filename);
    	
    	Logging.getLogger().info("Gamestate saved into db and into {}", filename);
	}
	
	/**
	 * Sets the {@link #matches} list, by putting only those matches into it, which has player1's id and player2's id.
	 */
	private void setMatchesForLoad()
	{
    	int currPlayerId=dbf.getIdByUsername(player.getUsername());
    	int othPlayerId=dbf.getIdByUsername(otherPlayer.getUsername());
    	
    	matches = dbf.loadMatches(currPlayerId, othPlayerId);
    	Logging.getLogger().info("{} matches in db", matches.size());
	}
	
	/**
	 * Resets the board, and then loads the saved state into it.
	 * @param filename the file which has the saved match.
	 */
	public void load(String filename)
	{
		System.out.println("load");
    	overOnce=false;
    	
    	List<ParsedLine> pl= ff.loadFile(filename);
    	board.initBoard();
    	
    	for(int i=0;i<logic.getBoard().BOARD_SIZE;i++)
    	{
    		for(int j=0;j<logic.getBoard().BOARD_SIZE;j++)
    		{	
    			fields[i][j].setText(" ");
    			fields[i][j].setId(" ");
    		}
    	}       	
    	
    	int count=0;
    	
    	for(ParsedLine l : pl)
    	{
    		int x= l.getX();
    		int y=l.getY();
    		char xOrO=l.getxOrO();
    		count++;
    		
    		logic.getBoard().getBoard()[x][y]=xOrO;
    		fields[x][y].setText(" "+ParsedLine.getNewXorO(xOrO)+" ");
    	}
    	logic.getBoard().setNumOfTakenFields(count);
    	
    	Logging.getLogger().info("Loaded in.");
	}
	
	/**
	 * Open up a window, displaying the name of the winner.
	 * @param xOrO for determining who won.
	 */
	private void won(char xOrO)
	{
		if(player.getOldXorO()=='X')
		{
			FinishWindow.finishBox("!! "+player.getUsername()+" won !!");
		}
		else{
			FinishWindow.finishBox("!! "+player.getUsername()+" won !!");
		}
		overOnce=true;
	}
	
	/**
	 * All operation reletated to fields.
	 * Check if there a tie, or win. If there is, then the user will be notified by a finish window.
	 * @param x column 
	 * @param y row 
	 */
	private void fieldAction(int x, int y)
	{
	    boolean won=logic.WhichWin()!=0;
	    boolean tie=logic.checkTie();
		boolean weCanAlterIt=false;
		char xOrO =player.getNewXorO();
		
		if(board.put(x, y, player.getOldXorO()) && !won && !tie)
		{
			fields[x][y].setText(" "+xOrO+" ");
			weCanAlterIt=true;
		}
		else
		{//ERROR, ALREADY TAKEN
			fields[x][y].setId("Taken");
		}
		
		int whichWin=logic.WhichWin();
	    
		if(logic.checkTie())
		{
			System.out.println("its a tie!");
			FinishWindow.finishBox("!! It is a tie !!");
			overOnce=true;
			weCanAlterIt=false;
			Logging.getLogger().info("Tie");
		}
		
		if(whichWin==1)
		{
			if(!overOnce)
				System.out.println("x won!!!!!!");
				won('X');
				weCanAlterIt=false;
				Logging.getLogger().info("X won");
		}
		
		if(whichWin==2)
		{
			if(!overOnce)
				System.out.println("o won!!!!!!");
				won('O');
				weCanAlterIt=false;
				Logging.getLogger().info("O won");
		}
		
		if(weCanAlterIt)
		{
			player = logic.getCurrentPlayer();
			plName.setText(player.getUsername() +" ( "+ player.getNewXorO()+" )");
		}	
	}
	
	/**
	 * Initialising the layout, the board, start the game window.
	 * Contains the starting point for handling action on new game, load, save, and on every individual field.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		initLayout();
        
        for(int i=0;i<logic.getBoard().BOARD_SIZE;i++)
        {
        	for(int j=0;j<logic.getBoard().BOARD_SIZE;j++)
        	{
        		int x=i;int y=j;
        		fields[i][j]= new Button(" ");
        		
        		boardGrid.setConstraints(fields[i][j],i,j);
        		boardGrid.getChildren().add(fields[i][j]);
        		
        		fields[i][j].setOnAction(e->{
        			fieldAction(x,y);
        		});
        	}
        }
        
        newGame.setOnAction(e->{
        	logic.getBoard().initBoard();
        	for(int i=0;i<logic.getBoard().BOARD_SIZE;i++)
        	{
        		for(int j=0;j<logic.getBoard().BOARD_SIZE;j++)
        		{
        			fields[i][j].setText(" ");
        			fields[i][j].setId("");
        		}
        	}
        	Logging.getLogger().info("New Game clicked");
        });
        
        save.setOnAction(e->{
        	save();
        	Logging.getLogger().info("Save clicked");
        });
        
        load.setOnAction(e->{
        	setMatchesForLoad();
        	if(!matches.isEmpty())
        		LoadWindow.loadBox(matches,this);
        	Logging.getLogger().info("Load cicked");
        });
        
        aboutme.setOnAction(e->{
        	AboutWindow.aboutBox();
        	Logging.getLogger().info("Aboutme clicked");
        });
        
        hboxTurns.getChildren().addAll(plNameText,plName);
        vbox.getChildren().addAll(MenuLayout,hboxTurns,boardGrid);
        
        Scene scene = new Scene(vbox, 600, 600);
        scene.getStylesheets().add("board.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setTitle("GOMOKU");
        
        primaryStage.setOnCloseRequest(e->{
    	System.out.println("closing..");
    	dbf.closeConnection();
    	Logging.getLogger().info("GameScene closed");
        });
        
	}

}
