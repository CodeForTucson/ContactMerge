package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.StreetAddressMatchLogic;
import com.cft.contactmerge.ApartmentMatchLogic;
import com.cft.contactmerge.CityMatchLogic;
import com.cft.contactmerge.StateMatchLogic;
import com.cft.contactmerge.CountryMatchLogic;
import com.cft.contactmerge.ZipMatchLogic;
import java.util.ArrayList;

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
    @Override
    public String toString() {
        StringBuilder fullAddress = new StringBuilder();
        String[] addressParts = {getStreetAddress(), getApartment(), getCity(), getState(), getCountry(), getZip()};

        for (String part: addressParts){
            if (!part.isEmpty() && fullAddress.toString().isEmpty()){
                fullAddress.append(part);
            } else if (!part.isEmpty()){
                fullAddress.append(", ").append(part);
            }
        }

        return fullAddress.toString();
    }

    public AnswerType isAddressMatch(Address otherAddress){
        // ToDo: Once all parts match methods are done, write tests for this method and fill in the code
        // Precondition: All address parts except apartment and zip must not be empty.
        if (getStreetAddress() == null || getStreetAddress().isEmpty() || getCity() == null || getCity().isEmpty() ||
                getState() == null || getState().isEmpty() || getCountry() == null || getCountry().isEmpty() ||
                otherAddress.getStreetAddress() == null || otherAddress.getStreetAddress().isEmpty() ||
                otherAddress.getCity() == null || otherAddress.getCity().isEmpty() ||
                otherAddress.getState() == null || otherAddress.getState().isEmpty() ||
                otherAddress.getCountry() == null || otherAddress.getCountry().isEmpty()){
            return AnswerType.no;
        }
        //AnswerType streetAddressMatchResult = isStreetAddressMatch();
        AnswerType apartmentMatchResult;
        //AnswerType cityMatchResult = isCityMatch();
        //AnswerType stateMatchResult = isStateMatch();
        //AnswerType countryMatchResult = isCountryMatch();
        AnswerType zipMatchResult;

        //return getAddressPartsMatchResult(streetAddressMatchResult, apartmentMatchResult, cityMatchResult, stateMatchResult, countryMatchResult, zipMatchResult);
        return AnswerType.no;
    }

    public AnswerType getAddressPartsMatchResult(AnswerType streetAddressMatchResult, AnswerType apartmentMatchResult,
                                                 AnswerType cityMatchResult, AnswerType stateMatchResult,
                                                 AnswerType countryMatchResult, AnswerType zipMatchResult){
        // ToDo: Once all parts match methods are done, this will be a sub-method for isAddressMatch()
        AnswerType[] matchResults = {streetAddressMatchResult, apartmentMatchResult, cityMatchResult, stateMatchResult, countryMatchResult, zipMatchResult};
        boolean isAllMatchResultsYes = true;

        // return yes if all match results (except zip and apartment if they are null) are yes
        for (int matchResult = 0; matchResult < matchResults.length; matchResult++){
            if (matchResult == 1 || matchResult == matchResults.length - 1) { // if apartment or zip
                if (matchResults[matchResult].equals(AnswerType.no)) {
                    isAllMatchResultsYes = false;
                    break;
                }
            } else {
                if (!matchResults[matchResult].equals(AnswerType.yes)){
                    isAllMatchResultsYes = false;
                    break;
                }
            }
        }
        if (isAllMatchResultsYes){
            return AnswerType.yes;
        }

        // return maybe if streetAddressMatchResult == maybe, and everything else is yes (and apartment/zip can be maybe or null)

        return AnswerType.no;
    }

    public AnswerType isStreetAddressMatch(String otherStreetAddress){
        if (getStreetAddress() == null || getStreetAddress().isEmpty() || otherStreetAddress.isEmpty()){
            return AnswerType.no;
        }

        ArrayList<String> normalizedStreetAddressOne = StreetAddressMatchLogic.setNormalizeStreetAddress(getStreetAddress());
        ArrayList<String> normalizedStreetAddressTwo = StreetAddressMatchLogic.setNormalizeStreetAddress(otherStreetAddress);

        if (normalizedStreetAddressOne.equals(normalizedStreetAddressTwo)){
            return AnswerType.yes;
        }
        return StreetAddressMatchLogic.getStreetAddressMatchResult(normalizedStreetAddressOne, normalizedStreetAddressTwo);
    }

    public AnswerType isApartmentMatch(String otherApartment){ // Done
        if (getApartment() == null || getApartment().isEmpty() || otherApartment.isEmpty()){
            return AnswerType.no;
        }

        ArrayList<String> normalizedApartmentOne = ApartmentMatchLogic.setNormalizeApartment(getApartment());
        ArrayList<String> normalizedApartmentTwo = ApartmentMatchLogic.setNormalizeApartment(otherApartment);

        if (normalizedApartmentOne.equals(normalizedApartmentTwo)){
            return AnswerType.yes;
        }
        return ApartmentMatchLogic.getApartmentMatchResult(normalizedApartmentOne, normalizedApartmentTwo);
    }

    public AnswerType isCityMatch(String otherCity){
        if (getCity() == null || getCity().isEmpty() || otherCity.isEmpty()){
            return AnswerType.no;
        }

        ArrayList<String> normalizedCityOne = CityMatchLogic.setNormalizeCity(getCity());
        ArrayList<String> normalizedCityTwo = CityMatchLogic.setNormalizeCity(otherCity);

        if (normalizedCityOne.equals(normalizedCityTwo)){
            return AnswerType.yes;
        }
        return CityMatchLogic.getCityMatchResult(normalizedCityOne, normalizedCityTwo);
    }

    public AnswerType isStateMatch(String otherState){
        if (getState() == null || getState().isEmpty() || otherState.isEmpty()){
            return AnswerType.no;
        }

        ArrayList<String> normalizedStateOne = StateMatchLogic.setNormalizeState(getState());
        ArrayList<String> normalizedStateTwo = StateMatchLogic.setNormalizeState(otherState);

        if (normalizedStateOne.equals(normalizedStateTwo)){
            return AnswerType.yes;
        }
        return StateMatchLogic.getStateMatchResult(normalizedStateOne, normalizedStateTwo);
    }

    public AnswerType isCountryMatch(String otherCountry){
        if (getCountry() == null || getCountry().isEmpty() || otherCountry.isEmpty()){
            return AnswerType.no;
        }

        ArrayList<String> normalizedCountryOne = CountryMatchLogic.setNormalizeCountry(getCountry());
        ArrayList<String> normalizedCountryTwo = CountryMatchLogic.setNormalizeCountry(otherCountry);

        if (normalizedCountryOne.equals(normalizedCountryTwo)){
            return AnswerType.yes;
        }
        return CountryMatchLogic.getCountryMatchResult(normalizedCountryOne, normalizedCountryTwo);
    }

    public AnswerType isZipMatch(String otherZip){
        if (getZip() == null || getZip().isEmpty() || otherZip.isEmpty()){
            return AnswerType.no;
        }

        ArrayList<String> normalizedZipOne = ZipMatchLogic.setNormalizeZip(getZip());
        ArrayList<String> normalizedZipTwo = ZipMatchLogic.setNormalizeZip(otherZip);

        if (normalizedZipOne.equals(normalizedZipTwo)){
            return AnswerType.yes;
        }
        return ZipMatchLogic.getZipMatchResult(normalizedZipOne, normalizedZipTwo);
    }
}