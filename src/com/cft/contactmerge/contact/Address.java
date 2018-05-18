package com.cft.contactmerge.contact;

public class Address {
    String streetAddress;
    String apartment;
    String city;
    String state;
    String country;
    String zip;

    /*******************************************************************************************************************
     *************************************************** Constructors **************************************************
     *******************************************************************************************************************/
    public Address(){}

    public Address(String streetAddress, String apartment, String city, String state, String country, String zip){
        setStreetAddress(streetAddress);
        setApartment(apartment);
        setCity(city);
        setState(state);
        setCountry(country);
        setZip(zip);
    }

    /*******************************************************************************************************************
     ************************************************* Get/Set Methods *************************************************
     *******************************************************************************************************************/
    public String getStreetAddress() {
        return this.streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getApartment() {
        return this.apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry(){
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip(){
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public String toString() {
        return getStreetAddress() + ", " + getApartment() + ", " + getCity() + ", " +
                getState() + ", " + getCountry() + ", " + getZip(); }
}