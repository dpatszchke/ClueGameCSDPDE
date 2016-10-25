package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

public class DEDPCluePlayerTests {
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
	
	// test for loading the players
	@Test
	public void loadingThePeople() {
		// variable for the test player
		HumanPlayer testHumanPlayer = board.gethumanPlayer();
		// variable for the computer players
		ComputerPlayer[] testComputerArray = board.getComputerPlayers();
		
		// Ensure players name is John Doe
		assertEquals("John Doe", testHumanPlayer.getPlayerName());
		// Ensure fist computer player is Jane Doe
		assertEquals("Jane Doe", testComputerArray[0].getPlayerName());
		// Ensure last computer player is Janet Fawn
		assertEquals("Janet Fawn", testComputerArray[4].getPlayerName());
		// Ensure human's color is orange
		assertEquals(Color.orange, testHumanPlayer.getColor());
		// Ensure first computer player is magenta
		assertEquals(Color.magenta, testComputerArray[0].getColor());
		// Ensure fifth computer player is yellow
		assertEquals(Color.yellow, testComputerArray[4].getColor());
		// Determine player is in row 18
		assertEquals(18, testHumanPlayer.getRow());
		// Determine the first computer player is in row 24
		assertEquals(24, testComputerArray[0].getRow());
		// Determine the fifth computer player is in row 0
		assertEquals(0, testComputerArray[4].getRow());
		// Determine player is in column 0
		assertEquals(0, testHumanPlayer.getColumn());
		// Determine the first computer player is in column 10
		assertEquals(10, testComputerArray[0].getColumn());
		// Determine the fifth computer player is in column 3
		assertEquals(3, testComputerArray[4].getColumn());
	}
	
	// test for loading the deck of cards
	@Test
	public void loadingTheDeckOfCards(){
		Set<Card> deckOfCards = board.getDeckOfCards();

		// size of deck is 21 cards
		assertEquals(21, deckOfCards.size());
		// Ensure there are the correct number of card types
		assertEquals(9, board.getNumberOfRoomCards());
		assertEquals(6, board.getNumberOfWeaponCards());
		assertEquals(6, board.getNumberOfPersonCards());
		// 
		boolean a = false;
		boolean b = false;
		boolean c = false;
		// loop through deck of cards, check to see if John Doe, Cross Bow,
		// and Gallery are all in the deck
		for(Card currentCard: deckOfCards){
			if(currentCard.getCardName().equals("John Doe")){
				a = true;
			}else if(currentCard.getCardName().equals("Cross Bow")){
				b = true;
			}else if(currentCard.getCardName().equals("Gallery")){
				c = true;
			}else{
				
			}
		}
		assert (a);
		assert (b);
		assert (c);
	}
	
	// test for dealing the cards to players
	@Test
	public void dealingOfTheCards(){
		// variable to store deck of cards
		Card[] dealtCards = board.getDealtCards();
		// variable to store information for computer players
		ComputerPlayer[] temp = board.getComputerPlayers();
		
		// Ensure player has 3 or 4 cards
		assert(board.gethumanPlayer().getPlayerCards().size() == 4 || board.gethumanPlayer().getPlayerCards().size() == 3);
		for (int i = 0;i <5; i++){
			// ensure each computer player has 3-4 cards per hand
			assert(temp[i].getPlayerCards().size() == 4 || temp[i].getPlayerCards().size() == 3);
		}

		// ensure 21 cards were dealt
		assertEquals(21, dealtCards.length);
		// loop through each card
		for (int i = 0; i < 21; i++){
			// assign card to temporary variable
			Card tempCard = dealtCards[i];
			// loop through dealt cards for
			for (int j = 0; j < 21; j++){
				// ensure the card appears only once
				if(j == i){	
				}else{
					assert(tempCard != dealtCards[j]);
				}
			}
		}
	}
	
}
