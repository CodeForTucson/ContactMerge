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
    private final static String[] namePunctuations = {".", ",", "'", "-"};
    protected final static String[][] alternateFirstNames = // List of names to match nicknames of the same person
            {
                    {"Al", "Allen"},
                    {"Al", "Alan"},
                    {"Bob", "Robert"}
            };

    /***********************************************************************
     ********************* Names Compare Logic Methods *********************
     ***********************************************************************/
    protected static boolean isNamesExactMatch(ArrayList<String> nameOne, ArrayList<String> nameTwo){
        return nameOne.equals(nameTwo);
    }

    protected static boolean doNamesContainMultipleParts(ArrayList<String> nameOne, ArrayList<String> nameTwo){
        return nameOne.size() > 1 || nameTwo.size() > 1;
    }

    protected static boolean doNamesContainMultipleParts(ArrayList<String> nameOne, ArrayList<String> nameTwo, ArrayList<String> nameThree, ArrayList<String> nameFour){
        return nameOne.size() > 1 || nameTwo.size() > 1 || nameThree.size() > 1 || nameFour.size() > 1;
    }

    protected static boolean doNamesContainSingleCharacterOnly(ArrayList<String> nameOne, ArrayList<String> nameTwo){
        return nameOne.get(0).length() == 1 || nameTwo.get(0).length() == 1;
    }

    protected static AnswerType getHyphinatedNamesMatchResults(ArrayList<String> hNameOne, ArrayList<String> hNameTwo){
        if (getNumOfCharsInName(hNameOne) > hyphenCharLimitYesVsMaybe && getNumOfCharsInName(hNameTwo) > hyphenCharLimitYesVsMaybe){
            return AnswerType.yes;
        }
        return AnswerType.maybe;
    }

    protected static AnswerType getFirstLastSwapNameMatchResult(ArrayList<String> contactOneFirstName, ArrayList<String> contactOneLastName, ArrayList<String> contactTwoFirstName, ArrayList<String> contactTwoLastName){
        // Check if both name sets are exact match
        if (isNamesExactMatch(contactOneFirstName, contactTwoLastName) || isNamesExactMatch(contactTwoFirstName, contactOneLastName)){
            return AnswerType.maybe;
        }

        // Check names with hyphen
        if (doNamesContainMultipleParts(contactOneFirstName, contactTwoFirstName, contactOneLastName, contactTwoLastName) &&
                (isNameMatchWithHyphens(contactOneFirstName, contactTwoLastName) && isNameMatchWithHyphens(contactTwoFirstName, contactOneLastName))
                ){
            return AnswerType.maybe;
        }

        // Check names with apostrophe
        if (doNamesContainMultipleParts(contactOneFirstName, contactTwoFirstName, contactOneLastName, contactTwoLastName) &&
                (combineNameParts(contactOneFirstName).equals(combineNameParts(contactTwoLastName)) && (combineNameParts(contactTwoFirstName).equals(combineNameParts(contactOneLastName))))
                ){
            return AnswerType.yes;
        }

        return AnswerType.no;
    }

    protected static boolean isNameMatchWithHyphens(ArrayList<String> nameOne, ArrayList<String> nameTwo){
        if (nameTwo.size() > nameOne.size()){
            for (String namePart: nameOne) {
                if (!nameTwo.contains(namePart)) {
                    return false;
                }
            }
        } else {
            for (String namePart: nameTwo) {
                if (!nameOne.contains(namePart)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected static boolean isNameMatchedWithInitial(ArrayList<String> nameOne, ArrayList<String> nameTwo){
        if (nameOne.get(0).length() == 1 && nameTwo.get(0).startsWith(nameOne.get(0))){
            return true;
        } else if (nameTwo.get(0).length() == 1 && nameOne.get(0).startsWith(nameTwo.get(0))){
            return true;
        }
        return false;
    }

    /***********************************************************************
     *************************** Support Methods ***************************
     ***********************************************************************/

    protected static ArrayList<String> editNameForMatching(String name){
        for(String punctuation: namePunctuations){
            name = name.replace(punctuation, " ");
        }

        return new ArrayList<>(Arrays.asList(name.trim().toLowerCase().split("\\s+")));
    }

    protected static int getNumOfCharsInName (ArrayList<String> name){
        int numOfCharsInName = 0;

        for (String namePart: name) {
            numOfCharsInName = numOfCharsInName + namePart.length();
        }

        return numOfCharsInName;
    }

    protected static String combineNameParts(ArrayList<String> name){
        StringBuilder combinedName = new StringBuilder();

        for (String namePart: name){
            combinedName.append(namePart);
        }

        return combinedName.toString();
    }
}
