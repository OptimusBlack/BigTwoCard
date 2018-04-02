
/**
 * This class is used to represent a Full House in the Big Two game. This hand consists of five cards, with two having the same rank and three having another same rank. The card in the triplet with the highest suit in a full house is referred to as the top card of this full house. A full house always beats any straights and flushes. A full house having a top card with a higher rank beats a full house having a top card with a lower rank.
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public class FullHouse extends Hand{

	/**
	 * Creates and returns an instance of the FullHouse class.
	 * 
	 * @param player
	 * 		The player that has played this hand of Full House.
	 * @param cards
	 * 		The hand of Full House that the player has played
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * It overrides the abstract method isValid of the Hand class. Checks if this hand is a valid Full House in the Big Two game.
	 * 
	 * @return
	 * 		A boolean value of true if this hand is a valid full house. Returns false otherwise.
	 */
	public boolean isValid() {
		if (this.size() == 5) {
			int counter1=0, counter2=0;
			for (int i=0; i<this.size(); i++) {
				if (this.getCard(0).getRank() == this.getCard(i).getRank()) {
					counter1++;
				}
			}
			
			if (counter1 == 2) {
				for (int i=2; i<this.size(); i++) {
					if (this.getCard(2).getRank() == this.getCard(i).getRank()) {
						counter2++;
					}
				}
				
				if (counter2 == 3) {
					return true;
				}
			}
			
			else if (counter1 == 3) {
				for (int i=3; i<this.size(); i++) {
					if (this.getCard(3).getRank() == this.getCard(i).getRank()) {
						counter2++;
					}
				}
				
				if (counter2 == 2) {
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
	 * 		A string of the type of the hand played. A Full House in this case.
	 */
	public String getType() {
		return "FullHouse";
	}
}
