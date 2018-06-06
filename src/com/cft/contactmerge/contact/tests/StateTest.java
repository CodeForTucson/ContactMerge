package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.contact.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    @Test
    void Constructor()
    {
        State property = new State("AZ");

        assertEquals("AZ", property.getValue());
    }

    @Test
    void getValue_NormalizeToAbbreviation()
    {
        State property = new State("Arizona");

        assertEquals("AZ", property.getValue());
    }

    @Test
    void toString_NormalizeToAbbreviation()
    {
        State property = new State("Arizona");

        assertEquals("AZ", property.toString());
    }
}