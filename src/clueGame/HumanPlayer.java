package clueGame;

import java.awt.Point;
import java.util.Set;

import com.sun.javafx.geom.Rectangle;

public class HumanPlayer extends Player {
	private String personGuess;
	private String weaponGuess;
	private String roomGuess;

	public HumanPlayer(String pName, String color, int row, int col) {
		super(pName, color, row, col);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void makeMove(Board board, Set<BoardCell> targets, clueGUI.ClueGame game) {	
		board.highlightTargets(targets, game);
	}

	public void setPersonGuess(String personGuess) {
		this.personGuess = personGuess;
		
	}

	public void setWeaponGuess(String weaponGuess) {
		this.weaponGuess = weaponGuess;
	}

	public String getPersonGuess() {
		return personGuess;
	}

	public String getWeaponGuess() {
		return weaponGuess;
	}

	public void setRoomGuess(String roomGuess) {
		this.roomGuess = roomGuess;
	}
	
	public String getRoomGuess() {
		return roomGuess;
	}
}
