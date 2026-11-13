// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, Assignment 2
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;
import java.nio.file.*;


/**
 * TableLayout draw a plan for tables in a restaurant.
 * It draws the outline of the room,
 * Then draws a number of tables placed around the room
 * Each table is a round circle with four chairs around it.
 * The chairs can be as simple as a square that is half visible
 */

public class TableLayout{

    public static final double T_DIAM = 50;  // diameter of the tables.

     /**
     * Draw a square room with 4 tables,
     * one towards each corner
     */
    public void drawRoom1(){
        /*# YOUR CODE HERE */

    }

    /**
     * Draw a long rectangular room with 7 tables in a zig zag
     */
    public void drawRoom2(){
        /*# YOUR CODE HERE */
        
    }


    /**
    * Setup the GUI with buttons
    */
    public void setupGUI(){
        UI.addButton("Clear", UI::clearGraphics);
        UI.addButton("Draw Room 1", this::drawRoom1);
        UI.addButton("Draw Room 2", this::drawRoom2);
        UI.addButton("Quit", UI::quit);
    }


    /**
     * Make object and call setupGUI on it
     */
    public static void main(String[] arguments){
        new TableLayout().setupGUI();
    }

    public static void run() {
        main(null);
    }
}
