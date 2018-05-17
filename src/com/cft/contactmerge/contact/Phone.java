package com.cft.contactmerge.contact;

public class Phone {
    private String phoneType;
    private String areaCode;
    private String phoneNumber;

    /*******************************************************************************************************************
     *************************************************** Constructors **************************************************
     *******************************************************************************************************************/
    public Phone(){
        setPhoneType("");
        setAreaCode("");
        setPhoneNumber("");
    }

    public Phone(String phoneType, String areaCode, String phoneNumber){
        setPhoneType(phoneType);
        setAreaCode(areaCode);
        setPhoneNumber(phoneNumber);
    }

    /*******************************************************************************************************************
     *************************************************** Get Methods ***************************************************
     *******************************************************************************************************************/
    public String getPhoneType(){
        return this.phoneType;
    }

    public String getAreaCode(){
        return this.areaCode;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    /*******************************************************************************************************************
     *************************************************** Set Methods ***************************************************
     *******************************************************************************************************************/
    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
