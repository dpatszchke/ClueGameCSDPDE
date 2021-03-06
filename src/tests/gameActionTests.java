package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {
	// variable for board
		private static Board board;
		
	// @Before to initialize board
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = new Board();
		board.setConfigFiles("TCJPClueLayout.csv", "TCJPClueLayoutLegend.txt", "ClueCards.txt", "Players.txt");
		board.initialize();
	}
	
	
	//Select a target tests
	@Test
	public void testTargetWithRoom() {       // if room in list that was not just visited, must select it
		ComputerPlayer player = new ComputerPlayer("John Doe", Color.blue , 4 , 19, board.getDeckOfCards());
		board.calcTargets(4, 19, 3);
		BoardCell tempDoor = board.getCellAt(7, 19);
		for (int i=0; i<100; i++) {		
			player.setLastCellVisited(board.getCellAt(2, 12));
			assertEquals(tempDoor, player.pickLocation(board.getTargets()));
		}
	}
	
	@Test
	public void testTargetRandomSelection() {       //if no rooms in list, select randomly
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
	public void testTargetSelectionAfterLeavingRoom() {        //if room just visited is in list, each target (including room) selected randomly
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
	public void checkingAccusations() {     //Make an accusation. Tests include
		board.setTheAnswer("Jim Buck", "Kitchen" , "Shot Gun");
		Solution correctSolution = new Solution("Jim Buck", "Kitchen" , "Shot Gun");
		assert(board.checkAccusation(correctSolution));             //solution that is correct
		Solution wrongPerson = new Solution("John Doe", "Kitchen" , "Shot Gun");
		assert(!(board.checkAccusation(wrongPerson)));              //solution with wrong person
		Solution wrongRoom = new Solution("Jim Buck", "Patio" , "Shot Gun");
		assert(!(board.checkAccusation(wrongRoom)));               //solution with wrong weapon
		Solution wrongWeapon = new Solution("Jim Buck", "Kitchen" , "Skinning Knife");
		assert(!(board.checkAccusation(wrongWeapon)));             //solution with wrong room
	}
	
	@Test
	public void dissprovingSuggesstion() {
		ComputerPlayer[] computerPlayers = board.getComputerPlayers();
		computerPlayers[0].setSuggestion("Jim Buck", "Gym", "Shot Gun");
		
		computerPlayers[0].setUpDissprovingSuggestionTestWithNoMatch();
		assertEquals(null, computerPlayers[0].dissproveSuggestion(computerPlayers[0].getSuggestion()));      //If player has no matching cards, null is returned
		
		computerPlayers[0].setUpDissprovingSuggestionTestWithOneMatch();
		Card tempCard = new Card("Jim Buck", CardType.PERSON);
		assertEquals(tempCard, computerPlayers[0].dissproveSuggestion(computerPlayers[0].getSuggestion()));   // If player has only one matching card it should be returned
		
		computerPlayers[0].setUpDissprovingSuggestionTestWithMultipleMatches();
		boolean shotGun = false;
		boolean jimBuck = false;
		computerPlayers[0].removeFromUnseenForMultipleWeaponsAndPersons();
		for(int i = 0; i < 25; i++){                         //Used to make sure both are selected in 25 tests
			if (computerPlayers[0].dissproveSuggestion(computerPlayers[0].getSuggestion()).getCardName() == "Shot Gun"){
				shotGun = true;
			}
			if (computerPlayers[0].dissproveSuggestion(computerPlayers[0].getSuggestion()).getCardName() == "Jim Buck"){
				jimBuck = true;
			}
		}
		assert(jimBuck);   //If players has >1 matching card, returned card should be chosen randomly
		assert(shotGun);
	}
	
	@Test
	public void handlingSuggestion() {
		ArrayList<Player> testPlayers = board.setUpHandlingSuggestionEnviroment();
		Solution nobodyCanDisprove = new Solution("Jill Buck", "Workshop", "Skinning Knife");
		//assertEquals(null, board.handleSuggestion(nobodyCanDisprove, testPlayers, 0));      //Suggestion no one can disprove returns null
		
		Solution accuserCanDissprove = new Solution("Jill Buck", "Workshop", "Shot Gun");
		//assertEquals(null, board.handleSuggestion(accuserCanDissprove, testPlayers, 0));     //Suggestion only accusing player can disprove returns null
		
		Solution humanCanDissprove = new Solution("Jill Buck", "Workshop", "Shot Gun");
		//assertEquals("Shot Gun", board.handleSuggestion(humanCanDissprove, testPlayers, 1).getCardName());    //Suggestion only human can disprove returns answer
		
		Solution humanCanDissproveButIsAccuser = new Solution("Jill Buck", "Workshop", "Shot Gun");
		//assertEquals(null, board.handleSuggestion(humanCanDissproveButIsAccuser, testPlayers, 0));    //Suggestion only human can disprove, but human is accuser, returns null
		
		Solution twoPlayersCandissprove = new Solution("James Fawn", "Gym", "Skinning Knife");
		//assertEquals("Gym", board.handleSuggestion(twoPlayersCandissprove, testPlayers, 0).getCardName());           //Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
		
		Solution humanAndComputerCanDissprove = new Solution("James Fawn", "Workshop", "Shot Gun");
		//assertEquals("James Fawn", board.handleSuggestion(humanAndComputerCanDissprove, testPlayers, 1).getCardName());     //Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
	}
	
	@Test
	public void creatingSuggestion() {
		ComputerPlayer[] computerPlayers = board.getComputerPlayers();
		
		computerPlayers[0].setRow(3);
		computerPlayers[0].setColumn(2);
		computerPlayers[0].createSuggestion(board.getCellAt(computerPlayers[0].getRow(),computerPlayers[0].getColumn()), board);
		assertEquals("Gym", computerPlayers[0].getSuggestion().room);             //Room matches current location
		
		//one weapon or one player has not been seen
		computerPlayers[0].removeFromUnseenForOneWeaponandPerson();
		computerPlayers[0].createSuggestion(board.getCellAt(computerPlayers[0].getRow(),computerPlayers[0].getColumn()), board);
		assertEquals("Shot Gun", computerPlayers[0].getSuggestion().weapon);              //If only one weapon not seen, it's selected
		assertEquals("John Doe", computerPlayers[0].getSuggestion().person);              //If only one person not seen, it's selected
		
		boolean shotGun = false;
		boolean blackPowderRifle = false;
		boolean johnDoe = false;
		boolean jimBuck = false;
		computerPlayers[0].removeFromUnseenForMultipleWeaponsAndPersons(); 
		for(int i = 0; i < 25; i++){                                         //Used to make sure that in 25 tests each on is selected
			computerPlayers[0].createSuggestion(board.getCellAt(computerPlayers[0].getRow(),computerPlayers[0].getColumn()), board);
			if (computerPlayers[0].getSuggestion().person == "John Doe"){
				johnDoe = true;
			}
			if (computerPlayers[0].getSuggestion().person == "Jim Buck"){
				jimBuck = true;
			}
			if (computerPlayers[0].getSuggestion().weapon == "Black Powder Rifle"){
				blackPowderRifle = true;
			}
			if (computerPlayers[0].getSuggestion().weapon == "Shot Gun"){
				shotGun = true;
			}
		}
		assert(shotGun);                        //If multiple weapons not seen, one of them is randomly selected
		assert(blackPowderRifle);
		assert(jimBuck);
		assert(johnDoe);                        //If multiple persons not seen, one of them is randomly selected
	}
}
