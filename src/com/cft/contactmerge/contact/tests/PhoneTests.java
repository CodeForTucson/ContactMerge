package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.contact.Phone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneTests {
    public String testFailedMessage(String valueOne, String valueTwo){
        // Note: isMatch() tests are implemented in CompareContactPartsTest.class
        return "<Expected Value: " + valueOne + ">" + " -vs- " + "<Actual Value: " + valueTwo + ">";
    }

    /*******************************************************************************************************************
     ************************************************ Constructor Tests ************************************************
     *******************************************************************************************************************/
    @Test
    void Phone_Constructor(){
        Phone phone = new Phone();
        assertNotNull(phone);
    }

    @Test
    void Phone_ConstructorValuesFilled(){
        Phone phone = new Phone("15207734512");
        assertEquals("1", phone.getCountryCallingCode(), testFailedMessage("1", phone.getCountryCallingCode()));
        assertEquals("520", phone.getAreaCode(), testFailedMessage("520", phone.getAreaCode()));
        assertEquals("7734512", phone.getPhoneNumber(), testFailedMessage("7734512", phone.getPhoneNumber()));
        assertEquals("15207734512", phone.getFullNumber(), testFailedMessage("15207734512", phone.getFullNumber()));
        assertEquals("1(520) 773-4512", phone.toString(), testFailedMessage("1(520) 773-4512", phone.toString()));
    }

    @Test
    void Phone_ConstructorValuesFilled_IgnoreSpaces_IgnorePunctuation(){
        Phone phone = new Phone("1(520) 773-4512");
        assertEquals("1", phone.getCountryCallingCode(), testFailedMessage("1", phone.getCountryCallingCode()));
        assertEquals("520", phone.getAreaCode(), testFailedMessage("520", phone.getAreaCode()));
        assertEquals("7734512", phone.getPhoneNumber(), testFailedMessage("7734512", phone.getPhoneNumber()));
        assertEquals("15207734512", phone.getFullNumber(), testFailedMessage("15207734512", phone.getFullNumber()));
        assertEquals("1(520) 773-4512", phone.toString(), testFailedMessage("1(520) 773-4512", phone.toString()));
    }

    /*******************************************************************************************************************
     ************************************************** Get/Set Tests **************************************************
     *******************************************************************************************************************/
    @Test
    void Phone_getCountryCallingCode(){
        Phone phone1 = new Phone("16108375937");
        Phone phone2 = new Phone("011446108375937");
        assertEquals("1", phone1.getCountryCallingCode(), testFailedMessage("1", phone1.getCountryCallingCode()));
        assertEquals("01144", phone2.getCountryCallingCode(), testFailedMessage("01144", phone2.getCountryCallingCode()));
    }

    @Test
    void Phone_getAreaCode(){
        Phone phone1 = new Phone("5207734512");
        Phone phone2 = new Phone("16108375937");
        Phone phone3 = new Phone("011446108375937");
        assertEquals("520", phone1.getAreaCode(), testFailedMessage("520", phone1.getAreaCode()));
        assertEquals("610", phone2.getAreaCode(), testFailedMessage("610", phone2.getAreaCode()));
        assertEquals("610", phone3.getAreaCode(), testFailedMessage("610", phone3.getAreaCode()));
    }

    @Test
    void Phone_getPhoneNumber(){
        Phone phone1 = new Phone("7734512");
        Phone phone2 = new Phone("5207734512");
        Phone phone3 = new Phone("15207734512");
        assertEquals("7734512", phone1.getPhoneNumber(), testFailedMessage("7734512", phone1.getPhoneNumber()));
        assertEquals("7734512", phone2.getPhoneNumber(), testFailedMessage("7734512", phone2.getPhoneNumber()));
        assertEquals("7734512", phone3.getPhoneNumber(), testFailedMessage("7734512", phone3.getPhoneNumber()));
    }

    @Test
    void Phone_setGetFullNumber(){
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        Phone phone3 = new Phone();
        Phone phone4 = new Phone();

        phone1.setFullNumber("7734512");
        assertEquals("7734512", phone1.getFullNumber(), testFailedMessage("7734512", phone1.getFullNumber()));

        phone2.setFullNumber("5207734512");
        assertEquals("5207734512", phone2.getFullNumber(), testFailedMessage("5207734512", phone2.getFullNumber()));

        phone3.setFullNumber("15207734512");
        assertEquals("15207734512", phone3.getFullNumber(), testFailedMessage("15207734512", phone3.getFullNumber()));

        phone4.setFullNumber("1(520) 773-4512");
        assertEquals("15207734512", phone4.getFullNumber(), testFailedMessage("15207734512", phone4.getFullNumber()));
    }

    /*******************************************************************************************************************
     ************************************************** toString Tests *************************************************
     *******************************************************************************************************************/
    @Test
    void Phone_toString(){
        Phone phone1 = new Phone("011447582468305");
        Phone phone2 = new Phone("15207734512");
        Phone phone3 = new Phone("5207734512");
        Phone phone4 = new Phone("7734512");

        assertEquals("01144(758) 246-8305", phone1.toString(), testFailedMessage("01144(758) 246-8305", phone1.toString()));
        assertEquals("1(520) 773-4512", phone2.toString(), testFailedMessage("1(520) 773-4512", phone2.toString()));
        assertEquals("(520) 773-4512", phone3.toString(), testFailedMessage("(520) 773-4512", phone3.toString()));
        assertEquals("773-4512", phone4.toString(), testFailedMessage("773-4512", phone4.toString()));
    }
}