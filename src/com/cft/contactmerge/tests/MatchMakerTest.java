package com.cft.contactmerge.tests;

import java.util.*;
import com.cft.contactmerge.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchMakerTest {

    @Test
    void MatchMaker() {
        List<Contact> existingContacts = new ArrayList<Contact>();
        List<Contact> contactsToMerge = new ArrayList<Contact>();

        MatchMaker matchMaker = new MatchMaker(existingContacts, contactsToMerge);
    }

    // TODO: Add tests for
    // 0. Invalid parms for constructor
    // 1. getProposedMatches()
    // 2. setSelectedMatch()
    // 3. removeSelectedMatch()
}