import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Created by mding on 2017-02-28.
 */
public class ConnectedComponentsImage {

    /* Attributes */
    private char[][] imageMatrix;
    private char[][] origMatrix;
    private uandf<String> unionFind;
    private boolean imgIsScanned;
    private static char symbol = '+';

    /* Constructor, creates character matrix to store image and new unionFind data structure */
    public ConnectedComponentsImage(int n) {
        this.imgIsScanned = false;
        this.unionFind = new uandf<String>(n*n);
        imageMatrix = new char[n][n];
        origMatrix = new char[n][n];
    }

    /* Prints image as is without changes */
    public void printImageWithoutChanges() {
        resetMatrix(); // Resets imageMatrix in case of new changes
        if (imgIsScanned == true) {
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
            origMatrix = imageMatrix;
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* Prints image labelled with unique characters */
    public void printImgWithUniqueChars() {
        resetMatrix(); // Resets imageMatrix in case of new changes
        if (imgIsScanned == true) {
            // Labels them depending on the component
            for (int row = 0; row < imageMatrix.length; row++) {
                for (int col = 0; col < imageMatrix[0].length; col++) {
                    if (origMatrix[row][col] == symbol) {
                        placeTempLabel(row,col);
                    }
                }
            }
            System.out.println(unionFind.final_sets());
        } else {
            System.out.println("Image not scanned yet, do that first");
        }
    }

    // Resets matrix after using different commands
    private void resetMatrix()  {
        this.imageMatrix = this.origMatrix;
    }

    /* Places labels or makes sets depending on if the right or above is filled up */
    private void placeTempLabel(int row, int col) {
        // creates a new x-y coordinate
        String newCoord = Integer.toString(row) + "-" + Integer.toString(col);
        // The first index
        try {
            if (row == 0 && col == 0) {
                unionFind.make_set(newCoord);
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
                } else {
                    unionFind.make_set(newCoord);
                }
            }
        } catch (Exception e){
            System.out.println(newCoord);
        }
    }

    // Handles the first row and checks if the left is already part of a set
    private boolean checkIfLeftIsSymbol(int row, int col) {
        if (origMatrix[row][col-1] == symbol) {
            return true;
        }
        return false;
    }

    // Handles the first column and checks if above is already part of a set
    private boolean checkIfAboveIsSymbol(int row, int col) {
        if (origMatrix[row-1][col] == symbol) {
            return true;
        }
        return false;
    }
}
