package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

public class BoardCell implements Comparable<BoardCell> {
	
	private static final int WIDTH = 24;
	private static final int HEIGHT = 24;
	private int row, column;
	private char initial;
	private DoorDirection doorDir;
	
	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		
		this.doorDir = DoorDirection.NONE;
	}
	
	public boolean isWalkway() {
		return (Character.toLowerCase(initial) == 'w'); //Some people used W for walkways, we used w. This is the workaround
	}
	
	public boolean isRoom() {
		return (Character.toLowerCase(initial) != 'w' && initial != 'C');
	}
	
	public boolean isCenterRoom() {
		return (initial == 'C');
	}
	
	public boolean isDoorway() {
		return (doorDir != DoorDirection.NONE);
	}

	public DoorDirection getDoorDirection() {
		return doorDir;
	}

	public char getInitial() {
		return initial;
	}		

	public void setDoorDir(char theDoorway) { 
		switch(theDoorway) {
		case 'D': 
			doorDir = DoorDirection.DOWN;
			break;
		case 'U': 
			doorDir = DoorDirection.UP;
			break;
		case 'R': 
			doorDir = DoorDirection.RIGHT;
			break;
		case 'L': 
			doorDir = DoorDirection.LEFT;
			break;
		case 'N': 
			doorDir = DoorDirection.CENTER;
			break;
		default:
		}
		
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", doorDir=" + doorDir + "]";
	}


	@Override
	public int compareTo(BoardCell cellPassed) {
		if((row == cellPassed.row) && (column == cellPassed.column)){
			return 0;
		}
		return -1;
	}
	
	public void draw(Graphics g, Map<Character, String> rooms) {
		if (this.isRoom()) {
			g.setColor(Color.GRAY);
			g.fillRect(column * WIDTH, row * HEIGHT, WIDTH, HEIGHT);
		} else if (this.isCenterRoom()) {
			g.setColor(Color.GRAY);
			g.fillRect(column * WIDTH, row * HEIGHT, WIDTH, HEIGHT);
		} else {
			g.setColor(Color.YELLOW);
			g.fillRect(column * WIDTH, row * HEIGHT, WIDTH, HEIGHT);
			g.setColor(Color.BLACK);
			g.drawRect(column * WIDTH,  row * HEIGHT, WIDTH, HEIGHT);
		}
		if (this.isDoorway()) {
			g.setColor(Color.BLUE);
			switch (this.getDoorDirection()) {
			case UP:
				g.fillRect(column * WIDTH, row * HEIGHT, WIDTH, HEIGHT / 6); break;
			case DOWN:
				g.fillRect(column * WIDTH, (row + 1) * HEIGHT - HEIGHT / 6, WIDTH, HEIGHT / 6); break;
			case LEFT:
				g.fillRect(column * WIDTH, row * HEIGHT, WIDTH / 6, HEIGHT); break;
			case RIGHT:
				g.fillRect((column + 1) * WIDTH - WIDTH / 6, row * HEIGHT, WIDTH / 6, HEIGHT); break;
			case CENTER:
				g.setColor(Color.BLACK);
				String[] splitName = rooms.get(this.getInitial()).split(" ");
				for (int i = 0; i < splitName.length; i++) {
					g.drawString(splitName[i], column * WIDTH - WIDTH / 2, row * HEIGHT - (splitName.length - i - 1) * HEIGHT / 2);
				}
				break;
			case NONE:
				break;
			}
		}
		
	}
	
}
