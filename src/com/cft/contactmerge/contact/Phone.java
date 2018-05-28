package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.PhoneNumberMatchLogic;

public class Phone implements IContactProperty<Phone> {

    private final String usaInternationalDigit = "1";
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
    @Override
    public Phone getValue(){
        return this;
    }

    public String getAreaCode(){
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = PhoneNumberMatchLogic.normalizeNumber(areaCode);
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = PhoneNumberMatchLogic.normalizeNumber(phoneNumber);
    }

    public String getUsaInternationalDigit(){
        return this.usaInternationalDigit;
    }

    public String getFullNumber(){
        StringBuilder fullNumber = new StringBuilder();

        if (getPhoneNumber().length() == PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode() &&
                getAreaCode().length() == PhoneNumberMatchLogic.getUsaAreaCodeSize()){
            // Get phoneNumber with areaCode and without internationalNumber
        } else if (getPhoneNumber().length() == PhoneNumberMatchLogic.getUsaNumberSize_InternationalNumber_NoAreaCode() &&
                getAreaCode().length() == PhoneNumberMatchLogic.getUsaAreaCodeSize()) {
            // Get phoneNumber with areaCode and internationalNumber
        } else if (getPhoneNumber().length() == PhoneNumberMatchLogic.getUsaNumberSize() &&
                (getAreaCode() == null || getAreaCode().isEmpty())){
            // Get phoneNumber without areaCode and without internationalNumber
        } else if (getPhoneNumber().length() == PhoneNumberMatchLogic.getUsaNumberSize_InternationalDigit_AreaCode() &&
                (getAreaCode() == null || getAreaCode().isEmpty())){
            // Get phoneNumber without areaCode and with internationalNumber
        } else if (getPhoneNumber().length() < PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode() ||
                (getPhoneNumber().length() > PhoneNumberMatchLogic.getUsaNumberSize_InternationalNumber_NoAreaCode() &&
                getPhoneNumber().length() < PhoneNumberMatchLogic.getUsaNumberSize()) ||
                getPhoneNumber().length() > PhoneNumberMatchLogic.getUsaNumberSize_InternationalDigit_AreaCode()
        ){
            // Get AreaCode (if not null or empty) + PhoneNumber
        }

        return fullNumber.toString();
    }

    public void setFullNumber(String fullNumber){
        fullNumber = PhoneNumberMatchLogic.normalizeNumber(fullNumber);

        if (fullNumber.length() == PhoneNumberMatchLogic.getUsaNumberSize()){
            setAreaCode(fullNumber.substring(0, PhoneNumberMatchLogic.getUsaAreaCodeSize()));
            setPhoneNumber(fullNumber.substring(PhoneNumberMatchLogic.getUsaAreaCodeSize()));
        } else if (fullNumber.length() == PhoneNumberMatchLogic.getUsaNumberSize_InternationalDigit_AreaCode()){
            fullNumber = removeUsaInternationalDigitFromNumber(fullNumber);
            setAreaCode(fullNumber.substring(0, PhoneNumberMatchLogic.getUsaAreaCodeSize()));
            setPhoneNumber(getUsaInternationalDigit() + fullNumber.substring(PhoneNumberMatchLogic.getUsaAreaCodeSize()));
        } else {
            setPhoneNumber(fullNumber);
        }
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    @Override
    public String toString(){
        if ((!(getAreaCode() == null) && getAreaCode().length() == PhoneNumberMatchLogic.getUsaAreaCodeSize())
                && getPhoneNumber().length() == PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode()) {
            return "(" + getAreaCode() + ") " + getPhoneNumber().substring(0, PhoneNumberMatchLogic.getUsaAreaCodeSize()) + "-" +
                    getPhoneNumber().substring(PhoneNumberMatchLogic.getUsaAreaCodeSize(), PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode());
        } else if ((!(getAreaCode() == null) && getAreaCode().length() == PhoneNumberMatchLogic.getUsaAreaCodeSize()) &&
                getPhoneNumber().length() == PhoneNumberMatchLogic.getUsaNumberSize_InternationalNumber_NoAreaCode()){
            return getUsaInternationalDigit() + " (" + getAreaCode() + ") " + getPhoneNumber().substring(0, PhoneNumberMatchLogic.getUsaAreaCodeSize()) + "-" +
                    getPhoneNumber().substring(PhoneNumberMatchLogic.getUsaAreaCodeSize(), PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode());
        } else if (getPhoneNumber().length() == PhoneNumberMatchLogic.getUsaNumberSize_InternationalDigit_AreaCode() - 1){
            return "(" + getPhoneNumber().substring(0, PhoneNumberMatchLogic.getUsaAreaCodeSize()) + ") " + getPhoneNumber().substring(PhoneNumberMatchLogic.getUsaAreaCodeSize(), 6) + "-" +
                    getPhoneNumber().substring(6, PhoneNumberMatchLogic.getUsaNumberSize_InternationalDigit_AreaCode() - 1);
        } else if (getPhoneNumber().length() == PhoneNumberMatchLogic.getUsaNumberSize_InternationalDigit_AreaCode()){
            return getUsaInternationalDigit() + " (" + removeUsaInternationalDigitFromNumber(getPhoneNumber()).substring(0, PhoneNumberMatchLogic.getUsaAreaCodeSize()) +
                    ") " + removeUsaInternationalDigitFromNumber(getPhoneNumber()).substring(PhoneNumberMatchLogic.getUsaAreaCodeSize(), 6) + "-" +
                    removeUsaInternationalDigitFromNumber(getPhoneNumber()).substring(6, PhoneNumberMatchLogic.getUsaNumberSize_InternationalDigit_AreaCode() - 1);
        }
        return getAreaCode() + getPhoneNumber();
    }

    @Override
    public AnswerType isMatch(Phone phone){
        // Use getFullNumber()
        return AnswerType.no;
    }

    public AnswerType isPhoneNumberMatch(String otherNumber){
        otherNumber = PhoneNumberMatchLogic.normalizeNumber(otherNumber);
        if (getAreaCode() == null || getAreaCode().isEmpty()){
            return PhoneNumberMatchLogic.getPhoneNumberMatchResult(removeUsaInternationalDigitFromNumber(getPhoneNumber()), removeUsaInternationalDigitFromNumber(otherNumber));
        }

        return PhoneNumberMatchLogic.getPhoneNumberMatchResult(getAreaCode() + removeUsaInternationalDigitFromNumber(getPhoneNumber()),
                                                               removeUsaInternationalDigitFromNumber(otherNumber));
    }

    public String removeUsaInternationalDigitFromNumber(String fullNumber){
        if (fullNumber.startsWith(getUsaInternationalDigit()) &&
                (fullNumber.length() == PhoneNumberMatchLogic.getUsaNumberSize_InternationalDigit_AreaCode() ||
                 fullNumber.length() == PhoneNumberMatchLogic.getUsaNumberSize_InternationalNumber_NoAreaCode())
                ){
            fullNumber = fullNumber.substring(getUsaInternationalDigit().length());
        }
        return fullNumber;
    }
}