package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;

public class ComputerPlayer extends Player{
	private BoardCell lastDoorVisited;
	private Solution suggestion;
	private Card temp;
	
	public void move(Board board, int playerTracker) {
		Set<BoardCell> targets = board.getTargets();
		BoardCell location = pickLocation(targets);
		setRow(location.getRow());
		setColumn(location.getColumn());
		board.repaint();
		
		//TODO: add making a suggestion if it is in a room
		//look at logic under humanPlayer
		if (location.isRoom()) {
			createSuggestion(location,board);
			temp = board.handleSuggestion(suggestion, board.getComputerPlayerArrayList(), playerTracker);
			if(temp == null) {
				makeAccusation(board);
			}
		}
		
	}
	
	public void setLastCellVisited(BoardCell lastCellVisited) {
		this.lastDoorVisited = lastCellVisited;
	}

	public BoardCell getLastCellVisited() {
		return lastDoorVisited;
	}

	public ComputerPlayer(String n, Color color, int r, int c, Set<Card> deck) {
		super(n,color, r, c, deck);
		temp = null;
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets){ //answer never set
		for(BoardCell currentTarget : targets){
			if(currentTarget.isDoorway() && !currentTarget.equals(lastDoorVisited)){
				lastDoorVisited = currentTarget;
				return currentTarget;
			}
		}
		int length = targets.size();
		Random rn = new Random();
		int randomIndex = Math.abs(rn.nextInt(length));
		int targetIndex = 0;
		for(BoardCell target : targets){
			if (targetIndex==randomIndex) {
				if (target.isDoorway()) {
					lastDoorVisited = target;
				}
				return target;
			}
			targetIndex++;
		}
		return null;
	}
	
	public void makeAccusation(Board board){
		String person = "";
		String room = "";
		String weapon = "";
		ArrayList<String> personList = new ArrayList<String>();
		ArrayList<String> weaponList = new ArrayList<String>();
		ArrayList<String> roomList = new ArrayList<String>();
		Iterator it = unseenCards.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair1 = (Map.Entry)it.next();
			if(pair1.getValue() == CardType.PERSON){
				personList.add(pair1.getKey().toString());
			}
		}
		
		if(personList.size() > 0) {
			person = personList.get(0);
		} else {
			person = null;
		}
		
		
		Iterator it2 = unseenCards.entrySet().iterator();
		while(it2.hasNext()){
			Map.Entry pair2 = (Map.Entry)it2.next();
			if(pair2.getValue() == CardType.WEAPON){
				weaponList.add(pair2.getKey().toString());
			}
		}
		
		if(weaponList.size() > 0) {
			weapon = weaponList.get(0);
		} else {
			weapon = null;
		}
		
		Iterator it3 = unseenCards.entrySet().iterator();
		while(it3.hasNext()){
			Map.Entry pair3 = (Map.Entry)it3.next();
			if(pair3.getValue() == CardType.ROOM){
				roomList.add(pair3.getKey().toString());
			}
		}
		
		if(roomList.size() > 0) {
			room = roomList.get(0);
		} else {
			room = null;
		}

		
		
		
		Solution suggestionTest = new Solution(person, room, weapon);
		boolean t = board.checkAccusation(suggestionTest);
		String tempString = person + ", " + room + ", " + weapon;
		if(t) {
			JOptionPane.showMessageDialog(board, "Player Guessed Right!", "Player Accusation", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		} else {
			JOptionPane.showMessageDialog(null, "Player Guessed : " + tempString);
		}
	}
	
	public void createSuggestion(BoardCell roomLocation, Board board){
		String person = "";
		String room = "";
		String weapon = "";
		ArrayList<String> personList = new ArrayList<String>();
		ArrayList<String> weaponList = new ArrayList<String>();
		Iterator it = unseenCards.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair1 = (Map.Entry)it.next();
			if(pair1.getValue() == CardType.PERSON){
				personList.add(pair1.getKey().toString());
			}
		}
		int length = personList.size();
		Random rn = new Random();
		int i = Math.abs(rn.nextInt() % length);
		if(i == personList.size()) {
			person = personList.get(i - 1);
		} else {
			person = personList.get(i);
		}
		
		Iterator it2 = unseenCards.entrySet().iterator();
		while(it2.hasNext()){
			Map.Entry pair2 = (Map.Entry)it2.next();
			if(pair2.getValue() == CardType.WEAPON){
				weaponList.add(pair2.getKey().toString());
			}
		}
		int length2 = weaponList.size();
		Random rn2 = new Random();
		int j = Math.abs(rn.nextInt() % length2);
		
		if(j == weaponList.size()) {
			weapon = weaponList.get(j - 1);
		} else {
			weapon = weaponList.get(j);
		}
		
		Map<Character, String> rooms = new TreeMap<Character, String>();
		rooms = board.getLegend();

		Iterator ro = (board.getLegend()).entrySet().iterator();
		while(ro.hasNext()){
			Map.Entry pair = (Map.Entry)ro.next();
			if(pair.getKey().toString().equals(String.valueOf(roomLocation.getInitial()))){
				room = pair.getValue().toString();
			}
		}
		
		
		Solution suggestionTest = new Solution(person, room, weapon);
		suggestion = suggestionTest;
		
	}

	public Solution getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String p, String r, String w) {
		suggestion =new Solution(p,r,w);
	}

	
}
