package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.contact.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NameTests {
    // Note: matching methods tests are implemented in CompareContactPartsTest.class
    public String testFailedMessage(String expectedValue, String actualValue){
        return "<Expected Value: " + expectedValue + ">" + " -vs- " + "<Actual Value: " + actualValue + ">";
    }

    /*******************************************************************************************************************
     ************************************************ Constructor Tests ************************************************
     *******************************************************************************************************************/
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

    /*******************************************************************************************************************
     ************************************************** Get/Set Tests **************************************************
     *******************************************************************************************************************/
    @Test
    void Name_setFullName(){
        Name name = new Name();
        name.setFullName("John", "Ray", "Doe", "Dr", "III");
        assertEquals("John", name.getFirstName(), testFailedMessage("John", name.getFirstName()));
        assertEquals("Ray", name.getMiddleName(), testFailedMessage("Ray", name.getMiddleName()));
        assertEquals("Doe", name.getLastName(), testFailedMessage("Doe", name.getLastName()));
        assertEquals("Dr", name.getPrefix(), testFailedMessage("Dr", name.getPrefix()));
        assertEquals("III", name.getSuffix(), testFailedMessage("III", name.getSuffix()));
    }

    @Test
    void Name_setFullName_FirstAndLastNamesOnly(){
        Name name = new Name();
        name.setFullName("John", null, "Doe", null, null);
        assertEquals("John", name.getFirstName(), testFailedMessage("John", name.getFirstName()));
        assertNull(name.getMiddleName(), testFailedMessage(null, name.getMiddleName()));
        assertEquals("Doe", name.getLastName(), testFailedMessage("Doe", name.getLastName()));
        assertNull(name.getPrefix(), testFailedMessage(null, name.getPrefix()));
        assertNull(name.getSuffix(), testFailedMessage(null, name.getSuffix()));
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
    void Name_setNamePartsNull(){
        Name name = new Name("John", "Ray", "Doe", "Dr", "III");
        assertEquals("John", name.getFirstName(), testFailedMessage("John", name.getFirstName()));
        assertEquals("Ray", name.getMiddleName(), testFailedMessage("Ray", name.getMiddleName()));
        assertEquals("Doe", name.getLastName(), testFailedMessage("Doe", name.getLastName()));
        assertEquals("Dr", name.getPrefix(), testFailedMessage("Dr", name.getPrefix()));
        assertEquals("III", name.getSuffix(), testFailedMessage("III", name.getSuffix()));

        name.setNamePartsNull();
        assertNull(name.getFirstName(), testFailedMessage(null, name.getFirstName()));
        assertNull(name.getMiddleName(), testFailedMessage(null, name.getMiddleName()));
        assertNull(name.getLastName(), testFailedMessage(null, name.getLastName()));
        assertNull(name.getPrefix(), testFailedMessage(null, name.getPrefix()));
        assertNull(name.getSuffix(), testFailedMessage(null, name.getSuffix()));
    }

    /*******************************************************************************************************************
     ************************************************ Sub-Methods Tests ************************************************
     *******************************************************************************************************************/
    @Test
    void Name_toString_FirstAndLastName(){
        Name name = new Name("John", "Ray", "Doe", "Dr", "III");
        assertEquals("Doe, John", name.toString());
    }

    @Test
    void Name_toString_FirstNameOnly(){
        Name name = new Name("John", "", "", "", "");
        assertEquals("John", name.toString());
    }

    @Test
    void Name_toString_LastNameOnly(){
        Name name = new Name("", "Doe", "Doe", "Dr", "III");
        assertEquals("Doe", name.toString());
    }

    @Test
    void Name_toString_NoName(){
        Name name = new Name();
        assertEquals("", name.toString());
    }

    @Test
    void Name_setParseSuffixAndPrefixInLastName(){
        //Note: this method is a private method, that gets called automatically every time setLastName() is called.
        Name personOne = new Name("Dave", "Dave", "Dr. Asherman, II");
        assertEquals("Dave", personOne.getFirstName(), testFailedMessage("Dave", personOne.getFirstName()));
        assertEquals("Dave", personOne.getMiddleName(), testFailedMessage("Dave", personOne.getMiddleName()));
        assertEquals("Asherman", personOne.getLastName(), testFailedMessage("Asherman", personOne.getLastName()));
        assertEquals("Dr.", personOne.getPrefix(), testFailedMessage("Dr.", personOne.getPrefix()));
        assertEquals("II", personOne.getSuffix(), testFailedMessage("II", personOne.getSuffix()));

        Name personTwo = new Name("Dave", "Dave", "Mr. Asherman, III");
        assertEquals("Dave", personTwo.getFirstName(), testFailedMessage("Dave", personTwo.getFirstName()));
        assertEquals("Dave", personTwo.getMiddleName(), testFailedMessage("Dave", personTwo.getMiddleName()));
        assertEquals("Asherman", personTwo.getLastName(), testFailedMessage("Asherman", personTwo.getLastName()));
        assertEquals("Mr.", personTwo.getPrefix(), testFailedMessage("Mr.", personTwo.getPrefix()));
        assertEquals("III", personTwo.getSuffix(), testFailedMessage("III", personTwo.getSuffix()));
    }

    @Test
    void Name_setParseSuffixAndPrefixInLastName_HyphenatedLastName(){
        //Note: this method is a private method, that gets called automatically every time setLastName() is called.
        Name personOne = new Name("Dave", "Dave", "Dr.Lloyd!-Atkinson , Sr.");
        assertEquals("Dave", personOne.getFirstName(), testFailedMessage("Dave", personOne.getFirstName()));
        assertEquals("Dave", personOne.getMiddleName(), testFailedMessage("Dave", personOne.getMiddleName()));
        assertEquals("Lloyd!-Atkinson", personOne.getLastName(), testFailedMessage("Lloyd!-Atkinson", personOne.getLastName()));
        assertEquals("Dr.", personOne.getPrefix(), testFailedMessage("Dr.", personOne.getPrefix()));
        assertEquals("Sr.", personOne.getSuffix(), testFailedMessage("Sr.", personOne.getSuffix()));

        Name personTwo = new Name("Dave", "Dave", "Mr. Lloyd! Atkinson , Jr");
        assertEquals("Dave", personTwo.getFirstName(), testFailedMessage("Dave", personTwo.getFirstName()));
        assertEquals("Dave", personTwo.getMiddleName(), testFailedMessage("Dave", personTwo.getMiddleName()));
        assertEquals("Lloyd! Atkinson", personTwo.getLastName(), testFailedMessage("Lloyd! Atkinson", personTwo.getLastName()));
        assertEquals("Mr.", personTwo.getPrefix(), testFailedMessage("Mr.", personTwo.getPrefix()));
        assertEquals("Jr.", personTwo.getSuffix(), testFailedMessage("Jr.", personTwo.getSuffix()));
    }

    @Test
    void Name_setParseSuffixAndPrefixInLastName_PunctuatedLastName(){
        //Note: this method is a private method, that gets called automatically every time setLastName() is called.
        Name personOne = new Name("Dave", "Dave", "Mr. O’Rourke!, Jr");
        assertEquals("Dave", personOne.getFirstName(), testFailedMessage("Dave", personOne.getFirstName()));
        assertEquals("Dave", personOne.getMiddleName(), testFailedMessage("Dave", personOne.getMiddleName()));
        assertEquals("O’Rourke!", personOne.getLastName(), testFailedMessage("O’Rourke!", personOne.getLastName()));
        assertEquals("Mr.", personOne.getPrefix(), testFailedMessage("Mr.", personOne.getPrefix()));
        assertEquals("Jr.", personOne.getSuffix(), testFailedMessage("Jr.", personOne.getSuffix()));
    }

    @Test
    void Name_isMiddleNameInFullName_True_PunctuationIgnoreSpaces(){
        Name name = new Name();

        assertTrue(name.isMiddleNameInFullName("Mr.Page,Christopher,James,1st"));
        assertTrue(name.isMiddleNameInFullName("Mr. Page, Christopher, James, I"));
        assertTrue(name.isMiddleNameInFullName("Page, Christopher, James"));
    }

    @Test
    void Name_isMiddleNameInFullName_True_MissingCommas(){
        Name name = new Name();

        assertTrue(name.isMiddleNameInFullName("Mr.Page Christopher James 1st"));
        assertTrue(name.isMiddleNameInFullName("Mr.Page Christopher James"));
        assertTrue(name.isMiddleNameInFullName("Page Christopher James"));
    }

    @Test
    void Name_isMiddleNameInFullName_False_PunctuationIgnoreSpaces(){
        Name name = new Name();

        assertFalse(name.isMiddleNameInFullName("Mr.Page,James,1st"));
        assertFalse(name.isMiddleNameInFullName("Mr. Page, James, I"));
        assertFalse(name.isMiddleNameInFullName("Page, James"));
    }
}