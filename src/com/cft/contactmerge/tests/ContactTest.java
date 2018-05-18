package com.cft.contactmerge.tests;

import com.cft.contactmerge.ContactMatchType;
import com.cft.contactmerge.contact.Address;
import com.cft.contactmerge.contact.Email;
import com.cft.contactmerge.contact.Name;
import com.cft.contactmerge.contact.Phone;
import org.junit.jupiter.api.Test;
import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.Contact;
import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    @Test
    void setName() {
        Contact newContact = new Contact();
        Name name = new Name("john", "leon", "adams", "dr", "III");
        newContact.setName(name);

        assertEquals("john", newContact.getName().getFirstName());
        assertEquals("leon", newContact.getName().getMiddleName());
        assertEquals("adams", newContact.getName().getLastName());
        assertEquals("dr", newContact.getName().getPrefix());
        assertEquals("III", newContact.getName().getSuffix());
    }

    @Test
    void setAddress() {
        Contact newContact = new Contact();
        Address address = new Address("ghi", "4", "hts", "az", "usa", "85713");
        newContact.setAddress(address);
        assertEquals("ghi", newContact.getAddress().getStreetAddress());
        assertEquals("4", newContact.getAddress().getApartment());
        assertEquals("hts", newContact.getAddress().getCity());
        assertEquals("az", newContact.getAddress().getState());
        assertEquals("usa", newContact.getAddress().getCountry());
        assertEquals("85713", newContact.getAddress().getZip());
    }

    @Test
    void setPhone() {
        Contact newContact = new Contact();
        Phone phone = new Phone("520", "7734512");
        newContact.setPhone(phone);
        assertEquals("520", newContact.getPhone().getAreaCode());
        assertEquals("7734512", newContact.getPhone().getPhoneNumber());
    }

    @Test
    void setEmail() {
        Contact newContact = new Contact();
        Email email = new Email("jfk@yahoo.com");
        newContact.setEmail(email);
        assertEquals("jfk@yahoo.com", newContact.getEmail().getEmailAddress());
    }

    // TODO: Need to add rest of tests for isMatch()

    @Test
    void isMatch_NoMatchOnAny() {
        Contact c1 = new Contact();
        Name name1 = new Name("John", "Ray", "Doe", "Dr", "III");
        Address address1 = new Address("123 Main St", "4", "Tucson", "AZ", "USA", "85750");
        Phone phone1 = new Phone("520", "1234567");
        Email email1 = new Email("jdoe@gmail.com");
        c1.setName(name1);
        c1.setAddress(address1);
        c1.setPhone(phone1);
        c1.setEmail(email1);

        Contact c2 = new Contact();
        Name name2 = new Name("Adam", "", "Smith", "", "");
        Address address2 = new Address("1400 Broadway", "8", "London", "", "England", "N14");
        Phone phone2 = new Phone("617", "7654321");
        Email email2 = new Email("asmith@comcast.net");
        c2.setName(name2);
        c2.setAddress(address2);
        c2.setPhone(phone2);
        c2.setEmail(email2);

        assertEquals(ContactMatchType.NoMatch, c1.CompareTo(c2).getMatchType());
    }
}