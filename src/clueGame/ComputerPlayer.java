package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private BoardCell lastDoorVisited;
	
	public void setLastCellVisited(BoardCell lastCellVisited) {
		this.lastDoorVisited = lastDoorVisited;
	}

	public BoardCell getLastCellVisited() {
		return lastDoorVisited;
	}

	public ComputerPlayer(String n, Color color, int r, int c) {
		super(n,color, r, c);
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
		System.out.println(i);
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

}
