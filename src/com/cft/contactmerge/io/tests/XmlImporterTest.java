package com.cft.contactmerge.io.tests;

import com.cft.contactmerge.io.XmlImporter;
import org.junit.jupiter.api.Test;
import java.io.*;
import com.cft.contactmerge.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class XmlImporterTest {

    private String getVerificationMessage(String item)
    {
        return String.format("Verify %s", item);
    }

    /* --------------------------------------------------------------------------------
     * Testing Load(filename)
     * -------------------------------------------------------------------------------- */
    @Test
    void XmlImporter_Load_NullFileName() {
        XmlImporter importer = new XmlImporter();

        assertThrows(IllegalArgumentException.class, () -> importer.Load( (String) null));
    }

    @Test
    void XmlImporter_Load_EmptyFileName() {
        XmlImporter importer = new XmlImporter();

        assertThrows(IllegalArgumentException.class, () -> importer.Load(""));
    }

    @Test
    void XmlImporter_Load_NonExistentFileName() {
        // TODO: Would be great if we could test this without access the file system but articles
        // I've read indicate you shouldn't try to mock File. How else to do it?
        XmlImporter importer = new XmlImporter();

        assertThrows(FileNotFoundException.class, () -> importer.Load("c:\\DoesNotExist\\DoesNotExist.xml"));
    }

    /* --------------------------------------------------------------------------------
     * Testing Load(inputStream)
     * -------------------------------------------------------------------------------- */
    @Test
    void XmlImporter_Load_NullStream() {
        XmlImporter importer = new XmlImporter();

        assertThrows(IllegalArgumentException.class, () -> importer.Load((InputStream) null));
    }

    @Test
    void XmlImporter_Load_EmptyStream() {
        XmlImporter importer = new XmlImporter();

        String testString = "";

        InputStream inputStream = new ByteArrayInputStream(testString.getBytes());

        assertThrows(UnknownFormatConversionException.class, () -> importer.Load(inputStream));
    }

    /* --------------------------------------------------------------------------------
     * Testing Iterator
     * -------------------------------------------------------------------------------- */

    private static String testXml = "<SharpGridExport>\n" +
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
            " <Data count=\"2\">\n" +
            "  <row c1=\"13252\" c2=\"John\" c3=\"Doe\" c4=\"(520) 123-4567\" c5=\"jdoe@gmail.com\" c6=\"123 Main St\" c7=\"\" c8=\"Tucson\" c9=\"AZ\" c10=\"85750\" />\n" +
            "  <row c1=\"13253\" c2=\"Adam\" c3=\"Smith\" c4=\"(520) 111-2222\" c5=\"adam.smith@yahoo.com\" c6=\"1010 Speedway Blvd\" c7=\"\" c8=\"Tucson\" c9=\"AZ\" c10=\"85750\" />\n" +
            "</Data>\n" +
            "</SharpGridExport>\n";

    @Test
    void XmlImporter_IteratorCalledBeforeLoad() {
        XmlImporter importer = new XmlImporter();

        assertThrows(IllegalStateException.class, () -> importer.iterator().hasNext());
    }

    @Test
    void XmlImporter_Load_EmptyDataSet() {
        XmlImporter importer = new XmlImporter();

        String testString = "<Data></Data>";

        InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
        importer.Load(inputStream);

        assertFalse(importer.iterator().hasNext());
    }

    @Test
    void XmlImporter_IteratorHasNext() {
        XmlImporter importer = new XmlImporter();

        InputStream inputStream = new ByteArrayInputStream(testXml.getBytes());
        importer.Load(inputStream);

        assertTrue(importer.iterator().hasNext());
    }

    @Test
    void XmlImporter_IteratorReturnsExpectedData() {
        XmlImporter importer = new XmlImporter();

        InputStream inputStream = new ByteArrayInputStream(testXml.getBytes());
        importer.Load(inputStream);

        ArrayList<String> lastNames = new ArrayList<String>(Arrays.asList("Doe", "Smith"));
        ArrayList<String> firstNames = new ArrayList<String>(Arrays.asList("John", "Adam"));
        ArrayList<String> addresses = new ArrayList<String>(Arrays.asList("123 Main St", "1010 Speedway Blvd"));
        ArrayList<String> phones = new ArrayList<String>(Arrays.asList("(520) 123-4567", "(520) 111-2222"));
        ArrayList<String> emails = new ArrayList<String>(Arrays.asList("jdoe@gmail.com", "adam.smith@yahoo.com"));

        int i = 0;
        for (Contact contact : importer)
        {
            assertEquals(lastNames.get(i), contact.getLastName(), getVerificationMessage(String.format("lastNames[%d]", i)));
            assertEquals(firstNames.get(i), contact.getFirstName(), getVerificationMessage(String.format("firstNames[%d]", i)));
            assertEquals(addresses.get(i), contact.getAddress(), getVerificationMessage(String.format("addresses[%d]", i)));
            assertEquals(phones.get(i), contact.getPhone(), getVerificationMessage(String.format("phones[%d]", i)));
            assertEquals(emails.get(i), contact.getEmail(), getVerificationMessage(String.format("emails[%d]", i)));

            i++;
        }

        assertEquals(2, i, getVerificationMessage("contact count"));
    }
}