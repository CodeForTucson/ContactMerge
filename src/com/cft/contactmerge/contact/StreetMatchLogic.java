package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public abstract class StreetMatchLogic {
    private static final ArrayList<String> poBoxTypes = new ArrayList<>(Arrays.asList("po", "box"));

    private static final Hashtable<String,String> roadTypes = new Hashtable<String,String>() {{
        put("aly", "alley");
        put("alley", "aly");
        put("anx", "annex");
        put("annex", "annex");
        put("arc", "arcade");
        put("arcade", "arcade");
        put("ave", "avenue");
        put("avenue", "avenue");
        put("byu", "bayoo");
        put("bayoo", "bayoo");
        put("bch", "beach");
        put("beach", "beach");
        put("bnd", "bend");
        put("bend", "bend");
        put("blf", "bluff");
        put("blfs", "bluff");
        put("bluff", "bluff");
        put("bluffs", "bluff");
        put("btm", "bottom");
        put("bottom", "bottom");
        put("blvd", "boulevard");
        put("boulevard", "boulevard");
        put("br", "branch");
        put("branch", "branch");
        put("brg", "bridge");
        put("bridge", "bridge");
        put("brk", "brook");
        put("brks", "brook");
        put("brook", "brook");
        put("brooks", "brook");
        put("bg", "burg");
        put("bgs", "burg");
        put("burg", "burg");
        put("burgs", "burg");
        put("byp", "bypass");
        put("bypass", "bypass");
        put("cp", "camp");
        put("camp", "camp");
        put("cyn", "canyon");
        put("canyon", "canyon");
        put("cpe", "cape");
        put("cape", "cape");
        put("cswy", "causeway");
        put("causeway", "causeway");
        put("ctr", "center");
        put("ctrs", "center");
        put("center", "center");
        put("centers", "center");
        put("cir", "circle");
        put("cirs", "circle");
        put("circle", "circle");
        put("circles", "circle");
        put("clf", "cliff");
        put("clfs", "cliff");
        put("cliff", "cliff");
        put("cliffs", "cliff");
        put("clb", "club");
        put("club", "club");
        put("cmn", "common");
        put("common", "common");
        put("cor", "corner");
        put("cors", "corner");
        put("corner", "corner");
        put("corners", "corner");
        put("crse", "course");
        put("course", "course");
        put("ct", "court");
        put("cts", "courts");
        put("court", "court");
        put("courts", "court");
        put("cv", "cove");
        put("cvs", "cove");
        put("cove", "cove");
        put("coves", "cove");
        put("crk", "creek");
        put("creek", "creek");
        put("cres", "crescent");
        put("crescent", "crescent");
        put("crst", "crest");
        put("crest", "crest");
        put("xing", "crossing");
        put("crossing", "crossing");
        put("xrd", "crossroad");
        put("curv", "curve");
        put("dl", "dale");
        put("dale", "dale");
        put("dm", "dam");
        put("dam", "dam");
        put("dv", "divide");
        put("divide", "divide");
        put("dr", "drive");
        put("drs", "drive");
        put("drive", "drive");
        put("drives", "drive");
        put("est", "estate");
        put("ests", "estate");
        put("estate", "estate");
        put("estates", "estate");
        put("expy", "expressway");
        put("expressway", "expressway");
        put("ext", "extension");
        put("exts", "extension");
        put("extension", "extension");
        put("extensions", "extension");
        put("fls", "fall");
        put("fall", "fall");
        put("falls", "fall");
        put("fry", "ferry");
        put("ferry", "ferry");
        put("fld", "field");
        put("flds", "field");
        put("field", "field");
        put("fields", "field");
        put("flt", "flat");
        put("flts", "flat");
        put("flat", "flat");
        put("flats", "flat");
        put("frd", "ford");
        put("frds", "ford");
        put("ford", "ford");
        put("fords", "ford");
        put("frst", "forest");
        put("forest", "forest");
        put("frg", "forge");
        put("frgs", "forge");
        put("forge", "forge");
        put("forges", "forge");
        put("frk", "fork");
        put("frks", "fork");
        put("fork", "fork");
        put("forks", "fork");
        put("ft", "fort");
        put("fort", "fort");
        put("fwy", "freeway");
        put("freeway", "freeway");
        put("gdn", "garden");
        put("gdns", "garden");
        put("garden", "garden");
        put("gardens", "garden");
        put("gtwy", "gateway");
        put("gateway", "gateway");
        put("gln", "glen");
        put("glns", "glen");
        put("glen", "glen");
        put("glens", "glen");
        put("grn", "green");
        put("grns", "green");
        put("green", "green");
        put("greens", "green");
        put("grv", "grove");
        put("grvs", "grove");
        put("grove", "grove");
        put("groves", "grove");
        put("hbr", "harbor");
        put("hbrs", "harbor");
        put("harbor", "harbor");
        put("harbors", "harbor");
        put("hvn", "haven");
        put("haven", "haven");
        put("hts", "heights");
        put("heights", "heights");
        put("hwy", "highway");
        put("highway", "highway");
        put("hl", "hill");
        put("hls", "hill");
        put("hill", "hill");
        put("hills", "hill");
        put("holw", "hollow");
        put("hollow", "hollow");
        put("inlt", "inlet");
        put("inlet", "inlet");
        put("i", "interstate");
        put("interstate", "interstate");
        put("is", "island");
        put("iss", "island");
        put("island", "island");
        put("islands", "island");
        put("isle", "isle");
        put("jct", "junction");
        put("jcts", "junction");
        put("junction", "junction");
        put("junctions", "junction");
        put("ky", "key");
        put("kys", "key");
        put("key", "key");
        put("keys", "key");
        put("knl", "knoll");
        put("knls", "knoll");
        put("knoll", "knoll");
        put("knolls", "knoll");
        put("lk", "lake");
        put("lks", "lake");
        put("lake", "lake");
        put("lakes", "lake");
        put("lndg", "land");
        put("land", "land");
        put("landing", "land");
        put("ln", "lane");
        put("lane", "lane");
        put("lgt", "light");
        put("lgts", "light");
        put("light", "light");
        put("lights", "light");
        put("lf", "loaf");
        put("loaf", "loaf");
        put("lck", "lock");
        put("lcks", "lock");
        put("lock", "lock");
        put("locks", "lock");
        put("ldg", "lodge");
        put("lodge", "lodge");
        put("loop", "loop");
        put("mall", "mall");
        put("mnr", "manor");
        put("mnrs", "manor");
        put("manor", "manor");
        put("manors", "manor");
        put("mdw", "meadow");
        put("mdws", "meadow");
        put("meadow", "meadow");
        put("meadows", "meadow");
        put("mews", "mews");
        put("ml", "mill");
        put("mls", "mill");
        put("mill", "mill");
        put("mills", "mill");
        put("msn", "mission");
        put("mission", "mission");
        put("mhd", "moorhead");
        put("moorhead", "moorhead");
        put("mtwy", "motorway");
        put("motorway", "motorway");
        put("mt", "mount");
        put("mtn", "mount");
        put("mtns", "mount");
        put("mount", "mount");
        put("mountain", "mount");
        put("mountains", "mount");
        put("nck", "neck");
        put("neck", "neck");
        put("orch", "orchard");
        put("orchard", "orchard");
        put("oval", "oval");
        put("opas", "overpass");
        put("overpass", "overpass");
        put("park", "park");
        put("parks", "park");
        put("pwky", "parkway");
        put("parkway", "parkway");
        put("parkways", "parkway");
        put("pass", "pass");
        put("psge", "passage");
        put("passage", "passage");
        put("path", "path");
        put("pike", "pike");
        put("pne", "pine");
        put("pnes", "pine");
        put("pine", "pine");
        put("pines", "pine");
        put("pl", "place");
        put("place", "place");
        put("pln", "plain");
        put("plns", "plain");
        put("plain", "plain");
        put("plains", "plain");
        put("plz", "plaza");
        put("plaza", "plaza");
        put("pt", "point");
        put("pts", "point");
        put("point", "point");
        put("points", "point");
        put("prt", "port");
        put("prts", "port");
        put("port", "port");
        put("ports", "port");
        put("pr", "prairie");
        put("prairie", "prairie");
        put("radl", "radial");
        put("radial", "radial");
        put("ramp", "ramp");
        put("rnch", "ranch");
        put("ranch", "ranch");
        put("rpd", "rapid");
        put("rpds", "rapid");
        put("rapid", "rapid");
        put("rapids", "rapid");
        put("rst", "rest");
        put("rest", "rest");
        put("rdg", "ridge");
        put("rdgs", "ridge");
        put("ridge", "ridge");
        put("ridges", "ridge");
        put("riv", "river");
        put("river", "river");
        put("rd", "road");
        put("rds", "road");
        put("road", "road");
        put("roads", "road");
        put("rte", "route");
        put("route", "route");
        put("row", "row");
        put("rue", "rue");
        put("run", "run");
        put("shl", "shoal");
        put("shls", "shoal");
        put("shoal", "shoal");
        put("shoals", "shoal");
        put("shr", "shore");
        put("shrs", "shore");
        put("shore", "shore");
        put("shores", "shore");
        put("skwy", "skyway");
        put("skyway", "skyway");
        put("spg", "spring");
        put("spgs", "spring");
        put("spring", "spring");
        put("springs", "spring");
        put("spur", "spur");
        put("spurs", "spur");
        put("sq", "square");
        put("sqs", "square");
        put("square", "square");
        put("squares", "square");
        put("sta", "station");
        put("station", "station");
        put("strm", "stream");
        put("stream", "stream");
        put("st", "street");
        put("sts", "street");
        put("street", "street");
        put("streets", "street");
        put("smt", "summit");
        put("summit", "summit");
        put("ter", "terrace");
        put("terrace", "terrace");
        put("trwy", "throughway");
        put("throughway", "throughway");
        put("trce", "trace");
        put("trace", "trace");
        put("trak", "track");
        put("track", "track");
        put("trl", "trail");
        put("trail", "trail");
        put("tunl", "tunnel");
        put("tunnel", "tunnel");
        put("tpke", "turnpike");
        put("turnpike", "turnpike");
        put("upas", "underpass");
        put("underpass", "underpass");
        put("un", "union");
        put("uns", "union");
        put("union", "union");
        put("unions", "union");
        put("vly", "valley");
        put("vlys", "valley");
        put("valley", "valley");
        put("valleys", "valley");
        put("via", "viaduct");
        put("viaduct", "viaduct");
        put("vw", "view");
        put("vws", "view");
        put("view", "view");
        put("views", "view");
        put("vlg", "village");
        put("vlgs", "village");
        put("village", "village");
        put("villages", "village");
        put("vl", "ville");
        put("vis", "vista");
        put("walk", "walk");
        put("walks", "walk");
        put("wall", "wall");
        put("way", "way");
        put("ways", "way");
        put("wl", "well");
        put("wls", "well");
        put("well", "well");
        put("wells", "well");
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
