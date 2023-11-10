import java.util.*;
import java.lang.Math;

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

    public void insert(Book book){
        RBTNode newNode = new RBTNode(book);

        if (head == null)
            head = newNode;

        RBTNode p = head; // node to traverse and search the tree

        // Binary search tree traversal
        while (p != null){
            if (p.book.id == book.id)
                return;
            else if (p.book.id > book.id){
                if (p.left == null){
                    p.left = newNode;
                    return;
                }
                p = p.left;
            }
                
            else{
                if (p.right == null){
                    p.right = newNode;
                    return;
                }
                p = p.right;
            }
        }
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
