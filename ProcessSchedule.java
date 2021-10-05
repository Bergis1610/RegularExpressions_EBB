
import java.util.*;
import java.util.regex.*;
import java.io.*;


/**
 * ProcessSchedule.java
 * This program will have methods to search a PDF converted to a text file
 * And print the patterns found in a new text file.
 * @author Emil Bjørlykke Berglund
 * @verion 1.0
 * Compiler Project 3
 * CS-322 Compiler Construction
 * Fall 2021
 */

public class ProcessSchedule {

    /** Regex patterns 
     *
     * Pattern a)
     * \w{2,4}-\d{3}\w*-\w{2,4}
     *
     * Pattern b)
     * (CLOSED|Open)\s\d{0,2}\s\d{0,2}
     *
     * Pattern c)
     * \w\D{1,3}-\d\d\d
     *
     */

    //Method to create a file
    public static void CreateFile(String name){
        try {
            File myObj = new File(name);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //Method to search a given file and return the instances found to be written into another file using another method
    public String instanceTextReturner(String filename, String command){
        int count = 0;
        String text = "";

        Pattern pattern = Pattern.compile(command);
        Matcher matcher;
        String line;

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                matcher = pattern.matcher(line);
                while(matcher.find()){
                    text = text + line.substring(matcher.start(),matcher.end()) + "\n";
                    count++;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        println("\nThe number of matches was: " + count);
        return text;
    }
    //Method to write text to file
    public void writeToFile(String fileName, String text){

        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(text);
            myWriter.close();
            System.out.println("\nSuccessfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //Method that counts the number of instances of a given command from a file
    public int instanceCounter(String filename, String command){
        int count = 0;
        int tot = 0;

        String currentClass = "0";


        Pattern pattern = Pattern.compile(command, Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        String line;
        String pat;


        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()){
                line = myReader.nextLine();
                matcher = pattern.matcher(line);

                if(matcher.find()){
                    pat = line.substring(matcher.start(),(matcher.end()-4));

                    if(currentClass.charAt(0) == '0') {
                        currentClass = pat;
                    }

                    if(!pat.equalsIgnoreCase(currentClass)) {
                        println("Program " + currentClass + " had " + count + " unique courses.\n");
                        currentClass = pat;
                        count = 1;
                    } else {
                        count++;
                    }

                    tot++;
                }
            }
            println("Program " + currentClass + " had " + count + " unique courses.\n");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        println("\nThe total number of classes was: " + tot);
        return count;
    }

    //Methods for searching the file for pre-set patterns
    //Pattern for search A: \w{2,4}-\d{3}\w*-\w{2,4}
    public void searchA(String searchedFile, String newFile){
        CreateFile(newFile);
        println("File aquired");
        String comm = "\\w{2,4}-\\d{3}\\w*-\\w{2,4}";

        String text = instanceTextReturner(searchedFile, comm);
        writeToFile(newFile, text);

    }
    //Pattern for search B: (CLOSED|Open)\s\d{0,2}\s\d{0,2}
    public void searchB(String searchedFile, String newFile){
        CreateFile(newFile);
        println("File aquired");

        String comm = "(CLOSED|Open)\\s\\d{0,2}\\s\\d{0,2}";
        String text = instanceTextReturner(searchedFile, comm);

        writeToFile(newFile, text);

    }
    //Pattern for search C: \w\D{1,3}-\d\d\d
    public void searchC(String searchedFile){
        String comm = "\\w\\D{1,3}-\\d\\d\\d";
        instanceCounter(searchedFile, comm);
    }

    //Main method used for prompts which will call one of the three searches
    public void hoved(){
        Scanner scan = new Scanner(System.in);

        Boolean done = false;
        String filePath = "";
        String newPath;
        String answer;
        while(!done) {
            print("First choose the path for the file you want to search: ");
            filePath = scan.nextLine();
            println();
            println("This is the path you chose: " + filePath);
            print("Is this the correct path? Please type 1 for yes or 0 for no: ");
            answer = scan.nextLine();
            if(answer.charAt(0) == '1')
                done = true;
        }
        println();
        println("Please select which method you want to call: ");
        println("Search a) Extracts the course title for each course and saves it to a new text file.");
        println("Search b) Extracts the section that indicates if the course is open or closed, " +
                "as well as the seats available and filled and saves it to a new text file.");
        println("Search c) counts the number of unique classes that each program offers and prints it out.\n");

        done = false;
        while(!done) {
            print("\nSo, type a, b or c to choose which method to call: ");
            answer = scan.nextLine();
            if(answer.length() == 1 &&(answer.charAt(0) == 'a' || answer.charAt(0) == 'A')){
                done = true;
                print("Please type the name and path for the new file to be created: ");
                newPath = scan.nextLine();

                searchA(filePath, newPath);

            } else if(answer.length() == 1 &&(answer.charAt(0) == 'b' || answer.charAt(0) == 'B')){
                done = true;
                print("Please type the name and path for the new file to be created: ");
                newPath = scan.nextLine();

                searchB(filePath, newPath);


            } else if(answer.length() == 1 &&(answer.charAt(0) == 'c' || answer.charAt(0) == 'C')) {
                done = true;

                searchC(filePath);

            } else {
                println("Please type a, b or c, either upper or lower case to continue");
            }

        }

        /*
        String searchedFile = "C:\\Users\\Bruker\\IdeaProjects\\ByteCodeTester\\src\\" + "Fall21" + ".txt";
        String searchedFile2 = "C:\\Users\\Bruker\\IdeaProjects\\ByteCodeTester\\src\\" + "Spring21" + ".txt";
        String newFile = "CourseInfoFall21";
        String newFile2 = "OpenClosedFall21";

        String comm = "\\w{2,4}-\\d{3}\\w*-\\w{2,4}";
        String comm2 = "(CLOSED|Open)\\s\\d{0,2}\\s\\d{0,2}";
        String comm3 = "\\w\\D{1,3}-\\d\\d\\d";


        process1(searchedFile, newFile, comm);
        process1(searchedFile, newFile2,comm2);
        //Fall 21
        instanceCounter(searchedFile, comm3);
        //Spring 21
        instanceCounter(searchedFile2, comm3);

         */

    }

    //main
    public static void main(String[]args){
        ProcessSchedule s = new ProcessSchedule();

        //Paths used for testing purposes
        /*
        //C:\Users\Bruker\IdeaProjects\ByteCodeTester\src\Fall21.txt
        //C:\Users\Bruker\IdeaProjects\ByteCodeTester\src\TESTERINFO.txt
        //C:\Users\Bruker\IdeaProjects\ByteCodeTester\src\TESTEROPEN.txt
         */
        s.hoved();
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
