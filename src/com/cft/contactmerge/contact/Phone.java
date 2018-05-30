package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;
import java.util.Hashtable;

public class Phone implements IContactProperty<Phone> {
    private String countryCallingCode;
    private String areaCode; // always has 3 digits
    private String phoneNumber; // always has 7 digits

    /*******************************************************************************************************************
     *************************************************** Constructors **************************************************
     *******************************************************************************************************************/
    public Phone(){}

    public Phone(String number){
        setFullNumber(number);
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

    private void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountryCallingCode(){
        return this.countryCallingCode;
    }

    private void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    public String getFullNumber(){
        StringBuilder fullNumber = new StringBuilder();

        if (!(getCountryCallingCode() == null || getCountryCallingCode().isEmpty())){
            fullNumber.append(getCountryCallingCode());
        }
        if (!(getAreaCode() == null || getAreaCode().isEmpty())){
            fullNumber.append(getAreaCode());
        }
        if (!(getPhoneNumber() == null || getPhoneNumber().isEmpty())){
            fullNumber.append(getPhoneNumber());
        }

        return fullNumber.toString();
    }

    public void setFullNumber(String fullNumber){
        if (fullNumber == null ||fullNumber.isEmpty()){
            throw new IllegalArgumentException("given number missing");
        }
        fullNumber = PhoneNumberMatchLogic.normalizeNumber(fullNumber);

        if (fullNumber.length() < PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode()){
            throw new IllegalArgumentException("given phone number invalid");
        } else if (fullNumber.length() == PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode()){
            setPhoneNumber(fullNumber);
        } else if (fullNumber.length() > PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode() && fullNumber.length() < PhoneNumberMatchLogic.getUsaNumberSize()){
            setPhoneNumber(fullNumber.substring(fullNumber.length() - PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode(), fullNumber.length()));
            setCountryCallingCode(fullNumber.substring(0, fullNumber.length() - PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode()));
        } else if (fullNumber.length() == PhoneNumberMatchLogic.getUsaNumberSize()) {
            setPhoneNumber(fullNumber.substring(fullNumber.length() - PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode(), fullNumber.length()));
            setAreaCode(fullNumber.substring(0, PhoneNumberMatchLogic.getUsaAreaCodeSize()));
        } else { // 11++
            setPhoneNumber(fullNumber.substring(fullNumber.length() - PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode(), fullNumber.length()));
            setAreaCode(fullNumber.substring(fullNumber.length() - PhoneNumberMatchLogic.getUsaNumberSize(), fullNumber.length() - PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode()));
            setCountryCallingCode(fullNumber.substring(0, fullNumber.length() - PhoneNumberMatchLogic.getUsaNumberSize()));
        }
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    @Override
    public String toString(){
        if ((getCountryCallingCode() == null || getCountryCallingCode().isEmpty()) &&
                (getAreaCode() == null || getAreaCode().isEmpty()) &&
                (getPhoneNumber() == null || getPhoneNumber().isEmpty())
                ){
            return "";
        }

        if (!(getCountryCallingCode() == null || getCountryCallingCode().isEmpty()) &&
                !(getAreaCode() == null || getAreaCode().isEmpty()) &&
                !(getPhoneNumber() == null || getPhoneNumber().isEmpty())
                ){
            return getCountryCallingCode() + "(" + getAreaCode() + ") " + getPhoneNumber().substring(0, PhoneNumberMatchLogic.getUsaAreaCodeSize()) +
                    "-" + getPhoneNumber().substring(PhoneNumberMatchLogic.getUsaAreaCodeSize(), PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode());
        } else if (!(getAreaCode() == null || getAreaCode().isEmpty()) &&
                !(getPhoneNumber() == null || getPhoneNumber().isEmpty())
                ){
            return "(" + getAreaCode() + ") " + getPhoneNumber().substring(0, PhoneNumberMatchLogic.getUsaAreaCodeSize()) +
                    "-" + getPhoneNumber().substring(PhoneNumberMatchLogic.getUsaAreaCodeSize(), PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode());
        } else if (!(getCountryCallingCode() == null || getCountryCallingCode().isEmpty()) &&
                !(getPhoneNumber() == null || getPhoneNumber().isEmpty())
                ){
            return getCountryCallingCode() + " " + getPhoneNumber().substring(0, PhoneNumberMatchLogic.getUsaAreaCodeSize()) +
                    "-" + getPhoneNumber().substring(PhoneNumberMatchLogic.getUsaAreaCodeSize(), PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode());
        }
        return getPhoneNumber().substring(0, PhoneNumberMatchLogic.getUsaAreaCodeSize()) +
                    "-" + getPhoneNumber().substring(PhoneNumberMatchLogic.getUsaAreaCodeSize(), PhoneNumberMatchLogic.getUsaNumberSize_NoAreaCode());
    }

    /*******************************************************************************************************************
     ************************************************** Match Methods **************************************************
     *******************************************************************************************************************/
    @Override
    public AnswerType isMatch(Phone otherPhone){
        // Precondition: phoneNumber variable(of both objects) must have a String(number) of not null or empty
        if ((getPhoneNumber() == null || getPhoneNumber().isEmpty()) ||
                (otherPhone.getPhoneNumber() == null || otherPhone.getPhoneNumber().isEmpty())
                ){
            return AnswerType.no;
        }

        Hashtable<String, AnswerType> matchResults = new Hashtable<>();
        String countryCallingCodeMatch = "isCountryCallingCodeMatch";
        String areaCodeMatch = "isAreaCodeMatch";
        String phoneNumberMatch = "isPhoneNumberMatch";



        if (!(getCountryCallingCode() == null || getCountryCallingCode().isEmpty()) ||
                !(otherPhone.getCountryCallingCode() == null || otherPhone.getCountryCallingCode().isEmpty())){
            matchResults.put(countryCallingCodeMatch, isCountryCallingCodeMatch(otherPhone.getCountryCallingCode()));
        }
        if (!(getAreaCode() == null || getAreaCode().isEmpty()) ||
                !(otherPhone.getAreaCode() == null || otherPhone.getAreaCode().isEmpty())){
            matchResults.put(areaCodeMatch, isAreaCodeMatch(otherPhone.getAreaCode()));
        }
        matchResults.put(phoneNumberMatch, isPhoneNumberMatch(otherPhone.getPhoneNumber()));

        return getGroupMatchResults(matchResults, countryCallingCodeMatch, areaCodeMatch, phoneNumberMatch);
    }

    private AnswerType getGroupMatchResults(Hashtable<String, AnswerType> matchResults, String countryCallingCodeMatch, String areaCodeMatch, String phoneNumberMatch){
        if (matchResults.containsKey(countryCallingCodeMatch) && matchResults.containsKey(areaCodeMatch) && matchResults.containsKey(phoneNumberMatch)){
            if (matchResults.get(countryCallingCodeMatch).equals(AnswerType.yes) &&
                    matchResults.get(areaCodeMatch).equals(AnswerType.yes) &&
                    matchResults.get(phoneNumberMatch).equals(AnswerType.yes)
                    ){
                return AnswerType.yes;
            } else if (matchResults.get(countryCallingCodeMatch).equals(AnswerType.maybe) &&
                    matchResults.get(areaCodeMatch).equals(AnswerType.yes) &&
                    matchResults.get(phoneNumberMatch).equals(AnswerType.yes)
                    ){
                return AnswerType.maybe;
            } else if (matchResults.get(countryCallingCodeMatch).equals(AnswerType.yes) &&
                    matchResults.get(areaCodeMatch).equals(AnswerType.maybe) &&
                    matchResults.get(phoneNumberMatch).equals(AnswerType.yes)
                    ){
                return AnswerType.maybe;
            } else if (matchResults.get(countryCallingCodeMatch).equals(AnswerType.maybe) &&
                    matchResults.get(areaCodeMatch).equals(AnswerType.maybe) &&
                    matchResults.get(phoneNumberMatch).equals(AnswerType.yes)
                    ){
                return AnswerType.maybe;
            } else {
                return AnswerType.no;
            }
        } else if (matchResults.containsKey(areaCodeMatch) && matchResults.containsKey(phoneNumberMatch)){
            if (matchResults.get(areaCodeMatch).equals(AnswerType.yes) && matchResults.get(phoneNumberMatch).equals(AnswerType.yes)){
                return AnswerType.yes;
            } else if (matchResults.get(areaCodeMatch).equals(AnswerType.maybe) && matchResults.get(phoneNumberMatch).equals(AnswerType.yes)){
                return AnswerType.maybe;
            } else {
                return AnswerType.no;
            }
        } else if (matchResults.containsKey(countryCallingCodeMatch) && matchResults.containsKey(phoneNumberMatch)){
            if (matchResults.get(countryCallingCodeMatch).equals(AnswerType.yes) && matchResults.get(phoneNumberMatch).equals(AnswerType.yes)){
                return AnswerType.yes;
            } else if (matchResults.get(countryCallingCodeMatch).equals(AnswerType.maybe) && matchResults.get(phoneNumberMatch).equals(AnswerType.yes)){
                return AnswerType.maybe;
            } else {
                return AnswerType.no;
            }
        } else if (matchResults.containsKey(phoneNumberMatch)){
            return matchResults.get(phoneNumberMatch);
        }

        return AnswerType.no;
    }

    private AnswerType isCountryCallingCodeMatch(String otherCountryCallingCode){
        // Precondition: Both countryCallingCode variables must not be null or empty at the same time
        /* Match Conditions:
         * If both countryCallingCodes are filled in and match, return yes
         * If one countryCallingCode is filled in and the other is not, return maybe
         * Otherwise, return no
         */
        if ((getCountryCallingCode() == null || getCountryCallingCode().isEmpty()) ^ (otherCountryCallingCode == null || otherCountryCallingCode.isEmpty())){
            return AnswerType.maybe;
        }
        if (getCountryCallingCode().equals(otherCountryCallingCode)){
            return AnswerType.yes;
        }
        return AnswerType.no;
    }

    private AnswerType isAreaCodeMatch(String otherAreaCode){
        // Precondition: Both areaCode variables must not be null or empty at the same time
        /* Match Conditions:
         * If both areaCodes are filled in and match, return yes
         * If one areaCode is filled in and the other is not, return maybe
         * Otherwise, return no
         */
        if ((getAreaCode() == null || getAreaCode().isEmpty()) ^ (otherAreaCode == null || otherAreaCode.isEmpty())){
            return AnswerType.maybe;
        }
        if (getAreaCode().equals(otherAreaCode)){
            return AnswerType.yes;
        }
        return AnswerType.no;
    }

    private AnswerType isPhoneNumberMatch(String otherNumber){
        // Precondition: Both phoneNumber variables must be filled in String(Number) at the same time
        /* Match Conditions:
         * If both phoneNumbers are filled in and match, return yes
         * Otherwise, return no
         */
        if (getPhoneNumber().equals(otherNumber)){
            return AnswerType.yes;
        }
        return AnswerType.no;
    }
}