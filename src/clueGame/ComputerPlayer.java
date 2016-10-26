package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class ComputerPlayer extends Player{
	private BoardCell lastDoorVisited;
	private Solution suggestion;
	
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
	
	public void createSuggestion(BoardCell roomLocation, Board board){
		String person = "";
		String room = "";
		String weapon = "";
		ArrayList<String> personList = new ArrayList<String>();
		ArrayList<String> weaponList = new ArrayList<String>();
		Iterator it = unseenCards.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			if(pair.getValue() == CardType.PERSON){
				personList.add(pair.getKey().toString());
			}
		}
		person = personList.get(0);
		
		Iterator it2 = unseenCards.entrySet().iterator();
		while(it2.hasNext()){
			Map.Entry pair = (Map.Entry)it2.next();
			if(pair.getValue() == CardType.WEAPON){
				weaponList.add(pair.getKey().toString());
			}
		}
		weapon = weaponList.get(0);
		
		 Map<Character, String> rooms = new TreeMap<Character, String>();
		 rooms = board.getLegend();
		 
		 Iterator ro = unseenCards.entrySet().iterator();
		 while(ro.hasNext()){
			Map.Entry pair = (Map.Entry)ro.next();
			if(pair.getKey().toString() == String.valueOf(roomLocation.getInitial())){
				room = pair.getValue().toString();
			}
		}
		
		Solution suggestionTest = new Solution(person, room, weapon);
		suggestion = suggestionTest;
	}

	public Solution getSuggestion() {
		return suggestion;
	}
	
}
