package org.robin.test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for validating IBANs in text.
 */
public class IbanValidator {
    /*
     Regular expression pattern to match IBANs in a text.
     \\b asserts a word boundary, ensuring the IBAN is not part of a larger word.
    [A-Z]{2} matches the 2-letter country code (e.g., "DE" for Germany).
    \\d{2} matches the 2-digit check digits (e.g., "15").
    [A-Z0-9]{1,30} matches the alphanumeric part of the IBAN, which can be between 1 and 30 characters long (including account number and bank code).
    b asserts another word boundary at the end of the IBAN, ensuring it's a complete entity. */
    private static final Pattern IBAN_PATTERN = Pattern.compile("\\b[A-Z]{2}\\d{2}[A-Z0-9]{1,30}\\b");

    /**
     * Checks if the given text contains any blacklisted IBAN.
     *
     * @param text      The text to scan for IBANs.
     * @param blacklist A list of blacklisted IBANs to check against.
     * @return true if a blacklisted IBAN is found, otherwise false.
     */
    public static boolean containsBlacklistedIban(String text, List<String> blacklist) {
        // Normalize text: remove spaces and convert to uppercase for consistent comparison.
        String normalizedText = getNormalizedText(text);

        // Search for IBAN patterns in the text.
        Matcher matcher = IBAN_PATTERN.matcher(normalizedText);
        while (matcher.find()) {
            // Extract the found IBAN.
            String iban = matcher.group();
            // Normalize the IBAN to match blacklisted entries.
            String normalizedIban = getNormalizeIban(iban);

            // Compare with each blacklisted IBAN.
            for (String blacklistedIban : blacklist) {
                String normalizedBlacklistedIban = getNormalizedText(blacklistedIban);
                if (normalizedIban.equals(normalizedBlacklistedIban)) {
                    return true; // Return true if a match is found.
                }
            }
        }
        return false; // No blacklisted IBAN found.
    }

    /**
     * Normalizes the given text by removing all whitespace characters and converting it to uppercase.
     *
     * @param text The input text to normalize.
     * @return The normalized text without spaces and in uppercase.
     */
    static String getNormalizedText(String text) {
        return text.replaceAll("\\s", "").toUpperCase();
    }

    /**
     * Normalizes an IBAN by truncating it to 22 characters (common IBAN length).
     *
     * @param iban The IBAN to normalize.
     * @return A truncated IBAN if it's longer than 22 characters; otherwise, the original IBAN.
     */
    static String getNormalizeIban(String iban) {
        if (iban.length() > 22) {
            return iban.substring(0, 22);
        }
        return iban;
    }
}

