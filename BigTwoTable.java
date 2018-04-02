import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.awt.*;

/**
 * This class is an interface for a Big Two Card game table (GUI). It implements the CardGameTable interface class.
 *
 * @author Lakhani Amsal Murad
 */
public class BigTwoTable implements CardGameTable {
    private CardGame game;
    private boolean[] selected;
    private int activePlayer;
    private JFrame frame;
    private JPanel bigTwoPanel;
    private JButton playButton;
    private JButton passButton;
    private JTextArea msgArea;
    private JTextArea serverMsgShow;
    private JTextField serverMsgWrite;
    private Image[][] cardImages;
    private Image cardBackImage;
    private Image[] avatars;
    private BigTwoPanel gamePanel;
    private JMenuItem connectMenuItem;

    /**
     * A constructor for a BigTwoTable class. It creates and returns an instance of BigTwoTable class
     *
     * @param game
     *      A CardGame object that depicts a card game.
     */
    public BigTwoTable(CardGame game){
        this.game = game;
        selected = new boolean[13];
        cardBackImage = new ImageIcon("src/images_cards/b.gif").getImage();
        avatars = new Image[4];
        avatars[0] = new ImageIcon("src/images_avatars/batman_128.png").getImage();
        avatars[1] = new ImageIcon("src/images_avatars/flash_128.png").getImage();
        avatars[2] = new ImageIcon("src/images_avatars/green_lantern_128.png").getImage();
        avatars[3] = new ImageIcon("src/images_avatars/superman_128.png").getImage();

        cardImages = new Image[4][13];

        String dir = "src/images_cards/";

        for (int i=0; i<4; i++){
            String suit = "d";

            if (i == 1){
                suit = "c";
            }

            else if (i == 2){
                suit = "h";
            }

            else if (i == 3){
                suit = "s";
            }

            for (int j=0; j<13; j++){
                int rank = j+1;
                cardImages[i][j] = new ImageIcon(dir + rank + suit + ".gif").getImage();
            }
        }

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Big Two Card");

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        connectMenuItem = new JMenuItem("Connect");
        connectMenuItem.addActionListener(new ConnectMenuItemListener());
        JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(new QuitMenuItemListener());
        menu.add(connectMenuItem);
        menu.add(quitMenuItem);
        menuBar.add(menu);

        playButton = new JButton("Play");
        playButton.addActionListener(new PlayButtonListener());
        passButton = new JButton("Pass");
        passButton.addActionListener(new PassButtonListener());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(playButton);
        buttonsPanel.add(passButton);

        bigTwoPanel = new JPanel();
        bigTwoPanel.setLayout(new BorderLayout());
        bigTwoPanel.setPreferredSize(new Dimension(800, 900));
        bigTwoPanel.setBackground(new Color(10, 108, 3));

        msgArea = new JTextArea();
        JScrollPane msgAreaScrollable = new JScrollPane(msgArea);
        msgAreaScrollable.setPreferredSize(new Dimension(400, 500));
        serverMsgShow = new JTextArea();
        JScrollPane msgShowScrollable = new JScrollPane(serverMsgShow);
        serverMsgWrite = new JTextField();
        serverMsgWrite.addActionListener( new WriteMsgListener());
        serverMsgWrite.setPreferredSize(new Dimension(320, 25));
        JLabel serverMsgWriteLabel = new JLabel("Message:");
        JPanel serverMsgWritePanel = new JPanel();
        serverMsgWritePanel.setPreferredSize(new Dimension(400, 35));
        serverMsgWritePanel.add(serverMsgWriteLabel);
        serverMsgWritePanel.add(serverMsgWrite);
        JPanel msgsPanel = new JPanel();
        msgsPanel.setLayout(new BorderLayout());
        msgsPanel.add(msgAreaScrollable, BorderLayout.NORTH);
        msgsPanel.add(msgShowScrollable, BorderLayout.CENTER);
        msgsPanel.add(serverMsgWritePanel, BorderLayout.SOUTH);

        gamePanel = new BigTwoPanel();

        bigTwoPanel.add(menuBar, BorderLayout.NORTH);
        bigTwoPanel.add(gamePanel, BorderLayout.CENTER);
        bigTwoPanel.add(buttonsPanel, BorderLayout.SOUTH);
        //frame.add(menuBar, BorderLayout.NORTH);
        frame.add(bigTwoPanel, BorderLayout.WEST);
        frame.add(msgsPanel, BorderLayout.CENTER);

        frame.setSize(1200, 900);
        frame.setVisible(true);
    }

    /**
     * Sets the index of the active player (i.e., the current player).
     *
     * @param activePlayer
     *            an int value representing the index of the active player
     */
    public void setActivePlayer(int activePlayer){
        this.activePlayer = activePlayer;
    }

    /**
     * Returns an array of indices of the cards selected.
     *
     * @return an array of indices of the cards selected
     */
    public int[] getSelected(){
        int[] cardsSelected = null;
        int count = 0;
        for (int i=0; i<selected.length; i++){
            if (selected[i]){
                count++;
            }
        }

        if (count != 0){
            cardsSelected = new int[count];
            count = 0;
            for (int i=0; i<selected.length; i++){
                if (selected[i]){
                    cardsSelected[count] = i;
                    count++;
                }
            }
        }

        return cardsSelected;
    }

    /**
     * Resets the list of selected cards to an empty list.
     */
    public void resetSelected(){
        this.selected = new boolean[13];
    }

    /**
     * Repaints the GUI.
     */
    public void repaint(){
        bigTwoPanel.revalidate();
        bigTwoPanel.repaint();
        frame.setVisible(true);
    }

    /**
     * Prints the specified string to the message area of the card game table.
     *
     * @param msg
     *            the string to be printed to the message area of the card game
     *            table
     */
    public void printMsg(String msg){
        msgArea.append(msg + "\n");
        msgArea.setCaretPosition(msgArea.getText().length());
    }

    /**
     * Prints the specified string to the chat area of the card game table.
     *
     * @param msg
     *          the string to be printed to the chat area of the card game
     *          table
     */
    public void printChat(String msg){
        serverMsgShow.append(msg + "\n");
    }

    /**
     * Clears the message area of the card game table.
     */
    public void clearMsgArea(){
        msgArea.setText("");
    }

    /**
     * Resets the GUI.
     */
    public void reset(){
        resetSelected();
        clearMsgArea();
        enable();
    }

    /**
     * Disables the connect button in the menu bar.
     */
    public void disableConnectButton(){
        connectMenuItem.setEnabled(false);
    }

    /**
     * Enables user interactions.
     */
    public void enable(){
        playButton.setEnabled(true);
        passButton.setEnabled(true);
        gamePanel.removeListener();
        gamePanel.addListener();
        bigTwoPanel.setEnabled(true);
    }

    /**
     * Disables user interactions.
     */
    public void disable(){
        playButton.setEnabled(false);
        passButton.setEnabled(false);
        gamePanel.removeListener();
        bigTwoPanel.setEnabled(false);
    }

    /**
     * An inner class of the BigTwoTable class that is used to paint the table with cards, avatars, player name, cards on table and the name of the player who has played the last hand on the table. It also handles and mouse click interactions. It is a subclass of JPanel and implements MouseListener interface.
     */
    class BigTwoPanel extends JPanel implements MouseListener{
        /**
         * Creates and returns an instance of the BigTwoPanel class.
         */
        public BigTwoPanel(){
            this.addMouseListener(this);
        }

        /**
         * Adds the mouse listener to the instance to handle user interaction with the panel.
         */
        public void removeListener(){
            this.removeMouseListener(this);
        }

        /**
         * Removes the mouse listener to the instance to stop user interaction with the panel.
         */
        public void addListener(){
            this.addMouseListener(this);
        }

        /**
         * Paints on the panel the avatars, cards, player name, cards on table and the name of the player who has played the last hand on the table.
         *
         * @param g
         *      A Graphics object used to paint the table.
         */
        public void paintComponent(Graphics g){
            int x = 180;
            int y = 10;

            for (int i=0; i < game.getPlayerList().size(); i++) {
                g.drawString(game.getPlayerList().get(i).getName(), 3, y+15);
                if ( ( (BigTwoClient) game).getPlayerID() == i) {
                    g.drawImage(avatars[i], 3, y+20, this);
                    for (int j=0; j < game.getPlayerList().get(i).getCardsInHand().size(); j++){
                        x += 30;
                        if (selected[j]){
                            g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()][game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()], x, y+20, this);
                        }
                        else {
                            g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()][game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()], x, y + 40, this);
                        }
                    }
                }

                else if (activePlayer == -1){
                    g.drawImage(avatars[i], 3, y+20, this);
                    for (int j=0; j < game.getPlayerList().get(i).getCardsInHand().size(); j++) {
                        x += 30;
                        g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()][game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()], x, y + 40, this);
                    }
                }

                else {
                    g.drawImage(avatars[i], 3, y+20, this);
                    for (int j = 0; j < game.getPlayerList().get(i).getCardsInHand().size(); j++) {
                        x += 30;
                        g.drawImage(cardBackImage, x, y + 40, this);
                    }
                }
                x = 180;
                y += 155;
                g.drawLine(0, y, 5000, y);
            }

            x = 0;
            Hand lastHandOnTable = game.getHandsOnTable().isEmpty() ? null : game.getHandsOnTable().get(game.getHandsOnTable().size() - 1);
            if (lastHandOnTable != null){
                String lastPlayer = "Played by " + lastHandOnTable.getPlayer().getName();
                g.drawString(lastPlayer, 3, y+15);
                for (int i=0; i < lastHandOnTable.size(); i++){
                    x += 30;
                    g.drawImage(cardImages[lastHandOnTable.getCard(i).getSuit()][lastHandOnTable.getCard(i).getRank()], x, y+40, this);
                }
            }
        }

        @Override
        /**
         * {@inheritDoc}
         */
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            if ( (y > 50 && y < 147) && (x > 210 && x < (210 + (game.getPlayerList().get(0).getCardsInHand().size() * 30) + 43)) && ( ((BigTwoClient) game).getPlayerID()  == 0) ){
                if ( x > (210 + ( (game.getPlayerList().get(0).getCardsInHand().size() - 1) * 30) ) ){
                    if ( selected[game.getPlayerList().get(0).getCardsInHand().size() - 1] ){
                        selected[game.getPlayerList().get(0).getCardsInHand().size() - 1] = false;
                    }
                    else{
                        selected[game.getPlayerList().get(0).getCardsInHand().size() - 1] = true;
                    }
                }

                else{
                    x = x - 210;
                    x = x/30;
                    if (selected[x]){
                        selected[x] = false;
                    }
                    else{
                        selected[x] = true;
                    }
                }
                BigTwoTable.this.repaint();
            }

            if ( (y > 205 && y < 302) && (x > 210 && x < (210 + (game.getPlayerList().get(1).getCardsInHand().size() * 30) + 43)) && ( ((BigTwoClient) game).getPlayerID() == 1) ){
                if ( x > (210 + ( (game.getPlayerList().get(1).getCardsInHand().size() - 1) * 30) ) ){
                    if ( selected[game.getPlayerList().get(1).getCardsInHand().size() - 1] ){
                        selected[game.getPlayerList().get(1).getCardsInHand().size() - 1] = false;
                    }
                    else{
                        selected[game.getPlayerList().get(1).getCardsInHand().size() - 1] = true;
                    }
                }

                else{
                    x = x - 210;
                    x = x/30;
                    if (selected[x]){
                        selected[x] = false;
                    }
                    else{
                        selected[x] = true;
                    }
                }
                BigTwoTable.this.repaint();
            }

            if ( (y > 360 && y < 457) && (x > 210 && x < (210 + (game.getPlayerList().get(2).getCardsInHand().size() * 30) + 43)) && ( ((BigTwoClient) game).getPlayerID() == 2) ){
                if ( x > (210 + ( (game.getPlayerList().get(2).getCardsInHand().size() - 1) * 30) ) ){
                    if ( selected[game.getPlayerList().get(2).getCardsInHand().size() - 1] ){
                        selected[game.getPlayerList().get(2).getCardsInHand().size() - 1] = false;
                    }
                    else{
                        selected[game.getPlayerList().get(2).getCardsInHand().size() - 1] = true;
                    }
                }

                else{
                    x = x - 210;
                    x = x/30;
                    if (selected[x]){
                        selected[x] = false;
                    }
                    else{
                        selected[x] = true;
                    }
                }
                BigTwoTable.this.repaint();
            }

            if ( (y > 515 && y < 612) && (x > 210 && x < (210 + (game.getPlayerList().get(3).getCardsInHand().size() * 30) + 43)) && ( ((BigTwoClient) game).getPlayerID() == 3) ){
                if ( x > (210 + ( (game.getPlayerList().get(3).getCardsInHand().size() - 1) * 30) ) ){
                    if ( selected[game.getPlayerList().get(3).getCardsInHand().size() - 1] ){
                        selected[game.getPlayerList().get(3).getCardsInHand().size() - 1] = false;
                    }
                    else{
                        selected[game.getPlayerList().get(3).getCardsInHand().size() - 1] = true;
                    }
                }

                else{
                    x = x - 210;
                    x = x/30;
                    if (selected[x]){
                        selected[x] = false;
                    }
                    else{
                        selected[x] = true;
                    }
                }
                BigTwoTable.this.repaint();
            }
        }

        @Override
        /**
         * {@inheritDoc}
         */
        public void mousePressed(MouseEvent e) {

        }

        @Override
        /**
         * {@inheritDoc}
         */
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        /**
         * {@inheritDoc}
         */
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        /**
         * {@inheritDoc}
         */
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Handles the play button clicks in the GUI.
     */
    class PlayButtonListener implements ActionListener{
        @Override
        /**
         * {@inheritDoc}
         */
        public void actionPerformed(ActionEvent e) {
            if (activePlayer == ((BigTwoClient) game).getPlayerID()) {
                if (BigTwoTable.this.getSelected() != null) {
                    game.makeMove(activePlayer, BigTwoTable.this.getSelected());
                }
            }
        }
    }

    /**
     * Handles the pass button clicks in the GUI.
     */
    class PassButtonListener implements ActionListener{
        @Override
        /**
         * {@inheritDoc}
         */
        public void actionPerformed(ActionEvent e) {
            if (activePlayer == ((BigTwoClient) game).getPlayerID()) {
                game.makeMove(activePlayer, null);
            }
        }
    }

    /**
     * Handles the "Options" menu item Connect clicks.
     */
    class ConnectMenuItemListener implements ActionListener{
        @Override
        /**
         * {@inheritDoc}
         */
        public void actionPerformed(ActionEvent e) {
            ((BigTwoClient) game).makeConnection();
        }
    }

    /**
     * Handles the "Options" menu item Quit clicks.
     */
    class QuitMenuItemListener implements ActionListener{
        @Override
        /**
         * {@inheritDoc}
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * Handles the enter key when hit after typing message.
     */
    class WriteMsgListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ( (BigTwoClient) game).sendMessage(new CardGameMessage(CardGameMessage.MSG, -1, serverMsgWrite.getText()));
            serverMsgWrite.setText("");
        }
    }
}
