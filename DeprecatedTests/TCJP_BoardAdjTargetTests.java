package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class TCJP_BoardAdjTargetTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("TCJPClueLayout.csv", "TCJPClueLayoutLegend.txt");
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are dark blue (white font) on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(22, 14);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(4, 12);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the walkway leading in
	// Test cells are pink on spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT (server room)
		Set<BoardCell> testList = board.getAdjList(24, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(24, 4)));
		// TEST DOORWAY LEFT (lab)
		testList = board.getAdjList(20, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(20, 10)));
		//TEST DOORWAY DOWN (workshop)
		testList = board.getAdjList(5, 7);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 7)));
		//TEST DOORWAY UP (patio)
		testList = board.getAdjList(20, 7);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(19, 7)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are sea green in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT (server room)
		Set<BoardCell> testList = board.getAdjList(24, 4);
		assertTrue(testList.contains(board.getCellAt(24, 3)));
		assertTrue(testList.contains(board.getCellAt(23, 4)));
		assertEquals(2, testList.size());
		// Test beside a door direction DOWN (workshop room south entrance)
		testList = board.getAdjList(6, 7);
		assertTrue(testList.contains(board.getCellAt(5, 7)));
		assertTrue(testList.contains(board.getCellAt(7, 7)));
		assertTrue(testList.contains(board.getCellAt(6, 8)));
		assertTrue(testList.contains(board.getCellAt(6, 6)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT (lab door)
		testList = board.getAdjList(20, 10);
		assertTrue(testList.contains(board.getCellAt(19, 10)));
		assertTrue(testList.contains(board.getCellAt(21, 10)));
		assertTrue(testList.contains(board.getCellAt(20, 11)));
		assertTrue(testList.contains(board.getCellAt(20, 9)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP (central patio door)
		testList = board.getAdjList(19, 7);
		assertTrue(testList.contains(board.getCellAt(19, 6)));
		assertTrue(testList.contains(board.getCellAt(19, 8)));
		assertTrue(testList.contains(board.getCellAt(20, 7)));
		assertEquals(3, testList.size());
	}

	// Test walkway scenarios
	// Test cells are orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test south narrow corridor, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(24, 10);
		assertTrue(testList.contains(board.getCellAt(23, 10)));
		assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(7, 0);
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 1)));
		assertTrue(testList.contains(board.getCellAt(8, 0)));
		assertEquals(3, testList.size());

		// Test on right edge, walkways down and left
		testList = board.getAdjList(12, 23);
		assertTrue(testList.contains(board.getCellAt(12, 22)));
		assertTrue(testList.contains(board.getCellAt(13, 23)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(7,5);
		assertTrue(testList.contains(board.getCellAt(7, 6)));
		assertTrue(testList.contains(board.getCellAt(7, 4)));
		assertTrue(testList.contains(board.getCellAt(8, 5)));
		assertTrue(testList.contains(board.getCellAt(6, 5)));
		assertEquals(4, testList.size());
		
		// Test on top edge of board, next to 1 room piece
		testList = board.getAdjList(0, 3);
		assertTrue(testList.contains(board.getCellAt(1, 3)));
		assertTrue(testList.contains(board.getCellAt(0, 4)));
		assertEquals(2, testList.size());
		
		// Test on walkway next to library door, not in correct direction
		testList = board.getAdjList(7, 18);
		assertTrue(testList.contains(board.getCellAt(6, 18)));
		assertTrue(testList.contains(board.getCellAt(7, 17)));
		assertTrue(testList.contains(board.getCellAt(8, 18)));
		assertEquals(3, testList.size());
	}	
	
	// Tests along walkways, 2 steps
	// These are orange on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(7, 5, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 3)));
		assertTrue(targets.contains(board.getCellAt(7, 7)));
		assertTrue(targets.contains(board.getCellAt(6, 4)));
		assertTrue(targets.contains(board.getCellAt(6, 6)));
		assertTrue(targets.contains(board.getCellAt(9, 5)));		
	}
	
	// Tests of just walkways, 3 steps
	// These are orange on the planning spreadsheet
	@Test
	public void testTargetsThreeSteps() {
		board.calcTargets(0, 3, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 3)));
		assertTrue(targets.contains(board.getCellAt(2, 4)));
		assertTrue(targets.contains(board.getCellAt(0, 4)));
		assertTrue(targets.contains(board.getCellAt(1, 3)));
		
		board.calcTargets(24, 10, 3);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 10)));
	}	
	
	// Tests of just walkways plus one door, 4 steps
	// These are orange on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(13, 17, 4);
		Set<BoardCell> targets= board.getTargets();		
		assertTrue(targets.contains(board.getCellAt(9, 17))); 
		assertTrue(targets.contains(board.getCellAt(17, 17)));	
		assertTrue(targets.contains(board.getCellAt(12, 20)));	
		assertTrue(targets.contains(board.getCellAt(13, 21)));	
		assertTrue(targets.contains(board.getCellAt(16, 18)));	
		assertTrue(targets.contains(board.getCellAt(15, 17)));	
		assertTrue(targets.contains(board.getCellAt(14, 18)));	
		assertTrue(targets.contains(board.getCellAt(12, 18)));  
		assertTrue(targets.contains(board.getCellAt(11, 17)));	
		assertTrue(targets.contains(board.getCellAt(10, 18)));	
		assertTrue(targets.contains(board.getCellAt(13, 19)));  
		assertEquals(11, targets.size());
	}	
	
	// Test getting into a room
	// These are orange on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// Only one door
		board.calcTargets(7, 0, 5);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		// Northern kitchen door
		assertTrue(targets.contains(board.getCellAt(10, 0)));
		// Other spaces
		assertTrue(targets.contains(board.getCellAt(5, 3))); 
		assertTrue(targets.contains(board.getCellAt(6, 4))); 
		assertTrue(targets.contains(board.getCellAt(7, 3))); 
		assertTrue(targets.contains(board.getCellAt(7, 1))); 
		assertTrue(targets.contains(board.getCellAt(7, 5))); 
		assertTrue(targets.contains(board.getCellAt(6, 0))); 
		assertTrue(targets.contains(board.getCellAt(6, 2))); 
		
		// Multiple doors
		board.calcTargets(0, 3, 6);
		targets= board.getTargets();
		
		// Gym door
		assertTrue(targets.contains(board.getCellAt(3, 2)));
		// West workshop door
		assertTrue(targets.contains(board.getCellAt(2, 5)));
		// Other spaces
		assertTrue(targets.contains(board.getCellAt(6, 3))); //
		assertTrue(targets.contains(board.getCellAt(5, 4))); //
		assertTrue(targets.contains(board.getCellAt(3, 4))); //
		assertTrue(targets.contains(board.getCellAt(1, 4)));  
		assertTrue(targets.contains(board.getCellAt(2, 3)));
		assertTrue(targets.contains(board.getCellAt(4, 3)));
		assertEquals(8, targets.size());

	}	
	
	// Test getting out of a room
	// These are orange on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step from workshop south
		board.calcTargets(5, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 7)));
		
		// Now, go for a walk from server
		board.calcTargets(24, 3, 6);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 5)));
	}

}
