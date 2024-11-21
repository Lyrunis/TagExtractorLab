package TagExtractor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TagProcessor {

	public Map<String, Integer> extractKeywords(File textFile, File stopWordsFile) throws IOException {
	    // Load stop words from the specified file
	    Set<String> stopWords = loadStopWords(stopWordsFile);

	    // A map to store keywords and their corresponding frequencies
	    Map<String, Integer> keywordCounts = new TreeMap<>();

	    // Process each line in the text file
	    Files.lines(Paths.get(textFile.getAbsolutePath()))
	            // Replace non-alphabetic characters with spaces and convert to lowercase
	            .map(line -> line.replaceAll("[^a-zA-Z]+", " ").toLowerCase())
	            // Split lines into individual words
	            .flatMap(line -> Arrays.stream(line.split("\\s+")))
	            // Filter out stop words and blank entries
	            .filter(word -> !word.isBlank() && !stopWords.contains(word))
	            // Count occurrences of each word
	            .forEach(word -> keywordCounts.put(word, keywordCounts.getOrDefault(word, 0) + 1));

	    return keywordCounts;
	}

    private Set<String> loadStopWords(File stopWordsFile) throws IOException {
        // Use a TreeSet to store stop words in sorted order
        Set<String> stopWords = new TreeSet<>();

        // Read each line from the stop words file and add it to the set
        Files.lines(Paths.get(stopWordsFile.getAbsolutePath()))
                .map(String::toLowerCase) // Convert to lowercase for case-insensitive matching
                .forEach(stopWords::add);

        return stopWords;
    }

    public static void saveResults(String results, File saveFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            writer.write(results); // Write the results string to the file
        }
    }
}
