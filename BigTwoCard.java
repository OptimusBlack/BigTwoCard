
/**
 * This class is used for representing a card following the Big Two game rules (2 of Spades is the highest rank followed by A of spades).
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public class BigTwoCard extends Card {

	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
	}

	/**
	 * This method overrides the method CompareTo in the Card class. Compares this card with the specified card for order according the Big two card game rules. For example, if this card is the spades of 2 and the specified card is of spades of 3 so it will report spades of 2 as the card of the higher rank.
	 * 
	 * @param card
	 * 		The card to be compared.
	 * 
	 * @return
	 * 		A negative integer, zero, or a positive integer as this card is	less than, equal to, or greater than the specified card.
	 */
	public int compareTo(Card card) {
		int thisRank, cardRank;
		if (this.rank == 0 || this.rank == 1) {
			thisRank = this.rank + 13;
		}
		
		else {
			thisRank = this.rank;
		}
		
		if (card.rank == 0 || card.rank == 1) {
			cardRank = card.rank + 13;
		}
		else {
			cardRank = card.rank;
		}
		
		if (thisRank > cardRank) {
			return 1;
		} else if (thisRank < cardRank) {
			return -1;
		} else if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else {
			return 0;
		}
	}
}
