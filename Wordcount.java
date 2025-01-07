package Wordcount;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Wordcount {
    public static void main(String args[]){
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter the filename: ");
        String filename = inputScanner.nextLine();
        int count = countWords(filename);
        System.out.println("Number of words in " + filename + " = " + count);
        inputScanner.close();
    }

    // Function to count the number of words in a file
    public static int countWords(String filename) {
        int count = 0;
        try {
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                fileScanner.next();
                count++;
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return count;
    }
}