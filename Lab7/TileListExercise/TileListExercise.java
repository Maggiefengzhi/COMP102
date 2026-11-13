// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, LabExercise 7
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/**
 * Creates an ArrayList of Scrabble tiles.
 * Uses the Tile class.
 * User can add a tile to the list, reset the list, and shuffle the list
 * The user can also ask the program to search for pairs of
 * adjacent tiles that have the same letter and remove both tiles.
 */
public class TileListExercise{

    private ArrayList<Tile> tiles = new ArrayList<Tile>();

    /** 
     * Reset the tiles field so that it is an empty list
     * Redraw the tiles.
     */
    public void reset(){
        /*# YOUR CODE HERE */

        this.redraw();
    }

    /** 
     * Add a new tile to the end of the list.
     * Note: the Tile constructor needs no arguments, and creates a random tile.
     */
    public void add(){
        /*# YOUR CODE HERE */

        this.redraw();
    }

    /** 
     * Checks for pairs of adjacent tiles that have the same letter
     * If it finds a pair, it removes one of them from the list,
     * and reports the letter removed
     * Redraw the tiles.
     */
    public void checkTiles(){
        /*# YOUR CODE HERE */

        this.redraw();
    }

    /** 
     * Shuffle the order of the tiles.
     */
    public void shuffle(){
        /*# YOUR CODE HERE */

        this.redraw();
    }

    //------------------------------------------------
    //   Methods for selecting and drawing the tiles
    //   (COMPLETED FOR YOU)
    //------------------------------------------------

    // Constants for drawing.
    public static final double LEFT = 20;
    public static final double TOP = 100;
    public static final double SIZE = 40;

    /**
     * Draws all the tiles on the table
     */
    public void redraw(){
        UI.clearGraphics();
        for (int i=0; i<this.tiles.size(); i++){
            int row = i/10;
            int col = i%10;
            this.tiles.get(i).draw(LEFT + col*(SIZE+1), TOP + row*(SIZE+1));
        }
    }

    /**
     * Setup GUI with buttons
     */
    public void setupGUI(){
        UI.addButton("Reset", this::reset);
        UI.addButton("Add", this::add);
        UI.addButton("Shuffle", this::shuffle);
        UI.addButton("Check", this::checkTiles);
        UI.addButton("Quit", UI::quit);
        UI.printMessage("Start by adding tiles");
    }    

    /**
     * Create object and call setupGUI 
     */
    public static void main(String[] arguments){
        new TileListExercise().setupGUI();
    }    

    public static void run() {
        main(null);
    }

}
