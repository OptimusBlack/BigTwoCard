
/**
 * This class is used to represent a flush in the Big Two game. It consists of five cards of the same suit. The card with the highest rank in a flush is referred to as the top card of this flush. A flush always beats any straights. A flush with a higher suit beats a flush with a lower suit. For flushes with the same suit, the one having a top card with a higher rank beats the one having a top card with a lower rank.
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public class Flush extends Hand {

	/**
	 * Creates and returns an instance of the Flush class.
	 * 
	 * @param player
	 * 		The player that has played this hand of flush.
	 * @param cards
	 * 		The hand of flush that the player has played.
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * It overrides the abstract method isValid of the Hand class. Checks if this hand is a valid flush in the Big Two game.
	 * 
	 * @return
	 * 		A boolean value of true if this hand is a valid flush. Returns false otherwise.
	 */
	public boolean isValid() {
		if (this.size() == 5) {
			for (int i=1; i<this.size(); i++) {
				if (this.getCard(0).getSuit() != this.getCard(i).getSuit()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * It overrides the abstract method getType of the Hand class. Gets the type of the hand played.
	 * 
	 * @return
	 * 		A string of the type of the hand played. A flush in this case.
	 */
	public String getType() {
		return "Flush";
	}
}
