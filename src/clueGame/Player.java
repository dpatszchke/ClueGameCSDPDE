package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private ArrayList<Card> myCards = new ArrayList<Card>();
	protected Map<String, CardType> unseenCards = new HashMap<String, CardType>();
	private Color color;
	
	
	
	public Player(String playerName, Color color, int row, int column, Set<Card> deck) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		for(Card currentCard: deck){
			unseenCards.put(currentCard.getCardName(), currentCard.getCardType());
		}
	}
	
	public void addCard(Card currentCard){
		myCards.add(currentCard);
		deleteCardFromUnseen(currentCard);
	}
	

	public void deleteCardFromUnseen(Card currentCard){
		unseenCards.remove(currentCard.getCardName());
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public ArrayList<Card> getPlayerCards() {
		return myCards;
	}

	public Color getColor() {
		return color;
	}
	
	public Card dissproveSuggestion(Solution suggestion){
		return null;
	}
	
	public void removeFromUnseenForOneWeaponandPerson(){
		unseenCards.clear();
		Card john = new Card("John Doe", CardType.PERSON);
		unseenCards.put(john.getCardName(), john.getCardType());
		Card shot = new Card("Shot Gun", CardType.WEAPON);
		unseenCards.put(shot.getCardName(), shot.getCardType());
	}
	
	public void removeFromUnseenForMultipleWeaponsAndPersons(){
		unseenCards.clear();
		Card joe = new Card("Joe Buck", CardType.PERSON);
		unseenCards.put(joe.getCardName(), joe.getCardType());
		Card john = new Card("John Doe", CardType.PERSON);
		unseenCards.put(john.getCardName(), john.getCardType());
		Card black = new Card("Black Powder Rifle", CardType.WEAPON);
		unseenCards.put(black.getCardName(), black.getCardType());
		Card shot = new Card("Shot Gun", CardType.WEAPON);
		unseenCards.put(shot.getCardName(), shot.getCardType());
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	

}
