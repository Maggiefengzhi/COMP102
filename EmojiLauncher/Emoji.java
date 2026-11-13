// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, Assignment 6
 * Name: Fengzhi Zhang
 * Username: zhangfeng1
 * ID: 300667389
 */

import ecs100.*;
import java.awt.Color;

/**
 * An Emoji represents an object that can be thrown through the air, or knocked down
 *  onto the ground by another Emoji.
 *  An Emoji can be created at a position, and it will appear at that place.
 *  An Emoji can be launched with a velocity (horizontal and vertical speeds)
 *  The simulation can call the step() method to make the Emoji move one step in its current direction
 *  If a moving Emoji bumps into a stationary Emoji, it will launch the other Emoji towards the ground.
 * There are a set of different emoji images, all of size 40x40
 */

public class Emoji{

    // Constants for all Emoji: size, position of the ground
    public static final double SIZE = 40;  // width/length of the emoji images
    public static final double GROUND = EmojiLauncher.GROUND;
    public static final double GRAVITY = 0.25;  // how much to reduce the speed each step.

    // Fields to store state of the Emoji:
    private String name;
    private double xPosition;
    private double heightAboveGround;
    private double horizontalSpeed;
    private double verticalSpeed;

    // Constructor
    /**
     * Construct a new Emoji object.
     *    Parameters are
     *   the initial position (x of the center, and
     *      the height of the bottom of the emoji above the ground),
     *   the name
     *    Stores the parameters into fields 
     *  SHOULD NOT DRAW THE EMOJI!
     */
    public Emoji(double x, double h, String name){
        this.xPosition = x;
        this.heightAboveGround = h;
        this.name = name;
    }

    // Methods
    /**
     * Draw the Emoji on the Graphics Pane centered at its current position
     */
    public void draw(){
        double leftEdgeEmoji = xPosition - SIZE/2;
        double topEdgeEmoji = GROUND - (heightAboveGround + SIZE);
        UI.drawImage("emojis/" + name, leftEdgeEmoji, topEdgeEmoji, SIZE, SIZE);
    }

    /**
     * If the emoji's speed is not 0, move the Emoji one step (DO NOT REDRAW IT)
     *    Change its height and x position using the vertical and horizonal steps
     *    Reduce its vertical speed each step (due to gravity), 
     *    If it would hit the ground, then change its y position so that it is
     *     resting on the ground and
     *     set its speed (horizontal and vertical) to 0.
     */
    public void step(){
        double unitStep = 1;
        
        if (getSpeed() != 0 ){
                this.xPosition = this.xPosition + horizontalSpeed * unitStep;
                this.heightAboveGround = this.heightAboveGround + verticalSpeed * unitStep;
                this.verticalSpeed = this.verticalSpeed - GRAVITY;
                
                if (heightAboveGround <= 0){
                    heightAboveGround = 0;
                    verticalSpeed = 0;
                    horizontalSpeed = 0;
                }
        }
    
    }

    /**
     * Return the height of the bottom of the emoji above the ground
     */
    public double getHeight(){
        return this.heightAboveGround;
        
    }

    /**
     * Return the horizontal position of the emoji
     */
    public double getX(){
        return this.xPosition;

    }

    /**
     * Return the speed of the emoji
     */
    public double getSpeed(){
        return Math.hypot(horizontalSpeed, verticalSpeed);

    }

    /**
     * Launch the emoji at the specified horizontal and vertical speeds
     */
    public void launch(double xSpeed, double ySpeed){
        this.horizontalSpeed = xSpeed;
        this.verticalSpeed = ySpeed;

    }

    /**
     * Return true if this emoji is touching the other emoji (and isn't the same emoji)
     */
    public boolean touching(Emoji other){
        double distance = Math.hypot(this.xPosition - other.xPosition, this.heightAboveGround - other.heightAboveGround); 
        
        if(this == other){
            return false;
        }else if(distance <= 40){
            return true; 
        }else{
            return false;
        }

    }

    /**
     * Make this emoji bump the other emoji and make it start moving.
     * Assumes: this != other, this is moving, other is not moving
     * Simple version: just make the other start to move a little bit.
     * Better version: do a proper, elastic collision.
     */
    public void bump(Emoji other){
        double xDifference = other.xPosition - this.xPosition;
        double heightDifference = other.heightAboveGround - this.heightAboveGround;
        double distance = Math.hypot(xDifference, heightDifference);
        
        if(distance == 0){
            return;
        }
        
        double directionX = xDifference / distance;
        double directionY = heightDifference / distance;
        double speedTowardsOther 
        = this.horizontalSpeed * directionX + this.verticalSpeed * directionY;
        
        if(speedTowardsOther <= 0){
            return;
        }
        
        double newXSpeed = speedTowardsOther * directionX;
        double newYSpeed = speedTowardsOther * directionY;
        
        this.launch(this.horizontalSpeed - newXSpeed, this.verticalSpeed - newYSpeed); 
        other.launch(newXSpeed, newYSpeed);   
    }


}
