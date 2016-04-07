package clueTests;


import java.util.LinkedList;
import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;


public class CellTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board("ClueLayout.csv", "Legend.txt");
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are OLIVE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(3, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(15, 1);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(15, 11);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(11, 14);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(6, 14);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are DARK BLUE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<BoardCell> testList = board.getAdjList(9, 4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 5)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(3, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(3,13)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(18, 12);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(18,13)));
		//TEST DOORWAY UP
		testList = board.getAdjList(14,15);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 15)));
		assertTrue(testList.contains(board.getCellAt(14, 16)));

	}

	// Test adjacency at entrance to rooms
	// These tests are ORANGE in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<BoardCell> testList = board.getAdjList(5, 13);
		assertTrue(testList.contains(board.getCellAt(4, 13)));
		assertTrue(testList.contains(board.getCellAt(6, 13)));
		assertTrue(testList.contains(board.getCellAt(5, 12)));
		assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(19, 12);
		assertTrue(testList.contains(board.getCellAt(19, 11)));
		assertTrue(testList.contains(board.getCellAt(19, 13)));
		assertTrue(testList.contains(board.getCellAt(18,12)));
		assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(16, 17);
		assertTrue(testList.contains(board.getCellAt(15,17)));
		assertTrue(testList.contains(board.getCellAt(17,17)));
		assertTrue(testList.contains(board.getCellAt(16,18)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(1,4);
		assertTrue(testList.contains(board.getCellAt(0,4)));
		assertTrue(testList.contains(board.getCellAt(2,4)));
		assertTrue(testList.contains(board.getCellAt(1,5)));
		assertEquals(3, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are DARK PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		LinkedList<BoardCell> testList = board.getAdjList(0, 16);
		assertTrue(testList.contains(board.getCellAt(0,15)));
		assertTrue(testList.contains(board.getCellAt(0,17)));
		assertEquals(2, testList.size());

		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(13, 0);
		assertTrue(testList.contains(board.getCellAt(13, 1)));
		assertTrue(testList.contains(board.getCellAt(14, 0)));
		assertTrue(testList.contains(board.getCellAt(12, 0)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(13, 9);
		assertTrue(testList.contains(board.getCellAt(13, 8)));
		assertTrue(testList.contains(board.getCellAt(13, 10)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(13,13);
		assertTrue(testList.contains(board.getCellAt(13, 12)));
		assertTrue(testList.contains(board.getCellAt(13, 14)));
		assertTrue(testList.contains(board.getCellAt(12, 13)));
		assertTrue(testList.contains(board.getCellAt(14, 13)));
		assertEquals(4, testList.size());

		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(19, 17);
		assertTrue(testList.contains(board.getCellAt(18, 17)));
		assertEquals(1, testList.size());

		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(13, 19);
		assertTrue(testList.contains(board.getCellAt(13, 18)));
		assertEquals(1, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(2, 5);
		assertTrue(testList.contains(board.getCellAt(1, 5)));
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		assertTrue(testList.contains(board.getCellAt(2, 6)));
		assertEquals(3, testList.size());
	}


	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are DARK GREEN on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(8, 19, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 19)));
		assertTrue(targets.contains(board.getCellAt(8,18)));	

		board.calcTargets(14, 0, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 0)));	
		assertTrue(targets.contains(board.getCellAt(15, 0)));			
	}

	// Tests of just walkways, 2 steps
	// These are DARK GREEN on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(8, 19, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 18)));
		assertTrue(targets.contains(board.getCellAt(8,17)));

		board.calcTargets(14, 0, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 0)));
		assertTrue(targets.contains(board.getCellAt(13, 1)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));			
	}

	// Tests of just walkways, 4 steps
	// These are DARK GREEN on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(8,19, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(7,16)));
		assertTrue(targets.contains(board.getCellAt(8, 17)));
		assertTrue(targets.contains(board.getCellAt(8, 15)));
		assertTrue(targets.contains(board.getCellAt(7, 18)));

		// Includes a path that doesn't have enough length
		board.calcTargets(14, 0, 4);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 4)));
		assertTrue(targets.contains(board.getCellAt(13, 3)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(12, 0)));
		assertTrue(targets.contains(board.getCellAt(13, 1)));

	}	

	// Tests of just walkways plus one door, 6 steps
	// These are DARK GREEN on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(19,5, 6);
		Set<BoardCell> targets= board.getTargets();
		//System.out.println(targets);
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 11)));
		assertTrue(targets.contains(board.getCellAt(13, 5)));	
		assertTrue(targets.contains(board.getCellAt(14, 4)));	
		assertTrue(targets.contains(board.getCellAt(14, 6)));	
		assertTrue(targets.contains(board.getCellAt(15, 5)));	
		assertTrue(targets.contains(board.getCellAt(19, 9)));	
		assertTrue(targets.contains(board.getCellAt(19, 7)));	
		assertTrue(targets.contains(board.getCellAt(16, 6)));	
		assertTrue(targets.contains(board.getCellAt(18, 6)));
		assertTrue(targets.contains(board.getCellAt(17, 5)));	

	}	

	// Test getting into a room
	// These are DARK GREEN on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		board.calcTargets(15, 17, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 17)));
		assertTrue(targets.contains(board.getCellAt(16, 18)));
		assertTrue(targets.contains(board.getCellAt(14, 16)));
		assertTrue(targets.contains(board.getCellAt(13, 17)));
	}


	// Test getting out of a room
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		board.calcTargets(9, 4, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(9, 5)));
		// Take two steps
		board.calcTargets(9, 4, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(8,5)));
		assertTrue(targets.contains(board.getCellAt(10, 5)));
		assertTrue(targets.contains(board.getCellAt(9, 6)));
	}

}
