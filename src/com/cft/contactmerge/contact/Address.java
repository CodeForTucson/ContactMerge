package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

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
        StringBuilder fullAddress = new StringBuilder();
        String[] addressParts = {getStreetAddress(), getApartment(), getCity(), getState(), getCountry(), getZip()};

        for (String part: addressParts){
            if (!part.isEmpty()){
                fullAddress.append(part).append(", ");
            }
        }

        if (!fullAddress.toString().isEmpty()){
            fullAddress.delete(fullAddress.length() - 2, fullAddress.length());
        }

        return fullAddress.toString();
    }

    public AnswerType isAddressMatch(Address otherAddress){
        /* ToDo: Discuss compare parts combinations for final return type...
         * (streetAddress = yes | apartment = yes | city = yes | state = yes | country = yes | zip = yes) = Answertype.yes
         * (streetAddress = no | apartment = no | city = no | state = no | country = no | zip = no) = Answertype.no
         * (streetAddress = yes | apartment = no | city = yes | state = no | country = yes | zip = no) = Answertype.maybe??
         * (streetAddress = yes | apartment = maybe | city = yes | state = maybe | country = yes | zip = maybe) = Answertype.maybe??
         * (streetAddress = maybe | apartment = no | city = yes | state = yes | country = yes | zip = maybe) = Answertype.maybe??
         * ect...
         * also discuss what to return if an address part is null (e.g.: this.country == null)
         */
        return AnswerType.no;
    }

    public AnswerType isStreetAddressMatch(String otherStreetAddress){
        /*
         * ToDo: Discuss to use compare(this.streetAddress, otherStreetAddress), or...
         * add a 2nd argument in this method, and compare (streetAddressOne, streetAddressTwo)
         */
        return AnswerType.no;
    }
}