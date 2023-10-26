/**
 * Class to store the patron details
 * 
 * Stores the patronId, priorityNumber and Timestamp at which the request is raised.
 * Used in ReservationHeap priority-queue of Books.
 */
class Patron{
    public int id;
    public int priority;
    private long timeStamp;

    public Patron(int patronId, int priorityNumber){
        this.id = patronId;
        this.priority = priorityNumber;
        this.timeStamp = System.nanoTime(); // Take the timestamp at initialization.
    }

    /**
     * @return Timestamp of the Object creation
     * Used only for breaking any ties in the priority queue. 
     */
    public long getTimeStamp(){
        return this.timeStamp;
    }

    public String toString(){
        return "" + id + "\t" + priority + "\t" + timeStamp;
    }
}

/**
 * Class to implement Custom Priority Queue
 * 
 * Uses a Min heap with an array as the underlying data structue.
 * Supports put and pop operations:
 *      put(patronId, priorityNumber): Adds the patron to the queue (enqueue)
 *      pop():                         Returns the patronId with highest priority (dequeue)
 * Stores 20 patron-information at most.
 */
public class PriorityQueue {
    private Patron[] heap;
    private int len;

    public PriorityQueue(){
        heap = new Patron[20];
        len = 0;
    }

    // Help swap elements in heap array
    private void swap(int i, int j){
        Patron temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Insert element into the priority queue
    public void put(int patronId, int priorityNumber){
        if (this.len >= 20){
            System.err.println("Heap is full!");
            return;
        }
        Patron patron = new Patron(patronId, priorityNumber);
        heap[len] = patron;
        int current = len++;

        while (current > 0){
            int parent = (current - 1) / 2; // parent is always >= 0
            
            if (heap[parent].priority > heap[current].priority || 
                (heap[parent].priority == heap[current].priority && 
                    heap[parent].getTimeStamp() > heap[current].getTimeStamp())){
                // Swap if current priority is higher or timestamp is lower than the parent            
                swap(current, parent);
                current = parent;
            } else current = -1; // Come out of the loop
        }
    }

    /** 
     * Return the next element in the queue
     * @return patronId with higest priority
     */ 
    public int pop(){
        if (len <= 0){
            System.err.println("Heap is empty!");
            return -1;
        }

        int id = heap[0].id; // Store patron id with highest priority

        // Remove root and make the last element root
        heap[0] = null;
        swap(0, --len);

        int current = 0;
        while (current < len){
            int child1 = current * 2 + 1, 
                child2 = current * 2 + 2,
                smallest = current;
            
            // Find the smallest among the three
            if (child1 < len &&
                    (heap[child1].priority < heap[smallest].priority ||
                    (heap[child1].priority == heap[smallest].priority &&
                        heap[child1].getTimeStamp() < heap[smallest].getTimeStamp()))){
                smallest = child1;
            }
            if (child2 < len &&
                    (heap[child2].priority < heap[smallest].priority ||
                    (heap[child2].priority == heap[smallest].priority &&
                        heap[child2].getTimeStamp() < heap[smallest].getTimeStamp()))){
                smallest = child2;
            }

            // Swap the current element with the smallest and continue
            if (current != smallest) {
                swap(current, smallest);
                current = smallest;
            } else current = len; // Get out of the loop when there is no change
        }
        return id;
    }

    // Return size of the queue
    public int size(){
        return len;
    }

    /**
     * Test Priority Queue impelemntation
     */
    public static void main(String[] args){
        PriorityQueue pq = new PriorityQueue();
        System.out.println(pq.pop());
        pq.put(1, 20);
        pq.put(2, 10);
        pq.put(3, 1);
        pq.put(4, 50);
        pq.put(5, 3);
        pq.put(6, 1);
        pq.put(7, 25);
        System.out.println("size: " + pq.size());
        System.out.println(pq.pop());
        pq.put(8, 55);
        System.out.println(pq.pop());
        System.out.println(pq.pop());
        pq.put(9, 5);
        System.out.println("size: " + pq.size());
        System.out.println(pq.pop());
        System.out.println(pq.pop());
        System.out.println("size: " + pq.size());    
    }
}
