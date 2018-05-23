package com.cft.contactmerge;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryMatchLogic {
    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getCountryMatchResult(ArrayList<String> countryParts1, ArrayList<String> countryParts2){
        return null;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static ArrayList<String> setNormalizeCountry(String country){
        return new ArrayList<>(Arrays.asList(country.trim().replaceAll("[^a-zA-Z ]", " ").
                toLowerCase().split("\\s+")));
    }
}