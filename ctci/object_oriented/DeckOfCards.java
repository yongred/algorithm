/**
7.1 Deck of Cards: Design the data structures for a generic deck of cards. Explain how you would subclass the data structures to implement blackjack.
Hints: #153, #275
*/

public class DeckOfCards{
	public enum Identity{
		//two,three, ... King, Ace, jokers
		TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
	}

	public enum Suit{
		HEART, SPADE, CLUB, DIAMOND
	}

	public class Card{
		public Identity identity;
		public int val; //val of the card
		public Suit suit; // Heart, Spade, Club, Diamond 

		public Card(){ //defualt card
			this.identity = "two";
			this.val = 2;
			this.suit = "Heart";
		}

		public Card(Identity identity, Suit suit){
			this.identity = identity;
			this.suit = suit;
		}

		public Card(Identity identity, int val, Suit suit){
			this.identity = identity;
			this.val = val;
			this.suit = suit;
		}
	}

	public Card [] cards;
	//public int cardNum; //52 or 54
	public boolean useJokers; 

	public DeckOfCards(){

	} 

	public void generateCards(){
		if(!useJokers){
			int i=0;
			for(Identity ident : Identity.values()){
				for(Suit suit: Suit.values()){
					cards[i] = new Card()
				}
			}
		}
	}


}