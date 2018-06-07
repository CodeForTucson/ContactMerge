/*
 * MergeFileExporter will be responsible for taking the final matching results
 * creating a file with the merged records.
 */
package com.cft.contactmerge.io;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.cft.contactmerge.ContactMatchType;
import com.cft.contactmerge.contact.ContactMatchingHelpers;
import com.cft.contactmerge.contact.MatchAction;
import com.cft.contactmerge.IContact;
import com.cft.contactmerge.ProposedMatch;
import com.cft.contactmerge.ColumnMap;

public class MergeFileExporter{

    private Collection<ProposedMatch> proposedMatches;

    public MergeFileExporter(Collection<ProposedMatch> proposedMatches)
    {
        if (proposedMatches == null) {
            throw new IllegalArgumentException("proposedMatches must not be null");
        }

        this.proposedMatches = proposedMatches;
    }

    private void writeContactToMerge(BufferedWriter writer, IContact contact, MatchAction action)
            throws IOException {
        writeContact(writer, contact, true, null, action);
    }

    private void writeMatchingContact(BufferedWriter writer, IContact contact, ContactMatchType matchType, MatchAction action)
            throws IOException {
        writeContact(writer, contact, false, matchType, action);
    }

    private void writeContact(BufferedWriter writer, IContact contact, boolean contactToMerge,
                              ContactMatchType matchType, MatchAction action)
            throws IOException {
        writer.write( String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
                contact.getName().getValue().getLastName(),
                contact.getName().getValue().getFirstName(),
                contact.getAddress() == null || contact.getAddress().getValue().getStreet() == null ? "" : contact.getAddress().getValue().getStreet(),
                contact.getAddress() == null || contact.getAddress().getValue().getCity() == null ? "" : contact.getAddress().getValue().getCity(),
                contact.getAddress() == null || contact.getAddress().getValue().getState() == null ? "" : contact.getAddress().getValue().getState(),
                contact.getAddress() == null || contact.getAddress().getValue().getZip() == null ? "" : contact.getAddress().getValue().getZip(),
                contact.getPhone() == null ? "" : contact.getPhone(),
                contact.getEmail() == null ? "" : contact.getEmail(),
                contactToMerge == true ? "Yes" : "No",
                contact.containsProperty("ContactId") ? contact.getPropertyValue("ContactId"): "",
                matchType == null ? "" : matchType.toString(),
                action == null ? "" : action.toString()));
        writer.newLine();
    }

    private void writeMatches(BufferedWriter writer, ProposedMatch match, MatchAction action)
            throws IOException {
        for (IContact possibleMatch : match.getPossibleTargetContacts()) {
            ContactMatchType matchType = match.getContactToMerge().compareTo(possibleMatch).getMatchType();
            writeMatchingContact(writer, possibleMatch, matchType, action);
        }
    }

    public void createMergeFile(SupportedFileType fileType, String filename, Collection<ColumnMap> columnMaps)
            throws IOException {
        if (filename == null || filename.trim() == "")
        {
            throw new IllegalArgumentException("filename is required");
        }

        if (fileType == null) {
            throw new IllegalArgumentException("fileType required");
        }

        if (fileType != SupportedFileType.TSV) {
            throw new UnsupportedOperationException("Only support TSV at the moment");
        }

        FileWriter fw = new FileWriter(filename);

        // TODO: The long term plan is to have the program spit out a file that can be imported
        // into the target system but for now we are creating a file that lists all of the
        // potential matches.
        try (BufferedWriter writer = new BufferedWriter(fw)) {
            writer.write("LastName\tFirstName\tStreetName\tCity\tState\tZip\tPhone\tEmail\tContactToMerge\tContactId\tMatchType\tProposedAction");
            writer.newLine();
            for(ProposedMatch match: proposedMatches) {
                IContact contactToMerge = match.getContactToMerge();

                MatchAction suggestedAction = ContactMatchingHelpers.getSuggestedAction(match);
                writeContactToMerge(writer, contactToMerge, suggestedAction);

                writeMatches(writer, match, suggestedAction);
            }
        }
    }
}
