package pkg;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pkg.Controller.Logic;
import pkg.util.Direction;
import pkg.util.Player;


public class GameTest {

	private Logic game;
	private Logic game2;
	private Logic game3;
	private Player p1;
	private Player p2;
	
	@Before
	public void setup()
	{
		
		p1= new Player(); p1.setUsername("admin");
		p2= new Player(); p2.setUsername("admin2");

		p1.setxOrO('X'); 
		p2.setxOrO('O'); 
		
		game= new Logic(p1,p2);
		game2= new Logic(p1,p2);
		game3= new Logic(p1,p2);
		
		game.getBoard().put(0, 0,'X');
		game.getBoard().put(1, 0,'X');
		game.getBoard().put(2, 0,'X');
		game.getBoard().put(3, 0,'X');
		game.getBoard().put(4, 0,'X');
		
		game2.getBoard().put(0, 0,'O');
		game2.getBoard().put(1, 0,'O');
		game2.getBoard().put(2, 0,'O');
		game2.getBoard().put(3, 0,'O');
		game2.getBoard().put(4, 0,'O');
		///////////////////////////////////////////tie
		int count=0;
		char character='O';
		
		for(int x=0;x<game3.getBoard().BOARD_SIZE;x++)
		{
			for(int y=0;y<game3.getBoard().BOARD_SIZE;y++)
			{
				count++;
				
				if(count==4)
				{
					count =0;
					if(character =='X')
					{
						character='O';
					}
					else{
						character='X';
					}
				}
				game3.getBoard().put(x, y, character);
			}
		}
		count=0;
	}
	
	@Test
	public void TieTest()
	{
		assertEquals(true,game3.checkTie());
	}
	
	@Test
	public void WhichWinTest()
	{
		assertEquals(1,game.WhichWin());
	}
	@Test
	public void WhichWinTest2()
	{
		assertEquals(2,game2.WhichWin());
	}

}
