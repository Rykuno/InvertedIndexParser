
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 Donny Blaine
 COSC 4315
 Feb 17, 2017
 Summary: Program reads a set of documents from an input folder or file and builds
 an inverted index. The program then writes the results of the index and
 corresponding document ids/names to output files.

 1) Apostrophes and hyphens handled through regex.
 2) Other punctuation and characters handled through regex.
 3) Every word was focred lower case by the .toLowerCase() method
 4) Stemming was handled through the use of the porter stemmer.
 */

public class IRSystem {

    //Treemap for the inverted index for sorting
    private Map<String, TermFreq> invertedIndex = new TreeMap<>();

    //Treemap for <DocumentName, DocumentID>
    private Map<String, Integer> documentMap = new TreeMap<>();

    //Iteration of documents for storing in map
    private int currentIteration = 1;

    /**
     * Constructor
     */
    IRSystem() {
        System.out.println("Executing constructor.");
    }

    /**
     * Builds Index
     * @param s1
     */
    public void buildIndex(String s1) {

        final File folder = new File(s1);
        ArrayList<File> files = storeFilesForFolder(folder);

        for (File file : files) {
            Scanner scan = null;
            try {
                scan = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (scan != null && scan.hasNextLine()) {
                String[] wordsForLine = scan.nextLine().split("\\s+");
                for (String word : wordsForLine) {
                    word = word.replaceAll("[^a-zA-Z ]", "").toLowerCase();

                    //Stemming
                    Stemmer stemmer = new Stemmer();
                    char[] charArray = word.toCharArray();
                    stemmer.add(charArray, charArray.length);
                    stemmer.stem();
                    word = stemmer.toString();

                    TermFreq termFreq = invertedIndex.get(word);
                    if (termFreq == null) {
                        TermFreq newTermFreq = new TermFreq();
                        newTermFreq.addWordOccurrence(currentIteration);
                        invertedIndex.put(word, newTermFreq);
                        documentMap.put(file.getName(), currentIteration);
                    } else {
                        termFreq.addWordOccurrence(currentIteration);
                        invertedIndex.put(word, termFreq);
                        documentMap.put(file.getName(), currentIteration);
                    }
                }
            }
            currentIteration++;
        }
    }

    /**
     * displays conents of the inverted index written to the file in sorted order.
     *
     * @param s2
     */
    public void writeIndex(String s2) {
        File outputFile = new File(s2);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(outputFile, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (String key : invertedIndex.keySet()) {
            TermFreq currentTerm = invertedIndex.get(key);
            if (writer != null) {
                writer.print("<" + key + "> " + currentTerm.printDocumentFrequency() + " \n");
            }
        }
        if (writer != null) {
            writer.close();
        }
    }

    /**
     * creates the document mapping output file
     *
     * @param s3
     */
    public void writeDocumentIDs(String s3) {
        File outputFile = new File(s3);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(outputFile, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (String key : documentMap.keySet()) {
            int id = documentMap.get(key);
            if (writer != null) {
                writer.println("<" + Integer.toString(id) + ", " + key + ">");
            }
        }
        if (writer != null) {
            writer.close();
        }
    }

    /**
     * returns arrayList of files from a documents folder
     * @param folder
     * @return arrayList containing all files
     */
    private ArrayList<File> storeFilesForFolder(final File folder) {
        ArrayList<File> fileArray = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                storeFilesForFolder(fileEntry);
            } else {
                fileArray.add(fileEntry);
            }
        }
        return fileArray;
    }
}
