/* Created by James Page on May 8th, 2018
 * Contains the low level business logic for matching naming properties
 */

package com.cft.contactmerge;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class NameMatchLogic {
    /***********************************************************************
     ****************************** Variables ******************************
     ***********************************************************************/
    private final static int hyphenCharLimitYesVsMaybe = 3;

    /***********************************************************************
     ********************* Names Compare Logic Methods *********************
     ***********************************************************************/
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
        if (getNumOfCharsInName(hNameOne) > hyphenCharLimitYesVsMaybe && getNumOfCharsInName(hNameTwo) > hyphenCharLimitYesVsMaybe){
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
