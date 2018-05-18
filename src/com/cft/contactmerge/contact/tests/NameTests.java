package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.contact.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NameTests {
    public String testFailedMessage(String valueOne, String valueTwo){
        return "<Expected Value: " + valueOne + ">" + " -vs- " + "<Actual Value: " + valueTwo + ">";
    }

    @Test
    void Name_Constructor() {
        Name name = new Name();
        assertNotNull(name);
    }

    @Test
    void Name_ConstructorValuesFilled() {
        Name name = new Name("John", "Ray", "Doe", "Dr", "III");
        assertEquals("John", name.getFirstName(), testFailedMessage("John", name.getFirstName()));
        assertEquals("Ray", name.getMiddleName(), testFailedMessage("Ray", name.getMiddleName()));
        assertEquals("Doe", name.getLastName(), testFailedMessage("Doe", name.getLastName()));
        assertEquals("Dr", name.getPrefix(), testFailedMessage("Dr", name.getPrefix()));
        assertEquals("III", name.getSuffix(), testFailedMessage("III", name.getSuffix()));
    }

    @Test
    void Name_getSetFirstName() {
        Name name = new Name();
        name.setFirstName("John");
        assertEquals("John", name.getFirstName());
    }

    @Test
    void Name_getSetMiddleName() {
        Name name = new Name();
        name.setMiddleName("Ray");
        assertEquals("Ray", name.getMiddleName());
    }

    @Test
    void Name_getSetLastName() {
        Name name = new Name();
        name.setLastName("Doe");
        assertEquals("Doe", name.getLastName());
    }

    @Test
    void Name_getSetPrefix() {
        Name name = new Name();
        name.setPrefix("Dr");
        assertEquals("Dr", name.getPrefix());
    }

    @Test
    void Name_getSetSuffix() {
        Name name = new Name();
        name.setSuffix("III");
        assertEquals("III", name.getSuffix());
    }

    @Test
    void Name_toString(){
        Name name = new Name("John", "Ray", "Doe", "Dr", "III");
        assertEquals("John, Ray, Doe, Dr, III", name.toString());
    }
}
