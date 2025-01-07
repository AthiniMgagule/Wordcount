package Wordcount;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Wordcount {
    public static void main(String args[]){
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter the filenames separated by commas: ");
        String filenames = inputScanner.nextLine();
        processFiles(filenames);
        inputScanner.close();
    }

    // Function to process multiple files
    public static void processFiles(String filenames) {
        String[] files = filenames.split(",");
        int totalWordCount = 0;
        int totalCharCount = 0;
        int totalLineCount = 0;
        HashMap<String, Integer> totalWordFrequency = new HashMap<>();

        for (String filename : files) {
            filename = filename.trim();
            FileStats stats = countWords(filename);
            System.out.println("Number of words in " + filename + " = " + stats.wordCount);
            System.out.println("Number of characters in " + filename + " (with spaces) = " + stats.charCountWithSpaces);
            System.out.println("Number of characters in " + filename + " (without spaces) = " + stats.charCountWithoutSpaces);
            System.out.println("Number of lines in " + filename + " = " + stats.lineCount);
            System.out.println("Word frequency in " + filename + " = " + stats.wordFrequency);
            totalWordCount += stats.wordCount;
            totalCharCount += stats.charCountWithSpaces;
            totalLineCount += stats.lineCount;
            mergeWordFrequencies(totalWordFrequency, stats.wordFrequency);
        }

        System.out.println("Total number of words in all files = " + totalWordCount);
        System.out.println("Total number of characters in all files (with spaces) = " + totalCharCount);
        System.out.println("Total number of lines in all files = " + totalLineCount);
        System.out.println("Total word frequency in all files = " + totalWordFrequency);
    }

    // Function to count the number of words, characters, and lines in a file
    public static FileStats countWords(String filename) {
        int wordCount = 0;
        int charCountWithSpaces = 0;
        int charCountWithoutSpaces = 0;
        int lineCount = 0;
        HashMap<String, Integer> wordFrequency = new HashMap<>();

        try {
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                lineCount++;
                charCountWithSpaces += line.length();
                charCountWithoutSpaces += line.replace(" ", "").length();
                String[] words = line.split("\\s+");
                wordCount += words.length;
                for (String word : words) {
                    word = word.toLowerCase();
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }

        return new FileStats(wordCount, charCountWithSpaces, charCountWithoutSpaces, lineCount, wordFrequency);
    }

    // Helper function to merge word frequencies from multiple files
    public static void mergeWordFrequencies(HashMap<String, Integer> total, HashMap<String, Integer> current) {
        for (String word : current.keySet()) {
            total.put(word, total.getOrDefault(word, 0) + current.get(word));
        }
    }

    // Class to hold file statistics
    static class FileStats {
        int wordCount;
        int charCountWithSpaces;
        int charCountWithoutSpaces;
        int lineCount;
        HashMap<String, Integer> wordFrequency;

        FileStats(int wordCount, int charCountWithSpaces, int charCountWithoutSpaces, int lineCount, HashMap<String, Integer> wordFrequency) {
            this.wordCount = wordCount;
            this.charCountWithSpaces = charCountWithSpaces;
            this.charCountWithoutSpaces = charCountWithoutSpaces;
            this.lineCount = lineCount;
            this.wordFrequency = wordFrequency;
        }
    }
}