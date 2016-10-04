package clueGame;

import java.util.Map;
import java.util.Set;

public class Board {
	
	private int numRows;
	private int numCols;
	public static final int MAX_BOARD_SIZE = 50;
	
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private String boardConfigFile;
	private String roomConfigFile;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
		
	public void initialize() {
		
	}
	
	public void loadRoomConfig() {
		
	}
	
	public void loadBoardConfig() {
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
	
	public void setConfigFiles(String boardCSV, String legend) {
		
	}
	
	public BoardCell getCellAt(int row, int col) {
		return null;
	}
	
	public Map<Character,String> getLegend() {
		return null;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numCols;
	}
	
	
}
