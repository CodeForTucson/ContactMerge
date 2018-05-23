package com.cft.contactmerge;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class ApartmentMatchLogic {
    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getApartmentMatchResult(ArrayList<String> apartmentParts1, ArrayList<String> apartmentParts2){
        return null;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static ArrayList<String> setNormalizeApartment(String apartment){
        return new ArrayList<>(Arrays.asList(apartment.trim().replaceAll("[^a-zA-Z0-9 ]", " ").
                toLowerCase().split("\\s+")));
    }
}
