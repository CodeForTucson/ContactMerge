package com.cft.contactmerge.tests;

import java.util.*;
import com.cft.contactmerge.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProposedMatchTest {

    private String getVerificationMessage(String item)
    {
        return String.format("Verify %s", item);
    }

    @Test
    void Constructor()
    {
        IContact contactToMerge = new Contact();
        List<IContact> possibleTargetContacts = new ArrayList<IContact>();

        ProposedMatch proposedMatch = new ProposedMatch(contactToMerge, possibleTargetContacts);
    }

    @Test
    void Constructor_NullContactToMerge()
    {
        assertThrows(IllegalArgumentException.class, () -> new ProposedMatch(null, new ArrayList<IContact>()));
    }

    @Test
    void Constructor_NullPossibleTargetContacts()
    {
        assertThrows(IllegalArgumentException.class, () -> new ProposedMatch( new Contact(),null));
    }

    @Test
    void getContactToMerge()
    {
        Contact expectedContactToMerge = new Contact();
        expectedContactToMerge.setFirstName("John");
        expectedContactToMerge.setLastName("Doe");
        expectedContactToMerge.getAddress().setStreet("123 Main St");
        expectedContactToMerge.getPhone().setFullNumber("(520) 555-1234");

        ProposedMatch proposedMatch = new ProposedMatch(expectedContactToMerge, new ArrayList<IContact>());

        IContact actualContactToMerge = proposedMatch.getContactToMerge();

        // TODO: Replace the following tests with a call to check if Contact.CompareTo() is
        // ContactMatchType.Identical once CompareTo() is implemented.
        assertEquals(expectedContactToMerge.getFirstName(), actualContactToMerge.getFirstName(), getVerificationMessage("FirstName"));
        assertEquals(expectedContactToMerge.getLastName(), actualContactToMerge.getLastName(), getVerificationMessage("LastName"));
        assertEquals(expectedContactToMerge.getAddress(), actualContactToMerge.getAddress(), getVerificationMessage("Address"));
        assertEquals(expectedContactToMerge.getPhone(), actualContactToMerge.getPhone(), getVerificationMessage("Phone"));
    }

    private List<IContact> getTestTargetContacts()
    {
        List<IContact> testTargetContacts = new ArrayList<IContact>();

        Contact contact1 = new Contact();
        contact1.setFirstName("A");
        testTargetContacts.add(contact1);

        Contact contact2 = new Contact();
        contact2.setFirstName("B");
        testTargetContacts.add(contact2);

        Contact contact3 = new Contact();
        contact3.setFirstName("C");
        testTargetContacts.add(contact3);

        return testTargetContacts;
    }

    private boolean listContainsContact(String searchFirstName, Collection<IContact> contactList) {
        for(IContact contact: contactList) {
            if (contact.getFirstName() == searchFirstName) {
                return true;
            }
        }

        return false;
    }

    @Test
    void getPossibleTargetContacts() {
        List<IContact> possibleTargetContacts = getTestTargetContacts();

        ProposedMatch proposedMatch = new ProposedMatch(new Contact(), possibleTargetContacts);

        List<IContact> matchingTargetContacts = proposedMatch.getPossibleTargetContacts();

        assertEquals(3, matchingTargetContacts.size(), getVerificationMessage("Match Count"));
        assertTrue(listContainsContact("A", matchingTargetContacts), getVerificationMessage("Contact A"));
        assertTrue(listContainsContact("B", matchingTargetContacts), getVerificationMessage("Contact B"));
        assertTrue(listContainsContact("C", matchingTargetContacts), getVerificationMessage("Contact C"));
    }

    @Test
    void getPossibleTargetContacts_NoMatches() {
        List<IContact> possibleTargetContacts = new ArrayList<IContact>();

        ProposedMatch proposedMatch = new ProposedMatch(new Contact(), possibleTargetContacts);

        List<IContact> matchingTargetContacts = proposedMatch.getPossibleTargetContacts();

        assertEquals(0, matchingTargetContacts.size(), getVerificationMessage("Match Count"));
    }

    @Test
    void setProposedTargetIndex()
    {
        List<IContact> possibleTargetContacts = getTestTargetContacts();
        ProposedMatch proposedMatch = new ProposedMatch(new Contact(), possibleTargetContacts);

        proposedMatch.setProposedTargetIndex(1);

        assertEquals(1, proposedMatch.getProposedTargetIndex());
    }

    @Test
    void setProposedTargetIndex_IndexTooHigh() {
        List<IContact> possibleTargetContacts = getTestTargetContacts();
        ProposedMatch proposedMatch = new ProposedMatch(new Contact(), possibleTargetContacts);

        assertThrows(IndexOutOfBoundsException.class, () -> proposedMatch.setProposedTargetIndex(4));
    }

    @Test
    void setProposedTargetIndex_IndexTooLow() {
        ProposedMatch proposedMatch = new ProposedMatch(new Contact(), new ArrayList<IContact>());

        assertThrows(IndexOutOfBoundsException.class, () -> proposedMatch.setProposedTargetIndex(-1));
    }

    @Test
    void setProposedTargetIndex_NoValidIndexForEmptyList() {
        ProposedMatch proposedMatch = new ProposedMatch(new Contact(), new ArrayList<IContact>());

        assertThrows(IndexOutOfBoundsException.class, () -> proposedMatch.setProposedTargetIndex(0));
    }

    @Test
    void removeTargetContact() {
        ProposedMatch proposedMatch = new ProposedMatch(new Contact(), getTestTargetContacts());
        proposedMatch.setProposedTargetIndex(1);

        proposedMatch.removeTargetContact(0);

        List<IContact> possibleTargetContacts = proposedMatch.getPossibleTargetContacts();
        assertEquals(-1, proposedMatch.getProposedTargetIndex(), getVerificationMessage("ProposedTargetIndex reset"));
        assertEquals(2, possibleTargetContacts.size(), getVerificationMessage("ProposedTargetContacts size"));
        assertTrue(listContainsContact("B", possibleTargetContacts ), getVerificationMessage("TargetContacts still contains B"));
        assertTrue(listContainsContact("C", possibleTargetContacts), getVerificationMessage("TargetContacts still contains C"));
        assertFalse(listContainsContact("A", possibleTargetContacts), getVerificationMessage("TargetContacts does not contain A"));
    }

    @Test
    void removeTargetContact_IndexTooLow() {
        ProposedMatch proposedMatch = new ProposedMatch(new Contact(), getTestTargetContacts());

        assertThrows(IndexOutOfBoundsException.class, () -> proposedMatch.removeTargetContact(-1));
    }

    @Test
    void removeTargetContact_IndexTooHigh() {
        ProposedMatch proposedMatch = new ProposedMatch(new Contact(), getTestTargetContacts());

        assertThrows(IndexOutOfBoundsException.class, () -> proposedMatch.removeTargetContact(3));
    }

    @Test
    void removeTargetContact_RemoveAll() {
        ProposedMatch proposedMatch = new ProposedMatch(new Contact(), getTestTargetContacts());
        proposedMatch.removeTargetContact(2);
        proposedMatch.removeTargetContact(1);
        proposedMatch.removeTargetContact(0);

        List<IContact> possibleTargetContacts = proposedMatch.getPossibleTargetContacts();
        assertEquals(0, possibleTargetContacts.size(), getVerificationMessage("ProposedTargetContacts size"));
    }

}
