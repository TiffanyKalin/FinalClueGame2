package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class ComputerPlayer extends Player{
	private char lastVisited;	
	
	public ComputerPlayer(String pName, String color, int row, int col) {
		super(pName, color, row, col);
		lastVisited = ' ';
		// TODO Auto-generated constructor stub
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		Vector<BoardCell> possibleTargets = new Vector<BoardCell>();		
		for (BoardCell c : targets) {
			if (c.isDoorway() && c.getInitial() != lastVisited)
				return c;
			else
				possibleTargets.add(c);
		}
		Random rand = new Random();		
		return possibleTargets.get(rand.nextInt(possibleTargets.size()));
	}
	
	public Solution makeAccustation(Set<Card> allCards) {
		Solution accusation;
		String weapon = "";
		String playerName = "";
		String room = "";
		
		accusation = new Solution(playerName, room, weapon);
		return accusation;
		
	}
	
	public Solution makeSuggestion(Board board, BoardCell location, Set<Card> allCards) {
			Solution suggestion;
			String weapon = "";
			String playerName = "";
			String room = "";
			ArrayList<String> potentialPlayers = new ArrayList<String>();		
			ArrayList<String> potentialWeapons = new ArrayList<String>();
			for (Card c : allCards) {
				if (!seenCards.contains(c))
					switch (c.getCardType()) {
					case PERSON:
						potentialPlayers.add(c.getCardName());
						break;
					case WEAPON:
						potentialWeapons.add(c.getCardName());
						break;
					}
			}			
			Random rand = new Random();			
			playerName = potentialPlayers.get(rand.nextInt(potentialPlayers.size()));
			char loc = location.getInitial();
			switch (loc) {
			case 'C':
				room = "Conservatory";
				break;
			case 'F':
				room = "Billiards";
				break;
			case 'B':
				room = "Ballroom";
				break;
			case 'A':
				room = "Kitchen";
				break;
			case 'I':
				room = "Library";
				break;
			case 'D':
				room = "Dining";
				break;
			case 'H':
				room = "Hall";
				break;
			case 'J':
				room = "Lounge";
				break;
			case 'G':
				room = "Study";
				break;
			}
			weapon = potentialWeapons.get(rand.nextInt(potentialWeapons.size()));
			suggestion = new Solution(playerName, room, weapon);
			return suggestion;
	}
	
	public char getLastVisited() {
		return lastVisited;
	}
	
	public void setLastVisited(char visited) {
		lastVisited = visited;
	}
}
