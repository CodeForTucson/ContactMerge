package com.cft.contactmerge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class StateMatchLogic {
    private static final Hashtable<String,String> stateAbbreviations = new Hashtable<String,String>() {{
        // List received from -> https://www.factmonster.com/us/postal-information/state-abbreviations-and-state-postal-codes
        put("al", "alabama");
        put("ala", "alabama");
        put("alabama", "alabama");
        put("ak", "alaska");
        put("alaska", "alaska");
        put("as", "");
        put("americansamoa", "americansamoa");
        put("az", "arizona");
        put("ariz", "arizona");
        put("arizona", "arizona");
        put("ar", "arkansas");
        put("ark", "arkansas");
        put("arkansas", "arkansas");
        put("ca", "california");
        put("calif", "california");
        put("california", "california");
        put("co", "colorado");
        put("colo", "colorado");
        put("colorado", "colorado");
        put("ct", "connecticut");
        put("conn", "connecticut");
        put("connecticut", "connecticut");
        put("de", "delaware");
        put("del", "delaware");
        put("delaware", "delaware");
        put("dc", "distofcolumbia");
        put("distofcolumbia", "distofcolumbia");
        put("fl", "florida");
        put("fla", "florida");
        put("florida", "florida");
        put("ga", "georgia");
        put("georgia", "georgia");
        put("gu", "guam");
        put("guam", "guam");
        put("hi", "hawaii");
        put("hawaii", "hawaii");
        put("id", "idaho");
        put("idaho", "idaho");
        put("il", "illinois");
        put("ill", "illinois");
        put("illinois", "illinois");
        put("in", "indiana");
        put("ind", "indiana");
        put("indiana", "indiana");
        put("ia", "iowa");
        put("iowa", "iowa");
        put("ks", "kansas");
        put("kans", "kansas");
        put("kansas", "kansas");
        put("ky", "kentucky");
        put("kentucky", "kentucky");
        put("la", "louisiana");
        put("louisiana", "louisiana");
        put("me", "maine");
        put("maine", "maine");
        put("md", "maryland");
        put("maryland", "maryland");
        put("mh", "marshallislands");
        put("marshallislands", "marshallislands");
        put("ma", "massachusetts");
        put("mass", "massachusetts");
        put("massachusetts", "massachusetts");
        put("mi", "michigan");
        put("mich", "michigan");
        put("michigan", "michigan");
        put("fm", "Micronesia");
        put("Micronesia", "Micronesia");
        put("mn", "minnesota");
        put("minn", "minnesota");
        put("minnesota", "minnesota");
        put("ms", "mississippi");
        put("miss", "mississippi");
        put("mississippi", "mississippi");
        put("mo", "missouri");
        put("missouri", "missouri");
        put("mt", "montana");
        put("mont", "montana");
        put("montana", "montana");
        put("ne", "nebraska");
        put("nebr", "nebraska");
        put("nebraska", "nebraska");
        put("nv", "nevada");
        put("nev", "nevada");
        put("nevada", "nevada");
        put("nh", "newhampshire");
        put("newhampshire", "newhampshire");
        put("nj", "newjersey");
        put("newjersey", "newjersey");
        put("nm", "newmexico");
        put("newmexico", "newmexico");
        put("ny", "newyork");
        put("newyork", "newyork");
        put("nc", "northcarolina");
        put("northcarolina", "northcarolina");
        put("nd", "");
        put("northdakota", "northdakota");
        put("mp", "");
        put("northernmarianas", "northernmarianas");
        put("oh", "ohio");
        put("ohio", "ohio");
        put("ok", "oklahoma");
        put("okla", "oklahoma");
        put("oklahoma", "oklahoma");
        put("or", "oregon");
        put("ore", "oregon");
        put("oregon", "oregon");
        put("pw", "palau");
        put("palau", "palau");
        put("pa", "pennsylvania");
        put("pennsylvania", "pennsylvania");
        put("pr", "puertorico");
        put("puertorico", "puertorico");
        put("ri", "rhodeisland");
        put("rhodeisland", "rhodeisland");
        put("sc", "southcarolina");
        put("southcarolina", "southcarolina");
        put("sd", "southdakota");
        put("southdakota", "southdakota");
        put("tn", "tennessee");
        put("tenn", "tennessee");
        put("tennessee", "tennessee");
        put("tx", "texas");
        put("tex", "texas");
        put("texas", "texas");
        put("ut", "utah");
        put("utah", "utah");
        put("vt", "vermont");
        put("vermont", "vermont");
        put("va", "virginia");
        put("virginia", "virginia");
        put("vi", "virginislands");
        put("virginislands", "virginislands");
        put("wa", "washington");
        put("wash", "washington");
        put("washington", "washington");
        put("wv", "westvirginia");
        put("wva", "westvirginia");
        put("westvirginia", "westvirginia");
        put("wi", "wisconsin");
        put("wis", "wisconsin");
        put("wisconsin", "wisconsin");
        put("wy", "wyoming");
        put("wyo", "wyoming");
        put("wyoming", "wyoming");
    }};

    /*******************************************************************************************************************
     *************************************************** Get Methods ***************************************************
     *******************************************************************************************************************/
    public static Hashtable<String,String> getStateAbbreviation(){
        return stateAbbreviations;
    }

    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getStateMatchResult(String normalizedState1, String normalizedState2){
        if ((getStateAbbreviation().containsKey(normalizedState1) && getStateAbbreviation().containsKey(normalizedState2)) &&
                getStateAbbreviation().get(normalizedState1).equals(getStateAbbreviation().get(normalizedState2))){
            return AnswerType.yes;
        }

        return AnswerType.no;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static String setNormalizeState(String state){
        return state.trim().replaceAll("[^a-zA-Z]", "").toLowerCase();
    }
}
