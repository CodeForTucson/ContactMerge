package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

import java.util.ArrayList;
import java.util.Hashtable;

public class Address implements IContactProperty<Address> {
    private String street;
    private String apartment;
    private String city;
    private String state;
    private String country;
    private String zip;
    private final String streetMatchHashKey = "isStreetMatch";
    private final String apartmentMatchHashKey = "isApartmentMatch";
    private final String cityMatchHashKey = "isCityMatch";
    private final String stateMatchHashKey = "isStateMatch";
    private final String countryMatchHashKey = "isCountryMatch";
    private final String zipMatchHashKey = "isZipMatch";

    /*******************************************************************************************************************
     *************************************************** Constructors **************************************************
     *******************************************************************************************************************/
    public Address() {
    }

    public Address(String street, String apartment, String city, String state, String country, String zip) {
        setFullAddress(street, apartment, city, state, country, zip);
    }

    /*******************************************************************************************************************
     ************************************************* Get/Set Methods *************************************************
     *******************************************************************************************************************/
    public void setFullAddress(String street, String apartment, String city, String state, String country, String zip){
        setStreet(street);
        setApartment(apartment);
        setCity(city);
        setState(state);
        setCountry(country);
        setZip(zip);
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Address getValue() {
        return this;
    }

    private String getStreetMatchHashKey() {
        return this.streetMatchHashKey;
    }

    private String getApartmentMatchHashKey() {
        return this.apartmentMatchHashKey;
    }

    private String getCityMatchHashKey() {
        return this.cityMatchHashKey;
    }

    private String getStateMatchHashKey() {
        return this.stateMatchHashKey;
    }

    private String getCountryMatchHashKey() {
        return this.countryMatchHashKey;
    }

    private String getZipMatchHashKey() {
        return this.zipMatchHashKey;
    }

    public void setAddressPartsNull(){
        setFullAddress(null, null, null, null, null, null);
    }

    /*******************************************************************************************************************
     ************************************************** Match Methods **************************************************
     *******************************************************************************************************************/
    public AnswerType isMatch(IContactProperty<Address> otherAddress) {
        if (isNoMatchingAvailable(otherAddress.getValue())) {
            return AnswerType.no;
        }

        Hashtable<String, AnswerType> addressPartsMatchResults = getAddressPartsMatchResults(otherAddress.getValue());
        if (addressPartsMatchResults.containsValue(AnswerType.no)) {
            return AnswerType.no;
        } else if (addressPartsMatchResults.containsValue(AnswerType.maybe)) {
            return AnswerType.maybe;
        } else if (addressPartsMatchResults.containsValue(AnswerType.yes)) {
            return AnswerType.yes;
        }

        // Default answer
        return AnswerType.no;
    }

    private Hashtable<String, AnswerType> getAddressPartsMatchResults(Address otherAddress) {
        Hashtable<String, AnswerType> addressPartsMatchResults = new Hashtable<>();

        if (!(getStreet() == null || getStreet().isEmpty()) &&
                !(otherAddress.getStreet() == null || otherAddress.getStreet().isEmpty())) {
            addressPartsMatchResults.put(getStreetMatchHashKey(), isStreetMatch(getStreet(), otherAddress.getStreet()));
        } else if (!(getStreet() == null || getStreet().isEmpty()) ^
                !(otherAddress.getStreet() == null || otherAddress.getStreet().isEmpty())) {
            addressPartsMatchResults.put(getStreetMatchHashKey(), AnswerType.maybe);
        }

        if (!(getApartment() == null || getApartment().isEmpty()) &&
                !(otherAddress.getApartment() == null || otherAddress.getApartment().isEmpty())) {
            addressPartsMatchResults.put(getApartmentMatchHashKey(), isApartmentMatch(getApartment(), otherAddress.getApartment()));
        } else if (!(getApartment() == null || getApartment().isEmpty()) ^
                !(otherAddress.getApartment() == null || otherAddress.getApartment().isEmpty())) {
            addressPartsMatchResults.put(getApartmentMatchHashKey(), AnswerType.maybe);
        }

        if (!(getCity() == null || getCity().isEmpty()) &&
                !(otherAddress.getCity() == null || otherAddress.getCity().isEmpty())) {
            addressPartsMatchResults.put(getCityMatchHashKey(), isCityMatch(getCity(), otherAddress.getCity()));
        } else if (!(getCity() == null || getCity().isEmpty()) ^
                !(otherAddress.getCity() == null || otherAddress.getCity().isEmpty())) {
            addressPartsMatchResults.put(getCityMatchHashKey(), AnswerType.maybe);
        }

        if (!(getState() == null || getState().isEmpty()) &&
                !(otherAddress.getState() == null || otherAddress.getState().isEmpty())) {
            addressPartsMatchResults.put(getStateMatchHashKey(), isStateMatch(getState(), otherAddress.getState()));
        } else if (!(getState() == null || getState().isEmpty()) ^
                !(otherAddress.getState() == null || otherAddress.getState().isEmpty())) {
            addressPartsMatchResults.put(getStateMatchHashKey(), AnswerType.maybe);
        }

        if (!(getCountry() == null || getCountry().isEmpty()) &&
                !(otherAddress.getCountry() == null || otherAddress.getCountry().isEmpty())) {
            addressPartsMatchResults.put(getCountryMatchHashKey(), isCountryMatch(getCountry(), otherAddress.getCountry()));
        } else if (!(getCountry() == null || getCountry().isEmpty()) ^
                !(otherAddress.getCountry() == null || otherAddress.getCountry().isEmpty())) {
            addressPartsMatchResults.put(getCountryMatchHashKey(), AnswerType.maybe);
        }

        if (!(getZip() == null || getZip().isEmpty()) &&
                !(otherAddress.getZip() == null || otherAddress.getZip().isEmpty())) {
            addressPartsMatchResults.put(getZipMatchHashKey(), isZipMatch(getZip(), otherAddress.getZip()));
        } else if (!(getZip() == null || getZip().isEmpty()) ^
                !(otherAddress.getZip() == null || otherAddress.getZip().isEmpty())) {
            addressPartsMatchResults.put(getZipMatchHashKey(), AnswerType.maybe);
        }

        return addressPartsMatchResults;
    }

    public static AnswerType isStreetMatch(String firstStreet, String secondStreet) {
        if (firstStreet == null || firstStreet.isEmpty() || secondStreet == null || secondStreet.isEmpty()) {
            return AnswerType.no;
        }

        ArrayList<String> normalizedStreetAddressOne = StreetMatchLogic.setNormalizeStreetAddress(firstStreet);
        ArrayList<String> normalizedStreetAddressTwo = StreetMatchLogic.setNormalizeStreetAddress(secondStreet);

        if (normalizedStreetAddressOne.equals(normalizedStreetAddressTwo)) {
            return AnswerType.yes;
        }
        return StreetMatchLogic.getStreetAddressMatchResult(normalizedStreetAddressOne, normalizedStreetAddressTwo);
    }

    public static AnswerType isApartmentMatch(String firstApartment, String secondApartment) { // Done
        if (firstApartment == null || firstApartment.isEmpty() || secondApartment == null || secondApartment.isEmpty()) {
            return AnswerType.no;
        }

        ArrayList<String> normalizedApartmentOne = ApartmentMatchLogic.setNormalizeApartment(firstApartment);
        ArrayList<String> normalizedApartmentTwo = ApartmentMatchLogic.setNormalizeApartment(secondApartment);

        if (normalizedApartmentOne.equals(normalizedApartmentTwo)) {
            return AnswerType.yes;
        }
        return ApartmentMatchLogic.getApartmentMatchResult(normalizedApartmentOne, normalizedApartmentTwo);
    }

    public static AnswerType isCityMatch(String firstCity, String secondCity) {
        if (firstCity == null || firstCity.isEmpty() || secondCity == null || secondCity.isEmpty()) {
            return AnswerType.no;
        }

        ArrayList<String> normalizedCityOne = CityMatchLogic.setNormalizeCity(firstCity);
        ArrayList<String> normalizedCityTwo = CityMatchLogic.setNormalizeCity(secondCity);

        if (normalizedCityOne.equals(normalizedCityTwo)) {
            return AnswerType.yes;
        }
        return CityMatchLogic.getCityMatchResult(normalizedCityOne, normalizedCityTwo);
    }

    public static AnswerType isStateMatch(String firstState, String secondState) {
        if (firstState == null || firstState.isEmpty() || secondState == null || secondState.isEmpty()) {
            return AnswerType.no;
        }

        String normalizedStateOne = StateMatchLogic.setNormalizeState(firstState);
        String normalizedStateTwo = StateMatchLogic.setNormalizeState(secondState);

        if (normalizedStateOne.equals(normalizedStateTwo)) {
            return AnswerType.yes;
        }
        return StateMatchLogic.getStateMatchResult(normalizedStateOne, normalizedStateTwo);
    }

    public static AnswerType isCountryMatch(String firstCountry, String secondCountry) {
        if (firstCountry == null || firstCountry.isEmpty() || secondCountry == null || secondCountry.isEmpty()) {
            return AnswerType.no;
        }

        String normalizedCountryOne = CountryMatchLogic.setNormalizeCountry(firstCountry);
        String normalizedCountryTwo = CountryMatchLogic.setNormalizeCountry(secondCountry);

        if (normalizedCountryOne.equals(normalizedCountryTwo)) {
            return AnswerType.yes;
        }
        return CountryMatchLogic.getCountryMatchResult(normalizedCountryOne, normalizedCountryTwo);
    }

    public static AnswerType isZipMatch(String firstZip, String secondZip){
        if (firstZip == null || firstZip.isEmpty() || secondZip == null || secondZip.isEmpty()) {
            return AnswerType.no;
        }

        ArrayList<String> normalizedZipOne = ZipMatchLogic.setNormalizeZip(firstZip);
        ArrayList<String> normalizedZipTwo = ZipMatchLogic.setNormalizeZip(secondZip);

        if (normalizedZipOne.equals(normalizedZipTwo)) {
            return AnswerType.yes;
        }
        return ZipMatchLogic.getZipMatchResult(normalizedZipOne, normalizedZipTwo);
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    @Override
    public String toString() {
        StringBuilder fullAddress = new StringBuilder();
        String[] addressParts = {getStreet(), getApartment(), getCity(), getState(), getCountry(), getZip()};

        for (String part : addressParts) {
            if (!(part == null || part.isEmpty()) && fullAddress.toString().isEmpty()) {
                fullAddress.append(part);
            } else if (!(part == null || part.isEmpty())) {
                fullAddress.append(", ").append(part);
            }
        }

        return fullAddress.toString();
    }

    private boolean isNoMatchingAvailable(Address otherAddress) {
        /* Used to detect if not any matches cannot be checked. For example:
         * (this.street equals null or is empty and otherAddress.street has a value) and...
         * (this.city has a value and otherAddress.city equals null or is empty)...
         * no matches can be made, therefor, return True
         */
        return ((getStreet() == null || getStreet().isEmpty() ||
                    otherAddress.getStreet() == null || otherAddress.getStreet().isEmpty()) &&
                (getApartment() == null || getApartment().isEmpty() ||
                        otherAddress.getApartment() == null || otherAddress.getApartment().isEmpty()) &&
                (getCity() == null || getCity().isEmpty() ||
                        otherAddress.getCity() == null || otherAddress.getCity().isEmpty()) &&
                (getState() == null || getState().isEmpty() ||
                        otherAddress.getState() == null || otherAddress.getState().isEmpty()) &&
                (getCountry() == null || getCountry().isEmpty() ||
                        otherAddress.getCountry() == null || otherAddress.getCountry().isEmpty()) &&
                (getZip() == null || getZip().isEmpty() ||
                        otherAddress.getZip() == null || otherAddress.getZip().isEmpty()));
    }

    // ToDO: create a parseAddress_Street_Apartment()
}