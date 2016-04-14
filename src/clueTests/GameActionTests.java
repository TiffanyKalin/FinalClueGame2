package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class GameActionTests {

	//BeforeClass setups board to test
	private static Board board;
	private static Solution solution;
	private static Card kitchenCard;
	private static Card studyCard;
	private static Card ropeCard;
	private static Card daggerCard;
	private static Card scarlettCard;
	private static Card mustardCard;
	private static Set<Card> deal;
	@BeforeClass
	public static void setUp() {
		board = new Board("ClueLayout.csv", "Legend.txt");
		board.initialize();
		kitchenCard = new Card("Kitchen", CardType.ROOM);
		studyCard = new Card("Study", CardType.ROOM);
		ropeCard = new Card("Rope", CardType.WEAPON);
		daggerCard = new Card("Dagger", CardType.WEAPON);
		scarlettCard = new Card("Miss Scarlett", CardType.PERSON);
		mustardCard = new Card("Colonel Mustard", CardType.PERSON);
		
		deal = new HashSet<Card>();
		deal.add(daggerCard);
		deal.add(kitchenCard);
		deal.add(mustardCard);
		deal.add(ropeCard);
		deal.add(scarlettCard);
		deal.add(studyCard);

	}

	//tests checkAccusation method
	@Test
	public void testAccusation() {
		//create wrong solutions that will be tested
		Solution correct = board.getTheAnswer();
		Solution wrongRoom = new Solution(correct.person, correct.room + "wrong", correct.weapon);
		Solution wrongPerson = new Solution(correct.person + "wrong", correct.room, correct.weapon);
		Solution wrongWeapon = new Solution(correct.person, correct.room, correct.weapon + "wrong");		

		//assert statements to test
		assertTrue(board.checkAccusation(board.getTheAnswer()));
		assertFalse(board.checkAccusation(wrongRoom));
		assertFalse(board.checkAccusation(wrongPerson));
		assertFalse(board.checkAccusation(wrongWeapon));

	}

	//part of disprove suggestion tests: test one player returns only possible card 
	@Test
	public void testDisproveSuggestionCorrectMatch() {
		//create one player and deal six specific cards to that player
		Player redPlayer = new HumanPlayer("Miss Scarlett", "red", 1, 1);
		redPlayer.addCardToHand(daggerCard);
		redPlayer.addCardToHand(ropeCard);
		redPlayer.addCardToHand(studyCard);
		redPlayer.addCardToHand(kitchenCard);
		redPlayer.addCardToHand(scarlettCard);
		redPlayer.addCardToHand(mustardCard);

		/*check that card  tested that in one case the correct person was returned, in one case the correct weapon was returned, 
		 * in one the correct room was returned, and finally for another suggestion null was returned.*/
		assertEquals(scarlettCard ,redPlayer.disproveSuggestion(new Solution("Miss Scarlett", "Candlestick", "Lounge")));
		assertEquals(daggerCard, redPlayer.disproveSuggestion(new Solution("Professor Plum","Dagger", "Lounge")));
		assertEquals(studyCard, redPlayer.disproveSuggestion(new Solution("Professor Plum", "Candlestick", "Study")));
		assertEquals(null, redPlayer.disproveSuggestion(new Solution("Professor Plum", "Candlestick", "Lounge")));

	}

	//tests disproveSuggestion part ii: multiple possible matches
	@Test
	public void testDisproveSuggestionRandomChoice() {
		//create one player and deal six specific cards to that player
		Player redPlayer = new HumanPlayer("Miss Scarlett", "red", 1, 1);
		redPlayer.addCardToHand(daggerCard);
		redPlayer.addCardToHand(ropeCard);
		redPlayer.addCardToHand(studyCard);
		redPlayer.addCardToHand(kitchenCard);
		redPlayer.addCardToHand(scarlettCard);
		redPlayer.addCardToHand(mustardCard);		
		
		//variables to keep track of cards used and if only suggestion returned
		int scarlettCardCount = 0;
		int studyCardCount = 0;
		int ropeCardCount = 0;
		boolean returnedSuggestionsOnly = true;
		
		//for loop goes through cards keeping track of how many are used
		for (int i = 0; i < 100; i++) {
			Card picked = redPlayer.disproveSuggestion(new Solution("Miss Scarlett", "Study", "Rope"));
			if (picked.equals(scarlettCard)) {
				scarlettCardCount++;
			}
			else if (picked.equals(studyCard)) {
				studyCardCount++;				
			}
			else if (picked.equals(ropeCard)) {
				ropeCardCount++;				
			}
			else {
				returnedSuggestionsOnly = false;
			}
		}
		
		//assert statements 
		assertTrue(scarlettCardCount > 0);
		assertTrue(studyCardCount > 0);
		assertTrue(ropeCardCount > 0);
		assertTrue(returnedSuggestionsOnly);
	}
	
	//test disprove suggestion part iii: all players queried; human player tested; player does not return card if it is his/her turn
	@Test
	public void testDisproveSuggestionPlayersQueried() {
		//create computer players, deal specific cards, create human player
		Vector<Player> playerList = new Vector<Player>();
		for (int i = 0; i < 5; i++) {
			playerList.add(new ComputerPlayer("computer" + i, "red", i, i));
		}
		playerList.get(0).addCardToHand(scarlettCard);
		playerList.get(1).addCardToHand(studyCard);
		playerList.get(2).addCardToHand(daggerCard);
		playerList.get(3).addCardToHand(mustardCard);
		playerList.get(4).addCardToHand(kitchenCard);
		HumanPlayer humanPlayer = new HumanPlayer("human", "red", 6, 6);
		humanPlayer.addCardToHand(ropeCard);
		playerList.add(humanPlayer);

		//set board variables
		board.setPlayers(playerList);
		board.setHumanPlayer(humanPlayer);
		
		//assert testing null and human player	
		assertEquals(null, board.handleSuggestion(new Solution("Professor Plum", "Lounge", "Candlestick"), playerList.get(0).getPlayerName(), new BoardCell(19, 4)));
		assertEquals(ropeCard, board.handleSuggestion(new Solution("Professor Plum", "Lounge", "Rope"), playerList.get(0).getPlayerName(), new BoardCell(19, 4)));
		
		//current player has card, null returned; tested computer and human
		assertEquals(null, board.handleSuggestion(new Solution("Miss Scarlett", "Lounge", "Candlestick"), playerList.get(0).getPlayerName(), new BoardCell(19, 4)));
		assertEquals(null, board.handleSuggestion(new Solution("Professor Plum", "Lounge", "Rope"), humanPlayer.getPlayerName(), new BoardCell(19, 4)));
		
		//check first player disproves and all players are queried 
		assertEquals(studyCard, board.handleSuggestion(new Solution("Professor Plum", "Study", "Dagger"), playerList.get(0).getPlayerName(), new BoardCell(19, 4)));
		assertEquals(ropeCard, board.handleSuggestion(new Solution("Professor Plum", "Lounge", "Rope"), playerList.get(0).getPlayerName(), new BoardCell(19, 4)));
	}
	
	//test select room always if in target list
	@Test
	public void testTargetSelectRoom() {
		ComputerPlayer player = new ComputerPlayer("", "red", 14, 4);
		// Pick a location with room in target
		board.calcTargets(14, 4, 2);
		int inRoom = 0;
		
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(15, 4))
				inRoom++;
		}
		
		// Ensure room was selected each time
		assertEquals(100, inRoom);							
	}
	
	//test random selection of room
	@Test
	public void testTargetRandomSelection() {
		ComputerPlayer player = new ComputerPlayer("", "red", 12, 6);
		// Pick a location with no rooms in target, just five targets
		board.calcTargets(12, 6, 2);
		boolean loc_14_6 = false;
		boolean loc_11_5 = false;
		boolean loc_10_6 = false;
		boolean loc_13_5 = false;
		boolean loc_13_7 = false;
		
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(14, 6))
				loc_14_6 = true;
			else if (selected == board.getCellAt(11, 5))
				loc_11_5 = true;
			else if (selected == board.getCellAt(10, 6))
				loc_10_6 = true;
			else if (selected == board.getCellAt(13, 5))
				loc_13_5 = true;
			else if (selected == board.getCellAt(13, 7))
				loc_13_7 = true;
			else
				fail("Invalid target selected");
		}
		// Ensure each target was selected at least once
		assertTrue(loc_14_6);
		assertTrue(loc_11_5);
		assertTrue(loc_10_6); 
		assertTrue(loc_13_5); 
		assertTrue(loc_13_7); 
	}
	
	//test last visited room is not visited again
	@Test
	public void testTargetVisitedRoom() {
		//create player and add room that will be last visited room
		ComputerPlayer player = new ComputerPlayer("", "red", 14, 4);
		player.setLastVisited('C');
		
		// Pick a location with rooms in target
		board.calcTargets(14, 4, 1);
		boolean loc_13_4 = false;
		boolean loc_14_3 = false;
		boolean loc_14_5 = false;
		boolean loc_15_4 = false;
		
		//for loop testing each place is selected 
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(13, 4))
				loc_13_4 = true;
			else if (selected == board.getCellAt(14, 3))
				loc_14_3 = true;
			else if (selected == board.getCellAt(14, 5))
				loc_14_5 = true;
			else if (selected == board.getCellAt(15, 4))
				loc_15_4 = true;
			else
				fail("Invalid target selected");
		}	
		
		//assert statements 
		assertTrue(loc_13_4);
		assertTrue(loc_14_3);
		assertTrue(loc_14_5);
		assertTrue(loc_15_4); 
		
	}
	
	//test computer player making suggestion - only one suggestion possible
	@Test
	public void testComputerSuggestion() {
		//has all cards in small deck except three
		ComputerPlayer redPlayer = new ComputerPlayer("red", "red", 1,1);
		redPlayer.seeCard(ropeCard);
		redPlayer.seeCard(scarlettCard);
		redPlayer.seeCard(studyCard);

		//makes sure suggestion created is expected suggestion
		Solution answer = new Solution("Colonel Mustard", "Kitchen", "Dagger"); 
		Solution suggestion = redPlayer.makeSuggestion(board,board.getCellAt(1, 1), deal);
		assertEquals(answer.room, suggestion.room);
		assertEquals(answer.weapon, suggestion.weapon);
		assertEquals(answer.person, suggestion.person);
		
	}
	
	//test computer player making suggestion - random solutions possible
	@Test
	public void testComputerSuggestionRandom() {
		//create player and some cards; two possible people cards 
		ComputerPlayer redPlayer = new ComputerPlayer("red", "red", 1,1);
		redPlayer.seeCard(ropeCard);
		redPlayer.seeCard(studyCard);
		boolean isScarlett = false;
		boolean isMustard = false;
		Solution answer = new Solution("Colonel Mustard", "Kitchen", "Dagger");  

		//for loop testing that each person card is selected at least once 
		for (int i = 0; i < 100; i++) {		
			Solution suggestion = redPlayer.makeSuggestion(board,board.getCellAt(1, 1), deal);
			if (suggestion.person == "Colonel Mustard")
				isMustard = true;
			else if (suggestion.person == "Miss Scarlett") 
				isScarlett = true;
			assertEquals(answer.room, suggestion.room);
			assertEquals(answer.weapon, suggestion.weapon);
		}
		
		//assert statements 
		assertTrue(isScarlett);
		assertTrue(isMustard);
		
	}
}


