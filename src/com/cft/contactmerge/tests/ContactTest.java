package com.cft.contactmerge.tests;

import com.cft.contactmerge.ContactMatchType;
import org.junit.jupiter.api.Test;
import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.Contact;
import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    @Test
    void setFirstName() {
        Contact newContact = new Contact();
        newContact.setFirstName("abc");
        assertEquals("abc", newContact.getFirstName());
    }

    @Test
    void setLastName() {
        Contact newContact = new Contact();
        newContact.setLastName("def");
        assertEquals("def", newContact.getLastName());
    }

    @Test
    void setAddress() {
        Contact newContact = new Contact();
        newContact.setAddress("ghi");
        assertEquals("ghi", newContact.getAddress());
    }

    @Test
    void setCity() {
        Contact newContact = new Contact();
        newContact.setCity("jkl");
        assertEquals("jkl", newContact.getCity());
    }

    @Test
    void setZip() {
        Contact newContact = new Contact();
        newContact.setZip("mno");
        assertEquals("mno", newContact.getZip());
    }

    @Test
    void setPhone() {
        Contact newContact = new Contact();
        newContact.setPhone("pqr");
        assertEquals("pqr", newContact.getPhone());
    }

    @Test
    void setEmail() {
        Contact newContact = new Contact();
        newContact.setEmail("stu");
        assertEquals("stu", newContact.getEmail());
    }

    // TODO: Need to add rest of tests for isMatch()

    @Test
    void isMatch_NoMatchOnAny() {
        Contact c1 = new Contact();
        c1.setFirstName("John");
        c1.setLastName("Doe");
        c1.setAddress("123 Main St");
        c1.setCity("Tucson");
        c1.setState("AZ");
        c1.setZip("85750");
        c1.setPhone("(520) 123-4567");
        c1.setEmail("jdoe@gmail.com");

        Contact c2 = new Contact();
        c2.setFirstName("Adam");
        c2.setLastName("Smith");
        c2.setAddress("1400 Broadway");
        c2.setCity("Boston");
        c2.setState("MA");
        c2.setZip("02144");
        c2.setPhone("(617) 123-4567");
        c2.setEmail("asmith@comcast.net");

        assertEquals(ContactMatchType.NoMatch, c1.CompareTo(c2).getMatchType());
    }
}