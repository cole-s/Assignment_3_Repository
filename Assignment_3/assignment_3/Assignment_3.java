/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_3;

import java.io.FileNotFoundException;

/**
 * Class: main to run from
 * Purpose: to execute the algorithms
 * @author casch
 */
public class Assignment_3 {

    /**
     * Method: main
     * Purpose: the main method of the program
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Control.readInputFile(args[0]); // reads input file
            // sets up arrays to be sorted and compared for shortest distance
            Point[] P = new Point[Control.points.length];
            Point[] Q = new Point[Control.points.length];
            
            // creates and fills arrays
            for(int index = 0; index < Control.points.length; index++){
                P[index] = Control.points[index];
                Q[index] = Control.points[index];
            }// end of for loop
            
            // sorts the arrays
            long start = System.nanoTime();
            Control.quickSortX(P, 0, Control.points.length-1);
            Control.mergeSortY(Q);
            //finds the min distance
            double distance = Control.findClosestPair(P, Q, 0);
            long finish = System.nanoTime();
            //prints results
            System.out.println("Total time taken: " + (finish-start));
            System.out.println("Distance: " + distance + "\nPoints: "+
                    Control.spoints);
        } catch (FileNotFoundException ex) {
            System.out.println("File couldn't be read or found");
        } // end of try-catch
    }// end of main
    
}// end of Class
