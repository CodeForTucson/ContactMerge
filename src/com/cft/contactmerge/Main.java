package com.cft.contactmerge;

import com.cft.contactmerge.ContactMatchType;
import com.cft.contactmerge.IContact;
import com.cft.contactmerge.io.CsvImporter;
import com.cft.contactmerge.io.MergeFileExporter;
import com.cft.contactmerge.io.SupportedFileType;
import com.cft.contactmerge.io.XmlImporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        if (args.length < 3) {
            System.out.println("USAGE: ContactMerge <existingContactsFile> <contactsToMergeFile> <mergeOutputFile>");
            return;
        }

        System.out.println("Loading Merge Target...");

        List<IContact> existingContacts = new ArrayList<IContact>();
        XmlImporter existingContactImporter = new XmlImporter();

        try {
            existingContactImporter.Load(args[0]);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return;
        }

        for (IContact contact: existingContactImporter) {
            existingContacts.add(contact);
        }

        System.out.println(String.format("Loaded %d existing contacts", existingContacts.size()));
        System.out.println();

        System.out.println("Loading Merge Source...");
        List<IContact> contactsToMerge = new ArrayList<IContact>();
        CsvImporter toMergeImporter = new CsvImporter();

        try {
            toMergeImporter.Load(args[1]);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return;
        }

        for (IContact contact: toMergeImporter) {
            contactsToMerge.add(contact);
        }

        System.out.println(String.format("Loaded %d contacts to merge", contactsToMerge.size()));
        System.out.println();

        System.out.println("Comparing Contacts...");
        MatchMaker matchMaker = new MatchMaker(existingContacts, contactsToMerge);
        List<ProposedMatch> matches = matchMaker.getProposedMatches();

        MergeFileExporter exporter = new MergeFileExporter(matches);
        exporter.createMergeFile(SupportedFileType.TSV, args[2], null);

        System.out.println();


        System.out.println("Done");
    }
}
