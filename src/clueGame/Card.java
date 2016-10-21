package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	

	public String getCardName() {
		return cardName;
	}



	public CardType getCardType() {
		return cardType;
	}



	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}



	public boolean equals(){
		return true;
	}



	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", cardType=" + cardType + "]";
	}
	
}
