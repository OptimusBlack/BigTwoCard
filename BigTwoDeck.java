
/**
 * This class is used to represent a deck of cards in the Big Two game accorkding to the Big Two game rules.
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public class BigTwoDeck extends Deck{

	/**
	 * Creates and return an instance of the BigTwoDeck class.
	 */
	public BigTwoDeck() {
		this.initialize();
	}
	
	/**
	 * This method overrides the initialize method in the Deck class. It initializes the deck of cards with Big Two cards. 
	 */
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				this.addCard(card);
			}
		}
	}
	
}
