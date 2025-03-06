package org.robin.test;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.robin.test.IbanValidator.getNormalizedText;
import static org.robin.test.IbanValidator.getNormalizeIban;

public class IbanValidatorTest {

    @Test
    public void testContainsBlacklistedIban_BlacklistedIbanPresent() {
        // GIVEN
        List<String> blacklist = Arrays.asList("DE12123456789012345678", "FR1420041010050500013M02606");
        String text = "DE12123456789012345678.";

        // WHEN
        boolean result = IbanValidator.containsBlacklistedIban(text, blacklist);

        // THEN
        assertTrue(result);
    }

    @Test
    public void testContainsBlacklistedIban_NoBlacklistedIbanPresent() {
        // GIVEN
        List<String> blacklist = Arrays.asList("DE12123456789012345678", "FR1420041010050500013M02606");
        String text = "DE89370400440532013000.";

        // WHEN
        boolean result = IbanValidator.containsBlacklistedIban(text, blacklist);

        // THEN
        assertFalse(result);
    }

    @Test
    public void testContainsBlacklistedIban_BlacklistedIbanWithSpaces() {
        // GIVEN
        List<String> blacklist = Arrays.asList("DE12 1234 5678 9012 3456 78", "FR14 2004 1010 0505 0001 3M02 606");
        String text = "DE12123456789012345678.";

        // WHEN
        boolean result = IbanValidator.containsBlacklistedIban(text, blacklist);

        // THEN
        assertTrue(result);
    }

    @Test
    public void testContainsBlacklistedIban_BlacklistedIbanInDifferentCase() {
        // GIVEN
        List<String> blacklist = Arrays.asList("de12123456789012345678", "fr1420041010050500013m02606");
        String text = "DE12123456789012345678.";

        // WHEN
        boolean result = IbanValidator.containsBlacklistedIban(text, blacklist);

        // THEN
        assertTrue(result);
    }

    @Test
    public void testContainsBlacklistedIban_BlacklistedIbanTruncated() {
        // GIVEN
        List<String> blacklist = Arrays.asList("DE12123456789012345678", "FR1420041010050500013M02606");
        String text = "DE121234567890123456789012345678.";

        // WHEN
        boolean result = IbanValidator.containsBlacklistedIban(text, blacklist);

        // THEN
        assertTrue(result);
    }

    @Test
    public void testContainsBlacklistedIban_NoIbanInText() {
        // GIVEN
        List<String> blacklist = Arrays.asList("DE12123456789012345678", "FR1420041010050500013M02606");
        String text = "This is a test text without any IBAN.";

        // WHEN
        boolean result = IbanValidator.containsBlacklistedIban(text, blacklist);

        // THEN
        assertFalse(result);
    }

    @Test
    public void testContainsBlacklistedIban_EmptyBlacklist() {
        // GIVEN
        List<String> blacklist = Arrays.asList();
        String text = "No black list here should everything work DE89370400440532013000.";

        // WHEN
        boolean result = IbanValidator.containsBlacklistedIban(text, blacklist);

        // THEN
        assertFalse(result);
    }

    @Test
    public void testContainsBlacklistedIban_EmptyText() {
        // GIVEN
        List<String> blacklist = Arrays.asList("DE12123456789012345678", "FR1420041010050500013M02606");
        String text = "";

        // WHEN
        boolean result = IbanValidator.containsBlacklistedIban(text, blacklist);

        // THEN
        assertFalse(result);
    }

    @Test
    public void testContainsBlacklistedIban_BlacklistedIbanMixedCase() {
        // GIVEN
        List<String> blacklist = Arrays.asList("DE12123456789012345678", "FR1420041010050500013M02606");
        String text = "dE12123456789012345678.";

        // WHEN
        boolean result = IbanValidator.containsBlacklistedIban(text, blacklist);

        // THEN
        assertTrue(result);
    }

    @Test
    void testGetNormalizedText_removesWhitespaceAndUppercases() {
        // GIVEN
        String input = "  hello world ";

        // WHEN
        String result = getNormalizedText(input);

        // THEN
        assertEquals("HELLOWORLD", result);
    }

    @Test
    void testGetNormalizedText_emptyString() {
        // GIVEN
        String input = "";

        // WHEN
        String result = getNormalizedText(input);

        // THEN
        assertEquals("", result);
    }

    @Test
    void testGetNormalizedText_onlyWhitespace() {
        // GIVEN
        String input = "   \t\n ";

        // WHEN
        String result = getNormalizedText(input);

        // THEN
        assertEquals("", result);
    }

    @Test
    void testGetNormalizeIban_shorterThan22Chars() {
        // GIVEN
        String iban = "DE893704";

        // WHEN
        String result = getNormalizeIban(iban);

        // THEN
        assertEquals("DE893704", result);
    }

    @Test
    void testGetNormalizeIban_exactly22Chars() {
        // GIVEN
        String iban = "DE89370400440532013000";

        // WHEN
        String result = getNormalizeIban(iban);

        // THEN
        assertEquals("DE89370400440532013000", result);
    }

    @Test
    void testGetNormalizeIban_longerThan22Chars() {
        // GIVEN
        String iban = "DE8937040044053201300012345678";

        // WHEN
        String result = getNormalizeIban(iban);

        // THEN
        assertEquals("DE89370400440532013000", result);
    }

}
