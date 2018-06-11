package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public abstract class StreetMatchLogic {
    private static final ArrayList<String> poBoxTypes = new ArrayList<>(Arrays.asList("po", "box"));

    private static final Hashtable<String,String> roadTypes = new Hashtable<String,String>() {{
        put("st", "street");
        put("street", "street");
        put("ave", "avenue");
        put("avenue", "avenue");
        put("dr", "drive");
        put("drive", "drive");
        put("ln", "lane");
        put("lane", "lane");
        put("trl", "trail");
        put("trail", "trail");
        put("cir", "circle");
        put("circle", "circle");
        put("blvd", "boulevard");
        put("pw", "parkway");
        put("pwky", "parkway");
        put("parkway", "parkway");
        put("boulevard", "boulevard");
        put("rd", "road");
        put("road", "road");
    }};

    private static final Hashtable<String,String> compassDirection = new Hashtable<String,String>() {{
        put("n", "north");
        put("north", "north");
        put("ne", "northeast");
        put("northeast", "northeast");
        put("nw", "northwest");
        put("northwest", "northwest");
        put("s", "south");
        put("south", "south");
        put("se", "southeast");
        put("southeast", "southeast");
        put("sw", "southwest");
        put("southwest", "southwest");
        put("e", "east");
        put("east", "east");
        put("w", "west");
        put("west", "west");
    }};

    /*******************************************************************************************************************
     *************************************************** Get Methods ***************************************************
     *******************************************************************************************************************/
    public static Hashtable<String,String> getRoadTypes(){
        return roadTypes;
    }

    public static Hashtable<String,String> getCompassDirection(){
        return compassDirection;
    }

    public static ArrayList<String> getPoBoxTypes(){
        return poBoxTypes;
    }

    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getStreetAddressMatchResult(ArrayList<String> stAddressParts1, ArrayList<String> stAddressParts2){
        // Precondition: All addressParts must follow the rules from the setNormalizeStreetAddress()
        String st1Direction;
        String st2Direction;
        String st1RoadType;
        String st2RoadType;

        // get return on po box addressses (if either one is a po box)
        if (isAddressPoBox(stAddressParts1) && isAddressPoBox(stAddressParts2)){
            // Strip all characters out of address, and only leave numbers
            stAddressParts1 = getNormalizedPoBoxAddress(stAddressParts1);
            stAddressParts2 = getNormalizedPoBoxAddress(stAddressParts2);
            // if both box addresses match, return yes
            if (stAddressParts1.equals(stAddressParts2)){
                return AnswerType.yes;
            } else {
                return AnswerType.no;
            }
        } else if (isAddressPoBox(stAddressParts1) ^ isAddressPoBox(stAddressParts2)){
            return AnswerType.no;
        }

        // abstract street direction if the address has one
        st1Direction = getDirectionFromAddress(stAddressParts1);
        st2Direction = getDirectionFromAddress(stAddressParts2);

        // abstract roadType if the address has one
        st1RoadType = getRoadTypeFromAddress(stAddressParts1);
        st2RoadType = getRoadTypeFromAddress(stAddressParts2);

        return getMatchCombinationResults(stAddressParts1, stAddressParts2,
                st1Direction, st2Direction, st1RoadType, st2RoadType);
    }

    private static String getRoadTypeFromAddress(ArrayList<String> stAddressParts){
        String roadType = "";

        int stRoadTypeElement = 0;
        for (String stPart: stAddressParts){
            if (getRoadTypes().containsKey(stPart)){
                roadType = getRoadTypes().get(stPart);
                stAddressParts.remove(stRoadTypeElement); // remove the roadType from the address
                break;
            }
            stRoadTypeElement++;
        }
        return roadType;
    }

    private static String getDirectionFromAddress(ArrayList<String> stAddressParts){
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

    private static ArrayList<String> getNormalizedPoBoxAddress(ArrayList<String> stAddressParts){
        for (int part = 0; part < stAddressParts.size(); part++){
            if (!isInteger(stAddressParts.get(part))){
                stAddressParts.remove(part);
                part--;
            }
        }
        return stAddressParts;
    }

    private static AnswerType getMatchCombinationResults(ArrayList<String> stAddressParts1, ArrayList<String> stAddressParts2,
                                                         String st1Direction, String st2Direction,
                                                         String st1RoadType, String st2RoadType){
        // Precondition: stAddressParts1 and stAddressParts2 must only contain the street number and street name
        AnswerType stDirectionMatch = getStDirectionMatchResults(st1Direction, st2Direction);
        AnswerType stRoadTypeMatch = getStRoadTypeMatchResults(st1RoadType, st2RoadType);

        if (stAddressParts1.equals(stAddressParts2)){
            if (stDirectionMatch.equals(AnswerType.yes) && stRoadTypeMatch.equals(AnswerType.yes)){
                return AnswerType.yes;
            } else if (!stDirectionMatch.equals(AnswerType.no) && stRoadTypeMatch.equals(AnswerType.maybe)){
                return AnswerType.maybe;
            } else if (stDirectionMatch.equals(AnswerType.maybe) && !stRoadTypeMatch.equals(AnswerType.no)){
                return AnswerType.maybe;
            }
        }

        return AnswerType.no;
    }

    private static boolean isAddressPoBox(ArrayList<String> stAddressParts){
        if (stAddressParts.size() > 1 && getPoBoxTypes().contains(stAddressParts.get(0))){
            return true;
        } else if (stAddressParts.size() > 2 && getPoBoxTypes().contains(stAddressParts.get(0)) && getPoBoxTypes().contains(stAddressParts.get(1))){
            return true;
        }
        return (stAddressParts.size() > 3 && getPoBoxTypes().contains(stAddressParts.get(0) + stAddressParts.get(1)) && getPoBoxTypes().contains(stAddressParts.get(2)));
    }

    private static AnswerType getStDirectionMatchResults(String st1Direction, String st2Direction){
        if (st1Direction.equals(st2Direction)){
            return AnswerType.yes;
        } else if (st1Direction.isEmpty() ^ st2Direction.isEmpty()){
            return AnswerType.maybe;
        }

        return AnswerType.no;
    }

    private static AnswerType getStRoadTypeMatchResults(String st1RoadType, String st2RoadType){
        if (st1RoadType.equals(st2RoadType)){
            return AnswerType.yes;
        } else if (st1RoadType.isEmpty() ^ st2RoadType.isEmpty()){
            return AnswerType.maybe;
        }

        return AnswerType.no;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static ArrayList<String> setNormalizeStreetAddress(String stAddress){
        return new ArrayList<>(Arrays.asList(stAddress.trim().replaceAll("[^a-zA-Z0-9 ]", " ").
                toLowerCase().split("\\s+")));
    }

    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
