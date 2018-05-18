package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.contact.Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTests {
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

    @Test
    void Email_getSetEmailAddress() {
        Email email = new Email();
        email.setEmailAddress("jfk@yahoo.com");
        assertEquals("jfk@yahoo.com", email.getEmailAddress());
    }

    @Test
    void Email_toString(){
        Email email = new Email("jfk@yahoo.com");
        assertEquals("jfk@yahoo.com", email.toString());
    }
}