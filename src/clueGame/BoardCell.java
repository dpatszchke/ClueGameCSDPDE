package clueGame;

public class BoardCell implements Comparable<BoardCell> {
	
	private int row, column;
	private char initial;
	private DoorDirection doorDir;
	
	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		
		this.doorDir = DoorDirection.NONE;
	}
	
	public boolean isWalkway() {
		return (Character.toLowerCase(initial) == 'w'); //Some people used W for walkways, we used w. This is the workaround
	}
	
	public boolean isRoom() {
		return (Character.toLowerCase(initial) != 'w' && initial != 'C');
	}
	
	public boolean isDoorway() {
		return (doorDir != DoorDirection.NONE);
	}

	public DoorDirection getDoorDirection() {
		return doorDir;
	}

	public char getInitial() {
		return initial;
	}		

	public void setDoorDir(char theDoorway) { 
		switch(theDoorway) {
		case 'D': 
			doorDir = DoorDirection.DOWN;
			break;
		case 'U': 
			doorDir = DoorDirection.UP;
			break;
		case 'R': 
			doorDir = DoorDirection.RIGHT;
			break;
		case 'L': 
			doorDir = DoorDirection.LEFT;
			break;
		default:
		}
		
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", doorDir=" + doorDir + "]";
	}


	@Override
	public int compareTo(BoardCell cellPassed) {
		if((row == cellPassed.row) && (column == cellPassed.column)){
			return 0;
		}
		return -1;
	}
	
}
