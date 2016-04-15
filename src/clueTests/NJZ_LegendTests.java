package clueTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;

public class NJZ_LegendTests {
	
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	private static Board board;

	@BeforeClass
	public static void setUp() {
		board = new Board();
		// Initialize will load default config files 
		board.initialize();
	}
	@Test
	public void testRooms() {
		// rooms is static, see discussion in lab writeup
		Map<Character, String> rooms = board.getRooms();
		// Ensure we read the correct number of rooms
		assertEquals(NUM_ROOMS, rooms.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Kitchen", rooms.get('K'));
		assertEquals("Billiards", rooms.get('R'));
		assertEquals("Dining", rooms.get('D'));
		assertEquals("Walkway", rooms.get('W'));
		assertEquals("Hall", rooms.get('H'));
		assertEquals("Lounge", rooms.get('O'));
		assertEquals("Study", rooms.get('S'));
	}
	
	// Testing bad Legend
		@Test (expected = BadConfigFormatException.class)
		public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
			Board board = new Board("ClueLayout.csv", "ClueLegendBadFormat.txt");
			board.loadRoomConfig();
		}

}
