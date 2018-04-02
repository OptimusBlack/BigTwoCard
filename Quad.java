
/**
 * This class is used to represent a Quad in the Big Two game. This hand consists of five cards, with four having the same rank. The card in the quadruplet with the highest suit in a quad is referred to as the top card of this quad. A quad always beats any straights, flushes and full houses. A quad having a top card with a higher rank beats a quad having a top card with a lower rank.
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public class Quad extends Hand {

	/**
	 * Creates and returns an instance of the Quad class.
	 * 
	 * @param player
	 * 		The player that has played this hand of quad.
	 * @param cards
	 * 		The hand of quad that the player has played.
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * It overrides the abstract method isValid of the Hand class. Checks if this hand is a valid quad in the Big Two game.
	 * 
	 * @return
	 * 		A boolean value of true if this hand is a valid quad. Returns false otherwise.
	 */
	public boolean isValid() {
		if (this.size() == 5) {
			int counter=0;
			for (int i=0; i<this.size(); i++) {
				if (this.getCard(0).getRank() == this.getCard(i).getRank()) {
					counter++;
				}
			}
			
			if (counter == 4) {
				return true;
			}
			
			else {
				counter=0;
				for (int i=1; i<this.size(); i++) {
					if (this.getCard(1).getRank() == this.getCard(i).getRank()) {
						counter++;
					}
				}
				
				if (counter == 4) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * It overrides the abstract method getType of the Hand class. Gets the type of the hand played.
	 * 
	 *  @return
	 * 		A string of the type of the hand played. A quad in this case.
	 */
	public String getType() {
		return "Quad";
	}

}
