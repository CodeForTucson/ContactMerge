/* Modified by James Page on May 8th, 2018
 * Contains the selections(optional matching) of which type of naming properties will be used for matching
 */

package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

import java.util.ArrayList;

public abstract class NameMatchSelector {
    /************************************************************************
     *********** Method(s) used to call specific name comparisons ***********
     ************************************************************************/
    public static AnswerType getFirstNamesMatchResultDefaultComparison(String contactOneFirstName, String contactTwoFirstName){
        // edit names to go through simple matching algorithms
        ArrayList<String> normalizedFirstNamesOne = NameMatchLogic.setNormalizeName(contactOneFirstName);
        ArrayList<String> normalizedFirstNamesTwo = NameMatchLogic.setNormalizeName(contactTwoFirstName);

        return FirstNameMatcher.getFirstNameMatchResult(normalizedFirstNamesOne, normalizedFirstNamesTwo);
    }

    public static AnswerType getLastNamesMatchResultDefaultComparisons(String contactOneLastName, String contactTwoLastName){
        ArrayList<String> normalizedLastNamesOne = NameMatchLogic.setNormalizeName(contactOneLastName);
        ArrayList<String> normalizedLastNamesTwo = NameMatchLogic.setNormalizeName(contactTwoLastName);

        return LastNameMatcher.getLastNameMatchResult(normalizedLastNamesOne, normalizedLastNamesTwo);
    }

    public static AnswerType getFirstLastNamesMatchResultDefaultComparisons(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){

        // edit names to go through simple matching algorithms
        ArrayList<String> normalizedFirstNamesOne = NameMatchLogic.setNormalizeName(contactOneFirstName);
        ArrayList<String> normalizedLastNamesOne = NameMatchLogic.setNormalizeName(contactOneLastName);
        ArrayList<String> normalizedFirstNamesTwo = NameMatchLogic.setNormalizeName(contactTwoFirstName);
        ArrayList<String> normalizedLastNamesTwo = NameMatchLogic.setNormalizeName(contactTwoLastName);

        // Note: Since first names are more common than last names, lets run last names match first.
        //       If they don't match, then we don't need to see if first names match, and can skip the first names completely during the matching calculations completely.
        AnswerType lNameMatchResult = LastNameMatcher.getLastNameMatchResult(normalizedLastNamesOne, normalizedLastNamesTwo);
        if (!lNameMatchResult.equals(AnswerType.no)){
            AnswerType fNameMatchResult = FirstNameMatcher.getFirstNameMatchResult(normalizedFirstNamesOne, normalizedFirstNamesTwo);
            if (lNameMatchResult.equals(AnswerType.yes) && fNameMatchResult.equals(AnswerType.yes)){
                return AnswerType.yes;
            } else if (!fNameMatchResult.equals(AnswerType.no)){
                return AnswerType.maybe;
            }
        }

        return NameMatchLogic.getFirstLastSwapNameMatchResult(normalizedFirstNamesOne, normalizedLastNamesOne, normalizedFirstNamesTwo, normalizedLastNamesTwo);
    }
}
