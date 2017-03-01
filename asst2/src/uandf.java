import java.util.HashSet;
import java.util.Iterator;
/**
 * Created by mding on 2017-02-28.
 * UNION FIND DATA STRUCTURE ADT
 * - CREATE TESTS AFTER
 */


/** Matrix positions  rows * cols, gonna have to use tree nodes **/

public class uandf {
    /* Attributes */
    private boolean finalSetsMethodIsCalled = false;
    private int[] representative;
    private int[] rank;

    // Constructor constructs an union-find data type with n elements, 1, 2,... ,n.
    public uandf(int n) {
        // If  n is less than or equal to zero, there is no point to the unionfind
        if (n <= 0) {
            try {
                throw new Exception("Needs more than 0 elements");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // initializes length of unionfind
            this.representative = new int[n];
            this.rank = new int[n];
        }
    }

    // creates a new set whose only member (and thus representative) is i.
    public void make_set(int i) {
        // if final_sets() is called, cant make any more changes
        if (this.finalSetsMethodIsCalled == false) {
            this.representative[i] = i; // assigns it to representative array
        } else {
            System.out.println("Final Sets Method is called, this method won't make new changes");
        }
    }

    // unites the dynamic sets that contains i and j, respectively, into a new
    // set that is the union of these two sets.
    public void union_sets(int i, int j) {
        // if final_sets() is called, cant make any more changes
        if (this.finalSetsMethodIsCalled == false) {
            // Implements union by rank method
            int firstRoot = find_set(i);
            int secRoot = find_set(j);

            // Just returns if they're equal because they have the same root
            if (firstRoot == secRoot) return;

            // Does the comparsion mentioned in our lecture notes to assign rank
            if (this.rank[firstRoot] > this.rank[secRoot]) {
                this.representative[secRoot] = firstRoot;
            } else if (this.rank[secRoot] > this.rank[firstRoot]) {
                this.representative[firstRoot] = secRoot;
            } else {
                // For the case when ranks of both sets are equal
                this.representative[firstRoot] = secRoot;
                this.rank[secRoot] = this.rank[secRoot] + 1;
            }
        } else {
            System.out.println("Final Sets Method is called, this method won't make new changes");
        }
    }

    // returns the representative of the set containing i
    public int find_set(int i) {
        int result = 0;
        try {
            return findSetHelper(i);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Not found
        }
        return result;
    }


    private int findSetHelper(int i) {
        // implements recursive find_set using path compression
        if (this.representative[i] != i) this.representative[i] = find_set(this.representative[i]); // keeps searching for representative
        return this.representative[i];
    }

    // returns the total number of current sets, finalizes the current sets (make set()
    // and union sets() will have no effect after this operation), and resets the representatives
    // of the sets so that integers from 1 to final sets() will be used as representatives.
    public int final_sets() {
        HashSet<Integer> sizeMap = new HashSet<Integer>();
        // Uses temporary hashset to check size of structure
        for (int i = 0; i < this.representative.length; i++) {
            if (!sizeMap.contains(this.representative[i])) {
                sizeMap.add(this.representative[i]);
            }
        }
        // and assigns final representative
        this.representative = getFinalRepArr(sizeMap);
        // Sets a flag so that make_set and union_sets cannot be called successfully
        this.finalSetsMethodIsCalled = true;
        return sizeMap.size();
    }

    // Private helper method to help me get final representative
    private int[] getFinalRepArr(HashSet<Integer> set) {
        int[] finalRep = new int[set.size()];
        Iterator<Integer> repIter = set.iterator();
        int count = 0;
        while (repIter.hasNext()) {
            finalRep[count] = repIter.next();
            count++;
        }
        return finalRep;
    }

}
