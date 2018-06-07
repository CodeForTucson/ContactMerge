package com.cft.contactmerge.tests;

import java.util.*;
import com.cft.contactmerge.*;
import com.cft.contactmerge.contact.*;
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
        contact1.setName(new Name(new LastName("Z"), new FirstName("A")));
        existingContacts.add(contact1);

        Contact contact2 = new Contact();
        contact2.setName(new Name(new LastName("Z"), new FirstName("B")));
        existingContacts.add(contact2);

        Contact contact3 = new Contact();
        contact3.setName(new Name(new LastName("Z"), new FirstName("C")));
        existingContacts.add(contact3);

        Contact contact4 = new Contact();
        contact4.setName(new Name(new LastName("Z"), new FirstName("D")));
        existingContacts.add(contact4);

        Contact contact5 = new Contact();
        contact5.setName(new Name(new LastName("Z"), new FirstName("E")));
        existingContacts.add(contact5);

        Contact contact6 = new Contact();
        contact6.setName(new Name(new LastName("Z"), new FirstName("F")));
        existingContacts.add(contact6);

        return existingContacts;
    }

    private IContact createMockContactToMerge(List<IContact> existingContacts, List<ContactMatchType> desiredResult) {
        if (desiredResult == null) {
            desiredResult = new ArrayList<ContactMatchType>();
        }

        IContact contact = mock(IContact.class);

        Iterator<ContactMatchType> desiredResultIterator = desiredResult.iterator();
        ContactMatchType matchType = ContactMatchType.NoMatch;

        for (IContact existingContact: existingContacts) {
            if (desiredResultIterator.hasNext()) {
                matchType = desiredResultIterator.next();
            }

            when(contact.compareTo(existingContact)).thenReturn(new ContactMatchResult(matchType));
        }

        return contact;
    }

    private void verifyExpectedContacts(int contactIndex, Collection<String> firstNames, Collection<IContact> contactList) {
        assertEquals(firstNames.size(), contactList.size(), getVerificationMessage(String.format("Contact%d match count", contactIndex)));

        for(String firstName: firstNames) {
            boolean found = false;

            for(IContact contact: contactList) {
                if (contact.getName().getValue().getFirstName() == firstName) {
                    found = true;
                    break;
                }
            }

            assertTrue(found, getVerificationMessage(String.format("Contact%d contains %s", contactIndex, firstName)));
        }
    }

    @Test
    void getProposedMatches_NoMatches() {
        List<IContact> existingContacts = getExistingContacts();

        // Set up list of contacts that won't match anything
        IContact contactToMerge1 = createMockContactToMerge(existingContacts, null);
        IContact contactToMerge2 = createMockContactToMerge(existingContacts, null);

        List<IContact> contactsToMerge = Arrays.asList(contactToMerge1, contactToMerge2);

        // Instantiate MatchMaker with list of contacts that won't match anything
        MatchMaker matchMaker = new MatchMaker(existingContacts, contactsToMerge);

        // Verify that there are not proposed matching
        List<ProposedMatch> proposedMatches = matchMaker.getProposedMatches();
        assertEquals(2, proposedMatches.size(), getVerificationMessage("ProposedMatches size"));
        assertEquals(0, proposedMatches.get(0).getPossibleTargetContacts().size(), getVerificationMessage("ContactToMerge 1"));
        assertEquals(0, proposedMatches.get(1).getPossibleTargetContacts().size(), getVerificationMessage("ContactToMerge 2"));
    }

    @Test
    void getProposedMatches() {
        List<IContact> existingContacts = getExistingContacts();


        // Set up first contact to match "A" as Match and "C" and PotentiallyRelated
        List<ContactMatchType> desiredResults = Arrays.asList(ContactMatchType.Match,
                ContactMatchType.NoMatch,
                ContactMatchType.PotentiallyRelated,
                ContactMatchType.NoMatch);

        IContact contactToMerge1 = createMockContactToMerge(existingContacts, desiredResults);

        // Set up second contact to match "B" as Related, "E" and PotentialMatch, and
        // "F" as Identical
        desiredResults = Arrays.asList(ContactMatchType.NoMatch,
                ContactMatchType.Related,
                ContactMatchType.NoMatch,
                ContactMatchType.NoMatch,
                ContactMatchType.PotentialMatch,
                ContactMatchType.Identical);

        IContact contactToMerge2 = createMockContactToMerge(existingContacts, desiredResults);

        // Instantiate MatchMaker with list of contacts that won't match anything
        List<IContact> contactsToMerge = Arrays.asList(contactToMerge1, contactToMerge2);
        MatchMaker matchMaker = new MatchMaker(existingContacts, contactsToMerge);

        // Verify proposed matching
        List<ProposedMatch> proposedMatches = matchMaker.getProposedMatches();

        assertEquals(2, proposedMatches.size(), getVerificationMessage("ProposedMatches size"));

        List<IContact> proposedMatchesContact1 = proposedMatches.get(0).getPossibleTargetContacts();
        verifyExpectedContacts(1, Arrays.asList("A", "C"), proposedMatchesContact1);

        List<IContact> proposedMatchesContact2 = proposedMatches.get(1).getPossibleTargetContacts();
        verifyExpectedContacts(2, Arrays.asList("B", "E", "F"), proposedMatchesContact2);
    }
}