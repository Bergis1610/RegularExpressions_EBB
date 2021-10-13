package one;

import java.util.*;
import java.util.regex.*;
import java.io.*;


/**
 * TextProcessor.java
 * This program will use the Regex library to scan and find
 * patterns from the Dracula text downloaded from gutenberg.org
 * @author Emil Bjørlykke Berglund
 * @version 1.0
 * Compiler Project 3
 * CS-322 Compiler Construction
 * Fall 2021
 */


public class TextProcessor {

    /**
     * Regex patterns (java format)
     * Pattern a)
     * \b[aA]\b|\b[Aa]n\b|\b[Tt]he\b
     *
     * Pattern b)
     * (Mina Harker|Mrs. Harker)
     *
     * (\b[Mm]rs\.|\b[Mm]ina)\sHarker\b
     *
     * Pattern c)
     * [^.]*(Transylvania)[^.]*
     *
     * Pattern d)
     * \b[Tt]o\s+\w+
     *
     * Pattern e)
     * \w*(?<![Gg]odalm|[Hh]els)ing\b
     *
     */

    //Universal scanner
    public static Scanner scan = new Scanner(System.in);

    /**
     * This is the pattern searcher. Any time it finds the pattern, it counts and prints it.
     * @param filename
     * @param pat
     * @return int
     */
    public int instanceFinder(String filename, String pat){
        int count = 0;

        Pattern pattern = Pattern.compile(pat, Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        String line;


        String key;

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                line = myReader.nextLine();


                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    key = line.substring(matcher.start(), matcher.end());
                    println(key);
                    count++;
                }
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        println("\nThe number of matches was: " + count);
        return count;
    }

    /**
     * Hoved means main in norwegian, simply means main method
     * This method prompts for the name of file to be checked and for the pattern to be used
     * Then does the search, counts occurences as well as printing every occurence
     *
     * This method was the original method until I reread the question and made the program
     * so that you can enter arguments from the command line
     */
    public void hoved(){

        print("Please type the file path: ");
        String filename = scan.nextLine();
        print("You chose this path: ");
        println(filename);
        println();

        println("Now, please type in a command.");
        println("Here are 5 RegEx examples: ");
        println("-----------------------------------------------------------");
        println("Pattern for a):   \\b[aA]\\b|\\b[Aa]n\\b|\\b[Tt]he\\b");
        println("Pattern for b):   (Mina Harker|Mrs. Harker)");
        println("Pattern for c):   [^.]*(Transylvania)[^.]*");
        println("Pattern for d):   \\b[Tt]o\\s+\\w+");
        println("Pattern for e):   \\w*(?<![Gg]odalm|[Hh]els)ing\\b");
        println("-----------------------------------------------------------");
        print("Please type the command here: ");
        String command = scan.nextLine();
        println();

        instanceFinder(filename, command);
        println("\n\nDone!");

    }

    //main
    public static void main(String[]args) {
        TextProcessor tp = new TextProcessor();

        String fileName;
        String patty;

        /**
         * Checks whether two arguments have been passed at the commandline,
         * if not, then a method will be called so the program can function regardless
         * In other words, its a hybrid method. If two arguments are passed at commandline,
         * then the program will run how its supposed to, but if not, then the program will
         * still function with user prompts and such
         *
         * The method tries to find the path to the wanted file and then compiles the pattern entered
         * and then searches every line for every occurence of the pattern and counts it and prints it.
         */

        if(args.length != 2){
            println("Please enter two arguments at the command line! First the filename and then the pattern.");
            println("\n\n");
            tp.hoved();
        } else {
            fileName = args[0];
            patty = args[1];

            Pattern pattern = Pattern.compile(patty,Pattern.CASE_INSENSITIVE);
            Matcher matcher;
            String line;
            int count = 0;

            try {
                File myObj = new File(fileName);
                Scanner myReader = new Scanner(myObj);

                while (myReader.hasNextLine()) {

                    line = myReader.nextLine();
                    matcher = pattern.matcher(line);

                    while(matcher.find()){
                        println(line.substring(matcher.start(),matcher.end()));
                        count++;
                    }

                }
                myReader.close();

            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            println("Total number of instances found: " + count);
        }

    }

    //Printer methods
    public static void print(Object o){
        System.out.print(o);
    }
    public static void println(Object o){
        System.out.println(o);
    }
    public static void print(){
        System.out.print("");
    }
    public static void println(){
        System.out.println("");
    }
}
