/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_3;

import java.io.FileNotFoundException;

/**
 *
 * @author casch
 */
public class Assignment_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Control.readInputFile(args[0]);
            Point[] P = Control.quickSortX(Control.points, 0, Control.points.length);
            int size = Control.points.length;
            Point[] Q = Control.mergeSortY(Control.points);
            Control.findClosestPair(P, Q, 0);
            System.out.println(Control.spoints);
        } catch (FileNotFoundException ex) {
            System.out.println("File couldn't be read or found");
        }
    }
    
}
