// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, Assignment 7
 * Name: Fengzhi Zhang
 * Username: zhangfeng1
 * ID: 300667389
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;


/**
 * This program allows a player to play a two-player card game based on "Battle".
 * 
 * The player builds a hand of up to 5 cards. During the game, the player can:
 * - Place the leftmost card onto the table
 * - Draw cards from the deck to refill their hand
 * - Swap a card in their hand with one from the deck
 * - Reorder the cards in their hand
 * 
 * In each round:
 * - The player places their leftmost card on the table
 * - The computer places the top card from the deck
 * - The higher card wins the round
 * - If cards are equal in value, the round is a draw
 * 
 * The first to win 7 rounds wins the game.
 * 
 * See the Assignment page for details on program design.
 */

public class TrickGame{

    // Constants for the game
    public static final int NUM_HAND = 5;      // Number of cards in hand
    public static final int TARGET_SCORE = 7;  // Number of rounds to win the game
    public static final int NUM_REPLACE = 3;   // Number of cards the player is allowed to replace per game

    // Fields for the game: deck, hand, and table
    private ArrayList<Card> deck = Card.getShuffledDeck();          // the deck (a list of Cards)
    private Card[] hand = new Card[NUM_HAND];                       // the hand (fixed size array of Cards)
    private ArrayList<Card> tableComputer = new ArrayList<Card>();  // the list of Cards that the computer has played
    private ArrayList<Card> tablePlayer = new ArrayList<Card>();    // the list of Cards that the player has played

    private int selectedPos = 0;      // selected position in the hand.
    private int compScore = 0;        // the number of points scored by the computer player
    private int playScore = 0;        // the number of points scored by the user player
    private int remainingReplaces = NUM_REPLACE;  // 

    // Constants for the layout
    public static final Color RACK_COLOR = new Color(122,61,0);

    public static final int HAND_LEFT = 60;      // x-position of the leftmost Card in the hand
    public static final int HAND_TOP = 500;      // y-Position of all the Cards in the hand 
    public static final int CARD_SPACING = 80;   // spacing is the distance from left side of a card
    // to left side of the next card in the hand
    public static final int CARD_OVERLAP = 15;   // overlap is the distance from left side of a card
    // to left side of the next card on the table
    public static final int CARD_HEIGHT = 110; 

    public static final int TABLE_LEFT = 10;                
    public static final int TABLE_TOP_COMPUTER = 80;
    public static final int TABLE_TOP_PLAYER   = TABLE_TOP_COMPUTER+CARD_HEIGHT+10;

    public static final int SCORES_TOP = 20;

    /**
     * CORE
     * 
     * Restarts the game by:
     *  - getting a new shuffled deck,
     *  - setting compScore, playScore, and remainingReplaces to their initial values
     *  - setting the table to be empty,
     *  - refilling the player's hand from the deck
     */
    public void restart(){
        deck = Card.getShuffledDeck();
        this.playScore = 0;
        this.compScore = 0;
        this.remainingReplaces = NUM_REPLACE;
        
        for (int i = 0; i < NUM_HAND; i++){
            Card card = deck.remove(0); 
            hand[i]= card;
        }

        this.tableComputer = new ArrayList<Card>();
        this.tablePlayer = new ArrayList<Card>();    
        this.redraw();
    }

    /**
     * CORE
     * 
     * If the deck is not empty and the hand has at least one empty slot,
     * picks up the top card from the deck and places it in the first empty position in the hand.
     * (needs to search along the array for an empty position.)
     */
    public void pickupCard(){
        boolean hasEmpty = false;
        int iEmpty = 0;
        for (int i = 0; i < NUM_HAND; i++){
            if (hand[i] == null){
                 iEmpty = i;
                 hasEmpty = true;
                 break;
            }
        
        }
        
        if(deck.size() > 0 && hasEmpty){
            hand[iEmpty] = deck.remove(0);
        }

        this.redraw();
    }

    /**
     * CORE
     * 
     * Draws all the Cards in the hand,
     *  This MUST use the constants:  (in order to make the selection work!)
     *   - CARD_SPACING, HAND_LEFT, HAND_TOP
     *   See the descriptions where these fields are defined.
     */
    public void drawHandCards(){
        //draw the cards.
        for (int i = 0; i < NUM_HAND; i++){
            if(hand[i] != null){
                hand[i].draw(HAND_LEFT + CARD_SPACING * i, HAND_TOP);

            }
        }
    }


    /**
     * CORE
     * Draws all the Cards in both the computer and player tables in two rows.
     *   See the descriptions of TABLE_LEFT, TABLE_TOP_COMPUTER, TABLE_TOP_PLAYER and CARD_OVERLAP.
     *
     * COMPLETION:
     * - Outlines the card with the highest rank from the previous round.
     * 
     */
    public void drawTableCards(){
        //draw the cards in the tableComputer and tablePlayer lists.
        for (int i = 0; i < tablePlayer.size(); i++){
            tableComputer.get(i).draw(TABLE_LEFT + CARD_OVERLAP * i, TABLE_TOP_COMPUTER);
            tablePlayer.get(i).draw(TABLE_LEFT + CARD_OVERLAP * i, TABLE_TOP_PLAYER);
        }
            UI.setColor(Color.red); 
            int last = tablePlayer.size()-1;
            if (last >= 0){
                if(tableComputer.get(last).getRank() > tablePlayer.get(last).getRank()){
                    UI.drawRect(TABLE_LEFT + CARD_OVERLAP * last, TABLE_TOP_COMPUTER, CARD_SPACING, CARD_HEIGHT);
                }else if(tableComputer.get(last).getRank() < tablePlayer.get(last).getRank()){
                    UI.drawRect(TABLE_LEFT + CARD_OVERLAP * last, TABLE_TOP_PLAYER, CARD_SPACING, CARD_HEIGHT);
                }
            
        }
    }

    /**
     * CORE
     *
     * If there is a card in the leftmost position of the hand:
     * - Places it on the table
     * - Picks up the top card from the deck for the computer and places it on the table
     * - Compares the ranks of the two cards and awards a point to the winner
     * - Redraws the table and hand
     * - Ends the game if either player has reached the target score
     */
    public void playRound(){
        if(hand[0] != null){
            
            if(hand[0] != null){
                Card playerCard = hand[0];
                tablePlayer.add(playerCard); 
                hand[0] = null;
                
                Card compCard = deck.remove(0);
                tableComputer.add(compCard);
                
                if (compCard.getRank() > playerCard.getRank()){
                    compScore ++;
                }else if(compCard.getRank() < playerCard.getRank()){
                    playScore ++;
                }
            
                redraw();
                
                if (playScore == TARGET_SCORE || compScore == TARGET_SCORE){
                endGame();}
            }
        }
    }

    /**
     * COMPLETION
     *
     * If there is a card at the selected position in the hand,
     * replaces it with the top card from the deck.
     * Decreases the number of remaining replacements.
     */
    public void replaceCard(){
        if (remainingReplaces > 0 ){
            if (hand[selectedPos] != null){
                hand[selectedPos] = deck.remove(0);
                remainingReplaces--;
            }
        }
        this.redraw();
    }

    /**
     * COMPLETION
     *
     * Swaps the selected card with the one to its left, if possible.
     * Updates the value of the selected position to reflect the
     * cards new index after the swap.
     */
    public void moveLeft(){
        if (hand[selectedPos] != null && selectedPos > 0){
            Card temp = hand[selectedPos-1];
            hand[selectedPos-1] = hand[selectedPos];
            hand[selectedPos] = temp;
            selectedPos--;
        }

        this.redraw();
    }

    /** ---------- The code below is already written for you ---------- **/

    /** 
     * Allows the user select a card in their hand using the mouse.
     * If the mouse is released over a card, sets selectedPos to that card's index.
     * Then, redraws the hand and table 
     */
    public void doMouse(String action, double x, double y){
        if (action.equals("released")){
            if (y >= HAND_TOP && y <= HAND_TOP+CARD_HEIGHT && 
            x >= HAND_LEFT && x <= HAND_LEFT + NUM_HAND*CARD_SPACING) {
                this.selectedPos = (int) ((x-HAND_LEFT)/CARD_SPACING);
                //UI.clearText();UI.println("selected "+this.selectedPos);
                this.redraw();
            }
        }
    }

    /**
     * Displays a win/lose message
     */
    public void endGame(){
        UI.setFontSize(40);
        UI.setColor(Color.red);
        if (this.playScore > this.compScore){
            UI.drawString("YOU WIN!!!", 500, HAND_TOP-80);
        }
        else{
            UI.drawString("YOU LOSE", 500, HAND_TOP-80);
        }
        UI.sleep(3000);
        this.restart();
    }

    /**
     *  Redraw the table and the hand.
     */
    public void redraw(){
        UI.clearGraphics();
        UI.setFontSize(20);
        UI.setColor(Color.black);
        UI.drawString("Player: " + playScore + " Computer: " + compScore, TABLE_LEFT+150, SCORES_TOP);
        UI.drawString("Remaining replaces: " + remainingReplaces, TABLE_LEFT+600, SCORES_TOP);

        // outline the hand and the selected position
        UI.setLineWidth(2);
        UI.setColor(Color.black);
        UI.drawRect(HAND_LEFT-4, HAND_TOP-4, (CARD_SPACING)*NUM_HAND+4, CARD_HEIGHT+8);

        UI.setColor(Color.green);
        int selLeft = HAND_LEFT + (this.selectedPos * (CARD_SPACING)) - 2;
        UI.drawRect(selLeft, HAND_TOP - 2, CARD_SPACING, CARD_HEIGHT+4);

        // draw the rack top
        UI.setColor(RACK_COLOR);
        UI.fillRect(HAND_LEFT-10, HAND_TOP-28, (CARD_SPACING)*NUM_HAND+20, 20);

        this.drawHandCards();
        this.drawTableCards();
    }

    /**
     * Set up the user interface
     */
    public void setupGUI(){
        UI.setMouseListener( this::doMouse );
        UI.addButton("Round", this::playRound);
        UI.addButton("Pickup", this::pickupCard);
        UI.addButton("Replace",  this::replaceCard); 
        UI.addButton("Left", this::moveLeft);
        UI.addButton("Restart", this::restart);
        UI.addButton("Quit", UI::quit);
        UI.setWindowSize(1100,650);
        UI.setDivider(0.0);

    }

    public static void main(String[] args){
        TrickGame tg = new TrickGame();
        tg.setupGUI();
        tg.restart();
    }   

    public static void run() {
        main(null);
    }
}
