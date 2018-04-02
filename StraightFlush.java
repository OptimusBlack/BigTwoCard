
/**
 * This class is used to represent a straight flush in the Big Two game. This hand consists of five cards with consecutive ranks and the same suit. The card with the highest rank in a straight flush is referred to as the top card of this straight flush. A straight flush always beats any straights, flushes, full houses and quads. A straight flush having a top card with a higher rank beats a straight flush having a top card with a lower rank. For straight flushes having top cards with the same rank, the one having a top card with a higher suit beats one having a top card with a lower suit.
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public class StraightFlush extends Hand{
	
	/**
	 * Creates and returns an instance of the StraightFlush class.
	 * 
	 * @param player
	 * 		The player that has played this hand of straight flush.
	 * @param cards
	 * 		he hand of straight flush that the player has played.
	 */
	public StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}

	/**
	 * It overrides the abstract method isValid of the Hand class. Checks if this hand is a valid straight flush in the Big Two game.
	 * 
	 * @return
	 * 		A boolean value of true if this hand is a valid straight flush. Returns false otherwise.
	 * 
	 */
	public boolean isValid() {
		if (this.size() == 5) {
			boolean sameSuits = true;
			for (int i=1; i<this.size() && sameSuits; i++) {
				if (this.getCard(0).getSuit() != this.getCard(i).getSuit()) {
					sameSuits = false;
				}
			}
			
			if (sameSuits) {
				if (this.getCard(4).getRank() == 1) {
					if ( this.getCard(3).getRank() == 0 && this.getCard(2).getRank() == 12 && this.getCard(1).getRank() == 11 && this.getCard(0).getRank() == 10 ) {
						return true;
					}
				}
				
				else if (this.getCard(4).getRank() == 0) {
					if ( this.getCard(3).getRank() == 12 && this.getCard(2).getRank() == 11 && this.getCard(1).getRank() == 10 && this.getCard(0).getRank() == 9 ) {
						return true;
					}
				}
				
				else {
					boolean isConsec = true;
					for (int i=0; i<this.size()-1 && isConsec; i++) {
						if ( this.getCard(i+1).getRank() - this.getCard(i).getRank() != 1 ) {
							isConsec = false;
						}
					}
					
					if (isConsec) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * It overrides the abstract method getType of the Hand class. Gets the type of the hand played.
	 * 
	 * @return
	 * 		A string of the type of the hand played. A staright flush in this case.
	 * 
	 */
	public String getType() {
		return "StraightFlush";
	}

}
