/*
 * 
 */
package assignment_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author casch
 */
public class Control {
    public static Point[] points; // points taken directly from the file
    public static String spoints = "";
    
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
    
    public static double findClosestPair(Point[] P, Point[] Q, int count){
        double dMinSq = -1;
        if(P.length <= 3){
            double closest = -1;
            int indexP = 0;
            int indexQ = 0;
            
            for(indexP = 0; indexP < P.length; indexP++){
                for(indexQ = 0; indexQ < Q.length; indexQ++){
                    if(!P[indexP].equals(Q[indexQ])){
                        double pair = (Math.pow(P[indexP].getX()-Q[indexQ].getX(), 2)-
                                Math.pow(P[indexP].getY()-Q[indexQ].getY(), 2));
                        if(pair < closest || closest == -1){
                            closest = pair;
                        }
                    }
                }
            }
            
            return closest;
        } else {
            int end = (int) Math.ceil((double) P.length/2.0)-1;
            Point[] Pleft = copyPartialArray(P, 0, end);
            Point[] Qleft = new Point[Pleft.length];
            int add = 0;
            for(int index = 0; index < Pleft.length; index++){
                for(int check = 0; check < Q.length; check++){
                    if(Q[check].equals(Pleft[index])){
                        Qleft[add] = Pleft[index];
                        add++;
                        check = Q.length;
                    } // end of if statement
                } // end of for loop
            }// end of for loop
            Point[] Pright = copyPartialArray(P, end+1, P.length-1);
            Point[] Qright = new Point[Pright.length];
            add = 0;
            for(int index = 0; index < Pright.length; index++){
                for(int check = 0; check < Q.length; check++){
                    if(Q[check].equals(Pleft[index])){
                        Qright[add] = Pright[index];
                        add++;
                        check = Q.length;
                    } // end of if statement
                } // end of for loop
            }// end of for loop
            
            double dleft = findClosestPair(Pleft, Qleft, count+1);
            double dright = findClosestPair(Pright, Qright, count+1);
            
            double d = Math.min(dleft, dright);
            int mid = Pleft[Pleft.length-1].getX();
            int num = 0;
            Point[] S = new Point[Q.length];
            for(int index = 0; index < Q.length; index++){
                if(Math.abs(Q[index].getX()-mid) < d){
                    S[num] = Q[index];
                    num++;
                }
            }// end if for loop
            dMinSq = Math.pow(d, 2);
            
            for(int index = 0; index < num-1; index++){
                int k = index + 1;
                double y_diff = S[k].getY() - S[index].getY();
                while((k <= num-1) && Math.pow( y_diff, 2) < dMinSq){
                    double x_diff = S[k].getX() - S[index].getX();
                    dMinSq = Math.min(Math.pow(x_diff, 2) + Math.pow(y_diff, 2), dMinSq);
                    
                    if(count == 0)
                        spoints = S[k].toString() + ", " + S[index].toString();
                    
                    k++;
                }// end of while loop
            } // end of for loop
        } // end of if-else statements
        return Math.sqrt(dMinSq);
    }// end of findClosestPair
    
    private static Point[] copyPartialArray(Point[] array, int start, int end){
        Point[] ret = new Point[(end-start)+1];
        int index2 = 0;
        for(int index = start; index <= end; index++){            
            ret[index2] = array[index];
            index2++;
        }
        return ret;
    }
//======================Sorting by X value======================================
       
    public static Point[] quickSortX(Point[] array, int left, int right){
        if(left < right){
            int split = hoarePartitioningX(array, left, right);
            quickSortX(array, left, split-1);
            quickSortX(array, split+1, right);
        }
        
        return array;
    }// end of quickSort
    
    private static int hoarePartitioningX(Point[] array, int left, int right){
        int tnum = array[left].getX(); // Pivot selected as the first element in the array list
        int numl = left;
        int numr = right;
        
        do{
            do{numl++;}while((numl < array.length-1) && (array[numl].getX() < tnum));
            do{numr--;}while((numr > 0) && (numr > array.length) && (array[numr].getX() > tnum));
            //Swap array[numl] with array[numr]
            Point temp = array[numl];
            array[numl] = array[numr];
            array[numr] = temp;
            
        }while(numl < numr);
        
        //Swap array[numl] with array[numr] (undo last swap when i>=j)
        Point temp = array[numl];
        array[numl] = array[numr];
        array[numr] = temp;
        //Swap array[left] with array[numr]
        temp = array[left];
        array[left] = array[numr];
        array[numr] = temp;
        
        return numr;
    }
    //=====================Sorting by y value=======================================
    public static Point[] mergeSortY(Point[] array){
        if(array.length > 1) {
            int N1 = (int) Math.floor(array.length/2);
            int N2 = array.length-N1;
            Point[] B = new Point[N1]; System.arraycopy(array, 0, B, 0, N1);
            Point[] C = new Point[N2]; System.arraycopy(array, N1, C, 0, N2);
            B = mergeSortY(B);
            C = mergeSortY(C);
            array = mergeY(B, C, array);
        }
        return array;
    }
    
    public static Point[] mergeY(Point[] array2, Point[] array3, Point[] array1){
        int index1 = 0;
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
}