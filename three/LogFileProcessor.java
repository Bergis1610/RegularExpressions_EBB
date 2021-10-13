package three;

import java.util.*;
import java.util.regex.*;
import java.io.*;

/**
 * LogFileProcessor.java
 * This program will be used to scan log files and store them in hashmaps.
 * @author Emil Bjørlykke Berglund
 * @version 1.0
 * Compiler Project 3
 * CS-322 Compiler Construction
 * Fall 2021
 */

public class LogFileProcessor {

    /** Regex Patterns
     * User:
     * (?<=user\s)\w*
     *
     * IP address:
     * \d*[.]\d*[.]\d*[.]\d*
     *
     */

    /**
     * Method for printing the hashmap line for line with the number of instances and the key
     * @param map
     */
    public void printHashMap(HashMap<String,Integer> map){

       Object[]keys = map.keySet().toArray();

       for(int i = 0;i < keys.length;i++){
           print(keys[i]);
           println(":  " + map.get(keys[i]));
       }

    }

    //main
    public static void main(String[]args) {
        //Constructor
        LogFileProcessor f = new LogFileProcessor();

        String fileName;
        String printFlag;

        //Returns if the user has not entered two arguments
        if (args.length != 2) {
            println("Error, Please enter two arguments at the command line.");
            return;
        } else {
            fileName = args[0];
            printFlag = args[1];
        }

        String line;
        String key;

        int lineCount = 0;
        int uniqueUserCount = 0;
        int uniqueIPCount = 0;

        //Creating the hashmap and pattern for the usernames
        HashMap<String, Integer> userHash = new HashMap<String, Integer>();
        String pat = "(?<=user\\s)\\w*";
        Pattern pattern = Pattern.compile(pat, Pattern.CASE_INSENSITIVE);
        Matcher matcher;

        //Creating the hashmap and pattern for the IP addresses
        HashMap<String, Integer> IPHash = new HashMap<String, Integer>();
        String pat2 = "\\d*[.]\\d*[.]\\d*[.]\\d*";
        Pattern pattern2 = Pattern.compile(pat2, Pattern.CASE_INSENSITIVE);
        Matcher matcher2;

        //Scan the file for patterns and add them to the appropriate hashmap
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                lineCount++;
                line = myReader.nextLine();

                //scan for user pattern
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    key = line.substring(matcher.start(), matcher.end());
                    if (!userHash.containsKey(key)) {
                        userHash.put(key, 0);
                        uniqueUserCount++;
                    }
                    userHash.put(key, (userHash.get(key) + 1));

                }
                //scan for IP-address pattern
                matcher2 = pattern2.matcher(line);
                while (matcher2.find()) {
                    key = line.substring(matcher2.start(), matcher2.end());
                    if (!IPHash.containsKey(key)) {
                        IPHash.put(key, 0);
                        uniqueIPCount++;
                    }
                    IPHash.put(key, (IPHash.get(key) + 1));
                }
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Prints the chosen hashmap or none if arg[1] == 0
        println("\n");
        if (printFlag.equals("1")) {
            f.printHashMap(IPHash);
        } else if (printFlag.equals("2")) {
            f.printHashMap(userHash);
        }
        //Prints the default output regardless
        println(lineCount + " lines in the log file were parsed.");
        println("There are " + uniqueUserCount + " unique users in the log.");
        println("There are " + uniqueIPCount + " unique IP addresses in the log.");
        println("\n");

        //Path used for testing purposes
        //C:\Users\Bruker\IdeaProjects\ByteCodeTester\src\authLog1.txt

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
