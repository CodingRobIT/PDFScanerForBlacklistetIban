#API Documentation - PDF IBAN Scanner
Use Insomnia or Postman to test the API.

### 1️⃣ Health Check (GET)
URL: http://localhost:8080/api/pdf/health
Response: "Server is running!" (200 OK)
### 2️⃣ Scan a PDF for Blacklisted IBANs (POST)
URL: http://localhost:8080/api/pdf/scan
```JSON
{
    "url": "DIRECT_DOWNLOAD_LINK_TO_PDF"
}
```
Requirements:
The PDF must be a direct download link (not a web preview).
Maximum PDF size: 50MB (configurable in application.properties).
### 3️⃣ Expected Behavior
If a blacklisted IBAN is found:
Response: 400 Bad Request
Message: "Blacklisted IBAN found!"
If no blacklisted IBAN is found:
Response: 200 OK
Message: "No blacklisted IBAN found."
### 4️⃣ Testing with Example PDFs
Example URL with a blacklisted IBAN:
```JSON
{
    "url": "https://www.dropbox.com/scl/fi/96d3ipn2t57x62p8z9o8x/Testdata_Invoices.pdf?rlkey=ikqizs4ycjz186lj47aqdh2mc&e=1&st=x917lasv&dl=1"
}
```
This test PDF contains the IBAN: DE15 3006 0601 0505 7807 80, which is blacklisted.

When using this, the response should be:
Status Code: 400 Bad Request
Message: "Blacklisted IBAN found!"

#### To receive a 200 OK response, either:

Remove or modify the IBAN in BlacklistService, or
Use this alternative PDF (which contains no blacklisted IBANs):
```JSON
{
    "url": "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
}
```

### 5️⃣ Important: How to Use Dropbox Links as Direct Downloads
Dropbox links by default open in the browser instead of downloading.

To convert a Dropbox link into a direct download, replace dl=0 with dl=1 in the URL:

❌ Non-downloadable: https://www.dropbox.com/...&dl=0
✅ Downloadable: https://www.dropbox.com/...&dl=1

### 6️⃣ Start Application
To start the application, you can either:

- Click the green play button in PdfApiApplication.java within IntelliJ IDEA to run the application directly.

- Or start it via the console by navigating to the project directory and running:
`mvn spring-boot:run`
Alternatively, if you have built the JAR file, start it with:
`java -jar target/pdf-iban-scanner.jar`
The application will be available at:
`http://localhost:8080`
