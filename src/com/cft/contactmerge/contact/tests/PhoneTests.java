package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.contact.Phone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneTests {
    public String testFailedMessage(String valueOne, String valueTwo){
        return "<Expected Value: " + valueOne + ">" + " -vs- " + "<Actual Value: " + valueTwo + ">";
    }

    @Test
    void Phone_Constructor(){
        Phone phone = new Phone();
        assertNotNull(phone);
    }

    @Test
    void Phone_ConstructorValuesFilled(){
        Phone phone = new Phone("520", "7734512");
        assertEquals("520", phone.getAreaCode(), testFailedMessage("520", phone.getAreaCode()));
        assertEquals("7734512", phone.getPhoneNumber(), testFailedMessage("7734512", phone.getPhoneNumber()));
    }

    @Test
    void Phone_getSetAreaCode(){
        Phone phone = new Phone();
        phone.setAreaCode("520");
        assertEquals("520", phone.getAreaCode());
    }

    @Test
    void Phone_getSetPhoneNumber(){
        Phone phone = new Phone();
        phone.setPhoneNumber("7734512");
        assertEquals("7734512", phone.getPhoneNumber());
    }

    @Test
    void Phone_toString(){
        Phone phone = new Phone("", "011447582468305");
        assertEquals("011447582468305", phone.toString());
    }

    @Test
    void Phone_toString_UsaPhoneNumber(){
        Phone phone = new Phone("520", "7734512");
        assertEquals("(520) 773-4512", phone.toString());
    }

    @Test
    void Phone_toString_UsaPhoneNumberAreaCodeCombined(){
        Phone phone = new Phone("", "5207734512");
        assertEquals("(520) 773-4512", phone.toString());
    }
}