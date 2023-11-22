/**
 * Class to maintain the details of a book in the library
 */
public class Book{
    public int id;
    private String name;
    private String authorName;
    public boolean available;
    public int borrowedBy;
    public PriorityQueue reservationHeap;

    public Book(int bookId, String bookName, String authorName, boolean availabilityStatus){
        this.id = bookId;
        this.name = bookName;
        this.authorName = authorName;
        this.available = availabilityStatus;
        this.borrowedBy = -1;
        this.reservationHeap = new PriorityQueue();
    }

    /**
     * Print the contents of the book object according to the specification
     *  */ 
    public String toString(){
        String book = "";
        book += "BookID = " + id;
        book += "\nTitle = " + name;
        book += "\nAuthor = " + authorName;
        book += "\nAvailability = " + (available?"\"Yes\"":"\"No\"");
        book += "\nBorrowedBy = " + (borrowedBy == -1?"None":borrowedBy);
        book += "\nReservations = [" + reservationHeap + "]";

        return book;
    }
}