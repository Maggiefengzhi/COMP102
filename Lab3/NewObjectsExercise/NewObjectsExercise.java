// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, LabExercise 3
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.awt.Color;

/**
 * The NewObjectsExercise class has several simple methods
 * that involve creating new objects and calling methods on those objects.
 * - growFlowers() should create two or three Flower objects and
 * make them grow and bloom
 * - blowUpBalloons() should create two Balloon objects and
 * make them expand and pop
 * - driveCars() should create two Car objects and make them
 * drive towards each other then turn to the side and drive some more
 */

public class NewObjectsExercise {

    /**
     * Creates two or three Flower objects and makes them grow and bloom
     */
    public void growFlowers(){
        /*# YOUR CODE HERE */

    }

    /**
     * Creates two Balloon objects and makes them expand several times and pop
     */
    public void blowUpBalloons(){
        /*# YOUR CODE HERE */

    }

    /** 
     * Creates two Car objects and makes them drive towards
     *  each other then turn to the side and drive some more.
     */
    public void driveCars(){
        /*# YOUR CODE HERE */

    }

    /**
     * Set up the Gui with buttons
     */
    public void setupGUI(){
        UI.initialise();
        UI.addButton("Clear", UI::clearGraphics );
        UI.addButton("Flowers", this::growFlowers );
        UI.addButton("Balloons", this::blowUpBalloons );
        UI.addButton("Cars", this::driveCars );
        UI.addButton("Quit", UI::quit );
        UI.setDivider(0);
    }

    /**
     * main: construct object and call setup GUI.
     */
    public static void main(String[] args){
        NewObjectsExercise noe = new NewObjectsExercise();
        noe.setupGUI();
    }
    
    public static void run() {
        main(null);
    }
}
