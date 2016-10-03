package experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	public static final int GRID_ROWS = 4;
	public static final int GRID_COLS = 4;
	
	private int boardRows;
	private int boardCols;
	private Set<BoardCell> targets;	
	private BoardCell[][] grid;

	public IntBoard(int rows, int cols) {
		super();
		this.boardRows = rows;
		this.boardCols = cols;
		grid = new BoardCell[rows][cols];
		
		for (int i = 0; i < boardRows; i++)	for (int j = 0; j < boardCols; j++) grid[i][j] = new BoardCell(i,j);	
		targets = new HashSet<BoardCell>();
	}
	
	public Map<BoardCell, HashSet<BoardCell>> calcAdjacencies() {
		Map<BoardCell, HashSet<BoardCell>> adjMap = new HashMap<BoardCell, HashSet<BoardCell>>();
		
		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardCols; j++) {
				HashSet<BoardCell> adjCells = new HashSet<BoardCell>();
				
				if (i != 0) adjCells.add(getCell(i-1,j));
				if (i != (boardRows-1)) adjCells.add(getCell(i+1,j));
				if (j != 0) adjCells.add(getCell(i,j-1));
				if (j != (boardCols-1)) adjCells.add(getCell(i,j+1));
				
				adjMap.put(getCell(i,j), adjCells);			
				
			}
		}
		
		return adjMap;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		targets.clear();
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {		
		Map<BoardCell, HashSet<BoardCell>> adjMap = new HashMap<BoardCell, HashSet<BoardCell>>();
		adjMap = calcAdjacencies();
		return adjMap.get(cell);
	}
	
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
} 
