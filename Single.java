
/**
 * This class is used to represent a single in the Big Two game. This hand consists of only one single card. The only card in a single is referred to as the top card of this single. A single with a higher rank beats a single with a lower rank.
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public class Single extends Hand {
	
	/**
	 * Creates and returns an instance of the Single class.
	 * 
	 * @param player
	 * 		The player that has played this hand of single.
	 * @param cards
	 * 		The hand of single that the player has played.
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}

	/**
	 * It overrides the abstract method isValid of the Hand class. Checks if this hand is a valid single in the Big Two game.
	 * 
	 * @return
	 * 		A boolean value of true if this hand is a valid single. Returns false otherwise.
	 * 
	 */
	public boolean isValid() {
		if (this.size() == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * It overrides the abstract method getType of the Hand class. Gets the type of the hand played.
	 * 
	 * @return
	 * 		A string of the type of the hand played. A single in this case.
	 * 
	 */
	public String getType() {
		return "Single";
	}

}
