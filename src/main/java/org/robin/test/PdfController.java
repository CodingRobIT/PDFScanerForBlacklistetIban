package org.robin.test;

import static org.robin.test.PdfDownloader.downloadPdf;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final PdfScanService pdfScanService = new PdfScanService();

    @GetMapping("/health")
    public String healthCheck() {
        return "Server is running!";
    }

    @PostMapping("/scan")
    public ResponseEntity<String> scanPdfFromUrl(@RequestBody Map<String, String> request) {
        // Extracts the URL from the incoming JSON request.
        String url = request.get("url");

        // If no URL is provided, return a 400 Bad Request response.
        if (url == null || url.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("URL is required");
        }

        try {
            // Downloads the PDF from the provided URL.
            File downloadedPdf = downloadPdf(url);

            return pdfScanService.scanPdf(downloadedPdf);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error scanning PDF.");
        }
    }
}
