package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JPanel;

public class Board extends JPanel {
	
	private int numRows;
	private int numCols;
	public static final int MAX_BOARD_SIZE = 50;
	
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<Card> deckOfCards;
	private String boardConfigFile;
	private String roomConfigFile;
	private String cardsConfigFile;
	private String playerConfigFile;
	private HumanPlayer humanPlayer;
	//private ComputerPlayer[] computerPlayers;
	private ComputerPlayer[] computerPlayers;
	private Card[] dealtCards = new Card[21];
	private static Solution theAnswer;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				board[i][j].draw(g, rooms);
			}
		}
		humanPlayer.draw(g);
		for (ComputerPlayer player : computerPlayers) {
			player.draw(g);
		}
	}
	
	public String[] getCards(CardType c) {
		Set<String> retVal = new HashSet<String>();
		for(Card card : deckOfCards) {
			if(card.getCardType().equals(c)) {
				retVal.add(card.getCardName());
			}
		}
		return retVal.toArray(new String [retVal.size()]);
	}
	
	public void initialize() {
		try { //Handle board and room configuration in one function; watch for exceptions
			
			loadRoomConfig();
			loadBoardConfig();
			//loadCardConfig();
			loadPlayerConfig();
			
			
			
		} catch (BadConfigFormatException e) {
			System.out.println(e);
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		calcAdjacencies();
	}
	
	public void dealCards(){
		int counter = 0;
		//TODO add logic to set a solution
		List<Card> dealList = new ArrayList();
		boolean weaponSwitch = true;
		boolean roomSwitch = true;
		boolean personSwitch = true;
		Card weaponCard = new Card("", CardType.WEAPON);
		Card roomCard = new Card("", CardType.ROOM);
		Card personCard = new Card("", CardType.PERSON);
		
		for(Card currentCard: deckOfCards){
			dealList.add(currentCard);
		}
		
		Collections.shuffle(dealList);
		for(Card card: dealList) {
			if(weaponSwitch) {
				if(card.getCardType() == CardType.WEAPON) {
					dealList.remove(card);
					weaponCard = card;
					weaponSwitch = false;
				}
			}
			if(roomSwitch) {
				if(card.getCardType() == CardType.ROOM) {
					dealList.remove(card);
					roomCard = card;
					roomSwitch = false;
				}
			}
			if(personSwitch) {
				if(card.getCardType() == CardType.PERSON) {
					dealList.remove(card);
					personCard = card;
					personSwitch = false;
				}
			}
		}
		
		
		//THIS IS WHERE THE ERROR WILL BE
		for(Card currentCard: dealtCards){
			if (counter % 6 == 0) {
				humanPlayer.addCard(currentCard);
				dealtCards[counter] = currentCard;
			} else {
				computerPlayers[(counter % 6)-1].addCard(currentCard);
				dealtCards[counter] = currentCard;
			}
			counter = counter + 1;
		}
		
		setTheAnswer(personCard.getCardName(), roomCard.getCardName(), weaponCard.getCardName());
	}
	
	public void loadPlayerConfig() throws BadConfigFormatException, FileNotFoundException{
		
		loadCardConfig();
		
		computerPlayers = new ComputerPlayer[5];
		FileReader playerFile = new FileReader(playerConfigFile);
		Scanner in = new Scanner(playerFile); 
		int c = 0;
		while (in.hasNextLine()) {
			String Line = in.nextLine();
			String[] playerInfo = Line.split(",");
			if (c == 0) {
				humanPlayer = new HumanPlayer(playerInfo[0], convertColor(playerInfo[1]) ,Integer.parseInt(playerInfo[2]), Integer.parseInt(playerInfo[3]), deckOfCards);
			} else {
				computerPlayers[c - 1] = new ComputerPlayer(playerInfo[0], convertColor(playerInfo[1]) ,Integer.parseInt(playerInfo[2]), Integer.parseInt(playerInfo[3]), deckOfCards);
			}
			c = c + 1;
		}
		in.close();
		dealCards();
	}
	
	public void loadCardConfig() throws BadConfigFormatException, FileNotFoundException{
		deckOfCards = new HashSet<Card>();
		FileReader cardsFile = new FileReader(cardsConfigFile);
		Scanner in = new Scanner(cardsFile); 
		
		while (in.hasNextLine()) {
			String Line = in.nextLine();
			String[] cardInfo = Line.split(", ");
			switch (cardInfo[1]){
				case "Room":
					Card currentCard = new Card(cardInfo[0], CardType.ROOM);
					deckOfCards.add(currentCard);
					break;
				case "Weapon":
					Card currentCard2 = new Card(cardInfo[0], CardType.WEAPON);
					deckOfCards.add(currentCard2);
					break;
				case "Person":
					Card currentCard3 = new Card(cardInfo[0], CardType.PERSON);
					deckOfCards.add(currentCard3);
					break;
			}
		}
		
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
				fin.close();
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
			else if (getNumColumns() != rowSpaces.length) {
				dim.close();
				throw new BadConfigFormatException("Column mismatch"); //If there's any variance from that, that's bad
			}
			
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
				if (!rooms.containsKey(currSpace.charAt(0))) {
					fin.close();
					throw new BadConfigFormatException("Undefined room/area"); //If that room's undefined, that's bad
				}
				
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
	
	public Color convertColor(String strColor) {
	    Color color; 
	    try {     
	        // We can use reflection to convert the string to a color
	        Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
	        color = (Color)field.get(null); 
	    } catch (Exception e) {  
	        color = null; // Not defined  
	    }
	    return color;
	}

	
	public void setConfigFiles(String boardCSV, String legend, String cards, String players) {
		boardConfigFile = boardCSV;
		roomConfigFile = legend;
		cardsConfigFile = cards;
		playerConfigFile = players;
	}
	
	public boolean checkAccusation(Solution accusation){
		if((theAnswer.person == accusation.person) && (theAnswer.room == accusation.room) && (theAnswer.weapon == accusation.weapon)){
			return true;
		}else{
			return false;
		}
	}
	
	public Card handleSuggestion(Solution suggestion, ArrayList<Player> players, int accuserPostion){
		Card returnCard;
		//looping through players and seeing if one of the players returns a card
		for(Player player:players){
			returnCard = player.dissproveSuggestion(suggestion);
			if (returnCard != null){
				return returnCard;
			}
		}
		for(int i = 0; i < accuserPostion; i++){
			returnCard = players.get(i).dissproveSuggestion(suggestion);
			if (returnCard != null){
				return returnCard;
			}
		}
		return null;
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
	
	public ComputerPlayer[] getComputerPlayers() {
		return computerPlayers;
	}
	
	public HumanPlayer gethumanPlayer() {
		return humanPlayer;
	}
	
	public Set<Card> getDeckOfCards() {
		return deckOfCards;
	}
	
	public int getNumberOfRoomCards() {
		int counter = 0;
		for(Card currentCard: deckOfCards){
			if(currentCard.getCardType() == CardType.ROOM){
				counter = counter + 1;
			}
		}
		return counter;
	}
	
	public int getNumberOfWeaponCards() {
		int counter = 0;
		for(Card currentCard: deckOfCards){
			if(currentCard.getCardType() == CardType.WEAPON){
				counter = counter + 1;
			}
		}
		return counter;
	}
	
	public int getNumberOfPersonCards() {
		int counter = 0;
		for(Card currentCard: deckOfCards){
			if(currentCard.getCardType() == CardType.PERSON){
				counter = counter + 1;
			}
		}
		return counter;
	}
	
	public Card[] getDealtCards() {
		return dealtCards;
	}
	public static Solution getTheAnswer() {
		return theAnswer;
	}
	public static void setTheAnswer(String person, String room, String weapon) {
		theAnswer = new Solution(person, room, weapon);
	}
	
	public ArrayList<Player> setUpHandlingSuggestionEnviroment() {
		
		ArrayList<Player> testPlayers = new ArrayList<Player>();
		HumanPlayer humanPlayer = new HumanPlayer("Jim Buck", Color.BLACK, 0,4,deckOfCards);
		Card humansCard = new Card("Shot Gun", CardType.WEAPON);
		humanPlayer.addCard(humansCard);
		
		ComputerPlayer testComp1 = new ComputerPlayer("John Doe", Color.blue,0,11, deckOfCards);
		Card testComp1Card = new Card("Gym", CardType.ROOM);
		testComp1.addCard(testComp1Card);
		
		ComputerPlayer testComp2 = new ComputerPlayer("Jane Doe", Color.magenta,13,23, deckOfCards);
		Card testComp2Card = new Card("James Fawn", CardType.PERSON);
		testComp2.addCard(testComp2Card);
		
		testPlayers.add(humanPlayer);
		testPlayers.add(testComp1);
		testPlayers.add(testComp2);
		
		return testPlayers;
	}
	
}
