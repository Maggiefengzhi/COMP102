// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, Assignment 2
 * Name: Fengzhi Zhang
 * Username: zhangfeng1
 * ID: 300667389
 */

import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;

import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;

/** CORE: draw three-striped flags */
public class ThreeStripeFlagDrawer{

    //Constants for CORE
    public static final double FLAG_WIDTH = 200;
    public static final double FLAG_HEIGHT = 133;

    /**
     * Asks user for a position and three colours, then calls the
     * drawThreeStripeFlag method, passing the appropriate arguments
     */
    public void drawFlags(){
        double left = UI.askDouble("Left of flag");
        double top = UI.askDouble("Top of flag");
        UI.println("Choose the colours");
        Color stripe1 = JColorChooser.showDialog(null, "First Stripe", Color.white);
        Color stripe2 = JColorChooser.showDialog(null, "Second Stripe", Color.white);
        Color stripe3 = JColorChooser.showDialog(null, "Third Stripe", Color.white);
        this.drawThreeStripeFlag(left, top, stripe1, stripe2, stripe3);
    }

    /**   CORE
     * draws a three colour flag at the given position consisting of
     * three equal size stripes of the given colors
     * The stripes are horizontal
     * The size of the flag is specified by the constants FLAG_WIDTH and FLAG_HEIGHT
     */
    public void drawThreeStripeFlag(
        double left ,double top ,Color stripe1 ,Color stripe2 ,Color stripe3
        ){
        double stripeHeight = FLAG_HEIGHT / 3.0;
        double stripeWidth = FLAG_WIDTH;
        
        UI.setColor(stripe1);
        UI.fillRect(left, top, stripeWidth, stripeHeight);
        
        UI.setColor(stripe2);
        UI.fillRect(left, top + stripeHeight, stripeWidth, stripeHeight);
        
        UI.setColor(stripe3);
        UI.fillRect(left, top + 2 * stripeHeight, stripeWidth, stripeHeight);
    }

    /**
     * Setup the GUI with buttons
     */
    public void setupGUI(){
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("Flags", this::drawFlags );
        UI.addButton("Quit", UI::quit );
    }

    /**
     * Create object and call setupGUI on it
     */ 
    public static void main(String[] args){
        ThreeStripeFlagDrawer tsf = new ThreeStripeFlagDrawer ();
        tsf.setupGUI();
    }

    public static void run() {
        main(null);
    }
}
