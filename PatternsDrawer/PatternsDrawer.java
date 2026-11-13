// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, Assignment 4
 * Name: Fengzhi Zhang
 * Username: zhangfeng1
 * ID: 300667389
 */

import ecs100.*;
import java.awt.Color;

/** PatternsDrawer: 
 * Draws four different repetitive patterns. */

public class PatternsDrawer{

    public static final double PATTERN_LEFT = 50.0;   // Left side of the pattern
    public static final double PATTERN_TOP = 50.0;    // Top of the pattern
    public static final double PATTERN_SIZE = 300.0;  // The size of the pattern on the window

    /** 
     * Draws a star pattern consisting of a circle containing black rays 
     * separated by white regions.
     * Asks the user for the number of rays, and calculates the angle of the rays.
     * Hint: use filled arcs to draw the rays,
     */
    public void drawStar(){
        UI.clearGraphics();
        UI.setColor(Color.black);
        double num = UI.askInt("How many rays:");
        double arcAngle = 360.0 / (num * 2);
        
        for (int i = 0; i < num; i++ ){
        double startAngle = i * 2 * arcAngle;
        UI.fillArc(PATTERN_LEFT, PATTERN_TOP, PATTERN_SIZE, PATTERN_SIZE, startAngle, arcAngle);
        
        }
        
    }

    /** 
     * Draws a triangular grid of equally sized squares that creates the
     * optical illusion of dark circles appearing at the intersections. 
     * The gap between the squares should be 1/3 the size of the squares.
     * It asks the user for the number of rows. 
     * The top row should have one square; the next row should have two squares;
     * and so on. The last row will have as many squares as there are rows.
     * Hint: requires a nested loop
     */
    public void drawTriGrid(){
        UI.clearGraphics();
        UI.setColor(Color.black);
        int num = UI.askInt("How many rows:");
        
        double squareLeft = PATTERN_LEFT;
        double squareTop = PATTERN_TOP;
        double squareWidth = (3.0 * PATTERN_SIZE) / (4 * num - 1);
        double gap = squareWidth / 3.0;
        
        for (int row = 0; row < num; row++){
        
            for(int col = 0; col <= row; col++){
               squareLeft = PATTERN_LEFT + (squareWidth + gap) * col;
               squareTop = PATTERN_TOP + (squareWidth + gap) * row;
               
               UI.fillRect(squareLeft, squareTop, squareWidth, squareWidth);
            
            }
            
        }

    }

    /**
     * Draws a rectangle containing a row of randomly sized blue circles
     * that fit within the rectangle.
     * The width of the rectangle is PATTERN_SIZE.
     * The method asks user for the height of the rectangle, which must be
     * less than the width: the method should keep asking for a height until
     * the user gives a height less than the width.
     * It then draws the rectangle and fills it from left to right with
     * randomly sized blue circles (each with a diameter between 3 units and
     * the specified height)
     *
     * Hint: use a while loop to ask for the height,
     * Hint: use another while loop to work out the size and draw each circle,
     *  stopping when the next circle won't fit.
     *  At the end, if there is still room, draw the appropriate sized circle
     *  to fill the gap.
     */
    public void drawRandomCircles(){
        double rectangleWidth = PATTERN_SIZE;
        
        double height = UI.askDouble("Height: ");
        
        while(height < 3 || height >= PATTERN_SIZE){
            height = UI.askDouble("Please enter a valid height: ");
        }
        
        UI.setColor(Color.black);
        UI.drawRect(PATTERN_LEFT, PATTERN_TOP, PATTERN_SIZE, height);
        
        double circleLeft = PATTERN_LEFT;
        double rightEdgeRect = PATTERN_LEFT + PATTERN_SIZE;
        
        UI.setColor(Color.blue);
        while(circleLeft <= rightEdgeRect){
            double diam = 3 + Math.random() * (height - 3);
            
                if(circleLeft + diam > rightEdgeRect){
                    diam = rightEdgeRect - circleLeft;
                }
            double circleTop = PATTERN_TOP + (height - diam) / 2.0;
            UI.fillOval(circleLeft, circleTop, diam, diam);
            
            circleLeft = circleLeft + diam;
        }
    }

    
    /**
     * Draws a square spiral pathway made of small circles arranged in the
     * shape of a square spiral
     *  Asks the user for the size of the circles
     *   and computes the number of circles needed to fit the PATTERN_SIZE
     *  Then draws all the circles from the outside to the inside.
     *  The gap between the legs of the spiral should be the width of the circles
     */
    public void drawSpiralPath(){
        double size = UI.askDouble("Size of the circles: ");  
            while(size <= 0 || size >= PATTERN_SIZE / 3){
                size = UI.askDouble("PLeaase enter a valid number: ");
            }
        
        double circleTop = PATTERN_TOP + size;
        double circleLeft = PATTERN_LEFT + size;
        double bottomLimit = PATTERN_TOP + PATTERN_SIZE - size * 2;
        double rightLimit = PATTERN_LEFT + PATTERN_SIZE - size * 2;
        
            while(circleTop + size < bottomLimit){
                while(circleLeft + size < rightLimit){
                    UI.setColor(Color.black);
                    UI.drawOval(circleLeft, circleTop, size, size);
                    
                    circleLeft += size;
                }
                circleTop += size;
            }
    }

    /**
     * Set up the GUI and buttons
     */
    public void setupGUI(){
        UI.initialise();
        UI.addButton("Clear",UI::clearPanes);
        UI.addButton("Core: Star", this::drawStar);
        UI.addButton("Core: TriGrid", this::drawTriGrid);
        UI.addButton("Completion: Random", this::drawRandomCircles);
        UI.addButton("Challenge: Spiral", this:: drawSpiralPath);
        UI.addButton("Quit", UI::quit);
    }   

    /**
     * main: create object and call setupGUI
     */
    public static void main(String[] arguments){
        PatternsDrawer pd = new PatternsDrawer();
        pd.setupGUI();
    }

    public static void run() {
        main(null);
    }
}

