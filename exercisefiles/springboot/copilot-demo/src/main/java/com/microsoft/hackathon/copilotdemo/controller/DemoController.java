package com.microsoft.hackathon.copilotdemo.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

/* 
* Create a GET operation to return the value of a key passed as query parameter. 
* If the key is not passed, return "key not passed".
* If the key is passed, return "hello <key>".
* 
*/

@RestController
public class DemoController {

    private Map<String, String> colorsMap;

    public DemoController() {
        // Load colors from JSON file into the map
        colorsMap = loadColorsFromJson();
    }

    
    private Map<String, String> loadColorsFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Assuming the file is directly under resources folder
            return mapper.readValue(new ClassPathResource("colors.json").getInputStream(), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load colors.json", e);
        }
    }

    @GetMapping("/")
    public String mainPage() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        return "Welcome to the Copilot Demo! <br><br>" +
               "Current Date and Time: " + zonedDateTime.toString() + "<br><br>" +
               "Click <a href=\"/compareDatesHtml\">here</a> to compare dates." +
               "<br><br>" +
               "Click <a href=\"/validatePhoneNumber\">here</a> to validate a phone number." + 
               "<br><br>" +
               "Click <a href=\"/validateSpanishDNI\">here</a> to validate a Spanish DNI." +
               "<br><br>" +
               "Click <a href=\"/color\">here</a> to find the color hex by color name ins path param.";
    }

    @GetMapping("/datetime")
    public String getDateTimeWithTimezone() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        return zonedDateTime.toString();
    }

    @GetMapping("/compareDates")
    public String compareDates(@RequestParam("date1") String date1, @RequestParam("date2") String date2) {
        String returnValue;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate1 = LocalDate.parse(date1, formatter);
            LocalDate parsedDate2 = LocalDate.parse(date2, formatter);
            
            if (parsedDate1.isBefore(parsedDate2)) {
                long daysBetween = parsedDate2.toEpochDay() - parsedDate1.toEpochDay();
                if (daysBetween > 0) {
                    returnValue = "Date 1 is before Date 2. Date 1: " + parsedDate1.toString() + ", Date 2: " + parsedDate2.toString() + ", Days between: " + daysBetween;
                } else if (daysBetween < 0) {
                    returnValue = "Date 1 is after Date 2. Date 1: " + parsedDate1.toString() + ", Date 2: " + parsedDate2.toString() + ", Days between: " + (-daysBetween);
                } else {
                    returnValue = "Date 1 is equal to Date 2. Date 1: " + parsedDate1.toString() + ", Date 2: " + parsedDate2.toString() + ", Days between: 0";
                }
            } else {
                returnValue = "Date 1 is equal to Date 2. Date 1: " + parsedDate1.toString() + ", Date 2: " + parsedDate2.toString() + ", Days between: 0";
            }
        } catch (DateTimeParseException e) {
            returnValue = "Invalid date format. Please provide dates in yyyy-MM-dd format.";
        }
        return returnValue
        .concat("<br><br>")
        .concat("Click <a href=\"/\">here</a> to back to the main page.");
    }
    
    @GetMapping("/compareDatesHtml")
    public String compareDatesHtml() {
        return "<html>\n" +
                "<body>\n" +
                "<h2>Compare Dates</h2>\n" +
                "<form action=\"/compareDates\" method=\"get\">\n" +
                "  <label for=\"date1\">Date 1:</label><br>\n" +
                "  <input type=\"date\" id=\"date1\" name=\"date1\" pattern=\"\\d{2}-\\d{2}-\\d{4}\" required><br><br>\n" +
                "  <label for=\"date2\">Date 2:</label><br>\n" +
                "  <input type=\"date\" id=\"date2\" name=\"date2\"  required><br><br>\n" +
                "  <input type=\"submit\" value=\"Compare\">\n" +
                "</form>\n" +
                "</body>\n" +
                "<br><br>" +
                "Click <a href=\"/\">here</a> to back to the main page." + 
                "</html>";
    }
    
    @GetMapping("/validatePhoneNumber")
    public String validatePhoneNumber(@RequestParam @Nullable String phoneNumber) {
        // Regular expression for a Spanish phone number
        String regex = "^\\+34(6|7|9)\\d{8}$";

        boolean isValid = phoneNumber == null || phoneNumber.matches(regex);

        return "<html>\n" +
                "<body>\n" +
                "<h2>Validate Phone Number</h2>\n" +
                "<form action=\"/validatePhoneNumber\" method=\"get\">\n" +
                "  <label for=\"phoneNumber\">Phone Number:</label><br>\n" +
                "  <input type=\"text\" id=\"phoneNumber\" name=\"phoneNumber\" required><br><br>\n" +
                "  <input type=\"submit\" value=\"Validate\">\n" +
                "</form>\n" +
                "</body>\n" +
                "<p>Provided phone(" + phoneNumber + ") number is " + 
                (isValid ? "valid" : "invalid") + " </p>" + 
                "Click <a href=\"/\">here</a> to back to the main page." + 
               "</html>";
    }

    @GetMapping("/validateSpanishDNI")
    public String validateSpanishDNI(@RequestParam @Nullable String spanishDNI) {
        // Regular expression for a Spanish DNI
        String regex = "^[0-9]{8}[A-Za-z]$";
        boolean isValid = spanishDNI != null && spanishDNI.matches(regex);
        return "<html>\n" +
                "<body>\n" +
                "<h2>Validate Spanish DNI</h2>\n" +
                "<form action=\"/validateSpanishDNI\" method=\"get\">\n" +
                "  <label for=\"spanishDNI\">Spanish DNI:</label><br>\n" +
                "  <input type=\"text\" id=\"spanishDNI\" name=\"spanishDNI\" required><br><br>\n" +
                "  <input type=\"submit\" value=\"Validate\">\n" +
                "</form>\n" +
                "</body>\n" +
                "<p>Provided Spanish DNI (" + spanishDNI + ") is " +
                (isValid ? "valid" : "invalid") + " </p>" +
                "<br><br>" +
                "Click <a href=\"/\">here</a> to back to the main page." + 
                "</html>";
    }

    
    @GetMapping("/colorRest")
    public String getColorHexCode1(@RequestParam @Nullable String colorName) {
        if (colorName == null || colorName.isEmpty()) {
            return "Please provide a color name.";
        }
        String returnValue;
        String hexCode = colorsMap.get(colorName.toLowerCase());
        if (hexCode != null) {
            returnValue = hexCode;
        } else {
            returnValue = "Color not found, try again";
        }
        return returnValue;
        
    }

    @GetMapping("/color")
    public String getColorHexCode(@RequestParam @Nullable String colorName) {

        return """
                <!DOCTYPE html>
                    <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <title>Color Hex Code Finder</title>
                            <script>
                                async function fetchColorHexCode() {
                                    const colorName = document.getElementById('colorName').value;
                                    try {
                                        const response = await fetch(`/colorRest?colorName=${colorName}`);
                                        const text = await response.text();
                                        document.getElementById('colorHexCode').innerHTML = text;
                                    } catch (error) {
                                        console.error('Error fetching color hex code:', error);
                                        document.getElementById('colorHexCode').innerHTML = 'Error fetching color hex code.';
                                    }
                                }
                            </script>
                        </head>
                        <body>
                            <h2>Find Color Hex Code</h2>
                            <label for="colorName">Enter Color Name:</label>
                            <input type="text" id="colorName" name="colorName">
                            <button onclick="fetchColorHexCode()">Get Hex Code</button>
                            <p id="colorHexCode">Color hex code will be displayed here.</p>
                            <br><br>
                            Click <a href=\"/\">here</a> to back to the main page.
                        </body>
                    </html>
                """;
    }
}
