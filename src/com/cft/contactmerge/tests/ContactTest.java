package com.cft.contactmerge.tests;

import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.Contact;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    @org.junit.jupiter.api.Test
    void isMatch() {

        // TODO: Need to add real ContactTest Tests
        Contact newContract = new Contact();
        Assertions.assertEquals(AnswerType.yes, newContract.IsMatch(null));
    }
}