/* Created by James Page on May 8th, 2018
 * Contains the order in which the first names will be considered a match or not...
 * Exact Match (and matched with punctuations), matched with hyphenated parts, matched with apostrophe, and matched with initial.
 */

package com.cft.contactmerge;

import java.util.ArrayList;

public abstract class FirstNameMatcher extends NameMatchLogic {
    public static AnswerType getFirstNameMatchResult(ArrayList<String> contactOneFirstName, ArrayList<String> contactTwoFirstName){
        //Note: if both names contained punctuations and were split into an array of parts of a name...
        //      and both names containing their own parts are exactly the same...
        //      the exactMatch will be confirmed
        if (isNamesExactMatch(contactOneFirstName, contactTwoFirstName)){
            return AnswerType.yes;
        }

        /* Check if both names are split based on punctuation, and these names parts are in reversing/mixed order
        if ((contactOneFirstName.size() > 1 && contactTwoFirstName.size() > 1) && contactOneFirstName.size() == contactTwoFirstName.size()){
            // ToDO: Should I include code to handle this scenario?? ReturnType if Found?
        }
        */

        if (doNamesContainMultipleParts(contactOneFirstName, contactTwoFirstName)){
            if (isNameMatchWithHyphens(contactOneFirstName, contactTwoFirstName)) {
                return getHyphinatedNamesMatchResults(contactOneFirstName, contactTwoFirstName); // yes or maybe
            } else if (combineNameParts(contactOneFirstName).equals(combineNameParts(contactTwoFirstName))){
                return AnswerType.yes;
            }
        }

        // Check names with initial
        if (doNamesContainSingleCharacterOnly(contactOneFirstName, contactTwoFirstName) &&
                isNameMatchedWithInitial(contactOneFirstName, contactTwoFirstName)
                ){
            return AnswerType.maybe;
        }

        return AnswerType.no;
    }
}
