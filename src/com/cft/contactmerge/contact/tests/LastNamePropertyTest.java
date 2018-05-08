package com.cft.contactmerge.contact.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.contact.*;


class LastNamePropertyTest {

    @Test
    void LastNameProperty_Constructor()
    {
        LastNameProperty property = new LastNameProperty("Smith");

        assertNotNull(property);
    }

    @Test
    void LastNameProperty_isMatch() {
        // TODO: Add tests
    }

    @Test
    void LastNameProperty_getValue() {
        LastNameProperty property = new LastNameProperty("Smith");

        assertEquals("Smith", property.getValue());
    }

    @Test
    void LastNameProperty_toString() {
        LastNameProperty property = new LastNameProperty("Smith");

        assertEquals("Smith", property.toString());
    }
}