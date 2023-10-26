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
    public Patron[] heap;
    public int len;

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
            System.err.println("Heap is Full!");
            return;
        }
        Patron patron = new Patron(patronId, priorityNumber);
        heap[len] = patron;
        int current = len++;

        while (current > 0){
            int parent = (current - 1) / 2; // parent is always >= 0
            if ((heap[parent].priority > heap[current].priority) || 
                (heap[parent].priority == heap[current].priority && 
                    heap[parent].getTimeStamp() > heap[current].getTimeStamp())){
                // Swap if current priority is higher or timestamp is lower than the parent            
                swap(current, parent);
                current = parent;
            } else current = -1; // Come out of the loop
        }
    }

    // Return the next element in the queue
    public int pop(){
        if (len <= 0){
            System.err.println("Heap is Empty!");
            return -1;
        }
        len--;
        return heap[0].id;
    }

    /**
     * Test Priority Queue impelemntation
     */
    public static void main(String[] args){
        PriorityQueue pq = new PriorityQueue();
        pq.put(1, 20);
        pq.put(1, 10);
        pq.put(10, 1);
        pq.put(1, 50);
        pq.put(1, 3);
        pq.put(15, 1);
        
        for (Patron p: pq.heap){
            System.out.println(p);
        }        
    }
}
