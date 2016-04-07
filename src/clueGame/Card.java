package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String name, CardType typeCard) {
		cardName = name;
		type = typeCard;
	}
	
	public boolean equals() {
		return true;
	}
	
	public CardType getCardType() {
		return type;
	}
	
	public String getCardName() {
		return cardName;
	}
}
