// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, Assignment 6
 * Name: Fengzhi Zhang
 * Username:zhangfeng1
 * ID: 300667389
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;

/**
 * Runs a simulation game of launching emojis at targets.
 */
public class EmojiLauncher{

    // Constants
    public static final double GROUND = 450;
    public static final double MAX_SPEED = 15;

    // Fields
    private ArrayList<Emoji> emojis = new ArrayList<Emoji>();  // list of all the emojis to display
    private Emoji newEmoji;                //the emoji about to be launched
    private boolean newEmojiReady = false; //the new emoji has been launched and can be added to emojis

    private double pressedX;  // Where the mouse was pressed
    private double pressedY;

    private int currentNumberOfTargets;  // Number of targets currently in the game

    private boolean running = false;

    /**
     * Restart the game:
     *  stop any running simulation,
     *  reset the number of targets
     *  create a target, and start the game
     */
    public void restart(){
        running=false;
        UI.sleep(300);  // to make sure that any running game has stopped
        this.currentNumberOfTargets=0;
        this.createTargets();
        this.runGame();
    }

    /**
     * Main loop for the game
     * It creates a set of target emojis
     * It has a loop that repeatedly
     *   - Makes each emoji take one step
     *   - Checks whether any moving emoji is touching a stationary emoji.
     *     If so, launches the stationary emoji towards the ground
     *   - redraws the state of the game
     */
    public void runGame(){
        running = true;    
        UI.printMessage("Drag Mouse to create and launch an emoji");

        while (running){
            // draw the ground and all the emojis
            UI.setImmediateRepaint(false);
            UI.clearGraphics();
            UI.drawLine(0, GROUND, 650, GROUND);

            for (Emoji em : this.emojis){
                em.draw();
            }

            UI.repaintGraphics();

            // make each emoji take a step
            for (Emoji em : this.emojis){
                em.step();
            }

            //check whether any moving emoji is touching a stationary emoji;
            // if so, it makes the moving emoji "bump" the stationary emoji
            for (Emoji em : this.emojis){
                if (em.getSpeed()>0){
                    for (Emoji other : this.emojis){
                        if (other.getSpeed()==0 && em.touching(other)){
                            em.bump(other);
                        }
                    }
                }
            }

            if (this.emojis.size()==currentNumberOfTargets){
                UI.printMessage("Please launch a new Emoji by dragging up from the ground");
            }

            else if (this.noActiveEmoji()){
                UI.printMessage("You may create a new set of Targets");
            }

            else {
                UI.printMessage("");
            }

            // add the newly created emoji to the list, if it is ready
            if (this.newEmojiReady) {
                this.emojis.add(this.newEmoji);
                this.newEmojiReady = false;
            }

            UI.sleep(40); // pause of 40 milliseconds
        }
    }

    /**
     * Creates a new list of target Emojis at random places,
     * with a random name from the EMOJI_NAMES list
     */
    public void createTargets(){
        this.emojis = new ArrayList<Emoji>();
        this.currentNumberOfTargets++;

        for (int i=1; i<=currentNumberOfTargets; i++){
            double x = 50+Math.random()*550;
            double h = Math.random()*400;
            String name = this.getRandomName();
            this.emojis.add(new Emoji(x, h, name));
        }
    }

    /**
     * Pressing will create a new emoji.
     * Releasing it will launch it
     * The x position is based on the pressed point;
     * the y position is at ground level.
     * Speed is based on the position of the release point relative to the pressed point.
     */
    public void doMouse(String action, double x, double y){
        switch (action){
            case "pressed" -> {
                    this.pressedX = x;
                    this.pressedY = y;
                    this.newEmoji = new Emoji(x, 10, getRandomName());
                    this.newEmojiReady = true;
                }

            case "released" -> {
                    double speedX = (x-pressedX)/10;
                    double speedY = (pressedY-y)/10;
                    //scale down if over the maximum allowed speed
                    double speed = Math.hypot(speedX, speedY);

                    if (speed < 1){
                        speedY = 1; 
                        speedX = 0.01;
                    }
                    else if (speed> MAX_SPEED){
                        speedY = speedY * MAX_SPEED/speed;
                        speedX = speedX * MAX_SPEED/speed;
                    }

                    this.newEmoji.launch(speedX, speedY);
                }

            case "dragged" -> {
                    UI.drawLine(this.pressedX, this.pressedY, x, y);
                }
        }
    }

    /**
     * Returns true if all the emoji are sitting on the ground
     */
    public boolean noActiveEmoji(){
        for (Emoji em : this.emojis){
            if (em.getHeight()>0){
                return false;
            }
        }
        return true;
    }

    //Constant:
    /**
     *  List of valid names for making new Emojis
     */
    public static final List<String> EMOJI_NAMES =
        List.of("angry.png","astonished.png","bicycle.png","cherry.png",
            "cloud.png","clover.png","coffee.png","crying.png","halo.png",
            "hibiscus.png","moon.png","omg.png","sleeping.png","smiling-eyes.png",
            "smiling.png","sunglasses.png","sweat.png","whale.png");

    /**
     * Returns a random name from the list of valid names
     */
    public String getRandomName(){
        return  EMOJI_NAMES.get((int)((int)(Math.random()*EMOJI_NAMES.size())));
    }

    /**
     * Setup the mouse listener and the buttons
     */
    public void setupGUI(){
        UI.setMouseMotionListener(this::doMouse); // the mouse will launch the emoji
        UI.addButton("New Targets", this::createTargets); // Creates a new set of targets
        UI.addButton("Restart", this::restart);           // Initialises the game
        UI.addButton("Quit", UI::quit);
        UI.setWindowSize(650,500);
        UI.setDivider(0);
    }

    /**
     * Create a new EmojiLauncher object and setup the interface
     */
    public static void main(String[] arguments){
        EmojiLauncher el = new EmojiLauncher();
        el.setupGUI();
        el.createTargets();
        el.runGame();
    }

    public static void run() {
        main(null);
    }

}
