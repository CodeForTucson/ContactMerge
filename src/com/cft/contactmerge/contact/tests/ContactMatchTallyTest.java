package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.contact.ContactMatchTally;
import com.cft.contactmerge.contact.IContactProperty;
import com.cft.contactmerge.AnswerType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContactMatchTallyTest {

    private void addToTally(ContactMatchTally tally, AnswerType answerType, int count) {
        for (int i = 0; i < count; i++) {
            IContactProperty sourceMock = null;

            if (answerType != null) {
                sourceMock = mock(IContactProperty.class);
                when(sourceMock.isMatch(any())).thenReturn(answerType);
            }

            IContactProperty targetMock = mock(IContactProperty.class);

            tally.addComparison(sourceMock, targetMock);
        }
    }

    private ContactMatchTally createTestTally(int yesCount, int noCount, int maybeCount, int nullCount) {
        ContactMatchTally tally = new ContactMatchTally();

        addToTally(tally, AnswerType.yes, yesCount);
        addToTally(tally, AnswerType.no, noCount);
        addToTally(tally, AnswerType.maybe, maybeCount);
        addToTally(tally, null, nullCount);

        return tally;
    }

    private void verifyTallyCounts(ContactMatchTally tally, int yesCount, int noCount, int maybeCount) {
        assertEquals(yesCount, tally.getYesCount(), "Verify yes count");
        assertEquals(noCount, tally.getNoCount(), "Verify no count");
        assertEquals(maybeCount, tally.getMaybeCount(), "Verify maybe count");
    }

    @Test
    void Constructor_AnswerCounts() {
        ContactMatchTally tally = new ContactMatchTally();
        verifyTallyCounts(tally, 0, 0, 0);
    }

    @Test
    void Constructor_TotalEvaluated() {
        ContactMatchTally tally = new ContactMatchTally();

        assertEquals(0, tally.getTotalEvaluated());
    }

    @Test
    void Constructor_TotalSubmitted() {
        ContactMatchTally tally = new ContactMatchTally();

        assertEquals(0, tally.getTotalSubmitted());
    }

    @Test
    void onlyYesCount() {
        ContactMatchTally tally = createTestTally(3, 0, 0, 0);
        verifyTallyCounts(tally, 3, 0, 0);
    }

    @Test
    void onlyNoCount() {
        ContactMatchTally tally = createTestTally(0, 2, 0, 0);
        verifyTallyCounts(tally, 0, 2, 0);
    }

    @Test
    void allAnswerTypesHave() {
        ContactMatchTally tally = createTestTally(1, 3, 2, 0);
        verifyTallyCounts(tally, 1, 3, 2);
    }

    @Test
    void getTotalEvaluated() {
        ContactMatchTally tally = createTestTally(1, 3, 7, 11);

        assertEquals(11, tally.getTotalEvaluated());
    }

    @Test
    void getTotalSubmitted() {
        ContactMatchTally tally = createTestTally(1, 3, 7, 11);

        assertEquals(22, tally.getTotalSubmitted());
    }

    @Test
    void getOneNullCount() {
        ContactMatchTally tally = createTestTally(1, 3, 7, 11);

        assertEquals(11, tally.getOneNullCount());
    }

    @Test
    void getBothNullCount() {
        ContactMatchTally tally = new ContactMatchTally();

        for (int i = 0; i < 2; i++) {
            tally.addComparison(null, null);
        }

        assertEquals(2, tally.getBothNullCount());
    }
}
