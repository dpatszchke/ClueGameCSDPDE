package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private BoardCell lastDoorVisited;
	private Solution suggesstion;
	
	public void setLastCellVisited(BoardCell lastCellVisited) {
		this.lastDoorVisited = lastCellVisited;
	}

	public BoardCell getLastCellVisited() {
		return lastDoorVisited;
	}

	public ComputerPlayer(String n, Color color, int r, int c, Set<Card> deck) {
		super(n,color, r, c, deck);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets){
		for(BoardCell currentTarget : targets){
			if(currentTarget.isDoorway() && !currentTarget.equals(lastDoorVisited)){
				lastDoorVisited = currentTarget;
				return currentTarget;
			}
		}
		int length = targets.size();
		Random rn = new Random();
		int i = Math.abs(rn.nextInt() % length);
		int j = 0;
		for(BoardCell tempTarget : targets){
			if (j==i) {
				if (tempTarget.isDoorway()) {
					lastDoorVisited = tempTarget;
				}
				return tempTarget;
			}
			j++;
		}
		return null;
	}
	
	public void makeAccusation(){
		
	}
	
	public void createSuggestion(){
		
	}

	public Solution getSuggesstion() {
		return suggesstion;
	}
	
	

}
