
/**
 * This class is used to represent a triple in the Big Two game. This hand consists of three cards with the same rank. The card with the highest suit in a triple is referred to as the top card of this triple. A triple with a higher rank beats a triple with a lower rank.
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public class Triple extends Hand{
	
	/**
	 * Creates and returns an instance of the Triple class.
	 * 
	 * @param player
	 * 		The player that has played this hand of triple.
	 * @param cards
	 * 		The hand of triple that the player has played.
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}

	/**
	 * It overrides the abstract method isValid of the Hand class. Checks if this hand is a valid triple in the Big Two game.
	 * 
	 * @return
	 * 		A boolean value of true if this hand is a valid triple. Returns false otherwise.
	 * 
	 */
	public boolean isValid() {
		if (this.size() == 3) {
			for (int i=1; i<this.size(); i++) {
				if (this.getCard(0).getRank() != this.getCard(i).getRank()) {
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
	 * 		A string of the type of the hand played. A triple in this case.
	 * 
	 */
	public String getType() {
		return "Triple";
	}

}
