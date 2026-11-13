// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP102 - 2026T2, Assignment 3
 * Name: Fengzhi Zhang
 * Username: zahngfeng1
 * ID: 300667389
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;

/** The program contains several methods for analysing the readings of the temperature levels over the course of a day.
 *  There are several things about the temperature levels that a user may be interested in: 
 *    The average temperature level.
 *    How the temperatures rose and fell over the day.
 *    The maximum and the minimum temperature levels during the day.
 */
public class TemperatureAnalyser{

    /* analyse reads a sequence of temperature levels from the user and prints out
     *    average, maximum, and minimum level and plots all the levels
     *    by calling appropriate methods
     */
    public void analyse(){
        UI.clearPanes();
        ArrayList<Double> listOfNumbers = UI.askNumbers("Enter levels, end with 'done': ");
        if (listOfNumbers.size() != 0) {
            this.printAverage(listOfNumbers);
            this.plotLevels(listOfNumbers);

            UI.printf("Maximum level was:  %f\n", this.maximumOfList(listOfNumbers));
            UI.printf("Minimum level was:  %f\n", this.minimumOfList(listOfNumbers));
        }
        else {
            UI.println("No readings");
        }
    }

    /** Print the average level
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the sum, which 
     *     needs to be initialised to an appropriate value.
     *  CORE
     */
    public void printAverage(ArrayList<Double> listOfNumbers) {
        double sum = 0;
        
        for(double num : listOfNumbers){
            sum += num;
        }
        
        double average = sum / listOfNumbers.size();
        
        UI.printf("Average level was: " + average);

    }

    /**
     * Plot a bar graph of the sequence of levels,
     * using narrow rectangles whose heights are equal to the level.
     * [Core]
     *   - Plot the bars.
     * [Completion]
     *   - Draws a horizontal line for the x-axis (or baseline) without any labels.
     *   - Any level greater than 400 should be plotted as if it were just 400, putting an
     *         asterisk ("*") above it to show that it has been cut off.
     * [Challenge:] 
     *   - The graph should also have labels on the axes, roughly every 50 pixels.
     *   - The graph should also draw negative temperature levels correctly.
     *   - Scale the y-axis and the bars so that the largest numbers and the smallest just fit on the graph.
     *     The numbers on the y axis should reflect the scaling.
     *   - Scale the x-axis so that all the bars fit in the window.
     */
    public void plotLevels(ArrayList<Double> listOfNumbers) {
        
        double left = 60;
        double top = 40;
        double graphWidth = 600;
        double graphHeight = 400;
        
        double max = this.maximumOfList(listOfNumbers);
        double min = this.minimumOfList(listOfNumbers);
        
        if (max < 0){
            max = 0;
        }
        
        if (min > 0){
            min = 0;
        }
        
        double range = max - min;
        
        if (range == 0){
            range =1;
        }
        
        double yScale = graphHeight / range;
        double zeroY = top + max * yScale;
        
        double step = graphWidth / listOfNumbers.size();
        double barWidth = step * 0.8;
        
        UI.drawLine(left, zeroY, left + graphWidth, zeroY);
        UI.drawLine(left, top, left, top + graphHeight);
        
        double leftnew = left;
        
        
        for(double level: listOfNumbers){
            
            if (level >= 0){
            double height = level * yScale;
            
            UI.setColor(Color.blue);
            UI.fillRect(leftnew, zeroY - height, barWidth, height);
            
            }else{
            double height = -level * yScale;
                
            UI.setColor(Color.blue);
            UI.fillRect(leftnew, zeroY, barWidth, height);
                
            }
            leftnew += step;
    
            }
            
        
        UI.println("Finished plotting");
        
    }
    

    /** Find and return the maximum level in the list
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the maximum, which
     *     needs to be initialised to an appropriate value.
     *  COMPLETION
     */
    public double maximumOfList(ArrayList<Double> listOfNumbers) {
        double maximum = listOfNumbers.get(0);
        
        for(double level: listOfNumbers){
            
            if (maximum < level){
                maximum = level;
            }
 
            }
        return maximum; 
            
    }

    /** Find and return the minimum level in the list
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the minimum, which
     *     needs to be initialised to an appropriate value.
     *  COMPLETION
     */
    public double minimumOfList(ArrayList<Double> listOfNumbers) {
        double minimum = listOfNumbers.get(0);
        
         for(double level: listOfNumbers){
            
            if (minimum > level){
                minimum = level;
            }

            }
            
        return minimum; 
    }



    public void setupGUI() {
        UI.initialise();
        UI.addButton("Analyse", this::analyse );
        UI.addButton("Quit", UI::quit );
    }

    public static void main(String[] args) {
        TemperatureAnalyser ta = new TemperatureAnalyser();
        ta.setupGUI();
    }

    public static void run() {
        main(null);
    }
}
