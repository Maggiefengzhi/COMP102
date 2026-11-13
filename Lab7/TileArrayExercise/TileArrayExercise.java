// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, LabExercise 7
 * Name:Fengzhi Zhang
 * Username:zhangfeng1
 * ID:300667389
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/**
 * Creates a rack of 7 Scrabble tiles.
 * Uses the Tile class.
 * User can select a tile using the mouse (written for you)
 * then shift it in the array one step to the left or to the right
 * or remove it from the array.
 * After every change to the array, it is necessary to call the redraw method.
 */
public class TileArrayExercise{

    public static final int NUM_TILES = 7;
    private Tile[] tiles = new Tile[NUM_TILES];
    private int selectedIndex = 0;        // The index of the selected tile.
    
 
    /** 
     * Fill the array in the tiles field with new Tiles
     * Redraws the tiles.
     */
    public void reset(){
       
            for(int i =0; i < NUM_TILES; i++){
                
                tiles[i] = new Tile();
            }

        this.redraw();
    }

    /** 
     * Move the selected tile one position to the left
     *  (as long as it isn't the leftmost tile)
     * Redraws the tiles.
     */
    public void left(){
        tiles[selectedIndex]= tiles[selectedIndex+1];
        tiles[selectedIndex+1]=null;
        
        this.redraw();
    }

    /** 
     * Move the selected tile one position to the right
     *  (as long as it isn't the rightmost tile)
     * Redraw the tiles.
     */
    public void right(){
        tiles[selectedIndex]= tiles[selectedIndex-1];
        tiles[selectedIndex-1]=null;
        
        this.redraw();
    }

    /**
     * Remove the currently selected tile, leaving a null in its place
     * Redraw the tiles.
     */
    public void remove(){
        tiles[selectedIndex]=null;
        

        this.redraw();
    }

    //------------------------------------------------
    //   Methods for selecting and drawing the tiles
    //   (COMPLETED FOR YOU)
    //------------------------------------------------
    // Constants for the drawing
    public static final double LEFT = 20;
    public static final double TOP = 100;
    public static final double SIZE = 40;

    /**
     * If mouse is clicked on the rack, works out which tile
     * and stores the index of the selected tile
     */
    public void doMouse(String action, double x, double y){
        if ( action.equals("released") &&
        y >= TOP && y <= TOP+SIZE &&
        x >= LEFT && x <= LEFT+NUM_TILES*(SIZE+1) ){
            this.selectedIndex = (int)((x-LEFT)/(SIZE+1));
            this.redraw();
        }
    }

    /**
     * Draws all the tiles in a rack.
     * highlights the currently selected tile.
     */
    public void redraw(){
        UI.eraseRect(LEFT-2, TOP-2, NUM_TILES*(SIZE+1)+3, SIZE+3);
        UI.setColor(Color.black);
        UI.drawRect(LEFT-2, TOP-2, NUM_TILES*(SIZE+1)+3, SIZE+3);
        UI.setColor(Color.red);
        UI.drawRect(LEFT+this.selectedIndex*(SIZE+1)-1, TOP-1, SIZE+1, SIZE+1);
        
        for (int i=0; i<this.tiles.length; i++){
            if (this.tiles[i]!=null){
                this.tiles[i].draw(LEFT + i*(SIZE+1), TOP);
            }
        }
    }

    /**
     * Setup the GUI with buttons
     */
    public void setupGUI(){
        UI.setMouseListener(this::doMouse);
        UI.addButton("Reset Tiles", this::reset);
        UI.addButton("Move Left", this::left);
        UI.addButton("Move Right", this::right);
        UI.addButton("Remove", this::remove);
        UI.addButton("Quit", UI::quit);
    }    

    /**
     * Create object and call setupGUI 
     */
    public static void main(String[] arguments){
        TileArrayExercise tae = new TileArrayExercise();
        tae.setupGUI();
        tae.redraw();
    }    

    public static void run() {
        main(null);
    }
    
}
