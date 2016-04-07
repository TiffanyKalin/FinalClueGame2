package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.List;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class GameSetupTests {

	//BeforeClass method
	private static Board board;
	private static Card personCard;
	private static Card weaponCard;
	private static Card roomCard;
	@BeforeClass
	public static void setUp() {
		//create Board and cards to test
		board = new Board("ClueLayout.csv", "Legend.txt");
		board.initialize();
		personCard = new Card("Miss Scarlett", CardType.PERSON);							   
		weaponCard = new Card("Candlestick", CardType.WEAPON);
		roomCard = new Card("Kitchen", CardType.ROOM);
	}

	/*tests loading people: tests that human player and 2 computer players have correct 
	name, color, and starting location(row and column)*/
	@Test
	public void testLoadPeople() {
		//human player
		assertEquals("Miss Scarlett", board.getPlayers().get(0).getPlayerName());
		assertEquals(Color.red, board.getPlayers().get(0).getColor());
	    assertEquals(0, board.getPlayers().get(0).getRow());
		assertEquals(6, board.getPlayers().get(0).getColumn());
	
		//two computer players
		assertEquals("Colonel Mustard", board.getPlayers().get(4).getPlayerName());
		assertEquals(Color.yellow, board.getPlayers().get(4).getColor());
		assertEquals(19, board.getPlayers().get(4).getRow());
		assertEquals(13, board.getPlayers().get(4).getColumn());
		
		assertEquals("Mrs. White", board.getPlayers().get(5).getPlayerName());
		assertEquals(Color.white, board.getPlayers().get(5).getColor());
		assertEquals(7, board.getPlayers().get(5).getRow());
		assertEquals(19, board.getPlayers().get(5).getColumn());
	}
	
	//test loading cards from file
	@Test
	public void testLoadCards() {
		//test the deck contains the correct number of cards (will include accusation cards)
		assertEquals(21, board.getPlayingCards().size());
		
		//test the deck contains cards of each type 
		int weapons = 0;
		int peoples = 0;
		int rooms = 0;
		boolean foundPerson = false;
		boolean foundWeapon = false;
		boolean foundRoom = false;
		//use an iterator for loop to increment each variable representing number of each card type
		for (Iterator<Card> cards = board.getPlayingCards().iterator(); cards.hasNext();) {
			Card card = cards.next();
			
			switch (card.getCardType()) {
			case PERSON:
				peoples++;
				if (card.getCardName().equalsIgnoreCase(personCard.getCardName())) 
					foundPerson = true;					
				break;
			case WEAPON:
				weapons ++;
				if (card.getCardName().equalsIgnoreCase(weaponCard.getCardName())) 
					foundWeapon = true;
				break;
			case ROOM:
				rooms++;
				if (card.getCardName().equalsIgnoreCase(roomCard.getCardName()))
					foundRoom = true;
				break;			
			}
		}
		//assertEquals statements: have correct number of each type of cards 
		assertEquals(6, peoples);
		assertEquals(6, weapons);
		assertEquals(9, rooms);
		
		//testing that deck contains cards created in BeforeClass
		assertTrue(foundPerson);
		assertTrue(foundWeapon);
		assertTrue(foundRoom);
	}
	
	//testing valid deal of cards
	@Test
	public void testCardDealing() {
		//test all cards are dealt and players have same number of cards
		LinkedList<String> currentDeck = new LinkedList<String>();
		int maxCards = 0;
		int minCards = 100;
		int numCards = 0;
		
		//go through all players
		for (Iterator<Player> players = board.getPlayers().iterator(); players.hasNext(); ) {
			Player player = players.next();
			int cards = player.getMyCards().size();
			
			//determine min and max of cards to determine if difference is close
			if (cards > maxCards) maxCards = cards;
			if (cards < minCards) minCards = cards;
			
			//add to find number of cards
			numCards += cards;
			
			//go through each player hand and compare it with a linked list to make sure no duplicates
			for (Iterator<Card> playerHand = player.getMyCards().iterator(); playerHand.hasNext();) {
				Card currentCard = playerHand.next();
				String name = currentCard.getCardName();
				if (currentDeck.contains(name)) fail("Contains duplicate cards.");
				else currentDeck.add(name);
			}
			
		}
		//assertTrue statements to make sure players have roughly same cards and the whole deck (minus cards for accusation) is dealt
		int difference = maxCards - minCards;
		assertTrue( difference <= 2);
		assertEquals(18, numCards);
	}
	
}
