import java.io.CharArrayReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
 * Created by mding on 2017-02-28.
 * Binary Image analysis using Connected Components, Union-find and Labelling Algorithms
 */
public class ConnectedComponentsImage {

    /* Attributes */
    private char[][] imageMatrix;
    private uandf<String> unionFind;
    private boolean imgIsScanned;
    private static char symbol = '+';
    private int setSize;
    private HashMap<Character,Integer> componentLabels;
    private LinkedHashMap<Character,Integer> orderedLabels;
    private boolean labelsAreCollected = false;
    private boolean labelsAreSorted = false;

    /* Constructor, creates character matrix to store image and new unionFind data structure */
    public ConnectedComponentsImage(int n) {
        this.setSize = 0;
        this.imgIsScanned = false;
        this.unionFind = new uandf<String>(n*n);
        imageMatrix = new char[n][n];
        componentLabels = new HashMap<Character,Integer>();
        orderedLabels = new LinkedHashMap<Character, Integer>();
    }

    /* Prints image as is without changes */
    public void printImageWithoutChanges() {
        if (imgIsScanned == true) { // checker to see if image put in
            for (int row = 0; row < imageMatrix.length; row++) {
                for (int col = 0; col < imageMatrix[0].length; col++) {
                    System.out.print(imageMatrix[row][col]);
                }
                System.out.print("\n");
            }
        } else {
            System.out.println("Image not scanned yet, do that first");
        }
    }

    /* Scans the image and then stores it in image matrix */
    public void scanImage() {
        File imageFile = new File("./girl.img");
        try {
            Scanner sc = new Scanner(imageFile);
            int rowCount = 0;
            while (sc.hasNextLine()) {
                String lineInput = sc.nextLine();
                for (int col = 0; col < lineInput.length(); col++) {
                    imageMatrix[rowCount][col] = lineInput.charAt(col);
                }
                rowCount++;
            }
            imgIsScanned = true;
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Image isn't scanned properly, for workaround, just stick image file in directory and put filename in code");
        }
    }

    /* Prints component list labels based on counter */
    public void printComponentList() {
        if (imgIsScanned == true) { // checker to see if image put in
            if (labelsAreCollected == true) { // checker to see if labels are in the system
                orderedLabels = sortLabelsInOrder();
                for (Map.Entry<Character, Integer> label : orderedLabels.entrySet()) {
                    System.out.println("Component Label: " + label.getKey() + ", size: " + label.getValue());
                }
            } else {
                System.out.println("Labels haven't even been put on yet, use printImgWithUniqueChars() method first");
            }
        } else {
            System.out.println("Image not scanned yet, do that first");
        }
    }

    /* Prints component list without those of size 1 and 2 */
    public void printComponentListWithoutSizesOfOneOrTwo() {
        if (imgIsScanned == true) {  // checker to see if image put in
            if (labelsAreCollected == true) { // checker to see if labels are in the system
                if (labelsAreSorted != true) { // sorts labels in case they weren't sorted
                    orderedLabels = sortLabelsInOrder();
                }
                for (Map.Entry<Character, Integer> label : orderedLabels.entrySet()) {
                    if (label.getValue() > 2) {
                        System.out.println("Component Label: " + label.getKey() + ", size: " + label.getValue());
                    }
                }
            } else {
                System.out.println("Labels haven't even been put on yet, use printImgWithUniqueChars() method first");
            }
        } else {
            System.out.println("Image not scanned yet, do that first");
        }
    }

    /* Sorts component labels by value using a LinkedHashMap */
    private LinkedHashMap<Character,Integer> sortLabelsInOrder() {
        //Transfer as List and sort it
        ArrayList<Map.Entry<Character, Integer>> compList = new ArrayList(componentLabels.entrySet());
        Collections.sort(compList, new Comparator<Map.Entry<Character, Integer>>(){
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        // Stores it as a linkedHashmap which can handle order
        LinkedHashMap<Character, Integer> result = new LinkedHashMap<Character,Integer>();
        for (Map.Entry<Character, Integer> entry : compList) {
            result.put( entry.getKey(), entry.getValue() );
        }
        labelsAreSorted = true;
        return result;
    }

    /* Prints image labelled with unique characters */
    public void printImgWithUniqueChars() {
        if (imgIsScanned == true) { // checker to see if image put in
            // Labels them depending on the component
            for (int row = 0; row < imageMatrix.length; row++) {
                for (int col = 0; col < imageMatrix[0].length; col++) {
                    if (imageMatrix[row][col] == symbol) {
                        placeTempLabel(row,col);
                    }
                }
            }
            setSize = unionFind.final_sets();
            System.out.println("Final_sets is called, number of sets : " + setSize);
            for (int row = 0; row < imageMatrix.length; row++) {
                for (int col = 0; col < imageMatrix[0].length; col++) {
                    if (imageMatrix[row][col] == symbol) {
                        System.out.print(printUniqCharHelper(row,col));
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.print("\n");
            }
        } else {
            System.out.println("Image not scanned yet, do that first");
        }
    }

    // Private helper method for printing
    private char printUniqCharHelper(int row, int col) {
        String identifier = Integer.toString(row) + "-" + Integer.toString(col);
        char uniqChar = ' ';
        for (int i = 0; i < setSize; i++) {
            if (unionFind.find_set(identifier).element.equals(unionFind.representative.get(i).element)) {
                uniqChar = (char) ((char) 97 + i); // Starts from a and adds the set label to it to get uniqChar
                // Saves into a hashtable that keeps count of each character and how many times they appear
                if (componentLabels.containsKey(uniqChar)) {
                    componentLabels.put(uniqChar, componentLabels.get(uniqChar) + 1);
                } else {
                    componentLabels.put(uniqChar,1);
                }
            }
        }
        labelsAreCollected = true;
        return uniqChar;
    }

    /* Places labels or makes sets depending on if the right or above is filled up */
    private void placeTempLabel(int row, int col) {
        // creates a new x-y coordinate
        String newCoord = Integer.toString(row) + "-" + Integer.toString(col);
        unionFind.make_set(newCoord);
        try { // The first index
            if (row == 0 && col == 0) {

            } else if (row == 0) { // handles the first row
                if (checkIfLeftIsSymbol(row, col) == true) {
                    String leftCoord = Integer.toString(row) + "-" + Integer.toString(col - 1);
                    unionFind.union_sets(newCoord, leftCoord);
                }
            } else if (col == 0) { // handles first column
                if (checkIfAboveIsSymbol(row, col) == true) {
                    String upperCoord = Integer.toString(row - 1) + "-" + Integer.toString(col);
                    unionFind.union_sets(newCoord, upperCoord);
                }
            } else { // handles normal case
                if (checkIfLeftIsSymbol(row, col) == true && checkIfAboveIsSymbol(row, col) == true) {
                    String leftCoord = Integer.toString(row) + "-" + Integer.toString(col - 1);
                    String upperCoord = Integer.toString(row - 1) + "-" + Integer.toString(col);
                    unionFind.union_sets(newCoord, leftCoord);
                    unionFind.union_sets(leftCoord, upperCoord);
                } else if (checkIfLeftIsSymbol(row, col) == true) {
                    String leftCoord = Integer.toString(row) + "-" + Integer.toString(col - 1);
                    unionFind.union_sets(newCoord, leftCoord);
                } else if (checkIfAboveIsSymbol(row, col) == true) {
                    String upperCoord = Integer.toString(row - 1) + "-" + Integer.toString(col);
                    unionFind.union_sets(newCoord, upperCoord);
                }
            }
        } catch (Exception e){
            System.out.println(newCoord);
        }
    }

    // Handles the first row and checks if the left is already part of a set
    private boolean checkIfLeftIsSymbol(int row, int col) {
        if (imageMatrix[row][col-1] == symbol) {
            return true;
        }
        return false;
    }

    // Handles the first column and checks if above is already part of a set
    private boolean checkIfAboveIsSymbol(int row, int col) {
        if (imageMatrix[row-1][col] == symbol) {
            return true;
        }
        return false;
    }
}
