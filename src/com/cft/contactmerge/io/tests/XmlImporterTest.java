package com.cft.contactmerge.io.tests;

import com.cft.contactmerge.Contact;
import com.cft.contactmerge.contact.Address;
import com.cft.contactmerge.contact.Phone;
import com.cft.contactmerge.contact.Email;
import com.cft.contactmerge.io.XmlImporter;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class XmlImporterTest {

    /* --------------------------------------------------------------------------------
     * Testing Load(filename)
     * -------------------------------------------------------------------------------- */
    @Test
    void Load_NullFileName() {
        XmlImporter importer = new XmlImporter();

        assertThrows(IllegalArgumentException.class, () -> importer.Load( (String) null));
    }

    @Test
    void Load_EmptyFileName() {
        XmlImporter importer = new XmlImporter();

        assertThrows(IllegalArgumentException.class, () -> importer.Load(""));
    }

    @Test
    void Load_NonExistentFileName() {
        // TODO: Would be great if we could test this without access the file system but articles
        // I've read indicate you shouldn't try to mock File. How else to do it?
        XmlImporter importer = new XmlImporter();

        assertThrows(FileNotFoundException.class, () -> importer.Load("c:\\DoesNotExist\\DoesNotExist.xml"));
    }

    /* --------------------------------------------------------------------------------
     * Testing Load(inputStream)
     * -------------------------------------------------------------------------------- */
    @Test
    void Load_NullStream() {
        XmlImporter importer = new XmlImporter();

        assertThrows(IllegalArgumentException.class, () -> importer.Load((InputStream) null));
    }

    @Test
    void Load_EmptyStream() throws IOException {
        XmlImporter importer = new XmlImporter();

        String testString = "";

        try (InputStream inputStream = new ByteArrayInputStream(testString.getBytes())) {
            assertThrows(UnknownFormatConversionException.class, () -> importer.Load(inputStream));
        }
    }

    /* --------------------------------------------------------------------------------
     * Testing Iterator
     * -------------------------------------------------------------------------------- */
    private static String xmlPrefix = "<SharpGridExport>\n" +
            " <RowDefinition count=\"23\">\n" +
            "  <Field alias=\"c1\" name=\"donor_donorid\" type=\"3\"/>\n" +
            "  <Field alias=\"c2\" name=\"indiv_firstname\" type=\"8\"/>\n" +
            "  <Field alias=\"c3\" name=\"indiv_lastname\" type=\"8\"/>\n" +
            "  <Field alias=\"c4\" name=\"donor_phone\" type=\"8\"/>\n" +
            "  <Field alias=\"c5\" name=\"donor_email\" type=\"8\"/>\n" +
            "  <Field alias=\"c6\" name=\"donor_address1\" type=\"8\"/>\n" +
            "  <Field alias=\"c7\" name=\"donor_address2\" type=\"8\"/>\n" +
            "  <Field alias=\"c8\" name=\"donor_city\" type=\"8\"/>\n" +
            "  <Field alias=\"c9\" name=\"donor_state\" type=\"8\"/>\n" +
            "  <Field alias=\"c10\" name=\"donor_zip\" type=\"8\"/>\n" +
            " </RowDefinition>\n" +
            " <Data count=\"2\">\n";

    private static String xmlSuffix = "</Data>\n" +
            "</SharpGridExport>\n";

    private String createTestStream(String dataElements)
    {
        return xmlPrefix + dataElements + xmlSuffix;
    }

    @Test
    void IteratorCalledBeforeLoad() {
        XmlImporter importer = new XmlImporter();

        assertThrows(IllegalStateException.class, () -> importer.iterator().hasNext());
    }

    @Test
    void Load_EmptyDataSet() throws IOException {
        XmlImporter importer = new XmlImporter();

        String testString = "<Data></Data>";

        try (InputStream inputStream = new ByteArrayInputStream(testString.getBytes())) {
            importer.Load(inputStream);
        }

        assertFalse(importer.iterator().hasNext());
    }

    @Test
    void Iterator_HasNext() throws IOException {
        String testData = "  <row c1=\"13252\" c2=\"John\" c3=\"Doe\" c4=\"\" c5=\"\" c6=\"\" c7=\"\" c8=\"\" c9=\"\" c10=\"\" />\n";

        XmlImporter importer = new XmlImporter();

        try (InputStream inputStream = new ByteArrayInputStream(createTestStream(testData).getBytes())) {
           importer.Load(inputStream);
        }

        assertTrue(importer.iterator().hasNext());
    }

    @Test
    void Iterator_HasNext_FalseBecauseMissingName() throws IOException {
        String testData = "  <row c1=\"13252\" c2=\"\" c3=\"Doe\" c4=\"\" c5=\"\" c6=\"\" c7=\"\" c8=\"\" c9=\"\" c10=\"\" />\n" +
                "  <row c1=\"13252\" c2=\"John\" c3=\"\" c4=\"\" c5=\"\" c6=\"\" c7=\"\" c8=\"\" c9=\"\" c10=\"\" />\n";

        XmlImporter importer = new XmlImporter();

        try (InputStream inputStream = new ByteArrayInputStream(createTestStream(testData).getBytes())) {
            importer.Load(inputStream);
        }

        assertFalse(importer.iterator().hasNext());
    }

    @Test
    void Iterator() throws IOException {

        String testData = "  <row c1=\"13252\" c2=\"John\" c3=\"Doe\" c4=\"(520) 123-4567\" c5=\"jdoe@gmail.com\" c6=\"123 Main St\" c7=\"\" c8=\"Tucson\" c9=\"AZ\" c10=\"85750\" />\n" +
                "  <row c1=\"13253\" c2=\"Adam\" c3=\"Smith\" c4=\"(520) 111-2222\" c5=\"adam.smith@yahoo.com\" c6=\"1010 Speedway Blvd\" c7=\"\" c8=\"Tucson\" c9=\"AZ\" c10=\"85750\" />\n";

        XmlImporter importer = new XmlImporter();

        try (InputStream inputStream = new ByteArrayInputStream(createTestStream(testData).getBytes())) {
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
            assertEquals(lastNames.get(i), contact.getName().getValue().getLastName(),
                    String.format("Verify lastNames[%d]", i));
            assertEquals(firstNames.get(i), contact.getName().getValue().getFirstName(),
                    String.format("Verify firstNames[%d]", i));
            assertEquals(addresses.get(i), contact.getAddress().getValue().getStreet(),
                    String.format("Verify addresses[%d]", i));
            assertEquals(phones.get(i), contact.getPhone().toString(),
                    String.format("Verify phones[%d]", i));
            assertEquals(emails.get(i), contact.getEmail().toString(),
                    String.format("Verify emails[%d]", i));

            i++;
        }

        assertEquals(2, i, "Verify contact count");
    }

    @Test
    void Iterator_SkipInvalidContacts() throws IOException {

        String testData = "  <row c1=\"13252\" c2=\"\" c3=\"Doe\" c4=\"(520) 123-4567\" c5=\"jdoe@gmail.com\" c6=\"123 Main St\" c7=\"\" c8=\"Tucson\" c9=\"AZ\" c10=\"85750\" />\n" +
                "  <row c1=\"13252\" c2=\"John\" c3=\"\" c4=\"(520) 123-4567\" c5=\"jdoe@gmail.com\" c6=\"123 Main St\" c7=\"\" c8=\"Tucson\" c9=\"AZ\" c10=\"85750\" />\n" +
                "  <row c1=\"13253\" c2=\"Adam\" c3=\"Smith\" c4=\"(520) 111-2222\" c5=\"adam.smith@yahoo.com\" c6=\"1010 Speedway Blvd\" c7=\"\" c8=\"Tucson\" c9=\"AZ\" c10=\"85750\" />\n";

        XmlImporter importer = new XmlImporter();

        try (InputStream inputStream = new ByteArrayInputStream(createTestStream(testData).getBytes())) {
            importer.Load(inputStream);
        }

        int i = 0;
        for (Contact contact : importer)
        {
            assertEquals("Smith", contact.getName().getValue().getLastName(),
                    String.format("Verify lastNames[%d]", i));
            assertEquals("Adam", contact.getName().getValue().getFirstName(),
                    String.format("Verify firstNames[%d]", i));

            i++;
        }

        assertEquals(1, i, "Verify contact count");
    }

    private void RunMissingDataTest(boolean streetAddressSpecified,
                                    boolean citySpecified,
                                    boolean stateSpecified,
                                    boolean zipSpecified,
                                    boolean phoneSpecified,
                                    boolean emailSpecified) throws IOException {

        String testData = "  <row c1=\"13252\" c2=\"John\" c3=\"Doe\" c4=\"\" c5=\"\" c6=\"\" c7=\"\" c8=\"\" c9=\"\" c10=\"\" />\n";

        if (streetAddressSpecified) {
            testData = testData.replaceAll("c6=\"\"", "c6=\"123 Main St\"");
        }

        if (citySpecified) {
            testData = testData.replaceAll("c8=\"\"", "c8=\"Tucson\"");
        }

        if (stateSpecified) {
            testData = testData.replaceAll("c9=\"\"", "c9=\"AZ\"");
        }

        if (zipSpecified) {
            testData = testData.replaceAll("c10=\"\"", "c10=\"85750\"");
        }

        if (phoneSpecified) {
            testData = testData.replaceAll("c4=\"\"", "c4=\"(520) 123-4567\"");
        }

        if (emailSpecified) {
            testData = testData.replaceAll("c5=\"\"", "c5=\"jdoe@gmail.com\"");
        }

        XmlImporter importer = new XmlImporter();

        try (InputStream inputStream = new ByteArrayInputStream(createTestStream(testData).getBytes())) {
            importer.Load(inputStream);
        }

        Contact contact = importer.iterator().next();

        assertEquals("Doe", contact.getName().getValue().getLastName(), "Verify LastName");
        assertEquals("John", contact.getName().getValue().getFirstName(), "Verify FirstName");

        if (streetAddressSpecified && citySpecified && stateSpecified && zipSpecified ) {
            assertEquals("123 Main St", contact.getAddress().getValue().getStreet(), "Verify StreetAddress");
            assertEquals("Tucson", contact.getAddress().getValue().getCity(), "Verify City");
            assertEquals("AZ", contact.getAddress().getValue().getState(), "Verify State");
            assertEquals("85750", contact.getAddress().getValue().getZip(), "Verify Zip");
        }
        else {
            assertEquals((new Address()).toString(), contact.getAddress().toString(), "Verify Address");
        }

        if (phoneSpecified) {
            assertEquals("(520) 123-4567", contact.getPhone().toString(), "Verify Phone");
        }
        else {
            assertEquals((new Phone()).toString(), contact.getPhone().toString(), "Verify Phone");
        }

        if (emailSpecified) {
            assertEquals("jdoe@gmail.com", contact.getEmail().toString(), "Verify Email");
        }
        else {
            assertEquals((new Email()).toString(), contact.getEmail().toString(), "Verify Email");
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