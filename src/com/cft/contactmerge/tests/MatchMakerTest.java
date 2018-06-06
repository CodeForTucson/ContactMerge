package com.cft.contactmerge.tests;

import java.util.*;
import com.cft.contactmerge.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MatchMakerTest {

    private String getVerificationMessage(String item)
    {
        return String.format("Verify %s", item);
    }

    @Test
    void Constructor() {
        List<IContact> existingContacts = new ArrayList<IContact>();
        List<IContact> contactsToMerge = new ArrayList<IContact>();

        MatchMaker matchMaker = new MatchMaker(existingContacts, contactsToMerge);
    }

    @Test
    void Constructor_NullExistingContacts() {
        assertThrows(IllegalArgumentException.class, () -> new MatchMaker(null, new ArrayList<IContact>()));
    }

    @Test
    void Constructor_NullContactsToMerge() {
        assertThrows(IllegalArgumentException.class, () -> new MatchMaker(new ArrayList<IContact>(), null));
    }

    private List<IContact> getExistingContacts()
    {
        List<IContact> existingContacts = new ArrayList<IContact>();

        Contact contact1 = new Contact();
        contact1.setFirstName("A");
        existingContacts.add(contact1);

        Contact contact2 = new Contact();
        contact2.setFirstName("B");
        existingContacts.add(contact2);

        Contact contact3 = new Contact();
        contact3.setFirstName("C");
        existingContacts.add(contact3);

        Contact contact4 = new Contact();
        contact4.setFirstName("D");
        existingContacts.add(contact4);

        Contact contact5 = new Contact();
        contact5.setFirstName("E");
        existingContacts.add(contact5);

        Contact contact6 = new Contact();
        contact6.setFirstName("F");
        existingContacts.add(contact6);

        return existingContacts;
    }

    private void verifyExpectedContacts(int contactIndex, Collection<String> firstNames, Collection<IContact> contactList) {
        assertEquals(firstNames.size(), contactList.size(), getVerificationMessage(String.format("Contact%d match count", contactIndex)));

        for(String firstName: firstNames) {
            boolean found = false;

            for(IContact contact: contactList) {
                if (contact.getName().getFirstName() == firstName) {
                    found = true;
                    break;
                }
            }

            assertTrue(found, getVerificationMessage(String.format("Contact%d contains %s", contactIndex, firstName)));
        }
    }

    @Test
    void getProposedMatches_NoMatches() {
        // Set up list of contacts that won't match anything
        IContact contactToMerge1 = mock(IContact.class);
        when(contactToMerge1.compareTo(any())).thenReturn(new ContactMatchResult(ContactMatchType.NoMatch));

        IContact contactToMerge2 = mock(IContact.class);
        when(contactToMerge2.compareTo(any())).thenReturn(new ContactMatchResult(ContactMatchType.NoMatch));

        List<IContact> contactsToMerge = Arrays.asList(contactToMerge1, contactToMerge2);

        // Instantiate MatchMaker with list of contacts that won't match anything
        MatchMaker matchMaker = new MatchMaker(getExistingContacts(), contactsToMerge);

        // Verify that there are not proposed matching
        List<ProposedMatch> proposedMatches = matchMaker.getProposedMatches();
        assertEquals(2, proposedMatches.size(), getVerificationMessage("ProposedMatches size"));
        assertEquals(0, proposedMatches.get(0).getPossibleTargetContacts().size(), getVerificationMessage("ContactToMerge 1"));
        assertEquals(0, proposedMatches.get(1).getPossibleTargetContacts().size(), getVerificationMessage("ContactToMerge 2"));
    }

    @Test
    void getProposedMatches() {
        // Set up first contact to match "A" as Match and "C" and PotentiallyRelated
        IContact contactToMerge1 = mock(IContact.class);
        when(contactToMerge1.compareTo(any())).thenReturn(new ContactMatchResult(ContactMatchType.Match))
                .thenReturn(new ContactMatchResult(ContactMatchType.NoMatch))
                .thenReturn(new ContactMatchResult(ContactMatchType.PotentiallyRelated))
                .thenReturn(new ContactMatchResult(ContactMatchType.NoMatch));

        // Set up second contact to match "B" as Related, "E" and PotentialMatch, and
        // "F" as Identical
        IContact contactToMerge2 = mock(IContact.class);
        when(contactToMerge2.compareTo(any())).thenReturn(new ContactMatchResult(ContactMatchType.NoMatch))
                .thenReturn(new ContactMatchResult(ContactMatchType.Related))
                .thenReturn(new ContactMatchResult(ContactMatchType.NoMatch))
                .thenReturn(new ContactMatchResult(ContactMatchType.NoMatch))
                .thenReturn(new ContactMatchResult(ContactMatchType.PotentialMatch))
                .thenReturn(new ContactMatchResult(ContactMatchType.Identical));

        List<IContact> contactsToMerge = Arrays.asList(contactToMerge1, contactToMerge2);

        List<IContact> existingContacts = getExistingContacts();

        // Instantiate MatchMaker with list of contacts that won't match anything
        MatchMaker matchMaker = new MatchMaker(getExistingContacts(), contactsToMerge);

        // Verify proposed matching
        List<ProposedMatch> proposedMatches = matchMaker.getProposedMatches();

        assertEquals(2, proposedMatches.size(), getVerificationMessage("ProposedMatches size"));

        List<IContact> proposedMatchesContact1 = proposedMatches.get(0).getPossibleTargetContacts();
        verifyExpectedContacts(1, Arrays.asList("A", "C"), proposedMatchesContact1);

        List<IContact> proposedMatchesContact2 = proposedMatches.get(1).getPossibleTargetContacts();
        verifyExpectedContacts(2, Arrays.asList("B", "E", "F"), proposedMatchesContact2);
    }
}