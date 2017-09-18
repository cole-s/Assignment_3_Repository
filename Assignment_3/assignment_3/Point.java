package assignment_3;
/**
 * Class: Point
 * Purpose: holds the values of points on a 2D plane with x and y values
 * @author casch
 */
public class Point {
    private int x_coord; // x value
    private int y_coord; // y value
    
    public Point(){
        // default values
        x_coord = 0;
        y_coord = 0;
    }// end of Point constructor
    
    // allows points to be changed if needed and allows access to 
    // coordinate values
    public void setX(int new_x){this.x_coord = new_x;}
    public int getX(){return this.x_coord;}
    public void setY(int new_y){this.y_coord = new_y;}
    public int getY(){return this.y_coord;}
    
    /**
     * Method: toString()
     * Purpose: String interpretation of the point
     * @return a String of the point
     */
    public String toString(){
        String ret = "(";
        ret = ret + x_coord + ", " + y_coord + ")";
        return ret;
    } // end of toString
}// end of Class
