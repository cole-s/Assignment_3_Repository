package assignment_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class: Control
 * Purpose: Serves as the handler for all user input and behind the scenes 
 *          calculations for sorting the array of points
 * @author casch
 */
public class Control {
    // varibles are public for easier use in the main class
    public static Point[] points; // points taken directly from the file
    public static String spoints = ""; // String interpretation of the closest points
    
    public static Point pointa;
    public static Point pointb;
    public static double dis = -1.0;
    
    /**
     * Method: readInputFile
     * Purpose: to read the input file taken from the arguments of the main 
     *          class
     * @param filename - name of the input file as a string
     * @throws FileNotFoundException  if the file cannot be found
     */
    public static void readInputFile(String filename) throws FileNotFoundException{
        try{
            BufferedReader file = new BufferedReader(new FileReader(filename));
            String s_num = file.readLine().trim();// input from file as a String
            if(s_num.isEmpty() || s_num.contains(" ")) 
            // in first input from file is incorrectly formated
                throw new NumberFormatException();
            // end of if statement  
            
            int num = Integer.parseInt(s_num); // converst String to integer
            Point[]temp = new Point[num];
            // matrix used to store information from file
            String line = new String();      

            for(int row = 0; row < num; row++){
                temp[row] = new Point();
                line = file.readLine(); // reads next line in file
                Scanner sin = new Scanner(line); // breaks down string returned
                    temp[row].setX(sin.nextInt());
                    //System.out.print(temp[row].getX());
                    temp[row].setY(sin.nextInt());
                    //System.out.print(temp[row].getY());
                    //end of if statement
               
            }// end of for loop

            file.close(); // closes file from arguments
            points = temp;
        } catch(IOException err){
            throw new FileNotFoundException();
        }// end of try-catch statements
    }// end of readInputFile
    
    /**
     * Method: findClosestPair
     * Purpose: finds the closest pair between two points from an array of points
     *          of size n
     * @param Pall - points sorted by x coord
     * @param Qall - points sorted by y coord
     * @param count - helps make sure the method is on the first call when
     *                needed
     * @return the distance of the closest pair
     */
    public static double findClosestPair(Point[] Pall, Point[] Qall, int count){
        double dMinSq = -1;
        String new_points = "";
        if(Pall.length <= 3){
            double closest = -1; // indicates the points with the closest dis
            int indexP = 0; // first index
            int indexQ = 0; // second index
            
            for(indexP = 0; indexP < Pall.length-1; indexP++){
                for(indexQ = indexP+1; indexQ < Pall.length; indexQ++){

                    double pair = (Math.pow(Pall[indexP].getX()-Pall[indexQ].getX(), 2)+
                            Math.pow(Pall[indexP].getY()-Pall[indexQ].getY(), 2));
                    if(pair < closest || closest == -1){
                        closest = pair;
                        if(dis == -1.0 || dis < closest){
                            dis = closest;
                            pointa = Pall[indexP];
                            pointb = Pall[indexQ];
                        }// end of if statement                        
                    } // end of if statement
                }// end of for loop
            }// end of for loop
            return Math.sqrt(closest);
        } else {
            int end = (int) Math.ceil((double) Pall.length/2.0)-1;
            Point[] Pleft = copyPartialArray(Pall, 0, end);
            Point[] Qleft = new Point[Pleft.length];
            int add = 0;
            
            for(int index = 0; index < Qall.length; index++){
                for(int check = 0; check < Pleft.length; check++){
                    if(Pleft[check].equals(Qall[index])){
                        Qleft[add] = Qall[index];
                        add++;
                        check = Qall.length;
                    } // end of if statement
                } // end of for loop
            }// end of for loop
            Point[] Pright = copyPartialArray(Pall, end+1, Pall.length-1);
            Point[] Qright = new Point[Pright.length];
            add = 0;
            for(int index = 0; index < Qall.length; index++){
                for(int check = 0; check < Pright.length; check++){
                    if(Pright[check].equals(Qall[index])){
                        Qright[add] = Qall[index];
                        add++;
                        check = Qall.length;
                    } // end of if statement
                } // end of for loop
            }// end of for loop
            
            // finds the two shortest distances
            double dleft = findClosestPair(Pleft, Qleft, count+1);
            double dright = findClosestPair(Pright, Qright, count+1);
            
            double d = Math.min(dleft, dright);
            int mid = Pleft[Pleft.length-1].getX();
            int num = 0;
            Point[] S = new Point[Qall.length];
            for(int index = 0; index < Qall.length; index++){
                if(Math.abs(Qall[index].getX()-mid) < d){
                    S[num] = Qall[index];
                    num++;
                }
            }// end if for loop
            dMinSq = Math.pow(d, 2); // defaults varible
            
            for(int index = 0; index < num-1; index++){
                int k = index + 1;
                double y_diff = S[k].getY() - S[index].getY();
                while((k <= num-1) && Math.pow( y_diff, 2) < dMinSq){
                    double x_diff = S[k].getX() - S[index].getX();
                    dMinSq = Math.min(Math.pow(x_diff, 2) + Math.pow(y_diff, 2), dMinSq);
                    if(dis == -1.0 || dis > dMinSq){
                        dis = dMinSq;
                        pointa = S[k];
                        pointb = S[index];   
                    }
                    k++;
                }// end of while loop
            } // end of for loop
        } // end of if-else statements
        spoints = pointa.toString() + ", " + pointb.toString();

        return Math.sqrt(dMinSq);
    }// end of findClosestPair
    
    /**
     * Method: copyPartialArray
     * Purpose: copies an array from index a to index b and returns the result
     * @param array - array to be copied from
     * @param start - start of the copy
     * @param end - ending index of the copy from the original array
     * @return the outcome from copying the corresponding points
     */
    private static Point[] copyPartialArray(Point[] array, int start, int end){
        Point[] ret = new Point[(end-start)+1];
        int index2 = 0;
        for(int index = start; index <= end; index++){            
            ret[index2] = array[index];
            index2++;
        }// end of for loop
        return ret;
    } // end of copyPartialArray
//======================Sorting by X value======================================
       
    /**
     * Method: quickSortX
     * Purpose: sorts the array with respect to the x values
     * @param array - array to be sorted
     * @param left - left end of array to be sorted
     * @param right - right index of the array to be sorted
     * @return a sorted array of points based on the x value
     */
    public static Point[] quickSortX(Point[] array, int left, int right){
        if(left < right){
            int split = hoarePartitioningX(array, left, right);
            quickSortX(array, left, split-1);
            quickSortX(array, split+1, right);
        }// end of if statement
        
        return array;
    }// end of quickSort
    
    /**
     * Method: hoarePartitioningX
     * Purpose: sorts the array from a pivot index and a left and right index 
     *          values
     * @param array - array to be sorted
     * @param left - left index of the array
     * @param right - right index of the array
     * @return the split point/pivot of the array
     */
    private static int hoarePartitioningX(Point[] array, int left, int right){
        int tnum = array[left].getX(); // Pivot selected as the first element in the array list
        int numl = left; // starts from left-most index
        int numr = right+1;// starts from right-most index
        
        do{
            do{numl++;}while((numl < array.length-1) && (array[numl].getX() < tnum));
            do{numr--;}while((numr > 0) && (array[numr].getX() > tnum));
            // end of do while loops
            //Swap array[numl] with array[numr]
            Point temp = array[numl];
            array[numl] = array[numr];
            array[numr] = temp;
            
        }while(numl < numr); // end of do while loop
        
        //Swap array[numl] with array[numr] (undo last swap when i>=j)
        Point temp = array[numl];
        array[numl] = array[numr];
        array[numr] = temp;
        //Swap array[left] with array[numr]
        temp = array[left];
        array[left] = array[numr];
        array[numr] = temp;
        
        return numr;
    }// end of hoarePartitioningX
    
    //=====================Sorting by y value=======================================
    
    /**
     * Method: mergeSortY
     * Purpose: sorts the array based on the y value of the points
     * @param arrayA - array to be sorted
     * @return a sorted array
     */
    public static Point[] mergeSortY(Point[] arrayA){
        if(arrayA.length > 1) {
            int size1 = (int) Math.floor(arrayA.length/2);
            int size2 = arrayA.length-size1;
            Point[] arrayB = new Point[size1]; System.arraycopy(arrayA, 0, arrayB, 0, size1);
            Point[] arrayC = new Point[size2]; System.arraycopy(arrayA, size1, arrayC, 0, size2);
            arrayB = mergeSortY(arrayB);
            arrayC = mergeSortY(arrayC);
            arrayA = mergeY(arrayB, arrayC, arrayA);
        } // end of if statement
        return arrayA;
    }// end of mergeSortY
    
    /**
     * Method: mergeY
     * Purpose: merges multiple arrays to create one sorted array
     * @param array2 - small array
     * @param array3 - small array
     * @param array1 - larger array to be sorted by smaller arrays
     * @return array1 as a sorted array
     */
    public static Point[] mergeY(Point[] array2, Point[] array3, Point[] array1){
        int index1 = 0; // indexes to indicate where in each array the current point is
        int index2 = 0;
        int index3 = 0;
        while((index1 < array2.length)&&(index2 < array3.length)){
            if(array2[index1].getY() <= array3[index2].getY()){
                array1[index3] = array2[index1];
                index1++;
            } else {
                array1[index3] = array3[index2];
                index2++;
            }// end of if-else statements
            index3++;
        }// end of while loop
        if(index1 == array2.length){
            System.arraycopy(array3, index2, array1, index3, array3.length-index2);
        } else {
            System.arraycopy(array2, index1, array1, index3, array2.length-index1);
        }// end of if-else statements
        
        return array1;
    }// end of mergeSort
}// end of Control Class