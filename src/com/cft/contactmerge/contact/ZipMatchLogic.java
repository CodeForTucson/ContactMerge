package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

import java.util.ArrayList;
import java.util.Arrays;

public class ZipMatchLogic {
    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getZipMatchResult(ArrayList<String> zipParts1, ArrayList<String> zipParts2){
        if (zipParts1.size() > 1 ^ zipParts2.size() > 1){
            if (zipParts1.size() > zipParts2.size() && isZipMatchWithExtention(zipParts1, zipParts2)){
                return AnswerType.maybe;
            } else if (zipParts2.size() > zipParts1.size() && isZipMatchWithExtention(zipParts2, zipParts1)) {
                return AnswerType.maybe;
            }
        }

        return AnswerType.no;
    }

    public static boolean isZipMatchWithExtention(ArrayList<String> zipWIthExtention, ArrayList<String> zipParts){
        for(String zipPart: zipParts){
            if (!zipWIthExtention.contains(zipPart)){
                return false;
            }
        }

        return true;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static ArrayList<String> setNormalizeZip(String zip){
        return new ArrayList<>(Arrays.asList(zip.trim().replaceAll("[^0-9 ]", " ").
                toLowerCase().split("\\s+")));
    }
}
