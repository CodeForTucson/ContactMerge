package com.cft.contactmerge.tests;

import java.util.*;
import com.cft.contactmerge.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProposedMatchTest {

    @Test
    void proposedMatch()
    {
        Contact contactToMerge = new Contact();
        List<Contact> possibleTargetContacts = new ArrayList<Contact>();

        ProposedMatch proposedMatch = new ProposedMatch(contactToMerge, possibleTargetContacts);
    }

    // TODO: Add tests for
    // 0. Invalid params to the constructor
    // 1. getContactToMerge()
    // 2. getPossibleTargetContacts()
    // 3. getProposedTargetIndex()
    //  4. setProposedTargetIndex(int proposedTargetIndex)
}