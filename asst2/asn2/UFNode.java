import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
/**
 * Created by mding on 2017-03-02.
 * For Union-Find Data Structure and to create trees to hold connected components
 */

public class UFNode<T> {
    /* Attributes */
    public UFNode<T> parent; // trees need to access parent
    public int rank; // records the rank of the node
    public T element; // stores the element

    // Constructor for Nodes in Union Find Data structure
    public UFNode(T element) {
        this.element = element;
        this.rank = 0;
    }

}
