package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

public class Email implements IContactProperty<Email> {
    private String emailAddress;

    /*******************************************************************************************************************
     *************************************************** Constructors **************************************************
     *******************************************************************************************************************/
    public Email(){}

    public Email(String emailAddress){
        setEmailAddress(emailAddress);
    }

    /*******************************************************************************************************************
     ************************************************* Get/Set Methods *************************************************
     *******************************************************************************************************************/
    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    @Override
    public String toString() {
        return getEmailAddress();
    }

    @Override
    public Email getValue() {return this;}

    @Override
    public AnswerType isMatch(IContactProperty<Email> otherEmail){
        if ((getEmailAddress() == null || getEmailAddress().isEmpty()) ||
                (otherEmail.getValue().getEmailAddress() == null || otherEmail.getValue().getEmailAddress().isEmpty())
                ){
            return AnswerType.no;
        }

        return isEmailAddressMatch(otherEmail.getValue().getEmailAddress());
    }

    public AnswerType isEmailAddressMatch(String otherEmailAddress){
        return EmailAddressMatchLogic.getEmailAddressMatchResult(EmailAddressMatchLogic.normalizeEmailAddress(getEmailAddress()),
                EmailAddressMatchLogic.normalizeEmailAddress(otherEmailAddress));
    }
}