// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, Assignment 3
 * Name: Fengzhi Zhang
 * Username: zhangfeng1
 * ID: 300667389
 */

import ecs100.*;

/**
 * Program to create simple animated animal character using the
 * Animal class.  
 */

public class PetShow{

    /** CORE
     * animate creates two or several animals on the window.
     *  Then animates them according to a fixed script by calling a series
     *  of methods on the animals.
     */
    public void animate(){
        Animal dog = new Animal("dog", "Govindadas", 100, 100);
        Animal tiger = new Animal("tiger", "Ben", 500, 100);
        
        tiger.introduce("Hi.");
        dog.introduce("Hi, my friends!");
        tiger.speak("I am hungery!");
        dog.goLeft(20);
        tiger.goLeft(100);
        dog.shout("Help!");
        dog.goLeft(11);
        tiger.goLeft(100);
        dog.shout("Oh no! God please help me!");
        dog.jump(10);
        tiger.think("I suddenly not feeling hungery now! I do not wan to have food now.");
        tiger.goRight(100);
        dog.think("Thanks GOd!");
    }

    /**
     *  threeAnimalsRoutine creates three animals on the window.
     *  Then makes each animal do the same routine in turn.
     *  You should define a routine method, and threeAnimalsRoutine
     *   should call the routine method three times, to make
     *   each of the three animals perform the routine in turn.
     */
    public void threeAnimalsRoutine(){
        Animal turtle = new Animal("turtle", "Krishnadas", 100, 300);
        Animal grasshopper = new Animal("grasshopper", "Radhadasi", 500, 300);
        Animal dinosaur = new Animal("dinosaur", "Gauradas", 200, 300);
        
        routine(turtle);
        routine(grasshopper);
        routine(dinosaur);
    }

    public void routine(Animal animal){
        animal.introduce("Hello");
        animal.jump(100);
        animal.goLeft(100);
        animal.jump(100);
        animal.think("Happy is so simple!");
        animal.goRight(100);
        animal.speak("Thanks God!");
    }

    public void newanimate() {

        Animal dog = new Animal("dog", "Ramadas", 100, 300);
        Animal tiger = new Animal("tiger", "Sitadasi", 500, 300);
    
        dog.introduce("Hello");
        tiger.introduce("Hi");
    
        dog.speak("Watch my hop!");
        dog.hop();
    
        tiger.speak("My turn!");
        tiger.hop();
    }
    /**
     * Make buttons to let the user run the methods
     */
    public void setupGUI(){
        UI.initialise();
        UI.addButton("Clear", UI::clearGraphics );
        UI.addButton("Animate", this::animate );
        UI.addButton("Three", this::threeAnimalsRoutine );
        UI.addButton("Quit", UI::quit );
        UI.setDivider(0);       // Expand the graphics area
    }

    /**
     * Create object and call setupGUI on it
     */
    public static void main(String[] args){
        PetShow ps = new PetShow();
        ps.setupGUI();
    }

    public static void run() {
        main(null);
    }
}

