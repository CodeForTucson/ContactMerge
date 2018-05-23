package com.cft.contactmerge;

import java.util.ArrayList;
import java.util.Arrays;

public class ZipMatchLogic {
    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getZipMatchResult(ArrayList<String> zipParts1, ArrayList<String> zipParts2){
        return null;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static ArrayList<String> setNormalizeZip(String zip){
        return new ArrayList<>(Arrays.asList(zip.trim().replaceAll("[^0-9 ]", " ").
                toLowerCase().split("\\s+")));
    }
}
