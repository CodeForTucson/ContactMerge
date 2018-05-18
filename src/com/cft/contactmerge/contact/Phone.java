package com.cft.contactmerge.contact;

public class Phone {
    private String areaCode;
    private String phoneNumber;

    /*******************************************************************************************************************
     *************************************************** Constructors **************************************************
     *******************************************************************************************************************/
    public Phone(){}

    public Phone(String areaCode, String phoneNumber){
        setAreaCode(areaCode);
        setPhoneNumber(phoneNumber);
    }

    /*******************************************************************************************************************
     ************************************************* Get/Set Methods *************************************************
     *******************************************************************************************************************/
    public String getAreaCode(){
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    public String toString(){
        if (getAreaCode().length() == 3 && getPhoneNumber().length() == 7) {
            return "(" + getAreaCode() + ") " + getPhoneNumber().substring(0, 3) + "-" +
                    getPhoneNumber().substring(3, 7);
        }
        return getAreaCode() + getPhoneNumber();
    }
}
