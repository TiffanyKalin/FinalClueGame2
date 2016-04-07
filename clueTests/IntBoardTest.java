package clueTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import clueGame.*;

public class IntBoardTest {
	
	private Board testGrid;

	@Before 
	public void init(){
		testGrid = new Board(4,4);
		testGrid.calcAdjacencies();
	}


	@Test
	public void testAdjacency0() { //top left
		BoardCell cell = testGrid.getCellAt(0,0);
		LinkedList<BoardCell> testList = testGrid.getAdjList(cell);
		assertTrue(testList.contains(testGrid.getCellAt(1, 0)));
		assertTrue(testList.contains(testGrid.getCellAt(0, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjecency3_3(){  //bottom right
		BoardCell cell = testGrid.getCellAt(3,3);
		LinkedList<BoardCell> testList = testGrid.getAdjList(cell);
		assertTrue(testList.contains(testGrid.getCellAt(2, 3)));
		assertTrue(testList.contains(testGrid.getCellAt(3, 2)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency2_3(){ //right edge
		BoardCell cell = testGrid.getCellAt(2, 3);
		LinkedList<BoardCell> testList = testGrid.getAdjList(cell);
		assertTrue(testList.contains(testGrid.getCellAt(1,3)));
		assertTrue(testList.contains(testGrid.getCellAt(2,2)));
		assertTrue(testList.contains(testGrid.getCellAt(3,3)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency2_0(){ //left edge
		BoardCell cell = testGrid.getCellAt(2, 0);
		LinkedList<BoardCell> testList = testGrid.getAdjList(cell);
		assertTrue(testList.contains(testGrid.getCellAt(1,0)));
		assertTrue(testList.contains(testGrid.getCellAt(2,1)));
		assertTrue(testList.contains(testGrid.getCellAt(3,0)));
		assertEquals(3, testList.size());

	}
	
	@Test
	public void testAdjacency1_1(){//second column middle
		BoardCell cell = testGrid.getCellAt(1, 1);
		LinkedList<BoardCell> testList = testGrid.getAdjList(cell);
		assertTrue(testList.contains(testGrid.getCellAt(0,1)));
		assertTrue(testList.contains(testGrid.getCellAt(2,1)));
		assertTrue(testList.contains(testGrid.getCellAt(1,0)));
		assertTrue(testList.contains(testGrid.getCellAt(1,2)));
		assertEquals(4, testList.size());

	}
	
	@Test
	public void testAdjacency2_2(){//second from last/middle
		BoardCell cell = testGrid.getCellAt(2, 2);
		LinkedList<BoardCell> testList = testGrid.getAdjList(cell);
		assertTrue(testList.contains(testGrid.getCellAt(1,2)));
		assertTrue(testList.contains(testGrid.getCellAt(3,2)));
		assertTrue(testList.contains(testGrid.getCellAt(2,1)));
		assertTrue(testList.contains(testGrid.getCellAt(2,3)));
		assertEquals(4, testList.size());

	}
	
	//============================================================================

	@Test
	public void testTargets0() { //top left
		testGrid.calcTargets(0, 0, 2);
		Set targets = testGrid.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(testGrid.getCellAt(0,2)));
		assertTrue(targets.contains(testGrid.getCellAt(1, 1)));
		//assertTrue(targets.contains(testGrid.getCellAt(0, 1)));
		assertTrue(targets.contains(testGrid.getCellAt(2, 0)));
	}
	
	@Test
	public void testTargets3_3() { //bottom right
		testGrid.calcTargets(3, 3, 2);
		Set targets = testGrid.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(testGrid.getCellAt(3, 1)));
		assertTrue(targets.contains(testGrid.getCellAt(1, 3)));
		assertTrue(targets.contains(testGrid.getCellAt(2, 2)));
	}
	@Test
	public void testTargets2_3() { //right edge
		testGrid.calcTargets(2, 3, 2);
		Set targets = testGrid.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(testGrid.getCellAt(0,3)));
		assertTrue(targets.contains(testGrid.getCellAt(1,2)));
		assertTrue(targets.contains(testGrid.getCellAt(2,1)));
		assertTrue(targets.contains(testGrid.getCellAt(3,2)));
	}
	@Test
	public void testTargets2_0() {//left edge
		testGrid.calcTargets(2, 0,2);
		Set targets = testGrid.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(testGrid.getCellAt(0,0)));
		assertTrue(targets.contains(testGrid.getCellAt(1, 1)));
		assertTrue(targets.contains(testGrid.getCellAt(2,2)));
		assertTrue(targets.contains(testGrid.getCellAt(3,1)));

	}
	@Test
	public void testTargets1_1() {//second column middle
		testGrid.calcTargets(1, 1, 2);
		Set targets = testGrid.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(testGrid.getCellAt(0,0)));
		assertTrue(targets.contains(testGrid.getCellAt(0,2)));
		assertTrue(targets.contains(testGrid.getCellAt(1,3)));
		assertTrue(targets.contains(testGrid.getCellAt(2,0)));
		assertTrue(targets.contains(testGrid.getCellAt(2,2)));
		assertTrue(targets.contains(testGrid.getCellAt(3,1)));
	}
	@Test
	public void testTargets2_2() {//second from last/middle
		testGrid.calcTargets(2, 2, 2);
		Set targets = testGrid.getTargets();
		System.out.println(targets);
		assertEquals(6, targets.size());
		//System.out.println(targets);
		assertTrue(targets.contains(testGrid.getCellAt(0,2)));
		assertTrue(targets.contains(testGrid.getCellAt(1,1)));
		assertTrue(targets.contains(testGrid.getCellAt(1,3)));
		assertTrue(targets.contains(testGrid.getCellAt(2,0)));
		assertTrue(targets.contains(testGrid.getCellAt(3,1)));
		assertTrue(targets.contains(testGrid.getCellAt(3,3)));
	}
	
}
