package clueGame;

import java.awt.Point;
import java.util.Set;

import com.sun.javafx.geom.Rectangle;

public class HumanPlayer extends Player {


	public HumanPlayer(String pName, String color, int row, int col) {
		super(pName, color, row, col);
		// TODO Auto-generated constructor stub
	}

	public void makeMove(Board board, Set<BoardCell> targets) {	
		board.highlightTargets(targets);
	}
}
