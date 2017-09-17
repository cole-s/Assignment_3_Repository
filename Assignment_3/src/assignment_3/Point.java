package assignment_3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author casch
 */
public class Point {
    private int x_coord;
    private int y_coord;
    
    public Point(){
        x_coord = 0;
        y_coord = 0;
    }// end of Point constructor
    
    public void setX(int new_x){this.x_coord = new_x;}
    public int getX(){return this.x_coord;}
    public void setY(int new_y){this.y_coord = new_y;}
    public int getY(){return this.y_coord;}
    
    public String toString(){
        String ret = "(";
        ret = ret + x_coord + ", " + y_coord + ")";
        return ret;
    }
}
