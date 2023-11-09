public class Book{
    int id;
    String name;
    String authorName;
    boolean available;
    int borrowedBy;
    PriorityQueue reservationHeap;

    public Book(int bookId, String bookName, String authorName, boolean availabilityStatus){
        this.id = bookId;
        this.name = bookName;
        this.authorName = authorName;
        this.available = availabilityStatus;
        this.borrowedBy = -1;
        this.reservationHeap = new PriorityQueue();
    }

    public String toString(){
        String book = "";
        book += "BookId = " + id;
        book += "\nTitle = " + name;
        book += "\nAuthor = " + authorName;

        book += "\nAvailability = ";
        if (available)
            book += "\"Yes\"";
        else 
            book += "\"No\"";

        book += "\nBorrowedBy = ";
        if (borrowedBy == -1)
            book += "None";
        else
            book += borrowedBy;
        book += "\nReservations = " + reservationHeap;

        return book;
    }
}