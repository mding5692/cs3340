/**
 * Created by mding on 2017-02-28.
 */
public class testUnionFind {

    public static void main(String [] args) {
        // Create new union find data structure:
        uandf testConstructor = new uandf(100);
        // makes sets from 0 to 99
        for (int i = 0; i < 100; i++) {
            testConstructor.make_set(i);
        }
//
        for (int i = 0; i < 99; i+=2) {
            testConstructor.union_sets(i, i+1);
        }

        /* Tests finalsets method */
        int finalSize = testConstructor.final_sets();
        System.out.println(finalSize);

        testConstructor.union_sets(10,11);
        System.out.println("Final Sets called");
        for (int i = 0; i < finalSize; i++) {
            System.out.println(testConstructor.find_set(i));
        }
    }
}
