package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

public class HumanPlayer extends Player{
	private boolean takingTurn = false;
	public HumanPlayer(String n, Color color, int r, int c, Set<Card> deck) {
		super(n,color, r, c, deck);
		
	}

	public void move(Board board) {
		board.highlight();
		takingTurn = true;
	}
	
	public boolean stillTakingTurn() {
		return takingTurn;
	}

	public void finishTurn(BoardCell cell, Board board) {
		setRow(cell.getRow());
		setColumn(cell.getColumn());
		board.repaint();
		takingTurn = false;
		
	}
	public void setTakingTurn(boolean b) {
		takingTurn = b;
	}

}
