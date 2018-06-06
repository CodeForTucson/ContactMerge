package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.contact.GeneralProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneralPropertyTest {

    @Test
    void Constructor()
    {
        GeneralProperty property = new GeneralProperty("Tucson");

        assertNotNull(property);
    }

    @Test
    void Constructor_NullValue()
    {
        assertThrows(IllegalArgumentException.class, () -> new GeneralProperty(null));
    }

    @Test
    void Constructor_EmptyValue()
    {
        assertThrows(IllegalArgumentException.class, () -> new GeneralProperty(""));
    }

    @Test
    void isMatch_Yes_Equal() {
        GeneralProperty source = new GeneralProperty("Tucson");
        GeneralProperty target = new GeneralProperty("tucson");

        assertEquals(AnswerType.yes, source.isMatch(target));
    }

    @Test
    void isMatch_No() {
        GeneralProperty source = new GeneralProperty("Phoenix");
        GeneralProperty target = new GeneralProperty("Tucson");

        assertEquals(AnswerType.no, source.isMatch(target));
    }

    @Test
    void getValue() {
        GeneralProperty property = new GeneralProperty("Tucson");

        assertEquals("Tucson", property.getValue());
    }

    @Test
    void GeneralProperty_toString() {
        GeneralProperty property = new GeneralProperty("Tucson");

        assertEquals("Tucson", property.toString());
    }
}