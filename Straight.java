
/**
 * This class is used to represent a straight in the Big Two game. This hand consists of five cards with consecutive ranks. The card with the highest rank in a straight is referred to as the top card of this straight. A straight having a top card with a higher rank beats a straight having a top card with a lower rank. For straights having top cards with the same rank, the one having a top card with a higher suit beats the one having a top card with a lower suit.
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public class Straight extends Hand {
	
	/**
	 * Creates and returns an instance of the Straight class.
	 * 
	 * @param player
	 * 		The player that has played this hand of straight.
	 * @param cards
	 * 		The hand of straight that the player has played.
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}

	/**
	 * It overrides the abstract method isValid of the Hand class. Checks if this hand is a valid straight in the Big Two game.
	 * 
	 * @return
	 * 		A boolean value of true if this hand is a valid straight. Returns false otherwise.
	 * 
	 */
	public boolean isValid() {
		if (this.size() == 5) {
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
		return false;
	}
	
	/**
	 * It overrides the abstract method getType of the Hand class. Gets the type of the hand played.
	 * 
	 * @return
	 * 		A string of the type of the hand played. A straight in this case.
	 * 
	 */
	public String getType() {
		return "Straight";
	}

}
