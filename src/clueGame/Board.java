package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

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
		try {
			
			loadRoomConfig();
			loadBoardConfig();
			
		} catch (BadConfigFormatException e) {
			System.out.println(e);
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
	}
	
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader roomFile = new FileReader(roomConfigFile);
		Scanner fin = new Scanner(roomFile);
		
		rooms = new TreeMap<Character,String>();
		String card = " Card";
		String other = " Other";
		while (fin.hasNextLine()) {
			String roomData = fin.nextLine();
			String[] roomComps = roomData.split(",");
			System.out.println(roomComps[0] + roomComps[1] + roomComps[2]);
			
			if ((roomComps[2] == card) && (roomComps[2] == other)) {	
				
				throw new BadConfigFormatException("Bad room classification");
			}
			else {
				rooms.put(roomComps[0].charAt(0), roomComps[1].substring(1));
			}
		}
		System.out.println(rooms);
		fin.close();
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader boardFile = new FileReader(boardConfigFile);
		Scanner dim = new Scanner(boardFile);
		int rowCounter = 0;
		
		while (dim.hasNextLine()) {
			String currRow = dim.nextLine();
			String[] rowSpaces = currRow.split(",");
			
			if (rowCounter == 0) setNumColumns(rowSpaces.length);
			else if (getNumColumns() != rowSpaces.length) throw new BadConfigFormatException("Column mismatch");
			
			rowCounter++;
		}
		
		setNumRows(rowCounter);		
		board = new BoardCell[numRows][numCols];
		
		dim.close();
		try {
		boardFile.close();
		}catch (IOException e){
		}
		
		FileReader boardFile2 = new FileReader(boardConfigFile);
		Scanner fin = new Scanner(boardFile2);
		int i =0;

		while (fin.hasNextLine()) {
			String currRow = fin.nextLine();
			String[] rowSpaces = currRow.split(",");
			
			for (int j = 0; j < numCols; j++) {
				String currSpace = rowSpaces[j];
				if (!rooms.containsKey(currSpace.charAt(0))) throw new BadConfigFormatException("Undefined room/area");
				
				else {
					board[i][j] = new BoardCell(i,j,currSpace.charAt(0));
					if (currSpace.length() > 1) {
						char theDoorway = currSpace.charAt(1);
						board[i][j].setDoorDir(theDoorway); 
					}
				}
			}
			i++;
		}
		fin.close();
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
	
	public void setConfigFiles(String boardCSV, String legend) {
		boardConfigFile = boardCSV;
		roomConfigFile = legend;
	}
	
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
	
	public Map<Character,String> getLegend() {
		return rooms;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numCols;
	}
	
	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}
	
	public void setNumColumns(int numCols) {
		this.numCols = numCols;
	}
	
	
}
