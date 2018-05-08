package com.cft.contactmerge.contact.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.contact.*;


class NamePropertyTest {

    private String createVerificationMessage(String itemName)
    {
        return "Verify " + itemName;
    }

    @Test
    void NameProperty_Constructor()
    {
        NameProperty property = new NameProperty("Doe", "Jane");

        assertNotNull(property);
    }

    @Test
    void NameProperty_isMatch() {
        // TODO: Add tests
    }

    @Test
    void NameProperty_getValue() {
        NameProperty property = new NameProperty("Doe", "Jane");

        assertEquals("Jane", property.getValue().getFirstName(), createVerificationMessage("FirstName"));
        assertEquals("Doe", property.getValue().getLastName(), createVerificationMessage("LastName"));
    }

    @Test
    void NameProperty_toString() {
        NameProperty property = new NameProperty("Doe", "Jane");

        assertEquals("Doe, Jane", property.toString());
    }

    @Test
    void NameProperty_getFirstName() {
        NameProperty property = new NameProperty("Doe", "Jane");

        assertEquals("Jane", property.getFirstName());
    }

    @Test
    void NameProperty_getLastName() {
        NameProperty property = new NameProperty("Doe", "Jane");

        assertEquals("Doe", property.getLastName());
    }
}