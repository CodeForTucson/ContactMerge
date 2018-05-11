/* Created by James Page on May 8th, 2018
 * Contains the methods deciding the order of comparisons in which the last names will be considered a match or not...
 * Exact Match (and matched with punctuations), and matched with hyphenated parts.
 */

package com.cft.contactmerge;

import java.util.ArrayList;

public abstract class LastNameMatcher {
    public static AnswerType getLastNameMatchResult(ArrayList<String> contactOneLastNames, ArrayList<String> contactTwoLastNames){
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
}
