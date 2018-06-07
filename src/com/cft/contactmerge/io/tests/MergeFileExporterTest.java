package com.cft.contactmerge.io.tests;

import com.cft.contactmerge.ColumnMap;
import com.cft.contactmerge.IContact;
import com.cft.contactmerge.Contact;
import com.cft.contactmerge.contact.Name;
import com.cft.contactmerge.contact.LastName;
import com.cft.contactmerge.contact.FirstName;
import com.cft.contactmerge.contact.Address;
import com.cft.contactmerge.contact.StreetAddress;
import com.cft.contactmerge.contact.State;
import com.cft.contactmerge.contact.Zip;
import com.cft.contactmerge.contact.Phone;
import com.cft.contactmerge.contact.Email;
import com.cft.contactmerge.contact.GeneralProperty;

import com.cft.contactmerge.ProposedMatch;
import com.cft.contactmerge.io.MergeFileExporter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.cft.contactmerge.io.SupportedFileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergeFileExporterTest {

    @Test
    void Constructor()
    {
        MergeFileExporter exporter = new MergeFileExporter(new ArrayList<ProposedMatch>());

        assertNotNull(exporter);
    }

    @Test
    void Constructor_nullProposedMatches()
    {
        assertThrows(IllegalArgumentException.class, () -> new MergeFileExporter(null));
    }

    // TODO: Need to change the following tests when ColumnMap is supported

    @Test
    void createMergeFile_nullFileType() {
        Collection<ProposedMatch> proposedMatches = new ArrayList<ProposedMatch>();
        MergeFileExporter exporter = new MergeFileExporter(proposedMatches);
        assertThrows(IllegalArgumentException.class, () -> exporter.createMergeFile(null,
                "c:\\NotARealFile.csv", null));
    }

    @Test
    void createMergeFile_nullFileName() {
        Collection<ProposedMatch> proposedMatches = new ArrayList<ProposedMatch>();
        MergeFileExporter exporter = new MergeFileExporter(proposedMatches);
        assertThrows(IllegalArgumentException.class, () -> exporter.createMergeFile(SupportedFileType.TSV,
                (String) null, null));
    }

    @Test
    void createMergeFile_emptyFileName() {
        Collection<ProposedMatch> proposedMatches = new ArrayList<ProposedMatch>();
        MergeFileExporter exporter = new MergeFileExporter(proposedMatches);
        assertThrows(IllegalArgumentException.class, () -> exporter.createMergeFile(SupportedFileType.TSV,
                "", null));
    }

    private String createTempFilename() throws IOException {
        UUID uuid = UUID.randomUUID();

        return File.createTempFile(uuid.toString(), ".tsv").getAbsolutePath();
    }

    @Test
    void createMergeFile_tsvNoColumnMap_NoProposedMatches() throws IOException {
        Collection<ProposedMatch> matches = new ArrayList<ProposedMatch>();
        MergeFileExporter exporter = new MergeFileExporter(matches);

        String tempFilename = createTempFilename();

        try (OutputStream outStream = new ByteArrayOutputStream())
        {
            exporter.createMergeFile(SupportedFileType.TSV, tempFilename, null);
        }

        File outputFile = new File(tempFilename);
        assertEquals(108, outputFile.length() );

        outputFile.delete();
    }

    private IContact createTestContact(String lastName, String firstName, String streetAddress,
                                       String phoneNumber, String email) {

        IContact newContact = new Contact();

        newContact.setName(new Name(new LastName(lastName), new FirstName(firstName)));

        if (streetAddress != null) {
            newContact.setAddress(new Address(new StreetAddress(streetAddress),
                    null,
                    new GeneralProperty("Tucson"),
                    new State("AZ"),
                    new Zip("85750")));
        }

        if (phoneNumber != null) {
            newContact.setPhone(new Phone(phoneNumber));
        }

        if (email != null) {
            newContact.setEmail(new Email(email));
        }

        return newContact;
    }

    @Test
    void createMergeFile_tsvNoColumnMap() throws IOException {
        Collection<ProposedMatch> matches = new ArrayList<ProposedMatch>();

        // First proposed match
        IContact sourceContact = createTestContact("Smith", "Adam", "123 Main St",
                null, null);

        List<IContact> possibleTargets = new ArrayList<IContact>();
        possibleTargets.add(createTestContact("Smith-Doe", "Adam", "1100 Broadway",
                "(520) 123-3456", "adamsmith@gmail.com"));

        matches.add(new ProposedMatch(sourceContact, possibleTargets));

        // Second proposed match
        sourceContact = createTestContact("White", "Susan", "3040 Oracle Ave",
                null, "swhite@hotmail.com");

        possibleTargets = new ArrayList<IContact>();

        matches.add(new ProposedMatch(sourceContact, possibleTargets));

        // Third proposed match
        sourceContact = createTestContact("Doe", "Jane", "2311 Skyline Drive",
                "(520) 111-2222", null);

        possibleTargets = new ArrayList<IContact>();
        possibleTargets.add(createTestContact("Doe", "Jane", null,
                null, "jdoe@yahoo.com"));
        possibleTargets.add(createTestContact("Doe", "Jane", "3502 Sabino Canyon Rd",
                "(520) 111-2222", "jdoe@yahoo.com"));

        matches.add(new ProposedMatch(sourceContact, possibleTargets));

        // Run the test
        MergeFileExporter exporter = new MergeFileExporter(matches);

        String tempFilename = createTempFilename();

        try (OutputStream outStream = new ByteArrayOutputStream())
        {
            exporter.createMergeFile(SupportedFileType.TSV, tempFilename, null);
        }

        File outputFile = new File(tempFilename);
        assertEquals(582, outputFile.length() );

        outputFile.delete();
    }

    @Test
    void createMergeFile_csvNoColumnMap() throws IOException {
       Collection<ProposedMatch> proposedMatches = new ArrayList<ProposedMatch>();
            MergeFileExporter exporter = new MergeFileExporter(proposedMatches);
            assertThrows(UnsupportedOperationException.class, () -> exporter.createMergeFile(SupportedFileType.CSV,
                    "c:\\NotARealFile.csv", null));
    }

    @Test
    void createMergeFile_xmlNoColumnMap() throws IOException {
        Collection<ProposedMatch> proposedMatches = new ArrayList<ProposedMatch>();
        MergeFileExporter exporter = new MergeFileExporter(proposedMatches);
        assertThrows(UnsupportedOperationException.class, () -> exporter.createMergeFile(SupportedFileType.XML,
                "c:\\NotARealFile.csv", null));
    }
}