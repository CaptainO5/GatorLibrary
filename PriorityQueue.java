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
}

/**
 * Class to implement Custom Priority Queue
 * 
 * Uses a Min heap with an array as the underlying data structue.
 * Supports put and pop operations:
 *      put(patronId, priorityNumber): Adds the patron to the queue (enqueue)
 *      pop():                         returns the patronId with highest priority (dequeue)
 * Stores 20 patron-information at most.
 */
public class PriorityQueue {
    public Patron[] heap;
    
    public PriorityQueue(){
        this.heap = new Patron[20];
    }
}
