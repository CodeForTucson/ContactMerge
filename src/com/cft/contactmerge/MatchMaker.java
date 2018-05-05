/*
 * The MatchMaker class is responsible for finding potential matches for contactsToMerge
 * in existingContacts. This class also provides methods for callers to select a match
 * from proposed matches (vis setSelectedMatch) and remove proposed matches that don't
 * appear to be appropriate (via removeMath).
 */
package com.cft.contactmerge;
import java.util.*;

public class MatchMaker {
    public MatchMaker(Collection<Contact> existingContacts, Collection<Contact> contactsToMerge)
    {

    }

    public List<ProposedMatch> getProposedMatches()
    {
        return null;
    }

    public void setSelectedMatch(int contactIndex, int selectedMatchIndex)
    {
    }

    public void removeMatch(int contactIndex)
    {

    }
}
