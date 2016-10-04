package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class TCJP_FileInitTests {
	// Constants that I will use to test whether the file was loaded correctly
		public static final int LEGEND_SIZE = 11;
		public static final int NUM_ROWS = 25;
		public static final int NUM_COLUMNS = 24;

		// NOTE: I made Board static because I only want to set it up one 
		// time (using @BeforeClass), no need to do setup before each test.
		private static Board board;
		
		@BeforeClass
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("TCJPClueLayout.csv", "TCJPClueLayoutLegend.txt");		
			// Initialize will load BOTH config files 
			board.initialize();
		}
		@Test
		public void testRooms() {
			// Get the map of initial => room 
			Map<Character, String> legend = board.getLegend();
			// Ensure we read the correct number of rooms
			assertEquals(LEGEND_SIZE, legend.size());
			// To ensure data is correctly loaded, test retrieving a few rooms 
			// from the hash, including the first and last in the file and a few others
			assertEquals("Server Room", legend.get('S'));
			assertEquals("Gym", legend.get('G'));
			assertEquals("Workshop", legend.get('R'));
			assertEquals("Laboratory", legend.get('B'));
			assertEquals("Patio", legend.get('P'));
			assertEquals("Walkway", legend.get('w'));
			
			//That's a majority so we should be okay
		}
		
		@Test
		public void testBoardDimensions() {
			// Ensure we have the proper number of rows and columns
			assertEquals(NUM_ROWS, board.getNumRows());
			assertEquals(NUM_COLUMNS, board.getNumColumns());		
		}
		
		// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
		// two cells that are not a doorway.
		// These cells are white on the planning spreadsheet
		@Test
		public void FourDoorDirections() {			
			BoardCell room = board.getCellAt(3, 2); //Gym door
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
			room = board.getCellAt(17, 2); //South kitchen door
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.DOWN, room.getDoorDirection()); 
			room = board.getCellAt(20, 11); //Lab door
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.LEFT, room.getDoorDirection());
			room = board.getCellAt(14, 23); //North observatory door
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.UP, room.getDoorDirection());
			// Test that room pieces that aren't doors know it
			room = board.getCellAt(0, 14); //Northern gallery wall
			assertFalse(room.isDoorway());	
			// Test that walkways are not doors
			BoardCell cell = board.getCellAt(6, 3); //Hallway to kitchen
			assertFalse(cell.isDoorway());		

		}
		
		// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCellAt(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(16, numDoors); //Counted by hand for our layout
		}

		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRoomInitials() {
			// Test first cell in room
			assertEquals('G', board.getCellAt(0, 0).getInitial());
			assertEquals('R', board.getCellAt(0, 5).getInitial());
			assertEquals('Y', board.getCellAt(0, 12).getInitial());
			// Test last cell in room
			assertEquals('K', board.getCellAt(4, 17).getInitial());
			assertEquals('S', board.getCellAt(20, 4).getInitial());
			// Test cells not against walls
			assertEquals('L', board.getCellAt(4, 22).getInitial());
			assertEquals('O', board.getCellAt(16, 21).getInitial());	
			assertEquals('B', board.getCellAt(20, 13).getInitial());
			assertEquals('P', board.getCellAt(23, 7).getInitial());		
			
			// All rooms tested and accounted for
			// Test a walkway
			assertEquals('w', board.getCellAt(1, 3).getInitial());
			// Test the closet
			assertEquals('C', board.getCellAt(11, 10).getInitial());
		}
		

}
