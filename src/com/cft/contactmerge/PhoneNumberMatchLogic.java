package com.cft.contactmerge;

public abstract class PhoneNumberMatchLogic {
    private static final int usaNumberSize = 10;
    private static final int usaAreaCodeSize = 3;
    private static final int usaNumberSize_NoAreaCode = 7;
    private static final int usaNumberSize_InternationalNumber_NoAreaCode = 8;
    private static final int usaNumberSize_InternationalDigit_AreaCode = 11;
    /*******************************************************************************************************************
     ************************************************* Matching Methods ************************************************
     *******************************************************************************************************************/
    public static AnswerType getPhoneNumberMatchResult(String phoneNumber1, String phoneNumber2) {
        if (phoneNumber1.equals(phoneNumber2)) {
            return AnswerType.yes;
        } else if (phoneNumber1.length() == getUsaNumberSize_NoAreaCode() && phoneNumber2.length() == getUsaNumberSize() &&
                phoneNumber1.equals(phoneNumber2.substring(getUsaAreaCodeSize(), phoneNumber2.length()))){
            return AnswerType.maybe;
        } else if (phoneNumber2.length() == getUsaNumberSize_NoAreaCode() && phoneNumber1.length() == getUsaNumberSize() &&
                phoneNumber2.equals(phoneNumber1.substring(getUsaAreaCodeSize(), phoneNumber1.length()))){
            return AnswerType.maybe;
        }

        return AnswerType.no;
    }

    /*******************************************************************************************************************
     ************************************************* Get/Set Methods *************************************************
     *******************************************************************************************************************/
    public static int getUsaNumberSize_InternationalDigit_AreaCode(){
        return usaNumberSize_InternationalDigit_AreaCode;
    }

    public static int getUsaNumberSize(){
        return usaNumberSize;
    }

    public static int getUsaAreaCodeSize(){
        return usaAreaCodeSize;
    }

    public static int getUsaNumberSize_NoAreaCode(){
        return usaNumberSize_NoAreaCode;
    }

    public static int getUsaNumberSize_InternationalNumber_NoAreaCode(){
        return usaNumberSize_InternationalNumber_NoAreaCode;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static String normalizeNumber(String number){
        return number.replaceAll("[^0-9]", "");
    }
}
