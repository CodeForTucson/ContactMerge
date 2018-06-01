/* Created by James Page on May 8th, 2018
 * Contains the low level business logic for matching naming properties
 */

package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public abstract class NameMatchLogic {
    /***********************************************************************
     ****************************** Variables ******************************
     ***********************************************************************/
    private static final int hyphenCharLimitYesVsMaybe = 3;
    private static final Hashtable<String,String> namePrefixes = new Hashtable<String,String>() {{
        //Note: Use .toLowerCase() when checking if the key exists
        put("mr", "Mr.");
        put("mrs", "Mrs.");
        put("miss", "Miss");
        put("dr", "Dr.");
    }};

    private static final Hashtable<String,String> nameSuffixes = new Hashtable<String,String>() {{
        //Note: Use .toLowerCase() when checking if the key exists
        put("jr", "Jr.");
        put("sr", "Sr.");
        put("1st", "1st");
        put("i", "I");
        put("2nd", "2nd");
        put("ii", "II");
        put("3rd", "3rd");
        put("iii", "III");
        put("4th", "4th");
        put("iv", "IV");
        put("5th", "5th");
        put("v", "V");
        put("6th", "6th");
        put("vi", "VI");
    }};

    private static final Hashtable<String,String> nameSuffixesMatch = new Hashtable<String,String>() {{
        //Note: Use .toLowerCase() when checking if the key exists
        put("jr", "Jr.");
        put("sr", "Sr.");
        put("1st", "I");
        put("i", "I");
        put("2nd", "II");
        put("ii", "II");
        put("3rd", "III");
        put("iii", "III");
        put("4th", "IV");
        put("iv", "IV");
        put("5th", "V");
        put("v", "V");
        put("6th", "VI");
        put("vi", "VI");
    }};

    /*******************************************************************************************************************
     *************************************************** Get Methods ***************************************************
     *******************************************************************************************************************/
    public static int getHyphenCharLimitYesVsMaybe(){
        return hyphenCharLimitYesVsMaybe;
    }

    public static Hashtable<String,String> getNamePrefixes(){
        return namePrefixes;
    }

    public static Hashtable<String,String> getNameSuffixes(){
        return nameSuffixes;
    }

    public static Hashtable<String,String> getNameSuffixesMatch(){
        return nameSuffixesMatch;
    }

    /***********************************************************************
     ********************* Names Compare Logic Methods *********************
     ***********************************************************************/
    public static AnswerType isNameMatch(ArrayList<String> contactOneLastNames, ArrayList<String> contactTwoLastNames){
        //Note: if both names contained punctuations and were split into an array of parts of a name...
        //      and both names containing their own parts are exactly the same...
        //      the exactMatch will be confirmed
        if (NameMatchLogic.isNamesExactMatch(contactOneLastNames, contactTwoLastNames)){
            return AnswerType.yes;
        }

        // Match Hyphenated Names
        if (NameMatchLogic.isNamesContainMultipleParts(contactOneLastNames, contactTwoLastNames)){
            if (NameMatchLogic.isNamePartsMatch(contactOneLastNames, contactTwoLastNames)) {
                return NameMatchLogic.getYesOrMaybeHyphenatedNameMatch(contactOneLastNames, contactTwoLastNames); // yes or maybe
            }
        }
        return AnswerType.no;
    }

    public static AnswerType isNameMatch_CheckInitial(ArrayList<String> contactOneFirstNames, ArrayList<String> contactTwoFirstNames){
        AnswerType possibleMatch = isNameMatch(contactOneFirstNames, contactTwoFirstNames);

        // Check names with initial
        if (possibleMatch.equals(AnswerType.no)) {
            if (NameMatchLogic.isNamesContainSingleCharacterOnly(contactOneFirstNames, contactTwoFirstNames) &&
                    NameMatchLogic.isNameMatchedWithInitial(contactOneFirstNames, contactTwoFirstNames)
                    ) {
                possibleMatch = AnswerType.maybe;
            }
        }

        return possibleMatch;
    }

    public static boolean isSuffixComparisonMatch(String suffixOne, String suffixTwo){
        if (getNameSuffixesMatch().containsKey(suffixOne) && getNameSuffixesMatch().containsKey(suffixTwo)){
            return (getNameSuffixesMatch().get(suffixOne).equals(getNameSuffixesMatch().get(suffixTwo)));
        }
        return false;
    }

    public static boolean isNamesExactMatch(ArrayList<String> nameOne, ArrayList<String> nameTwo){
        return nameOne.equals(nameTwo);
    }

    public static boolean isNamesContainMultipleParts(ArrayList<String> nameOne, ArrayList<String> nameTwo){
        return nameOne.size() > 1 || nameTwo.size() > 1;
    }

    public static boolean isNamesContainMultipleParts(ArrayList<String> nameOne, ArrayList<String> nameTwo, ArrayList<String> nameThree, ArrayList<String> nameFour){
        return nameOne.size() > 1 || nameTwo.size() > 1 || nameThree.size() > 1 || nameFour.size() > 1;
    }

    public static boolean isNamesContainSingleCharacterOnly(ArrayList<String> nameOne, ArrayList<String> nameTwo){
        return nameOne.get(0).length() == 1 || nameTwo.get(0).length() == 1;
    }

    public static AnswerType getYesOrMaybeHyphenatedNameMatch(ArrayList<String> hNameOne, ArrayList<String> hNameTwo){
        /*
         * Precondition: One of two names must have already contained more than one parts, and matched.
         */
        if (getNumOfCharsInName(hNameOne) > getHyphenCharLimitYesVsMaybe() && getNumOfCharsInName(hNameTwo) > getHyphenCharLimitYesVsMaybe()){
            return AnswerType.yes;
        }
        return AnswerType.maybe;
    }

    public static AnswerType getFirstLastSwapNameMatchResult(ArrayList<String> contactOneFirstNames, ArrayList<String> contactOneLastNames, ArrayList<String> contactTwoFirstNames, ArrayList<String> contactTwoLastNames){
        /*
         * Precondition: Names must be pre-configured for the following matching requirements...
         * Names contain alphabet characters only
         * The characters are all lowercase only (for all names)
         * No leading/trailing white spaces
         * Spaces and Punctuations in between the name must removed after being separated into name parts of the ArrayList.
         */

        // Check if both name sets are exact match
        if (isNamesExactMatch(contactOneFirstNames, contactTwoLastNames) && isNamesExactMatch(contactTwoFirstNames, contactOneLastNames)){
            return AnswerType.maybe;
        }

        // Match Hyphenated Names
        if (isNamesContainMultipleParts(contactOneFirstNames, contactTwoFirstNames, contactOneLastNames, contactTwoLastNames) &&
                (isNamePartsMatch(contactOneFirstNames, contactTwoLastNames) && isNamePartsMatch(contactTwoFirstNames, contactOneLastNames))
                ){
            return AnswerType.maybe;
        }

        return AnswerType.no;
    }

    public static boolean isNamePartsMatch(ArrayList<String> namePartsOne, ArrayList<String> namePartsTwo){
        if (namePartsTwo.size() > namePartsOne.size()){
            for (String namePart: namePartsOne) {
                if (!namePartsTwo.contains(namePart)) {
                    return false;
                }
            }
        } else {
            for (String namePart: namePartsTwo) {
                if (!namePartsOne.contains(namePart)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNameMatchedWithInitial(ArrayList<String> nameOne, ArrayList<String> nameTwo){
        return (nameOne.get(0).length() == 1 && nameTwo.get(0).startsWith(nameOne.get(0))) || (nameTwo.get(0).length() == 1 && nameOne.get(0).startsWith(nameTwo.get(0)));
    }

    /***********************************************************************
     *************************** Support Methods ***************************
     ***********************************************************************/

    public static ArrayList<String> setNormalizeName(String name){
        return new ArrayList<>(Arrays.asList(name.trim().replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+")));
    }

    public static String setNormalizeSuffix(String suffix){
        return suffix.trim().replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
    }

    public static int getNumOfCharsInName (ArrayList<String> name){
        /*
         * Precondition: Each character in the String elements must only contain alpha characters
         */
        int numOfCharsInName = 0;

        for (String namePart: name) {
            numOfCharsInName = numOfCharsInName + namePart.length();
        }

        return numOfCharsInName;
    }
}
