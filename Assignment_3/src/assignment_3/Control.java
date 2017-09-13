/*
 * 
 */
package assignment_3;

/**
 *
 * @author casch
 */
public class Control {
    private static Point[] points; // points taken directly from the file
    
    public static void readInputFile(String filename){
        
    }// end of readInputFile
    
    public static String findClosestPair(Point[] P, Point[] Q){
        //BruteForce
        
        //Actual Algorithm
        return null;
    }// end of findClosestPair
    
//======================Sorting by X value======================================
    public static void mergeSortX(Point[] array2, Point[] array3, Point[] array1){
        int index1 = 0;
        int index2 = 0;
        int index3 = 0;
        while((index1 < array2.length)&&(index2 < array3.length)){
            if(array2[index1].getX() <= array3[index2].getX()){
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
    }// end of mergeSort
    
    public static void quickSortX(Point[] array, int left, int right){
        if(left < right){
            int split = hoarePartitioningX(array, left, right);
            quickSortX(array, left, split-1);
            quickSortX(array, split+1, right);
        }
    }// end of quickSort
    
    private static int hoarePartitioningX(Point[] array, int left, int right){
        int tnum = array[left].getX(); // Pivot selected as the first element in the array list
        int numl = left;
        int numr = right+1;
        
        do{
            do{numl++;}while((numl < array.length-1) && (array[numl].getX() < tnum));
            do{numr--;}while((numr > 0) && (array[numr].getX() > tnum));
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
}

//=====================Sorting by Y value=======================================
