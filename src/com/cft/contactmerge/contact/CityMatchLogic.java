package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public abstract class CityMatchLogic {
    private static final Hashtable<String,String> compassDirection = StreetMatchLogic.getCompassDirection();

    /*******************************************************************************************************************
     *************************************************** Get Methods ***************************************************
     *******************************************************************************************************************/
    public static Hashtable<String,String> getCompassDirection(){
        return compassDirection;
    }

    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getCityMatchResult(ArrayList<String> cityParts1, ArrayList<String> cityParts2){
        String city1Direction;
        String city2Direction;

        // abstract street direction if the address has one
        city1Direction = getDirectionFromCity(cityParts1);
        city2Direction = getDirectionFromCity(cityParts2);

        return getMatchCombinationResults(cityParts1, cityParts2, city1Direction, city2Direction);
    }

    private static String getDirectionFromCity(ArrayList<String> stAddressParts){
        StringBuilder stDirection = new StringBuilder();

        for (int stDirectionElement = 0, numOfDirections = 0; stDirectionElement < stAddressParts.size(); stDirectionElement++){
            if (getCompassDirection().containsKey(stAddressParts.get(stDirectionElement))){
                stDirection.append(getCompassDirection().get(stAddressParts.get(stDirectionElement)));
                stAddressParts.remove(stDirectionElement); // remove the direction from the address
                numOfDirections++;
                stDirectionElement--;
            }
            if (numOfDirections == 2){
                break;
            }
        }

        return stDirection.toString();
    }

    private static AnswerType getMatchCombinationResults(ArrayList<String> cityParts1, ArrayList<String> cityParts2,
                                                         String city1Direction, String city2Direction){
        // Precondition: stAddressParts1 and stAddressParts2 must only contain the street number and street name
        AnswerType cityDirectionMatch = getCityDirectionMatchResults(city1Direction, city2Direction);

        if (cityParts1.equals(cityParts2) && cityDirectionMatch.equals(AnswerType.yes)){
            return AnswerType.yes;
        } else if (cityParts1.equals(cityParts2) && cityDirectionMatch.equals(AnswerType.maybe)){
            return AnswerType.maybe;
        }

        return AnswerType.no;
    }

    private static AnswerType getCityDirectionMatchResults(String city1Direction, String city2Direction){
        if (city1Direction.equals(city2Direction)){
            return AnswerType.yes;
        } else if (city1Direction.isEmpty() ^ city2Direction.isEmpty()){
            return AnswerType.maybe;
        }

        return AnswerType.no;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static ArrayList<String> setNormalizeCity(String city){
        return new ArrayList<>(Arrays.asList(city.trim().replaceAll("[^^a-zA-Z0-9 ]", " ").
                toLowerCase().split("\\s+")));
    }
}
