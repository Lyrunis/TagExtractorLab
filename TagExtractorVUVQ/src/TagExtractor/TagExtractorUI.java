package TagExtractor;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TagExtractorUI {
    // GUI components
    private JFrame frame; // Main application window
    private JTextArea resultArea; // Area to display analysis results
    private File textFile; // The text file selected by the user
    private File stopWordsFile; // The stop words file selected by the user

    public void display() {
        // Create and configure the main application window
        frame = new JFrame("Tag Extractor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create the top panel with buttons for file selection and actions
        JPanel topPanel = new JPanel();
        JButton selectTextFileButton = new JButton("Select Text File");
        JButton selectStopWordsButton = new JButton("Select Stop Words File");
        JButton analyzeButton = new JButton("Analyze");
        JButton saveButton = new JButton("Save Results");

        // Add buttons to the top panel
        topPanel.add(selectTextFileButton);
        topPanel.add(selectStopWordsButton);
        topPanel.add(analyzeButton);
        topPanel.add(saveButton);

        // Create the text area to display results with a scroll bar
        resultArea = new JTextArea();
        resultArea.setEditable(false); // Make it read-only
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to the main frame
        frame.add(topPanel, BorderLayout.NORTH); // Buttons at the top
        frame.add(scrollPane, BorderLayout.CENTER); // Results area in the center

        // Add event listeners to the buttons
        selectTextFileButton.addActionListener(e -> chooseFile(true)); // Select text file
        selectStopWordsButton.addActionListener(e -> chooseFile(false)); // Select stop words file
        analyzeButton.addActionListener(e -> analyzeText()); // Analyze the text
        saveButton.addActionListener(e -> saveResults()); // Save analysis results

        // Make the frame visible
        frame.setVisible(true);
    }

    private void chooseFile(boolean isTextFile) {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showOpenDialog(frame);
        if (choice == JFileChooser.APPROVE_OPTION) { // If the user selected a file
            if (isTextFile) {
                textFile = fileChooser.getSelectedFile();
                resultArea.append("Selected Text File: " + textFile.getName() + "\n");
            } else {
                stopWordsFile = fileChooser.getSelectedFile();
                resultArea.append("Selected Stop Words File: " + stopWordsFile.getName() + "\n");
            }
        }
    }

    private void analyzeText() {
        // Ensure both files are selected before proceeding
        if (textFile == null || stopWordsFile == null) {
            JOptionPane.showMessageDialog(frame, "Please select both a text file and a stop words file.");
            return;
        }

        // Process the text file using the TagProcessor class
        TagProcessor processor = new TagProcessor();
        try {
            // Extract keywords and their frequencies
            var results = processor.extractKeywords(textFile, stopWordsFile);
            
            // Display the results in the text area
            resultArea.setText(""); // Clear previous results
            results.forEach((word, count) -> resultArea.append(word + ": " + count + "\n"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error analyzing text: " + e.getMessage());
        }
    }

    private void saveResults() {
        // Ensure there are results to save
        if (resultArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No results to save.");
            return;
        }

        // Open a file chooser dialog to select the save location
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showSaveDialog(frame);
        if (choice == JFileChooser.APPROVE_OPTION) { // If the user selected a location
            File saveFile = fileChooser.getSelectedFile();
            try {
                // Save the results using the TagProcessor class
                TagProcessor.saveResults(resultArea.getText(), saveFile);
                JOptionPane.showMessageDialog(frame, "Results saved successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Error saving results: " + e.getMessage());
            }
        }
    }
}
