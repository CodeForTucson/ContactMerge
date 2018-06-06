package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.contact.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressTests {
    // Note: matching methods tests are implemented in CompareContactPartsTest.class
    public String testFailedMessage(String valueOne, String valueTwo){
        return "<Expected Value: " + valueOne + ">" + " -vs- " + "<Actual Value: " + valueTwo + ">";
    }

    /*******************************************************************************************************************
     ************************************************ Constructor Tests ************************************************
     *******************************************************************************************************************/
    @Test
    void Address_Constructor() {
        Address address = new Address();
        assertNotNull(address);
    }

    @Test
    void Address_Constructor_AllAddressPartsFilled() {
        Address address = new Address("ghi", "4", "hts", "az", "usa", "85713");
        assertEquals("ghi", address.getStreet(), testFailedMessage("ghi", address.getStreet()));
        assertEquals("4", address.getApartment(), testFailedMessage("4", address.getApartment()));
        assertEquals("hts", address.getCity(), testFailedMessage("hts", address.getCity()));
        assertEquals("az", address.getState(), testFailedMessage("az", address.getState()));
        assertEquals("usa", address.getCountry(), testFailedMessage("usa", address.getCountry()));
        assertEquals("85713", address.getZip(), testFailedMessage("85713", address.getZip()));
    }

    @Test
    void Address_Constructor_StreetAddressOnly() {
        Address address = new Address("ghi", null, null, null, null, null);
        assertEquals("ghi", address.getStreet(), testFailedMessage("ghi", address.getStreet()));
        assertNull(address.getApartment(), testFailedMessage("null", address.getApartment()));
        assertNull(address.getCity(), testFailedMessage("null", address.getCity()));
        assertNull(address.getState(), testFailedMessage("null", address.getState()));
        assertNull(address.getCountry(), testFailedMessage("null", address.getCountry()));
        assertNull(address.getZip(), testFailedMessage("null", address.getZip()));
    }

    @Test
    void Address_Constructor_CityOnly() {
        Address address = new Address(null, null, "Tucson", null, null, null);
        assertNull(address.getStreet(), testFailedMessage("null", address.getStreet()));
        assertNull(address.getApartment(), testFailedMessage("null", address.getApartment()));
        assertEquals("Tucson", address.getCity(), testFailedMessage("Tucson", address.getCity()));
        assertNull(address.getState(), testFailedMessage("null", address.getState()));
        assertNull(address.getCountry(), testFailedMessage("null", address.getCountry()));
        assertNull(address.getZip(), testFailedMessage("null", address.getZip()));
    }

    private StreetAddress createMockStreetAddress() {
        StreetAddress streetAddressMock = mock(StreetAddress.class);
        when(streetAddressMock.getValue()).thenReturn("123 Main St");

        return streetAddressMock;
    }

    private Apartment createMockApartment() {
        Apartment apartmentMock = mock(Apartment.class);
        when(apartmentMock.getValue()).thenReturn("10");

        return apartmentMock;
    }

    private GeneralProperty createMockCity() {
        GeneralProperty cityMock = mock(GeneralProperty.class);
        when(cityMock.getValue()).thenReturn("Tucson");

        return cityMock;
    }

    private State createMockState() {
        State stateMock = mock(State.class);
        when(stateMock.getValue()).thenReturn("AZ");

        return stateMock;
    }

    private Zip createMockZip() {
        Zip zipMock = mock(Zip.class);
        when(zipMock.getValue()).thenReturn("85750");

        return zipMock;
    }

    private Address createTestAddress() {

        return new Address(createMockStreetAddress(), createMockApartment(), createMockCity(), createMockState(),
                createMockZip());
    }

    @Test
    void Constructor()
    {
        Address address = createTestAddress();
        assertNotNull(address);
    }

    @Test
    void Constructor_ApartmentAndZipOptional()
    {
        Address address = new Address(createMockStreetAddress(), null, createMockCity(),
                createMockState(), null);

        assertNotNull(address);
    }

    @Test
    void Constructor_NullStreetAddress()
    {
        assertThrows(IllegalArgumentException.class, () ->
                new Address(null, null, createMockCity(), createMockState(), null));
    }

    @Test
    void Constructor_NullCity()
    {
        assertThrows(IllegalArgumentException.class, () ->
                new Address(createMockStreetAddress(),
                        null,
                        null,
                        createMockState(),
                        null));
    }

    @Test
    void Constructor_NullState()
    {
        assertThrows(IllegalArgumentException.class, () ->
                new Address(createMockStreetAddress(), null, createMockCity(), null, null));
    }


    /*******************************************************************************************************************
     ************************************************** Get/Set Tests **************************************************
     *******************************************************************************************************************/
    @Test
    void Address_setFullAddress_AllAddressPartsFilled(){
        Address address = new Address();
        address.setFullAddress("123 main st", "apt 5", "Tucson", "AZ", "USA", "85713");

        assertEquals("123 main st", address.getStreet(), testFailedMessage("123 main st", address.getStreet()));
        assertEquals("apt 5", address.getApartment(), testFailedMessage("apt 5", address.getApartment()));
        assertEquals("Tucson", address.getCity(), testFailedMessage("Tucson", address.getCity()));
        assertEquals("AZ", address.getState(), testFailedMessage("AZ", address.getState()));
        assertEquals("USA", address.getCountry(), testFailedMessage("USA", address.getCountry()));
        assertEquals("85713", address.getZip(), testFailedMessage("85713", address.getZip()));
    }

    @Test
    void Address_setFullAddress_StreetAddressOnly() {
        Address address = new Address();
        address.setFullAddress("123 main st", null, null, null, null, null);

        assertEquals("123 main st", address.getStreet(), testFailedMessage("123 main st", address.getStreet()));
        assertNull(address.getApartment(), testFailedMessage("null", address.getApartment()));
        assertNull(address.getCity(), testFailedMessage("null", address.getCity()));
        assertNull(address.getState(), testFailedMessage("null", address.getState()));
        assertNull(address.getCountry(), testFailedMessage("null", address.getCountry()));
        assertNull(address.getZip(), testFailedMessage("null", address.getZip()));
    }

    @Test
    void Address_setFullAddress_StreetAddressAndApartmentOnly() {
        Address address = new Address();
        address.setFullAddress("123 main st", "apt 5", null, null, null, null);

        assertEquals("123 main st", address.getStreet(), testFailedMessage("123 main st", address.getStreet()));
        assertEquals("apt 5", address.getApartment(), testFailedMessage("apt 5", address.getApartment()));
        assertNull(address.getCity(), testFailedMessage("null", address.getCity()));
        assertNull(address.getState(), testFailedMessage("null", address.getState()));
        assertNull(address.getCountry(), testFailedMessage("null", address.getCountry()));
        assertNull(address.getZip(), testFailedMessage("null", address.getZip()));
    }

    @Test
    void Address_getSetStreetAddress(){
        Address address = new Address();
        address.setStreet("ghi");
        assertEquals("ghi", address.getStreet());
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
    void Address_setAddressPartsNull(){
        Address address = new Address();
        assertNull(address.getStreet(), testFailedMessage("null", address.getStreet()));
        assertNull(address.getApartment(), testFailedMessage("null", address.getApartment()));
        assertNull(address.getCity(), testFailedMessage("null", address.getCity()));
        assertNull(address.getState(), testFailedMessage("null", address.getState()));
        assertNull(address.getCountry(), testFailedMessage("null", address.getCountry()));
        assertNull(address.getZip(), testFailedMessage("null", address.getZip()));

        Address address2 = new Address("123 main st", "apt 5", "Tucson", "AZ", "USA", "85713");
        assertEquals("123 main st", address2.getStreet(), testFailedMessage("123 main st", address2.getStreet()));
        assertEquals("apt 5", address2.getApartment(), testFailedMessage("apt 5", address2.getApartment()));
        assertEquals("Tucson", address2.getCity(), testFailedMessage("Tucson", address2.getCity()));
        assertEquals("AZ", address2.getState(), testFailedMessage("AZ", address2.getState()));
        assertEquals("USA", address2.getCountry(), testFailedMessage("USA", address2.getCountry()));
        assertEquals("85713", address2.getZip(), testFailedMessage("85713", address2.getZip()));

        address2.setAddressPartsNull();
        assertNull(address2.getStreet(), testFailedMessage("null", address2.getStreet()));
        assertNull(address2.getApartment(), testFailedMessage("null", address2.getApartment()));
        assertNull(address2.getCity(), testFailedMessage("null", address2.getCity()));
        assertNull(address2.getState(), testFailedMessage("null", address2.getState()));
        assertNull(address2.getCountry(), testFailedMessage("null", address2.getCountry()));
        assertNull(address2.getZip(), testFailedMessage("null", address2.getZip()));
    }

    /*******************************************************************************************************************
     ************************************************ Sub-Methods Tests ************************************************
     *******************************************************************************************************************/
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