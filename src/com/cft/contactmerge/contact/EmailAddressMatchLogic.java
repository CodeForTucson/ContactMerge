package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public abstract class EmailAddressMatchLogic {
    public static AnswerType getEmailAddressMatchResult(String firstEmailAddress, String secondEmailAddress){
        if (firstEmailAddress.equals(secondEmailAddress)){
            return AnswerType.yes;
        } else if (firstEmailAddress.toLowerCase().equals(secondEmailAddress.toLowerCase())){
            return AnswerType.maybe;
        }

        return AnswerType.no;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static String normalizeEmailAddress(String emailAddress){
        return emailAddress.trim().replaceAll("\\s+","");
    }
}
