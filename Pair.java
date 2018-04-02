
/**
 * This class is used to represent a Pair in the Big Two game. This hand consists of two cards with the same rank. The card with a higher suit in a	pair is referred to as the top card of this pair. A pair with a higher rank beats a pair with a lower rank. For pairs with the same rank, the one containing the highest suit beats the other.
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public class Pair extends Hand{
	
	/**
	 * Creates and returns an instance of the Pair class.
	 * 
	 * @param player
	 * 		The player that has played this hand of Pair.
	 * @param cards
	 * 		The hand of pair that the player has played.
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}

	/**
	 * It overrides the abstract method isValid of the Hand class. Checks if this hand is a valid pair in the Big Two game.
	 * 
	 * @return
	 * 		A boolean value of true if this hand is a valid pair. Returns false otherwise.
	 */
	public boolean isValid() {
		if ( (this.size() == 2) && (this.getCard(0).getRank() == this.getCard(1).getRank()) ) {
			return true;
		}
		return false;
	}
	
	/**
	 * It overrides the abstract method getType of the Hand class. Gets the type of the hand played.
	 * 
	 * @return
	 * 		A string of the type of the hand played. A pair in this case.
	 * 
	 */
	public String getType() {
		return "Pair";
	}

}
