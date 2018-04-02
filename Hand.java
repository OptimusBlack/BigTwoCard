
/**
 * This class is used to model a hand in the Big Two game. This is an abstract class.
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public abstract class Hand extends CardList {
	
	private CardGamePlayer player;
	
	/**
	 * Creates and returns an instance of the Hand class. Can only be used by subclasses since this class is abstract.
	 * 
	 * @param player
	 * 		The player that has played this hand.
	 * @param cards
	 * 		The list of cards that would make the hand.
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player = player;
		cards.sort();
		
		for (int i=0; i<cards.size(); i++) {
			this.addCard(cards.getCard(i));
		}
	}
	
	/**
	 * The player that has played this hand.
	 * 
	 * @return
	 * 		The player who has played this hand.
	 */
	public CardGamePlayer getPlayer() {
		return player;
	}
	
	/**
	 * Retrieves the top card in this hand according to the Big Two card game rules.
	 * 
	 * @return
	 * 		A card that is of the top rank in the hand.
	 */
	public Card getTopCard() {
		if (this.getType() == "FullHouse") {
			Card card1;
			int counter1=0, counter2=0;
			card1 = this.getCard(this.size()-1);
			for (int i=0; i<this.size(); i++) {
				if (card1.getRank() == this.getCard(i).getRank()) {
					counter1++;
				}
				else {
					counter2++;
				}
			}
			
			if (counter1 > counter2) {
				return card1;
			}
			else {
				return this.getCard(2);
			}
		}
		
		else if (this.getType() == "Quad") {
			Card card1;
			int counter1=0, counter2=0;
			card1 = this.getCard(this.size()-1);
			for (int i=0; i<this.size(); i++) {
				if (card1.getRank() == this.getCard(i).getRank()) {
					counter1++;
				}
				else {
					counter2++;
				}
			}
			
			if (counter1 > counter2) {
				return card1;
			}
			else {
				return this.getCard(3);
			}
		}
		
		else {
			return this.getCard(this.size()-1);
		}
	}
	
	/**
	 * Checks if this hand has beaten the specified hand according to the Big Two game rules. 
	 *
	 * @param hand
	 * 		The hand that is to be checked if it has been beaten by this hand in the Big Two card game.
	 * @return
	 * 		A boolean value true if this card beats the specified hand. Returns false otherwise.
	 */
	public boolean beats(Hand hand) {
		if ( (this.getType() == "StraightFlush") && (hand.getType() == "StraightFlush") ) {
			if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
		}
		
		else if ( (this.getType() == "StraightFlush") && (hand.getType() == "Quad" || hand.getType() == "FullHouse" || hand.getType() == "Flush" || hand.getType() == "Straight") ) {
			return true;
		}
		
		else if ( (this.getType() == "Quad") && (hand.getType() == "Quad") ) {
			if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
		}
		
		else if ( (this.getType() == "Quad") && (hand.getType() == "FullHouse" || hand.getType() == "Flush" || hand.getType() == "Straight") ) {
			return true;
		}
		
		else if ( (this.getType() == "FullHouse") && (hand.getType() == "FullHouse") ) {
			if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
		}
		
		else if ( (this.getType() == "FullHouse") && (hand.getType() == "Flush" || hand.getType() == "Straight") ) {
			return true;
		}
		
		else if ( (this.getType() == "Flush") && (hand.getType() == "Flush") ) {
			boolean sameSuits = true;
			if (this.getCard(0).getSuit() != hand.getCard(0).getSuit()) {
				sameSuits = false;
			}
			
			if (sameSuits) {
				if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
					return true;
				}
			}
			
			else {
				if (this.getCard(0).getSuit() > hand.getCard(0).getSuit()) {
					return true;
				}
			}
		}
		
		else if ( (this.getType() == "Flush") && (hand.getType() == "Straight") ) {
			return true;
		}
		
		else if ( (this.getType() == "Straight") && (hand.getType() == "Straight") ) {
			if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
		}
		
		else if ( (this.getType() == "Triple") && (hand.getType() == "Triple") ) {
			if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
		}
		
		else if ( (this.getType() == "Pair") && (hand.getType() == "Pair") ) {
			if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
		}
		
		else if ( (this.getType() == "Single") && (hand.getType() == "Single") ) {
			if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * An abstract method to be overridden in the subclasses. Checks if the hand played is valid.
	 * 
	 * @return
	 * 		A boolean true if the hand played is a valid hand. Returns false otherwise.
	 */
	public abstract boolean isValid();
	/**
	 * An abstract method to be overridden in the subclasses. It gets the type of the hand played.
	 * 
	 * @return
	 * 		A string specifiying the type of hand played.
	 */
	public abstract String getType();
}
