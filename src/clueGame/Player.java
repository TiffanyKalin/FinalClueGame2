package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.lang.reflect.Field;

public class Player {
	String playerName;
	int row;
	int column;
	Color color;
	Set<Card> myCards;
	Set<Card> seenCards;
	
	public Player(String pName, String color, int row, int col) {
		this.playerName = pName;
		this.color = convertColor(color.trim());
		this.row = row;
		this.column = col;
		myCards = new HashSet<Card>();
		seenCards = new HashSet<Card>();
	}
	
	public Card disproveSuggestion (Solution suggestion) {
		Random rand = new Random();
		Vector<Card> disprovers = new Vector<Card>();
		for (Card c: myCards) 
			if (c.getCardName() == suggestion.person || c.getCardName() == suggestion.room || c.getCardName() == suggestion.weapon)
				disprovers.add(c);		
		if (disprovers.size() > 0)
			return disprovers.get(rand.nextInt(disprovers.size()));
		return null;
	}
	
    // Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}

	//getters for test purposes 
	public String getPlayerName() {
		return playerName;
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

	public Color getColor() {
		return color;
	}

	public Set<Card> getMyCards() {
		return myCards;
	}

	public Set<Card> getSeenCards() {
		return seenCards;
	}
	
	public void addCardToHand(Card card) {
		myCards.add(card);
	}
	
	public void setSeenCards(Set<Card> seenCards) {
		this.seenCards = seenCards;
	}
	public void seeCards(Set<Card> seenCards) {
		this.seenCards.addAll(seenCards);
	}
	public void seeCard(Card card) {
		this.seenCards.add(card);
	}

	public void makeMove(Set<BoardCell> targets) {
		// TODO Auto-generated method stub
		
	}
}
