package com.cft.contactmerge.contact;

public abstract class PhoneNumberMatchLogic {
    private static final int usaNumberSize = 10;
    private static final int usaAreaCodeSize = 3;
    private static final int usaNumberSize_NoAreaCode = 7;
    /*******************************************************************************************************************
     ************************************************* Get/Set Methods *************************************************
     *******************************************************************************************************************/
    public static int getUsaNumberSize(){
        return usaNumberSize;
    }

    public static int getUsaAreaCodeSize(){
        return usaAreaCodeSize;
    }

    public static int getUsaNumberSize_NoAreaCode(){
        return usaNumberSize_NoAreaCode;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public static String normalizeNumber(String number){
        return number.replaceAll("[^0-9]", "");
    }
}
