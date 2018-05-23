package com.cft.contactmerge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public abstract class CityMatchLogic {
    private static final Hashtable<String,String> compassDirection = StreetAddressMatchLogic.getCompassDirection();

    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getCityMatchResult(ArrayList<String> cityParts1, ArrayList<String> cityParts2){
        return null;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static ArrayList<String> setNormalizeCity(String city){
        return new ArrayList<>(Arrays.asList(city.trim().replaceAll("[^^a-zA-Z0-9 ]", " ").
                toLowerCase().split("\\s+")));
    }
}
