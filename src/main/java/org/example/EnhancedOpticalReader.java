package org.example;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class EnhancedOpticalReader extends JFrame {

    private Tesseract tesseract;
    private JTextArea resultArea;
    private JButton browseButton;
    private JButton clearButton;
    private JButton increaseButton;
    private JButton decreaseButton;


    public EnhancedOpticalReader() {
        super("KSU OCR Scanner");

        // Setup Tesseract
        tesseract = new Tesseract();
        //tesseract.setDatapath("C:\\TesseractEnhanced\\tessdata");
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR");
        
        tesseract.setLanguage("eng"); // Default language

        // Setup UI
        resultArea = new JTextArea(40, 80);
        resultArea.setWrapStyleWord(true);
        resultArea.setLineWrap(true);
        resultArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        browseButton = new JButton("Select PDF File");
        browseButton.addActionListener(e -> chooseAndScanImage());

        clearButton = new JButton("Clear Text");
        clearButton.addActionListener(e -> clearText());

        increaseButton = new JButton("Increase Font");
        increaseButton.addActionListener(e -> increateFont());

        decreaseButton = new JButton("Decrease Font");
        decreaseButton.addActionListener(e -> decreaseFont());

        JPanel panel = new JPanel();
        panel.add(browseButton);
        panel.add(clearButton);
        panel.add(increaseButton);
        panel.add(decreaseButton);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center window
        this.setVisible(true);
    }

    private void chooseAndScanImage() {
        JFileChooser fileChooser = new JFileChooser("C:\\");
        fileChooser.setDialogTitle("Select a PDF file for OCR");

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            performOCR(selectedFile);
        }
    }

    private void increateFont() {
        //resultArea.setFont(resultArea.getFont().deriveFont(resultArea.getFont().getSize() + 1));
        Font lFont = new Font(resultArea.getFont().getName(), Font.PLAIN, resultArea.getFont().getSize() + 1);
        resultArea.setFont(lFont);
    }

    private void decreaseFont() {
        //resultArea.setFont(resultArea.getFont().deriveFont(resultArea.getFont().getSize() + 1));
        Font lFont = new Font(resultArea.getFont().getName(), Font.PLAIN, resultArea.getFont().getSize() - 1);
        resultArea.setFont(lFont);
    }

    private void clearText() {
        resultArea.setText("");
    }

    private void performOCR(File imageFile) {
        try {
            resultArea.setText("Scanning image...\n");
            String result = tesseract.doOCR(imageFile);
            resultArea.setText(result);
        } catch (TesseractException e) {
            resultArea.setText("Error during OCR: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        //String tessDataPath = args[0];
        SwingUtilities.invokeLater(() -> new EnhancedOpticalReader());
    }
}
