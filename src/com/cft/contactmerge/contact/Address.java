package com.cft.contactmerge.contact;

public class Address {
    String streetAddress;
    String apartment;
    String city;
    String state;
    String country;
    String zip;
    String addressType;

    /*******************************************************************************************************************
     *************************************************** Constructors **************************************************
     *******************************************************************************************************************/
    public Address(){
        setStreetAddress("");
        setApartment("");
        setCity("");
        setState("");
        setCountry("");
        setZip("");
        setAddressType("");
    }

    public Address(String streetAddress, String apartment, String city, String state, String country, String zip, String addressType){
        setStreetAddress(streetAddress);
        setApartment(apartment);
        setCity(city);
        setState(state);
        setCountry(country);
        setZip(zip);
        setAddressType(addressType);
    }

    /*******************************************************************************************************************
     *************************************************** Get Methods ***************************************************
     *******************************************************************************************************************/
    public String getStreetAddress() {
        return this.streetAddress;
    }

    public String getApartment() {
        return this.apartment;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getCountry(){
        return this.country;
    }

    public String getZip(){
        return this.zip;
    }

    public String getAddressType() {
        return this.addressType;
    }

    /*******************************************************************************************************************
     *************************************************** Set Methods ***************************************************
     *******************************************************************************************************************/
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}
