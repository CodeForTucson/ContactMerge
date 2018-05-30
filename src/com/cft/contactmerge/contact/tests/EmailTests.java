package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.contact.Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTests {
    // Note: isMatch() tests are implemented in CompareContactPartsTest.class

    /*******************************************************************************************************************
     ************************************************ Constructor Tests ************************************************
     *******************************************************************************************************************/
    @Test
    void Email_Constructor() {
        Email email = new Email();

        assertNotNull(email);
    }

    @Test
    void Email_ConstructorValuesFilled() {
        Email email = new Email("jfk@yahoo.com");

        assertEquals("jfk@yahoo.com", email.getEmailAddress());
    }

    /*******************************************************************************************************************
     ************************************************** Get/Set Tests **************************************************
     *******************************************************************************************************************/
    @Test
    void Email_getSetEmailAddress() {
        Email email = new Email();

        email.setEmailAddress("jfk@yahoo.com");
        assertEquals("jfk@yahoo.com", email.getEmailAddress());
    }

    /*******************************************************************************************************************
     ************************************************** toString Tests *************************************************
     *******************************************************************************************************************/
    @Test
    void Email_toString(){
        Email email = new Email("jfk@yahoo.com");
        assertEquals("jfk@yahoo.com", email.toString());
    }

    /*******************************************************************************************************************
     ******************************************** isEmailAddressMatch Tests ********************************************
     *******************************************************************************************************************/
    @Test
    void isEmailAddressMatch_Yes_IgnoreSpaces() {
        Email originalEmail = new Email("jdoe@yahoo.com");

        assertEquals(AnswerType.yes, originalEmail.isEmailAddressMatch(" jdoe @yahoo.com "));
    }

    @Test
    void isEmailAddressMatch_Maybe_IgnoreCase() {
        Email originalEmail = new Email("jdoe@yahoo.com");

        assertEquals(AnswerType.maybe, originalEmail.isEmailAddressMatch("JDoe@Yahoo.com"));
    }

    @Test
    void isEmailAddressMatch_No() {
        Email originalEmail = new Email("jdoe@yahoo.com");

        assertEquals(AnswerType.no, originalEmail.isEmailAddressMatch("jdoe@gmail.com"));
    }

    @Test
    void isEmailAddressMatch_No_Missing() {
        Email originalEmail = new Email("jdoe@yahoo.com");

        assertEquals(AnswerType.no, originalEmail.isEmailAddressMatch(""));
    }
}