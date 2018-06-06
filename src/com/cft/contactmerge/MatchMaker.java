/*
 * The MatchMaker class is responsible for finding potential matches for contactsToMerge
 * in existingContacts. This class also provides methods for callers to select a match
 * from proposed matches (vis setSelectedMatch) and remove proposed matches that don't
 * appear to be appropriate (via removeMath).
 */
package com.cft.contactmerge;
import java.util.*;

public class MatchMaker {
    private List<ProposedMatch> proposedMatches;
    private Collection<IContact> contactsToMerge;
    private Collection<IContact> existingContacts;

    public MatchMaker(Collection<IContact> existingContacts, Collection<IContact> contactsToMerge) {
        if (existingContacts == null) {
            throw new IllegalArgumentException("existingContacts can not be null");
        }

        if (contactsToMerge == null) {
            throw new IllegalArgumentException("contactsToMerge can not be null");
        }

        this.contactsToMerge = contactsToMerge;
        this.existingContacts = existingContacts;
    }

    private void searchForMatches() {
        this.proposedMatches = new ArrayList<ProposedMatch>();

        for(IContact contactToMerge: contactsToMerge) {
            List<IContact> possibleMatches = new ArrayList<IContact>();

            for(IContact existingContact: existingContacts) {
                if (contactToMerge.compareTo(existingContact).getMatchType() != ContactMatchType.NoMatch) {
                    possibleMatches.add(existingContact);
                }

            }

            ProposedMatch proposedMatch = new ProposedMatch(contactToMerge, possibleMatches);

            this.proposedMatches.add(proposedMatch);
        }
    }

    public List<ProposedMatch> getProposedMatches() {
        if (this.proposedMatches == null) {
            searchForMatches();
        }

        return this.proposedMatches;
    }
}
