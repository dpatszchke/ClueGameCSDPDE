package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
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
		
		if (cell.isRoom()) {
			Map<Character,String> roomMap = board.getLegend();
			String currentRoom = roomMap.get(cell.getInitial());
			GuessDialog dialog = new GuessDialog(board,currentRoom);
			dialog.setVisible(true);
		}
		takingTurn = false;
		//if this location is a room we need to prompt the human for a suggestion
		//need to make a control panel for this
		//runs through the other players in a loop and they attempt to disprove
		//if disprove returns null "no new clue" else "cardName()"
		
		//for us, we must hold the room constant to the room that we are in
		
	}
	public void setTakingTurn(boolean b) {
		takingTurn = b;
	}

}
