package org.robin.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// This is the main entry point of the application. It starts the Spring Boot server.
public class PdfApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfApiApplication.class, args);
	}

}

/*
 Use Insomnia or Postman to test the API.

 A GET endpoint is available to check if the server is running.
 To perform a health check, send a GET request to:
 localhost:8080/api/pdf/health
 If the server is running, you will receive a 200 response with the message: "Server is running!"

 To scan a PDF file via URL:
 1. Send a POST request to:
    localhost:8080/api/pdf/scan
 2. This endpoint requires a direct download link to the PDF file in a JSON body.
 3. The request body (Body > raw > JSON) should be formatted as follows:
 	this url contains a blacklisted IBAN and returns 400 Blacklisted IBAN found!
 {
    "url": "https://www.dropbox.com/scl/fi/96d3ipn2t57x62p8z9o8x/Testdata_Invoices.pdf?rlkey=ikqizs4ycjz186lj47aqdh2mc&e=1&st=x917lasv&dl=1"
 }

 and this is a Dummy PDF without IBAN and returns 200 No blacklisted IBAN found.
 {
 "url": "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
 }


 Max Size of the PDF is 50MB >>> application.properties to change PDF size

 The test PDF Contains this IBAN DE15 3006 0601 0505 7807 80 and this IBAN is in the Blacklist
 Out of the Box the Code should get an 400 Bad Request with the message Blacklisted IBAN found!
 to get an 200 go to BlacklistService change or Delete the DE15 3006 0601 0505 7807 80 or
 add another link with another test PDF link.

 Important the PDF link must be a Download File.

 In Dropbox when you get the "link" it will lead you to the DropBox site to make the link Downloadable just replace
 https://www.dropbox.com/scl/fi/96d...7lasv&dl=0 <<< this 0 with an 1
 https://www.dropbox.com/scl/fi/96d...7lasv&dl=1 <<< and this works then.
*/
