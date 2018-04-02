import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class is used to model a Big Two card game.
 * 
 * @author Lakhani, Amsal Murad
 *
 */
public class BigTwoClient implements CardGame, NetworkGame{
    private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID;
	private String playerName;
	private String serverIP = "127.0.0.1";
	private int serverPort = 2396;
	private Socket sock;
	private ObjectOutputStream oos;
	private int currentIdx;
	private BigTwoTable bigTwoTable;
	private boolean firstMoveDone;

	/**
	 * Creates and returns an instance of the BigTwoClient class.
	 */
	public BigTwoClient() {
		playerList = new ArrayList<CardGamePlayer>();
		handsOnTable = new ArrayList<Hand>();
		numOfPlayers = 4;

		for (int i=0; i<4; i++) {
			this.playerList.add(new CardGamePlayer());
		}

        String inputName = JOptionPane.showInputDialog(null, "", "Enter your name", JOptionPane.PLAIN_MESSAGE);
        while (inputName == null){
            inputName = JOptionPane.showInputDialog(null, "Please enter a valid name!", "Enter your name", JOptionPane.ERROR_MESSAGE);
        }
        playerName = inputName;

		bigTwoTable = new BigTwoTable(this);
		makeConnection();
	}
	
	/**
	 * Static method to compose a valid hand. If the provided cards can not form a valid it returns null.
	 * 
	 * @param player
	 * 		The player who has played the card.
	 * @param cards
	 * 		Cards the player has played.
	 * @return
	 * 		A hand if it is valid. Return null otherwise
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
	    if (cards != null) {
            Hand[] typesOfHands = new Hand[8];
            cards.sort();
            typesOfHands[0] = new Single(player, cards);
            typesOfHands[1] = new Pair(player, cards);
            typesOfHands[2] = new Triple(player, cards);
            typesOfHands[3] = new Straight(player, cards);
            typesOfHands[4] = new Flush(player, cards);
            typesOfHands[5] = new FullHouse(player, cards);
            typesOfHands[6] = new Quad(player, cards);
            typesOfHands[7] = new StraightFlush(player, cards);

            for (int i = 7; i >= 0; i--) {
                if (typesOfHands[i].isValid()) {
                    return typesOfHands[i];
                }
            }
            return null;
        }
        return null;
	}

	public static void main(String[] args) {
		BigTwoClient game = new BigTwoClient();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void start(Deck deck) {
	    firstMoveDone = false;
		this.deck = deck;
		handsOnTable = new ArrayList<Hand>();
		bigTwoTable.enable();
		
		//Distributing the cards in the already shuffled deck to all players equally (13 each)
		int counter = 0;
		for (int i=0; i<4; i++) {
		    this.playerList.get(i).removeAllCards();
			for (int j=counter; j<counter+13; j++) {
				this.playerList.get(i).addCard(this.deck.getCard(j));
			}
			this.playerList.get(i).sortCardsInHand();
			counter += 13;
		}
		
		//Selecting first player having the 3 of diamonds
		for (int i=0; i<4; i++) {
			for (int j=0; j<13; j++) {
				if ( this.playerList.get(i).getCardsInHand().getCard(j).getRank() == 2 && this.playerList.get(i).getCardsInHand().getCard(j).getSuit() == 0 ) {
					this.currentIdx = i;
					this.bigTwoTable.setActivePlayer(currentIdx);
				}
			}
		}
		this.bigTwoTable.printMsg(this.playerList.get(currentIdx).getName() + "'s turn:");
		this.bigTwoTable.repaint();                                             //Printing game console
	}

	@Override
    /**
     * {@inheritDoc}
     */
	public Deck getDeck() {
		return deck;
	}

	@Override
    /**
     * {@inheritDoc}
     */
	public ArrayList<CardGamePlayer> getPlayerList(){
		return playerList;
	}

	@Override
    /**
     * {@inheritDoc}
     */
	public ArrayList<Hand> getHandsOnTable(){
		return handsOnTable;
	}

	@Override
    /**
     * {@inheritDoc}
     */
	public int getCurrentIdx() {
		return currentIdx;
	}

    @Override
    /**
     * {@inheritDoc}
     */
    public boolean endOfGame() {
        if (this.playerList.get(0).getNumOfCards() == 0 || this.playerList.get(1).getNumOfCards() == 0 || this.playerList.get(2).getNumOfCards() == 0 || this.playerList.get(3).getNumOfCards() == 0){
            return true;
        }
        return false;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void checkMove(int playerID, int[] cardIdx) {
        if (!endOfGame()){
            BigTwoCard firstCard = new BigTwoCard(0,2);
            CardList cardsPlayed;
            Hand handPlayed;

            if (!firstMoveDone){
                if (cardIdx == null){
                    bigTwoTable.printMsg("{Pass}  <== Not a legal move");
                    bigTwoTable.resetSelected();
                    bigTwoTable.repaint();
                }

                else{
                    cardsPlayed = this.playerList.get(playerID).play(cardIdx);
                    handPlayed = BigTwoClient.composeHand(this.playerList.get(playerID), cardsPlayed);
                    if (cardsPlayed.contains(firstCard) && handPlayed != null){
                        this.handsOnTable.add(handPlayed);
                        this.getPlayerList().get(playerID).removeCards(cardsPlayed);
                        bigTwoTable.printMsg("{"+handPlayed.getType()+"} " + handPlayed.toString());
                        firstMoveDone = true;
                        playerID = (playerID + 1) % 4;
                        bigTwoTable.setActivePlayer(playerID);
                        bigTwoTable.resetSelected();
                        if (!endOfGame()) {
                            bigTwoTable.printMsg(playerList.get(playerID).getName() + "'s turn:");
                        }
                        bigTwoTable.repaint();
                    }

                    else{
                        bigTwoTable.printMsg("Not a legal move!");
                        bigTwoTable.resetSelected();
                        bigTwoTable.repaint();
                    }
                }
            }

            else{
                if (this.handsOnTable.get(this.handsOnTable.size()-1).getPlayer().getName() == this.playerList.get(playerID).getName()){
                    if (cardIdx == null){
                        bigTwoTable.printMsg("{Pass}  <== Not a legal move");
                        bigTwoTable.resetSelected();
                        bigTwoTable.repaint();
                    }

                    else{
                        cardsPlayed = this.playerList.get(playerID).play(cardIdx);
                        handPlayed = BigTwoClient.composeHand(this.playerList.get(playerID), cardsPlayed);
                        if (handPlayed != null){
                            this.handsOnTable.add(handPlayed);
                            this.getPlayerList().get(playerID).removeCards(cardsPlayed);
                            bigTwoTable.printMsg("{"+handPlayed.getType()+"} " + handPlayed.toString());
                            playerID = (playerID + 1) % 4;
                            bigTwoTable.setActivePlayer(playerID);
                            bigTwoTable.resetSelected();
                            if (!endOfGame()) {
                                bigTwoTable.printMsg(playerList.get(playerID).getName() + "'s turn:");
                            }
                            bigTwoTable.repaint();
                        }
                        else{
                            bigTwoTable.printMsg("Not a legal move!");
                            bigTwoTable.resetSelected();
                            bigTwoTable.repaint();
                        }
                    }
                }

                else{
                    if (cardIdx == null){
                        bigTwoTable.printMsg("{Pass}");
                        playerID = (playerID + 1) % 4;
                        bigTwoTable.setActivePlayer(playerID);
                        bigTwoTable.resetSelected();
                        if (!endOfGame()) {
                            bigTwoTable.printMsg(playerList.get(playerID).getName() + "'s turn:");
                        }
                        bigTwoTable.repaint();
                    }

                    else{
                        cardsPlayed = this.playerList.get(playerID).play(cardIdx);
                        handPlayed = BigTwoClient.composeHand(this.playerList.get(playerID), cardsPlayed);

                        if (handPlayed != null && handPlayed.beats(this.handsOnTable.get(this.handsOnTable.size()-1))){
                            this.handsOnTable.add(handPlayed);
                            this.getPlayerList().get(playerID).removeCards(cardsPlayed);
                            bigTwoTable.printMsg("{"+handPlayed.getType()+"} " + handPlayed.toString());
                            playerID = (playerID + 1) % 4;
                            bigTwoTable.setActivePlayer(playerID);
                            bigTwoTable.resetSelected();
                            if (!endOfGame()) {
                                bigTwoTable.printMsg(playerList.get(playerID).getName() + "'s turn:");
                            }
                            bigTwoTable.repaint();
                        }
                        else{
                            bigTwoTable.printMsg("Not a legal move!");
                            bigTwoTable.resetSelected();
                            bigTwoTable.repaint();
                        }
                    }
                }
            }

            if (endOfGame()){
                //currentIdx = -1;
                bigTwoTable.setActivePlayer(-1);
                bigTwoTable.resetSelected();
                bigTwoTable.repaint();
                //bigTwoTable.printMsg("Game ends");
                String dialogueBox = "";
                for (int i=0; i<4; i++) {
                    if (this.playerList.get(i).getNumOfCards() == 0) {
                        dialogueBox = dialogueBox + this.playerList.get(i).getName() + " wins the game.\n";
                    }

                    else {
                        dialogueBox = dialogueBox + this.playerList.get(i).getName() + " has " + this.playerList.get(i).getNumOfCards() + " cards in hand.\n";
                    }
                }
                bigTwoTable.disable();

                int optionClicked = JOptionPane.showOptionDialog(null, dialogueBox, "Game ends!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                if (optionClicked == JOptionPane.OK_OPTION){
                    sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
                }
                else{
                    System.exit(0);
                }
            }
        }
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void makeMove(int playerID, int[] cardIdx){
	    //checkMove(playerID, cardIdx);
        sendMessage(new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx));
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public int getPlayerID() {
        return playerID;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public String getPlayerName() {
        return playerName;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public String getServerIP() {
        return serverIP;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public int getServerPort() {
        return serverPort;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void makeConnection() {
        try{
            sock = new Socket(serverIP, serverPort);
            oos = new ObjectOutputStream(sock.getOutputStream());
            bigTwoTable.disableConnectButton();
            Thread incomingThread = new Thread(new ServerHandler());
            incomingThread.start();
            sendMessage(new CardGameMessage(CardGameMessage.JOIN, -1, playerName));
            sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void parseMessage(GameMessage message) {
        if (message.getType() == CardGameMessage.PLAYER_LIST){
            setPlayerID(message.getPlayerID());
            String[] players = (String[]) message.getData();

            for (int i=0; i<players.length; i++){
                if (players[i] != null) {
                    playerList.get(i).setName(players[i]);
                }
            }
        }

        else if (message.getType() == CardGameMessage.JOIN){
            String nameOfPlayer = (String) message.getData();
            playerList.get(message.getPlayerID()).setName(nameOfPlayer);
            bigTwoTable.repaint();
        }

        else if (message.getType() == CardGameMessage.FULL){
            bigTwoTable.printMsg("Server full. Cannnot join.");
        }

        else if (message.getType() == CardGameMessage.QUIT){
            playerList.get(message.getPlayerID()).setName("");
            String chatMsg = (String) message.getData();
            bigTwoTable.printChat(chatMsg + " left");
            bigTwoTable.disable();
            sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
        }

        else if (message.getType() == CardGameMessage.READY){
            bigTwoTable.printMsg(playerList.get(message.getPlayerID()).getName() + " is ready.");
        }

        else if (message.getType() == CardGameMessage.START){
            BigTwoDeck deckOfCards = (BigTwoDeck) message.getData();
            start(deckOfCards);
        }

        else if (message.getType() == CardGameMessage.MOVE){
            checkMove(message.getPlayerID(), (int[]) message.getData());
        }

        else if (message.getType() == CardGameMessage.MSG){
            bigTwoTable.printChat( (String) message.getData() );
        }
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void sendMessage(GameMessage message) {
        try {
            oos.writeObject(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the incoming messages from the server. Implements the Runnable class.
     */
    class ServerHandler implements Runnable{
        @Override
        /**
         * {@inheritDoc}
         */
        public void run() {
            CardGameMessage incomingMessage;
            try{
                ObjectInputStream incomingStream = new ObjectInputStream(sock.getInputStream());
                while( (incomingMessage = (CardGameMessage) incomingStream.readObject()) != null ){
                    parseMessage(incomingMessage);
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
