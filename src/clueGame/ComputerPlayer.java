package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	public ComputerPlayer(String n, Color color, int r, int c) {
		super(n,color, r, c);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets){
		return null;
	}
	
	public void makeAccusation(){
		
	}
	
	public void createSuggestion(){
		
	}

}
