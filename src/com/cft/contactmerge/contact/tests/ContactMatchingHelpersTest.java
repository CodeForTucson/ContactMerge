package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.ProposedMatch;
import com.cft.contactmerge.contact.*;
import com.cft.contactmerge.ContactMatchType;
import com.cft.contactmerge.ContactMatchResult;
import com.cft.contactmerge.IContact;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ContactMatchingHelpersTest {

    // Test helper to create a list of proposed matches that result in the specified ContactMatchTypes when
    // compared with the ProposedMatch.getContactToMerge.
    private ProposedMatch createProposedMatchWithMockContacts(Collection<ContactMatchType> desiredCompareToResults) {
        IContact contactToMerge = mock(IContact.class);
        when(contactToMerge.getPhone()).thenReturn(mock(Phone.class));

        List<IContact> possibleTargetContacts = new ArrayList<IContact>();

        for (ContactMatchType desiredResult: desiredCompareToResults) {
            // This is the mock contact the main contact is being compared to. Since we are providing the
            // result, this mock does not need to return anything however we set a property value so
            // that we know what match result was received on the compare
            IContact compareTarget = mock(IContact.class);
            when(compareTarget.getPropertyValue("DesiredMatchType")).thenReturn(desiredResult.toString());

            possibleTargetContacts.add(compareTarget);

            // Indicate what mock should return when comparing with this possible match
            when(contactToMerge.compareTo(compareTarget)).thenReturn(new ContactMatchResult(desiredResult));
        }

        return new ProposedMatch(contactToMerge, possibleTargetContacts);
    }

    @Test
    void sortPossibleMatches_emptyList() {
        IContact contactToMerge = mock(IContact.class);
        List<IContact> possibleTargetContacts = new ArrayList<IContact>();

        // Call method under test
        List<IContact> sortedContacts = ContactMatchingHelpers.sortPossibleMatches(contactToMerge, possibleTargetContacts);

        // Verify that returned list is in expected order.
        assertEquals(0, sortedContacts.size(), "Verify sorted list size");
    }

    private void verifyExpectedMatchType(List<IContact> sortedContacts, int index, ContactMatchType matchType) {
        assertEquals(matchType.toString(),
                sortedContacts.get(index).getPropertyValue("DesiredMatchType"),
                String.format("Verify item %d is ContactMatchType == %s", index, matchType.toString()));
    }

    @Test
    void sortPossibleMatches() {
        Collection<ContactMatchType> desiredMatchTypes = Arrays.asList(ContactMatchType.Related,
                ContactMatchType.MatchButModifying,
                ContactMatchType.PotentiallyRelated,
                ContactMatchType.NoMatch,
                ContactMatchType.PotentialMatch,
                ContactMatchType.Identical,
                ContactMatchType.Match);

        ProposedMatch proposedMatch = createProposedMatchWithMockContacts(desiredMatchTypes);

                // Call method under test
        List<IContact> sortedContacts = ContactMatchingHelpers.sortPossibleMatches(proposedMatch.getContactToMerge(), proposedMatch.getPossibleTargetContacts());

        // Verify that returned list is in expected order.
        assertEquals(6, sortedContacts.size(), "Verify sorted list size");
        verifyExpectedMatchType(sortedContacts, 0, ContactMatchType.Identical);
        verifyExpectedMatchType(sortedContacts, 1, ContactMatchType.Match);
        verifyExpectedMatchType(sortedContacts, 2, ContactMatchType.MatchButModifying);
        verifyExpectedMatchType(sortedContacts, 3, ContactMatchType.PotentialMatch);
        verifyExpectedMatchType(sortedContacts, 4, ContactMatchType.Related);
        verifyExpectedMatchType(sortedContacts, 5, ContactMatchType.PotentiallyRelated);
    }

    @Test
    void hasContactInfo_true_hasAdress() {
        IContact contact = mock(IContact.class);
        when(contact.getAddress()).thenReturn(mock(Address.class));
        assertTrue(ContactMatchingHelpers.hasContactInfo(contact));
    }

    @Test
    void hasContactInfo_true_hasPhone() {
        IContact contact = mock(IContact.class);
        when(contact.getPhone()).thenReturn(mock(Phone.class));
        assertTrue(ContactMatchingHelpers.hasContactInfo(contact));
    }

    @Test
    void hasContactInfo_true_hasEmail() {
        IContact contact = mock(IContact.class);
        when(contact.getEmail()).thenReturn(mock(Email.class));
        assertTrue(ContactMatchingHelpers.hasContactInfo(contact));
    }

    @Test
    void hasContactInfo_false() {
        IContact contact = mock(IContact.class);
        assertFalse(ContactMatchingHelpers.hasContactInfo(contact));
    }

    @Test
    void getSuggestedAction_add_noMatches() {
        IContact contact = mock(IContact.class);
        when(contact.getPhone()).thenReturn(mock(Phone.class));

        ProposedMatch proposedMatch = new ProposedMatch(contact, new ArrayList<IContact>());

        // Call method under test
        MatchAction action = ContactMatchingHelpers.getSuggestedAction(proposedMatch);

        // Verify that returned list is in expected order.
        assertEquals(MatchAction.Add, action);
    }

    @Test
    void getSuggestedAction_skip_noContactInfo() {
        IContact contact = mock(IContact.class);
        when(contact.compareTo(any())).thenReturn(new ContactMatchResult(ContactMatchType.Match));

        List<IContact> existingContacts = new ArrayList<IContact>();

        ProposedMatch proposedMatch = new ProposedMatch(contact, existingContacts);

        // Call method under test
        MatchAction action = ContactMatchingHelpers.getSuggestedAction(proposedMatch);

        // Verify that returned list is in expected order.
        assertEquals(MatchAction.Skip, action);
    }

    @Test
    void getSuggestedAction_review_onlyRelatedMatches() {
        Collection<ContactMatchType> desiredMatchTypes = Arrays.asList(ContactMatchType.Related,
                ContactMatchType.NoMatch,
                ContactMatchType.PotentiallyRelated);

        ProposedMatch proposedMatch = createProposedMatchWithMockContacts(desiredMatchTypes);

        // Call method under test
        MatchAction action = ContactMatchingHelpers.getSuggestedAction(proposedMatch);

        // Verify that returned list is in expected order.
        assertEquals(MatchAction.Review, action);
    }

    @Test
    void getSuggestedAction_review_multiOfSameType() {
        Collection<ContactMatchType> desiredMatchTypes = Arrays.asList(ContactMatchType.Related,
                ContactMatchType.Match,
                ContactMatchType.Match,
                ContactMatchType.PotentiallyRelated);

        ProposedMatch proposedMatch = createProposedMatchWithMockContacts(desiredMatchTypes);

        // Call method under test
        MatchAction action = ContactMatchingHelpers.getSuggestedAction(proposedMatch);

        // Verify that returned list is in expected order.
        assertEquals(MatchAction.Review, action);
    }

    @Test
    void getSuggestedAction_update_singleMatch() {
        Collection<ContactMatchType> desiredMatchTypes = Arrays.asList(ContactMatchType.Related,
                ContactMatchType.Match,
                ContactMatchType.NoMatch,
                ContactMatchType.PotentiallyRelated);

        ProposedMatch proposedMatch = createProposedMatchWithMockContacts(desiredMatchTypes);

        // Call method under test
        MatchAction action = ContactMatchingHelpers.getSuggestedAction(proposedMatch);

        // Verify that returned list is in expected order.
        assertEquals(MatchAction.Update, action);
    }

    @Test
    void getSuggestedAction_update_singleClosestMatch() {
        Collection<ContactMatchType> desiredMatchTypes = Arrays.asList(ContactMatchType.Related,
                ContactMatchType.Match,
                ContactMatchType.NoMatch,
                ContactMatchType.Identical);

        ProposedMatch proposedMatch = createProposedMatchWithMockContacts(desiredMatchTypes);

        // Call method under test
        MatchAction action = ContactMatchingHelpers.getSuggestedAction(proposedMatch);

        // Verify that returned list is in expected order.
        assertEquals(MatchAction.Update, action);
    }
}