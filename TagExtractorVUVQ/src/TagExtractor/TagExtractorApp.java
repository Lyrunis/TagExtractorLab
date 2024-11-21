package TagExtractor;

import javax.swing.*;

/**
 * Entry point for the Tag Extractor application.
 * This class initializes the application by creating and displaying the UI.
 */
public class TagExtractorApp {
    public static void main(String[] args) {
        // Ensure the GUI runs on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(() -> {
            // Create an instance of the application's UI class
            TagExtractorUI appUI = new TagExtractorUI();
            
            // Display the user interface
            appUI.display();
        });
    }
}
