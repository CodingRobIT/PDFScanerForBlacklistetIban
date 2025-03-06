## API Documentation - PDF IBAN Scanner

Use Insomnia or Postman to test the API.

### 1️⃣ Health Check (GET)
- **URL**: `http://localhost:8080/api/pdf/health`
- **Response**: `"Server is running!"` (200 OK)

### 2️⃣ Scan a PDF for Blacklisted IBANs (POST)
- **URL**: `http://localhost:8080/api/pdf/scan`
- **Request Body (JSON)**:
  ```json
  {
      "url": "DIRECT_DOWNLOAD_LINK_TO_PDF"
  }
  
The PDF must be a direct download link (not a web preview).
Max PDF size: 50MB (configurable in application.properties).
### 3️⃣ Expected Behavior:
If a blacklisted IBAN is found:
Response: 400 Bad Request
Message: "Blacklisted IBAN found!"
If no blacklisted IBAN is found:
Response: 200 OK
Message: "No blacklisted IBAN found."
### 4️⃣ Testing with Example PDF:
Example URL: "https://www.dropbox.com/scl/fi/96d3ipn2t57x62p8z9o8x/Testdata_Invoices.pdf?rlkey=ikqizs4ycjz186lj47aqdh2mc&e=1&st=x917lasv&dl=1"
This test PDF contains the IBAN: DE15 3006 0601 0505 7807 80 (which is blacklisted).
To receive a 200 OK response, remove or modify this IBAN in BlacklistService.
### 5️⃣ Important: How to Use Dropbox Links as Direct Downloads
Dropbox links by default open in the browser instead of downloading.

To make the link a direct download, replace dl=0 with dl=1 in the URL:

❌ Non-downloadable: https://www.dropbox.com/...&dl=0

✅ Downloadable: https://www.dropbox.com/...&dl=1
### 6️⃣ Start Application
- To start the application, you can either:
  - Click the **green play arrow** in `PdfApiApplication.java` within IntelliJ IDEA to run the application directly.
  - Or, you can start the application via the console by navigating to the project directory and running the following command:
    ```bash
    mvn spring-boot:run
    ```
    Alternatively, if you have built the JAR file, you can run it using:
    ```bash
    java -jar target/pdf-iban-scanner.jar
    ```
- The application will start on `http://localhost:8080`.
