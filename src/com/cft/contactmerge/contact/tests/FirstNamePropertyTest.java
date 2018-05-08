package com.cft.contactmerge.contact.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.contact.*;

import static org.junit.jupiter.api.Assertions.*;

class FirstNamePropertyTest {

    @Test
    void FirstNameProperty_Constructor()
    {
        FirstNameProperty property = new FirstNameProperty("Joe");

        assertNotNull(property);
    }

    @Test
    void FirstNameProperty_isMatch() {
        // TODO: Add tests
    }

    @Test
    void FirstNameProperty_getValue() {
        FirstNameProperty property = new FirstNameProperty("Joe");

        assertEquals("Joe", property.getValue());
    }

    @Test
    void FirstNameProperty_toString() {
        FirstNameProperty property = new FirstNameProperty("Joe");

        assertEquals("Joe", property.toString());
    }
}