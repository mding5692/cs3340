/**
 * Created by mding on 2017-03-02.
 * Main method for finding Connected Components Analysis on binary image
 * Run this for the final program
 */
public class FindConnectedComp {

    public static void main(String [] args) {
        System.out.println("Will read image from ./girl.img file unless you specify not to");
        ConnectedComponentsImage newImage = new ConnectedComponentsImage(71);
        newImage.scanImage();
        printMsg("Q10b1) Printing input binary image:");
        newImage.printImageWithoutChanges();
        printMsg("Q10b2) Printing unique characters:");
        newImage.printImgWithUniqueChars();
    }

    public static void printMsg(String msg) {
        System.out.println("");
        System.out.println(msg);
        System.out.println("-----------------");
        System.out.println("");
    }

}
