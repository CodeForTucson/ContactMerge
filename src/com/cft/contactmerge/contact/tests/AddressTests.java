package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.contact.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTests {
    public String testFailedMessage(String valueOne, String valueTwo){
        return "<Expected Value: " + valueOne + ">" + " -vs- " + "<Actual Value: " + valueTwo + ">";
    }

    @Test
    void Address_Constructor() {
        Address address = new Address();
        assertNotNull(address);
    }

    @Test
    void Address_ConstructorValuesFilled() {
        Address address = new Address("ghi", "4", "hts", "az", "usa", "85713");
        assertEquals("ghi", address.getStreetAddress(), testFailedMessage("ghi", address.getStreetAddress()));
        assertEquals("4", address.getApartment(), testFailedMessage("4", address.getApartment()));
        assertEquals("hts", address.getCity(), testFailedMessage("hts", address.getCity()));
        assertEquals("az", address.getState(), testFailedMessage("az", address.getState()));
        assertEquals("usa", address.getCountry(), testFailedMessage("usa", address.getCountry()));
        assertEquals("85713", address.getZip(), testFailedMessage("85713", address.getZip()));
    }

    @Test
    void Address_getSetStreetAddress(){
        Address address = new Address();
        address.setStreetAddress("ghi");
        assertEquals("ghi", address.getStreetAddress());
    }

    @Test
    void Address_getSetApartment(){
        Address address = new Address();
        address.setApartment("4");
        assertEquals("4", address.getApartment());
    }

    @Test
    void Address_getSetCity(){
        Address address = new Address();
        address.setCity("hts");
        assertEquals("hts", address.getCity());
    }

    @Test
    void Address_getSetState(){
        Address address = new Address();
        address.setState("az");
        assertEquals("az", address.getState());
    }

    @Test
    void Address_getSetCountry(){
        Address address = new Address();
        address.setCountry("usa");
        assertEquals("usa", address.getCountry());
    }

    @Test
    void Address_getSetZip(){
        Address address = new Address();
        address.setZip("85713");
        assertEquals("85713", address.getZip());
    }

    @Test
    void Address_toString(){
        Address address = new Address("ghi", "4", "hts", "az", "usa", "85713");
        assertEquals("ghi, 4, hts, az, usa, 85713",address.toString());
    }

    @Test
    void Address_toString_OnlyZip(){
        Address address = new Address("", "", "", "", "", "85713");
        assertEquals("85713",address.toString());
    }

    @Test
    void Address_toString_MissingCountry(){
        Address address = new Address("ghi", "4", "hts", "az", "", "85713");
        assertEquals("ghi, 4, hts, az, 85713",address.toString());
    }

    @Test
    void Address_toString_OnlyStreetAddress(){
        Address address = new Address("ghi", "", "", "", "", "");
        assertEquals("ghi",address.toString());
    }
}