package org.robin.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Utility class for downloading a PDF file from a given URL.
 */
public class PdfDownloader {

    /**
     * Downloads a PDF file from the specified URL and saves it as a temporary file.
     *
     * @param urlString The URL of the PDF file to download.
     * @return A File object representing the downloaded PDF.
     * @throws IOException If an error occurs during the download process.
     */
    public static File downloadPdf(String urlString) throws IOException {
        // Create a URL object from the provided string.
        URL url = new URL(urlString);

        // Create a temporary file with a ".pdf" extension.
        Path tempFile = Files.createTempFile("downloaded", ".pdf");

        // Open an input stream to read data from the URL and copy it to the temporary file.
        try (InputStream in = url.openStream()) {
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }

        // Return the downloaded file.
        return tempFile.toFile();
    }
}
