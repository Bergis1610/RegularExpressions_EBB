package two;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.Scanner;

/**
 * ExtractText.java
 * This program will use the PDFBox library from Apache Foundation to extract
 * the data for the PDF that you can download at bellarmine.edu as a text file
 * @author Emil Bjørlykke Berglund
 * @version 1.0
 * Compiler Project 3
 * CS-322 Compiler Construction
 * Fall 2021
 */


/** Note to self:
 * To compile a program on the command line with an external jar use the following command:
 * javac -g -d "JarFilePath" -cp "NameOfJarFile.jar" "ClassName".java
 * Then run the program using the following command:
 * java -cp "JarFilePath".jar;. "ClassName"
 * Also make sure that the jar file is in the same directory as the java class
 */


public class ExtractText {


    /**
     * This method is used to scan a PDF file
     */

    public static String getText(File pdfFile) throws IOException {
        PDDocument doc = PDDocument.load(pdfFile);
        return new PDFTextStripper().getText(doc);
    }

    /**
     * Method for creating a file
     * @param name
     */
    public void CreateFile(String name){
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


    /**
     * Method for using the getText() method to return the text scanned from a PDF
     * @param nameOfPdf
     * @return String
     */

    public String ReadFromPDF(String nameOfPdf){

        String text = "";
        try {
            text = getText(new File(nameOfPdf));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;

    }

    /**
     * This method takes a string input, the one thats returned from the ReadFromPDF() method
     * And writes it to a given text file.
     * @param fileName
     * @param text
     */


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

    /**
     * Method using the three previous one to extract, and write text from a pdf file to a text file.
     * @param PDFName
     * @param fileName
     */

    public void pdfExtractor(String PDFName, String fileName){

        CreateFile(fileName);

        String text = ReadFromPDF(PDFName);

        writeToFile(fileName,text);
    }


    //main
    public static void main(String[]args){

        ExtractText o = new ExtractText();
        Scanner scan = new Scanner(System.in);


        System.out.print("Please type the path of the PDF from which you want to extract the text: ");
        String PDF1 = scan.nextLine();
        System.out.print("\nNow, please type the path of the new file to be created with the extracted text from the pdf: ");
        String newFile = scan.nextLine();

        o.pdfExtractor(PDF1,newFile);



        //Strings that I used to create the files
        /*
        String PDF1 = "C:\\Users\\Bruker\\IdeaProjects\\ByteCodeTester\\src\\CCBellarmineScheduleFall21.pdf";
        String PDF2 = "C:\\Users\\Bruker\\IdeaProjects\\ByteCodeTester\\src\\CCBellarmineScheduleSpring21.pdf";

        String newFile1 = "C:\\Users\\Bruker\\IdeaProjects\\ByteCodeTester\\src\\Fall21.txt";
        String newFile2 = "C:\\Users\\Bruker\\IdeaProjects\\ByteCodeTester\\src\\Spring21.txt";
        */

    }//end of main

}//end of class
