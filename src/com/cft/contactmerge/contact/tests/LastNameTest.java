package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.AnswerType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.contact.*;


class LastNameTest {

    @Test
    void Constructor()
    {
        LastName property = new LastName("Smith");
        assertEquals("Smith", property.toString());
    }

    @Test
    void isMatch_Yes_Equal() {
        LastName source = new LastName("ADAMS");
        LastName target = new LastName("Adams");

        assertEquals(AnswerType.yes, source.isMatch(target));
    }

    @Test
    void isMatch_Maybe_ShortPartMatch() {
        LastName source = new LastName("Adams Doe");
        LastName target = new LastName("Doe");

        assertEquals(AnswerType.maybe, source.isMatch(target));
    }

    @Test
    void isMatch_No() {
        LastName source = new LastName("Adamson");
        LastName target = new LastName("Adam");

        assertEquals(AnswerType.no, source.isMatch(target));
    }
}