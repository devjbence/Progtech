package pkg.UI;

import javafx.stage.Stage;
import pkg.DB.IDbFunctions;
import pkg.Controller.Logic;
import pkg.DB.DbFunctionsImpJDBC;
import pkg.DB.DbFunctionsImpJSON;
import pkg.DB.DbFunctionsImplHIB;
import pkg.util.Logging;
import pkg.util.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Creates the Authentication window.
 */
@SuppressWarnings("restriction")
public class AuthenticationScene extends Scenes{
	
	/**
	 * Checks if both player are logged in.
	 */
	private boolean isBothLoggedIn;
	/**
	 * Is the first player logged in.
	 */
	private boolean isU1LoggedIn;
	/**
	 * Is the second player logged in.
	 */
	private boolean isU2LoggedIn;
	
	/**
	 * The main scene.
	 */
	private Scenes scene;
	/**
	 * The game window.
	 */
	private Stage primaryStage;
	/**
	 * For database operations.
	 */
	private IDbFunctions dbf;
	/**
	 * Player.
	 */
	private Player player1,player2,player;
	/**
	 * The logic of the game.
	 */
	private Logic mainControl;
	
	/**
	 * GridPane for the label and the password field for player #1.
	 */
	private GridPane gpane4Au1;
	/**
	 * GridPane for the label and the password field for player #2.
	 */
	private GridPane gpane4Au2;
	/**
	 * GridPane for the label and the password field for player #1.
	 */
	private GridPane gpane4Au3;
	/**
	 * GridPane for the label and the password field for player #2.
	 */
	private GridPane gpane4Au4;
	
	/**
	 * Vertical box for the GridPane #1.
	 */
	private VBox vbox1;
	/**
	 * Vertical box for the GridPane #2.
	 */
	private VBox vbox2;
	/**
	 * Vertical box for vbox1 and vbox2.
	 */
	private VBox vbox3;
	
	/**
	 * Label player player #1's name.
	 */
	private Label pl1;
	/**
	 *  Label player player #2's name.
	 */
	private Label pl2;
	/**
	 *  Label player player #1's user name.
	 */
	private Label uname1;
	/**
	 *  Label for player player #1 password.
	 */
	private Label passw1;
	/**
	 * Label for player player #2 user name.
	 */
	private Label uname2;
	/**
	 * Password label for player player #2.
	 */
	private Label passw2;
	/**
	 * Error with the password or user name for player #1.
	 */
	private Label pl1Error;
	/**
	 * Error with the password or user name for player #2.
	 */
	private Label pl2Error;
	
	/**
	 * Input user#1's name.
	 */
	private TextField utext1;
	/**
	 * Input user#2's name.
	 */
	private TextField utext2;
	
	/**
	 * Password input for player #1.
	 */
	private PasswordField ptext1;
	/**
	 * Password input for player #2.
	 */
	private PasswordField ptext2;
	
	/**
	 * Login button for player #1.
	 */
	private Button login1;
	/**
	 * Login button for player #2.
	 */
	private Button login2 ;
	
	/**
	 * Sign up button for player #1.
	 */
	private Button signUp1;
	/**
	 * Sign up button for player #2.
	 */
	private Button signUp2;
	
	/**
	 * Creates the {@code AuthenticationScene} object.
	 * Starts the primaryStage (window).
	 * @param ps the primaryStage (window).
	 */
	public AuthenticationScene(Stage ps)
	{
		boolean isBothLoggedIn=false;
		boolean isU1LoggedIn=false;
		boolean isU2LoggedIn=false;
		
		primaryStage=ps;
		try {
			
			this.start(primaryStage);
			//dbf= new DbFunctionsImpJDBC();
			//dbf = new DbFunctionsImplHIB();
			dbf = new DbFunctionsImpJSON();
			
		} catch (Exception e) {
			Logging.getLogger().error(e.getMessage());
		}
		Logging.getLogger().info("AuthenticationScene object created");
	}
	
	/**
	 * Initialises the layout.
	 */
	public void initLayout()
	{
		 gpane4Au1= new GridPane();
		 gpane4Au2= new GridPane();
		 gpane4Au3= new GridPane();
		 gpane4Au4= new GridPane();
		 vbox1= new VBox();
		 vbox3= new VBox();
		 vbox2= new VBox();
		
		gpane4Au1.setPadding(new Insets(10,10,10,10));
		gpane4Au1.setHgap(10);
		gpane4Au1.setVgap(10);
		
		gpane4Au2.setPadding(new Insets(10,10,10,10));
		gpane4Au2.setHgap(10);
		gpane4Au2.setVgap(10);
		
		gpane4Au3.setPadding(new Insets(10,10,10,10));
		gpane4Au3.setHgap(10);
		
		gpane4Au4.setPadding(new Insets(10,10,10,10));
		gpane4Au4.setHgap(10);
		
		 pl1= new Label("Player #1 - "+(char)10006);
		 pl2= new Label("Player #2 - "+(char)9679);
		 uname1= new Label("Username: ");
		 passw1= new Label("Password: ");
		 uname2= new Label("Username: ");
		 passw2= new Label("Password: ");
		 pl1Error= new Label();
		 pl2Error= new Label();
		
		 utext1= new TextField();
		 ptext1= new PasswordField();
		 utext2= new TextField();
		 ptext2= new PasswordField();
		
		 login1 = new Button("Login");
		 signUp1= new Button("Sign Up");
		 login2 = new Button("Login");
		 signUp2= new Button("Sign Up");
		
		gpane4Au1.setConstraints(pl1,0,0);
		gpane4Au1.setConstraints(uname1,0,1);
		gpane4Au1.setConstraints(utext1,1,1);
		gpane4Au1.setConstraints(passw1,0,2);
		gpane4Au1.setConstraints(ptext1,1,2);
		
		gpane4Au2.setConstraints(pl2,0,0);
		gpane4Au2.setConstraints(uname2,0,1);
		gpane4Au2.setConstraints(utext2,1,1);
		gpane4Au2.setConstraints(passw2,0,2);
		gpane4Au2.setConstraints(ptext2,1,2);
		
		gpane4Au3.setConstraints(login1,0,0);
		gpane4Au3.setConstraints(signUp1,1,0);
		gpane4Au3.setConstraints(pl1Error,2,0);
		
		gpane4Au4.setConstraints(login2,0,0);
		gpane4Au4.setConstraints(signUp2,1,0);
		gpane4Au4.setConstraints(pl2Error,2,0);
		
		gpane4Au1.getChildren().addAll(pl1,uname1,passw1,utext1,ptext1);
		gpane4Au2.getChildren().addAll(pl2,uname2,passw2,utext2,ptext2);
		gpane4Au3.getChildren().addAll(login1,signUp1,pl1Error);
		gpane4Au4.getChildren().addAll(login2,signUp2,pl2Error);
		
		vbox1.getChildren().addAll(gpane4Au1,gpane4Au3);
		vbox2.getChildren().addAll(gpane4Au2,gpane4Au4);
		vbox3.getChildren().addAll(vbox1,vbox2);
		
		Logging.getLogger().info("Authentication layout created");
	}
	
	/**
	 * Initialises the window by calling {@link #initLayout()};
	 * Sets the scene.
	 * Closes the database connection on exit.
	 * Contains the actions for the buttons.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
				
	initLayout();

	Scene authenticationScene= new Scene(vbox3,330,330);
	
	primaryStage.setScene(authenticationScene);
	primaryStage.setResizable(false);
	primaryStage.setTitle("Login - Sign in");
	primaryStage.show();
	
    primaryStage.setOnCloseRequest(e->{
    	System.out.println("closing..");
    	dbf.closeConnection();
    	Logging.getLogger().info("AuthenticationScene closed");
    });
	
	login1.setOnAction(e->{
		
		login(utext1,ptext1,pl1Error,'X');
		startGame(primaryStage);
		Logging.getLogger().info("login1 clicked");
	});
	
	
	login2.setOnAction(e->{
		login(utext2,ptext2,pl2Error,'O');
		startGame(primaryStage);
		Logging.getLogger().info("login2 clicked");
	});
	
	signUp1.setOnAction(e->{
		signup( utext1,  ptext1,  pl1Error, 'X');
		startGame(primaryStage);
		Logging.getLogger().info("signup1 clicked");
	});
	
	signUp2.setOnAction(e->{
		signup( utext2,  ptext2,  pl2Error, 'O');
		startGame(primaryStage);
		Logging.getLogger().info("signup2 clicked");
	});
		
	}
	
	/**
	 * Sets {@code isBothLoggedIn} to true, if all the two players are logged in.
	 */
	private void checkIfBothLoggedIn()
	{
		if(isU1LoggedIn==true && isU2LoggedIn==true)
		{
			isBothLoggedIn=true;
		}
	}
	
	/**
	 * Logs the user in with given user name and password.
	 * If the user name/password wrong, it displays an error message.
	 * 
	 * @param utext for user name
	 * @param ptext for password
	 * @param plError for error message
	 * @param XO for initialising the player.
	 */
	private void login(TextField utext, TextField ptext, Label plError,char XO)
	{
		String username=utext.getText();
		String passw=ptext.getText();
		char xOrO=XO;
		player =dbf.login(username, passw, xOrO);
		
		if(player !=null)
		{
			InitPlayers(XO);
			plError.setText("Logged in");
			Logging.getLogger().info("{} logged in", username);
		}
		else
		{
			plError.setText("Wrong uname/passw !");
			Logging.getLogger().warn("Wrong username/passw");
		}
	}
	
	/**
	 * Signs up the user if the user name is not in the users table.
	 * 
	 * @param utext for user name
	 * @param ptext for password
	 * @param plError for error
	 * @param XO for initialising the player.
	 */
	private void signup(TextField utext, TextField ptext, Label plError,char XO)
	{
		String username=utext.getText();
		String passw=ptext.getText();
		char xOrO=XO;
		player=dbf.signUp(username, passw,xOrO);
		
		if(player!=null)
		{
			InitPlayers(XO);
			plError.setText("Signed in");
			Logging.getLogger().info("{} signed in", username);
		}
		else
		{
			plError.setText("Wrong uname/passw !");
			Logging.getLogger().warn("Wrong username/passw");
		}
	}
	
	/**
	 * player #1 will be with X, player #2 with O.
	 * @param XO X or O to create players.
	 */
	private void InitPlayers(char XO)
	{
		if(XO=='X')
		{
			player1=player;
			isU1LoggedIn=true;
		}
		else{
			player2=player;
			isU2LoggedIn=true;
		}
	}
	
	/**
	 * Starts the authentication window.
	 * @param primaryStage the window
	 */
	private void startGame(Stage primaryStage)
	{
		checkIfBothLoggedIn();
		
		if(isBothLoggedIn)
		{
			mainControl= new Logic(player1,player2);
			scene = new GameScene(dbf,mainControl);
			try {
				scene.start(primaryStage);
				Logging.getLogger().info("AuthenticationScene started");
			} catch (Exception e) {
				Logging.getLogger().error(e.getMessage());
			}	
		}
	}

}
