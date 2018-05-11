/* Created by James Page on May 8th, 2018
 * Contains the order in which the last names will be considered a match or not...
 * Exact Match (and matched with punctuations), matched with hyphenated parts, matched with apostrophe.
 */

package com.cft.contactmerge;

import java.util.ArrayList;

public abstract class LastNameMatcher extends NameMatchLogic {
    public static AnswerType getLastNameMatchResult(ArrayList<String> contactOneLastName, ArrayList<String> contactTwoLastName){
        //Note: if both names contained punctuations and were split into an array of parts of a name...
        //      and both names containing their own parts are exactly the same...
        //      the exactMatch will be confirmed
        if (isNamesExactMatch(contactOneLastName, contactTwoLastName)){
            return AnswerType.yes;
        }

        /* Check if both names are split based on punctuation, and these names parts are in reversing/mixed order
        if ((contactOneFirstName.size() > 1 && contactTwoFirstName.size() > 1) && contactOneFirstName.size() == contactTwoFirstName.size()){
            // ToDO: Should I include code to handle this scenario?? ReturnType if Found?
        }
        */

        if (doNamesContainMultipleParts(contactOneLastName, contactTwoLastName)){
            if (isNameMatchWithHyphens(contactOneLastName, contactTwoLastName)) {
                return getHyphinatedNamesMatchResults(contactOneLastName, contactTwoLastName); // yes or maybe
            } else if (combineNameParts(contactOneLastName).equals(combineNameParts(contactTwoLastName))){
                return AnswerType.yes;
            }
        }
        return AnswerType.no;
    }
}
