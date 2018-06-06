package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.contact.Apartment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentTest {

    @Test
    void Constructor()
    {
        Apartment property = new Apartment("4C");
        assertEquals("4C", property.getValue());
    }
}