import java.io.*;
import java.util.*;
import java.lang.Math;

public class gatorLibrary{
    private static RedBlackTree library;
    private static PrintWriter output;

    public static void printBook(int bookId){
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

    public static void printBooks(int bookId1, int bookId2){
        boolean found = false;
        for (Book book: library.getBooks(bookId1, bookId2)){
            found = true;
            writeToOut(book.toString());
        }

        if (!found)
            System.out.println("No books found in the range [" + bookId1 + ", " + bookId2 + "]\n");
    }

    private static void borrowBook(int patronId, int bookId, int priority){
        Book book = library.getBook(bookId);

        if (book == null){
            writeToOut(
                "Book " + bookId + " not found in the Library"
            );
            return;
        }

        if (book.available) {
            book.available = false;
            book.borrowedBy = patronId;

            writeToOut(
                "Book " + bookId + " Borrowed by Patron " + patronId
            );
        } else {
            book.reservationHeap.put(patronId, priority);
            
            writeToOut(
                "Book " + bookId + " Reserved by Patron " + patronId
            );
        }
    }

    private static void returnBook(int patronId, int bookId){
        Book book = library.getBook(bookId);

        if (book == null){
            writeToOut(
                "Book " + bookId + " not found in the Library"
            );
            return;
        }

        book.available = true;
        writeToOut(
            "Book " + bookId + " Returned by Patron " + patronId
        );

        book.borrowedBy = book.reservationHeap.pop();

        if (book.borrowedBy != -1) {
            book.available = false;

            writeToOut(
                "Book " + bookId + " Allotted to Patron " + book.borrowedBy
            );
        }
    }

    public static void deleteBook(int bookId){
        Book book = library.delete(bookId);
        if (book == null){
            writeToOut(
                "Book " + bookId + " not found in the Library"
            );
            return;
        }

        String out = "Book " + bookId + " is no longer available.";

        if (book.reservationHeap.size() > 0){
            out += " Reservations made by Patrons " + book.reservationHeap + " have been cancelled!";
        }

        writeToOut(out);
    }

    private static void findCloset(int targetId) {
        Book[] nearest = library.findNearest(targetId);
        if (nearest[0] == null){
            System.out.println("No Books in the Library yet!\n");
            return;
        }
        writeToOut(nearest[0].toString());

        if (nearest[1] != null && Math.abs(nearest[0].id - targetId) == Math.abs(nearest[1].id - targetId))
            writeToOut(nearest[1].toString());
    }

    public static void main(String[] args){
        try {
            if (args.length != 1){
                System.err.println("Usage: `java gatorLibrary file_name.txt`");
                return;
            }
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
            while (!method.equals("Quit")){
                if (input.hasNextLine()){
                    cmd = input.nextLine();

                    // Divide the input text into method and parameters
                    method = cmd.substring(0, cmd.indexOf("("));
                    String[] params = cmd.substring(cmd.indexOf("(") + 1, cmd.indexOf(")")).split(", ");

                    System.out.println("Command read: " + cmd);

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
                            findCloset(Integer.parseInt(params[0]));
                            break;
                        case "ColorFlipCount":
                            writeToOut("Colour Flip Count: " + library.getColoFlipCount());
                            break;
                        default:
                            break;
                    }
                } else{
                    System.out.println("'Quit()' is not in input!");
                    cmd = "Quit()";
                }
            }
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
        }
    }

    /**
     * Write the given text to output file and teh stdout
     * @param out output writer object
     * @param text text to be written to the output
     */
    private static void writeToOut(String text){
        output.println(text + "\n");
        System.out.println(text + "\n");
    }
}
