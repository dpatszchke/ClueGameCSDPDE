package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

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
		assert("John Doe", human.getPlayerName());
		assert("Jane Doe", computer1.getPlayerName());
		assert("Janet Fawn", computer5.getPlayername());
		assert(Color.orange, human.getColor());
		assert(Color.magenta, computer1.getColor());
		assert(Color.yellow, computer5.getColor());
		assert(18, human.getRow());
		assert(24, computer1.getRow());
		assert(0, computer5.getRow());
		assert(0, human.getColumn());
		assert(10, computer1.getColumn());
		assert(3, computer5.getColumn());
	}
	
	

}
