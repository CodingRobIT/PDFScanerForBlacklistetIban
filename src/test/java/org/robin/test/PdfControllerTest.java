package org.robin.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PdfControllerTest {

    @Autowired
    private PdfController pdfController;

    @Test
    public void testHealthCheck() throws Exception {
        // GIVEN: A GET request to "/api/pdf/health"
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(pdfController).build();

        // WHEN: The GET request is executed
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pdf/health"))

                // THEN: The response should be HTTP 200 OK
                .andExpect(status().isOk());
    }

    @Test
    public void testScanPdfFromUrl_UrlWithBlacklistedIban() throws Exception {
        // GIVEN: A request Map with a URL containing a blacklisted IBAN (this is e real PDF in dropbox)
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("url", "https://www.dropbox.com/scl/fi/96d3ipn2t57x62p8z9o8x/Testdata_Invoices.pdf?rlkey=ikqizs4ycjz186lj47aqdh2mc&e=1&st=x917lasv&dl=1");

        // WHEN: A POST request is made with this URL
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestMap, headers);
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/api/pdf/scan",
                HttpMethod.POST,
                entity,
                String.class
        );

        // THEN: The response should have status 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Blacklisted IBAN found!", response.getBody());
    }

    @Test
    public void testScanPdfFromUrl_UrlWithNoBlacklistedIban() throws Exception {
        // GIVEN: A request Map with a URL that does not contain a blacklisted IBAN (this is a real dummy PDF file without any IBAN numbers in there)
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("url", "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf");

        // WHEN: A POST request is made with this URL
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestMap, headers);
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/api/pdf/scan",
                HttpMethod.POST,
                entity,
                String.class
        );

        // THEN: The response should have status 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No blacklisted IBAN found.", response.getBody());
    }

    @Test
    public void testScanPdfFromUrl_NoUrl() throws Exception {
        // GIVEN: A request Map with no URL provided
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("url", "");

        // WHEN: A POST request is made with an empty URL
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestMap, headers);
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/api/pdf/scan",
                HttpMethod.POST,
                entity,
                String.class
        );

        // THEN: The response should have status 400 (Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("URL is required", response.getBody());
    }
}

