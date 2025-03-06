package org.robin.test;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * Utility class for extracting text from a PDF file.
 */
public class PdfTextExtractor {

    /**
     * Extracts all text from a given PDF file.
     *
     * @param file The PDF file from which to extract text.
     * @return A string containing the extracted text.
     * @throws IOException If an error occurs while reading the PDF file.
     */
    public static String extractTextFromPdf(File file) throws IOException {
        // Load the PDF document from the provided file.
        try (PDDocument document = PDDocument.load(file)) {
            // Create a PDFTextStripper to extract text content.
            PDFTextStripper stripper = new PDFTextStripper();

            // Extract and return the text from the document.
            return stripper.getText(document);
        }
    }
}
