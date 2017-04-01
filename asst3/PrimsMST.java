import java.util.*;
import java.io.*;

/**
 * Created by mding on 2017-03-28.
 */
public class PrimsMST {
    // Attributes
    private HashMap<Integer, ArrayList<Integer>> adjList;
    private HashMap<Integer,Node> vertices;
    private HashMap<String, Integer> weightList;
    public int numOfVertices;

    // Constructor, creates a prim's algorithm with a set fixed size
    public PrimsMST(int n) {
        this.vertices = new HashMap<Integer,Node>(n);
        this.numOfVertices = n;
        this.adjList = new HashMap<Integer, ArrayList<Integer>>();
        this.weightList = new HashMap<String, Integer>();
    }

    // Adds to vertices, sets first node key to 0
    private void addToVertices(Node node, int n) {
        if (this.vertices.size() == 0) {
            node.key = 0;
            this.vertices.put(n,node);
        } else if (!this.vertices.containsKey(n)) {
            this.vertices.put(n,node);
        }
    }

    // Adds to adjacency list
    private void addToAdjList(int first, int second) {
        // Adds first node
        if (this.adjList.containsKey(first)) {
            ArrayList firstNeighbours = this.adjList.get(first);
            firstNeighbours.add(second);
            this.adjList.put(first,firstNeighbours);
        } else {
            ArrayList<Integer> newNodeList = new ArrayList<Integer>();
            newNodeList.add(second);
            this.adjList.put(first,newNodeList);
        }
        // Adds second node
        if (this.adjList.containsKey(second)) {
            ArrayList secNeighbours = this.adjList.get(second);
            secNeighbours.add(first);
            this.adjList.put(second, secNeighbours);
        } else {
            ArrayList<Integer> newNodeList = new ArrayList<Integer>();
            newNodeList.add(first);
            this.adjList.put(second,newNodeList);
        }
    }

    // Adds edge to the adjacency list
    private void addEdge(int first, int second, int weight) {
        // Puts it into weightlist
        String edgeName = Integer.toString(first) + "-" + Integer.toString(second);
        this.weightList.put(edgeName, weight);
        // Creates nodes
        Node firstNode = new Node(first);
        Node secNode = new Node(second);
        // Adds to graph if not already there
        addToVertices(firstNode,first);
        addToVertices(secNode,second);
        // Adds to adjacency lists
        addToAdjList(first,second);
    }

    // Private method to check if edge is in list
    private String checkEdgeExists(int firstNode, int secondNode) {
        String firstEdgeKey = Integer.toString(firstNode) + "-" + Integer.toString(secondNode);
        String secEdgeKey = Integer.toString(secondNode) + "-" + Integer.toString(firstNode);
        if (this.weightList.containsKey(firstEdgeKey)) {
            return firstEdgeKey;
        } else if (this.weightList.containsKey(secEdgeKey)) {
            return secEdgeKey;
        }
        return null;
    }

    // Prints out all edges in adjacency list form using our weightlist
    private void printAllEdges() {
        System.out.println("Prints adjacency list");
        System.out.println("----------------------------");
        System.out.println("Edges are written as node a - node b where '-' represents link or edge between two nodes");
        for (Map.Entry<String, Integer> entry : weightList.entrySet()) {
            System.out.println("Edge: " + entry.getKey() + " | Weight: " + entry.getValue());
        }
    }

    // Stores into adjacencyList all the edges from input graph
    private static PrimsMST storeEdges() {
        PrimsMST primsAlgo = null;
        // Handles IO
        File inputGraph = new File("./mst_graph1.txt"); // Finds file, change path here to change
        // Try-catch statement handles reading file
        try {
            Scanner input = new Scanner(inputGraph);
            int numOfVertices = input.nextInt();
            primsAlgo = new PrimsMST(numOfVertices);
            // Keeps reading input
            while (input.hasNextLine()) {
                int first = input.nextInt();
                int sec = input.nextInt();
                int weight = input.nextInt();
                primsAlgo.addEdge(first,sec,weight);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not available, try storing it in local directory as mst_graph1.txt");
        }
        return primsAlgo;
    }

    // Generates keys from nodes in graph based on their names
    private int[] generateKeys(int n) {
        int[] keys = new int[n];
        int i = 0;
        for (Map.Entry<Integer,Node> entry : this.vertices.entrySet()) {
            keys[i] = entry.getKey();
            i++;
        }
        return keys;
    }

    // Gets the weight between two nodes
    private int getWeight(int first, int sec) {
        String edgeName = checkEdgeExists(first, sec);
        int edgeWeight = 0;
        // Searchs through preset hashmap for weights
        if (edgeName != null) {
            edgeWeight = this.weightList.get(edgeName);
        }
        return edgeWeight;
    }

    // Method to run Prims algorithm based on lecture notes
    private void implementPrims(Heap<Integer> minHeap) {
        // Keeps going while minHeap hasn't reached 0
        while (minHeap.currSize != 0) {
            // Extracts min and uses lecture notes syntax
            int u = minHeap.delete_min();

            // Gos through all vertices adjacent to min
            ArrayList<Integer> adjacentNodes = this.adjList.get(u);
            for (int v : adjacentNodes) {
                // Updates the key
                if (minHeap.in_heap(v) && getWeight(u,v) < this.vertices.get(v).key) {
                    Node vNode = this.vertices.get(v);
                    Node uNode = this.vertices.get(u);
                    int newKey = getWeight(u,v);
                    vNode.pi = uNode;
                    vNode.key = newKey;
                    this.vertices.put(v,vNode);
                    minHeap.decrease_key(v,newKey);
                }
            }

        }
    }

    // Prints Edges making up Minimum spanning trees
    private void printMSTEdges() {
        System.out.println("Printing MST Edges");
        System.out.println("----------------------------");
        System.out.println("Edges are written as node a - node b where '-' represents link or edge between two nodes");

        // Iterates through all nodes looking for nodes with pis
        for (Map.Entry<Integer,Node> entry : this.vertices.entrySet()) {
            if (entry.getValue().pi != null) {
                int first = entry.getKey();
                int sec = entry.getValue().pi.name;
                String edgeName = checkEdgeExists(first,sec);
                if (edgeName != null) {
                    System.out.println("Edge: " + edgeName + " | Weight: " + this.weightList.get(edgeName));
                } else {
                    System.out.println("Error: Not able to find an edge that should be existing");
                }
            }
        }

    }

    // Main method pulls in the input, and then output both adjacency list and MST
    public static void main(String[] args) {
        // Handles IO and stores all edges
        PrimsMST primsAlgo = storeEdges();

        // Prints out all edges in Adjacency List form
        primsAlgo.printAllEdges();

        // Creates keys, set first node's to 0 and others are preset with Integer.MAX_VALUE
        int[] keys = primsAlgo.generateKeys(primsAlgo.numOfVertices);

        // Creates min heap
        Heap<Integer> min_heap = new Heap<Integer>(keys, primsAlgo.numOfVertices); // Creates a min_heap for handling Prim's algorithm
        min_heap.heap_ini(keys, primsAlgo.numOfVertices);
        primsAlgo.implementPrims(min_heap);

        // Prints out Min. Spanning Tree Edges after Prim's algorithm
        System.out.println("\n");
        primsAlgo.printMSTEdges();
    }
}
