package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private ArrayList<Card> playerCards;
	private Color color;
	
	
	
	public Player(String playerName, Color color, int row, int column) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
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
		return playerCards;
	}

	public Color getColor() {
		return color;
	}
	
	public Card dissproveSuggestion(Solution suggestion){
		return null;
	}

}
