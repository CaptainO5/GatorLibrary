class Book{
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

class RBTNode{
    public static final boolean BLACK = true, RED = false;

    Book book;
    boolean color; // color of the node (true -> black; false -> red)
    RBTNode left, right;

    public RBTNode(Book b){
        book = b;
        color = RBTNode.BLACK; // Default the color to black
    }

    public RBTNode(Book b, boolean color){
        book = b;
        this.color = color;
    }

    public void flipColor(){
        /* XOr to flip the node color
        *  Equivalent of color = !color
        */
        color ^= true;
    }
}

public class RedBlackTree {

    public RBTNode head;
    private int colorFlipCount; // Variable to store the color flip count

    public RedBlackTree(){
        head = null;
        colorFlipCount = 0;
    }

    public int getColoFlipCount(){
        return colorFlipCount;
    }

    public static void main(String[] args){
        Book b = new Book(1, "newBook", "someone", true);
        System.out.println(b);
    }
    
}
