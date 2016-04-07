package clueGame;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.Graphics;

public class BoardCell {
	private int row;
	private int column;
	private DoorDirection doorDirection;
	private char initial;
	private boolean doorway = false;
	private int cellWidth = 25;
	private int cellHeight = 25;	
	private int doorLength = 3;
	private boolean walkway = false;
	private boolean nameCell = false;
	

	public BoardCell(int row,int column){
		this.row = row;
		this.column = column;
	}

	@Override
	public String toString() {
		return "BoardCell [" + row + "," + column + "]";
	}

	public boolean isWalkway() {
		return walkway;
	}

	public void setDoorway(boolean doorway) {
		this.doorway = doorway;
	}

	public boolean isRoom() {
		return !walkway;
	}

	public boolean isDoorway() {
		return doorway;
	}

	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return initial;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
	public void setWalkway(boolean ww) {
		walkway = ww;
	}
	
	public void drawPlayer(Graphics g, Color c) {
		Graphics2D g2 = (Graphics2D) g;
		int x=cellWidth*column;
		int y=cellHeight*row;
		g2.setColor(c);
		g2.fillOval(x+1, y+1, cellWidth-2, cellHeight-2);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int x=cellWidth*column;
		int y=cellHeight*row;
	
		if (walkway) {
			g2.setColor(Color.black);
			g2.fillRect(x, y, cellWidth, cellHeight);
			g2.setColor(Color.yellow);
			g2.fillRect(x+1, y+1, cellWidth-2, cellHeight-2);
		}
		else {
			g2.setColor(Color.gray);
			g2.fillRect(x, y, cellWidth, cellHeight);
		}
		if (nameCell) {
			String name = Board.rooms.get(initial);
			g2.setColor(Color.blue);
			g2.drawString(name, x, y);			
		}
		if (doorway) {
			int width = cellWidth;
			int height = doorLength;
			g2.setColor(Color.blue);
			switch (doorDirection) {
			case UP:
				width = cellWidth;
				height = doorLength;
				break;
			case DOWN:
				width = cellWidth;
				height = doorLength;
				y += cellHeight - doorLength;
				break;
			case RIGHT:
				width = doorLength;
				height = cellHeight;
				x += cellWidth - doorLength;
				break;
			case LEFT:
				width = doorLength;
				height = cellHeight;
				break;		
			case NONE:
				break;
			}	
			g2.fillRect(x, y, width, height);
		}
		
		
	}

	public void setNameCell(boolean b) {
		nameCell = b;
		
	}



}
