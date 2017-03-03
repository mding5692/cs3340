import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
/**
 * Created by mding on 2017-02-28.
 * UNION FIND DATA STRUCTURE ADT
 *
 */

/** Matrix positions  rows * cols, gonna have to use tree nodes **/

public class uandf<T> {
    /* Attributes */
    private boolean finalSetsMethodIsCalled = false;
    public ArrayList<UFNode> representative;

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
            this.representative = new ArrayList<>(n);
        }
    }

    // creates a new set whose only member (and thus representative) is i.
    public void make_set(T i) {
        // if final_sets() is called, cant make any more changes
        if (this.finalSetsMethodIsCalled == false) {
            UFNode<T> newNode = new UFNode<T>(i);
            newNode.parent = newNode; // At the start, assigns newNode's parent to itself
            representative.add(newNode);
        } else {
            System.out.println("Final Sets Method is called, this method won't make new changes");
        }
    }

    // unites the dynamic sets that contains i and j, respectively, into a new
    // set that is the union of these two sets.
    public void union_sets(T i, T j) {
        // if final_sets() is called, cant make any more changes
        if (this.finalSetsMethodIsCalled == false) {
            // Implements union by rank method
            UFNode<T> firstRoot = find_set(i);
            UFNode<T> secRoot = find_set(j);

            // Just returns if they're equal because they have the same root
            if (firstRoot.element.equals(secRoot.element)) return;

            // Does the comparsion mentioned in our lecture notes to assign rank
            if (firstRoot.rank > secRoot.rank) {
                secRoot.parent = firstRoot;
            } else if (secRoot.rank > firstRoot.rank) {
                firstRoot.parent = secRoot;
            } else {
                // For the case when ranks of both sets are equal
                firstRoot.parent = secRoot;
                secRoot.rank = secRoot.rank + 1;
            }
        } else {
            System.out.println("Final Sets Method is called, this method won't make new changes");
        }
    }

    // returns the representative of the set containing i
    public UFNode<T> find_set(T i) {
        UFNode<T> result = null;
        for (int j = 0; j < representative.size(); j++) {
            if (representative.get(j).element.equals(i)) result = findSetHelper(representative.get(j));
        }
        if (result == null) {
            try {
                throw new Exception("No such element found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // Helper method that searches for the root of a tree/set
    private UFNode<T> findSetHelper(UFNode<T> node) {
        // keeps recursing unless parent == node which means it points to itself
        if (!node.parent.element.equals(node.element)) {
            return findSetHelper(node.parent);
        }
        return node;
    }

    // returns the total number of current sets, finalizes the current sets (make set()
    // and union sets() will have no effect after this operation), and resets the representatives
    // of the sets so that integers from 1 to final sets() will be used as representatives.
    public int final_sets() {
        // Pushes all the roots from 0 to final_sets()
        int count = 0;
        for (int i = 0; i < representative.size(); i++) {
            if (representative.get(i).parent.element.equals(representative.get(i).element)) {
                representative.add(0,representative.get(i));
                representative.remove(i+1);
                count++;
            }
        }
        // Sets a flag so that make_set and union_sets cannot be called successfully
        this.finalSetsMethodIsCalled = true;
        return count;
    }
}
