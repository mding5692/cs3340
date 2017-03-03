/**
 * Created by mding on 2017-02-28.
 * Used to test unionfind method, don't run if not testing
 */
public class testUnionFind {

    public static void main(String [] args) {
        // Create new union find data structure:
        uandf testConstructor = new uandf(5);

        String[] testStrs = new String[]{"17-18", "19-20","20-20","31-40","50-10"};



        // make_sets
        for (int i = 0; i < testStrs.length; i++) {
            testConstructor.make_set(testStrs[i]);
        }

        testConstructor.union_sets("17-18","19-20");
        System.out.println(testConstructor.find_set("17-18"));
        testConstructor.union_sets("20-20","31-40");
        System.out.println("before final: " + testConstructor.find_set("17-18"));
        System.out.println(testConstructor.final_sets());
        System.out.println(testConstructor.find_set("17-18"));
        System.out.println("-------");
        for (int i = 0; i < testStrs.length; i++) {
            System.out.println(testConstructor.find_set(testStrs[i]));
        }
    }
}
