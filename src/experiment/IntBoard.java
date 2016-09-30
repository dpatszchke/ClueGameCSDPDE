package experiment;

import java.util.HashSet;
import java.util.Set;

public class IntBoard {
	public static final int GRID_ROWS = 4;
	public static final int GRID_COLS = 4;
	
	private BoardCell[][] grid;

	public IntBoard(int rows, int cols) {
		super();
		grid = new BoardCell[rows][cols];
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets() {
		return null;
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return null;
	}
	
	public BoardCell getCell(int row, int col) {
		return null;
	}
} 
