import java.util.*;

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

    public Book getBook(int id){
        RBTNode p = head; // node to traverse and search the tree

        // Binary search tree traversal
        while (p != null){
            if (p.book.id == id)
                return p.book;
            else if (p.book.id > id)
                p = p.left;
            else
                p = p.right;
        }

        // When id is not in the tree
        return null;
    }

    /**
     * Recursively traverse the tree in in-order and store the relavent books @param books list
     */
    private void getBooks(int id1, int id2, RBTNode p, List<Book> books){
        if (p == null)
            return;

        getBooks(id1, id2, p.left, books);
        if (id1 <= p.book.id && p.book.id <= id2)
            books.add(p.book);
        getBooks(id1, id2, p.right, books);
    }

    /** 
     * Get the list of books in the range [id1, id2]
     * */ 
    public Book[] getBooks(int id1, int id2){
        List<Book> books = new ArrayList<Book>();
        getBooks(id1, id2, this.head, books);
        return (Book[]) books.toArray();
    }

    public static void main(String[] args){
        Book b = new Book(1, "newBook", "someone", true);
        System.out.println(b);
    }
    
}
