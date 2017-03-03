/**
 * Created by mding on 2017-03-02.
 * Main method for finding Connected Components Analysis on binary image
 * Run this for the final program
 */
public class FindConnectedComp {

    /* Main method for answering second part of Q10 */
    public static void main(String [] args) {
        System.out.println("Will read image from ./girl.img file unless you specify not to");
        ConnectedComponentsImage newImage = new ConnectedComponentsImage(71);
        newImage.scanImage(); // Scans the image from local ./girl.img file in directory
        printMsg("Q10b1) Printing input binary image:");
        newImage.printImageWithoutChanges();
        printMsg("Q10b2) Printing unique characters:");
        newImage.printImgWithUniqueChars();
        printMsg("Q10b3) Ranked component list:");
        newImage.printComponentList();
        printMsg("Q10b4) Ranked component list w/o sizes 1 or 2:");
        newImage.printComponentListWithoutSizesOfOneOrTwo();
    }

    // Helper method for just printing lines between different questions
    private static void printMsg(String msg) {
        System.out.println("");
        System.out.println(msg);
        System.out.println("-----------------");
        System.out.println("");
    }

}
