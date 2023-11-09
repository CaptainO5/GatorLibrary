import java.io.*;
import java.util.*;

public class gatorLibrary{
    private static RedBlackTree library;
    private static PrintWriter output;

    public static void printBook(int id){
        Book book = library.getBook(id);

        if (book != null){
            writeToOut(
                book.toString()
            );
            return;
        }
        
        writeToOut(
            "Book " + id + " not found in the Library"
        );
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

    public static void main(String[] args){
        try {
            String inputFile = args[0];
            String outputFile = inputFile.split("\\.")[0] + "_output_file.txt";

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

                    writeToOut(cmd);

                    switch (method) {
                        case "PrintBook":
                            printBook(Integer.parseInt(params[0]));
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

                        default:
                            break;
                    }
                    writeToOut("");
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
             * Occurs when no input file is provided via cosole or the file extension
             * is missing.
             */
            System.out.println("Usage: `java gatorLibrary file_name.txt`");
            return;
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
        output.println(text);
        System.out.println(text);
    }
}
