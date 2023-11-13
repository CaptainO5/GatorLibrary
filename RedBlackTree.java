import java.util.*;
import java.lang.Math;

class RBTNode{
    public static final boolean BLACK = true, RED = false;

    Book book;
    boolean color; // color of the node (true -> black; false -> red)
    RBTNode left, right, parent;

    public RBTNode(Book b){
        book = b;
        color = RBTNode.RED; // Default the color to red
    }

    public RBTNode(Book b, boolean color){
        book = b;
        this.color = color;
    }

    public RBTNode(Book b, RBTNode parent){
        book = b;
        this.parent = parent;
        this.color = RBTNode.RED;
    }

    public int childCount(){
        int count = 0;
        if (left != null)
            count++;
        if (right != null)
            count++;
        return count;
    }

    public boolean isLeftChild(){
        return parent.left == this;
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

    /**
     * Search for the node with given key (book id)
     * 
     * @return the node if found or the parent of the node
     */
    private RBTNode search(int key){
        RBTNode p = head; // node to traverse and search the tree

        // Binary search tree traversal
        while (p != null){
            if (p.book.id == key)
                return p;
            else if (p.book.id > key){
                if (p.left == null)
                    return p;
                p = p.left;
            } else {
                if (p.right == null)
                    return p;
                p = p.right;
            }
        }

        // When head is null
        return p;
    }

    public void insert(Book book){
        // Search for the location to insert the book
        RBTNode found = search(book.id);

        // Insert the book in the correct location in the tree
        if (found == null)
            head = new RBTNode(book, RBTNode.BLACK);
        else if (found.book.id != book.id){
            RBTNode newNode = new RBTNode(book, found); // Set found as the parent
            if (found.book.id > book.id)
                found.left = newNode;
            else{
                found.right = newNode;
            }
        }

        // TODO check if the RBTree properties are met or make the necessary changes
    }

    public Book delete(int id){
        // if (head.book.id == id){
        //     Book book = head.book;
        //     head = null;
        //     return book;
        // }

        // RBTNode p = head; // node to traverse and search the tree

        // // Binary search tree traversal
        // while (p != null){
        //     if (p.book.id > id){
        //         if (p.left != null && p.left.book.id == id){

        //         }
        //         p = p.left;
        //     }
                
        //     else if (p.book.id < id){
        //         if (p.right == null){
        //             return null;
        //         }
        //         p = p.right;
        //     }
        // }
        // return null;
        return getBook(id);
    }

    public Book[] findNearest(int targetId){
        Book[] nearest = new Book[2];
        RBTNode p = head; // node to traverse and search the tree

        // Binary search tree traversal
        while (p != null){
            if (nearest[0] == null)
                nearest[0] = p.book;
            else {
                int min_dif = Math.abs(nearest[0].id - targetId);
                int dif = Math.abs(p.book.id - targetId);
                if (min_dif > dif)
                    nearest[0] = p.book;
                else if (min_dif == dif)
                    nearest[1] = p.book;
            }
            if (p.book.id > targetId)
                p = p.left;
            else
                p = p.right;
        }

        // When id is not in the tree
        return nearest;

    }

    /**
     * Search method to find the book with given id
     * @param id bookId to search in the tree
     * @return the book if found else return null
     */
    public Book getBook(int id){
        // Use internal search method to find the node with given bookId
        RBTNode found = search(id);

        // Return the book if found
        if (found != null && found.book.id == id)
            return found.book;

        // When id is not in the tree
        return null;
    }

    /**
     * Helper recursive method to get the relavent books
     * @param p Current head of the recursive tree
     * @param books Globsl list to store the results
     */
    private void getBooks(int id1, int id2, RBTNode p, List<Book> books){
        if (p == null)
            return;

        // Recursively traverse the tree in in-order and store the relavent books in the range
        getBooks(id1, id2, p.left, books);
        if (id1 <= p.book.id && p.book.id <= id2)
            books.add(p.book);
        getBooks(id1, id2, p.right, books);
    }

    /** 
     * Get the list of books in the range [id1, id2]
     * */ 
    public List<Book> getBooks(int id1, int id2){
        List<Book> books = new ArrayList<Book>();
        getBooks(id1, id2, this.head, books);
        return books;
    }

    public static void main(String[] args){
        Book b = new Book(1, "newBook", "someone", true);
        System.out.println(b);
    }
}
