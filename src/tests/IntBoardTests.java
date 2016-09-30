package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	
	private IntBoard disBoard;
	
	@Before //We'll setup a new board for each test 
	public void setupBoard() {
		disBoard = new IntBoard(IntBoard.GRID_ROWS, IntBoard.GRID_COLS);
	}

	@Test
	public void testAdj00() { //Test adjacency for 0,0 (upper left)
		BoardCell cell = disBoard.getCell(0,0);
		Set<BoardCell> testList = disBoard.getAdjList(cell); //Set cell of interest and get adjacency list
		
		assertTrue(testList.contains(disBoard.getCell(0,1))); //Check for each adjacent cell
		assertTrue(testList.contains(disBoard.getCell(1,0)));
		assertEquals(2, testList.size()); //Check to ensure no other cells got in there somehow
	}
	
	@Test
	public void testAdj33() { //Test adjacency for 3,3 (lower right)
		BoardCell cell = disBoard.getCell(3,3);
		Set<BoardCell> testList = disBoard.getAdjList(cell); //Set cell of interest and get adjacency list
		
		assertTrue(testList.contains(disBoard.getCell(2,3))); //Check for each adjacent cell
		assertTrue(testList.contains(disBoard.getCell(3,2)));
		assertEquals(2, testList.size()); //Check to ensure no other cells got in there somehow
	}
	
	@Test
	public void testAdj13() { //Test adjacency for 1,3 (right edge)
		BoardCell cell = disBoard.getCell(1,3);
		Set<BoardCell> testList = disBoard.getAdjList(cell); //Set cell of interest and get adjacency list
		
		assertTrue(testList.contains(disBoard.getCell(0,3))); //Check for each adjacent cell
		assertTrue(testList.contains(disBoard.getCell(2,3)));
		assertTrue(testList.contains(disBoard.getCell(1,2)));
		assertEquals(3, testList.size()); //Check to ensure no other cells got in there somehow
	}
	
	@Test
	public void testAdj30() { //Test adjacency for 3,0 (left edge)
		BoardCell cell = disBoard.getCell(3,0);
		Set<BoardCell> testList = disBoard.getAdjList(cell); //Set cell of interest and get adjacency list
		
		assertTrue(testList.contains(disBoard.getCell(2,0))); //Check for each adjacent cell
		assertTrue(testList.contains(disBoard.getCell(3,1)));
		assertEquals(2, testList.size()); //Check to ensure no other cells got in there somehow
	}
	
	@Test
	public void testAdj11() { //Test adjacency for 1,1 (second column from left)
		BoardCell cell = disBoard.getCell(1,1);
		Set<BoardCell> testList = disBoard.getAdjList(cell); //Set cell of interest and get adjacency list
		
		assertTrue(testList.contains(disBoard.getCell(0,1))); //Check for each adjacent cell
		assertTrue(testList.contains(disBoard.getCell(2,1)));
		assertTrue(testList.contains(disBoard.getCell(1,2)));
		assertTrue(testList.contains(disBoard.getCell(1,0)));
		assertEquals(4, testList.size()); //Check to ensure no other cells got in there somehow
	}
	
	@Test
	public void testAdj22() { //Test adjacency for 2,2 (second column from right)
		BoardCell cell = disBoard.getCell(2,2);
		Set<BoardCell> testList = disBoard.getAdjList(cell); //Set cell of interest and get adjacency list
		
		assertTrue(testList.contains(disBoard.getCell(2,1))); //Check for each adjacent cell
		assertTrue(testList.contains(disBoard.getCell(2,3)));
		assertTrue(testList.contains(disBoard.getCell(1,2)));
		assertTrue(testList.contains(disBoard.getCell(3,2)));
		assertEquals(4, testList.size()); //Check to ensure no other cells got in there somehow
	}
	
	@Test
	public void testTarg22_1() { //Test targeting for 2,2 on roll of 1
		BoardCell cell = disBoard.getCell(2,2); //Set cell of interest
		
		disBoard.calcTargets(cell, 1);
		Set<BoardCell> testList = disBoard.getTargets(); //Set roll, calculate and return target set of cells
		
		assertTrue(testList.contains(disBoard.getCell(2,1))); //Check for each target cell
		assertTrue(testList.contains(disBoard.getCell(2,3)));
		assertTrue(testList.contains(disBoard.getCell(1,2)));
		assertTrue(testList.contains(disBoard.getCell(3,2)));
		assertEquals(4, testList.size()); //Check to ensure no other cells got in there somehow
	}
	
	@Test
	public void testTarg22_3() { //Test targeting for 2,2 on roll of 3
		BoardCell cell = disBoard.getCell(2,2); //Set cell of interest
		
		disBoard.calcTargets(cell, 3);
		Set<BoardCell> testList = disBoard.getTargets(); //Set roll, calculate and return target set of cells
		
		assertTrue(testList.contains(disBoard.getCell(2,1))); //Check for each target cell
		assertTrue(testList.contains(disBoard.getCell(3,0)));
		assertTrue(testList.contains(disBoard.getCell(1,2)));
		assertTrue(testList.contains(disBoard.getCell(3,2)));
		assertTrue(testList.contains(disBoard.getCell(0,1)));
		assertTrue(testList.contains(disBoard.getCell(0,3)));
		assertTrue(testList.contains(disBoard.getCell(1,0)));
		assertTrue(testList.contains(disBoard.getCell(2,3)));
		assertEquals(8, testList.size()); //Check to ensure no other cells got in there somehow
	}
	
	@Test
	public void testTarg00_4() { //Test targeting for 0,0 on roll of 4
		BoardCell cell = disBoard.getCell(0,0); //Set cell of interest
		
		disBoard.calcTargets(cell, 4);
		Set<BoardCell> testList = disBoard.getTargets(); //Set roll, calculate and return target set of cells
		
		assertTrue(testList.contains(disBoard.getCell(0,2))); //Check for each target cell
		assertTrue(testList.contains(disBoard.getCell(1,1)));
		assertTrue(testList.contains(disBoard.getCell(1,3)));
		assertTrue(testList.contains(disBoard.getCell(2,0)));
		assertTrue(testList.contains(disBoard.getCell(2,2)));
		assertTrue(testList.contains(disBoard.getCell(3,1)));
		assertEquals(6, testList.size()); //Check to ensure no other cells got in there somehow
	}
	
	@Test
	public void testTarg13_2() { //Test targeting for 1,3 on roll of 2
		BoardCell cell = disBoard.getCell(1,3); //Set cell of interest
		
		disBoard.calcTargets(cell, 2);
		Set<BoardCell> testList = disBoard.getTargets(); //Set roll, calculate and return target set of cells
		
		assertTrue(testList.contains(disBoard.getCell(0,2))); //Check for each target cell
		assertTrue(testList.contains(disBoard.getCell(1,1)));
		assertTrue(testList.contains(disBoard.getCell(2,2)));
		assertTrue(testList.contains(disBoard.getCell(3,3)));
		assertEquals(4, testList.size()); //Check to ensure no other cells got in there somehow
	}
	
	@Test
	public void testTarg31_3() { //Test targeting for 3,1 on roll of 3
		BoardCell cell = disBoard.getCell(3,1); //Set cell of interest
		
		disBoard.calcTargets(cell, 3);
		Set<BoardCell> testList = disBoard.getTargets(); //Set roll, calculate and return target set of cells
		
		assertTrue(testList.contains(disBoard.getCell(0,1))); //Check for each target cell
		assertTrue(testList.contains(disBoard.getCell(1,0)));
		assertTrue(testList.contains(disBoard.getCell(1,2)));
		assertTrue(testList.contains(disBoard.getCell(2,1)));
		assertTrue(testList.contains(disBoard.getCell(2,3)));
		assertTrue(testList.contains(disBoard.getCell(3,0)));
		assertTrue(testList.contains(disBoard.getCell(3,2)));
		assertEquals(7, testList.size()); //Check to ensure no other cells got in there somehow
	}
	
	@Test
	public void testTarg21_6() { //Test targeting for 3,1 on roll of 3
		BoardCell cell = disBoard.getCell(2,1); //Set cell of interest
		
		disBoard.calcTargets(cell, 6);
		Set<BoardCell> testList = disBoard.getTargets(); //Set roll, calculate and return target set of cells
		
		assertTrue(testList.contains(disBoard.getCell(0,1))); //Check for each target cell
		assertTrue(testList.contains(disBoard.getCell(0,3)));
		assertTrue(testList.contains(disBoard.getCell(1,0)));
		assertTrue(testList.contains(disBoard.getCell(1,2)));
		assertTrue(testList.contains(disBoard.getCell(2,3)));
		assertTrue(testList.contains(disBoard.getCell(3,0)));
		assertTrue(testList.contains(disBoard.getCell(3,2)));
		assertEquals(7, testList.size()); //Check to ensure no other cells got in there somehow
	}

}
