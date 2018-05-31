package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class ApartmentMatchLogic {
    private static final ArrayList<String> apartmentTypes = new ArrayList<>(Arrays.asList("apartment","apt","ap","suite","ste","unit"));

    /*******************************************************************************************************************
     *************************************************** Get Methods ***************************************************
     *******************************************************************************************************************/
    public static ArrayList<String> getApartmentTypes(){
        return apartmentTypes;
    }

    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getApartmentMatchResult(ArrayList<String> apartmentParts1, ArrayList<String> apartmentParts2){
        apartmentParts1 = removeApartmentType(apartmentParts1);
        apartmentParts2 = removeApartmentType(apartmentParts2);

        if (apartmentParts1.equals(apartmentParts2)){
            return AnswerType.yes;
        }

        return AnswerType.no;
    }

    public static ArrayList<String> removeApartmentType(ArrayList<String> apartmentParts){
        for (int i = 0; i < apartmentParts.size(); i++){
            for (String apartmentType: getApartmentTypes()){
                apartmentParts.set(i, apartmentParts.get(i).replaceAll(apartmentType, "").trim());
            }
            if (apartmentParts.get(i).isEmpty()) { // apartmentTypeWasRemoved
                apartmentParts.remove(i);
                i--;
            }
        }
        return apartmentParts;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static ArrayList<String> setNormalizeApartment(String apartment){
        return new ArrayList<>(Arrays.asList(apartment.trim().replaceAll("[^a-zA-Z0-9 ]", " ").
                toLowerCase().split("\\s+")));
    }
}
