package com.cft.contactmerge.io.tests;


import com.cft.contactmerge.io.TsvImporter;
import com.cft.contactmerge.Contact;
import com.cft.contactmerge.contact.Address;
import com.cft.contactmerge.contact.Phone;
import com.cft.contactmerge.contact.Email;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UnknownFormatConversionException;

import static org.junit.jupiter.api.Assertions.*;

class TsvImporterTest {

    /* --------------------------------------------------------------------------------
     * Testing Load(filename)
     * -------------------------------------------------------------------------------- */

    @Test
    void Load_NullFileName() {
        TsvImporter importer = new TsvImporter();

        assertThrows(IllegalArgumentException.class, () -> importer.Load( (String) null));
    }

    @Test
    void Load_EmptyFileName() {
        TsvImporter importer = new TsvImporter();

        assertThrows(IllegalArgumentException.class, () -> importer.Load(""));
    }

    @Test
    void Load_NonExistentFileName() {
        TsvImporter importer = new TsvImporter();

        assertThrows(FileNotFoundException.class, () -> importer.Load("c:\\DoesNotExist\\DoesNotExist.xml"));
    }

    /* --------------------------------------------------------------------------------
     * Testing Load(inputStream)
     * -------------------------------------------------------------------------------- */
    @Test
    void Load_NullStream() {
        TsvImporter importer = new TsvImporter();

        assertThrows(IllegalArgumentException.class, () -> importer.Load((InputStream) null));
    }

    @Test
    void Load_EmptyStream() throws IOException {
        TsvImporter importer = new TsvImporter();

        String testString = "";

        try (InputStream inputStream = new ByteArrayInputStream(testString.getBytes())) {
            assertThrows(UnknownFormatConversionException.class, () -> importer.Load(inputStream));
        }
    }

    /* --------------------------------------------------------------------------------
     * Testing Iterator
     * -------------------------------------------------------------------------------- */

    private static String testTsv =  "Bidder #\tCRM ID\tType\tSalutation\tFirst\tLast\tCompany\tPhone #\tE-mail\tAddress\tCity\tState\tZip\tCountry\tUser Defined 1\tUser Defined 2\tNotes\tVIP\tTable #\tTable Name\tTicket #\tTicket Package\tExpress Checkout Credit Card\tRSVP Status\tChecked In\n"+"5\t\tPerson\t\tJohn\tDoe\t\t(520) 123-4567\tjdoe@gmail.com\t123 Main St\tTucson\tArizona\t85716\t\t\tBeider\t\tNo\t18\tGroup 1\t3\tComp Ticket-Honoree\t\t\tYes\n"+"5\t\tPerson\t\tAdam\tSmith\t\t(520) 111-2222\tadam.smith@yahoo.com\t1010 Speedway Blvd\tTucson\tAZ\t85716\t\t\t\t\tNo\t18\tGroup 1\t1\tComp Ticket-Honoree\t\t\tYes\n";

    @Test
    void IteratorCalledBeforeLoad() {
        TsvImporter importer = new TsvImporter();

        assertThrows(IllegalStateException.class, () -> importer.iterator().hasNext());
    }

    @Test
    void Load_EmptyDataSet() throws IOException {
        TsvImporter importer = new TsvImporter();

        String testString = " ";

        try (InputStream inputStream = new ByteArrayInputStream(testString.getBytes())) {
            importer.Load(inputStream);
        }

        assertFalse(importer.iterator().hasNext());
    }

    @Test///////
    void IteratorHasNext() throws IOException {
        TsvImporter importer = new TsvImporter();

        try (InputStream inputStream = new ByteArrayInputStream(testTsv.getBytes())) {
            importer.Load(inputStream);
        }

        assertTrue(importer.iterator().hasNext());
    }

    @Test//////
    void IteratorReturnsExpectedData() throws IOException {
        TsvImporter importer = new TsvImporter();

        try (InputStream inputStream = new ByteArrayInputStream(testTsv.getBytes())) {
            importer.Load(inputStream);
        }

        ArrayList<String> lastNames = new ArrayList<String>(Arrays.asList("Doe", "Smith"));
        ArrayList<String> firstNames = new ArrayList<String>(Arrays.asList("John", "Adam"));
        ArrayList<String> addresses = new ArrayList<String>(Arrays.asList("123 Main St", "1010 Speedway Blvd"));
        ArrayList<String> phones = new ArrayList<String>(Arrays.asList("(520) 123-4567", "(520) 111-2222"));
        ArrayList<String> emails = new ArrayList<String>(Arrays.asList("jdoe@gmail.com", "adam.smith@yahoo.com"));

        int i = 0;
        for (Contact contact : importer)
        {
            assertEquals(lastNames.get(i), contact.getName().getValue().getLastName(), String.format("Verify lastNames[%d]", i));
            assertEquals(firstNames.get(i), contact.getName().getValue().getFirstName(), String.format("Verify firstNames[%d]", i));
            assertEquals(addresses.get(i), contact.getAddress().getValue().getStreet(), String.format("Verify addresses[%d]", i));
            assertEquals(phones.get(i), contact.getPhone().toString(), String.format("Verify phones[%d]", i));
            assertEquals(emails.get(i), contact.getEmail().toString(), String.format("Verify emails[%d]", i));

            i++;
        }

        assertEquals(2, i, "Verify contact count");
    }

    private void RunMissingDataTest(boolean streetAddressSpecified,
                                    boolean citySpecified,
                                    boolean stateSpecified,
                                    boolean zipSpecified,
                                    boolean phoneSpecified,
                                    boolean emailSpecified) throws IOException {

        String testData = "Bidder #\tCRM ID\tType\tSalutation\tFirst\tLast\tCompany\tPhone #\tE-mail\tAddress\tCity\tState\tZip\tCountry\tUser Defined 1\tUser Defined 2\tNotes\tVIP\tTable #\tTable Name\tTicket #\tTicket Package\tExpress Checkout Credit Card\tRSVP Status\tChecked In\n"+"5\t\tPerson\t\tJohn\tDoe\t\t(520) 123-4567\tjdoe@gmail.com\t123 Main St\tTucson\tAZ\t85716\t\t\tBeider\t\tNo\t18\tGroup 1\t3\tComp Ticket-Honoree\t\t\tYes\n";

        if (!streetAddressSpecified) {
            testData = testData.replaceAll("123 Main St", "");
        }

        if (!citySpecified) {
            testData = testData.replaceAll("Tucson", "");
        }

        if (!stateSpecified) {
            testData = testData.replaceAll("AZ", "");
        }

        if (!zipSpecified) {
            testData = testData.replaceAll("85716", "");
        }

        if (!phoneSpecified) {
            testData = testData.replaceAll("\\(520\\) 123\\-4567", "");
        }

        if (!emailSpecified) {
            testData = testData.replaceAll("jdoe@gmail.com", "");
        }

        TsvImporter importer = new TsvImporter();

        try (InputStream inputStream = new ByteArrayInputStream(testData.getBytes())) {
            importer.Load(inputStream);
        }

        Contact contact = importer.iterator().next();

        assertEquals("Doe", contact.getName().getValue().getLastName(), "Verify LastName");
        assertEquals("John", contact.getName().getValue().getFirstName(), "Verify FirstName");

        if (streetAddressSpecified && citySpecified && stateSpecified && zipSpecified ) {
            assertEquals("123 Main St", contact.getAddress().getValue().getStreet(), "Verify StreetAddress");
            assertEquals("Tucson", contact.getAddress().getValue().getCity(), "Verify City");
            assertEquals("AZ", contact.getAddress().getValue().getState(), "Verify State");
            assertEquals("85716", contact.getAddress().getValue().getZip(), "Verify Zip");
        }
        else {
            assertEquals( (new Address()).toString(), contact.getAddress().toString(), "Verify Address");
        }

        if (phoneSpecified) {
            assertEquals("(520) 123-4567", contact.getPhone().toString(), "Verify Phone");
        }
        else {
            assertEquals( (new Phone()).toString(), contact.getPhone().toString(), "Verify Phone");
        }

        if (emailSpecified) {
            assertEquals("jdoe@gmail.com", contact.getEmail().toString(), "Verify Email");
        }
        else {
            assertEquals( (new Email()).toString(), contact.getEmail().toString(), "Verify Email");
        }
    }

    @Test
    void Iterator_MissingEmailAndPhone() throws IOException {
        RunMissingDataTest(true, true, true, true,
                false, false);
    }

    @Test
    void Iterator_MissingStreetAddress() throws IOException {
        RunMissingDataTest(false, true, true, true,
                true, true);
    }

    @Test
    void Iterator_MissingCity() throws IOException {
        RunMissingDataTest(true, false, true, true,
                true, true);
    }

    @Test
    void Iterator_MissingState() throws IOException {
        RunMissingDataTest(true, true, false, true,
                true, true);
    }

    @Test
    void Iterator_MissingZip() throws IOException {
        RunMissingDataTest(true, true, true, false,
                true, true);
    }

}