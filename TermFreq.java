import java.util.HashMap;
import java.util.Map;

/**
 * TermFreq handles the map in which holds the documentID and frequency.
 */

public class TermFreq {

    //Map for DocumentID and Frequency
    private Map<Integer, Integer> occurrences = new HashMap<>();

    /**
     * Constructor
     */
    public TermFreq() {

    }

    /**
     * Checks if the word already exists in the map and adds occurrence if so.
     * @param documentID
     */
    public void addWordOccurrence(int documentID) {
        if (occurrences.containsKey(documentID)) {
            occurrences.put(documentID, occurrences.get(documentID) + 1);
        } else {
            occurrences.put(documentID, 1);
        }
    }

    /**
     * Returns the formatted result for the map
     * @return formatted result
     */
    public String printDocumentFrequency() {
        String output = "";
        for (int item : occurrences.keySet()) {
            output += "<" + item + "," + occurrences.get(item) + "> ";
        }
        return output;
    }
}
