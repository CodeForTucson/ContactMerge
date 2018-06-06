package com.cft.contactmerge.tests;

import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.ContactMatchResult;
import com.cft.contactmerge.ContactMatchType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactMatchResultTest {

    @Test
    void getMatchType() {
        ContactMatchResult matchResult = new ContactMatchResult(ContactMatchType.Identical);

        assertEquals(ContactMatchType.Identical, matchResult.getMatchType());
    }

    @Test
    void isMatchFound_YesBecauseIdentical() {
        ContactMatchResult matchResult = new ContactMatchResult(ContactMatchType.Identical);

        Assertions.assertEquals(AnswerType.yes, matchResult.isMatchFound());
    }

    @Test
    void isMatchFound_YesBecauseMatch() {
        ContactMatchResult matchResult = new ContactMatchResult(ContactMatchType.Match);

        assertEquals(AnswerType.yes, matchResult.isMatchFound());
    }

    @Test
    void isMatchFound_YesBecauseMatchingButModifying() {
        ContactMatchResult matchResult = new ContactMatchResult(ContactMatchType.MatchButModifying);

        assertEquals(AnswerType.yes, matchResult.isMatchFound());
    }

    @Test
    void isMatchFound_MaybeBecausePotentialMatch() {
        ContactMatchResult matchResult = new ContactMatchResult(ContactMatchType.PotentialMatch);

        assertEquals(AnswerType.maybe, matchResult.isMatchFound());
    }

    @Test
    void isMatchFound_NoBecauseRelated() {
        ContactMatchResult matchResult = new ContactMatchResult(ContactMatchType.Related);

        assertEquals(AnswerType.no, matchResult.isMatchFound());
    }

    @Test
    void isMatchFound_NoBecausePotentiallyRelated() {
        ContactMatchResult matchResult = new ContactMatchResult(ContactMatchType.PotentiallyRelated);

        assertEquals(AnswerType.no, matchResult.isMatchFound());
    }

    @Test
    void isMatchFound_NoBecauseNoMatch() {
        ContactMatchResult matchResult = new ContactMatchResult(ContactMatchType.NoMatch);

        assertEquals(AnswerType.no, matchResult.isMatchFound());
    }

    @Test
    void isRelatedFound() {
        // TODO: Add tests for isRelatedFound()
    }
}