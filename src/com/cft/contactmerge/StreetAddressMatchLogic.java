package com.cft.contactmerge;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class StreetAddressMatchLogic {
    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getStreetAddressMatchResult(ArrayList<String> stAddress1, ArrayList<String> stAddress2){
        // ToDo: Fill in code to obtain matching result
        return AnswerType.no;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static ArrayList<String> setNormalizeStreetAddress(String stAddress){
        return new ArrayList<>(Arrays.asList(stAddress.trim().replaceAll("[^a-zA-Z0-9 ]", " ").
                toLowerCase().split("\\s+")));
    }
}
