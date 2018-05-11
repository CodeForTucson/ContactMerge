/* Modified by James Page on May 8th, 2018
 * Contains the selections of which type of naming properties will be used for matching
 */

package com.cft.contactmerge;

import java.util.ArrayList;

public abstract class NameMatchSelector extends NameMatchLogic {
    /************************************************************************
     *********** Method(s) used to call specific name comparisons ***********
     ************************************************************************/
    public static AnswerType doFirstNamesMatchUsingAllComparisonChecks(String contactOneFirstName, String contactTwoFirstName){
        // edit names to go through simple matching algorithms
        ArrayList<String> editedFirstNameOne = editNameForMatching(contactOneFirstName);
        ArrayList<String> editedFirstNameTwo = editNameForMatching(contactTwoFirstName);

        return FirstNameMatcher.getFirstNameMatchResult(editedFirstNameOne, editedFirstNameTwo);
    }

    public static AnswerType doLastNamesMatchUsingAllComparisonChecks(String contactOneLastName, String contactTwoLastName){
        ArrayList<String> editedLastNameOne = editNameForMatching(contactOneLastName);
        ArrayList<String> editedLastNameTwo = editNameForMatching(contactTwoLastName);

        return LastNameMatcher.getLastNameMatchResult(editedLastNameOne, editedLastNameTwo);
    }

    public static AnswerType doFirstAndLastNamesMatchUsingAllComparisonChecks(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){

        // edit names to go through simple matching algorithms
        ArrayList<String> editedFirstNameOne = editNameForMatching(contactOneFirstName);
        ArrayList<String> editedLastNameOne = editNameForMatching(contactOneLastName);
        ArrayList<String> editedFirstNameTwo = editNameForMatching(contactTwoFirstName);
        ArrayList<String> editedLastNameTwo = editNameForMatching(contactTwoLastName);

        // ToDO: Discussion - What if the person got married/divorced and the last name was changed, but first name is still the same?? How to handle this case?
        // Note: Since first names are more common than last names, lets run last names match first.
        //       If they don't match, then we don't need to see if first names match, and can skip the first names completely during the matching calculations completely.
        AnswerType lNameMatchResults = getLastNameMatchResultWithFirstNameSwap(editedFirstNameOne, editedLastNameOne, editedFirstNameTwo, editedLastNameTwo);
        if (lNameMatchResults.equals(AnswerType.no)){
            return AnswerType.no;
        }

        AnswerType fNameMatchResults = getFirstNameMatchResultWithLastNameSwap(editedFirstNameOne, editedLastNameOne, editedFirstNameTwo, editedLastNameTwo);
        if (fNameMatchResults.equals(AnswerType.no)){
            return AnswerType.no;
        }

        if (fNameMatchResults.equals(AnswerType.maybe) || lNameMatchResults.equals(AnswerType.maybe)){
            return AnswerType.maybe;
        }

        return AnswerType.yes;
    }
    /************************************************************************
     ********************* Name Comparison Sub Method(s) ********************
     ************************************************************************/
    private static AnswerType getFirstNameMatchResultWithLastNameSwap(ArrayList<String> contactOneFirstName, ArrayList<String> contactOneLastName, ArrayList<String> contactTwoFirstName, ArrayList<String> contactTwoLastName){
        AnswerType firstNameMatchResult = FirstNameMatcher.getFirstNameMatchResult(contactOneFirstName, contactTwoFirstName);
        if (!firstNameMatchResult.equals(AnswerType.no)){
            return firstNameMatchResult;
        }

        return getFirstLastSwapNameMatchResult(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName);
    }

    private static AnswerType getLastNameMatchResultWithFirstNameSwap(ArrayList<String> contactOneFirstName, ArrayList<String> contactOneLastName, ArrayList<String> contactTwoFirstName, ArrayList<String> contactTwoLastName){
        AnswerType lastNameMatchResult = LastNameMatcher.getLastNameMatchResult(contactOneLastName, contactTwoLastName);
        if (!lastNameMatchResult.equals(AnswerType.no)){
            return lastNameMatchResult;
        }

        return getFirstLastSwapNameMatchResult(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName);
    }
}
