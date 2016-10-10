package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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
	private Set<BoardCell> targets;
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
		try { //Handle board and room configuration in one function; watch for exceptions
			
			loadRoomConfig();
			loadBoardConfig();
			
		} catch (BadConfigFormatException e) {
			System.out.println(e);
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
		calcAdjacencies();
	}
	
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader roomFile = new FileReader(roomConfigFile);
		Scanner fin = new Scanner(roomFile); //Open the room legend file
		
		rooms = new TreeMap<Character,String>(); //Initialize the map
		
		String card = " Card";
		String other = " Other"; //Assign primitives for the card types
		
		while (fin.hasNextLine()) { //Scan through the whole document
			String roomData = fin.nextLine(); //For each line
			String[] roomComps = roomData.split(","); //Break it at the commas and store the parts
			if (!(roomComps[2].equals(card)) && !(roomComps[2].equals(other))) { //If the room isn't a card or other type
				
				throw new BadConfigFormatException("Bad room classification"); //That's bad
			}
			else {
				rooms.put(roomComps[0].charAt(0), roomComps[1].substring(1)); //Otherwise, store that mapping
			}
		}
		fin.close();
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader boardFile = new FileReader(boardConfigFile);
		Scanner dim = new Scanner(boardFile); //Load up the board csv
		int rowCounter = 0; //Int to count the rows in the csv
		
		while (dim.hasNextLine()) { //Column length test; for each row
			String currRow = dim.nextLine();
			String[] rowSpaces = currRow.split(","); //Break the row at the commas
			
			if (rowCounter == 0) setNumColumns(rowSpaces.length); //For the first loop, assume that's the correct length
			else if (getNumColumns() != rowSpaces.length) throw new BadConfigFormatException("Column mismatch"); //If there's any variance from that, that's bad
			
			rowCounter++; //Add one to the row count
		}
		
		setNumRows(rowCounter);	//Now we know the number of rows, so set that
		board = new BoardCell[numRows][numCols]; //Initialize the board dimensions
		
		//This block just closes and reopens the file so the scanner resets
		dim.close();
		try {
		boardFile.close();
		}catch (IOException e){		}		
		FileReader boardFile2 = new FileReader(boardConfigFile);
		Scanner fin = new Scanner(boardFile2);
		
		int i =0; //Stores the current row number we're looking at
		while (fin.hasNextLine()) { //For each line in the file
			String currRow = fin.nextLine();
			String[] rowSpaces = currRow.split(","); //Break it
			
			for (int j = 0; j < numCols; j++) { //And for each column in the row
				String currSpace = rowSpaces[j]; //Get its room data
				if (!rooms.containsKey(currSpace.charAt(0))) throw new BadConfigFormatException("Undefined room/area"); //If that room's undefined, that's bad
				
				else {
					board[i][j] = new BoardCell(i,j,currSpace.charAt(0)); //Otherwise, initialize the individual cell with row, col and room initial
					if (currSpace.length() > 1) { //If there's more than one letter, there's a door (or name, but let's worry about that later)
						char theDoorway = currSpace.charAt(1); //Get the doorway index as the next letter of the cell data
						board[i][j].setDoorDir(theDoorway); //Use that to set the door direction
					}
				}
			}
			i++; //Get ready to look at the next row
		}
		fin.close();
	}
	
	public void calcAdjacencies() {	
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				//Make a new set to store the adjacent cells
				HashSet<BoardCell> adjCells = new HashSet<BoardCell>();
				
				//If we're in a room, then there aren't any adjacent cells (not now at least)
				if (!getCellAt(i,j).isDoorway() && !getCellAt(i,j).isWalkway()) {
					adjMatrix.put(getCellAt(i,j), adjCells);
					continue;
				}
				
				//If we're in a door, there's only one way out
				if (getCellAt(i,j).isDoorway()) {
					switch(getCellAt(i,j).getDoorDirection()) {
					case DOWN:
						adjCells.add(getCellAt(i+1,j));
						break;
					case UP:
						adjCells.add(getCellAt(i-1,j));
						break;
					case LEFT:
						adjCells.add(getCellAt(i,j-1));
						break;
					case RIGHT:
						adjCells.add(getCellAt(i,j+1));
						break;
					default:
					}
					adjMatrix.put(getCellAt(i,j), adjCells);
					continue;					
				}
				
				
				//Otherwise, we're on a walkway. Get adjacencies for other walkways
				if ((i != 0) && getCellAt(i-1,j).isWalkway()) adjCells.add(getCellAt(i-1,j));
				if ((i != (numRows-1)) && getCellAt(i+1,j).isWalkway()) adjCells.add(getCellAt(i+1,j));
				if ((j != 0) && getCellAt(i,j-1).isWalkway()) adjCells.add(getCellAt(i,j-1));
				if ((j != (numCols-1)) && getCellAt(i,j+1).isWalkway()) adjCells.add(getCellAt(i,j+1));
				
				//And adjacencies for the doors
				if (i != 0 && getCellAt(i-1,j).isDoorway()) 
					if (getCellAt(i-1,j).getDoorDirection().equals(DoorDirection.DOWN)) adjCells.add(getCellAt(i-1,j));
				if (i != (numRows-1) && getCellAt(i+1,j).isDoorway()) 
					if (getCellAt(i+1,j).getDoorDirection().equals(DoorDirection.UP)) adjCells.add(getCellAt(i+1,j));
				if (j != 0 && getCellAt(i,j-1).isDoorway()) 
					if (getCellAt(i,j-1).getDoorDirection().equals(DoorDirection.RIGHT)) adjCells.add(getCellAt(i,j-1));
				if (j != (numCols-1) && getCellAt(i,j+1).isDoorway()) 
					if (getCellAt(i,j+1).getDoorDirection().equals(DoorDirection.LEFT)) adjCells.add(getCellAt(i,j+1));

				adjMatrix.put(getCellAt(i,j), adjCells);			

			}
		}
		System.out.println(adjMatrix);
	}
	
	public void calcTargets(int i, int j, int pathLength) {
		targets = new HashSet<BoardCell>();
		Set<BoardCell> visited = new HashSet<BoardCell>();
		visited.add(getCellAt(i,j));
		Set<BoardCell> adjCell = new HashSet<BoardCell>();
		adjCell = getAdjList(i,j);

		findAllTargets(getCellAt(i,j), pathLength, visited, adjCell);

	}
	
	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMatrix.get(getCellAt(i,j));
	}
	
	public void findAllTargets(BoardCell cellAt, int pathLength, Set<BoardCell> visited, Set<BoardCell> adjCell) {
		for (BoardCell c: adjCell) {
			if (visited.contains(c)) continue;
			
			visited.add(c);
			if (pathLength == 1 || c.isDoorway()) targets.add(c);
			else {
				Set<BoardCell> newAdjCell = new HashSet<BoardCell>();
				newAdjCell = getAdjList(c.getRow(), c.getColumn());
				findAllTargets(c, pathLength-1, visited, newAdjCell);
			}
			
			visited.remove(c);
		}
		
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
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
}
