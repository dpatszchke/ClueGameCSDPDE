package clueGame;

public class BoardCell {
	
	private int row, column;
	private char initial;
	
	public BoardCell() {
		super();
	}
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}

}
