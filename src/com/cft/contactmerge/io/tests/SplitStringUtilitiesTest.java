package com.cft.contactmerge.io.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.io.*;
import java.util.*;

class SplitStringUtilitiesTest {

    private String createVerificationMessage(String itemName)
    {
        return String.format("Verify %s", itemName);
    }

    /* ----------------------------------------------------------
     * TSV Tests
     * ---------------------------------------------------------- */
    @Test
    void splitTsvString_EmptyString() {
        List<String> parts = SplitStringUtilities.splitTsvString("");

        assertEquals(1, parts.size(), createVerificationMessage("result size"));
        assertEquals( "", parts.iterator().next(), createVerificationMessage("first result"));
    }

    @Test
    void splitTsvString_SingleValue() {
        List<String> parts = SplitStringUtilities.splitTsvString("Joe");

        assertEquals(1, parts.size(), createVerificationMessage("result size"));
        assertEquals( "Joe", parts.iterator().next(), "first result");
    }

    @Test
    void splitTsvString_MultipleValues() {
        List<String> expectedParts = Arrays.asList("Joe", "Smith", "123 Main St");
        String testString = String.join("\t", expectedParts);

        List<String> actualParts = SplitStringUtilities.splitTsvString(testString);

        assertEquals(3, actualParts.size(), createVerificationMessage("result size"));

        for(int i=0; i < actualParts.size(); i++) {
            assertEquals(expectedParts.get(i), actualParts.get(i), String.format("result[%d]", i));
        }
    }

    @Test
    void splitTsvString_MissingValues() {
        List<String> expectedParts = Arrays.asList("", "", "Smith", "");
        String testString = String.join("\t", expectedParts);

        List<String> actualParts = SplitStringUtilities.splitTsvString(testString);

        assertEquals(4, actualParts.size(), createVerificationMessage("result size"));

        for(int i=0; i < actualParts.size(); i++) {
            assertEquals(expectedParts.get(i), actualParts.get(i), String.format("result[%d]", i));
        }
    }

    @Test
    void splitTsvString_LeadingTrailingSpacesTrimmed() {
        List<String> expectedParts = Arrays.asList("   ", " Smith  ");
        String testString = String.join("\t", expectedParts);

        List<String> actualParts = SplitStringUtilities.splitTsvString(testString);

        assertEquals(2, actualParts.size(), createVerificationMessage("result size"));

        assertEquals("", actualParts.get(0), "result[0]");
        assertEquals("Smith", actualParts.get(1), "result[1]");
    }

    /* ----------------------------------------------------------
     * CSV Tests
     * ---------------------------------------------------------- */
    @Test
    void splitCsvString_EmptyString() {
        List<String> parts = SplitStringUtilities.splitCsvString("");

        assertEquals(1, parts.size(), createVerificationMessage("result size"));
        assertEquals( "", parts.iterator().next(), createVerificationMessage("first result"));
    }

    @Test
    void splitCsvString_SingleValue() {
        List<String> parts = SplitStringUtilities.splitCsvString("Joe");

        assertEquals(1, parts.size(), createVerificationMessage("result size"));
        assertEquals( "Joe", parts.iterator().next(), "first result");
    }

    @Test
    void splitCsvString_MultipleValues() {
        List<String> expectedParts = Arrays.asList("Joe", "Smith", "123 Main St");
        String testString = String.join(",", expectedParts);

        List<String> actualParts = SplitStringUtilities.splitCsvString(testString);

        assertEquals(3, actualParts.size(), createVerificationMessage("result size"));

        for(int i=0; i < actualParts.size(); i++) {
            assertEquals(expectedParts.get(i), actualParts.get(i), String.format("result[%d]", i));
        }
    }

    @Test
    void splitCsvString_MissingValues() {
        List<String> expectedParts = Arrays.asList("", "Smith", "", "");
        String testString = String.join(",", expectedParts);

        List<String> actualParts = SplitStringUtilities.splitCsvString(testString);

        assertEquals(4, actualParts.size(), createVerificationMessage("result size"));

        for(int i=0; i < actualParts.size(); i++) {
            assertEquals(expectedParts.get(i), actualParts.get(i), String.format("result[%d]", i));
        }
    }

    @Test
    void splitCsvString_LeadingTrailingSpacesTrimmed() {
        List<String> expectedParts = Arrays.asList("   ", " Smith  ");
        String testString = String.join(",", expectedParts);

        List<String> actualParts = SplitStringUtilities.splitCsvString(testString);

        assertEquals(2, actualParts.size(), createVerificationMessage("result size"));

        assertEquals("", actualParts.get(0), "result[0]");
        assertEquals("Smith", actualParts.get(1), "result[1]");
    }

    @Test
    void splitCsvString_SingleQuotedValue() {
        List<String> parts = SplitStringUtilities.splitCsvString("\"Smith, Joe\"");

        assertEquals(1, parts.size(), createVerificationMessage("result size"));
        assertEquals( "Smith, Joe", parts.iterator().next(), "first result");
    }

    @Test
    void splitCsvString_MixQuotedNonQuoted() {
        List<String> parts = SplitStringUtilities.splitCsvString("\"Joe\", Smith, \"123 Main St, Apt 10\"");

        Iterator<String> partsIterator = parts.iterator();
        assertEquals(3, parts.size(), createVerificationMessage("result size"));
        assertEquals( "Joe", partsIterator.next(), "result[0]");
        assertEquals( "Smith", partsIterator.next(), "result[1]");
        assertEquals( "123 Main St, Apt 10", partsIterator.next(), "result[2]");
    }

    @Test
    void splitCsvString_TextAfterQuoted() {
        List<String> parts = SplitStringUtilities.splitCsvString("\"Joe\", \"Adam\"son, \"123 Main St\"");

        Iterator<String> partsIterator = parts.iterator();
        assertEquals(3, parts.size(), createVerificationMessage("result size"));
        assertEquals( "Joe", partsIterator.next(), "result[0]");
        assertEquals( "Adam", partsIterator.next(), "result[1]");
        assertEquals( "123 Main St", partsIterator.next(), "result[2]");
    }

    @Test
    void splitCsvString_TextAfterLastQuoted() {
        List<String> parts = SplitStringUtilities.splitCsvString("\"Joe\", \"Adam\"son, \"123 Main St\"reet");

        Iterator<String> partsIterator = parts.iterator();
        assertEquals(3, parts.size(), createVerificationMessage("result size"));
        assertEquals( "Joe", partsIterator.next(), "result[0]");
        assertEquals( "Adam", partsIterator.next(), "result[1]");
        assertEquals( "123 Main St", partsIterator.next(), "result[2]");
    }

    @Test
    void splitCsvString_TextBeforeQuoted() {
        List<String> parts = SplitStringUtilities.splitCsvString("\"Joe\", Adam\"son\", \"123 Main St\"");

        Iterator<String> partsIterator = parts.iterator();
        assertEquals(3, parts.size(), createVerificationMessage("result size"));
        assertEquals( "Joe", partsIterator.next(), "result[0]");
        assertEquals( "Adam\"son\"", partsIterator.next(), "result[1]");
        assertEquals( "123 Main St", partsIterator.next(), "result[2]");
    }

    @Test
    void splitCsvString_TextBeforeQuotedWithComma() {
        List<String> parts = SplitStringUtilities.splitCsvString("\"Joe\", Adam\"s,on\", \"123 Main St\"");

        Iterator<String> partsIterator = parts.iterator();
        assertEquals(4, parts.size(), createVerificationMessage("result size"));
        assertEquals( "Joe", partsIterator.next(), "result[0]");
        assertEquals( "Adam\"s", partsIterator.next(), "result[1]");
        assertEquals( "on\"", partsIterator.next(), "result[2]");
        assertEquals( "123 Main St", partsIterator.next(), "result[3]");


    }

}