import java.lang.*;

/**
 * Created by mding on 2017-03-28.
 * Heap abstract data type
 * - Takes array of elements and constucts a min-heap
 */
public class Heap<T> {
    // Attributes
    private int[] origArr;
    public int[] heapArr;
    private int maxSize;
    public int currSize;
    public int n;

    // Constructor, stores original array and initializes a new heapArr for use
    public Heap(int[] keys, int n) {
        // Leaves space for first value which is 0
        this.origArr = new int[n+1];
        for (int i = 1; i < origArr.length; i++) {
            this.origArr[i] = keys[i-1];
        }
        this.heapArr = new int[(2*n)];
        this.maxSize = n;
        this.currSize = n;
//        this.n = n;
    }

    //  Method to initialize a heap with the array keys of n elements indexed from 1
    //  to n, where key[i] is the key of the element whose id is i
    public void heap_ini(int[] keys, int n) {
        // Uses heapification, also is 0-based so reduces all counts by 1
        for (int i = n; i <= (2*n)-1; i++) {
            this.heapArr[i] = i - n + 1;
        }
        // Handles original array part of heap array
        for (int i = n-1; i >=1; i--) {
            if(this.origArr[this.heapArr[2*i]] < this.origArr[this.heapArr[(2*i)+1]]) {
                this.heapArr[i] = this.heapArr[2*i];
            } else {
                this.heapArr[i] = this.heapArr[(2*i)+1];
            }
        }
    }

    //  Returns true if the element whose id is id is in the heap
    public boolean in_heap(int id) {
        int elementKey = this.key(id);
        for (int i = 1; i < origArr.length; i++) {
            if (origArr[i] == elementKey) {
                return true;
            }
        }
        return false;
    }

    //  Returns the minimum key of the heap
    public int min_key() {
        return this.origArr[this.heapArr[1]];
    }

    //  Returns the id of the element with minimum key in the heap
    public int min_id() {
        return this.heapArr[1];
    }

    // Returns the key of the element whose id is id in the heap
    public int key(int id) {
        return this.origArr[id];
    }

    // Deletes the element with minimum key from the heap
    public int delete_min() {
        // Decrements current size
        this.currSize--;
        // Use max value that can be attained by integer instead of infinity
        this.origArr[0] = Integer.MAX_VALUE;
        // assigns this to a variable to simiplify things
        int newValue = this.heapArr[1] + this.maxSize - 1;
        this.heapArr[newValue] = 0;

        // sets the minimum to be returned
        int min = this.origArr[this.heapArr[1]];
        int index = newValue/2;

        // rearranges heap
        while (index >= 1) {
            if (this.origArr[this.heapArr[2*index]] < this.origArr[this.heapArr[(2*index) + 1]]) {
                this.heapArr[index] = this.heapArr[2*index];
            } else {
                this.heapArr[index] = this.heapArr[(2*index) + 1];
            }
            index = index/2;
        }

        // returns minimum if needed
        return min;
    }

    // sets the key of the element whose id is id to new key if its
    // current key is greater than new key.
    public void decrease_key(int id, int new_key) {
        if (this.key(id) > new_key) {
            this.origArr[id] = new_key;
            int i = (id + maxSize - 1)/2;
            // Runs heapify
            while (i >= 1) {
                if (this.origArr[this.heapArr[2*i]] < this.origArr[this.heapArr[(2*i)+1]]) {
                    this.heapArr[i] = this.heapArr[2*i];
                } else {
                    this.heapArr[i] = this.heapArr[(2*i) + 1];
                }
                i = i/2;
            }
        }
    }
}
