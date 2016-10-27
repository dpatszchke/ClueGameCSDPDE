package clueGame;

public class Card{
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





	@Override
	public boolean equals(Object obj) {
		Card other = (Card) obj;
		if (cardName == null) {
			if (other.cardName != null)
				return false;
		} else if (!cardName.equals(other.cardName))
			return false;
		if (cardType != other.cardType)
			return false;
		return true;
	}
	
	
	
}
