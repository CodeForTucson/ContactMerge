package com.cft.contactmerge;

import java.util.ArrayList;
import java.util.Arrays;

public class StateMatchLogic {
    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getStateMatchResult(ArrayList<String> stateParts1, ArrayList<String> stateParts2){
        return null;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static ArrayList<String> setNormalizeState(String state){
        return new ArrayList<>(Arrays.asList(state.trim().replaceAll("[^a-zA-Z ]", " ").
                toLowerCase().split("\\s+")));
    }
}
