import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class gatorLibrary{
    private static RedBlackTree library;
    private static PrintWriter output;
    private static boolean verbose = false;

    /**
     * Print the informaiton of the book with given bookId
     */
    private static void printBook(int bookId){
        Book book = library.getBook(bookId);

        if (book != null)
            writeToOut(
                book.toString()
            );
        else
            writeToOut(
                "Book " + bookId + " not found in the Library"
            );
    }

    /**
     * Prints all the books in the given range of bookIds
     */
    private static void printBooks(int bookId1, int bookId2){
        boolean found = false; // Var to check if books found in the given range
        for (Book book: library.getBooks(bookId1, bookId2)){
            found = true;
            writeToOut(book.toString());
        }

        // When there are no books in the given range
        if (!found && verbose)
            System.out.println("No books found in the range [" + bookId1 + ", " + bookId2 + "]\n");
    }

    /**
     * Helper method to execute BorrowBook() command
     * Let the partron with patronId borrow the book bookId
     * or adds the patron to the reservation queue of the book with given priority
     */
    private static void borrowBook(int patronId, int bookId, int priority){
        // Get the book from the library
        Book book = library.getBook(bookId);

        if (book == null){
            writeToOut(
                "Book " + bookId + " not found in the Library"
            );
            return;
        }

        // Allot to the patron if available
        if (book.available) {
            book.available = false;
            book.borrowedBy = patronId;

            writeToOut(
                "Book " + bookId + " Borrowed by Patron " + patronId
            );
        // Add patron to the reservation queue otherwise
        } else {
            book.reservationHeap.put(patronId, priority);
            
            writeToOut(
                "Book " + bookId + " Reserved by Patron " + patronId
            );
        }
    }

    /**
     * Helper method to implement ReturnBook() command
     * Updates the book's availability status
     * Allots the book to top priority in the reservation if available
     */
    private static void returnBook(int patronId, int bookId){
        // Search and get the book with given Id
        Book book = library.getBook(bookId);

        // Check if the book is available in the library
        if (book == null){
            writeToOut(
                "Book " + bookId + " not found in the Library"
            );
            return;
        }

        // Set the availability status
        book.available = true;
        writeToOut(
            "Book " + bookId + " Returned by Patron " + patronId
        );

        // If there are patrons waiting in the reservation queue
        // Allot the book to them
        book.borrowedBy = book.reservationHeap.pop();
        if (book.borrowedBy != -1) {
            book.available = false;

            writeToOut(
                "Book " + bookId + " Allotted to Patron " + book.borrowedBy
            );
        }
    }

    /**
     * Helper method for DeleteBook()
     * Deletes the book if present in the tree
     * Prints the confirmation along with the reservation heap of the book 
     * */ 
    private static void deleteBook(int bookId){
        // Delete the book from the tree
        Book book = library.delete(bookId);
        if (book == null){
            writeToOut(
                "Book " + bookId + " not found in the Library"
            );
            return;
        }

        // Print the status of deletion
        String out = "Book " + book.id + " is no longer available";

        if (book.reservationHeap.size() == 1)
            out += ". Reservation made by Patron " + book.reservationHeap + " has been cancelled!";

        if (book.reservationHeap.size() > 1)
            out += ". Reservations made by Patrons " + book.reservationHeap + " have been cancelled!";

        writeToOut(out);
    }

    /**
     * Helper method to implement FindClosest() command
     * Finds atmost 2 books with ids nearest to the given targetId and prints
     */
    private static void findClosest(int targetId) {
        Book[] nearest = library.findNearest(targetId);
        if (nearest[0] == null){
            System.out.println("No Books in the Library yet!\n");
            return;
        }
        // In case of tie, print both in the id order
        if (nearest[1] != null && Math.abs(nearest[0].id - targetId) == Math.abs(nearest[1].id - targetId)){
            if (nearest[1].id > nearest[0].id){
                writeToOut(nearest[0].toString());
                writeToOut(nearest[1].toString());
            } else {
                writeToOut(nearest[1].toString());
                writeToOut(nearest[0].toString());
            }
            return;
        }
        writeToOut(nearest[0].toString());
    }

    /**
     * Start of the program
     * @param args Expects inputfile path from the command line
     */
    public static void main(String[] args){
        long startTime = System.currentTimeMillis(); // Optional timer for inference
        try {
            if (args.length < 1){
                System.err.println("Usage: `java gatorLibrary file_name.txt`");
                return;
            }
            if (args.length == 2) verbose = true;
            String inputFile = args[0];
            if (!inputFile.endsWith(".txt")){
                System.err.println("The input file must be a text file (ending with .txt)!");
                return;
            }
            String outputFile = inputFile.substring(0, inputFile.length() - 4) + "_output_file.txt";

            Scanner input = new Scanner(new File(inputFile));

            output = new PrintWriter(new FileWriter(outputFile));

            library = new RedBlackTree();

            String cmd, method = "";
            // Exit when a Quit() is found in the File
            while (!method.equals("Quit") && input.hasNextLine()){
                cmd = input.nextLine();

                if (verbose) System.out.println("Command read: " + cmd);

                // Divide the input text into method and parameters
                method = cmd.substring(0, cmd.indexOf("("));
                String[] params = cmd.substring(cmd.indexOf("(") + 1, cmd.indexOf(")")).split(", ?");

                // Execute based on the input method provided
                switch (method) {
                    case "InsertBook":
                        library.insert(
                            new Book(
                                Integer.parseInt(params[0]),
                                params[1],
                                params[2],
                                params[3].equals("\"Yes\"")
                            )
                        );
                        if (verbose) System.out.println(params[1] + " is added to the library!\n");
                        break;
                    case "PrintBook":
                        printBook(Integer.parseInt(params[0]));
                        break;
                    case "PrintBooks":
                        printBooks(
                            Integer.parseInt(params[0]),
                            Integer.parseInt(params[1])
                            );
                        break;
                    case "BorrowBook":
                        borrowBook(
                            Integer.parseInt(params[0]),
                            Integer.parseInt(params[1]),
                            Integer.parseInt(params[2])
                        );
                        break;
                    case "ReturnBook":
                        returnBook(
                            Integer.parseInt(params[0]),
                            Integer.parseInt(params[1])
                        );
                        break;
                    case "DeleteBook":
                        deleteBook(Integer.parseInt(params[0]));
                        break;
                    case "FindClosestBook":
                        findClosest(Integer.parseInt(params[0]));
                        break;
                    case "ColorFlipCount":
                        writeToOut("Color Flip Count: " + library.getColorFlipCount());
                        break;
                    default:
                        break;
                }
            }
            // Check if Quit() is in the input
            if (!method.equals("Quit")){
                if (verbose) System.out.println("'Quit()' is not in input!");
            } else
                output.print("Program Terminated!!");
            input.close();
            output.close();
        } catch (ArrayIndexOutOfBoundsException e){
            /**
             * Handle Index out of Bounds Exception
             * Occurs when the parameters in input file are specified wrongly!
             */
            System.err.println("Check the number of parameters!");
        } catch (FileNotFoundException e){
            System.out.println(e);
            return;
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e){
            System.err.println("Invalid input in the text file!");
        }
        long endTime = System.currentTimeMillis();
        if (verbose) System.out.printf("Execution time: %.3fs\n", (endTime - startTime) / 1000.0);
    }

    /**
     * Helper method to write the given text to output file and teh stdout
     * @param text text to be written to the output
     */
    private static void writeToOut(String text){
        output.println(text + "\n");
        if (verbose) System.out.println(text + "\n");
    }
}
