package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private ArrayList<Card> myCards = new ArrayList<Card>();
	private Set<Card> unseenCards = new HashSet<Card>();
	private Color color;
	
	
	
	public Player(String playerName, Color color, int row, int column, Set<Card> deck) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		this.unseenCards = deck;
	}
	
	public void addCard(Card currentCard){
		myCards.add(currentCard);
		deleteCardFromUnseen(currentCard);
	}
	

	public void deleteCardFromUnseen(Card currentCard){
		unseenCards.remove(currentCard);
;
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

}
