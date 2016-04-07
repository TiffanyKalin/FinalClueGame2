package clueTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class NJZ_BoardTests {
	
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board("ClueLayout.csv", "Legend.txt");
		board.initialize();
		//System.out.println(board.getRows());
	}

	@Test
	public void testRoomInitials() {
		assertEquals('A', board.getCellAt(2, 1).getInitial());
		assertEquals('B', board.getCellAt(9, 2).getInitial());
		assertEquals('C', board.getCellAt(17, 3).getInitial());
		assertEquals('W', board.getCellAt(15, 6).getInitial());
		assertEquals('D', board.getCellAt(0, 9).getInitial());
	}
	
	@Test
	public void testRoomDimensions() {
		assertEquals(20, board.getRows());
		assertEquals(20, board.getColumns());
	}
	
	@Test
	public void testDoorDirections() {
		BoardCell first = board.getCellAt(2,4);
		assertEquals(DoorDirection.UP, first.getDoorDirection());
		BoardCell second = board.getCellAt(18,12);
		assertEquals(DoorDirection.DOWN, second.getDoorDirection());
	}

	// Testing bad room
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		Board board = new Board("ClueLayoutBadColumns.csv", "Legend.txt");
		board.loadRoomConfig();
		board.loadBoardConfig();
	}
	
	

}
