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
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("TCJPClueLayout.csv", "TCJPClueLayoutLegend.txt", "ClueCards.txt", "Players.txt");
		board.initialize();
	}
	
	@Test
	public void loadingThePeople() {
		HumanPlayer testHumanPlayer = board.gethumanPlayer();
		ComputerPlayer[] testComputerArray = board.getComputerPlayers();
		
		assertEquals("John Doe", testHumanPlayer.getPlayerName());
		assertEquals("Jane Doe", testComputerArray[0].getPlayerName());
		assertEquals("Janet Fawn", testComputerArray[4].getPlayerName());
		assertEquals(Color.orange, testHumanPlayer.getColor());
		assertEquals(Color.magenta, testComputerArray[0].getColor());
		assertEquals(Color.yellow, testComputerArray[4].getColor());
		assertEquals(18, testHumanPlayer.getRow());
		assertEquals(24, testComputerArray[0].getRow());
		assertEquals(0, testComputerArray[4].getRow());
		assertEquals(0, testHumanPlayer.getColumn());
		assertEquals(10, testComputerArray[0].getColumn());
		assertEquals(3, testComputerArray[4].getColumn());
	}
	
	@Test
	public void loadingTheDeckOfCards(){
		Set<Card> deckOfCards = board.getDeckOfCards();

		assertEquals(21, deckOfCards.size());
		assertEquals(9, board.getNumberOfRoomCards());
		assertEquals(6, board.getNumberOfWeaponCards());
		assertEquals(6, board.getNumberOfPersonCards());
		boolean a = false;
		boolean b = false;
		boolean c = false;
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
	
	

}
