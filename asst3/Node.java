/**
 * Created by mding on 2017-03-29.
 * Node in the graph, stores generic type
 */
public class Node {
    // Attributes
    public int key;
    public int name;
    public Node pi;

    // Constructor, takes in a key
    public Node(int i) {
        this.name = i;
        this.key = Integer.MAX_VALUE;
        this.pi = null;
    }
}
