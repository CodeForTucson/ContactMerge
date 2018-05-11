/* Created by James Page on May 8th, 2018
 * Contains the methods deciding the order of comparisons in which the first names will be considered a match or not...
 * Exact Match (and matched with punctuations), matched with hyphenated parts, and matched with initial.

 */

package com.cft.contactmerge;

import java.util.ArrayList;

public abstract class FirstNameMatcher {
    public static AnswerType getFirstNameMatchResult(ArrayList<String> contactOneFirstNames, ArrayList<String> contactTwoFirstNames){
        //Note: if both names contained punctuations and were split into an array of parts of a name...
        //      and both names containing their own parts are exactly the same...
        //      the exactMatch will be confirmed
        if (NameMatchLogic.isNamesExactMatch(contactOneFirstNames, contactTwoFirstNames)){
            return AnswerType.yes;
        }

        // Match Hyphenated Names
        if (NameMatchLogic.isNamesContainMultipleParts(contactOneFirstNames, contactTwoFirstNames)){
            if (NameMatchLogic.isNamePartsMatch(contactOneFirstNames, contactTwoFirstNames)) {
                return NameMatchLogic.getYesOrMaybeHyphenatedNameMatch(contactOneFirstNames, contactTwoFirstNames); // yes or maybe
            }
        }

        // Check names with initial
        if (NameMatchLogic.isNamesContainSingleCharacterOnly(contactOneFirstNames, contactTwoFirstNames) &&
                NameMatchLogic.isNameMatchedWithInitial(contactOneFirstNames, contactTwoFirstNames)
                ){
            return AnswerType.maybe;
        }

        return AnswerType.no;
    }
}
