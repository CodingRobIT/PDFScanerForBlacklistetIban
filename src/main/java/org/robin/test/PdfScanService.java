package org.robin.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PdfScanService {

    private final BlacklistService blacklistService = new BlacklistService();

    public ResponseEntity<String> scanPdf(File pdfFile) throws IOException {
        // Extract the text from the PDF
        String text = PdfTextExtractor.extractTextFromPdf(pdfFile);

        // Get the blacklist  Todo: optimize that.
        List<String> blacklistedIbans = blacklistService.getBlacklistedIbans();

        // Check if an IBAN is on the blacklist
        if (IbanValidator.containsBlacklistedIban(text, blacklistedIbans)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Blacklisted IBAN found!");
        }

        return ResponseEntity.ok("No blacklisted IBAN found.");
    }
}
