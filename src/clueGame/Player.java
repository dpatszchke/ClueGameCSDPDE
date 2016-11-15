package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	private String playerName;
	protected int row;
	protected int column;
	private ArrayList<Card> myCards = new ArrayList<Card>();
	protected Map<String, CardType> unseenCards = new HashMap<String, CardType>();
	protected Color color;
	
	public abstract void move(Board board);
	
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
		if(currentCard != null) {
			unseenCards.remove(currentCard.getCardName());
		}
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
		ArrayList<Card> dissprovingCards = new ArrayList<Card>();
		for(Card currentCard : myCards){
			if((currentCard.getCardName().equals(suggestion.person)) || (currentCard.getCardName().equals(suggestion.room) || (currentCard.getCardName().equals(suggestion.weapon)))){
				dissprovingCards.add(currentCard);
			}
		}
		int length = dissprovingCards.size();
		switch(length){
		case 0:
			return null;
		case 1:
			return dissprovingCards.get(0);
		default:
			Random rn = new Random();
			int i = Math.abs(rn.nextInt() % length);
			return dissprovingCards.get(i);
		}
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
		Card  jim = new Card("Jim Buck", CardType.PERSON);
		unseenCards.put(jim.getCardName(), jim.getCardType());
		Card john = new Card("John Doe", CardType.PERSON);
		unseenCards.put(john.getCardName(), john.getCardType());
		Card black = new Card("Black Powder Rifle", CardType.WEAPON);
		unseenCards.put(black.getCardName(), black.getCardType());
		Card shot = new Card("Shot Gun", CardType.WEAPON);
		unseenCards.put(shot.getCardName(), shot.getCardType());
	}
	
	public void setUpDissprovingSuggestionTestWithNoMatch(){
		myCards.clear();
	}
	
	public void setUpDissprovingSuggestionTestWithOneMatch(){
		myCards.clear();
		Card tempCard = new Card("Jim Buck", CardType.PERSON);
		myCards.add(tempCard);
	}
	
	public void setUpDissprovingSuggestionTestWithMultipleMatches(){
		myCards.clear();
		Card tempCard = new Card("Jim Buck", CardType.PERSON);
		myCards.add(tempCard);
		Card tempCard2 = new Card("Shot Gun", CardType.WEAPON);
		myCards.add(tempCard2);
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(column * 24, row * 24, 24, 24);
		g.setColor(Color.BLACK);
		g.drawOval(column * 24, row * 24, 24, 24);
	}

	

	

}
