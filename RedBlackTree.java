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

    public int getRedChildCount(){
        int count = 0;
        if (left != null && left.color == RBTNode.RED)
            count++;
        if (right != null && right.color == RBTNode.RED)
            count++;
        return count;
    }

    // Check if the current node is the left child of parent
    public boolean isLeftChild(){
        return parent.left == this;
    }

    public void flipColor(){
        /* XOr to flip the node color
        *  Equivalent of color = !color
        */
        color ^= true;
    }

    // Print Id and color of the node
    public String toString(){
        return book.id + "(" + (color?"B":"R") + ")";
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

    private void rotateCaseRR(RBTNode n){
        RBTNode m = n.right;

        n.right = m.left;
        m.left = n;

        if (n.right != null)
            n.right.parent = n;

        m.parent = n.parent;
        if (n == head)
            head = m;
        else if (n.isLeftChild())
            m.parent.left = m;
        else
            m.parent.right = m;

        n.parent = m;
    }

    private void rotateCaseLL(RBTNode n){
        RBTNode m = n.left;

        n.left = m.right;
        m.right = n;

        if (n.left != null)
            n.left.parent = n;

        m.parent = n.parent;
        if (n == head)
            head = m;
        else if (n.isLeftChild())
            m.parent.left = m;
        else
            m.parent.right = m;

        n.parent = m;
    }

    private void rotateCaseLR(RBTNode n){
        rotateCaseRR(n.left);
        rotateCaseLL(n);
    }

    private void rotateCaseRL(RBTNode n){
        rotateCaseLL(n.right);
        rotateCaseRR(n);
    }

    // Check and fix the RedBlack Tree property on insert
    private void balanceAfterInsert(RBTNode p){
        RBTNode pp = p.parent;

        // if p is the head
        if (pp == null){
            p.color = RBTNode.BLACK;
            colorFlipCount++;
            return;
        }

        if (pp.color == RBTNode.BLACK)
            return;

        RBTNode gp = pp.parent; // Not NULL since p.parent != head

        // Case XYr: when both the children of gp are red
        if (gp.getRedChildCount() == 2){
            gp.flipColor();
            gp.left.flipColor();
            gp.right.flipColor();
            colorFlipCount += 3;
            balanceAfterInsert(gp);
            return;
        }
        // Csse LLb: when p and pp are left children
        if (p.isLeftChild() && pp.isLeftChild()){
            pp.flipColor();
            gp.flipColor();
            colorFlipCount += 2;
            rotateCaseLL(gp);
            return;
        }
        // Case RRb: when p and pp are right children
        if (!p.isLeftChild() && !pp.isLeftChild()){
            pp.flipColor();
            gp.flipColor();
            colorFlipCount += 2;
            rotateCaseRR(gp);
            return;
        }
        // Casr LRb: when p is the right child and pp is the left child
        if (!p.isLeftChild() && pp.isLeftChild()){
            p.flipColor();
            gp.flipColor();
            colorFlipCount += 2;
            rotateCaseLR(gp);
        } 
        // Case RLb: when p is the left child and pp is the right child
        else{
            p.flipColor();
            gp.flipColor();
            colorFlipCount += 2;
            rotateCaseRL(gp);
        }
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

            // Check if the RBTree properties are met or make the necessary changes
            balanceAfterInsert(newNode);
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

    private void printTree(RBTNode head){
        if (head == null)
            return;
        RBTNode p = head;
        printTree(p.left);
        System.out.print(p + " ");
        printTree(p.right);
    }

    // Print in-order traversal of the tree (With colors)
    public void printTree(){
        printTree(head);
        System.out.println("");
    }

    // Test the working of RB-Tree
    public static void main(String[] args){
        RedBlackTree rbt = new RedBlackTree();
        rbt.insert(new Book(2, "newBook", "someone", true));
        rbt.insert(new Book(1, "newBook", "someone", true));
        rbt.insert(new Book(3, "newBook", "someone", true));
        rbt.printTree();
        rbt.insert(new Book(4, "newBook", "someone", true));
        rbt.printTree();
        rbt.insert(new Book(0, "newBook", "someone", true));
        rbt.printTree();
        rbt.insert(new Book(5, "newBook", "someone", true));
        rbt.printTree();
        rbt.insert(new Book(25, "newBook", "someone", true));
        rbt.printTree();
        rbt.insert(new Book(20, "newBook", "someone", true));
        rbt.printTree();
        rbt.insert(new Book(7, "newBook", "someone", true));
        rbt.printTree();
    }
}
