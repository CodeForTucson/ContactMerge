package com.cft.contactmerge.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.*;

class CompareContactPartsTest {

    @Test
    void doNamesMatch_NoDifferentLastName() {
        Contact c1 = new Contact();
        c1.setFirstName("John");
        c1.setLastName("Doe");

        Contact c2 = new Contact();
        c2.setFirstName("John");
        c2.setLastName("Adams");

        CompareContactParts comparer = new CompareContactParts(c1, c2);

        assertEquals(AnswerType.no, comparer.doNamesMatch());
    }

    @Test
    void doNamesMatch_NoDifferentFirstName() {
        assertTrue(false);
    }

    @Test
    void doNamesMatch_YesCaseDoesNotMatter() {
        assertTrue(false);
    }

    @Test
    void doNamesMatch_YesIgnorePrefixSuffix() {
        assertTrue(false);
    }

    @Test
    void doNamesMatch_YesIgnoreSpaces() {
        assertTrue(false);
    }

    @Test
    void doNamesMatch_YesIgnorePunctuation() {
        assertTrue(false);
    }

    @Test
    void doNamesMatch_YesHyphenatedNameInFirst() {
        assertTrue(false);
    }

    @Test
    void doNamesMatch_YesHyphenatedNameInSecond() {
        assertTrue(false);
    }

    @Test
    void doNamesMatch_MaybeFirstLastSwap() {
        assertTrue(false);
    }

    // TODO: Add other name tests
    // 1. Middle initials
    // 2. Common forms like: Robert, Robbie, Bob
    // 3. Compare household name to individual name
    // 4. Handle mispellings(??)

    @Test
    void doAddressesMatch() {
        assertTrue(false);
    }

    @Test
    void doCitiesMatch() {
        assertTrue(false);
    }

    @Test
    void doStatesMatch() {
        assertTrue(false);
    }

    @Test
    void doPhoneNumbersMatch() {
        assertTrue(false);
    }

    @Test
    void doEmailsMatch() {
        assertTrue(false);
    }
}