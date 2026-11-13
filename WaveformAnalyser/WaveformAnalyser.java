// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, Assignment 5
 * Name: Fengzhi Zhang
 * Username: zhangfeng1
 * ID: 300667389
 */

import ecs100.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.awt.Color;

/**
 * This program reads waveform data from a file and displays it
 * The program will also do some analysis on the data
 * The user can also edit the data - deleting, duplicating, and adding 
 *
 * The methods you are to complete all focus on the ArrayList of data.
 * It is related to assignment 3 which analysed temperature levels
 *
 * CORE
 *  display:            displays the waveform.
 *  read:               reads numbers into an ArrayList.
 *  showSpread:         displays the maximum and minimum values of the waveform.
 *  increaseRegion:     increases all the values in the selected region by 10%.
 *  decreaseRegion:     decreases all the values in the selected region by 10%.
 *  doubleFrequency:    removes every second value from the waveform.
 *
 * COMPLETION
 *  highlightPeaks:     puts small green circles around all the peaks in the waveform.
 *  displayDistortion:  shows in red the distorted part of the signal.
 *  deleteRegion:       deletes the selected region of the waveform

 * CHALLENGE
 *  duplicateRegion:    duplicates the selected region of the waveform
 *  displayEnvelope:    displays the envelope.
 *  save:               saves the current waveform values into a file.
 *  ....                allows more editing
 *                       
 */

public class WaveformAnalyser{

    // Constants: 
    public static final int ZERO_LINE = 300;    // dimensions of the graph for the display method
    public static final int GRAPH_LEFT = 10;
    public static final int GRAPH_WIDTH = 800;
    public static final int GRAPH_RIGHT = GRAPH_LEFT + GRAPH_WIDTH;

    public static final double THRESHOLD = 200;  // threshold for the distortion level
    public static final int CIRCLE_SIZE = 10;    // size of the circles for the highlightPeaks method

    // Fields 
    private ArrayList<Double> waveform;   // the field to hold the ArrayList of values

    private int regionStart = 0; // The index of the first value in the selected region
    private int regionEnd;       // The index one past the last value in the selected region

    /**
     * Set up the user interface
     */
    public void setupGUI(){
        UI.setMouseListener(this::doMouse);   
        //core
        UI.addButton("Display", this::display);
        UI.addButton("Read Data", this::read);
        UI.addButton("Show Spread", this::showSpread);
        UI.addButton("Increase region", this::increaseRegion);
        UI.addButton("Decrease region", this::decreaseRegion);
        UI.addButton("Double frequency", this::doubleFrequency);
        //completion
        UI.addButton("Peaks", this::highlightPeaks);
        UI.addButton("Distortion", this::displayDistortion);
        UI.addButton("Delete", this::deleteRegion);
        //challenge
        UI.addButton("Duplicate", this::duplicateRegion);
        UI.addButton("Envelope", this::displayEnvelope);
        UI.addButton("Save", this::save);

        UI.addButton("Quit", UI::quit);
        UI.setWindowSize(900, 650);
    }

    /**
     * [CORE]
     * Displays the waveform as a line graph,
     * Draw the axes
     * Plots a line graph of all the points with a blue line between
     *  each pair of adjacent points
     * The n'th value in waveform is at
     *    x-position is GRAPH_LEFT + n
     *    y-position is ZERO_LINE - the value
     * Don't worry if the data goes past the end the window
     */
    public void display(){
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        UI.clearGraphics();

        // draw x axis (showing where the value 0 will be)
        UI.setColor(Color.black);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE, GRAPH_RIGHT, ZERO_LINE); 

        // plot points: blue line between each pair of values
        UI.setColor(Color.blue);
        for (int i = 0; i < this.waveform.size() - 1; i++){
            double value1 = this.waveform.get(i);
            double value2 = this.waveform.get(i+1);
            
            double x1 = GRAPH_LEFT + i;
            double y1 = ZERO_LINE - value1;
            
            double x2 = GRAPH_LEFT + i + 1;
            double y2 = ZERO_LINE - value2;
            
            UI.drawLine(x1, y1, x2, y2);
        }

        this.displayRegion();  // Displays the selected region, if any
    }

    /**
     * [CORE]
     * Clears the panes, 
     * Asks user for a waveform file (eg waveform1.txt)
     * The files consist of a sequence of numbers.
     * Creates an ArrayList stored in the waveform field, then
     * Reads data from the file into the ArrayList
     * calls display.
     */
    public void read(){
        UI.clearPanes();
        String fname = UIFileChooser.open();
        this.waveform = new ArrayList<Double>();   // create an empty list in the waveform field
        
        try{
            Scanner scan = new Scanner (Path.of(fname));
            
            while (scan.hasNextDouble()){
                double value = scan.nextDouble();
                this.waveform.add(value);
            }
            scan.close();
        
        }catch(IOException e){
            UI.println("File reading failed: " + e);
        }
        
        

        UI.printMessage("Read " + this.waveform.size() + " data points from " + fname);

        this.regionStart = 0;
        this.regionEnd = this.waveform.size();
        this.display();
    }

    /**
     * Displays the selected region by a red line on the axis
     */
    public void displayRegion(){
        UI.setColor(Color.red);
        UI.setLineWidth(3);
        UI.drawLine(GRAPH_LEFT+this.regionStart, ZERO_LINE, GRAPH_LEFT+this.regionEnd-1, ZERO_LINE);
        UI.setLineWidth(1);
    }

    /**
     * [CORE]
     * The spread is the difference between the maximum and minimum values of the waveform.
     * Finds the maximum and minimum values of the waveform, then
     * Displays the spread by drawing two horizontal lines on top of the waveform: 
     *   one green line for the maximum value, and
     *   one red line for the minimum value.
     */
    public void showSpread() {
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        this.display();
        
        double min = this.waveform.get(0);
        double max = this.waveform.get(0);
        double right = GRAPH_LEFT + this.waveform.size();
        
        UI.setLineWidth(1);  
        for(double value : this.waveform){
            if (value < min){
                min = value;
            }        
        }
        double minY = ZERO_LINE - min;
        UI.setColor(Color.red);
        UI.drawLine(GRAPH_LEFT, minY, right, minY);
        
        for(double value : this.waveform){
            if (value > max){
                max = value;
            }        
        }       
        double maxY = ZERO_LINE - max;
        UI.setColor(Color.green);
        UI.drawLine(GRAPH_LEFT, maxY, right, maxY);
    }

    /**
     * [CORE]
     * Increases the values in the selected region of the waveform by 10%.
     * (The selected region is initially the whole waveform, but the user can drag the
     *  mouse over part of the graph to select a smaller region).
     * The selected region goes from the index in the regionStart field to the index
     *  in the regionEnd field.
     */
    public void increaseRegion() {
        if (this.waveform == null){ //there is no waveform to process
            UI.println("No waveform");
            return;
        }
        
        for(int i = regionStart; i < regionEnd; i++){
            double oldValue = this.waveform.get(i);
            double newValue = oldValue * 1.1;
            
            this.waveform.set(i, newValue);
        }

        this.display();
    }

    /**
     * [CORE]
     * Decreases the values in the selected region of the waveform by 10%.
     * (The selected region is initially the whole waveform, but the user can drag the
     *  mouse over part of the graph to select a smaller region).
     * The selected region goes from the index in the regionStart field to the index
     *  in the regionEnd field.
     */
    public void decreaseRegion() {
        if (this.waveform == null){ //there is no waveform to process
            UI.println("No waveform");
            return;
        }
        for(int i = regionStart; i < regionEnd; i++){
            double oldValue = this.waveform.get(i);
            double newValue = oldValue * 0.9;
            
            this.waveform.set(i, newValue);
        }

        this.display();
    }

    /**
     * [CORE]
     * Double the frequency of the waveform by removing every second value in the list.
     * Resets the selected region to the whole waveform
     */
    public void doubleFrequency() {
        if (this.waveform == null){ //there is no waveform to process
            UI.println("No waveform");
            return;
        }
        int i = 1;
        
        while (i < this.waveform.size()){
            this.waveform.remove(i);
            i = i + 1;
        }
        
        this.regionStart = 0;
        this.regionEnd = this.waveform.size();
        this.display();
    }

    /**
     * [COMPLETION]
     * Plots the peaks with small green circles. 
     *    A peak is defined as a value that is greater than or equal to both its
     *    neighbouring values.
     * Note the size of the circle is in the constant CIRCLE_SIZE
     */
    public void highlightPeaks() {
        this.display();     //use display if displayDistortion isn't complete
       
        for (int i = 1; i < waveform.size() - 1; i++){
            if (waveform.get(i) >= waveform.get(i + 1) && waveform.get(i) >= waveform.get(i - 1)){
                double peak = waveform.get(i);
                
                double x = GRAPH_LEFT + i;
                double y = ZERO_LINE - waveform.get(i);
                double left = x - CIRCLE_SIZE / 2; 
                double top = y -  CIRCLE_SIZE / 2;
                
                UI.setColor(Color.green);
                UI.setLineWidth(1);
                UI.drawOval(left, top, CIRCLE_SIZE, CIRCLE_SIZE);
            }
        }
    }

    /**
     * [COMPLETION]  [Fancy version of display]
     * Display the waveform as a line graph. 
     * Draw a line between each pair of adjacent points
     *   * If neither of the points is distorted, the line is BLUE
     *   * If either of the two end points is distorted, the line is RED
     * Draw the horizontal lines representing the value zero and thresholds values.
     * Uses THRESHOLD to determine distorted values.
     * Uses GRAPH_LEFT and ZERO_LINE for the dimensions and positions of the graph.
     * [Hint] You may find Math.abs(int a) useful for this method.
     * You may assume that all the values are between -250 and +250.
     */
    public void displayDistortion() {
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        UI.clearGraphics();

        // draw zero axis
        UI.setColor(Color.black);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE, GRAPH_LEFT + this.waveform.size() , ZERO_LINE); 

        // draw thresholds
        UI.setColor(Color.green);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE - THRESHOLD, GRAPH_RIGHT, ZERO_LINE - THRESHOLD);
        UI.drawLine(GRAPH_LEFT, ZERO_LINE + THRESHOLD, GRAPH_RIGHT, ZERO_LINE + THRESHOLD);
        
        for (int i =0; i < this.waveform.size() - 1; i++){
            double value1 = Math.abs(this.waveform.get(i));
            double value2 = Math.abs(this.waveform.get(i+1));
            
            if (value1 > THRESHOLD || value2 > THRESHOLD){
                UI.setColor(Color.red);
            }else{
                UI.setColor(Color.blue);
            }
            
            double x1 = GRAPH_LEFT + i;
            double y1 = ZERO_LINE - this.waveform.get(i);
            
            double x2 = GRAPH_LEFT + i + 1;
            double y2 = ZERO_LINE - this.waveform.get(i+1);
            
            UI.drawLine(x1, y1, x2, y2); 
        }
        
        this.displayRegion();
    }

    /**
     * [COMPLETION]
     * Removes the selected region from the waveform
     * selection should be reset to be the whole waveform
     * redisplays the waveform
     */
    public void deleteRegion(){
        if (this.waveform == null) {
            UI.println("No waveform");
            return;
        }
        int numToDelete = this.regionEnd - this.regionStart;
        
        for(int i = this.regionStart; i <= numToDelete; i++){
            this.waveform.remove(regionStart);
        }
        
        this.regionStart = 0;
        this.regionEnd = this.waveform.size();

        this.display();
    }

    /**
     * [CHALLENGE]
     * If there is a selected region, then add a copy of that section to the waveform,
     * immediately following the selected region
     * selection should be reset to be the whole waveform
     * redisplay the waveform
     */
    public void duplicateRegion(){
        ArrayList<Double>copiedRegion = new ArrayList<Double>();
        for (int i = this.regionStart; i < this.regionEnd; i++){
            double value = this.waveform.get(i);
            copiedRegion.add(value);                        
        }
        this.waveform.addAll(regionStart + 1, copiedRegion);
        this.regionStart = 0;
        this.regionEnd = this.waveform.size();
        this.display();
    }

    /**
     * [CHALLENGE]
     * Displays the envelope (upper and lower) with GREEN lines connecting all the peaks.
     *    A peak is defined as a point that is greater than or equal to *both* neighbouring points.
     */
    public void displayEnvelope(){
        if (this.waveform == null){ //there is no data to display
            UI.println("No waveform to display");
            return;
        }
        this.display();  // display the waveform,

        /*# YOUR CODE HERE */

    }

    /**
     * [CHALLENGE]
     * Saves the current waveform values into a file
     */
    public void save(){
        /*# YOUR CODE HERE */

    }

    /**
     * Lets user select a region of the waveform with the mouse
     * and deletes that section of the waveform.
     */
    public void doMouse(String action, double x, double y){
        int index = (int)x-GRAPH_LEFT;
        if (action.equals("pressed")){
            this.regionStart = Math.max(index, 0);
        }
        else if (action.equals("released")){
            if (index < this.regionStart){
                this.regionEnd = this.regionStart;
                this.regionStart = Math.max(index,this.waveform.size());
            }
            else {
                this.regionEnd = Math.min(index,this.waveform.size());
            }
            this.display();
        }

    }

    /**
     * Make a "triangular" waveform file for testing the other methods
     */
    public void makeTriangleWaveForm(){
        this.waveform = new ArrayList<Double>();
        for (int cycle=0; cycle<10; cycle++){
            for (int i=0; i<15; i++){this.waveform.add(i*18.0);}
            for (int i=15; i>-15; i--){this.waveform.add(i*18.0);}
            for (int i=-15; i<0; i++){this.waveform.add(i*18.0);}
        }
        this.regionStart = 0;
        this.regionEnd = this.waveform.size();
    }

    public static void main(String[] args){
        WaveformAnalyser wav = new WaveformAnalyser();
        wav.setupGUI();
        wav.makeTriangleWaveForm();
    }

    public static void run() {
        main(null);
    }

}
