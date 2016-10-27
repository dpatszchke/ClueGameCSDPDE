package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class gameActionTests {
	// variable for board
		private static Board board;
		
	// @Before to initialize board
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("TCJPClueLayout.csv", "TCJPClueLayoutLegend.txt", "ClueCards.txt", "Players.txt");
		board.initialize();
	}
	
	@Test
	public void testTargetWithRoom() {
		ComputerPlayer player = new ComputerPlayer("John Doe", Color.blue , 4 , 19, board.getDeckOfCards());
		board.calcTargets(4, 19, 3);
		BoardCell tempDoor = board.getCellAt(7, 19);
		//System.out.println(player.pickLocation(board.getTargets()));
		for (int i=0; i<100; i++) {		
			player.setLastCellVisited(board.getCellAt(2, 12));
			assertEquals(tempDoor, player.pickLocation(board.getTargets()));
		}
	}
	
	@Test
	public void testTargetRandomSelection() {
	    ComputerPlayer player = new ComputerPlayer("John Doe", Color.blue , 16 , 5, board.getDeckOfCards());
	    // Pick a location with no rooms in target, just three targets
	    board.calcTargets(16, 5, 3);
	    boolean loc_13_5 = false;
	    boolean loc_19_5 = false;
	    boolean loc_18_4 = false;
	    // Run the test a large number of times
	    for (int i=0; i<100; i++) {
	        BoardCell selected = player.pickLocation(board.getTargets());
	        if (selected == board.getCellAt(13, 5))
	            loc_13_5 = true;
	        else if (selected == board.getCellAt(19, 5))
	            loc_19_5 = true;
	        else if (selected == board.getCellAt(18, 4))
	            loc_18_4 = true;
	        else
	            fail("Invalid target selected");
	        }
	        // Ensure each target was selected at least once
	        assertTrue(loc_13_5);
	        assertTrue(loc_19_5);
	        assertTrue(loc_18_4);				
	}
	
	@Test
	public void testTargetSelectionAfterLeavingRoom() {
	    ComputerPlayer player = new ComputerPlayer("John Doe", Color.blue , 8 , 0, board.getDeckOfCards());
	    // Pick a location with no rooms in target, just three targets
	    player.setLastCellVisited(new BoardCell(10,0,'K'));
	    board.calcTargets(8, 0, 2);
	    boolean loc_6_0 = false;
	    boolean loc_7_1 = false;
	    boolean loc_10_0 = false;
	    // Run the test a large number of times
	    for (int i=0; i<100; i++) {
	        BoardCell selected = player.pickLocation(board.getTargets());
	        if (selected == board.getCellAt(6, 0))
	            loc_6_0 = true;
	        else if (selected == board.getCellAt(7, 1))
	            loc_7_1 = true;
	        else if (selected == board.getCellAt(10, 0))
	            loc_10_0 = true;
	        else
	            fail("Invalid target selected");
	        }
	        // Ensure each target was selected at least once
	        assertTrue(loc_6_0);
	        assertTrue(loc_7_1);
	        assertTrue(loc_10_0);				
	}


	@Test
	public void checkingAccusations() {
		board.setTheAnswer("Jim Buck", "Kitchen" , "Shot Gun");
		Solution correctSolution = new Solution("Jim Buck", "Kitchen" , "Shot Gun");
		assert(board.checkAccusation(correctSolution));
		Solution wrongPerson = new Solution("John Doe", "Kitchen" , "Shot Gun");
		assert(!(board.checkAccusation(wrongPerson)));
		Solution wrongRoom = new Solution("Jim Buck", "Patio" , "Shot Gun");
		assert(!(board.checkAccusation(wrongRoom)));
		Solution wrongWeapon = new Solution("Jim Buck", "Kitchen" , "Skinning Knife");
		assert(!(board.checkAccusation(wrongWeapon)));
	}
	
	@Test
	public void dissprovingSuggesstion() {
		fail();
		
	}
	
	@Test
	public void handlingSuggestion() {
		fail();
	}
	
	@Test
	public void creatingSuggestion() {
		ComputerPlayer[] computerPlayers = board.getComputerPlayers();
		
		computerPlayers[0].setRow(3);
		computerPlayers[0].setColumn(2);
		computerPlayers[0].createSuggestion(board.getCellAt(computerPlayers[0].getRow(),computerPlayers[0].getColumn()), board);
		assertEquals("Gym", computerPlayers[0].getSuggestion().room);
		
		//one weapon or one player has not been seen
		computerPlayers[0].removeFromUnseenForOneWeaponandPerson();
		computerPlayers[0].createSuggestion(board.getCellAt(computerPlayers[0].getRow(),computerPlayers[0].getColumn()), board);
		assertEquals("Shot Gun", computerPlayers[0].getSuggestion().weapon);
		assertEquals("John Doe", computerPlayers[0].getSuggestion().person);
		
		boolean shotGun = false;
		boolean blackPowderRifle = false;
		boolean johnDoe = false;
		boolean joeBuck = false;
		computerPlayers[0].removeFromUnseenForMultipleWeaponsAndPersons();
		for(int i = 0; i < 25; i++){
			computerPlayers[0].createSuggestion(board.getCellAt(computerPlayers[0].getRow(),computerPlayers[0].getColumn()), board);
			if (computerPlayers[0].getSuggestion().person == "John Doe"){
				johnDoe = true;
			}
			if (computerPlayers[0].getSuggestion().person == "Joe Buck"){
				joeBuck = true;
			}
			if (computerPlayers[0].getSuggestion().weapon == "Black Powder Rifle"){
				blackPowderRifle = true;
			}
			if (computerPlayers[0].getSuggestion().weapon == "Shot Gun"){
				shotGun = true;
			}
		}
		assert(shotGun);
		assert(blackPowderRifle);
		assert(joeBuck);
		assert(johnDoe);
	}
}
