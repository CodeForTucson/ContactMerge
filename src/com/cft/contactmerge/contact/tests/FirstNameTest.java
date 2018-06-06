package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.AnswerType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.contact.*;

class FirstNameTest {

    @Test
    void Constructor()
    {
        FirstName property = new FirstName("Joe");
        assertEquals("Joe", property.getValue());
    }

    @Test
    void isMatch_Yes_Equal() {
        FirstName source = new FirstName("Joe");
        FirstName target = new FirstName("JOE");

        assertEquals(AnswerType.yes, source.isMatch(target));
    }

    @Test
    void isMatch_Maybe_ShortPartMatch() {
        FirstName source = new FirstName("Mary Joe");
        FirstName target = new FirstName("JOE");

        assertEquals(AnswerType.maybe, source.isMatch(target));
    }

    @Test
    void isMatch_No() {
        FirstName source = new FirstName("Kathleen");
        FirstName target = new FirstName("Lee");

        assertEquals(AnswerType.no, source.isMatch(target));
    }
}