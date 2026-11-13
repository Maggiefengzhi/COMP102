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
 * Tile
 * Represents a single tile.
 */

public class Tile{

    private String letter;
    private String image;

    /** 
     * Construct a new Tile object
     */
    public Tile(){
        this.letter = ""+(char)(Math.random()*8+'A');
        this.image = "tiles/"+ this.letter + ".jpg";
    }

    /**
     * Return the letter on the tile
     */
    public String getLetter(){
        return this.letter;
    }

    /**
     * Draw the tile
     */
    public void draw(double x, double y){
        UI.drawImage(this.image, x, y);
    }

}
