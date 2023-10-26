import java.io.*;
import java.util.*;

public class gatorLibrary{
    public static void main(String[] args){
        try {
            String inputFile = args[0];
            String outputFile = inputFile.split("\\.")[0] + "_output_file.txt";

            Scanner input = new Scanner(new File(inputFile));

            PrintWriter output = new PrintWriter(new FileWriter(outputFile));

            String cmd, method = "";
            // Exit when a Quit() is found in the File
            while (!method.equals("Quit")){
                if (input.hasNextLine()){
                    cmd = input.nextLine();

                    // Divide the input text into method and parameters
                    method = cmd.substring(0, cmd.indexOf("("));
                    String[] params = cmd.substring(cmd.indexOf("(") + 1, cmd.indexOf(")")).split(", ");

                    for (String param: params){
                        System.out.println(param);
                    }

                    writeToOut(output, cmd);
                    writeToOut(output, "");
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
    private static void writeToOut(PrintWriter out, String text){
        out.println(text);
        System.out.println(text);
    }
}
