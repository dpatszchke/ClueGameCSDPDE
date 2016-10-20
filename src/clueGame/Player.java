package clueGame;

import java.util.ArrayList;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private ArrayList<Card> playerCards;
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

	private Color color;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	public Card dissproveSuggestion(Solution suggestion){
		
	}

}
