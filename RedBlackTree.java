import java.util.ArrayList;
import java.util.List;

/**
 * Class to store the necessary node information for the tree
 */
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

    // Helper to get the number of children of a node
    public int childCount(){
        int count = 0;
        if (left != null)
            count++;
        if (right != null)
            count++;
        return count;
    }

    // Helper to get the number of red children of a node
    public int redChildCount(){
        int count = 0;
        if (left != null && left.color == RBTNode.RED)
            count++;
        if (right != null && right.color == RBTNode.RED)
            count++;
        return count;
    }

    // Helper to check if the current node is the left child of parent
    public boolean isLeftChild(){
        return parent.left == this;
    }

    /* XOr to flip the node color
    *  Equivalent of color = !color
    */
    public void flipColor(){
        color ^= true;
    }

    // Print Id and color of the node
    public String toString(){
        return book.id + "(" + (color?"B":"R") + ")";
    }
}

/**
 * Class to implement a custom Red Black Tree
 */
public class RedBlackTree {
    public RBTNode root;
    private int colorFlipCount; // Variable to store the color flip count

    public RedBlackTree(){
        root = null;
        colorFlipCount = 0;
    }

    // Return color flip count
    public int getColorFlipCount(){
        return colorFlipCount;
    }

    /**
     * Search for the node with given key (book id)
     * 
     * @return the node if found or the parent of the node
     */
    private RBTNode search(int key){
        RBTNode p = root; // node to traverse and search the tree

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

        // When root is null
        return p;
    }

    /**
     * Helper to left rotate around the node @param n
     *  */ 
    private void rotateCaseRR(RBTNode n){
        RBTNode m = n.right;

        n.right = m.left;
        m.left = n;

        if (n.right != null)
            n.right.parent = n;

        m.parent = n.parent;
        if (n == root)
            root = m;
        else if (n.isLeftChild())
            m.parent.left = m;
        else
            m.parent.right = m;

        n.parent = m;
    }

    /**
     * Helper to right rotate around the node @param n
     */ 
    private void rotateCaseLL(RBTNode n){
        RBTNode m = n.left;

        n.left = m.right;
        m.right = n;

        if (n.left != null)
            n.left.parent = n;

        m.parent = n.parent;
        if (n == root)
            root = m;
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

        // If p is the root
        if (pp == null){
            p.color = RBTNode.BLACK;
            colorFlipCount--; // Adjust extra black to red change count of the root
            return;
        }

        if (pp.color == RBTNode.BLACK)
            return;

        RBTNode gp = pp.parent; // Not NULL since p.parent != root

        // Case XYr: when both the children of gp are red
        if (gp.redChildCount() == 2){
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

    /**
     * Insert book @param book into the tree
     */ 
    public void insert(Book book){
        // Search for the location to insert the book
        RBTNode found = search(book.id);

        // Insert the book in the correct location in the tree
        if (found == null)
            root = new RBTNode(book, RBTNode.BLACK);

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

    /**
     * Get the node with maximum book id of the tree with root @param root
     *  */ 
    private RBTNode searchMax(RBTNode root){
        RBTNode p = root;
        while(p.right != null){
            p = p.right;
        }
        return p;
    }

    // Balance the red black tree property after delete
    private void balanceAfterDelete(RBTNode py, RBTNode y) {
        // Using Xcn cases to balance the tree

        // When y is the root
        if (py == null)
            return;
        
        // X = L
        if (py.left == y){
            // c = b
            // Case Lb0
            RBTNode v = py.right;
            if (v == null || (v.color == RBTNode.BLACK && v.redChildCount() == 0)){
                if (v != null){
                    v.flipColor();
                    colorFlipCount++;
                }
                // Lb0 (case 2 py is red)
                if (py.color == RBTNode.RED){
                    py.flipColor();
                    colorFlipCount++;
                } 
                // Lb0 (case 1 py is black)
                else
                    balanceAfterDelete(py.parent, py);
            }
            // c = b
            else if (v.color == RBTNode.BLACK){
                // Case Lb1 (case 1)
                if (v.redChildCount() == 1 && v.right != null && v.right.color == RBTNode.RED){
                    v.right.flipColor();
                    colorFlipCount++;
                    if (py.color == RBTNode.RED){
                        py.flipColor();
                        v.flipColor();
                        colorFlipCount += 2;
                    }
                    rotateCaseRR(py);
                }
                // Case Lb1 (case 2) and Lb2
                else {
                    if (py.color == RBTNode.RED)
                        py.flipColor();
                    else
                        v.left.flipColor();
                    colorFlipCount++;
                    rotateCaseRL(py);
                }
            }
            // c = r
            else{
                // Case Lr(0)
                RBTNode w = py.right.left;
                if (w == null || w.redChildCount() == 0){
                    v.flipColor();
                    colorFlipCount++;
                    if (w != null){
                        w.flipColor();
                        colorFlipCount++;
                    }
                    rotateCaseRR(py);
                } 
                // Case Lr(1) (case 1)
                else if (w.redChildCount() == 1 && w.right != null && w.right.color == RBTNode.RED){
                    w.right.flipColor();
                    colorFlipCount++;
                    rotateCaseRL(py);
                }
                // Case Lr(1) (case 2) and Lr(2)
                else{
                    RBTNode x = w.left;
                    x.flipColor();
                    colorFlipCount++;

                    // Rotation to make x the parent of py
                    w.left = x.right;
                    if (w.left != null)
                        w.left.parent = w;

                    x.right = v;
                    v.parent = x;

                    py.right = x.left;
                    if (py.right != null)
                        py.right.parent = py;
                    
                    x.left = py;
                    x.parent = py.parent;
                    py.parent = x;

                    if (x.parent == null)
                        root = x;
                }
            }
        } 
        // X = R (Mirror image of the above code)
        else{
            // c = b
            RBTNode v = py.left;
            // Case Rb0
            if (v == null || (v.color == RBTNode.BLACK && v.redChildCount() == 0)){
                if (v != null){
                    v.flipColor();
                    colorFlipCount++;
                }
                // Rb0 (case 2 py is red)
                if (py.color == RBTNode.RED){
                    py.flipColor();
                    colorFlipCount++;
                } 
                // Rb0 (case 1 py is black)
                else
                    balanceAfterDelete(py.parent, py);
            } 
            // c = b
            else if (v.color == RBTNode.BLACK){
                // Case Rb1 (case 1)
                if (v.redChildCount() == 1 && v.left != null && v.left.color == RBTNode.RED){
                    v.left.flipColor();
                    colorFlipCount++;
                    if (py.color == RBTNode.RED){
                        py.flipColor();
                        v.flipColor();
                        colorFlipCount += 2;
                    }
                    rotateCaseLL(py);
                }
                // Case Rb1 (case 2) and Rb2
                else {
                    if (py.color == RBTNode.RED)
                        py.flipColor();
                    else
                        v.right.flipColor();
                    colorFlipCount++;
                    rotateCaseLR(py);
                }
            }
            // c = r
            else{
                // Case Rr(0)
                RBTNode w = py.left.right;
                if (w == null || w.redChildCount() == 0){
                    v.flipColor();
                    colorFlipCount++;
                    if (w != null){
                        w.flipColor();
                        colorFlipCount++;
                    }
                    rotateCaseLL(py);
                } 
                // Case Rr(1) (case 1)
                else if (w.redChildCount() == 1 && w.left != null && w.left.color == RBTNode.RED){
                    w.left.flipColor();
                    colorFlipCount++;
                    rotateCaseLR(py);
                }
                // Case Rr(1) (case 2) and Rr(2)
                else{
                    RBTNode x = w.right;
                    x.flipColor();
                    colorFlipCount++;

                    // Rotation to make x the parent of py
                    w.right = x.left;
                    if (w.right != null)
                        w.right.parent = w;

                    x.left = v;
                    v.parent = x;

                    py.left = x.right;
                    if (py.left != null)
                        py.left.parent = py;
                    
                    x.right = py;
                    x.parent = py.parent;
                    py.parent = x;

                    if (x.parent == null)
                        root = x;
                }
            }
        }
    }

    /**
     * Delete the node with the given id and return the deleted book
     * @param id bookId to be deleted from the tree
     * @return Deleted book if present in the tree else null
     */
    public Book delete(int id){
        // Get the node we want to delete
        RBTNode p = search(id);
        if (p.book.id != id) // When the node doesn't exist in the tree
            return null;

        // Variables to keep track of the swapped book's node color 
        // (if we swap a book for deleting a node with two children)
        RBTNode swappedTo = null;
        boolean swapColor = false;

        // When p has two children, swap content with a leaf and dekete that leaf
        if (p.childCount() == 2){
            RBTNode swap = searchMax(p.left);
            Book temp = p.book;
            if (swap.color != p.color)
                colorFlipCount++; // Since the color of the "Book" changes!!
            p.book = swap.book;
            swap.book = temp;

            swappedTo = p;
            swapColor = swap.color;

            p = swap;
        }
        
        RBTNode py = p.parent;
        RBTNode y;

        // if p is the root
        if (py == null){
            if (p.left != null)
                root = p.left;
            else
                root = p.right;
            if (root != null){
                root.parent = null;
                if (root.color != RBTNode.BLACK){
                    root.flipColor();
                    colorFlipCount++;
                }
            }   
            return p.book;
        }
        
        // Remove p and add its child to py in place of it
        if (p.left != null){
            if (p.isLeftChild())
                py.left = p.left;
            else
                py.right = p.left;
            y = p.left; // Make y point to the 'deficient' subtree
        } else{
            if (p.isLeftChild())
                py.left = p.right;
            else
                py.right = p.right;
            y = p.right; // Make y point to the 'deficient' subtree
        }

        
        if (y != null)
            y.parent = py;
        
        // When removed node is red: No black node deficiency in the tree
        if (p.color == RBTNode.RED)
            return p.book;
        
        // When p is black, has a child and that child is RED
        if (y != null && y.color == RBTNode.RED){
            y.flipColor();
            colorFlipCount++;
            return p.book;
        }

        boolean sameBeforeBalancing = (swappedTo != null && swapColor == swappedTo.color);
        // When y is null or black
        balanceAfterDelete(py, y);
        boolean sameAfterBalancing = (swappedTo != null && swapColor == swappedTo.color);

        // Adjust the color flip count to reflect the change in color of the book after swapping
        if (!sameBeforeBalancing && sameAfterBalancing)
            colorFlipCount -= 2; // Two color flips for the swapped book => net = 0

        return p.book;
    }

    // Return the Books with ids closest to the Target
    public Book[] findNearest(int targetId){
        Book[] nearest = new Book[2];
        RBTNode p = root; // node to traverse and search the tree

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
     * @param p Current root of the recursive tree
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
        getBooks(id1, id2, this.root, books);
        return books;
    }

    // Helper to see the node colors of each book id
    private void printTree(RBTNode root){
        if (root == null)
            return;
        RBTNode p = root;
        printTree(p.left);
        System.out.print(p + " ");
        printTree(p.right);
    }

    // Print in-order traversal of the tree (With colors)
    public void printTree(){
        printTree(root);
        System.out.println("");
    }

    /* 
    * (optional) Helper method to check the correct implementation
    *  - Count number of black nodes in a path from root to any leaf
    */ 
    public int countBlacks(){
        int count = 0;
        RBTNode p = root;
        while(p != null){
            count += p.color?1:0; // Black = True
            p = p.left;
        }
        return count;
    }

    // Test the working of RB-Tree
    public static void main(String[] args){
        RedBlackTree rbt = new RedBlackTree();

        rbt.insert(new Book(101, "newBook", "someone", true));
        rbt.insert(new Book(48, "newBook", "someone", true));
        rbt.insert(new Book(132, "newBook", "someone", true));
        rbt.insert(new Book(25, "newBook", "someone", true));
        rbt.insert(new Book(73, "newBook", "someone", true));
        rbt.insert(new Book(12, "newBook", "someone", true));
        rbt.insert(new Book(6, "newBook", "someone", true));
        System.out.println(rbt.getColorFlipCount());
        rbt.printTree();
        rbt.delete(25);
        rbt.delete(12);
        rbt.printTree();
        System.out.println(rbt.getColorFlipCount());
        rbt.insert(new Book(2, "newBook", "someone", true));
        rbt.insert(new Book(1, "newBook", "someone", true));
        rbt.insert(new Book(3, "newBook", "someone", true));
        System.out.println(rbt.getBook(2));
        System.out.println("Blacks: " + rbt.countBlacks());
    }
}
